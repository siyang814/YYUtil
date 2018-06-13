package com.example.list.yyutil.util.image.updata;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.list.yyutil.util.ParserUtil;
import com.example.list.yyutil.util.internet.CallBack;
import com.example.list.yyutil.util.internet.MyTask;
import com.example.list.yyutil.util.internet.OkHttpUtil;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by yy on 2018/5/18.
 */

public class OkhttpSendImage {

    private OkHttpUtil okHttpUtil;
    private String url;
    private HashMap<String, Object> map;
    private Object resultModel;
    private CallBack callBack;


    public OkhttpSendImage() {
        okHttpUtil = new OkHttpUtil();
    }


    public void PostImage (String url, HashMap<String, Object> map, final Object resultModel, final CallBack callBack) throws IOException {

        this.url = url;
        this.map = map;
        this.resultModel = resultModel;
        this.callBack = callBack;
        new MyAsyncTask().execute("");
    }



    class MyAsyncTask extends AsyncTask
    {

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            MyTask myTask = new MyTask();
            String result = (String) o;
            if ( TextUtils.isEmpty(result))
            {
                callBack.handlerError(null);
                return;
            }
            myTask.setResultModel(ParserUtil.getParserResult(result, resultModel, null));
            callBack.handler(myTask);

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                return okHttpUtil.post(url, map);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
    }



}
