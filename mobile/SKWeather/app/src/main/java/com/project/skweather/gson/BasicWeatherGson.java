package com.project.skweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-06-05.
 */

public class BasicWeatherGson {

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
        @SerializedName("hourly")
        public List<Hourly> hourlyList = new ArrayList<>();
        public List<Hourly> getHourlyList() { return hourlyList; }

        @SerializedName("summary")
        public List<Summary> summaryList = new ArrayList<>();
        public List<Summary> getSummaryList(){
            return summaryList;
        }

        public class Hourly {

            //지역 데이터를 담을 클래스
            @SerializedName("grid") Grid grid;

            //날씨 상태, 날씨 아이콘 데이터를 담을 클래스
            @SerializedName("sky") Sky sky;

            //현재 온도와 최고, 최저 온도 데이터를 담을 클래스
            @SerializedName("temperature") Temperature temperature;

            //풍향, 풍속 데이터를 담을 클래스
            @SerializedName("wind") Wind wind;

            @SerializedName("timeRelease") String updateTime;
            public String getUpdateTime(){
                return updateTime;
            }

            @SerializedName("humidity") String humidity;
            public String getHumidity(){ return humidity; }

            public class Grid {
                @SerializedName("city") String city;
                @SerializedName("county") String country;
                @SerializedName("village") String village;

                public String getCity(){
                    return city;
                }
                public String getCountry(){
                    return country;
                }
                public String getVillage(){
                    return village;
                }
            }

            public class Sky {
                @SerializedName("name") String name;
                @SerializedName("code") String code;

                public String getName(){ return name; }
                public String getCode(){ return code; }
            }

            public class Temperature {
                @SerializedName("tc") String tc;
                @SerializedName("tmax") String tmax;
                @SerializedName("tmin") String tmin;

                public String getTc() { return tc; }
                public String getTmax(){ return tmax; }
                public String getTmin() { return tmin; }
            }

            public class Wind {
                @SerializedName("wdir") String wdir;
                @SerializedName("wspd") String wspd;

                public String getWdir() { return wdir; }
                public String getWspd() { return wspd; }
            }
            public Grid getGrid(){ return grid; }
            public Sky getSky() { return sky; }
            public Temperature getTemperature() { return temperature; }
            public Wind getWind() { return wind; }
        }

        public class Summary {
            @SerializedName("yesterday") Yesterday yesterday;
            @SerializedName("tomorrow")  Tomorrow tomorrow;

            @SerializedName("timeRelease") String updateTime;
            public String getUpdateTime(){
                return updateTime;
            }

            public class Yesterday {
                @SerializedName("sky") Sky sky;
                @SerializedName("temperature") Temperature temperature;

                public class Sky {
                    @SerializedName("code") String code;
                    @SerializedName("name") String name;

                    public String getCode(){
                        return code;
                    }
                    public String getName(){
                        return name;
                    }
                }
                public class Temperature {
                    @SerializedName("tmax") String tmax;
                    @SerializedName("tmin") String tmin;

                    public String getTmax(){
                        return tmax;
                    }
                    public String getTmin(){
                        return tmin;
                    }
                }
                public Sky getSky(){
                    return sky;
                }
                public Temperature getTemperature(){
                    return temperature;
                }

            }
            public class Tomorrow {
                @SerializedName("sky") Sky sky;
                @SerializedName("temperature") Temperature temperature;

                public class Sky {
                    @SerializedName("code") String code;
                    @SerializedName("name") String name;

                    public String getCode(){
                        return code;
                    }
                    public String getName(){
                        return name;
                    }
                }
                public class Temperature {
                    @SerializedName("tmax") String tmax;
                    @SerializedName("tmin") String tmin;

                    public String getTmax(){
                        return tmax;
                    }
                    public String getTmin(){
                        return tmin;
                    }
                }
                public Sky getSky(){
                    return sky;
                }
                public Temperature getTemperature(){
                    return temperature;
                }
            }
            public Yesterday getYesterday(){
                return yesterday;
            }
            public Tomorrow getTomorrow(){
                return tomorrow;
            }
        }
    }
    public Result getResult() {return result;}
    public Weather getWearher() {return wearher;}
}
