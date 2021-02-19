package com.tohandesign.cryptocoinapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;
import com.tohandesign.cryptocoinapp.Adapters.RecyclerAdapter;
import com.tohandesign.cryptocoinapp.CurrencyApi.CryptoApi;
import com.tohandesign.cryptocoinapp.CurrencyApi.CryptoCoin;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class DetailsActivity extends AppCompatActivity {

    private String COIN_ID;
    private CryptoCoin coin;

    private TextView nameView, subnameView, valueView, rateView, walletText, subnameView2;
    private ImageView image, image2;

    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navView);
        bottomNavigationView.setBackground(null);
        COIN_ID = getIntent().getStringExtra("COIN_ID");
        nameView = (TextView)findViewById(R.id.cryptoTitle);
        subnameView = (TextView)findViewById(R.id.cryptoShort);
        valueView = (TextView)findViewById(R.id.cryptoValue);
        rateView = (TextView)findViewById(R.id.cryptoRate);
        image = (ImageView)findViewById(R.id.cryptoIcon);

        image2 = (ImageView)findViewById(R.id.cryptoIcon2);
        walletText = (TextView)findViewById(R.id.cryptoTitle2);
        subnameView2 = (TextView)findViewById(R.id.cryptoShort2);

        getData();

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
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
                    case R.id.main_prices:
                        Intent k = new Intent(getApplication(), ListActivity.class);
                        startActivity(k);
                        break;
                    case R.id.main_settings:
                        //Intent l = new Intent(getApplication(), ListActivity.class);
                        //startActivity(l);
                        break;
                }

                return false;
            }
        });



    }


    public void getData(){
        CryptoApi cryptoApi = new CryptoApi();

        try {
            coin = cryptoApi.getCoin(COIN_ID,"usd");
            nameView.setText(coin.getName());
            subnameView.setText(coin.getSymbol());
            subnameView2.setText(coin.getSymbol());
            valueView.setText(coin.getCurrentPrice()+"$");

            Double rate = coin.getChange_percentage();
            rateView.setTextColor((rate < 0) ? Color.parseColor("#8F0000") : Color.parseColor("#0D8E53"));
            rateView.setText(rate+"%");

            Picasso.get().load(coin.getImageUrl()).into(image);
            Picasso.get().load(coin.getImageUrl()).into(image2);
            walletText.setText(coin.getName() + "Wallet");

        } catch (JSONException e) {
            Log.i("JsonLog", e.toString());
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.i("JsonLog", e.toString());
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.i("JsonLog", e.toString());
            e.printStackTrace();
        }


    }

    public void backClicked(View v) {
        super.onBackPressed();
    }


}