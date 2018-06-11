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

public class BasicWeatherRetrofit extends RetrofitService {

    private final static String TAG = "SKPlanetRetrofit";

    private BasicWeatherGson basicWeatherGson;

    public BasicWeatherRetrofit(WeatherHandler handler, Context mContext, double lat, double lon){
        super(handler,mContext,lat,lon);
    }

    @Override
    public void run(){
        super.run();
        Call<BasicWeatherGson> call =  RetrofitSender.getApiService().getWeatherPlanetBasicRetrofit(
                WeatherValue.SK_PLANET_TODAY_FIRST_URL,
                WeatherValue.SK_PLANET_TODAY_SECOND_URL,
                version, lat, lon
        );

        Log.d("ResponseURL", "ResponseURL : " + call.request().url());

        call.enqueue(new Callback<BasicWeatherGson>() {
            @Override
            public void onResponse(Call<BasicWeatherGson> call, Response<BasicWeatherGson> response) {

                if(response.isSuccessful()){
                    basicWeatherGson = response.body();
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    Log.d(TAG, TAG + "Response.raw : " + response.raw());
                    try {
                        // Json 데이터 Result의 Code 값 = 9200 (응답 성공)
                        if(basicWeatherGson.getResult().getCode().equals("9200")) {
                            BasicWeather.getInstance().setCity(basicWeatherGson.getWearher().getHourlyList().get(0).getGrid().getCity());
                            BasicWeather.getInstance().setCountry(basicWeatherGson.getWearher().getHourlyList().get(0).getGrid().getCountry());
                            BasicWeather.getInstance().setViliage(basicWeatherGson.getWearher().getHourlyList().get(0).getGrid().getVillage());
                            BasicWeather.getInstance().setHumidity(basicWeatherGson.getWearher().getHourlyList().get(0).getHumidity());
                            BasicWeather.getInstance().setCurTemperature(Double.parseDouble(String.format("%.1f",Double.parseDouble(
                                    basicWeatherGson.getWearher().getHourlyList().get(0).getTemperature().getTc()))));
                            BasicWeather.getInstance().setCurMaxTemp(Double.parseDouble(String.format("%.1f",Double.parseDouble(
                                    basicWeatherGson.getWearher().getHourlyList().get(0).getTemperature().getTmax()))));
                            BasicWeather.getInstance().setCurMinTemp(Double.parseDouble(String.format("%.1f",Double.parseDouble(
                                    basicWeatherGson.getWearher().getHourlyList().get(0).getTemperature().getTmin()))));
                            BasicWeather.getInstance().setCurCloudName(basicWeatherGson.getWearher().getHourlyList().get(0).getSky().getName());
                            BasicWeather.getInstance().setCurWindDirection(basicWeatherGson.getWearher().getHourlyList().get(0).getWind().getWdir());
                            BasicWeather.getInstance().setCurWindSpeed((String.format("%.1f",Double.parseDouble(
                                    basicWeatherGson.getWearher().getHourlyList().get(0).getWind().getWspd()))));
                            BasicWeather.getInstance().setCurIconCode(basicWeatherGson.getWearher().getHourlyList().get(0).getSky().getCode());
                            BasicWeather.getInstance().setTodayTime(basicWeatherGson.getWearher().getHourlyList().get(0).getUpdateTime());

                            /*
                             * Bundle 객체에 current를 Key로 응답 결과를 value로 저장.
                             * Handler에서 응답 결과를 받기위해 Message 객체에 Bundle을 저장.
                             */
                            bundle.putInt("current", WeatherValue.RESPONSE_SUCCESS);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                            Log.d(TAG, "Response Success : " + basicWeatherGson.getResult().getCode());
                            Log.d(TAG, "Response Message : " + basicWeatherGson.getResult().getMessage());
                        }
                        else {
                            bundle.putInt("current", WeatherValue.RESPONSE_FAIL);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                            Log.e(TAG, "Response Failed : " + basicWeatherGson.getResult().getCode());
                            Log.e(TAG, "Response Message : " + basicWeatherGson.getResult().getMessage());
                        }
                    }catch(Exception array_exp){
                        Log.e("Exception Called!!!", "Exception call...");
                        bundle.putInt("current", WeatherValue.RESPONSE_STOP);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
                }
                else {
                    Log.d(TAG, "Response Status : " + response.isSuccessful() + "");
                    Log.d(TAG, "Response Code : " + response.code() + "");
                    Log.d(TAG, "Response Message : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<BasicWeatherGson> call, Throwable t) {
                Log.e(TAG,"Weather Response Failed : " + t.getMessage() );
                Log.e(TAG,"Weather Reponse Message : "+call.request());
            }
        });
    }
}
