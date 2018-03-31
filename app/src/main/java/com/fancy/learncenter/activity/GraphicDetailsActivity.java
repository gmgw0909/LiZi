package com.fancy.learncenter.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fancy.learncenter.activity.base.BaseActivity;
import com.fancy.learncenter.utils.DimenUtil;
import com.fancy.learncenter.utils.KeyboardChangeListener;
import com.fancy.learncenter.utils.ToastUtil;
import com.fancy.learncenter.utils.Utils;
import com.fancy.learncenter.view.LPopWindow;
import com.fancy.lizi.R;

import java.net.URISyntaxException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LeeBoo on 2018/1/22.
 */

public class GraphicDetailsActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.rl_send)
    RelativeLayout rlSend;
    @Bind(R.id.ll_send)
    View llSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic_details);
        ButterKnife.bind(this);
        title.setText("图文详情");
        initKeyboardChangeListener();
    }

    @OnClick({R.id.back, R.id.location, R.id.pinglun, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.location:
                //TODO 到时候传经纬度过来获取精准定位
                goToGDMap("小胡的小屋", "31.22698", "121.524155");
                break;
            case R.id.pinglun:
                startActivity(new Intent(GraphicDetailsActivity.this, DiscussActivity.class));
                break;
            case R.id.share:
                initPicPopWindow(view);
                break;
        }
    }

    //分享弹出框
    LPopWindow lPopWindow;

    private void initPicPopWindow(View view) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pupopwinodw_share, null);
        TextView cancel = (TextView) contentView.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lPopWindow.dismiss();
            }
        });
        TextView weixin = (TextView) contentView.findViewById(R.id.weixin);
        weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("微信");
            }
        });
        TextView quan = (TextView) contentView.findViewById(R.id.quan);
        quan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("朋友圈");
            }
        });
        TextView qq = (TextView) contentView.findViewById(R.id.qq);
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("微信");
            }
        });
        TextView zone = (TextView) contentView.findViewById(R.id.zone);
        zone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("朋友圈");
            }
        });
        lPopWindow = new LPopWindow.Builder(GraphicDetailsActivity.this)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtil.dip2px(200))
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .build()
                .showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    //键盘打开关闭监听器
    private void initKeyboardChangeListener() {
        new KeyboardChangeListener(this).setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                if (isShow) {
                    llSend.setVisibility(View.VISIBLE);
                    rlSend.setVisibility(View.GONE);
                } else {
                    llSend.setVisibility(View.GONE);
                    rlSend.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void goToGDMap(String poiName, String mLatitude, String mLongitude) {
        if (Utils.isAvilible(this, "com.autonavi.minimap")) {
            try {
                Intent intent = Intent.getIntent("androidamap://viewMap?sourceApplication=粒子&poiname=" + poiName + "" + "&lat="
                        + mLatitude
                        + "&lon="
                        + mLongitude + "&dev=0");
                startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "您尚未安装高德地图", Toast.LENGTH_LONG)
                    .show();
            Uri uri = Uri
                    .parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}
