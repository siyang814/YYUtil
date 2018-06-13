package com.lis99.mobile.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;

/**
 * Created by yy on 2017/6/13.
 */

public class SensorUtil {

    private static SensorUtil instance;

    private SensorCallBack callBack;

    private SensorManager sensorManager;

    private MySensorEventListener mySensorEventListener;
    float x;
    float y;
    float z;

    public SensorUtil() {
        if ( mySensorEventListener == null )
        mySensorEventListener = new MySensorEventListener();
    }


    public static SensorUtil getInstance() {
        if ( instance == null ) instance = new SensorUtil();
        return instance;
    }

    public void setCallBack(SensorCallBack callBack) {
        this.callBack = callBack;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void getSensorXY (Activity activity, final SensorCallBack sensorCallBack )
    {
        callBack = sensorCallBack;
        sensorManager = (SensorManager) activity.getSystemService(activity.SENSOR_SERVICE);

//        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(mySensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL, 1000);

    }

    public void reMove ()
    {
        sensorManager.unregisterListener(mySensorEventListener);
        callBack = null;
    }

    private class MySensorEventListener implements SensorEventListener
    {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];
            if ( callBack != null )
            {
                callBack.Handler(x, y,z);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

    public interface SensorCallBack
    {
        public void Handler ( float x, float y,float z );
    }
}
