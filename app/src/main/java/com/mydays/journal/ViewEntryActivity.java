package com.mydays.journal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewEntryActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView titre;
    private TextView contenue;
    private RatingBar rating;
    private ImageView image;
    private int id;
    private DbHelper mDb;
    private Billet billet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titre = (TextView)findViewById(R.id.titre);
        contenue = (TextView)findViewById(R.id.contenue);
        rating = (RatingBar)findViewById(R.id.rating);
        image = (ImageView)findViewById(R.id.categorie);
        id = getIntent().getIntExtra("id",0);
        mDb = new DbHelper(this);
        billet = mDb.getBillet(id);
        titre.setText(billet.getTitle());
        contenue.setText(billet.getContent());
        rating.setRating(billet.getRating());
        image.setImageDrawable(Categorie.getCategies(this).get(billet.getCatigorie()).getDrawable());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(Menu.NONE, 1, Menu.NONE, "Modifier");
        item.setIcon(R.drawable.ic_edit_white_24dp);
        item.setShowAsAction(item.SHOW_AS_ACTION_ALWAYS);
        item = menu.add(Menu.NONE, 2, Menu.NONE, "Supprimer");
        item.setIcon(R.drawable.ic_delete_white_24dp);
        item.setShowAsAction(item.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == 1 ){
            Intent intent = new Intent(this, UpdateEntryActivity.class);
            intent.putExtra("id",billet.getId());
            startActivity(intent);
        }
        if(item.getItemId() == 2 ){
            (new DbHelper(this)).delete(billet.getId());
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(!billet.getPlace().isEmpty()){
            String[] values = billet.getPlace().split(";");
            googleMap.addMarker((new MarkerOptions()).position(new LatLng(Double.valueOf(values[0]),Double.valueOf(values[1]))).title("Vous etiez la"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(values[0]),Double.valueOf(values[1])), 17));
        }

    }
}
