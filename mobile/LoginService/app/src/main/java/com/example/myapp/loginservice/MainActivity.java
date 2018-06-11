package com.example.myapp.loginservice;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private CharSequence title;
    private CharSequence drawerTitle;
    private Button logout_btn;
    private ImageView platform_imgv;
    private TextView login_email, login_name;

    private String platform;
    private String email;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(drawerTitle);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(title);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("PLATFORM", platform);
                startActivity(intent);
                finish();

            }
        });
    }

    private void init(){
        title = drawerTitle = getTitle();

        Intent intent = getIntent();
        platform = intent.getStringExtra("PLATFORM");
        email = intent.getStringExtra("LOGIN_EMAIL");
        name = intent.getStringExtra("LOGIN_NAME");

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        platform_imgv = (ImageView)findViewById(R.id.platform_imgv);
        if(platform.equals("facebook")){
            platform_imgv.setImageResource(R.drawable.facebook_logo);
        }
        else {
            platform_imgv.setImageResource(R.drawable.google_logo);
        }
        login_email = (TextView)findViewById(R.id.login_email_tv);
        login_name = (TextView)findViewById(R.id.login_name_tv);
        logout_btn = (Button)findViewById(R.id.logout_btn);

        login_email.setText(email);
        login_name.setText(name);

    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.d("onPostCreate", "onPostCreate is Called");
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        Log.d("onPonfigurateionChanged", "onConfigurationChanged is Called");
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
