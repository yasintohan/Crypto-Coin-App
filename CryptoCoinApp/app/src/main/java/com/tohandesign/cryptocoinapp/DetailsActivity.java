package com.tohandesign.cryptocoinapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navView);
        bottomNavigationView.setBackground(null);

    }

    public void ethereumClick(View v) {

        Intent i = new Intent(this, TradeActivity.class);
        startActivity(i);
    }

}