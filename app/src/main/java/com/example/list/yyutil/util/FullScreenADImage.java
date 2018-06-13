//package com.example.list.yyutil.util;
//
//import android.graphics.Bitmap;
//import android.text.TextUtils;
//
//import com.example.list.yyutil.util.DeviceInfo;
//import com.example.list.yyutil.util.MyRequestManager;
//import com.example.list.yyutil.util.ParserUtil;
//
//import java.util.HashMap;
//
///**
// * Created by yy on 15/9/1.
// *
// *  全屏广告
// *
// */
//public class FullScreenADImage  {
//
//    private static FullScreenADImage instance;
//
////    private String URl = "http://api.lis99.com/v2/advertise/channelAd/";
//    private String URl = C.getDOMAIN()+"/v2/advertise/channelAd/";
//
//    private String platform, channel, version;
//
//    private ADModel model;
//
//    private Bitmap bAD;
//
//    public FullScreenADImage ()
//    {
//        initNative();
//    }
//
////    public static FullScreenADImage getInstance ()
////    {
////        if ( instance == null ) instance = new FullScreenADImage();
////        return instance;
////    }
//
//    public void clean ()
//    {
//        if ( bAD != null )
//        bAD.recycle();
//        bAD = null;
//    }
//
//    public void initNative ()
//    {
//        platform = "2";
//        version = "0";
//        channel = DeviceInfo.CHANNELVERSION;
////        测试用
////        channel = "zhuzhan";
//        model = new ADModel();
//        String JsonInfo = SharedPreferencesHelper.getAD();
//        if (!TextUtils.isEmpty(JsonInfo))
//        {
//            model = (ADModel) ParserUtil.getParserResult(JsonInfo, model, null);
//            if ( model == null )
//            {
//                model = new ADModel();
//            }
//            else
//            {
//                version = model.lists.version;
//                //没有广告 ==0
//                if ( model.lists.flag != 0 )
//                {
//                    bAD = com.lis99.mobile.util.ImageUtil.getAD(LSBaseActivity.activity);
//                }
//            }
//        }
//    }
//
//    public Bitmap getbAD ()
//    {
//        return bAD;
//    }
//
//    //检查更新
//    public void getUpdata ()
//    {
//        URl = URl + platform +"/" + version;
//        HashMap<String, Object> map = new HashMap<String, Object>();
//
//        map.put("channel", channel);
//
//        MyRequestManager.getInstance().requestPostNoDialog(URl, map, model, new CallBack() {
//            @Override
//            public void handler(com.lis99.mobile.engine.base.MyTask mTask) {
//                model = (ADModel) mTask.getResultModel();
//                SharedPreferencesHelper.saveAD(mTask.getresult());
//                //有新广告
//                if ( model.lists.flag == 2 )
//                {
//                    com.lis99.mobile.util.ImageUtil.saveAD(LSBaseActivity.activity, model.lists.images);
//                }
//                //本地广告被删除了， 从新下载
//                if (  model.lists.flag == 1 && bAD == null )
//                {
//                    com.lis99.mobile.util.ImageUtil.saveAD(LSBaseActivity.activity, model.lists.images);
//                }
//            }
//        });
//
//    }
//
//
//}
