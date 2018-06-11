package com.project.skweather.handler;

import android.os.Message;

import com.project.skweather.utils.WeatherValue;

/**
 * Created by Administrator on 2018-06-05.
 */

public class WIndexWeatherHandler extends WeatherHandler {

    @Override
    public void handleMessage(Message msg){
        int what = msg.getData().getInt("windex");
        String kind = msg.getData().getString("kind");
        switch(what){
            case WeatherValue.RESPONSE_SUCCESS :
                if(kind.equals("FEEL")){
                    setFeelResponseResult(WeatherValue.CONNECT_SUCCESS);
                }
                else if(kind.equals("UV")){
                    setUvResponseResult(WeatherValue.CONNECT_SUCCESS);
                }
                else if(kind.equals("POLLEN")){
                    setPollenResponseResult(WeatherValue.CONNECT_SUCCESS);
                }
                break;

            case WeatherValue.RESPONSE_FAIL:
                if(kind.equals("FEEL"))
                    setFeelResponseResult(WeatherValue.CONNECT_FAIL);
                if(kind.equals("UV"))
                    setUvResponseResult(WeatherValue.CONNECT_FAIL);
                if (kind.equals("POLLEN"))
                    setPollenResponseResult(WeatherValue.CONNECT_FAIL);
                break;
            default:
                break;
        }
    }
}
