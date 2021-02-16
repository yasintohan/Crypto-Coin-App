package com.tohandesign.cryptocoinapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;




import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tohandesign.cryptocoinapp.Adapters.RecyclerAdapter;
import com.tohandesign.cryptocoinapp.CurrencyApi.CryptoApi;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navView);
        bottomNavigationView.setBackground(null);

        CryptoApi cryptoApi = new CryptoApi();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.mainRecView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL , false);
        recyclerView.setLayoutManager(llm);

        RecyclerAdapter adapter = null;
        try {
            adapter = new RecyclerAdapter(cryptoApi.getCoins("usd",3,1), "$");
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }


    public void ethereumClick(View v) {

        Intent i = new Intent(this, DetailsActivity.class);
        startActivity(i);
    }
}