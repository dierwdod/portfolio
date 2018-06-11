package com.project.skweather.retrofit;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.project.skweather.gson.WIndexWeatherGson;
import com.project.skweather.handler.WeatherHandler;
import com.project.skweather.model.WindexWeather;
import com.project.skweather.utils.WeatherValue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018-06-05.
 */

public class WindexWeatherRetrofit extends RetrofitService {


    private final static String TAG = "SKPlanetDetailRetrofit";

    private WIndexWeatherGson wIndexWeatherGson;

    private String detailUrl;

    public WindexWeatherRetrofit(WeatherHandler handler, Context mContext, double lat, double lon, String detailUrl){
        super(handler, mContext, lat, lon);
        this.detailUrl = detailUrl;
    }
    @Override
    public void run() {
        super.run();
        Call<WIndexWeatherGson> call = RetrofitSender.getApiService().getWeatherPlanetWIndexRetrofit(
                detailUrl, version, lat, lon
        );

        Log.d("URL", "URL : " + call.request().url());

        call.enqueue(new Callback<WIndexWeatherGson>() {
            @Override
            public void onResponse(Call<WIndexWeatherGson> call, Response<WIndexWeatherGson> response) {

                if(response.isSuccessful()){
                    wIndexWeatherGson = response.body();
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();

                    Log.d(TAG, TAG + "Response.raw : " + response.raw());
                    try {
                        if (wIndexWeatherGson.getResult().getCode().equals("9200")) { // 9200 = 성공을 의미
                            WindexWeather skPlanetWIndex = WindexWeather.getInstance();
                            switch (detailUrl) {
                                case WeatherValue.SK_PLANET_FEEL_URL:
                                    setFeelGson(skPlanetWIndex);
                                    bundle.putString("kind", "FEEL");
                                    break;
                                case WeatherValue.SK_PLANET_UV_URL:
                                    setUVGson(skPlanetWIndex);
                                    bundle.putString("kind", "UV");
                                    break;
                                case WeatherValue.SK_PLANET_POLLEN_URL:
                                    setPollenGson(skPlanetWIndex);
                                    bundle.putString("kind", "POLLEN");
                                    break;
                                default:
                                    break;
                            }
                            bundle.putInt("windex", WeatherValue.RESPONSE_SUCCESS);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        } else {
                            bundle.putInt("windex", WeatherValue.RESPONSE_FAIL);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                            Log.d(TAG, "요청 실패 : " + wIndexWeatherGson.getResult().getCode());
                            Log.d(TAG, "메시지 : " + wIndexWeatherGson.getResult().getMessage());
                        }
                    }catch (Exception e){
                        bundle.putInt("windex", WeatherValue.RESPONSE_STOP);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
                }
                else {
                    Log.d(TAG, "상태 : " + response.isSuccessful() + "");
                    Log.d(TAG, "코드 : " + response.code() + "");
                    Log.d(TAG, "내용 : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<WIndexWeatherGson> call, Throwable t) {
                Log.e(TAG,"날씨정보 불러오기 실패 : " + t.getMessage() );
                Log.e(TAG,"요청 메시지 : "+call.request());
            }
        });
    }

    private void setUVGson(WindexWeather windexWeather) {
        windexWeather.setUvValue(wIndexWeatherGson.getWearher().getwIndex().getUvIndexList().get(0).getDay00().getUvValue());
        windexWeather.setUvComment(wIndexWeatherGson.getWearher().getwIndex().getUvIndexList().get(0).getDay00().getUvComment());
    }

    private void setFeelGson(WindexWeather windexWeather){
        windexWeather.setCurFeelTemp(String.format("%.1f",Double.parseDouble(wIndexWeatherGson.getWearher().getwIndex().getWctIndexList().get(0).getCurrent().getFeelTemp())));
    }

    private void setPollenGson(WindexWeather windexWeather) {
        windexWeather.setOakValue(wIndexWeatherGson.getWearher().getwIndex().getPaIndexList().get(0).getDay00().getOak().getValue());
        windexWeather.setOakGrade(wIndexWeatherGson.getWearher().getwIndex().getPaIndexList().get(0).getDay00().getOak().getGrade());

        windexWeather.setPineValue(wIndexWeatherGson.getWearher().getwIndex().getPaIndexList().get(0).getDay00().getPine().getValue());
        windexWeather.setPineGrade(wIndexWeatherGson.getWearher().getwIndex().getPaIndexList().get(0).getDay00().getPine().getGrade());

        windexWeather.setWeedValue(wIndexWeatherGson.getWearher().getwIndex().getPaIndexList().get(0).getDay00().getWeed().getValue());
        windexWeather.setWeedGrade(wIndexWeatherGson.getWearher().getwIndex().getPaIndexList().get(0).getDay00().getWeed().getGrade());
    }

}