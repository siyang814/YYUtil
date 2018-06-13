package com.example.list.yyutil.util.internet;

import android.util.Log;

/**
 * Created by yy on 2018/6/11.
 */

public class TimeOutTest {

    private long requestTime;
    private long callBackTime;
    private String url;

    public TimeOutTest(String sUrl ) {
//        if ( !Common.isTest() ) return;
        this.url = sUrl;
        requestTime = System.currentTimeMillis();

    }

    public void callBack ()
    {
//        if ( !Common.isTest() ) return;
        callBackTime = System.currentTimeMillis();
        log();
    }


    public void log ()
    {
        Log.w("MYTIME", "接口"+url+"耗时："+(callBackTime - requestTime)+"毫秒");
//        Log.w("MYTIME", "Interface : "+url+" ："+(callBackTime - requestTime)+" Millisecond");
    }

}
