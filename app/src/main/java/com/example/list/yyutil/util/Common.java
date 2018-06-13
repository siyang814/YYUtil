package com.example.list.yyutil.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by yy on 2018/5/30.
 */

public class Common {

    public static int WIDTH, HEIGHT, densityDpi;
    public static float scale;

    private static String TAG = "MYUTIL";

    public static void log(String str) {
//        if ("ttest".equals(CHANNELVERSION))
            Log.w(TAG, str);
    }

    /**
     * 自定义的TOAST
     */
    public static void toast(Activity a, String s) {
        Toast.makeText(a, s, Toast.LENGTH_SHORT)
                .show();
    }


    private static long firstClick, lastClick;
    private static int count;
    /**
     * 双击判断，
     *
     * @return true 双击， fasle 单击
     */
    public static boolean isDoubleClick() {
        // 如果第二次点击 距离第一次点击时间过长 那么将第二次点击看为第一次点击
        if (firstClick != 0 && System.currentTimeMillis() - firstClick > 300) {
            count = 0;
        }
        count++;
        if (count == 1) {
            firstClick = System.currentTimeMillis();
        } else if (count == 2) {
            lastClick = System.currentTimeMillis();
            // 两次点击小于300ms 也就是连续点击
            if (lastClick - firstClick < 300) {// 判断是否是执行了双击事件
                return true;
            }
        }
        return false;
    }

    /**
     * 双击退出应用
     *
     * @return true 双击， fasle 单击
     */
    public static boolean exitApp() {
        // 如果第二次点击 距离第一次点击时间过长 那么将第二次点击看为第一次点击
        if (firstClick != 0 && System.currentTimeMillis() - firstClick > 3000) {
            count = 0;
        }
        count++;
        if (count == 1) {
            firstClick = System.currentTimeMillis();
        } else if (count == 2) {
            lastClick = System.currentTimeMillis();
            // 两次点击小于300ms 也就是连续点击
            if (lastClick - firstClick < 3000) {// 判断是否是执行了双击事件
                return true;
            }
        }
        return false;
    }

    /**
     * 拨打电话
     *
     * @param number
     */
    public static void telPhone(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
        context.startActivity(intent);
    }


    public static int dip2px(float dipValue) {
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 判断手机是否有SD卡。
     *
     * @return 有SD卡返回true，没有返回false。
     */
    public static boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    /**
     * 隐藏键盘
     *
     * @param a
     */
    public static void hideSoftInput(Activity a) {

        View view = a.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) a.getSystemService(Context
                    .INPUT_METHOD_SERVICE);

            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    //显示键盘
    public static void showSoftInput(Activity a, EditText view) {
        InputMethodManager inputmanger = (InputMethodManager) a.getSystemService(Context
                .INPUT_METHOD_SERVICE);
//                inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        inputmanger.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    // 安装APK
    public static final void installAPK(Activity activity, String fileURL) {
        try {
            File file = new File(fileURL);
            Common.log("apk path = " + file.getAbsolutePath());
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            String type = "application/vnd.android.package-archive";
            intent.setDataAndType(Uri.fromFile(file), type);
            activity.startActivity(intent);
        } catch (Exception e) {
            Common.log("installAPK Exception = " + e.toString());
        }
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date now = new Date();
        return format.format(now);


    }

    /**
     * 拷贝文字到剪切板
     *
     * @param a
     * @param str
     */
    public static void copyText(Activity a, String str) {
        if (TextUtils.isEmpty(str)) return;
        ClipData myClip = ClipData.newPlainText("text", str);
        ClipboardManager myClipBoard = (ClipboardManager) a.getSystemService(a
                .getApplicationContext().CLIPBOARD_SERVICE);
        myClipBoard.setPrimaryClip(myClip);
        Common.toast(a, "已复制：" + str);
    }
    /**
     * @param list
     * @return true:is empty
     */
    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    //    本地验证手机号
    public static boolean isPhoneNum ( Activity activity, String userName )
    {
        if(TextUtils.isEmpty(userName) || !Pattern.matches("1[3|4|5|6|7|8|9][0-9]{9}",userName)){
            toast(activity, "手机号码填写不正确");
            return false;
        }
        return true;
    }




}
