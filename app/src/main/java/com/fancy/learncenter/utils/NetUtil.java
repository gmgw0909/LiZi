package com.fancy.learncenter.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import java.util.Map;

/**
 * Created by Hyy on 2016/9/22.
 */
public class NetUtil {

    /**
     * 检测网络是否可用
     *
     *
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }
}
