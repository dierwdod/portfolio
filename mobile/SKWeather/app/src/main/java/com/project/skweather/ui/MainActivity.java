package com.project.skweather.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.skweather.R;
import com.project.skweather.handler.CurrentWeatherHandler;
import com.project.skweather.handler.DustWeatherHandler;
import com.project.skweather.handler.ForecastWeatherHandler;
import com.project.skweather.handler.WIndexWeatherHandler;
import com.project.skweather.handler.WeatherHandler;
import com.project.skweather.model.BasicWeather;
import com.project.skweather.model.DustWeather;
import com.project.skweather.model.WindexWeather;
import com.project.skweather.preferences.SettingsPreferences;
import com.project.skweather.retrofit.BasicWeatherRetrofit;
import com.project.skweather.retrofit.DustWeatherRetrofit;
import com.project.skweather.retrofit.ForecastWeatherRetrofit;
import com.project.skweather.retrofit.RetrofitService;
import com.project.skweather.retrofit.WindexWeatherRetrofit;
import com.project.skweather.service.GPSService;
import com.project.skweather.service.InitService;
import com.project.skweather.utils.WeatherValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private final int OPTION_REQUEST_CODE = 1;

    public static final int RENEW_GPS = 1;
    public static final int SEND_PRINT = 2;

    private TextView temperature_tv, max_temperature_tv, min_temperature_tv;
    private TextView weather_wind_dir_tv, weather_wind_speed_tv, cur_update_time_tv, weather_icon_name_tv;
    private TextView location_name_tv, weather_humidity_tv;
    private ImageView weather_icon_imgv;

    private TextView yest_max_temp_tv, yest_min_temp_tv;
    private TextView today_max_temp_tv, today_min_temp_tv;
    private TextView tom_max_temp_tv, tom_min_temp_tv;
    private ImageView yest_icon_imgv, today_icon_imgv, tom_icon_imgv;
    private TextView forecast_update_time_tv;

    private WeatherHandler curWeatherHandler;
    private WeatherHandler forecastWeatherHandler;
    private WeatherHandler dustWeatherHandler;
    private WeatherHandler windexWeatherHandler;

    private Handler gpsHandler;

    private GPSService gpsService = null;
    private RetrofitService retrofitService;

    private SettingsPreferences settingsPreferences;

    private boolean dustOption;
    private boolean feelOption;
    private boolean uvOption;
    private boolean pollenOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, TAG + " onCreate Called!!!");

        init();
        startWeatherAsyncTask();
    }

    private void init(){
        settingsPreferences = SettingsPreferences.getInstance(this);

        temperature_tv = (TextView)findViewById(R.id.temperature_tv);
        max_temperature_tv = (TextView)findViewById(R.id.max_temperature_tv);
        min_temperature_tv = (TextView)findViewById(R.id.min_temperature_tv);
        weather_wind_dir_tv = (TextView)findViewById(R.id.weather_wind_dir_tv);
        weather_wind_speed_tv = (TextView)findViewById(R.id.weather_wind_speed_tv);
        weather_icon_imgv = (ImageView)findViewById(R.id.weather_icon_imgv);
        weather_icon_name_tv = (TextView)findViewById(R.id.weather_icon_name_tv);
        cur_update_time_tv = (TextView)findViewById(R.id.cur_update_time_tv);
        location_name_tv = (TextView)findViewById(R.id.location_name_tv);
        weather_humidity_tv = (TextView)findViewById(R.id.weather_humidity_tv);
        yest_max_temp_tv = (TextView)findViewById(R.id.yest_max_temp_tv);
        yest_min_temp_tv = (TextView)findViewById(R.id.yest_min_temp_tv);
        today_max_temp_tv = (TextView)findViewById(R.id.today_max_temp_tv);
        today_min_temp_tv = (TextView)findViewById(R.id.today_min_temp_tv);
        tom_max_temp_tv = (TextView)findViewById(R.id.tom_max_temp_tv);
        tom_min_temp_tv = (TextView)findViewById(R.id.tom_min_temp_tv);

        yest_icon_imgv = (ImageView)findViewById(R.id.yest_icon_imgv);
        today_icon_imgv = (ImageView)findViewById(R.id.today_icon_imgv);
        tom_icon_imgv = (ImageView)findViewById(R.id.tom_icon_imgv);

        forecast_update_time_tv = (TextView)findViewById(R.id.forecast_update_time_tv);
        gpsInit();

    }
    private void gpsInit(){
        if(Build.VERSION.SDK_INT >= 23
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        gpsHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                int what = msg.what;
                switch(what){
                    case RENEW_GPS:
                        makeNewGpsService();
                        break;
                    case SEND_PRINT:
                        logPrint((String)msg.obj);
                        break;
                    default:
                        break;
                }
            }
        };
    }
    private void startWeatherAsyncTask(){
        gpsService = new GPSService(MainActivity.this, gpsHandler);
        GPSAyncTask gpsAyncTask = new GPSAyncTask();
        gpsAyncTask.execute(gpsService.isGetLocation());
    }

    /**
     * 기본 날씨 가져오기
     * 1. 현재 날씨
     * 2. 어제, 오늘, 내일 날씨
     */
    private void doBasicWeatherService(double latitude, double longitude){
        curWeatherHandler = new CurrentWeatherHandler();
        forecastWeatherHandler = new ForecastWeatherHandler();

        BasicWeatherRetrofit basicWeatherRetrofit = new BasicWeatherRetrofit(curWeatherHandler, this, latitude, longitude);
        ForecastWeatherRetrofit forecastWeatherRetrofit = new ForecastWeatherRetrofit(forecastWeatherHandler, this, latitude, longitude);

        curWeatherHandler.setResponseResult(WeatherValue.CONNECTING);
        forecastWeatherHandler.setResponseResult(WeatherValue.CONNECTING);

        basicWeatherRetrofit.start();
        forecastWeatherRetrofit.start();

        dustWeatherHandler = new DustWeatherHandler();
        windexWeatherHandler = new WIndexWeatherHandler();

        if(settingsPreferences.getOptionCount() > 0){
            getOptionStatus(latitude, longitude);
        }
    }
    /**
     * 추가 날씨
     * 미세먼지, 체감온도, 자외선, 꽃가루
     * SharedPreferences에서 체크 상태 가져온다.
     */
    private void getOptionStatus(double latitude, double longitude){
        dustOption = settingsPreferences.getSettingsPreference(WeatherValue.SETTING_DUST_PREF);
        feelOption = settingsPreferences.getSettingsPreference(WeatherValue.SETTING_FEEL_PREF);
        uvOption = settingsPreferences.getSettingsPreference(WeatherValue.SETTING_UV_PREF);
        pollenOption = settingsPreferences.getSettingsPreference(WeatherValue.SETTING_POLLEN_PREF);
        doAddtionWeatherService(latitude, longitude);
    }

    /**
     * 추가 날씨
     * 미세먼지, 체감온도, 자외선, 꽃가루
     * 중 선택된 날씨를 추가적으로 화면에 뿌린다.
     */
    private void doAddtionWeatherService(double latitude, double longitude){

        if(dustOption){
            dustWeatherHandler.setResponseResult(WeatherValue.CONNECTING);
            retrofitService = new DustWeatherRetrofit(dustWeatherHandler, this, latitude, longitude);
            retrofitService.start();
        }
        else {
            dustWeatherHandler.setResponseResult(WeatherValue.NONE_SETTING);
        }
        if(feelOption){
            windexWeatherHandler.setFeelResponseResult(WeatherValue.CONNECTING);
            retrofitService = new WindexWeatherRetrofit(windexWeatherHandler, this, latitude, longitude, WeatherValue.SK_PLANET_FEEL_URL);
            retrofitService.start();
        }
        else {
            windexWeatherHandler.setFeelResponseResult(WeatherValue.NONE_SETTING);
        }
        if(uvOption){
            windexWeatherHandler.setUvResponseResult(WeatherValue.CONNECTING);
            retrofitService = new WindexWeatherRetrofit(windexWeatherHandler, this, latitude, longitude, WeatherValue.SK_PLANET_UV_URL);
            retrofitService.start();
        }
        else {
            windexWeatherHandler.setUvResponseResult(WeatherValue.NONE_SETTING);
        }
        if(pollenOption){
            windexWeatherHandler.setPollenResponseResult(WeatherValue.CONNECTING);
            retrofitService = new WindexWeatherRetrofit(windexWeatherHandler, this, latitude, longitude, WeatherValue.SK_PLANET_POLLEN_URL);
            retrofitService.start();
        }
        else {
            windexWeatherHandler.setPollenResponseResult(WeatherValue.NONE_SETTING);
        }
    }

    private void makeNewGpsService(){
        if(gpsService == null){
            gpsService = new GPSService(MainActivity.this, gpsHandler);
        }
        else {
            gpsService.Update();
        }
    }


    public void logPrint(String str){
        Log.d(TAG, getTimeStr() + " " + str + "\n");
    }

    public String getTimeStr(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("MM/dd HH:mm:ss");
        return sdfNow.format(date);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == OPTION_REQUEST_CODE){
            switch(resultCode){
                case RESULT_OK:
                    startWeatherAsyncTask();
                    break;
                case RESULT_CANCELED:
                    break;
                default:
                    break;
            }
        }
    }

    /* GPS 정보를 받아 비동기 처리를 통해 현재 위치 확인 */
    public class GPSAyncTask extends AsyncTask<Boolean, Integer, Integer> {
        ProgressDialog gpsDialog = null;
        private final int GPS_SUCCESS = 1;
        private final int GPS_DISABLED = -1;
        private double latitude;
        private double longitude;
        private UIAsyncTask uiAsyncTask;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            gpsDialog = new ProgressDialog(MainActivity.this);
            gpsDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            gpsDialog.setMessage("GPS 정보를 받아오는 중...");
            gpsDialog.show();
        }

        @Override
        protected Integer doInBackground(Boolean... args){
            int loadingCount = 0;
            boolean gpsStatus = args[0];
            if(!gpsStatus){
                latitude = Double.parseDouble(settingsPreferences.getLastLatWeatherPreferences(
                        WeatherValue.SETTING_DEFAULT_LAT_PREF));
                longitude = Double.parseDouble(settingsPreferences.getLastLongWeatherPreferences(
                        WeatherValue.SETTING_DEFAULT_LON_PREF));
                gpsDialog.dismiss();
                return GPS_DISABLED;
            }
            else {
                while(loadingCount <= WeatherValue.SK_PLANET_CONNECT_TIMEOUT){
                    try{
                        Thread.sleep(1000);
                        loadingCount += 1000;
                        latitude = gpsService.getLatitude();
                        longitude = gpsService.getLongitude();
                        if(latitude != 0.0 && longitude != 0.0){
                            gpsDialog.dismiss();
                            gpsDialog = null;
                            return GPS_SUCCESS;
                        }
                    }
                    catch (InterruptedException ie){
                        ie.printStackTrace();
                    }
                }
                return GPS_DISABLED;
            }
        }
        @Override
        protected void onPostExecute(Integer result){
            super.onPostExecute(result);
            switch(result){
                case GPS_SUCCESS:
                    Log.d("GPS_SUCCESS", "GPS_SUCCESS");
                    setGPSLastLocation();
                    doBasicWeatherService(latitude, longitude);
                    uiAsyncTask = new UIAsyncTask();
                    uiAsyncTask.execute();
                    break;
                case GPS_DISABLED:
                    Log.d("GPS_DISABLED", "GPS_DISABLED");
                    Toast.makeText(MainActivity.this, "현재 위치의 날씨를 보시려면 우측 상단의 GPS아이콘을 실행해주세요.", Toast.LENGTH_SHORT).show();
                    doBasicWeatherService(latitude, longitude);
                    uiAsyncTask = new UIAsyncTask();
                    uiAsyncTask.execute();
                    break;
                default:
                    break;
            }
        }
        private void setGPSLastLocation(){
            settingsPreferences.setLastLatPreferences(
                    WeatherValue.SETTING_DEFAULT_LAT_PREF,
                    String.valueOf(latitude));
            settingsPreferences.setLastLongPreferences(
                    WeatherValue.SETTING_DEFAULT_LON_PREF,
                    String.valueOf(longitude));
        }
    }

    /* 날씨 응답 시 비동기 처리를 통해 위젯에 값을 지정한다.*/
    public class UIAsyncTask extends AsyncTask<Void, Integer, Integer[]> {
        private ProgressDialog weatherLoadingDialog = null;
        private LinearLayout other_addtion_ll = null;
        private LinearLayout dust_addtion_ll = null;
        private LinearLayout uv_addtion_ll = null;
        private LinearLayout pollen_addtion_ll = null;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            other_addtion_ll = (LinearLayout)findViewById(R.id.other_addtion_ll);
            dust_addtion_ll = (LinearLayout)findViewById(R.id.dust_addtion_ll);
            uv_addtion_ll = (LinearLayout)findViewById(R.id.uv_addtion_ll);
            pollen_addtion_ll = (LinearLayout)findViewById(R.id.pollen_addtion_ll);

            weatherLoadingDialog = new ProgressDialog(MainActivity.this);
            weatherLoadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            weatherLoadingDialog.setMessage("날씨 정보를 받아오는 중...");

            //여기서 에러남. 이유를 모르겠다...
            weatherLoadingDialog.show();
        }

        @Override
        protected Integer[] doInBackground(Void... args){

            int loadingCount = 0;
            Integer[] connectStatus = new Integer[6];
            while(loadingCount <= WeatherValue.SK_PLANET_CONNECT_TIMEOUT){
                try{
                    Thread.sleep(1000);
                    loadingCount += 1000;
                    setConnectStatus(connectStatus);

                    if(checkConnection(connectStatus)){
                        break;
                    }

                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            weatherLoadingDialog.dismiss();
            weatherLoadingDialog = null;
            return connectStatus;
        }

        @Override
        protected void onPostExecute(Integer[] results){
            super.onPostExecute(results);
            int completeStatus = WeatherValue.CONNECT_FAIL;
            for(int i = 0 ; i < results.length; i++){
                switch(results[i]){
                    case WeatherValue.CONNECT_SUCCESS:
                        setWeather(i);
                        completeStatus = WeatherValue.CONNECT_SUCCESS;
                        break;
                    case WeatherValue.CONNECT_FAIL:
                        diabledWeather(i);
                        completeStatus =  WeatherValue.CONNECT_FAIL;
                        break;
                    default:
                        break;
                }
            }
            if(completeStatus == WeatherValue.CONNECT_SUCCESS){
                Toast.makeText(MainActivity.this, "날씨 정보가 업데이트 되었습니다.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "날씨 정보를 업데이트할 수 없습니다.\n다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
            }
        }

        public void setConnectStatus(Integer[] con){
            con[0] = curWeatherHandler.getResponseResult();
            con[1] = forecastWeatherHandler.getResponseResult();
            con[2] = dustWeatherHandler.getResponseResult();
            con[3] = windexWeatherHandler.getFeelResponseResult();
            con[4] = windexWeatherHandler.getUvResponseResult();
            con[5] = windexWeatherHandler.getPollenResponseResult();
        }

        public boolean checkConnection(Integer[] con){
            for(int i = 0 ; i < con.length; i++){
                if(con[i] == WeatherValue.CONNECTING)
                    return false;
            }
            return true;
        }

        public void setWeather(int weatherKind){
            switch(weatherKind){
                case 0:
                    showBasicWeather();
                    break;
                case 1:
                    showForecastWeather();
                    break;
                case 2:
                    showDustWeather();
                    break;
                case 3:
                    showFeelWeather();
                    break;
                case 4:
                    showUvWeather();
                    break;
                case 5:
                    showPollenWeather();
                    break;
            }
        }

        private void showBasicWeather(){
            BasicWeather basic = BasicWeather.getInstance();
            cur_update_time_tv.setText("업데이트 : " + weatherUpdateTimeStr(basic.getTodayTime()));
            location_name_tv.setText(basic.getCity()
                    + " " + basic.getCountry()
                    + " " + basic.getViliage()
            );
            temperature_tv.setText(basic.getCurTemperature()+" º");
            weather_icon_imgv.setImageResource(
                    InitService.getInstance().getTodayImage(basic.getCurIconCode()));
            weather_icon_name_tv.setText(basic.getCurCloudName());
            max_temperature_tv.setText("최고 온도 : " + basic.getCurMaxTemp()+" º");
            min_temperature_tv.setText("최저 온도 : " + basic.getCurMinTemp()+" º");
            weather_wind_dir_tv.setText("풍향 : " + basic.getCurWindDirection());
            weather_wind_speed_tv.setText("풍속 : " +  basic.getCurWindSpeed() + " m/s");
            weather_humidity_tv.setText("습도 : " + basic.getHumidity());
        }

        private void showForecastWeather(){
            BasicWeather basic = BasicWeather.getInstance();

            forecast_update_time_tv.setText("업데이트 : " + weatherUpdateTimeStr(basic.getForecastTime()));

            yest_icon_imgv.setImageResource(
                    InitService.getInstance().getYesterdayImage(basic.getYestCode()));
            yest_max_temp_tv.setText("최고 : " + basic.getYestTmax() + " º");
            yest_min_temp_tv.setText("최저 : " + basic.getYestTmin() + " º");

            today_icon_imgv.setImageResource(
                    InitService.getInstance().getTodayImage(basic.getCurIconCode()));
            today_max_temp_tv.setText("최고 : " + basic.getCurMaxTemp() + " º");
            today_min_temp_tv.setText("최저 : " + basic.getCurMinTemp() + " º");

            tom_icon_imgv.setImageResource(
                    InitService.getInstance().getTomorrowImage(basic.getTommorrowCode()));
            tom_max_temp_tv.setText("최고 : " + basic.getTommorrowTmax() + " º");
            tom_min_temp_tv.setText("최저 : " + basic.getTommorrowTmin() + " º");
        }

        private void showDustWeather(){
            DustWeather dust = DustWeather.getInstance();
            if(other_addtion_ll.getVisibility() == View.GONE){
                other_addtion_ll.setVisibility(View.VISIBLE);
            }

            if(dust_addtion_ll.getVisibility() == View.GONE){
                dust_addtion_ll.setVisibility(View.VISIBLE);
            }
            TextView dust_info_tv = (TextView)findViewById(R.id.dust_info_tv);
            dust_info_tv.setText(weatherValueStr(dust.getDustValue(), dust.getDustGrade()));
        }

        private void showFeelWeather(){
            WindexWeather wIndex = WindexWeather.getInstance();
            TextView weather_feel_tv = (TextView)findViewById(R.id.weather_feel_tv);
            weather_feel_tv.setText("체감 온도 : " + wIndex.getCurFeelTemp() + " º");

        }

        private void showUvWeather(){
            WindexWeather wIndex = WindexWeather.getInstance();
            if(other_addtion_ll.getVisibility() == View.GONE){
                other_addtion_ll.setVisibility(View.VISIBLE);
            }

            if(uv_addtion_ll.getVisibility() == View.GONE){
                uv_addtion_ll.setVisibility(View.VISIBLE);
            }
            TextView uv_info_tv = (TextView)findViewById(R.id.uv_info_tv);
            uv_info_tv.setText(weatherValueStr(wIndex.getUvValue(), wIndex.getUvComment()));
        }

        private void showPollenWeather(){
            WindexWeather wIndex = WindexWeather.getInstance();
            if(pollen_addtion_ll.getVisibility() == View.GONE){
                pollen_addtion_ll.setVisibility(View.VISIBLE);
            }
            TextView oak_info_tv = (TextView)findViewById(R.id.oak_info_tv);
            TextView pine_info_tv = (TextView)findViewById(R.id.pine_info_tv);
            TextView weed_info_tv = (TextView)findViewById(R.id.weed_info_tv);
            oak_info_tv.setText(weatherValueStr(wIndex.getOakValue(), wIndex.getOakGrade()));
            pine_info_tv.setText(weatherValueStr(wIndex.getPineValue(),wIndex.getPineGrade()));
            weed_info_tv.setText(weatherValueStr(wIndex.getWeedValue(), wIndex.getWeedGrade()));
        }

        private void diabledWeather(int weatherKind){
            switch(weatherKind){
                case 3:
                    dust_addtion_ll.setVisibility(View.GONE);
                    break;
                case 4:
                    uv_addtion_ll.setVisibility(View.GONE);
                    break;
                case 5:
                    pollen_addtion_ll.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    /* GPS, 옵션 선택 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.location:
                if(gpsService == null){
                    gpsService = new GPSService(MainActivity.this, gpsHandler);
                }
                else {
                    gpsService.Update();
                }

                if(gpsService.isGetLocation()){
                    GPSAyncTask gpsAyncTask = new GPSAyncTask();
                    gpsAyncTask.execute(gpsService.isGetLocation());
                }
                else {
                    gpsService.showSettingsAlert();
                }
                return true;
            case R.id.setting:
                Intent intent = new Intent(MainActivity.this, WeatherOptionActivity.class);
                startActivityForResult(intent, OPTION_REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* 관측 된 날씨 값 여부 */
    private String weatherValueStr(String value, String grade){
        if(value.equals("") || grade.equals("")){
            return "관측된 값이 없습니다.";
        }
        else {
            return value + "\n" + grade;
        }
    }

    /* 시간 포맷 변환 */
    private String weatherUpdateTimeStr(String updateTime) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(updateTime);
            String upd_time = new SimpleDateFormat("yyyy년 M월 d일 (a h시)").format(date);
            return upd_time;
        }
        catch (ParseException pe){
            return updateTime;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, TAG+" onResume() Called!!!");
    }

    @Override
    protected  void onPause() {
        super.onPause();
        Log.d(TAG, TAG + " onPause() Called!!!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, TAG+" onStop() Called!!!");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, TAG+" onStart() Called!!!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, TAG+" onDestroy() Called");

        if(gpsService != null && gpsService.isGetLocation()){
            gpsService.stopUsingGPS();
            gpsService = null;
        }
    }
}
