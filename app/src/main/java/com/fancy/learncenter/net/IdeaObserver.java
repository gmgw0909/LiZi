package com.fancy.learncenter.net;


import android.app.Activity;
import android.app.Dialog;

import com.fancy.learncenter.bean.BaseDataBean;
import com.fancy.learncenter.common.Constant;
import com.fancy.learncenter.utils.ToastUtil;
import com.fancy.learncenter.view.CustomProgressDialog;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by hyy on 2017/12/1.
 * describe as
 */

public abstract class IdeaObserver<T extends BaseDataBean> implements Observer<BaseDataBean<T>> {

    public static int SUCCESS_CODE = 0;
    Dialog dialog;


    public IdeaObserver() {
    }

    public IdeaObserver(Activity activity, boolean isShowDialog) {
        if (isShowDialog) {
            dialog = CustomProgressDialog.creatRequestDialog(activity);
        }
    }

    @Override
    public void onNext(@NonNull BaseDataBean<T> tBaseDataBean) {
        if (tBaseDataBean.getCode() == SUCCESS_CODE) {
//            ToastUtil.show(tBaseDataBean.getMessage());
            onSuccess((T) tBaseDataBean);
        } else {
            //弹出服务器返回的错误信息
            ToastUtil.show(tBaseDataBean.getMessage());
            onFail(tBaseDataBean.getCode() + "");
        }
    }

    @Override
    public void onComplete() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        ToastUtil.show("开始获取验证码" + e);
        onFail("我是系统错误");

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

        if (dialog != null) {
            dialog.show();
        }
    }

    public abstract void onSuccess(T t);

    public abstract void onFail(String errorCode);


}