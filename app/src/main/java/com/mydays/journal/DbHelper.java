package com.mydays.journal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;


public class DbHelper extends SQLiteOpenHelper {
    protected SQLiteDatabase getDb() {
        if (mDb == null || !mDb.isOpen())
        mDb = getWritableDatabase();
        return mDb;
    }

    static protected SQLiteDatabase mDb = null;

    public DbHelper(Context context) {
        super(context, "journal", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table billet (id integer primary key, title string(255),day date,content text,rating float,categorie integer,place string(255))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList<Billet> getBillets(Date date) {
        String sql = "SELECT * from billet ";
        Cursor cursor;
        if (date != null) {
            sql += "WHERE day = ?";
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cursor = getDb().rawQuery(sql, new String[]{(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DAY_OF_MONTH)))});
        } else {
            cursor = getDb().rawQuery(sql, new String[]{});
        }
        return populate(cursor);
    }

    private ArrayList<Billet> populate(Cursor cursor) {
        ArrayList<Billet> billets = new ArrayList<>();
        while (cursor.moveToNext()) {
            Billet billet = new Billet(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getInt(cursor.getColumnIndex("rating")),
                    cursor.getInt(cursor.getColumnIndex("categorie")),
                    cursor.getString(cursor.getColumnIndex("place")),
                    cursor.getString(cursor.getColumnIndex("content")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    Date.valueOf(cursor.getString(cursor.getColumnIndex("day"))));
            billets.add(billet);
        }
        return billets;
    }

    public ArrayList<Billet> getBillets() {
        return getBillets(null);
    }

    public Billet addBillet(String date, String titre, String contenue, float rating, int category,String place) {
        SQLiteDatabase db = getDb();
        ContentValues values = new ContentValues();
        values.put("day", date);
        values.put("title", titre);
        values.put("content", contenue);
        values.put("rating", rating);
        values.put("categorie", category);
        values.put("place", place);
        long inserted = db.insert("billet", null, values);
        return new Billet((int) inserted, rating, category, "", contenue, titre, Date.valueOf(date));
    }

    public void updateBillet(int id, String date, String titre, String contenue, float rating, int category) {
        SQLiteDatabase db = getDb();
        ContentValues values = new ContentValues();
        values.put("day", date);
        values.put("title", titre);
        values.put("content", contenue);
        values.put("rating", rating);
        values.put("categorie", category);
        db.update("billet", values, "id = " + id, null);

    }

    public Billet getBillet(int id) {
        String sql = "SELECT * from billet WHERE id = ?";
        Cursor cursor = getDb().rawQuery(sql, new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        return new Billet(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getInt(cursor.getColumnIndex("rating")),
                cursor.getInt(cursor.getColumnIndex("categorie")),
                cursor.getString(cursor.getColumnIndex("place")),
                cursor.getString(cursor.getColumnIndex("content")),
                cursor.getString(cursor.getColumnIndex("title")),
                Date.valueOf(cursor.getString(cursor.getColumnIndex("day"))));
    }

    public void delete(Integer id) {
        getDb().delete("billet","id = "+id,new String[]{});
    }

    public float getAverageRating(String date) {
        String sql = "SELECT AVG(rating) from billet WHERE day = ?";
        Cursor cursor = getDb().rawQuery(sql, new String[]{date});
        cursor.moveToFirst();
        getBillets(Date.valueOf(date));
        return cursor.getFloat(0);
    }
}
