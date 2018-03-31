package com.fancy.learncenter.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andview.refreshview.callback.IHeaderCallBack;
import com.andview.refreshview.utils.Utils;
import com.fancy.lizi.R;

import java.util.Calendar;

/**
 * Created by Hyy on 2016/10/8.
 */
public class CustomHeaderView extends LinearLayout implements IHeaderCallBack {
    private ViewGroup mContent;
    //    private ImageView mArrowImageView;
    private ProgressBar mProgressBar;
    private TextView mHintTextView;
    private TextView mHeaderTimeTextView;
//    private final int ROTATE_ANIM_DURATION = 180;

    public CustomHeaderView(Context context) {
        super(context);
        initView(context);
    }

    public CustomHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContent = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.custom_headerview, this);
//        mArrowImageView = (ImageView) findViewById(R.id.xrefreshview_header_arrow);
//		mOkImageView = (ImageView) findViewById(R.id.xrefreshview_header_ok);
        mHintTextView = (TextView) findViewById(R.id.xrefreshview_header_hint_textview);
        mHeaderTimeTextView = (TextView) findViewById(R.id.xrefreshview_header_time);
        mProgressBar = (ProgressBar) findViewById(R.id.xrefreshview_header_progressbar);
    }

    public void setRefreshTime(long lastRefreshTime) {
        if (true) {
            return;
        }

        // 获取当前时间
        Calendar mCalendar = Calendar.getInstance();
        long refreshTime = mCalendar.getTimeInMillis();
        long howLong = refreshTime - lastRefreshTime;
        int minutes = (int) (howLong / 1000 / 60);
        String refreshTimeText = "";
        Resources resources = getContext().getResources();
        if (minutes < 1) {
            refreshTimeText = resources
                    .getString(com.andview.refreshview.R.string.xrefreshview_refresh_justnow);
        } else if (minutes < 60) {
            refreshTimeText = resources
                    .getString(com.andview.refreshview.R.string.xrefreshview_refresh_minutes_ago);
            refreshTimeText = Utils.format(refreshTimeText, minutes);
        } else if (minutes < 60 * 24) {
            refreshTimeText = resources
                    .getString(com.andview.refreshview.R.string.xrefreshview_refresh_hours_ago);
            refreshTimeText = Utils.format(refreshTimeText, minutes / 60);
        } else {
            refreshTimeText = resources
                    .getString(com.andview.refreshview.R.string.xrefreshview_refresh_days_ago);
            refreshTimeText = Utils.format(refreshTimeText, minutes / 60 / 24);
        }
        mHeaderTimeTextView.setText(refreshTimeText);
    }


    /**
     * hide footer when disable pull load more
     */
    public void hide() {
        setVisibility(View.GONE);
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    @Override
    public void onStateNormal() {
        mProgressBar.setVisibility(View.GONE);
//        mArrowImageView.setVisibility(View.VISIBLE);
        mHintTextView.setText("下拉刷新");
    }

    @Override
    public void onStateReady() {
        mProgressBar.setVisibility(View.VISIBLE);
//        mArrowImageView.setVisibility(View.GONE);
        mHintTextView.setText("松开刷新");
    }

    @Override
    public void onStateRefreshing() {
        mProgressBar.setVisibility(View.VISIBLE);
//        mArrowImageView.setVisibility(View.GONE);
        mHintTextView.setText("正在刷新");
    }

    @Override
    public void onStateFinish() {
        mProgressBar.setVisibility(View.GONE);
//        mArrowImageView.setVisibility(View.VISIBLE);
        mHintTextView.setText("加载完成");
    }

    @Override
    public void onHeaderMove(double offset, int offsetY, int deltaY) {

    }

    @Override
    public int getHeaderHeight() {
        return getMeasuredHeight();
    }
}

