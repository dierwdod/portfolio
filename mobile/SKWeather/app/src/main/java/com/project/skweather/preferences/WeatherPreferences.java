package com.project.skweather.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Set;

/**
 * Created by Administrator on 2018-06-05.
 */

public class WeatherPreferences {

    private static WeatherPreferences weatherPreferences = new WeatherPreferences();

    private static final String TAG = "WeatherPreferences";
    private final String WEATHER_PREF_NAME = "weatherSettings";
    private final String LOCATION_PREF_NAME= "locationPrefence";


    private static Context mContext;

    public WeatherPreferences(){
        Log.d(TAG, TAG + " Constructor Called");
    }

    public static WeatherPreferences getInstance(Context context){
        Log.d(TAG, TAG+" Singleton Called!!!");
        mContext = context;
        if(weatherPreferences == null){
            weatherPreferences = new WeatherPreferences();
        }
        return weatherPreferences;
    }

    public void setWeatherOption(String key, String setting){
        SharedPreferences pref = mContext.getSharedPreferences(WEATHER_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, setting);
        editor.commit();
    }

    public String getWeatherOption(String key){
        SharedPreferences pref = mContext.getSharedPreferences(WEATHER_PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(key,null);
    }

    public void setLocationPreference(String key, Set<String> loc){
        SharedPreferences pref = mContext.getSharedPreferences(LOCATION_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putStringSet(key, loc);
        editor.commit();
    }

    public Set<String> getLocationPreference(String key){
        SharedPreferences pref = mContext.getSharedPreferences(LOCATION_PREF_NAME, Context.MODE_PRIVATE);
        return pref.getStringSet(key, null);
    }
}

