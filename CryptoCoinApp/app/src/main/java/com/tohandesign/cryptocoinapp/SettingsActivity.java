package com.tohandesign.cryptocoinapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setSelectedItemId(R.id.main_settings);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.main_home:
                        Intent i = new Intent(getApplication(), MainActivity.class);
                        startActivity(i);
                        break;
                    case R.id.main_portfolio:
                        //Intent j = new Intent(getApplication(), ListActivity.class);
                        //startActivity(j);
                        break;
                    case R.id.main_prices:
                        Intent k = new Intent(getApplication(), ListActivity.class);
                        startActivity(k);
                        break;
                }

                return false;
            }
        });

    }

    public void backClicked(View v) {
        super.onBackPressed();
    }
}