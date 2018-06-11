package com.project.skweather.utils;

/**
 * Created by Administrator on 2018-06-05.
 */

public interface WeatherValue {

    String WEATHER_PLANET_API_KEY = "INPUT WEATHERPLANET API KEY";

    String SK_PLANET_BASE_URL = "http://apis.skplanetx.com/";
    String SK_PLANET_TODAY_FIRST_URL = "current";
    String SK_PLANET_TODAY_SECOND_URL = "hourly";
    String SK_PLANET_FORECAST_FIRST_URL = "summary";
    String SK_PLANET_FORECAST_SECOND_URL = "";

    String SK_PLANET_DUST_URL = "dust";
    String SK_PLANET_UV_URL = "uvindex";
    String SK_PLANET_FEEL_URL = "wctindex";
    String SK_PLANET_POLLEN_URL = "paindex";

    String SETTING_DEFAULT_LAT_PREF = "LAST_LATITUDE";
    String SETTING_DEFAULT_LON_PREF = "LAST_LONGITUDE";
    String SETTING_DEFAULT_LAT = "37.566353";
    String SETTING_DEFAULT_LONG = "126.977953";
    String SETTING_DUST_PREF = "DUST";
    String SETTING_UV_PREF = "UV";
    String SETTING_FEEL_PREF = "FEEL";
    String SETTING_POLLEN_PREF = "POLLEN";

    int SK_PLANET_CONNECT_TIMEOUT = 5000;

    int RESPONSE_SUCCESS = 1;
    int RESPONSE_FAIL = 0;
    int RESPONSE_STOP = -1;


    int CONNECT_SUCCESS = 1;
    int CONNECTING = 3;
    int NONE_SETTING = 0;
    int CONNECT_FAIL = -1;

}