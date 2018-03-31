package com.fancy777.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import fancy.hyypaysdk.pay.Constant;


public class AppRegister extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final IWXAPI api = WXAPIFactory.createWXAPI(context, null);

        api.registerApp(Constant.APP_ID);
    }
}
