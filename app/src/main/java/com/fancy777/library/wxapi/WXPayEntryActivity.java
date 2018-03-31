package com.fancy777.library.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fancy.learncenter.utils.LogUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import fancy.hyypaysdk.pay.Constant;


public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {


    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID, false);
        api.handleIntent(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        LogUtil.MyLog("sssssss", resp.errStr + "ssssssfsf" + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Intent mIntent = new Intent(Constant.WXPAY_CALLBACK_NAME);
            if (resp.errCode == 0) {
                mIntent.putExtra(Constant.WXPAY_RESULT, "0");
            } else if (resp.errCode == -1) {
                mIntent.putExtra(Constant.WXPAY_RESULT, "-1");
            } else if (resp.errCode == -2) {
                mIntent.putExtra(Constant.WXPAY_RESULT, "-2");
            }
            sendBroadcast(mIntent);
        }

        this.finish();
    }
}