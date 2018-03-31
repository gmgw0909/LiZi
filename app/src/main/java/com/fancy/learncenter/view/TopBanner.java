package com.fancy.learncenter.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.fancy.lizi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyy on 2017/2/4.
 */

public class TopBanner<T> extends RelativeLayout {
    Context context;
    /**
     * 网络图片资源集合
     */
    List<T> resData;
    /**
     * 小圆点集合
     */
    List<View> pointDatas;

    /**
     * 圆点距离底部的距离
     */
    int maginBottom = 0;

    /**
     * 是否无限循环 默认不无限
     */
    boolean isCycle;
    /**
     * 自动滑动间隔时间
     */
    int autoPlayDuration = 3000;
    /**
     * handel发送消息标识（任意值）
     */
    int WHAT_AUTO_PLAY = 100;
    /**
     * ViewPage条目集合
     */
    List<View> vpView;
    /**
     * ViewPager适配器
     */
    ViewPagerAdapter pagerAdapter;
    /**
     * 小圆点集合
     */
    LinearLayout linearLayout;

    /**
     * 是否显示小圆点
     * 默认没有小圆点
     */
    boolean isPoint;

    /**
     * 是否自动轮播
     * 默认不轮播
     */
    boolean isAutoPlay;

    /**
     * 小圆点初始资源文件
     * * DrawableRes
     */
    int pointGg;
    /**
     * 小圆点选中时的资源文件
     */
    int pointPressedGg;

    /**
     * 小园点点整体位置
     */
    int location;

    ViewPager viewPager;


    public TopBanner(Context context) {
        this(context, null);
    }

    public TopBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBanner);
        isPoint = typedArray.getBoolean(R.styleable.TopBanner_isPoint, false);
        isAutoPlay = typedArray.getBoolean(R.styleable.TopBanner_autoPlay, false);
//        pointGg = typedArray.getResourceId(R.styleable.TopBanner_pointBg, R.drawable.point);
//
//        pointPressedGg = typedArray.getResourceId(R.styleable.TopBanner_pointPressedBg, R.drawable.point_pressed);


        isCycle = typedArray.getBoolean(R.styleable.TopBanner_isCycle, false);
        maginBottom = typedArray.getInteger(R.styleable.TopBanner_maginBottom, 50);

        location = typedArray.getInteger(R.styleable.TopBanner_location, RelativeLayout.CENTER_HORIZONTAL);

        typedArray.recycle();
    }


    private void initView(Context context) {
        removeAllViews();
        viewPager = new ViewPager(context);
        vpView = new ArrayList<>();
        resData = new ArrayList();
        pagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (vpView.size() != 0) {
                    int newPosition = position % vpView.size();
                    selectedPoint(newPosition);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        LayoutParams viewPagerParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        addView(viewPager, viewPagerParams);

        linearLayout = new LinearLayout(context);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(50, 50, 50, maginBottom);
        params.addRule(location);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView(linearLayout, params);
    }


    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (isCycle) {
                return Integer.MAX_VALUE;
            } else {
                return resData.size();
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (vpView.size() > 0) {
                int newPosition;
                if (isCycle) {
                    newPosition = position % vpView.size();
                } else {
                    newPosition = position;
                }

                View view = vpView.get(newPosition);
                if (container.equals(view.getParent())) {
                    container.removeView(view);
                }

                if (bindViewCallBack != null) {
                    bindViewCallBack.bindView(view, newPosition, resData.get(newPosition));
                }

                container.addView(view);
                return view;
            }
            return null;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }
    }


    /**
     * 添加滑动条目资源
     *
     * @param resData 网络图片资源
     */
    public void setResData(List resData, int resID) {

        if (resData.size() == 0) {
            return;
        }
        initView(context);
        this.resData = resData;
        pointDatas = new ArrayList<>();


        for (int i = 0; i < resData.size(); i++) {
            vpView.add(LayoutInflater.from(context).inflate(resID, null));
            addPoint();
        }
        if (vpView.size() == 1) {

            resData.add(resData.get(0));
            resData.add(resData.get(0));

            vpView.add(LayoutInflater.from(context).inflate(resID, null));
            vpView.add(LayoutInflater.from(context).inflate(resID, null));
        } else if (vpView.size() == 2) {
            resData.add(resData.get(0));
            resData.add(resData.get(1));

            vpView.add(LayoutInflater.from(context).inflate(resID, null));
            vpView.add(LayoutInflater.from(context).inflate(resID, null));
        }


        pagerAdapter.notifyDataSetChanged();

        if (isCycle) {
            int startIndex = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % vpView.size());
            viewPager.setCurrentItem(startIndex);
        }


        selectedPoint(0);
        startAutoPlay();
    }


    private void addPoint() {
        if (!isPoint) {
            return;
        }
        View dot = new View(getContext());
        dot.setBackgroundResource(pointGg);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
        params.setMargins(10, 0, 0, 0);
        dot.setLayoutParams(params);
        dot.setEnabled(false);
        pointDatas.add(dot);
        linearLayout.addView(dot);
    }

    private void selectedPoint(int dex) {
        if (!isPoint) {
            return;
        }

        for (int i = 0; i < pointDatas.size(); i++) {
            pointDatas.get(i).setBackgroundResource(pointGg);
        }

        if (pointDatas.size() == 1) {
            pointDatas.get(0).setBackgroundResource(pointPressedGg);
        } else if (pointDatas.size() == 2) {
            pointDatas.get(dex % 2 == 0 ? 0 : 1).setBackgroundResource(pointPressedGg);
        } else {
            pointDatas.get(dex).setBackgroundResource(pointPressedGg);

        }

    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == WHAT_AUTO_PLAY) {
                if (viewPager != null) {
                    if (resData.size() > 0) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                        startAutoPlay();
                    }
                }
            }

            return false;
        }
    });

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);

        if (visibility == VISIBLE) {
            startAutoPlay();
        } else {
            stopAutoPlay();
        }
    }

    /**
     * 停止自动轮播
     */
    public void stopAutoPlay() {
        if (isAutoPlay) {
            handler.removeMessages(WHAT_AUTO_PLAY);
        }
    }

    /**
     * 开始自动轮播
     */
    public void startAutoPlay() {
        if (isAutoPlay) {
            stopAutoPlay();
            handler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, autoPlayDuration);
        }

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stopAutoPlay();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                startAutoPlay();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    BindViewCallBack<T> bindViewCallBack;

    public void setBindViewCallBack(BindViewCallBack<T> bindViewCallBack) {
        this.bindViewCallBack = bindViewCallBack;
    }

    public interface BindViewCallBack<T> {
        abstract void bindView(View rootView, int position, T itemData);
    }

    public List getDataBean() {
        return resData;
    }

    public int px2dip(float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
