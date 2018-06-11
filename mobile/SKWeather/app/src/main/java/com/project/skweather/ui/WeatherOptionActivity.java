package com.project.skweather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;

import com.project.skweather.R;
import com.project.skweather.preferences.SettingsPreferences;
import com.project.skweather.utils.WeatherValue;

/**
 * Created by Administrator on 2018-06-05.
 */

public class WeatherOptionActivity extends AppCompatActivity implements View.OnClickListener {

    private SettingsPreferences settingsPreferences;

    private CheckBox dust_cb, feel_cb, uv_cb, pollen_cb;
    private Button option_btn;

    private boolean dustChecked, feelChecked, uvChecked, pollenChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_option);
        settingsPreferences = SettingsPreferences.getInstance(this);
        init();

    }
    private void init(){
        dust_cb = (CheckBox)findViewById(R.id.dust_cb);
        feel_cb = (CheckBox)findViewById(R.id.feel_cb);
        uv_cb = (CheckBox) findViewById(R.id.uv_cb);
        pollen_cb = (CheckBox)findViewById(R.id.pollen_cb);

        option_btn = (Button)findViewById(R.id.option_btn);

        dust_cb.setChecked(settingsPreferences.getSettingsPreference(WeatherValue.SETTING_DUST_PREF));
        feel_cb.setChecked(settingsPreferences.getSettingsPreference(WeatherValue.SETTING_FEEL_PREF));
        uv_cb.setChecked(settingsPreferences.getSettingsPreference(WeatherValue.SETTING_UV_PREF));
        pollen_cb.setChecked(settingsPreferences.getSettingsPreference(WeatherValue.SETTING_POLLEN_PREF));

        optionCheckedInit();

        option_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dust_cb.isChecked() != dustChecked ||
                        feel_cb.isChecked() != feelChecked ||
                        uv_cb.isChecked() != uvChecked ||
                        pollen_cb.isChecked() != pollenChecked){

                    settingsPreferences.setSettingsPreference(WeatherValue.SETTING_DUST_PREF, dust_cb.isChecked());
                    settingsPreferences.setSettingsPreference(WeatherValue.SETTING_FEEL_PREF, feel_cb.isChecked());
                    settingsPreferences.setSettingsPreference(WeatherValue.SETTING_UV_PREF, uv_cb.isChecked());
                    settingsPreferences.setSettingsPreference(WeatherValue.SETTING_POLLEN_PREF, pollen_cb.isChecked());

                    Intent intent = new Intent();
                    intent.putExtra("optionResult", 1);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                else {
                    Intent intent = new Intent();
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }
            }
        });
    }

    private void optionCheckedInit(){
        dustChecked = dust_cb.isChecked();
        feelChecked = feel_cb.isChecked();
        uvChecked = uv_cb.isChecked();
        pollenChecked = pollen_cb.isChecked();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.dust_ll:
                dust_cb.setChecked(!dust_cb.isChecked());
                break;
            case R.id.feel_ll:
                feel_cb.setChecked(!feel_cb.isChecked());
                break;
            case R.id.uv_ll:
                uv_cb.setChecked(!uv_cb.isChecked());
                break;
            case R.id.pollen_ll:
                pollen_cb.setChecked(!pollen_cb.isChecked());
                break;
            default:
                break;
        }
    }
}
