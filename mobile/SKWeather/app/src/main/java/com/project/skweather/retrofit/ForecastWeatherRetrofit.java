package com.project.skweather.retrofit;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.project.skweather.gson.BasicWeatherGson;
import com.project.skweather.handler.WeatherHandler;
import com.project.skweather.model.BasicWeather;
import com.project.skweather.utils.WeatherValue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018-06-05.
 */

public class ForecastWeatherRetrofit extends RetrofitService{
    private final static String TAG = "SKPlanetForecast";


    private BasicWeatherGson basicWeatherGson;

    public ForecastWeatherRetrofit(WeatherHandler handler, Context mContext, double lat, double lon){
        super(handler, mContext, lat, lon);
    }

    @Override
    public void run() {
        super.run();
        Call<BasicWeatherGson> call = RetrofitSender.getApiService().getWeatherPlanetBasicRetrofit(
                WeatherValue.SK_PLANET_FORECAST_FIRST_URL,
                WeatherValue.SK_PLANET_FORECAST_SECOND_URL,
                version, lat, lon
        );

        Log.d("URL", "URL : " + call.request().url());

        call.enqueue(new Callback<BasicWeatherGson>() {
            @Override
            public void onResponse(Call<BasicWeatherGson> call, Response<BasicWeatherGson> response) {
                Log.d("Forecast", "Forecast : " + response.isSuccessful());
                if (response.isSuccessful()) {
                    basicWeatherGson = response.body();
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();

                    Log.d(TAG, TAG + " Response.raw : " + response.raw());
                    try {
                        if (basicWeatherGson.getResult().getCode().equals("9200")) { // 9200 = 성공을 의미
                            BasicWeather.getInstance().setYestCode(basicWeatherGson.getWearher().getSummaryList().get(0).getYesterday().getSky().getCode());
                            BasicWeather.getInstance().setYestName(basicWeatherGson.getWearher().getSummaryList().get(0).getYesterday().getSky().getName());
                            BasicWeather.getInstance().setYestTmax(Double.parseDouble(String.format("%.1f", Double.parseDouble(basicWeatherGson.getWearher().getSummaryList().get(0).getYesterday().getTemperature().getTmax()))));
                            BasicWeather.getInstance().setYestTmin(Double.parseDouble(String.format("%.1f", Double.parseDouble(basicWeatherGson.getWearher().getSummaryList().get(0).getYesterday().getTemperature().getTmin()))));

                            BasicWeather.getInstance().setTommorrowCode(basicWeatherGson.getWearher().getSummaryList().get(0).getTomorrow().getSky().getCode());
                            BasicWeather.getInstance().setTommorrowName(basicWeatherGson.getWearher().getSummaryList().get(0).getTomorrow().getSky().getName());
                            BasicWeather.getInstance().setTommorrowTmax(Double.parseDouble(String.format("%.1f", Double.parseDouble(basicWeatherGson.getWearher().getSummaryList().get(0).getTomorrow().getTemperature().getTmax()))));
                            BasicWeather.getInstance().setTommorrowTmin(Double.parseDouble(String.format("%.1f", Double.parseDouble(basicWeatherGson.getWearher().getSummaryList().get(0).getTomorrow().getTemperature().getTmin()))));
                            BasicWeather.getInstance().setForecastTime(basicWeatherGson.getWearher().getSummaryList().get(0).getUpdateTime());

                            bundle.putInt("forecast", WeatherValue.RESPONSE_SUCCESS);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        } else {
                            bundle.putInt("forecast", WeatherValue.RESPONSE_FAIL);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                            Log.d(TAG, "요청 실패 : " + basicWeatherGson.getResult().getCode());
                            Log.d(TAG, "메시지 : " + basicWeatherGson.getResult().getMessage());
                        }
                    }catch (ArrayIndexOutOfBoundsException e){
                        bundle.putInt("forecast", WeatherValue.RESPONSE_FAIL);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
                }
                else {
                    Log.d(TAG, "상태 : " + response.isSuccessful() + "");
                    Log.d(TAG, "코드 : " + response.code() + "");
                }

            }

            @Override
            public void onFailure(Call<BasicWeatherGson> call, Throwable t) {
                Log.e(TAG, "날씨정보 불러오기 실패 : " + t.getMessage());
                Log.e(TAG, "요청 메시지 : " + call.request());
            }
        });
    }
}
