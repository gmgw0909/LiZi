package com.fancy.learncenter.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.fancy.learncenter.activity.base.BaseActivity;
import com.fancy.learncenter.common.Constant;
import com.fancy.lizi.R;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.CustomGSYVideoPlayer;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LeeBoo on 2018/1/19.
 */

public class PreviewVideoActivity extends BaseActivity {

    @Bind(R.id.video_player)
    CustomGSYVideoPlayer videoPlayer;

    OrientationUtils orientationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_preview_video);
        ButterKnife.bind(this);
        String urlPath = getIntent().getStringExtra(Constant.VIDEO_PATH);
        if (!TextUtils.isEmpty(urlPath)) {
            initVideoPlayer(urlPath);
        }
    }

    /**
     * 初始化VideoPlayer
     */
    private void initVideoPlayer(String urlPath) {
        videoPlayer.setUp(urlPath, true, "mm");
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setVisibility(View.VISIBLE);
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                videoPlayer.startWindowFullscreen(PreviewVideoActivity.this, true, true);
            }
        });
        videoPlayer.setBottomProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_progress));
        videoPlayer.setDialogVolumeProgressBar(getResources().getDrawable(R.drawable.video_new_volume_progress_bg));
        videoPlayer.setDialogProgressBar(getResources().getDrawable(R.drawable.video_new_progress));
        videoPlayer.setDialogProgressColor(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.white));
        videoPlayer.setBottomShowProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_seekbar_progress),
                getResources().getDrawable(R.drawable.video_new_seekbar_thumb));
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        videoPlayer.startPlayLogic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }
}
