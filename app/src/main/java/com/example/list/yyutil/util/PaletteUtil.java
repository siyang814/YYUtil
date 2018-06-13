package com.example.list.yyutil.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.graphics.Palette;

/**
 * Created by yy on 2017/9/19.
 */

public class PaletteUtil {


    private PaletteListener listener;

    public void setListener(PaletteListener listener) {
        this.listener = listener;
    }

    public PaletteUtil() {

    }


    public void getPalette (final Bitmap bitmap, final PaletteListener listener)
    {
        this.listener = listener;
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener()
        {


            /**
             * Called when the {@link Palette} has been generated.
             *
             * @param palette
             */
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch = palette.getMutedSwatch();
                if ( swatch != null )
                {
                    Bitmap paletteBitmap = getBitmap(swatch.getRgb());
                    if ( paletteBitmap != null || listener != null )
                    {
                        listener.HandleBitmap(paletteBitmap);
                    }

                }
//                没有取到颜色， 默认黑色
                else
                {
                    Bitmap paletteBitmap = getBitmap(Color.parseColor("#333333"));
                    if ( paletteBitmap != null || listener != null )
                    {
                        listener.HandleBitmap(paletteBitmap);
                    }
                }
            }
        });
    }

    private Bitmap getBitmap ( int rgb )
    {
        Bitmap bitmap = Bitmap.createBitmap(1,1,Bitmap.Config.RGB_565);
        bitmap.eraseColor(rgb);
        return bitmap;
    }


    public interface PaletteListener
    {
        public void HandleBitmap ( Bitmap bitmap);
    }
}
