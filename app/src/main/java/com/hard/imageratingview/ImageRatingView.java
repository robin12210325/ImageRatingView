package com.hard.imageratingview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by 1 on 2016/12/25.
 */

public class ImageRatingView extends View {

    public ImageRatingView(Context context) {
        super(context);
    }

    public ImageRatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void test(String s){
        Log.d("tagg",s);
    }
}
