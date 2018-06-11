package com.project.skweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-06-05.
 */

public class WIndexWeatherGson {

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
        @SerializedName("wIndex")
        WIndex wIndex;

        public class WIndex {
            @SerializedName("uvindex")
            List<UVIndex> uvIndexList = new ArrayList<>();
            public List<UVIndex> getUvIndexList(){
                return uvIndexList;
            }

            @SerializedName("paindex")
            List<PaIndex> paIndexList = new ArrayList<>();
            public List<PaIndex> getPaIndexList(){
                return paIndexList;
            }

            @SerializedName("wctIndex")
            List<WctIndex> wctIndexList = new ArrayList<>();
            public List<WctIndex> getWctIndexList(){
                return wctIndexList;
            }

            public class UVIndex {
                @SerializedName("day00") Day00 day00;

                public class Day00 {
                    @SerializedName("comment") String uvComment;
                    @SerializedName("index") String uvValue;
                    public String getUvComment(){
                        return uvComment;
                    }
                    public String getUvValue(){
                        return uvValue;
                    }
                }
                public Day00 getDay00(){
                    return day00;
                }
            }

            public class WctIndex {
                @SerializedName("current") Current current;

                public class Current {
                    @SerializedName("timeRelease") String feelUpdateTime;
                    @SerializedName("index") String feelTemp;

                    public String getFeelUpdateTime(){
                        return feelUpdateTime;
                    }
                    public String getFeelTemp(){
                        return feelTemp;
                    }
                }
                public Current getCurrent(){
                    return current;
                }
            }

            public class PaIndex{
                @SerializedName("day00") Day00 day00;

                public class Day00 {
                    @SerializedName("oak") Oak oak;
                    @SerializedName("pine") Pine pine;
                    @SerializedName("weed") Weed weed;

                    public class Oak {
                        @SerializedName("value") String value;
                        @SerializedName("grade") String grade;
                        public String getValue() { return value; }
                        public String getGrade() { return grade; }
                    }
                    public class Pine {
                        @SerializedName("value") String value;
                        @SerializedName("grade") String grade;
                        public String getValue() { return value; }
                        public String getGrade() { return grade; }
                    }
                    public class Weed {
                        @SerializedName("value") String value;
                        @SerializedName("grade") String grade;
                        public String getValue() { return value; }
                        public String getGrade() { return grade; }
                    }
                    public Oak getOak(){ return oak; }
                    public Pine getPine(){ return pine; }
                    public Weed getWeed(){ return weed; }
                }
                public Day00 getDay00(){
                    return day00;
                }
            }
        }

        public WIndex getwIndex(){
            return wIndex;
        }
    }
    public Result getResult() {return result;}
    public Weather getWearher() {return wearher;}
}
