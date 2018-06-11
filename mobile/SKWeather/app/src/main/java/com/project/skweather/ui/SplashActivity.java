package com.project.skweather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.project.skweather.R;
import com.project.skweather.service.InitService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018-06-05.
 */

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    private String[] todayIconCode;
    private String[] yesterdayIconCode;
    private String[] tomorrowIconCode;

    private boolean morningCheck = false;

    private final int[] todayIconImage = {
            R.drawable.weather_38, R.drawable.weather_01, R.drawable.weather_02,
            R.drawable.weather_03, R.drawable.weather_12, R.drawable.weather_13,
            R.drawable.weather_14, R.drawable.weather_18, R.drawable.weather_21,
            R.drawable.weather_32, R.drawable.weather_04, R.drawable.weather_29,
            R.drawable.weather_26, R.drawable.weather_27, R.drawable.weather_28
    };
    private final int[] forecastIconImage = {
            R.drawable.weather_38, R.drawable.weather_01, R.drawable.weather_08,
            R.drawable.weather_02, R.drawable.weather_09, R.drawable.weather_03,
            R.drawable.weather_10, R.drawable.weather_18, R.drawable.weather_21,
            R.drawable.weather_32, R.drawable.weather_04
    };

    private final int[] afternoonIconImage = {
            R.drawable.weather_08, R.drawable.weather_09, R.drawable.weather_10,
            R.drawable.weather_40, R.drawable.weather_41, R.drawable.weather_42
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        todayIconCode = getResources().getStringArray(R.array.current_icon_code);
        yesterdayIconCode = getResources().getStringArray(R.array.yesterday_icon_code);
        tomorrowIconCode = getResources().getStringArray(R.array.tomorrow_icon_code);

        Log.d("SplashActivity", "SplashAvtivity onCreate Called!!!");
        morningCheck = checkMorning();
        setImageCodeAndResource();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    public boolean checkMorning(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("HH");
        int nowTime = Integer.parseInt(sdfNow.format(date));
        if(8 <= nowTime && nowTime <= 18){
            return true;
        }
        else {
            return false;
        }
    }

    private void setImageCodeAndResource(){
        InitService initService = InitService.getInstance();

        for(int i = 0 ; i < todayIconCode.length; i++){
            if(!morningCheck && (1 <= i && i <= 6)){
                initService.setTodayImage(todayIconCode[i], afternoonIconImage[i-1]);
                continue;
            }
            initService.setTodayImage(todayIconCode[i], todayIconImage[i]);
        }

        for(int i = 0 ; i < yesterdayIconCode.length; i++) {
            if(!morningCheck && (1 <= i && i <= 3)){
                initService.setYesterdayImage(yesterdayIconCode[i], afternoonIconImage[i-1]);
                initService.setTomorrowImage(tomorrowIconCode[i], afternoonIconImage[i-1]);
                continue;
            }
            initService.setYesterdayImage(yesterdayIconCode[i], todayIconImage[i]);
            initService.setTomorrowImage(tomorrowIconCode[i], forecastIconImage[i]);
        }
    }
}

