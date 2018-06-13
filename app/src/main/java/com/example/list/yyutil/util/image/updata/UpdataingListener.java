package com.example.list.yyutil.util.image.updata;

import com.example.list.yyutil.model.MyBaseModel;

/**
 * Created by yy on 2018/5/17.
 */

abstract public class UpdataingListener extends MyBaseModel {

    public final int WAIT = 1;
    public final int START = 2;
    public final int STOP = 3;
    public static int OK = 4;
    public final int ERROR = 5;

    public int status=WAIT;
//  重试
    public int count = 0;
    public final int maxCount = 3;

    public void setSTART ()
    {
        status = START;
    }

    public void setOK()
    {
        status = OK;
    }

    public void setWAIT()
    {
        status = WAIT;
    }

    public void setSTOP()
    {
        status = STOP;
    }

    public void setERROR ()
    {
        status = ERROR;
    }

    public abstract byte[] getImageBytes();

    public abstract String getImageInfoString();

    public abstract void setImageUrl(String url);
}
