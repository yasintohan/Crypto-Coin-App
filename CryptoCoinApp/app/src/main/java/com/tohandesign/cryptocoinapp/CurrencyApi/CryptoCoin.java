package com.tohandesign.cryptocoinapp.CurrencyApi;

public class CryptoCoin {

    private String id;
    private String symbol;
    private String name;
    private String imageUrl;
    private double currentPrice;
    private int marketCap;
    private int marketCapRank;
    private double totalVolume;
    private double high24h;
    private double low24h;
    private double change_percentage;


    public CryptoCoin(String id, String symbol, String name, String imageUrl, double currentPrice, int marketCap, int marketCapRank, double totalVolume, double high24h, double low24h, double change_percentage) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.imageUrl = imageUrl;
        this.currentPrice = currentPrice;
        this.marketCap = marketCap;
        this.marketCapRank = marketCapRank;
        this.totalVolume = totalVolume;
        this.high24h = high24h;
        this.low24h = low24h;
        this.change_percentage = change_percentage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(int marketCap) {
        this.marketCap = marketCap;
    }

    public int getMarketCapRank() {
        return marketCapRank;
    }

    public void setMarketCapRank(int marketCapRank) {
        this.marketCapRank = marketCapRank;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public double getHigh24h() {
        return high24h;
    }

    public void setHigh24h(double high24h) {
        this.high24h = high24h;
    }

    public double getLow24h() {
        return low24h;
    }

    public void setLow24h(double low24h) {
        this.low24h = low24h;
    }

    public double getChange_percentage() {
        return change_percentage;
    }

    public void setChange_percentage(double change_percentage) {
        this.change_percentage = change_percentage;
    }
}
