package com.fancy.learncenter.utils;

import android.util.Log;

import com.fancy.learncenter.common.Constant;

/**
 * Created by Hyy on 2016/9/22.
 */
public class LogUtil {

    public static void MyLog(String log, String msg) {
        if (!Constant.release) {
            Log.i(log, msg);
        }
    }
}
