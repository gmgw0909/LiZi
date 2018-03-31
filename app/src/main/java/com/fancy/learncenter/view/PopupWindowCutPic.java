package com.fancy.learncenter.view;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fancy.learncenter.common.MyApplication;
import com.fancy.learncenter.utils.ToastUtil;
import com.fancy.lizi.R;
import com.jph.takephoto.app.TakePhoto;

/**
 * Created by Hyy on 2016/11/4.
 */
public class PopupWindowCutPic extends PopupWindow {

    View locationView;


    public PopupWindowCutPic(View locationView) {

        this.locationView = locationView;
        popopView = LayoutInflater.from(MyApplication.context).inflate(R.layout.pupopwinodw_choose_pic, null);

        initPopupWionw();
    }

    View popopView;

    private void initPopupWionw() {
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        this.setOutsideTouchable(true);

        popopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindowCutPic.this.dismiss();
            }
        });
        this.update();

        this.setContentView(popopView);
    }

    public void isShow() {
        this.showAtLocation(locationView, Gravity.BOTTOM, 0, 0);
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

    public void setOnClickListener(final CutPicCallBack cutPicCallBack) {
        TextView cancel = (TextView) popopView.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                popupWindow.dismiss();
                cutPicCallBack.cancel();
            }
        });
        TextView choose = (TextView) popopView.findViewById(R.id.choose);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtil.show("选择相册");
                cutPicCallBack.choose();
            }
        });
        TextView camera = (TextView) popopView.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtil.show("拍照");
                cutPicCallBack.camera();
            }
        });
        TextView see = (TextView) popopView.findViewById(R.id.see);
        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtil.show("查看大图");
                cutPicCallBack.see();
            }
        });
    }


    public interface CutPicCallBack {
        //选择 图片
        void choose();

        //取消
        void cancel();

        //拍照
        void camera();

        //查看大图
        void see();
    }


}
