package com.project.skweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-06-05.
 */

public class DustWeatherGson {

    @SerializedName("result")
    Result result;
    @SerializedName("weather")
    Weather wearher;


    public class Result {
        @SerializedName("message") String message;
        @SerializedName("code") String code;

        public String getMessage() { return message; }
        public String getCode() { return code; }
    }

    public class Weather {

        @SerializedName("dust")
        List<Dust> dustList = new ArrayList<>();
        public List<Dust> getDustList(){
            return dustList;
        }


        public class Dust {
            @SerializedName("pm10") Pm10 pm10;

            public class Pm10 {
                @SerializedName("value") String dustValue;
                @SerializedName("grade") String dustGrade;
                public String getDustValue(){
                    return dustValue;
                }
                public String getDustGrade(){
                    return dustGrade;
                }
            }

            public Pm10 getPm10(){
                return pm10;
            }
        }
    }
    public Result getResult() {return result;}
    public Weather getWearher() {return wearher;}
}
