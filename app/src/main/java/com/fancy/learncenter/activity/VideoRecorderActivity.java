package com.fancy.learncenter.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fancy.learncenter.activity.base.BaseActivity;
import com.fancy.learncenter.common.Constant;
import com.fancy.learncenter.common.MyApplication;
import com.fancy.learncenter.utils.PermissionUtils;
import com.fancy.learncenter.view.FocusSurfaceView;
import com.fancy.learncenter.view.RecordedButton;
import com.fancy.lizi.R;
import com.yixia.camera.MediaRecorderNative;
import com.yixia.camera.VCamera;
import com.yixia.camera.model.MediaObject;
import com.yixia.videoeditor.adapter.UtilityAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 仿新版微信录制视频
 * 基于ffmpeg视频编译
 * 使用的是免费第三方VCamera
 */
public class VideoRecorderActivity extends BaseActivity implements View.OnClickListener {

    private static final int HANDLER_RECORD = 200;
    private static final int HANDLER_EDIT_VIDEO = 201;

    private MediaRecorderNative mMediaRecorder;
    private MediaObject mMediaObject;
    private FocusSurfaceView sv_ffmpeg;
    private RecordedButton rb_start;
    private RelativeLayout rl_bottom;
    private ImageView iv_back;
    private TextView tv_hint;
    private TextView textView;
    private Chronometer mRecordTime;

    //最大录制时间 10秒
    private int maxDuration = 10200;
    //录制暂停时间间隔
    private long mPauseTime = 0;
    //本次段落是否录制完成
    private boolean isRecordedOver;
    private ImageView iv_change_flash;
    private List<Integer> cameraTypeList = new ArrayList<>();
    private String currentVideoFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_recoder);
        sv_ffmpeg = (FocusSurfaceView) findViewById(R.id.sv_ffmpeg);
        rb_start = (RecordedButton) findViewById(R.id.rb_start);
        ImageView iv_finish = (ImageView) findViewById(R.id.iv_finish);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_hint = (TextView) findViewById(R.id.tv_hint);
        rl_bottom = (RelativeLayout) findViewById(R.id.rl_bottom);
        iv_change_flash = (ImageView) findViewById(R.id.iv_change_flash);
        ImageView iv_change_camera = (ImageView) findViewById(R.id.iv_change_camera);
        ImageView xiangce = (ImageView) findViewById(R.id.xiangce);
        mRecordTime = (Chronometer) findViewById(R.id.record_time);
        initMediaRecorder();
        try {
            mMediaObject.getDuration();
        } catch (Exception e) {
            PermissionUtils.openSettingActivity(VideoRecorderActivity.this, "没有录音和读写手机存储权限，无法进行视频录制功能，请开启权限");
        }
        sv_ffmpeg.setTouchFocus(mMediaRecorder);
        rb_start.setMax(maxDuration);
        rb_start.setDeleteMode(true);
        rb_start.setOnGestureListener(new RecordedButton.OnGestureListener() {
            @Override
            public void onLongClick() {
                if (mPauseTime != 0) {
                    mRecordTime.setBase(mRecordTime.getBase() + (SystemClock.elapsedRealtime() - mPauseTime));
                } else {
                    mRecordTime.setBase(SystemClock.elapsedRealtime());
                }
                mRecordTime.start();
                isRecordedOver = false;
                mMediaRecorder.startRecord();
                rb_start.setSplit();
                myHandler.sendEmptyMessageDelayed(HANDLER_RECORD, 100);
                cameraTypeList.add(mMediaRecorder.getCameraType());
            }

            @Override
            public void onClick() {

            }

            @Override
            public void onLift() {
                isRecordedOver = true;
                mMediaRecorder.stopRecord();
                changeButton(mMediaObject.getMediaParts().size() > 0);
                mRecordTime.stop();
                mPauseTime = SystemClock.elapsedRealtime();
                rb_start.setResponseLongTouch(false);
            }

            @Override
            public void onOver() {
                isRecordedOver = true;
                rb_start.closeButton();
                mMediaRecorder.stopRecord();
                videoFinish();
                mRecordTime.stop();
                mPauseTime = 0;
            }
        });
        iv_back.setOnClickListener(this);
        iv_finish.setOnClickListener(this);
        iv_change_flash.setOnClickListener(this);
        iv_change_camera.setOnClickListener(this);
        xiangce.setOnClickListener(this);
    }

    private void changeButton(boolean flag) {
        if (flag) {
            tv_hint.setVisibility(View.VISIBLE);
            rl_bottom.setVisibility(View.VISIBLE);
        } else {
            tv_hint.setVisibility(View.GONE);
            rl_bottom.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化视频拍摄状态
     */
    private void initMediaRecorderState() {
        rb_start.setVisibility(View.VISIBLE);
        changeButton(false);
        tv_hint.setVisibility(View.VISIBLE);
        LinkedList<MediaObject.MediaPart> list = new LinkedList<>();
        list.addAll(mMediaObject.getMediaParts());
        for (MediaObject.MediaPart part : list) {
            mMediaObject.removePart(part, true);
        }
        rb_start.setProgress(mMediaObject.getDuration());
        rb_start.cleanSplit();
    }

    private void videoFinish() {
        changeButton(false);
        rb_start.setVisibility(View.GONE);
        textView = showProgressDialog();
        myHandler.sendEmptyMessage(HANDLER_EDIT_VIDEO);
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_RECORD://拍摄视频的handler
                    if (!isRecordedOver) {
                        if (rl_bottom.getVisibility() == View.VISIBLE) {
                            changeButton(false);
                        }
                        rb_start.setProgress(mMediaObject.getDuration());
                        myHandler.sendEmptyMessageDelayed(HANDLER_RECORD, 30);
                    }
                    break;
                case HANDLER_EDIT_VIDEO://合成视频的handler
                    int progress = UtilityAdapter.FilterParserAction("", UtilityAdapter.PARSERACTION_PROGRESS);
                    if (textView != null)
                        textView.setText("视频合成中 " + progress + "%");
                    if (progress == 100) {
                        syncVideo();
                    } else if (progress == -1) {
                        closeProgressDialog();
                        Toast.makeText(getApplicationContext(), "视频合成失败", Toast.LENGTH_SHORT).show();
                    } else {
                        sendEmptyMessageDelayed(HANDLER_EDIT_VIDEO, 30);
                    }
                    break;
            }
        }
    };

    /**
     * 合成视频
     */
    private void syncVideo() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                if (textView != null)
                    textView.setText("视频合成中");
            }

            @Override
            protected String doInBackground(Void... params) {

                List<String> pathList = new ArrayList<>();
                for (int x = 0; x < mMediaObject.getMediaParts().size(); x++) {
                    MediaObject.MediaPart mediaPart = mMediaObject.getMediaParts().get(x);

                    String mp4Path = MyApplication.VIDEO_PATH + "/" + x + ".mp4";
                    List<String> list = new ArrayList<>();
                    list.add(mediaPart.mediaPath);
                    tsToMp4(list, mp4Path);
                    pathList.add(mp4Path);
                }

                List<String> tsList = new ArrayList<>();
                for (int x = 0; x < pathList.size(); x++) {
                    String path = pathList.get(x);
                    String ts = MyApplication.VIDEO_PATH + "/" + x + ".ts";
                    mp4ToTs(path, ts);
                    tsList.add(ts);
                }

                String output = MyApplication.VIDEO_PATH + "/finish.mp4";
                boolean flag = tsToMp4(tsList, output);
                if (!flag) output = "";
                deleteDirRoom(new File(MyApplication.VIDEO_PATH), output);
                return output;
            }

            @Override
            protected void onPostExecute(String result) {
                closeProgressDialog();
                if (!TextUtils.isEmpty(result)) {
                    currentVideoFilePath = result;
                    goToReleaseOrChat();
                } else {
                    Toast.makeText(getApplicationContext(), "视频合成失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
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

    /**
     * 删除文件夹下所有文件, 只保留一个
     *
     * @param fileName 保留的文件名称
     */
    public static void deleteDirRoom(File dir, String fileName) {

        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File f : files) {
                deleteDirRoom(f, fileName);
            }
        } else if (dir.exists()) {
            if (!dir.getAbsolutePath().equals(fileName)) {
                dir.delete();
            }
        }
    }

    public void mp4ToTs(String path, String output) {

        //./ffmpeg -i 0.mp4 -c copy -bsf:v h264_mp4toannexb -f mpegts ts0.ts

        StringBuilder sb = new StringBuilder("ffmpeg");
        sb.append(" -i");
        sb.append(" " + path);
        sb.append(" -c");
        sb.append(" copy");
        sb.append(" -bsf:v");
        sb.append(" h264_mp4toannexb");
        sb.append(" -f");
        sb.append(" mpegts");
        sb.append(" " + output);

        int i = UtilityAdapter.FFmpegRun("", sb.toString());
    }

    public boolean tsToMp4(List<String> path, String output) {

        //ffmpeg -i "concat:ts0.ts|ts1.ts|ts2.ts|ts3.ts" -c copy -bsf:a aac_adtstoasc out2.mp4

        StringBuilder sb = new StringBuilder("ffmpeg");
        sb.append(" -i");
        String concat = "concat:";
        for (String part : path) {
            concat += part;
            concat += "|";
        }
        concat = concat.substring(0, concat.length() - 1);
        sb.append(" " + concat);
        sb.append(" -c");
        sb.append(" copy");
        sb.append(" -bsf:a");
        sb.append(" aac_adtstoasc");
        sb.append(" -y");
        sb.append(" " + output);

        int i = UtilityAdapter.FFmpegRun("", sb.toString());
        return i == 0;
    }

    /**
     * 初始化录制对象
     */
    private void initMediaRecorder() {
        mMediaRecorder = new MediaRecorderNative();
        String key = String.valueOf(System.currentTimeMillis());
        //设置缓存文件夹
        mMediaObject = mMediaRecorder.setOutputDirectory(key, VCamera.getVideoCachePath());
        //设置视频预览源
        mMediaRecorder.setSurfaceHolder(sv_ffmpeg.getHolder());
        //准备
        mMediaRecorder.prepare();
        //滤波器相关
        UtilityAdapter.freeFilterParser();
        UtilityAdapter.initFilterParser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaRecorder.startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaRecorder.stopPreview();
        iv_change_flash.setImageResource(R.mipmap.video_flash_close);
    }

    @Override
    public void onBackPressed() {
        if (rb_start.getSplitCount() == 0) {
            super.onBackPressed();
        } else {
            initMediaRecorderState();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaObject != null) {
            mMediaObject.cleanTheme();
        }
        mMediaRecorder.release();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1010 && null != data) {
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
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                if (rb_start.isDeleteMode()) {//判断是否要删除视频段落
                    MediaObject.MediaPart lastPart = mMediaObject.getPart(mMediaObject.getMediaParts().size() - 1);
                    mMediaObject.removePart(lastPart, true);
                    rb_start.setProgress(mMediaObject.getDuration());
                    rb_start.deleteSplit();
                    rb_start.setDeleteMode(true);
                    if (cameraTypeList.size() > 0) {
                        cameraTypeList.remove(cameraTypeList.size() - 1);
                    }
                    changeButton(mMediaObject.getMediaParts().size() > 0);
                    mRecordTime.setBase(SystemClock.elapsedRealtime());
                    rb_start.setResponseLongTouch(true);
                    mPauseTime = 0;
                }
                break;
            case R.id.iv_finish:
                videoFinish();
                break;
            case R.id.xiangce:
                choiceVideo();
                break;
            case R.id.iv_change_camera:
                mMediaRecorder.switchCamera();
                iv_change_flash.setImageResource(R.mipmap.video_flash_close);
                break;
            case R.id.iv_change_flash:
                if (mMediaRecorder.changeFlash(this)) {
                    iv_change_flash.setImageResource(R.mipmap.video_flash_open);
                } else {
                    iv_change_flash.setImageResource(R.mipmap.video_flash_close);
                }
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
}
