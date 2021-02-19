package com.tohandesign.cryptocoinapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tohandesign.cryptocoinapp.Adapters.RecyclerAdapter;
import com.tohandesign.cryptocoinapp.Adapters.RecyclerViewClickInterface;
import com.tohandesign.cryptocoinapp.CurrencyApi.CryptoApi;
import com.tohandesign.cryptocoinapp.CurrencyApi.CryptoCoin;

import org.json.JSONException;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    private List<CryptoCoin> coins;
    SwipeRefreshLayout swipeRefreshLayout;
    CryptoApi cryptoApi;

    RecyclerView recyclerView;
    LinearLayoutManager llm;
    RecyclerAdapter adapter = null;
    private int pageCount = 1;

    TextView previousBtn;
    TextView nextBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setSelectedItemId(R.id.main_prices);

        previousBtn = (TextView) findViewById(R.id.backPageBtn);
        nextBtn = (TextView) findViewById(R.id.nextPageBtn);

        recyclerView = (RecyclerView)findViewById(R.id.mainRecView);
        recyclerView.setHasFixedSize(true);
        llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL , false);
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
                    case R.id.main_settings:
                        Intent l = new Intent(getApplication(), SettingsActivity.class);
                        startActivity(l);
                        break;
                }

                return false;
            }
        });


    }

    private void getData(){

        if(pageCount == 1) { previousBtn.setVisibility(View.INVISIBLE); } else { previousBtn.setVisibility(View.VISIBLE); }

        try {
            cryptoApi = new CryptoApi();
            coins = cryptoApi.getCoins("usd",10,pageCount);
            adapter = new RecyclerAdapter(coins, "$", this, R.layout.list_card);
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

    public void pageClick(View v){
        switch(v.getId()){
            case R.id.backPageBtn:
                pageCount--;
                break;
            case R.id.nextPageBtn:
                pageCount++;
                break;
        }
        getData();
        adapter.notifyDataSetChanged();
    }

    public void backClicked(View v) {
        super.onBackPressed();
    }

}