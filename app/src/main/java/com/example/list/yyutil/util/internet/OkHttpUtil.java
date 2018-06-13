package com.example.list.yyutil.util.internet;

import android.text.TextUtils;

import com.example.list.yyutil.util.Common;
import com.example.list.yyutil.util.updata.DonwloadResponseListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yy on 16/8/16.
 */
public class OkHttpUtil {

    private static OkHttpUtil instance;

    private OkHttpClient okHttpClient;

    private OkHttpClient.Builder builder;

    private static final int TIMEOUT = 10;

//    private RequestHeadInterface headInterface;

    public OkHttpUtil() {

            builder = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS);

        try {
            final TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
            };

        // Install the all-trusting trust manager
        SSLContext sslContext = null;
        SSLSocketFactory sslSocketFactory = null;

            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            sslSocketFactory = sslContext.getSocketFactory();


        builder.sslSocketFactory(sslSocketFactory);

        } catch (Exception e) {
            e.printStackTrace();
        }

        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

//        builder.addInterceptor(new HttpLoggingInterceptor());

        okHttpClient = builder.build();

        getHead ();
    }

    public static OkHttpUtil getInstance() {
        if ( instance == null )
        {
            synchronized (OkHttpUtil.class)
            {
                if ( instance == null )
                {
                    instance = new OkHttpUtil();
                }
            }
        }

        return instance;
    }

//    获取公共的请求头信息
    private void getHead ()
    {
//        headInterface = RequestHeadInfo.getInstance();
    }

    private String getHttp (String url ) throws IOException {


//        if ( headInterface != null )
//        {
//            url = headInterface.assembleHeads(url);
//        }

        Request.Builder requestB = new Request.Builder().url(url);
        requestB.method("GET", null);
        Request request = requestB.build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        if ( !response.isSuccessful()) throw new IOException("Not isSuccessful="+response);
//        if ( null != response.cacheResponse() ) return response.cacheResponse().toString();
        Common.log("GetRequestUrl====="+url);
        return response.body() == null ? "" : response.body().source().readUtf8();
    }


    private String postHttp (String url, Map<String, Object> map ) throws IOException {
        FormBody.Builder fbuilder = new FormBody.Builder();

//        if ( headInterface != null )
//        {
//            map = headInterface.assembleHeads(map);
//        }

        if ( map != null && !map.isEmpty() )
        {
            Set<String> keys = map.keySet();

            for ( String key : keys )
            {
                String value = String.valueOf(map.get(key));
                fbuilder.add(key, value);
            }
        }



//        Common.log("RequestBody="+map.toString());

        RequestBody requestBody = fbuilder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        if ( !response.isSuccessful()) throw new IOException("Not isSuccessful="+response);
//        if ( null != response.cacheResponse() ) return response.body().toString();
        return response.body() == null ? "" : response.body().source().readUtf8();
    }

    private void getHttpAsyn (String url, Callback callback ) throws IOException {
//        if ( headInterface != null )
//        {
//            url = headInterface.assembleHeads(url);
//        }

        Request.Builder requestB = new Request.Builder().url(url);
        requestB.method("GET", null);
        Request request = requestB.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    private void postHttpAsyn (String url, Map<String, Object> map, Callback callback ) throws IOException {


//        if ( headInterface != null )
//        {
//            map = headInterface.assembleHeads(map);
//        }

        FormBody.Builder fbuilder = new FormBody.Builder();
        if ( map != null && !map.isEmpty() )
        {
            Set<String> keys = map.keySet();

            for ( String key : keys )
            {
                String value = String.valueOf(map.get(key));
                fbuilder.add(key, value);
            }
        }

        RequestBody requestBody = fbuilder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(callback);
    }

//    上传图片
    private String postImg ( String url, Map<String, Object> map ) throws IOException {

        if (TextUtils.isEmpty(url) || map == null || map.isEmpty() )
        {
            return null;
        }

//        if ( headInterface != null )
//        {
//            map = headInterface.assembleHeads(map);
//        }

        MediaType JPG = MediaType.parse("image/jpg");

        Set<Map.Entry<String, Object>> set = map.entrySet();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        for ( Map.Entry<String, Object> entry : set )
        {
            if ( "photo".equals(entry.getKey()))
            {
                builder.addFormDataPart("photo", "image.jpg", RequestBody.create(JPG, (byte[])map.get("photo")));
                continue;
            }
            builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
        }

        RequestBody body = builder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            if ( !response.isSuccessful()) throw new IOException("Not isSuccessful="+response);
//        if ( null != response.cacheResponse() ) return response.cacheResponse().toString();
            return response.body() == null ? "" : response.body().source().readUtf8();
    }

    public void downLoadFile (String url, final File file, final DonwloadResponseListener downloadListener )
    {
        if ( TextUtils.isEmpty(url) )
        {
            if ( downloadListener != null )
            {
                downloadListener.onfailure();
            }
        }

        Request.Builder requestB = new Request.Builder().url(url);
        requestB.method("GET", null);
        Request request = requestB.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                if ( downloadListener != null )
                {
                    downloadListener.onfailure();
                }
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if ( !response.isSuccessful() )
                {
                    downloadListener.onfailure();
                    return;
                }

                InputStream inputStream = null;
                byte[] buf = new byte[2048];
                int length = 0;
                FileOutputStream fileOutputStream = null;

                try {
                    inputStream = response.body().byteStream();

                    long total = response.body().contentLength();

                    fileOutputStream = new FileOutputStream(file);

                    long sum = 0;

                    while ( (length = inputStream.read(buf)) != -1 )
                    {
                        fileOutputStream.write(buf);

                        sum += length;

                        downloadListener.OnSuccess(sum, total, false);

                    }

                    fileOutputStream.flush();

                    downloadListener.onSuccess(file);


                }catch (Exception e)
                {

                }
                finally {
                    try {
                        if ( inputStream != null )
                        {
                            inputStream.close();
                        }
                    }
                    catch ( Exception e)
                    {

                    }
                    try {
                        if ( fileOutputStream != null )
                        {
                            fileOutputStream.close();
                        }
                    }
                    catch ( Exception e)
                    {

                    }
                }

            }



        });




    }


    public String get (String url ) throws IOException {
        return getHttp(url);
    }

    public String post (String url, Map<String, Object> map ) throws IOException {
        return postHttp(url, map);
    }

    public void getAsyn (String url, Callback callback ) throws IOException {
        getHttpAsyn(url, callback);
    }

    public void postAsyn (String url, HashMap<String, Object> map ,Callback callback ) throws IOException {
        postHttpAsyn(url, map, callback);
    }

    public String postImage (String url, Map<String, Object> map ) throws IOException
    {
        return postImg(url, map);
    }



















}


