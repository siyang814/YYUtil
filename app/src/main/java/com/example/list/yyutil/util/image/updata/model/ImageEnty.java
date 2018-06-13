package com.example.list.yyutil.util.image.updata.model;

import android.text.TextUtils;

import com.example.list.yyutil.util.image.ImageUtil;
import com.example.list.yyutil.util.image.updata.EntyListener;
import com.example.list.yyutil.util.image.updata.UpdataingListener;

/**
 * Created by yy on 2018/6/11.
 */

public class ImageEnty extends UpdataingListener
{

    public String url;
    private EntyListener listener;

    private String imgPath = "";
    private String imgName;

    private String imageUrl;


    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgName() {
        if ( TextUtils.isEmpty(imgPath))
        {
            return  "";
        }
        else
        {
            return imgPath.substring(imgPath.lastIndexOf(".", imgPath.length()));
        }
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public void setSTART() {
        super.setSTART();
        if ( listener != null )
            listener.start(this);
    }

    @Override
    public void setOK() {
        super.setOK();
        if ( listener != null )
            listener.sendOK(this);
    }

    @Override
    public void setWAIT() {
        super.setWAIT();
    }

    @Override
    public void setSTOP() {
        super.setSTOP();
    }

    @Override
    public void setERROR() {
        super.setERROR();
        count++;
        if ( listener != null )
            listener.sendError(this);
    }

    @Override
    public byte[] getImageBytes() {
        return null;
    }

    @Override
    public String getImageInfoString() {
        if ( TextUtils.isEmpty(imgPath)) return null;
        return ImageUtil.getUpdataImage(imgPath);
    }

    public void setListener(EntyListener listener) {
        this.listener = listener;
    }
}

