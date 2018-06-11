package com.project.skweather.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.project.skweather.utils.WeatherValue;

import java.util.Map;

/**
 * Created by Administrator on 2018-06-05.
 */

public class SettingsPreferences {

    private static SettingsPreferences settingsPreferences = new SettingsPreferences();

    private static final String TAG = "SettingsPreferences";
    private final String SETTINGS_PREF_NAME = "Settings";

    private static Context mContext;

    public SettingsPreferences(){
        Log.d(TAG, TAG + " Constructor Called");
    }

    public static SettingsPreferences getInstance(Context context){
        Log.d(TAG, TAG+" Singleton Called!!!");
        mContext = context;
        if(settingsPreferences == null){
            settingsPreferences = new SettingsPreferences();
        }
        return settingsPreferences;
    }

    public void setLastLatPreferences(String key, String value){
        SharedPreferences pref = mContext.getSharedPreferences(SETTINGS_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public String getLastLatWeatherPreferences(String key){
        SharedPreferences pref = mContext.getSharedPreferences(SETTINGS_PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(key, WeatherValue.SETTING_DEFAULT_LAT);
    }
    public void setLastLongPreferences(String key, String value){
        SharedPreferences pref = mContext.getSharedPreferences(SETTINGS_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public String getLastLongWeatherPreferences(String key){
        SharedPreferences pref = mContext.getSharedPreferences(SETTINGS_PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(key, WeatherValue.SETTING_DEFAULT_LONG);
    }

    public void setSettingsPreference(String key, boolean option){
        SharedPreferences pref = mContext.getSharedPreferences(SETTINGS_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, option);
        editor.commit();
    }
    public boolean getSettingsPreference(String key){
        SharedPreferences pref = mContext.getSharedPreferences(SETTINGS_PREF_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }

    public int getOptionCount() {
        int optionCount = 0;
        SharedPreferences pref = mContext.getSharedPreferences(SETTINGS_PREF_NAME, Context.MODE_PRIVATE);
        try {
            Map<String, ?> countMap = pref.getAll();
            for (Map.Entry<String, ?> countEntry : countMap.entrySet()) {
                if (countEntry.getValue() instanceof Boolean && (Boolean)countEntry.getValue()){
                    optionCount++;
                }
            }
        }
        catch (Exception e){
            Log.e(TAG, TAG + "Option Count Exception");
            e.printStackTrace();
        }
        return optionCount;
    }
}
