package com.project.skweather.retrofit;

import android.content.Context;

import com.project.skweather.handler.WeatherHandler;

/**
 * Created by Administrator on 2018-06-05.
 */

public abstract class RetrofitService extends Thread {

    protected WeatherHandler handler;
    protected Context context;
    protected int version = 1;
    protected String lat;
    protected String lon;

    protected RetrofitService(WeatherHandler handler, Context mContext, double lat, double lon){
        this.handler = handler;
        this.context = mContext;
        this.lat = String.valueOf(lat);
        this.lon = String.valueOf(lon);
    }

    @Override
    public void run(){
        super.run();
    }
}
