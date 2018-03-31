package com.fancy.learncenter.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fancy.learncenter.adapter.DiscussAdapter;
import com.fancy.learncenter.utils.DimenUtil;
import com.fancy.learncenter.utils.KeyboardChangeListener;
import com.fancy.learncenter.utils.ToastUtil;
import com.fancy.learncenter.view.LPopWindow;
import com.fancy.lizi.R;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.model.VideoOptionModel;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.CustomGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * 单独的视频播放页面
 * Created by shuyu on 2016/11/11.
 */
public class VideoDetailActivity extends Activity {
    String ulrPath = "http://img1.fancyedu.com/dubbing_resource/video/1503911575801_gels-hlsyqdlq-0-0.mp4";
    public final static String IMG_TRANSITION = "IMG_TRANSITION";
    public final static String TRANSITION = "TRANSITION";

    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    @Bind(R.id.video_player)
    CustomGSYVideoPlayer videoPlayer;
    @Bind(R.id.rl_send)
    RelativeLayout rlSend;
    @Bind(R.id.ll_send)
    View llSend;

    private Transition transition;
    OrientationUtils orientationUtils;


    View topView;
    DiscussAdapter discussAdapter;
    ArrayList<String> discussDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);
        initKeyboardChangeListener();
//        isTransition = getIntent().getBooleanExtra(TRANSITION, false);
        vedioId = getIntent().getStringExtra(VEDIO_KEY);
        init(ulrPath);
        discussDatas = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            discussDatas.add("");
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);
        discussAdapter = new DiscussAdapter(this, discussDatas);
        recycleView.setAdapter(discussAdapter);
        discussAdapter.setHeaderView(topView, recycleView);
    }

    private String vedioId;
    public static String VEDIO_KEY = "VEDIO_ID";

    private void init(String urlPath) {
        topView = LayoutInflater.from(this).inflate(R.layout.activity_video_detail_top, null);
        VideoOptionModel videoOptionModel =
                new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 50);
        List<VideoOptionModel> list = new ArrayList<>();
        list.add(videoOptionModel);
        GSYVideoManager.instance().setOptionModelList(list);
        //需要路径的
        videoPlayer.setUp(urlPath, true, "非凡学习");
        //增加封面
//        SimpleDraweeView imageView = new SimpleDraweeView(this);
//        GenericDraweeHierarchy hierarchy = imageView.getHierarchy();
//        hierarchy.setPlaceholderImage(R.mipmap.default_img);
//        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
//        imageView.setHierarchy(hierarchy);
//        imageView.setImageURI(vedioDataBean.getVideoDetailVO().getImgUrl());
//        videoPlayer.setThumbImageView(imageView);

        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.GONE);
//        videoPlayer.getTitleTextView().setText(vedioDataBean.getVideoDetailVO().getTitle());
//        videoPlayer.setShowPauseCover(true);

        //videoPlayer.setSpeed(2f);

        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);

        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);

        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setVisibility(View.VISIBLE);
//

        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                videoPlayer.startWindowFullscreen(VideoDetailActivity.this, true, true);

//                orientationUtils.resolveByClick();
            }
        });

        videoPlayer.setBottomProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_progress));
        videoPlayer.setDialogVolumeProgressBar(getResources().getDrawable(R.drawable.video_new_volume_progress_bg));
        videoPlayer.setDialogProgressBar(getResources().getDrawable(R.drawable.video_new_progress));
        videoPlayer.setDialogProgressColor(getResources().getColor(R.color.yellow), getResources().getColor(R.color.white));

        videoPlayer.setBottomShowProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_seekbar_progress),
                getResources().getDrawable(R.drawable.video_new_seekbar_thumb));
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);

//
        videoPlayer.startPlayLogic();
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        videoPlayer.setLockLand(true);
        //过渡动画
    }

    @OnClick({R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
        lPopWindow = new LPopWindow.Builder(VideoDetailActivity.this)
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


    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }
}
