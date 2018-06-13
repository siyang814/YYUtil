package com.example.list.yyutil.util.internet;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.sdk.android.httpdns.HttpDns;
import com.alibaba.sdk.android.httpdns.HttpDnsService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by yy on 2018/6/11.
 */

public class ALHttpDns {

    private String accountId = "157060";

    private HttpDnsService httpDnsService;

    private static ALHttpDns instance;

    public String[] url;

    public static ALHttpDns getInstance(Context context) {
        if (instance == null) instance = new ALHttpDns(context);
        return instance;
    }

    public ALHttpDns(Context context) {

        if (httpDnsService == null) {
            httpDnsService = HttpDns.getService(context, accountId);
            httpDnsService.setTimeoutInterval(5000);
            httpDnsService.setHTTPSRequestEnabled(true);
            httpDnsService.setCachedIPEnabled(false);
            ArrayList<String> hostList = new ArrayList<>(Arrays.asList(url));
            httpDnsService.setPreResolveHosts(hostList);
        }

    }

    public String getHostIp(String host) {
//        if (Common.isTest() && C.TESTHOST) return host;
        String ip = "";
        try {
            URL url = new URL(host);
            String urlHost = url.getHost();
            ip = httpDnsService.getIpByHostAsync(urlHost);
            if (TextUtils.isEmpty(ip)) return host;
            host = host.replaceFirst(urlHost, ip);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return host;
    }
}
