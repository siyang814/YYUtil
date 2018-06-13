package com.example.list.yyutil.util.updata;

import com.example.list.yyutil.model.MyBaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yy on 2018/6/11.
 */

public class UpdataModel extends MyBaseModel {

    public String filePath;
    public String appName = "/lis99.apk";
    @SerializedName("url")
    public String url;
    @SerializedName("changelog")
    public String changelog;
    @SerializedName("version")
    public String version;

    /**
     *      0， 没有更新， 1 有更新， 2强制更新
     */
    @SerializedName("type")
    public int type;


}
