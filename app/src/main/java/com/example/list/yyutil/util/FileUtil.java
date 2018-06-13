package com.example.list.yyutil.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yy on 16/4/20.
 */
public class FileUtil {

    /**
     *  主目录
     */
    public static String filePath = "";
    /**
     *      数据库目录
     */
    public static String cachePath = "";
/*图片目录*/
    public static String imgPath = "";
    public static String picturePath = "";

    /*错误日志目录*/
    public static String crashPath = "";
    /*草稿箱图片目录*/
    public static String dbImgPath = "";


    public static void setFilePath (Context context)
    {
        filePath = getPath(
                context.getApplicationContext(), "lishi99").getPath();

        cachePath = context.getFilesDir() + "/cache/";

        imgPath = filePath + "/image/";
        picturePath = filePath + "/picture/";
        crashPath = filePath + "/crash/";

        dbImgPath = context.getFilesDir() + "/lishi99/";

        File file = new File(filePath);
        if ( !file.exists())
        {
            file.mkdirs();
        }

        file = new File(cachePath);
        if ( !file.exists())
        {
            file.mkdirs();
        }

        file = new File(imgPath);
        if ( !file.exists())
        {
            file.mkdirs();
        }

        file = new File(picturePath);
        if ( !file.exists())
        {
            file.mkdirs();
        }

        file = new File(crashPath);
        if ( !file.exists())
        {
            file.mkdirs();
        }

        file = new File(dbImgPath);
        if ( !file.exists())
        {
            file.mkdirs();
        }

    }





    /**
     *      获取文件的byte[]
     * @param fileName
     * @return
     */
    public static String readFileToString(String fileName) {
        String result = "";
        File file = new File(fileName);
        InputStream in = null;
        try {
            // 一次读一个字节
            in = new FileInputStream(file);
            int tempbyte;
            byte[] b = new byte[in.available()];
            while ((tempbyte = in.read(b)) != -1) {
//                System.out.write(tempbyte);
            }
            in.close();


            result = Base64.encode(b);
            return result;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *      获取文件的byte[]
     * @param fileName
     * @return
     */
    public static byte[] readFileByBytess(String fileName) {
//        String result = "";
        File file = new File(fileName);
        InputStream in = null;
        try {
            // 一次读一个字节
            in = new FileInputStream(file);
            int tempbyte;
            byte[] b = new byte[in.available()];
            while ((tempbyte = in.read(b)) != -1) {
//                System.out.write(tempbyte);
            }
            in.close();


//            result = Base64.encode(b);

            return b;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File getPath(Context context, String cacheDir)
    {
        File appCacheDir = null;
        if("mounted".equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
        }

        if(appCacheDir == null || !appCacheDir.exists() && !appCacheDir.mkdirs()) {
            appCacheDir = context.getCacheDir();
        }

        return appCacheDir;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return perm == 0;
    }

}
