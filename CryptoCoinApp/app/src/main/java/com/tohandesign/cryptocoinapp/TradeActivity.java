package com.tohandesign.cryptocoinapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navView);
        bottomNavigationView.setBackground(null);

    }
}