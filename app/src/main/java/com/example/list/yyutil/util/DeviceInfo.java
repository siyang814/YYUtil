package com.example.list.yyutil.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by yy on 2018/5/30.
 */

public class DeviceInfo {

    public static String IMEI, SDKVERSION, CLIENTVERSION, CHANNELVERSION, MODEL, PLATFORM = "1", PLATFORM_NEW = "Android";
    public static int CLIENTVERSIONCODE, SDKVERSIONCODE;

    public static String PUSH_TOKEN;

    /**
     * 	manufacturer	厂商
     */
    public static String MANUFACTURER;

    @SuppressLint("MissingPermission")
    public static void getDeviceInfo (Context a)
    {
        getVersion(a);
        MODEL = android.os.Build.MODEL;
        SDKVERSION = Build.VERSION.SDK;
        SDKVERSIONCODE = android.os.Build.VERSION.SDK_INT;

        MANUFACTURER = Build.MANUFACTURER;

        try
        {
            ApplicationInfo ai = a.getPackageManager().getApplicationInfo(a.getPackageName(), PackageManager.GET_META_DATA);
//			CHANNELVERSION = ai.metaData.getString("UMENG_CHANNEL");
            CHANNELVERSION = ai.metaData.getString("JPUSH_CHANNEL");
            //这里检测一下  判断是否是通过美团打包方式生成的apk，
            // 如果是，则通过它的方式设置渠道号，
            // 如果不是,则使用默认的方式得到渠道号
            if(!ChannelUtil.getChannel(a, ChannelUtil.CHANNEL_DEFAULT).equals(ChannelUtil.CHANNEL_DEFAULT)){
                CHANNELVERSION=ChannelUtil.getChannel(a, ChannelUtil.CHANNEL_DEFAULT);
            }
            String Jpush = "JPUSH_CHANNEL="+CHANNELVERSION + "\nJPUSH_APPKEY="+ai.metaData.getString("JPUSH_APPKEY");

            Common.log(Jpush);
//            PUSH_TOKEN = SharedPreferencesHelper.getJPushToken();

            Common.log("\nJPushToken="+ PUSH_TOKEN);

//			CHANNELVERSION = "zs360";
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Common.log("CANNOT GET TOKEN="+e.getMessage());
        }


        TelephonyManager tm = (TelephonyManager) a.getSystemService(a.TELEPHONY_SERVICE);
        IMEI = tm.getDeviceId();
        Common.log("MODEL="+MODEL+"=IMEI="+IMEI+"=SDKVERSION="+SDKVERSION + "=SDKVERSIONCODE=" + SDKVERSIONCODE + "=CLIENTVERSION="+CLIENTVERSION + "=CLIENTVERSIONCODE="+CLIENTVERSIONCODE + "=CHANNELVERSION=" + CHANNELVERSION+"\n MANUFACTURER="+MANUFACTURER);

    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    private static void getVersion(Context a) {
        try {
            PackageManager manager = a.getPackageManager();
            PackageInfo info = manager.getPackageInfo(a.getPackageName(), 0);
            CLIENTVERSION = info.versionName;
            CLIENTVERSIONCODE = info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断wifi连接状态
     *
     * @return
     */
    public boolean isWifiAvailable( Context context ) {
        ConnectivityManager conMan = (ConnectivityManager) context
                .getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo.State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        if (NetworkInfo.State.CONNECTED == wifi) {
            return true;
        } else {
            return false;
        }
    }

    public static String getLocalIpAddress() {
        String ipAddress = null;
        try {
            List<NetworkInterface> interfaces = Collections
                    .list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface iface : interfaces) {
                if (iface.getDisplayName().equals("eth0")) {
                    List<InetAddress> addresses = Collections.list(iface
                            .getInetAddresses());
                    for (InetAddress address : addresses) {
                        if (address instanceof Inet4Address) {
                            ipAddress = address.getHostAddress();
                        }
                    }
                } else if (iface.getDisplayName().equals("wlan0")) {
                    List<InetAddress> addresses = Collections.list(iface
                            .getInetAddresses());
                    for (InetAddress address : addresses) {
                        if (address instanceof Inet4Address) {
                            ipAddress = address.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipAddress;
    }


    //	获取本机WIFI
    private String getWifiIpAddress( Context context ) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        // 获取32位整型IP地址
        int ipAddress = wifiInfo.getIpAddress();

        //返回整型地址转换成“*.*.*.*”地址
        return String.format("%d.%d.%d.%d",
                (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
    }

    //	3G网络IP
    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();)
            {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * .获取手机MAC地址
     * 只有手机开启wifi才能获取到mac地址
     */
    public static String getMacAddress( Context context ){
        String result = "";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        result = wifiInfo.getMacAddress();
        return result;
    }



    @SuppressLint("MissingPermission")
    public String getPhoneInfo(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        StringBuffer sb = new StringBuffer();

        sb.append("\nLine1Number = " + tm.getLine1Number());
        sb.append("\nNetworkOperator = " + tm.getNetworkOperator());//移动运营商编号
        sb.append("\nNetworkOperatorName = " + tm.getNetworkOperatorName());//移动运营商名称
        sb.append("\nSimCountryIso = " + tm.getSimCountryIso());
        sb.append("\nSimOperator = " + tm.getSimOperator());
        sb.append("\nSimOperatorName = " + tm.getSimOperatorName());
        sb.append("\nSimSerialNumber = " + tm.getSimSerialNumber());
        sb.append("\nSubscriberId(IMSI) = " + tm.getSubscriberId());
        return  sb.toString();
    }



}
