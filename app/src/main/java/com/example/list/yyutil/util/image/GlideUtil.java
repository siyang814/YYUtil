package com.example.list.yyutil.util.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.list.yyutil.util.Common;

/**
 * Created by yy on 16/7/26.
 */
public class GlideUtil {

    private static GlideUtil instance;

    public static GlideUtil getInstance ()
    {
        if ( instance == null ) instance = new GlideUtil();
        return instance;
    }

    /**     获取图片， 根据URL 判断是否为gif图片
     *      白色背景、有动画、计算宽高
     * @param activity
     * @param url
     * @param img
     * @param imgDot
     */
    public void getBigAnimeWidthGifImage (Activity activity, String url, ImageView img, ImageView imgDot)
    {
        if (TextUtils.isEmpty(url))
        {
            url = "";
        }
        if ( url.contains(".gif"))
        {
            getWithWhiteAnimationAndWidthGif(activity, url, img, imgDot);
        }
        else
        {
            getWithWhiteAnimationAndWidth(activity, url, img, imgDot);
        }
    }

    /***
     *      获取图片， 根据URL 判断是否为gif图片
     * @param mContext
     * @param url
     * @param img
     */
    public void getGifImageRound(Activity mContext, String url, ImageView img )
    {
        if (TextUtils.isEmpty(url))
        {
            url = "";
        }
        if ( url.contains(".gif"))
        {
            getListGif(mContext, url, img);
        }
        else
        {
            getListImageBG(mContext, url, img);
        }

    }
    //
    public void getGifBannerImage (Activity mContext, String url, ImageView img)
    {
        Glide.with(mContext)
                .load(url)
//                .placeholder(R.drawable.club_topic_default)
//                .error(R.drawable.club_topic_default)
//                .fallback(R.drawable.club_topic_default)
                .crossFade()
                .into(img);
    }

    /**加载本地gif 图*/
    public void getGifNative (Activity activity, int i, ImageView imageView )
    {
        Glide.with(activity).load(i).asGif().into(imageView);
    }

    private void getListGif (final Activity mContext, final String url, final ImageView img)
    {
        Glide.with(mContext)
                .load(url)
                .transform(new GlideRoundTransform(mContext,3))
//                .placeholder(R.drawable.list_item_bg)
//                .error(R.drawable.list_item_bg)
//                .fallback(R.drawable.list_item_bg)
                .crossFade()
                .into(img);
    }


    /**
     *          用户头像
     * @param activity
     * @param url
     * @param img
     */
    public void getHeadImageView (Activity activity, String url, ImageView img )
    {
        Glide.with(activity)
                .load(url)
//                .placeholder(R.drawable.ls_nologin_header_icon)
//                .error(R.drawable.ls_nologin_header_icon)
//                .fallback(R.drawable.ls_nologin_header_icon)
                .crossFade()
                .into(img);
    }

    private AnimationDrawable animationDrawable;
    /**
     *      有默认加载动画的
     * @param imgDot
     * @param img
     * @return
     */
    public void getWithWhiteBG (Activity activity, String url, ImageView img, final ImageView imgDot )
    {

        Glide.with(activity)
                .load(url)
                .asBitmap()
//                .placeholder(R.drawable.club_topic_default)
//                .error(R.drawable.club_topic_default)
//                .fallback(R.drawable.club_topic_default)
                .into( new GlideLoadingAnimation(img, imgDot));
    }


    /**
     *      有默认加载动画的
     * @param imgDot
     * @param img
     * @param callBack      提供callBack返回 bitmap
     * @return
     */
    public void getWithWhiteBG (Activity activity, String url, ImageView img, final ImageView imgDot, CallBack callBack )
    {

        Glide.with(activity)
                .load(url)
                .asBitmap()
//                .placeholder(R.drawable.club_topic_default)
//                .error(R.drawable.club_topic_default)
//                .fallback(R.drawable.club_topic_default)
                .into( new GlideLoadingAnimation(img, imgDot, callBack));
    }

    public static class GlideLoadingAnimation extends SimpleTarget<Bitmap> {
        //3个点， 背影（展示的图片）
        private ImageView imgDot, imgRes;
        private AnimationDrawable animationDrawable;
        private CallBack callBack;

        public GlideLoadingAnimation(ImageView imgRes, ImageView imgDot) {
            this.imgRes = imgRes;
            this.imgDot = imgDot;
            if ( this.imgDot != null )
            {
                this.imgDot.setVisibility(View.VISIBLE);
                animationDrawable = (AnimationDrawable)imgDot.getDrawable();
                animationDrawable.start();
            }

        }

        public GlideLoadingAnimation(ImageView imgRes, ImageView imgDot, CallBack callBack) {

            this.imgRes = imgRes;
            this.imgDot = imgDot;
            this.callBack = callBack;
            if ( this.imgDot != null )
            {
                this.imgDot.setVisibility(View.VISIBLE);
                animationDrawable = (AnimationDrawable)imgDot.getDrawable();
                animationDrawable.start();
            }

        }

        /**
         * The method that will be called when the resource load has finished.
         *
         * @param resource       the loaded resource.
         * @param glideAnimation
         */
        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            if ( this.imgDot != null )
            this.imgDot.setVisibility(View.GONE);
            if ( animationDrawable != null )
            {
                animationDrawable.stop();
                animationDrawable = null;
            }
            this.imgRes.setImageBitmap(resource);
            if ( this.callBack != null ) callBack.handler(resource);
        }
    }

    /**
     *      有默认加载动画的
     * @param img
     * @return
     */
    public void getDefualt (Activity activity, String url, ImageView img )
    {

        Glide.with(activity)
                .load(url)
                .crossFade()
                .into(img);
    }

    /**
     *      列表大图，带有默认背景灰色
     * @param img
     * @return
     */
    public void getListImageBG (Activity activity, String url, ImageView img )
    {


                Glide.with(activity)
                .load(url)
                .transform(new GlideRoundTransform(activity,3))
//                .placeholder(R.drawable.list_item_bg)
//                .error(R.drawable.list_item_bg)
//                .fallback(R.drawable.list_item_bg)
                .crossFade()
                .into(img);
    }

    /**
     *      有默认加载动画的
     * @param img
     * @return
     */
    public void getWithWidth (Activity activity, String url, ImageView img )
    {

        Glide.with(activity)
                .load(url)
                .asBitmap()
                .into( new GlideLoadingAnimation1(img));
    }

    public static class GlideLoadingAnimation1 extends SimpleTarget<Bitmap> {
//         背影（展示的图片）
        private ImageView imgRes;
        private AnimationDrawable animationDrawable;

        public GlideLoadingAnimation1(ImageView imgRes) {
            this.imgRes = imgRes;
        }

        /**
         * The method that will be called when the resource load has finished.
         *
         * @param resource       the loaded resource.
         * @param glideAnimation
         */
        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            int ImageWidth = imgRes.getWidth();
            int w = resource.getWidth();
            int h = resource.getHeight();
            int imgh = ImageWidth * h / w;
            android.view.ViewGroup.LayoutParams l = imgRes.getLayoutParams();
            l.height = imgh;
            this.imgRes.setImageBitmap(resource);
        }
    }



    /**
     *      有默认加载动画的, 根据图片宽度，原图比例自适应高度
     * @param imgDot
     * @param img
     * @return
     */
    private void getWithWhiteAnimationAndWidthGif ( Activity activity, String url, ImageView img, final ImageView imgDot )
    {

        Glide.with(activity)
                .load(url)
                .asGif()
                .into( new GlideLoadingAnimationAndWidthGif(img, imgDot));
//                .into( new GlideLoadingAnimationAndWidthGif(img, imgDot));
    }

    /**
     *      有默认加载动画的, 根据图片宽度，原图比例自适应高度
     * @param imgDot
     * @param img
     * @return
     */
    public void getWithWhiteAnimationAndWidth ( Activity activity, String url, ImageView img, final ImageView imgDot )
    {

        Glide.with(activity)
                .load(url)
                .asBitmap()
                .into( new GlideLoadingAnimationAndWidth(img, imgDot));
    }

    public static class GlideLoadingAnimationAndWidth extends SimpleTarget<Bitmap> {
        //3个点， 背影（展示的图片）
        private ImageView imgDot, imgRes;
        private AnimationDrawable animationDrawable;

        public GlideLoadingAnimationAndWidth(ImageView imgRes, ImageView imgDot) {
            this.imgRes = imgRes;
            this.imgDot = imgDot;

            if ( this.imgDot != null )
            {
                this.imgDot.setVisibility(View.VISIBLE);
                animationDrawable = (AnimationDrawable)imgDot.getDrawable();
                animationDrawable.start();
            }

        }

        /**
         * The method that will be called when the resource load has finished.
         *
         * @param resource       the loaded resource.
         * @param glideAnimation
         */
        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            if ( this.imgDot != null )
            this.imgDot.setVisibility(View.GONE);
            if ( animationDrawable != null )
            {
                animationDrawable.stop();
                animationDrawable = null;
            }

            int ImageWidth = Common.WIDTH;
            int w = resource.getWidth();
            int h = resource.getHeight();
            int imgh = ImageWidth * h / w;
            android.view.ViewGroup.LayoutParams l = imgRes.getLayoutParams();
            l.height = imgh;
            this.imgRes.setImageBitmap(resource);
//            if (this.adapter!=null) {
//                adapter.notifyDataSetChanged();
//            }
        }
    }

    public static class GlideLoadingAnimationAndWidthGif extends ImageViewTarget<GifDrawable> {
        //3个点， 背影（展示的图片）
        private ImageView imgDot, imgRes;
        private AnimationDrawable animationDrawable;

        public GlideLoadingAnimationAndWidthGif(ImageView imgRes, ImageView imgDot) {
            super(imgRes);
            this.imgRes = imgRes;
            this.imgDot = imgDot;
            if ( this.imgDot != null )
            {
                this.imgDot.setVisibility(View.VISIBLE);
                animationDrawable = (AnimationDrawable)imgDot.getDrawable();
                animationDrawable.start();
            }

        }

        @Override
        public void onResourceReady(GifDrawable resource, GlideAnimation<? super GifDrawable>
                glideAnimation) {
            super.onResourceReady(resource, glideAnimation);
            if ( this.imgDot != null )
            this.imgDot.setVisibility(View.GONE);
            if ( animationDrawable != null )
            {
                animationDrawable.stop();
                animationDrawable = null;
            }

            int ImageWidth = Common.WIDTH;
            int w = resource.getIntrinsicWidth();
            int h = resource.getIntrinsicHeight();
            int imgh = ImageWidth * h / w;
            android.view.ViewGroup.LayoutParams l = imgRes.getLayoutParams();
            l.height = imgh;
            imgRes.setLayoutParams(l);
            imgRes.setImageDrawable(resource);
            resource.start();
        }

        @Override
        protected void setResource(GifDrawable gifDrawable) {

        }
    }

    public static interface CallBack
    {
        public void handler (Bitmap bitmap);
    }


}
