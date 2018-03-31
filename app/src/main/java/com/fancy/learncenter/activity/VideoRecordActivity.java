package com.fancy.learncenter.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import com.fancy.learncenter.common.Constant;
import com.fancy.learncenter.utils.MyCamParaUtil;
import com.fancy.lizi.R;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LeeBoo on 2017/1/18
 * <p>
 * 不需要暂停后可以继续录制(注释的地方为可实现暂停录制)
 */


public class VideoRecordActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "VideoRecordActivity";
    public static final int CONTROL_CODE = 1;

    private ImageView mRecordControl;
    private ImageView mRecordPlay;
    private SurfaceView surfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Chronometer mRecordTime;
    private ImageView mWanCheng;
    private ImageView mQuXiao;
    private ImageView mFanzhuan;
    private ImageView mXiangce;

    private boolean isRecording;           // 标记，判断当前是否正在录制
    private long mPauseTime = 0;           //录制暂停时间间隔
    private Camera mCamera;
    private MediaRecorder mediaRecorder;
    private String currentVideoFilePath;
    private int cameraPosition = 0;         //1代表前置摄像头，0代表后置摄像头

    private Handler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<VideoRecordActivity> mActivity;

        public MyHandler(VideoRecordActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
            if (mActivity.get() == null) {
                return;
            }
            switch (msg.what) {
                case CONTROL_CODE:
                    //开启按钮
                    mActivity.get().mRecordPlay.setEnabled(true);
                    break;
            }
        }
    }


    private MediaRecorder.OnErrorListener OnErrorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mediaRecorder, int what, int extra) {
            try {
                if (mediaRecorder != null) {
                    mediaRecorder.reset();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_record);
        initView();
    }

    private void initView() {
        surfaceView = (SurfaceView) findViewById(R.id.record_surfaceView);
        mRecordControl = (ImageView) findViewById(R.id.record_control);
        mRecordPlay = (ImageView) findViewById(R.id.record_play);
        mRecordTime = (Chronometer) findViewById(R.id.record_time);
        mWanCheng = (ImageView) findViewById(R.id.wancheng);
        mQuXiao = (ImageView) findViewById(R.id.quxiao);
        mFanzhuan = (ImageView) findViewById(R.id.fanzhuan);
        mXiangce = (ImageView) findViewById(R.id.xiangce);
        mRecordPlay.setOnClickListener(this);
        mWanCheng.setOnClickListener(this);
        mQuXiao.setOnClickListener(this);
        mFanzhuan.setOnClickListener(this);
        mXiangce.setOnClickListener(this);

        //配置SurfaceHolder
        mSurfaceHolder = surfaceView.getHolder();
        // 设置Surface不需要维护自己的缓冲区
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // 设置分辨率
        mSurfaceHolder.setFixedSize(1280, 720);
        // 设置该组件不会让屏幕自动关闭
        mSurfaceHolder.setKeepScreenOn(true);
        mSurfaceHolder.addCallback(mCallBack);//回调接口
    }

    private SurfaceHolder.Callback mCallBack = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            initCamera(cameraPosition);
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
            if (mSurfaceHolder.getSurface() == null) {
                return;
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            stopCamera();
        }
    };


    /**
     * 初始化摄像头
     */
    private void initCamera(int cameraPosition) {
        if (mCamera != null) {
            stopCamera();
        }
        //默认启动后置摄像头
        if (cameraPosition == 0) {
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        } else {
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        }
        if (mCamera == null) {
            Toast.makeText(this, "未能获取到相机！", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            //配置CameraParams
            setCameraParams();
            //启动相机预览
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }


    /**
     * 设置摄像头参数
     */
    private void setCameraParams() {
        if (mCamera != null) {
            Camera.Parameters params = mCamera.getParameters();
            //设置相机的横竖屏(竖屏需要旋转90°)
            if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                params.set("orientation", "portrait");//人像（竖屏）
            } else {
                params.set("orientation", "landscape");//风景（横屏）
            }
            MyCamParaUtil.setCameraDisplayOrientation(this, cameraPosition, mCamera);

            Camera.Size pictureSize = MyCamParaUtil.getInstance().getPictureSize(params.getSupportedPictureSizes(), 800);
            //预览大小
            Camera.Size previewSize = MyCamParaUtil.getInstance().getPreviewSize(params.getSupportedPreviewSizes(), MyCamParaUtil.getScreenHeight(this));
            if (previewSize != null)
                params.setPreviewSize(previewSize.width, previewSize.height);
            if (pictureSize != null)
                params.setPictureSize(pictureSize.width, pictureSize.height);
            //设置聚焦模式
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            //缩短Recording启动时间
            params.setRecordingHint(true);
            //影像稳定能力
            if (params.isVideoStabilizationSupported())
                params.setVideoStabilization(true);
            mCamera.setParameters(params);
        }
    }


    /**
     * 释放摄像头资源
     */
    private void stopCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * 开始录制视频
     */
    public void startRecord() {
        initCamera(cameraPosition);
        mCamera.unlock();
        setConfigRecord();
        try {
            //开始录制
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isRecording = true;
        if (mPauseTime != 0) {
            mRecordTime.setBase(SystemClock.elapsedRealtime() - (mPauseTime - mRecordTime.getBase()));
        } else {
            mRecordTime.setBase(SystemClock.elapsedRealtime());
        }
        mRecordTime.start();
    }

    /**
     * 停止录制视频
     */
    public void stopRecord() {
        if (isRecording && mediaRecorder != null) {
            // 设置后不会崩
            mediaRecorder.setOnErrorListener(null);
            mediaRecorder.setPreviewDisplay(null);
            //停止录制
            mediaRecorder.stop();
            mediaRecorder.reset();
            //释放资源
            mediaRecorder.release();
            mediaRecorder = null;
            mRecordTime.stop();
            isRecording = false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_play:
                if (!isRecording) {
                    //开始录制视频
                    startRecord();
                    mRecordControl.setImageResource(R.mipmap.ic_zanting);
                    mRecordPlay.setEnabled(false);//1s后才能停止
                    mHandler.sendEmptyMessageDelayed(CONTROL_CODE, 1000);
                    mFanzhuan.setVisibility(View.GONE);
                } else {
                    //停止视频录制
                    mRecordControl.setImageResource(R.mipmap.ic_kaishi);
                    stopRecord();
                    mCamera.lock();
                    stopCamera();
                    mRecordTime.stop();
                    mPauseTime = 0;
                    mQuXiao.setVisibility(View.VISIBLE);
                    mWanCheng.setVisibility(View.VISIBLE);
                    mRecordPlay.setEnabled(false);
                }
                break;
            case R.id.wancheng:
                goToReleaseOrChat();
                break;
            case R.id.xiangce:
                choiceVideo();
                break;
            case R.id.fanzhuan:
                if (cameraPosition == 1) {
                    cameraPosition = 0;
                } else {
                    cameraPosition = 1;
                }
                initCamera(cameraPosition);
                break;
            case R.id.quxiao:
                mRecordPlay.setEnabled(true);
                mQuXiao.setVisibility(View.GONE);
                mWanCheng.setVisibility(View.GONE);
                mFanzhuan.setVisibility(View.VISIBLE);
                mRecordTime.setBase(SystemClock.elapsedRealtime());
                break;
        }
    }

    /**
     * 从相册中选择视频
     */
    private void choiceVideo() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1010);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1010 && resultCode == RESULT_OK && null != data) {
            Uri selectedVideo = data.getData();
            String[] filePathColumn = {MediaStore.Video.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedVideo,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            currentVideoFilePath = cursor.getString(columnIndex);
            cursor.close();
            goToReleaseOrChat();
        }
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
    }

    public static String getSDPath(Context context) {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        } else if (!sdCardExist) {
            Toast.makeText(context, "SD卡不存在", Toast.LENGTH_SHORT).show();
        }
        File eis = new File(sdDir.toString() + "/Video/");
        try {
            if (!eis.exists()) {
                eis.mkdir();
            }
        } catch (Exception e) {

        }
        return sdDir.toString() + "/Video/";
    }

    /**
     * 配置MediaRecorder()
     */
    private void setConfigRecord() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.reset();
        mediaRecorder.setCamera(mCamera);
        //设置采集声音
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        //设置采集图像
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setOnErrorListener(OnErrorListener);

        //使用SurfaceView预览
        mediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
        CamcorderProfile mProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
        mediaRecorder.setOutputFormat(mProfile.fileFormat);
        //设置音频的编码格式
        mediaRecorder.setAudioEncoder(mProfile.audioCodec);
        //设置图像的编码格式
        mediaRecorder.setVideoEncoder(mProfile.videoCodec);
        mediaRecorder.setVideoEncodingBitRate(mProfile.videoBitRate);
        mediaRecorder.setAudioEncodingBitRate(mProfile.audioBitRate);
        mediaRecorder.setAudioChannels(mProfile.audioChannels);
        mediaRecorder.setAudioSamplingRate(mProfile.audioSampleRate);
        mediaRecorder.setVideoFrameRate(mProfile.videoFrameRate);
        //设置选择角度，顺时针方向，因为默认是前置逆向90度 后置270度，这样图像就是正常显示了,这里设置的是观看保存后的视频的角度
        if (cameraPosition == 0) {
            mediaRecorder.setOrientationHint(90);
        } else {
            mediaRecorder.setOrientationHint(270);
        }
        //设置录像的分辨率
        mediaRecorder.setVideoSize(mProfile.videoFrameWidth, mProfile.videoFrameHeight);
        //设置录像视频保存地址
        currentVideoFilePath = getSDPath(getApplicationContext()) + getVideoName();
        mediaRecorder.setOutputFile(currentVideoFilePath);
    }

    private String getVideoName() {
        return "VID_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".mp4";
    }

    //跳转到发布视频页面
    private void goToRelease() {
        Intent intent = new Intent(this, ReleaseVideoActivity.class);
        intent.putExtra(Constant.VIDEO_PATH, currentVideoFilePath);
        startActivity(intent);
        finish();
    }

    //返回到聊天页面 发送视频消息
    private void goToChat() {
        Intent intent = new Intent();
        intent.putExtra(Constant.VIDEO_PATH, currentVideoFilePath);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void goToReleaseOrChat() {
        if (getIntent().getBooleanExtra("isFromChat", false)) {
            goToChat();
        } else {
            goToRelease();
        }
    }

}
