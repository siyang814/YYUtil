package com.example.list.yyutil.util.updata;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.example.list.yyutil.util.Common;
import com.example.list.yyutil.util.DeviceInfo;
import com.example.list.yyutil.util.FileUtil;
import com.example.list.yyutil.util.internet.CallBack;
import com.example.list.yyutil.util.internet.MyRequestManager;
import com.example.list.yyutil.util.internet.MyTask;
import com.example.list.yyutil.util.internet.OkHttpUtil;

import java.io.File;
import java.util.HashMap;

/**
 * Created by yy on 16/4/5.
 */
public class UpdataUtil {

    private static UpdataUtil instance;
    private UpdataModel model;

    private String url;

    private Activity activity;

    public static UpdataUtil getInstance ()
    {
        if ( instance == null ) instance = new UpdataUtil();
        return instance;
    }

//    获取储存目录
    public File getStorage ( Context context )
    {
        File file = FileUtil.getPath(
            context, "/lishi99/down");
        return file;
    }

    /**
     *      获取新版本， 0没有更新， 1 有更新， 2强制更新
     */
    public void getUpData (Activity activity)
    {
        this.activity = activity;
        if ( DeviceInfo.CLIENTVERSIONCODE == 0 )
        {
            DeviceInfo.getDeviceInfo(activity);
        }

        HashMap<String, Object> map = new HashMap<>();

        map.put("version", DeviceInfo.CLIENTVERSIONCODE);

        model = new UpdataModel();

        MyRequestManager.getInstance().requestPostNoDialog(url, map, model,
                new CallBack() {

                    @Override
                    public void handler(MyTask mTask) {
                        model = (UpdataModel) mTask.getResultModel();

                        if (model == null) return;

                        if (0 == model.type) {


                        } else if ( 1 == model.type  ){

//                            com.lis99.mobile.util.DialogManager.getInstance().showUpdataDialog(model, upDataCallBack, "");

                        }
                        else if ( 2 == model.type )
                        {

//                            com.lis99.mobile.util.DialogManager.getInstance().showUpdataDialog(model, upDataCallBack, "退出");

                        }

                    }
                });
    }

    private CallBack upDataCallBack = new CallBack() {
        @Override
        public void handler(MyTask mTask) {

            if ( "ok".equals(mTask.getresult()))
            {
                model.filePath = getStorage(activity).toString();
//                model.name = "/lis99.apk";
                downLoadFile();
            }
            else if ( "cancel".equals(mTask.getresult()))
            {
                // 强制更新
                if ( 2 == model.type )
                {
//                    退出应用
                    Common.exitApp();
                }
            }


        }
    };

    public void start ( Activity activity, UpdataModel model )
    {
        this.activity = activity;
        this.model = model;
        model.filePath = getStorage(activity).toString();
        downLoadFile();
    }

    private void downLoadFile ()
    {
        String fullPath = model.filePath + model.appName;

        File file = new File(fullPath);
        if (file.exists()) {
            file.delete();
        }

        file = new File(model.filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(fullPath);

        initDialog();

        OkHttpUtil.getInstance().downLoadFile(model.url, file, new DonwloadResponseListener() {
            @Override
            public void OnSuccess(long bytesRead, long contentLength, boolean done) {
                final int progress = (int) (bytesRead * 100 / contentLength);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.setProgress(progress);
                    }
                });

            }

            @Override
            public void onfailure() {
                Common.toast(activity, "更新失败");
            }

            @Override
            public void onSuccess(File file) {

            }
        });

    }

    public ProgressDialog progressDialog; // 进度条对话框引用
    private void initDialog ()
    {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);// 进度条不能回退
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("下载更新");// 设置标题
        progressDialog.show();
    }

}
