package com.project.skweather.model;

import android.util.Log;

/**
 * Created by Administrator on 2018-06-05.
 */

public class WindexWeather {

    private static WindexWeather instance;

    //자외선
    private String uvValue;
    private String uvComment;


    //체감 온도
    private String curFeelTemp;

    //꽃가루
    private String oakValue;
    private String oakGrade;

    private String pineValue;
    private String pineGrade;

    private String weedValue;
    private String weedGrade;


    private WindexWeather(){
        Log.d("WindexWeather", "WindexWeather Constructor Called!!!");
    }

    public static WindexWeather getInstance(){
        if(instance == null){
            instance = new WindexWeather();
        }
        Log.d("WindexWeather", "WindexWeather Singleton Called!!!");
        return instance;
    }


    public String getUvValue() {
        return uvValue;
    }

    public void setUvValue(String uvValue) {
        this.uvValue = uvValue;
    }

    public String getUvComment() {
        return uvComment;
    }

    public void setUvComment(String uvComment) {
        this.uvComment = uvComment;
    }

    public String getCurFeelTemp() {
        return curFeelTemp;
    }

    public void setCurFeelTemp(String curFeelTemp) {
        this.curFeelTemp = curFeelTemp;
    }

    public String getOakValue() {
        return oakValue;
    }

    public void setOakValue(String oakValue) {
        this.oakValue = oakValue;
    }

    public String getOakGrade() {
        return oakGrade;
    }

    public void setOakGrade(String oakGrade) {
        this.oakGrade = oakGrade;
    }

    public String getPineValue() {
        return pineValue;
    }

    public void setPineValue(String pineValue) {
        this.pineValue = pineValue;
    }

    public String getPineGrade() {
        return pineGrade;
    }

    public void setPineGrade(String pineGrade) {
        this.pineGrade = pineGrade;
    }

    public String getWeedValue() {
        return weedValue;
    }

    public void setWeedValue(String weedValue) {
        this.weedValue = weedValue;
    }

    public String getWeedGrade() {
        return weedGrade;
    }

    public void setWeedGrade(String weedGrade) {
        this.weedGrade = weedGrade;
    }
}

