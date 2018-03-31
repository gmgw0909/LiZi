package com.fancy.learncenter.common;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.fancy.learncenter.utils.ToastUtil;
import com.fancy.learncenter.view.CallReceiver;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.yixia.camera.VCamera;

import java.io.File;

/**
 * Created by Hyy on 2016/9/22.
 */
public class MyApplication extends Application {

    public static String VIDEO_PATH = Environment.getExternalStorageDirectory().getPath() + "/lzVideo/";
    public static Context context = null;
    private static MyApplication instance;

    EaseUI easeUI;
    CallReceiver callReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        ToastUtil.initToast(getApplicationContext());
//        //微信
//        PlatformConfig.setWeixin("wxd422caeaa8ad29c5", "3820b5f7985c8ba0a30c123f619d8262");
//        //新浪微博
//        PlatformConfig.setSinaWeibo("600049747", "04b48b094faeb16683c32669824ebdad");
//        //QQ
//        PlatformConfig.setQQZone("1105746474", "3inyZO7bl8nK9SaC");
        initFresco();
        //初始化视频录制
        initVideoRecorder();
        //初始化环信EaseUI
        initEaseUi();
    }

    private void initEaseUi() {
        easeUI = EaseUI.getInstance();
        easeUI.init(this, null);
        //用户信息提供者
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }
        });
        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        if (callReceiver == null) {
            callReceiver = new CallReceiver();
        }
        //register incoming call receiver
        registerReceiver(callReceiver, callFilter);
    }

    private void initVideoRecorder() {
        VIDEO_PATH += String.valueOf(System.currentTimeMillis());
        File file = new File(VIDEO_PATH);
        if(!file.exists()) file.mkdirs();
        //设置视频缓存路径
        VCamera.setVideoCachePath(VIDEO_PATH);
        // 开启log输出,ffmpeg输出到logcat
        VCamera.setDebugMode(true);
        // 初始化拍摄SDK，必须
        VCamera.initialize(this);
    }

    private EaseUser getUserInfo(String username) {
        if (username.equals("15656238290")) {
            EaseUser easeUser = new EaseUser("15656238290");
            easeUser.setNickname("宝哥");
            easeUser.setAvatar("http://q2.qlogo.cn/g?b=qq&k=icxczU07SK4Vic8wtJAEKuEg&s=100&t=0");
            return easeUser;
        } else if (username.equals("12345")) {
            EaseUser easeUser = new EaseUser("12345");
            easeUser.setNickname("小胡");
            easeUser.setAvatar("http://img0.imgtn.bdimg.com/it/u=1881776517,987084327&fm=27&gp=0.jpg");
            return easeUser;
        }
        return null;
    }

    private void initFresco() {
        Fresco.initialize(this);
    }

    public Context getContext() {
        if (context == null) {
            context = this.getApplicationContext();
        }
        return context;
    }

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(callReceiver);
    }
}
