package com.project.skweather.service;

import com.project.skweather.gson.BasicWeatherGson;
import com.project.skweather.gson.DustWeatherGson;
import com.project.skweather.gson.WIndexWeatherGson;
import com.project.skweather.utils.WeatherValue;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018-06-05.
 */

public interface WeatherApiService {

    /**
     * @param first
     * @param second
     * @param version
     * @param lat
     * @param lon
     * @return
     *
     * 날씨 가져오기
     * 1. 현재 온도, 최고 온도, 최저 온도
     * 2. 어제 오늘 내일 최고 온도 및 최저 온도
     */
    @Headers({
            "CONNECT_TIMEOUT:5000",
            "READ_TIMEOUT:5000",
            "Accept:application/json",
            "appKey:"+ WeatherValue.WEATHER_PLANET_API_KEY
    })
    @GET("weather/{first}/{second}")
    Call<BasicWeatherGson> getWeatherPlanetBasicRetrofit(
            @Path("first") String first,
            @Path("second") String second,
            @Query("version") int version,
            @Query("lat") String lat,
            @Query("lon") String lon
    );

    /**
     *
     * @param pathUrl
     * @param version
     * @param lat
     * @param lon
     * @return
     * 사용자 요청에 따른 추가 날씨 정보 가져오기
     * 1. 자외선
     * 2. 꽃가루
     * 3. 체감온도
     */
    @Headers({
            "CONNECT_TIMEOUT:5000",
            "READ_TIMEOUT:5000",
            "Accept:application/json",
            "appKey:"+ WeatherValue.WEATHER_PLANET_API_KEY
    })
    @GET("weather/windex/{url}")
    Call<WIndexWeatherGson> getWeatherPlanetWIndexRetrofit(
            @Path("url") String pathUrl,
            @Query("version") int version, @Query("lat") String lat, @Query("lon") String lon

    );

    /**
     *
     * @param pathUrl
     * @param version
     * @param lat
     * @param lon
     * @return
     *
     * 미세먼지 Method
     */
    @Headers({
            "CONNECT_TIMEOUT:5000",
            "READ_TIMEOUT:5000",
            "Accept:application/json",
            "appKey:"+WeatherValue.WEATHER_PLANET_API_KEY
    })
    @GET("weather/{url}")
    Call<DustWeatherGson> getWeatherPlanetDustRetrofit(
            @Path("url") String pathUrl,
            @Query("version") int version, @Query("lat") String lat, @Query("lon") String lon

    );

}
