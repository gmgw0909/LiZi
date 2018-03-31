package com.fancy.learncenter.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.fancy.lizi.R;


/**
 * Created by Hyy on 2016/10/8.
 */
public class ProgressUtil {
    public static Dialog creatRequestDialog(Context context, String tip) {
        Dialog dialog = new Dialog(context, R.style.dlg_request_style);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dlg_request);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int width = display.getWidth();
        lp.width = (int) (0.6 * width);
        TextView titleTxtv = (TextView) dialog.findViewById(R.id.tvLoad);
        if (tip == null || tip.length() == 0) {
            titleTxtv.setText("正在发送");
        } else {
            titleTxtv.setText(tip);
        }
        return dialog;
    }

    public static void ToastDialog(Context context, String title, String msg) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(msg)
                .setPositiveButton("确定", null).create().show();
    }
}
