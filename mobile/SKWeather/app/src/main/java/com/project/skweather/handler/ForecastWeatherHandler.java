package com.project.skweather.handler;

import android.os.Message;
import android.util.Log;

import com.project.skweather.utils.WeatherValue;

/**
 * Created by Administrator on 2018-06-05.
 */

public class ForecastWeatherHandler extends WeatherHandler {

    @Override
    public void handleMessage(Message msg){
        int what = msg.getData().getInt("forecast");
        Log.d("what", "forecast what : " + what);

        switch(what){
            case WeatherValue.RESPONSE_SUCCESS :
                setResponseResult(WeatherValue.CONNECT_SUCCESS);
                break;
            case WeatherValue.RESPONSE_FAIL:
                setResponseResult(WeatherValue.CONNECT_FAIL);
                break;
            default:
                break;
        }
    }
}
