package com.fancy.learncenter.view;

import android.app.Activity;
import android.view.WindowManager;
import android.widget.PopupWindow;


/**
 * Created by Hyy on 2016/9/26.
 */
public class BasedPopupWindow extends PopupWindow {

    Activity mContext;

    public BasedPopupWindow(Activity mContext) {
        this.mContext = mContext;
    }

    public void showPopupWindow() {
        if (!this.isShowing()) {
            WindowManager.LayoutParams wlp = mContext.getWindow().getAttributes();
            wlp.alpha = 0.5f;
            mContext.getWindow().setAttributes(wlp);
            mContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            dismiss();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();

        WindowManager.LayoutParams wlp = mContext.getWindow().getAttributes();
        wlp.alpha = 1f;
        mContext.getWindow().setAttributes(wlp);
        mContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }


}
