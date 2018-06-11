package com.project.skweather.service;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.project.skweather.ui.MainActivity;

import java.util.Set;

/**
 * Created by Administrator on 2018-06-05.
 */

public class GPSService extends Service implements LocationListener {
    private static final String TAG = "GPSService";

    private final Context context;

    boolean isGPSEnabled = false;

    boolean isNetworkEnabled = false;

    boolean isGetLocation = false;

    Location location;
    double latitude;
    double longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    private static final long MIN_TIME_BW_UPDATES = 1000*60*1;

    protected LocationManager locationManager;

    private Handler handler;

    public GPSService(Context context, Handler handler){
        this.context = context;
        this.handler = handler;
        getLocation();
    }

    public void Update(){
        getLocation();
    }

    public Location getLocation(){
        if(Build.VERSION.SDK_INT >= 23
                && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            Log.d(TAG, "PermissionStatus : " + ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION));

            return null;
        }
        try {
            locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGPSEnabled && !isNetworkEnabled){
                Log.d(TAG, "isGPSEnabled : " + isGPSEnabled);
                Log.d(TAG, "isNetworkEnabled : " + isNetworkEnabled);

            }
            else {
                if(isNetworkEnabled){
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d(TAG, "Network Enabled");

                    if(locationManager != null){
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(location != null){
                            latitude = location.getLatitude();
                            longitude = location.getLatitude();
                        }
                        this.isGetLocation = true;
                    }
                }
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d(TAG, "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                            this.isGetLocation = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }
    public void stopUsingGPS(){
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            return;
        }
        if(locationManager != null){
            locationManager.removeUpdates(GPSService.this);
        }
    }

    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        Log.d("LocationServie", latitude + "");
        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
        Log.d("LocationServie", longitude + "");
        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean isGetLocation() {
        return this.isGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS 사용이 불가능한 상태입니다. 환경 설정 메뉴로 가시겠습니까?");

        // On pressing Settings button
        alertDialog.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null){
            double latitude= location.getLatitude();
            double longitude = location.getLongitude();
            sendString("onLocationChanged is - \nLat: " + latitude + "\nLong: " + longitude + " provider:"+location.getProvider()+" mock:"+location.isFromMockProvider());
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        handler.sendEmptyMessage(MainActivity.RENEW_GPS);
        sendString( "onProviderDisabled " + provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        handler.sendEmptyMessage(MainActivity.RENEW_GPS);
        sendString( "onProviderEnabled " + provider);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        handler.sendEmptyMessage(MainActivity.RENEW_GPS);
        sendString("onStatusChanged " + provider + " : " + status + ":" + printBundle(extras));
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private void sendString(String str){
        Message msg = handler.obtainMessage();
        msg.what = MainActivity.SEND_PRINT;
        msg.obj = new String(str);
        handler.sendMessage(msg);
    }

    public static String printBundle(Bundle extras) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("extras = " + extras);
            sb.append("\n");
            if (extras != null) {
                Set keys = extras.keySet();
                sb.append("++ bundle key count = " + keys.size());
                sb.append("\n");

                for (String _key : extras.keySet()) {
                    sb.append("key=" + _key + " : " + extras.get(_key)+",");
                }
                sb.append("\n");
            }
        } catch (Exception e) {

        } finally {

        }
        return sb.toString();
    }
}
