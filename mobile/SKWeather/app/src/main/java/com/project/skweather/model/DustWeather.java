package com.project.skweather.model;

import android.util.Log;

/**
 * Created by Administrator on 2018-06-05.
 */

public class DustWeather {

    private static DustWeather instance;

    //미세먼지
    private String dustValue;
    private String dustGrade;

    public DustWeather(){
        Log.d("DustWeather", "DustWeather Constructor Called!!!");

    }
    public static DustWeather getInstance(){
        if(instance == null){
            instance = new DustWeather();
        }
        return instance;
    }

    public String getDustValue() {
        return dustValue;
    }

    public void setDustValue(String dustValue) {
        this.dustValue = dustValue;
    }

    public String getDustGrade() {
        return dustGrade;
    }

    public void setDustGrade(String dustGrade) {
        this.dustGrade = dustGrade;
    }
}

