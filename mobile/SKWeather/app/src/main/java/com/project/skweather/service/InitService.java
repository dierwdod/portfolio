package com.project.skweather.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018-06-05.
 */

public class InitService {

    private static InitService initService;

    private Map<String, Integer> todayWeatherSet = new HashMap<>();
    private Map<String, Integer> yesterdayWeatherSet = new HashMap<>();
    private Map<String, Integer> tomorrowWeatherSet = new HashMap<>();

    public static InitService getInstance(){
        if(initService == null){
            initService = new InitService();
        }
        return initService;
    }

    public void setTodayImage(String key, int value){
        todayWeatherSet.put(key, value);
    }
    public void setYesterdayImage(String key, int value){
        yesterdayWeatherSet.put(key, value);
    }
    public void setTomorrowImage(String key, int value){
        tomorrowWeatherSet.put(key, value);
    }

    public int getTodayImage(String key){
        return todayWeatherSet.get(key);
    }
    public int getYesterdayImage(String key){
        return yesterdayWeatherSet.get(key);
    }
    public int getTomorrowImage(String key){
        return tomorrowWeatherSet.get(key);
    }
}