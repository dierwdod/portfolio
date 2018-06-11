package com.project.skweather.retrofit;

import com.project.skweather.service.WeatherApiService;
import com.project.skweather.utils.WeatherValue;

import retrofit2.GsonConverterFactory;

/**
 * Created by Administrator on 2018-06-05.
 */

public class RetrofitSender {

    private static retrofit2.Retrofit retrofit = null;
    private static WeatherApiService skService = null;


    public static WeatherApiService getApiService(){
        if(retrofit == null){
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(WeatherValue.SK_PLANET_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        skService = retrofit.create(WeatherApiService.class);
        return skService;
    }
}
