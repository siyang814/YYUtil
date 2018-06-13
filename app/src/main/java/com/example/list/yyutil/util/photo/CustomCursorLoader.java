package com.example.list.yyutil.util.photo;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

/**
 * Created by yy on 2018/6/12.
 */

public class CustomCursorLoader  extends CursorLoader {

    public CustomCursorLoader(Context context) {
        super(context);
    }

    public CustomCursorLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected void onStopLoading() {
    }

}
