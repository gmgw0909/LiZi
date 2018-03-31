package com.fancy.learncenter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.callback.IFooterCallBack;
import com.fancy.lizi.R;

/**
 * Created by Hyy on 2016/10/8.
 */
public class CustomFootView extends LinearLayout implements IFooterCallBack {
    private Context mContext;

    //    private View mContentView;
    private View mProgressBar;
    private TextView mHintView;
    //    private TextView mClickView;
    private boolean showing = true;

    public CustomFootView(Context context) {
        super(context);
        initView(context);
    }

    public CustomFootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @Override
    public void callWhenNotAutoLoadMore(final XRefreshView.XRefreshViewListener listener) {
//        mClickView.setText(com.andview.refreshview.R.string.xrefreshview_footer_hint_click);
//        mClickView.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.onLoadMore(false);
//                    onStateRefreshing();
//                }
//            }
//        });
    }

    /**
     * 正常状态，例如需要点击footerview才能加载更多，主要是到达底部不自动加载更多时会被调用
     */
    @Override
    public void onStateReady() {
        mHintView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
//        mClickView.setVisibility(View.VISIBLE);
        show(true);
    }

    /**
     * 正在刷新
     */
    @Override
    public void onStateRefreshing() {
        mHintView.setVisibility(View.VISIBLE);
        mHintView.setText("正在加载");
        mProgressBar.setVisibility(View.VISIBLE);
//        mClickView.setVisibility(View.GONE);
        show(true);
    }

    /**
     * 刷新结束 在此方法中不要调用show()方法
     *
     * @param hideFooter footerview是否被隐藏
     */
    @Override
    public void onStateFinish(boolean hideFooter) {
        if (hideFooter) {
            mHintView.setText("加载完成");
        } else {
            //处理数据加载失败时ui显示的逻辑，也可以不处理，看自己的需求
            mHintView.setText("加载完成");
        }
        mHintView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
//        mClickView.setVisibility(View.GONE);
    }

    /**
     * 已无更多数据 在此方法中不要调用show()方法
     */
    @Override
    public void onStateComplete() {
        mHintView.setText("暂未更多数据");
        mHintView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
//        mClickView.setVisibility(View.GONE);
    }

    /**
     * 设置显示或者隐藏footerview 不要在onStateFinish和onStateComplete中调用此方法
     *
     * @param show
     */
    @Override
    public void show(final boolean show) {
        if (show == showing) {
            return;
        }
        showing = show;
//        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView
//                .getLayoutParams();
//        lp.height = show ? LayoutParams.WRAP_CONTENT : 0;
//        mContentView.setLayoutParams(lp);
//        setVisibility(show?VISIBLE:GONE);

    }

    /**
     * footerview是否显示中
     *
     * @return
     */
    @Override
    public boolean isShowing() {
        return showing;
    }

    private void initView(Context context) {
        mContext = context;
        ViewGroup moreView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.custom_footview, this);
        moreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

//        mContentView = moreView.findViewById(com.andview.refreshview.R.id.xrefreshview_footer_content);
        mProgressBar = moreView
                .findViewById(R.id.xrefreshview_foot_progressbar);
        mHintView = (TextView) moreView
                .findViewById(R.id.xrefreshview_foot_hint_textview);
//        mClickView = (TextView) moreView
//                .findViewById(com.andview.refreshview.R.id.xrefreshview_footer_click_textview);
    }

    /**
     * 获得footerview的高度
     *
     * @return
     */
    @Override
    public int getFooterHeight() {
        return getMeasuredHeight();
    }
}
