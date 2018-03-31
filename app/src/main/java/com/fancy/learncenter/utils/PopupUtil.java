package com.fancy.learncenter.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fancy.learncenter.common.MyApplication;
import com.fancy.lizi.R;

/**
 * Created by Hyy on 2016/9/26.
 */
public class PopupUtil {


    View locationView;

    public PopupUtil(View locationView) {

        this.locationView = locationView;
        popopView = LayoutInflater.from(MyApplication.context).inflate(R.layout.pupopwinodw_choose_pic, null);

        initPopupWionw();
    }

    PopupWindow popupWindow;

    View popopView;

    private void initPopupWionw() {
        popupWindow = new PopupWindow();

        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true);

        popopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(popopView);
    }

    public void isShow() {
//        popopView.startAnimation(getTranslateAnimation(200, 0, 300));
        popupWindow.showAtLocation(locationView, Gravity.BOTTOM, 0, 0);
    }


    public void setOnClickListener() {
        TextView cance = (TextView) popopView.findViewById(R.id.cancel);
        cance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        TextView choose = (TextView) popopView.findViewById(R.id.choose);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("选择相册");
            }
        });
        TextView camera = (TextView) popopView.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("拍照");
            }
        });
        TextView see = (TextView) popopView.findViewById(R.id.see);
        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("查看大图");
            }
        });
    }

    /**
     * 生成TranslateAnimation
     *
     * @param durationMillis 动画显示时间
     * @param start          初始位置
     */
    protected Animation getTranslateAnimation(int start, int end, int durationMillis) {
        Animation translateAnimation = new TranslateAnimation(0, 0, start, end);
        translateAnimation.setDuration(durationMillis);
        translateAnimation.setFillEnabled(true);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }
}
