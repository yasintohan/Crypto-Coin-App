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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.squareup.picasso.Picasso;
import com.tohandesign.cryptocoinapp.Adapters.RecyclerAdapter;
import com.tohandesign.cryptocoinapp.CurrencyApi.CoinHistoryItem;
import com.tohandesign.cryptocoinapp.CurrencyApi.CryptoApi;
import com.tohandesign.cryptocoinapp.CurrencyApi.CryptoCoin;

import org.json.JSONException;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class DetailsActivity extends AppCompatActivity {

    private String COIN_ID;
    private CryptoCoin coin;

    private TextView nameView, subnameView, valueView, rateView, walletText, subnameView2;
    private ImageView image, image2;

    SwipeRefreshLayout swipeRefreshLayout;

    private int chartDay = 14;
    private String type = "prices";


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
        getChartData(chartDay,type);



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
                        Intent l = new Intent(getApplication(), SettingsActivity.class);
                        startActivity(l);
                        break;
                }

                return false;
            }
        });



    }


    private void getData(){


        try {
            CryptoApi cryptoApi = new CryptoApi();
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
            walletText.setText(coin.getName() + " Wallet");

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

    private void getChartData(int day, String type){
        try {
            CryptoApi cryptoApi = new CryptoApi();
            List<CoinHistoryItem> list = cryptoApi.getCoinHistory(COIN_ID, "usd", day, type);


            GraphView graph = (GraphView) findViewById(R.id.graph);


            // set date label formatter
            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
            graph.getGridLabelRenderer().setNumHorizontalLabels(2);

            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(getPoints(list));

            CoinHistoryItem minItem =  Collections.min(list, Comparator.comparing(s -> s.getCurrentPrice()));
            CoinHistoryItem maxItem =  Collections.max(list, Comparator.comparing(s -> s.getCurrentPrice()));

            // set manual X bounds
            graph.getViewport().setYAxisBoundsManual(true);

            graph.getViewport().setMinY(minItem.getCurrentPrice());
            graph.getViewport().setMaxY(maxItem.getCurrentPrice());

            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(list.get(0).getDate().getTime());
            graph.getViewport().setMaxX(list.get(list.size()-1).getDate().getTime());

            // enable scaling and scrolling
            graph.getViewport().setScalable(true);
            graph.getViewport().setScalableY(true);
            graph.removeAllSeries();
            graph.addSeries(series);


            series.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    Date date = new java.util.Date((long) dataPoint.getX());
                    String formattedDate = (String) android.text.format.DateFormat.format("dd-MM-yyyy HH:mm:ss", date);

                    Toast.makeText(getApplicationContext(), formattedDate + " - " + dataPoint.getY() + "$", Toast.LENGTH_SHORT).show();
                }
            });

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

    public void chartDaysClicked(View v) {

        switch (v.getId()){
            case R.id.onehrText:
                chartDay = 1;
                break;
            case R.id.threeDay:
                chartDay = 3;
                break;
            case R.id.oneWeek:
                chartDay = 7;
                break;
            case R.id.oneMonth:
                chartDay = 30;
                break;
            case R.id.maxText:
                chartDay = 999;
                break;
        }
        getChartData(chartDay,type);


    }

    private  DataPoint[] getPoints(List<CoinHistoryItem> list){
        DataPoint[] dpArray = new DataPoint[list.size()];
        for(int i = 0; i < list.size(); i++) {
            DataPoint dp = new DataPoint(list.get(i).getDate(), list.get(i).getCurrentPrice());


            dpArray[i] = dp;
        }
    return dpArray;
    }




    public void backClicked(View v) {
        super.onBackPressed();
    }




}