package com.tohandesign.cryptocoinapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tohandesign.cryptocoinapp.Adapters.RecyclerAdapter;
import com.tohandesign.cryptocoinapp.Adapters.RecyclerViewClickInterface;
import com.tohandesign.cryptocoinapp.CurrencyApi.CryptoApi;
import com.tohandesign.cryptocoinapp.CurrencyApi.CryptoCoin;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    private List<CryptoCoin> coins;
    SwipeRefreshLayout swipeRefreshLayout;
    CryptoApi cryptoApi;

    RecyclerView recyclerView;
    LinearLayoutManager llm;
    RecyclerAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navView);
        bottomNavigationView.setBackground(null);



        recyclerView = (RecyclerView)findViewById(R.id.mainRecView);
        recyclerView.setHasFixedSize(true);
        llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL , false);
        recyclerView.setLayoutManager(llm);
        getData();


        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });



    }

    private void getData(){

        try {
            cryptoApi = new CryptoApi();
            coins = cryptoApi.getCoins("usd",3,1);
            adapter = new RecyclerAdapter(coins, "$", this);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("COIN_ID", coins.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(final int position) {
        Toast.makeText(this, coins.get(position).getName(), Toast.LENGTH_SHORT).show();
        //coins.remove(position);
    }



}