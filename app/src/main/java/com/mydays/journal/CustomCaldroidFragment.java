package com.mydays.journal;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

import java.util.HashMap;
import java.util.Map;

import hirondelle.date4j.DateTime;


public class CustomCaldroidFragment extends CaldroidFragment {
    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        // TODO Auto-generated method stub
        return new CaldroidSampleCustomAdapter(getActivity(), month, year, getCaldroidData(),new HashMap<String, Object>());
    }

    private class CaldroidSampleCustomAdapter extends CaldroidGridAdapter {
        public CaldroidSampleCustomAdapter(FragmentActivity activity, int month, int year, Map<String, Object> caldroidData, Map<String, Object> extraData) {
            super(activity, month, year,caldroidData,extraData);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            DateTime dateTime = this.datetimeList.get(position);
            DbHelper dbHelper = new DbHelper(parent.getContext());
            float rating = dbHelper.getAverageRating(dateTime.getYear() + "-" + dateTime.getMonth() + "-" + dateTime.getDay());
            if(rating != 0.0){
                int satisfaction;
                if(rating > 4.0){
                    satisfaction = R.drawable.ic_sentiment_very_satisfied_black_48dp;
                }else if(rating > 3.0){
                    satisfaction = R.drawable.ic_sentiment_satisfied_black_48dp;
                }else if(rating > 2.0){
                    satisfaction = R.drawable.ic_sentiment_neutral_black_48dp;
                }else if(rating > 1.0){
                    satisfaction = R.drawable.ic_sentiment_dissatisfied_black_48dp;
                }else {
                    satisfaction = R.drawable.ic_sentiment_very_dissatisfied_black_48dp;
                }
                view.setBackgroundResource(satisfaction);
            }
            return view;
        }
    }
}
