package com.project.skweather.model;

import android.util.Log;

/**
 * Created by Administrator on 2018-06-05.
 */

public class BasicWeather {
    private static BasicWeather instance;

    //오늘 예보보
    private double curTemperature;
    private double curMaxTemp;
    private double curMinTemp;
    private String curIconCode;
    private String curCloudName;
    private String curWindDirection;
    private String curWindSpeed;

    private String city;
    private String country;
    private String viliage;

    private String humidity;

    private String todayTime;

    //어제 예보
    private String yestName;
    private String yestCode;
    private double yestTmax;
    private double yestTmin;

    //내일 예보
    private String tommorrowName;
    private String tommorrowCode;
    private double tommorrowTmax;
    private double tommorrowTmin;

    private String forecastTime;

    private BasicWeather(){
        Log.d("BasicWeather Model", "BasicWeather Constructor Created");
    }

    public static BasicWeather getInstance(){
        if(instance == null){
            instance = new BasicWeather();
        }
        Log.d("BasicWeather Model", "BasicWeather instance Created");
        return instance;
    }

    /**
     * 현재 날씨 가져오기
     * @return
     */

    public double getCurMaxTemp() {
        return curMaxTemp;
    }

    public void setCurMaxTemp(double curMaxTemp) {
        this.curMaxTemp = curMaxTemp;
    }

    public double getCurMinTemp() {
        return curMinTemp;
    }

    public void setCurMinTemp(double curMinTemp) {
        this.curMinTemp = curMinTemp;
    }

    public double getCurTemperature() {
        return curTemperature;
    }

    public void setCurTemperature(double curTemperature) {
        this.curTemperature = curTemperature;
    }

    public String getCurCloudName() {
        return curCloudName;
    }

    public void setCurCloudName(String curCloudName) {
        this.curCloudName = curCloudName;
    }

    public String getCurWindDirection() {
        return curWindDirection;
    }

    public void setCurWindDirection(String curWindDirection) {
        this.curWindDirection = curWindDirection;
    }

    public String getCurWindSpeed() {
        return curWindSpeed;
    }

    public void setCurWindSpeed(String curWindSpeed) {
        this.curWindSpeed = curWindSpeed;
    }

    public String getCurIconCode() {
        return curIconCode;
    }

    public void setCurIconCode(String curIconCode) {
        this.curIconCode = curIconCode;
    }

    public String getTodayTime() {
        return todayTime;
    }

    public void setTodayTime(String todayTime) {
        this.todayTime = todayTime;
    }

    /**
     * 어제 내일 날씨 가져오기
     */
    public String getYestName() {
        return yestName;
    }

    public void setYestName(String yestName) {
        this.yestName = yestName;
    }

    public String getYestCode() {
        return yestCode;
    }

    public void setYestCode(String yestCode) {
        this.yestCode = yestCode;
    }

    public double getYestTmax() {
        return yestTmax;
    }

    public void setYestTmax(double yestTmax) {
        this.yestTmax = yestTmax;
    }

    public double getYestTmin() {
        return yestTmin;
    }

    public void setYestTmin(double yestTmin) {
        this.yestTmin = yestTmin;
    }

    public String getTommorrowName() {
        return tommorrowName;
    }

    public void setTommorrowName(String tommorrowName) {
        this.tommorrowName = tommorrowName;
    }

    public String getTommorrowCode() {
        return tommorrowCode;
    }

    public void setTommorrowCode(String tommorrowCode) {
        this.tommorrowCode = tommorrowCode;
    }

    public double getTommorrowTmax() {
        return tommorrowTmax;
    }

    public void setTommorrowTmax(double tommorrowTmax) {
        this.tommorrowTmax = tommorrowTmax;
    }

    public double getTommorrowTmin() {
        return tommorrowTmin;
    }

    public void setTommorrowTmin(double tommorrowTmin) {
        this.tommorrowTmin = tommorrowTmin;
    }

    public String getForecastTime() {
        return forecastTime;
    }

    public void setForecastTime(String forecastTime) {
        this.forecastTime = forecastTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getViliage() {
        return viliage;
    }

    public void setViliage(String viliage) {
        this.viliage = viliage;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
