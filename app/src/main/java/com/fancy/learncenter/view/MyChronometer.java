package com.fancy.learncenter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Chronometer;

/**
 * Created by LeeBoo on 2018/2/2.
 */

public class MyChronometer extends Chronometer {

    public MyChronometer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyChronometer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyChronometer(Context context) {
        super(context);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        //continue when view is hidden
        visibility = View.VISIBLE;
        super.onWindowVisibilityChanged(visibility);
    }
}
