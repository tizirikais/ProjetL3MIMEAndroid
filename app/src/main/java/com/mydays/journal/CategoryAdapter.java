package com.mydays.journal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;


public class CategoryAdapter extends ArrayAdapter<Categorie> {
    private final ArrayList<Categorie> list;

    public CategoryAdapter(Context context, ArrayList<Categorie> list) {
        super(context, 0);
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView image = null;
        if(convertView != null && convertView instanceof ImageView){
            image = (ImageView) convertView;
            image.setImageDrawable(list.get(position).getDrawable());
        }else {
            image = new ImageView(parent.getContext());
            image.setImageDrawable(list.get(position).getDrawable());
        }
        return image;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
