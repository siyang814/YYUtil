package com.example.list.yyutil.util.updata;

import java.io.File;

/**
 * Created by yy on 2018/6/12.
 */

public interface DonwloadResponseListener {

    /**
     * @param bytesRead     已下载字节数
     * @param contentLength 总字节数
     * @param done          是否下载完成
     *  计算方式是 (100 * bytesRead) / contentLength
     *  日志为 45%...
     */
    void OnSuccess(long bytesRead, long contentLength, boolean done);

    /**
     * 下载失败的回调方法
     */
    void onfailure();

    /**
     * 这是一个下载成功之后,返回文件路径的方法
     *
     * @param file
     */
    void onSuccess(File file);
}