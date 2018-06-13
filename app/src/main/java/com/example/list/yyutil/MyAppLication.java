package com.example.list.yyutil;

import android.app.Application;
import android.content.Context;

/**
 * Created by yy on 2018/6/12.
 */

public class MyAppLication extends Application {

    static private MyAppLication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

    }

    public static Context getContext ()
    {
        return instance.getApplicationContext();
    }



    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
