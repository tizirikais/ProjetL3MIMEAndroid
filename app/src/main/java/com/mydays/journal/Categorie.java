package com.mydays.journal;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;


public class Categorie {

    public static final int VERY_HAPPY = 1;
    private Drawable drawable;

    public Categorie(Drawable drawable) {
        this.drawable = drawable;
    }

    public Drawable getDrawable() {
        return drawable;
    }
    static public ArrayList<Categorie> getCategies(Context context){
        ArrayList<Categorie> list = new ArrayList<>();
        list.add(new Categorie(context.getResources().getDrawable(R.drawable.ic_sentiment_very_satisfied_black_48dp)));
        list.add(new Categorie(context.getResources().getDrawable(R.drawable.ic_sentiment_satisfied_black_48dp)));
        list.add(new Categorie(context.getResources().getDrawable(R.drawable.ic_sentiment_neutral_black_48dp)));
        list.add(new Categorie(context.getResources().getDrawable(R.drawable.ic_sentiment_dissatisfied_black_48dp)));
        list.add(new Categorie(context.getResources().getDrawable(R.drawable.ic_sentiment_very_dissatisfied_black_48dp)));
        return list;
    }
}
