package com.project.skweather.handler;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Administrator on 2018-06-05.
 */

public abstract class WeatherHandler extends Handler {

    private int responseResult;
    private int feelResponseResult;
    private int uvResponseResult;
    private int pollenResponseResult;

    @Override
    public abstract void handleMessage(Message msg);

    public void setResponseResult(int responseResult){
        this.responseResult = responseResult;
    }
    public int getResponseResult(){
        return this.responseResult;
    }

    public int getFeelResponseResult() {
        return feelResponseResult;
    }

    public void setFeelResponseResult(int feelResponseResult) {
        this.feelResponseResult = feelResponseResult;
    }

    public int getUvResponseResult() {
        return uvResponseResult;
    }

    public void setUvResponseResult(int uvResponseResult) {
        this.uvResponseResult = uvResponseResult;
    }

    public int getPollenResponseResult() {
        return pollenResponseResult;
    }

    public void setPollenResponseResult(int pollenResponseResult) {
        this.pollenResponseResult = pollenResponseResult;
    }
}

