package com.mydays.journal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        showProgress(false);
        mMotsdepasse.setError(null);
        mMotsdepasse.setText("");
    }

    private EditText mMotsdepasse;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DbHelper helper = new DbHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        mMotsdepasse = (EditText) findViewById(R.id.password);
        mMotsdepasse.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }
    private void attemptLogin() {

        mMotsdepasse.setError(null);

        String password = mMotsdepasse.getText().toString();

        boolean cancel = false;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String saved_password = sharedPref.getString("password", "");
        if (!saved_password.equals(password)) {
            mMotsdepasse.setError(getString(R.string.error_incorrect_password));
            mMotsdepasse.requestFocus();
        } else {
            showProgress(true);
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        }


    }

    private boolean isPasswordValid(String motdepasse) {
        return motdepasse.length() > 4;
    }

    private void showProgress(final boolean show) {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
        }
        return super.onOptionsItemSelected(item);
    }
}

