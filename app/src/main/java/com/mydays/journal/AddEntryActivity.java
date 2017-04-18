package com.mydays.journal;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;


public class AddEntryActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION = 121;
    protected EditText mContenue;
    protected EditText mTitre;
    protected RatingBar mRating;
    protected String mDate;
    protected Spinner mSpinner;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(Menu.NONE, 1, Menu.NONE, "ajouter");
        item.setIcon(R.drawable.ic_add_white_48dp);
        item.setShowAsAction(item.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == 1  && validateForm()){
            process();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void process() {
        DbHelper mDb = new DbHelper(this);
        Location location = null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
            }
        } else {
            LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
            location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

        }
        mDb.addBillet(mDate,mTitre.getText().toString(),mContenue.getText().toString(),mRating.getRating(),mSpinner.getSelectedItemPosition(),
                location != null ? location.getLatitude()+";"+location.getLongitude():"");
    }

    private boolean validateForm() {
        if(mTitre.getText().toString().isEmpty()){
            return false;
        }
        if(mContenue.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDate = getIntent().getStringExtra("date");
        mContenue = (EditText)findViewById(R.id.contenue);
        mTitre = (EditText)findViewById(R.id.titre);
        mRating = (RatingBar)findViewById(R.id.rating);
        mSpinner = (Spinner)findViewById(R.id.smiley);
        mSpinner.setAdapter(new CategoryAdapter(this, Categorie.getCategies(this)));
    }

}
