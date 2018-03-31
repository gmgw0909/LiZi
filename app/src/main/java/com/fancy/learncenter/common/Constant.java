package com.fancy.learncenter.common;

import android.os.Environment;

/**
 * Created by Hyy on 2016/9/22.
 */
public class Constant {

    public static String BASE_URL = "http://47.96.168.113/";   //android测试地址

    // APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "wxad8a7420a00f9c20";
    // 是否是正式版本
    public static boolean release = false;

    public static final String PIC_PATH = Environment.getExternalStorageDirectory() + "/Fancy/Teeya/";

    //定位回调地址String
    public static String LOCATION_STRING = "Location_Str";
    //录制视频路径地址回调String
    public static String VIDEO_PATH = "video_path";

    public static final String MESSAGE_ATTR_IS_VOICE_CALL = "is_voice_call";
    public static final String MESSAGE_ATTR_IS_VIDEO_CALL = "is_video_call";
}











