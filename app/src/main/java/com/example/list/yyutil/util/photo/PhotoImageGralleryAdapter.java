package com.example.list.yyutil.util.photo;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.list.yyutil.util.image.GlideUtil;

import java.util.List;

/**
 * Created by zhangjie on 11/22/15.
 */
public class PhotoImageGralleryAdapter extends PagerAdapter {

    private Context mContext;

    // 所要显示的图片的数组
    List<String> photos;



    public static interface LSImageGralleryListner {
        void onClickPageView(View v);
    }

    public LSImageGralleryListner lsImageGralleryListner;

    // 构造方法
    public PhotoImageGralleryAdapter(Context mContext, List<String> photos) {
        super();
        this.mContext = mContext;
        this.photos = photos;


    }

    public void setList (List<String> photos)
    {
        this.photos = photos;
        notifyDataSetChanged();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    private int width;
    private ViewTreeObserver viewTreeObserver;
    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        final TouchImageView iv = new TouchImageView(mContext);
//        final ImageView iv = new ImageView(mContext);
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//                iv.setScaleType(ImageView.ScaleType.MATRIX);

        collection.addView(iv, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        GlideUtil.getInstance().getGifBannerImage(((Activity) mContext), photos.get(position), iv);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lsImageGralleryListner != null) {
                    lsImageGralleryListner.onClickPageView(v);
                }
            }
        });


        return iv;
    }


    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        if (photos == null) {
            return 0;
        }
        return photos.size();
    }

    private int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}