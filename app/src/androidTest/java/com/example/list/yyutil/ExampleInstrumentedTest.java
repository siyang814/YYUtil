package com.example.list.yyutil;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.list.yyutil.util.DeviceInfo;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    DeviceInfo deviceInfo;

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.list.yyutil", appContext.getPackageName());

        deviceInfo = new DeviceInfo();

        deviceInfo.getPhoneInfo(appContext);


    }

    public void getDeviceInfo ()
    {
        System.out.println("DeviceInfo.CHANNELVERSION="+DeviceInfo.CHANNELVERSION);
    }

}
