package com.tohandesign.cryptocoinapp.CurrencyApi;

import android.graphics.Color;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CryptoApi extends JsonTracker {

    private String API_KEY = "https://api.coingecko.com/api/v3";

    public CryptoApi() {

    }

    public List<CryptoCoin> getCoins(String currency, int count, int page) throws JSONException, ExecutionException, InterruptedException {
        String jsonStr = API_KEY+ "/coins/markets?vs_currency="+currency+"&order=market_cap_desc&per_page="+count+"&page="+page+"&sparkline=false";
        List<CryptoCoin> itemList = new ArrayList<CryptoCoin>();

        JSONArray jsonArray = new JSONArray(execute(jsonStr).get());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject o = jsonArray.getJSONObject(i);
            CryptoCoin coin = new CryptoCoin(o.getString("id"),o.getString("symbol"),o.getString("name"), o.getString("image"), o.getDouble("current_price"), o.getInt("market_cap"), o.getInt("market_cap_rank"), o.getDouble("total_volume"), o.getDouble("high_24h"),o.getDouble("low_24h"),o.getDouble("price_change_percentage_24h"));
            itemList.add(coin);
        }



     return itemList;
    }


    public CryptoCoin getCoin(String id, String currency) throws ExecutionException, InterruptedException, JSONException {
        String jsonStr = API_KEY+ "/coins/markets?vs_currency="+currency+"&ids="+id+"&order=market_cap_desc&per_page=100&page=1&sparkline=false";

        JSONArray jsonArray = new JSONArray(execute(jsonStr).get());
        JSONObject o = jsonArray.getJSONObject(0);
        CryptoCoin coin = new CryptoCoin(o.getString("id"),o.getString("symbol"),o.getString("name"), o.getString("image"), o.getDouble("current_price"), o.getInt("market_cap"), o.getInt("market_cap_rank"), o.getDouble("total_volume"), o.getDouble("high_24h"),o.getDouble("low_24h"),o.getDouble("price_change_percentage_24h"));

        return coin;
    }


    public List<CoinHistoryItem> getCoinHistory(String id, String currency, int day, String type) throws ExecutionException, InterruptedException, JSONException {
        String interval = day > 1 ? "daily" : "hourly";
        String dayStr = day < 100 ? day+"" : "max";
        String jsonStr = API_KEY+ "/coins/"+id+"/market_chart?vs_currency="+currency+"&days="+dayStr+"&interval="+interval;
        List<CoinHistoryItem> itemList = new ArrayList<CoinHistoryItem>();


        JSONObject jsonObj = new JSONObject(execute(jsonStr).get());
        JSONArray jsonArray = jsonObj.getJSONArray(type);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONArray o = jsonArray.getJSONArray(i);
            CoinHistoryItem item = new CoinHistoryItem(id,new java.util.Date(Long.parseLong(o.getString(0))));
            switch (type){
                case "prices":
                    item.setCurrentPrice(o.getDouble(1));
                    break;
                case "market_caps":
                    item.setMarketCap(o.getDouble(1));
                    break;
                case "total_volumes":
                    item.setTotalVolume(o.getDouble(1));
                    break;
            }

            itemList.add(item);
        }

        return itemList;
    }



}
