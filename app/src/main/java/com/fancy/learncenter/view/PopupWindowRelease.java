package com.fancy.learncenter.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fancy.learncenter.activity.ReleaseGraphicActivity;
import com.fancy.learncenter.activity.VideoRecorderActivity;
import com.fancy.learncenter.utils.PermissionUtils;
import com.fancy.lizi.R;

public class PopupWindowRelease extends BasedPopupWindow {

    private View popView;
    private View locationView;
    private View.OnClickListener listener;

    LinearLayout pic_release;
    LinearLayout video_release;

    public PopupWindowRelease(Activity mContext, View viewGroup) {
        super(mContext);
        popView = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_realease, (ViewGroup) viewGroup, false);
        this.locationView = viewGroup;
        initPopupWindow();
    }

    private void initPopupWindow() {
        pic_release = (LinearLayout) popView.findViewById(R.id.pic_release);
        video_release = (LinearLayout) popView.findViewById(R.id.video_release);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        update();
        setContentView(popView);
        popView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        pic_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ReleaseGraphicActivity.class));
            }
        });
        video_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionUtils.cameraIsCanUse()) {
                    mContext.startActivity(new Intent(mContext, VideoRecorderActivity.class));
                } else {
                    PermissionUtils.openSettingActivity(mContext,"没有相机和录音权限，无法开启这个功能，请开启权限");
                }
            }
        });
    }

    @Override
    public void showPopupWindow() {
        super.showPopupWindow();
        showAtLocation(locationView, Gravity.BOTTOM, 0, 0);
    }
}
