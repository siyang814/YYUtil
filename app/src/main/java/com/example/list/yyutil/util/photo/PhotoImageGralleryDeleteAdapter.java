package com.example.list.yyutil.util.photo;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.list.yyutil.util.image.GlideUtil;

import java.util.List;

/**
 * Created by zhangjie on 11/22/15.
 */
public class PhotoImageGralleryDeleteAdapter extends PagerAdapter {

    private Context mContext;

    // 所要显示的图片的数组
    List<String> photos;



    public static interface LSImageGralleryListner {
        void onClickPageView(View v);
    }

    public LSImageGralleryListner lsImageGralleryListner;

    // 构造方法
    public PhotoImageGralleryDeleteAdapter(Context mContext, List<String> photos) {
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

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        if ( position == 0 )
        {
            ImageView imageView = new ImageView(mContext);
            collection.addView(imageView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            return imageView;
        }

        final TouchImageView iv = new TouchImageView(mContext);
//        final ImageView iv = new ImageView(mContext);
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//                iv.setScaleType(ImageView.ScaleType.MATRIX);

        collection.addView(iv, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        GlideUtil.getInstance().getGifBannerImage(((Activity) mContext), photos.get(position - 1), iv);
        if ( position == 1 )
        {
            iv.setOnTouchListener(new MyTouchListener(iv));
        }
        else
        {
            iv.setOnTouchListener(null);
        }

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
        return photos.size() + 1;
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


    class MyTouchListener implements View.OnTouchListener
    {

        private float downX;

        private ImageView imageView;

        public MyTouchListener(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    downX = motionEvent.getX();
                    imageView.getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    float moveX = motionEvent.getX();
                    if ( moveX >= downX )
                    {
                        imageView.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    else
                    {
                        imageView.getParent().requestDisallowInterceptTouchEvent(false);
                    }

                    downX = motionEvent.getX();

                    break;
                case MotionEvent.ACTION_UP:
                    imageView.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return false;
        }
    }





















}