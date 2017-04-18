package com.mydays.journal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public Date mDate;
    private CaldroidFragment calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDate = new Date();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_date);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        calendar = new CustomCaldroidFragment();
        Bundle args = new Bundle();
        final Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        calendar.setArguments(args);

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, calendar);
        t.commit();
        calendar.setCaldroidListener(new CaldroidListener() {
            @Override
            public void onSelectDate(Date pDate, View view) {
                Calendar date = Calendar.getInstance();
                date.setTime(pDate);
                Intent intent = new Intent(MainActivity.this, DayActivity.class);
                intent.putExtra("date", date.get(Calendar.YEAR) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.DAY_OF_MONTH));
                startActivity(intent);
            }

        });
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.day:
                        Intent intent = new Intent(MainActivity.this, DayActivity.class);
                        Date date = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");
                        intent.putExtra("date", dateFormat.format(date));
                        startActivity(intent);
                        break;
                    case R.id.settings:
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        break;
                    case R.id.month:
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        break;
                }
                return true;
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddEntryActivity.class);
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");
                intent.putExtra("date", dateFormat.format(date));
                startActivity(intent);
            }
        });
    }

    /**
     * Save current states of the Caldroid here
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (calendar != null) {
            calendar.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }
    }
}
