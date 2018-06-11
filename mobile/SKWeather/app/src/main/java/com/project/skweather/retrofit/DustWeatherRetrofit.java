package com.project.skweather.retrofit;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.project.skweather.gson.DustWeatherGson;
import com.project.skweather.handler.WeatherHandler;
import com.project.skweather.model.DustWeather;
import com.project.skweather.utils.WeatherValue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018-06-05.
 */

public class DustWeatherRetrofit extends RetrofitService {

    private final static String TAG = "SKPlanetDustRetrofit";


    private DustWeatherGson dustWeatherGson;

    public DustWeatherRetrofit(WeatherHandler handler, Context mContext, double lat, double lon){
        super(handler, mContext, lat, lon);
    }

    @Override
    public void run(){
        super.run();
        Call<DustWeatherGson> call = RetrofitSender.getApiService().getWeatherPlanetDustRetrofit(
                WeatherValue.SK_PLANET_DUST_URL,
                version, lat, lon
        );
        Log.d("URL", "URL : " + call.request().url());

        call.enqueue(new Callback<DustWeatherGson>() {
            @Override
            public void onResponse(Call<DustWeatherGson> call, Response<DustWeatherGson> response) {

                if(response.isSuccessful()){
                    dustWeatherGson = response.body();
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();

                    Log.d(TAG, "Response.raw : " + response.raw());
                    try {
                        if (dustWeatherGson.getResult().getCode().equals("9200")) { // 9200 = 성공을 의미

                            DustWeather.getInstance().setDustValue(dustWeatherGson.getWearher().getDustList().get(0).getPm10().getDustValue());
                            DustWeather.getInstance().setDustGrade(dustWeatherGson.getWearher().getDustList().get(0).getPm10().getDustGrade());


                            bundle.putInt("dust", WeatherValue.RESPONSE_SUCCESS);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        } else {
                            bundle.putInt("dust", WeatherValue.RESPONSE_FAIL);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                            Log.d(TAG, "요청 실패 : " + dustWeatherGson.getResult().getCode());
                            Log.d(TAG, "메시지 : " + dustWeatherGson.getResult().getMessage());
                        }
                    }catch (Exception e){
                        bundle.putInt("dust", WeatherValue.RESPONSE_STOP);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
                }
                else {
                    Log.d(TAG, "상태 : " + response.isSuccessful() + "");
                    Log.d(TAG, "코드 : " + response.code() + "");
                    Log.d(TAG, "내용 : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<DustWeatherGson> call, Throwable t) {
                Log.e(TAG,"날씨정보 불러오기 실패 : " + t.getMessage() );
                Log.e(TAG,"요청 메시지 : "+call.request());
            }
        });
    }
}
