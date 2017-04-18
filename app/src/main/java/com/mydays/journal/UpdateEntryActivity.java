package com.mydays.journal;

import android.os.Bundle;


public class UpdateEntryActivity extends AddEntryActivity {

    private static final int LOCATION_PERMISSION = 121;
    private int id;
    private Billet billet;
    private DbHelper mDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("id", 0);
        mDb = new DbHelper(this);
        billet = mDb.getBillet(id);
        mTitre.setText(billet.getTitle());
        mContenue.setText(billet.getContent());
        mRating.setRating(billet.getRating());
        mSpinner.setSelection(billet.getCatigorie());
    }

    @Override
    protected void process() {
        mDb.updateBillet(billet.getId(), billet.getDay().toString(), mTitre.getText().toString(), mContenue.getText().toString(), mRating.getRating(), mSpinner.getSelectedItemPosition());
    }


}
