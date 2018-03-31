package com.fancy.learncenter.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.fancy.lizi.R;

/**
 * Created by zhpan on 2017/4/13.
 */

public class CustomProgressDialog {
    public static Dialog creatRequestDialog(Context context) {

        Dialog dialog = new Dialog(context, R.style.dlg_request_style);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_progress);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();

        int width = display.getWidth();

        lp.width = (int) (0.6 * width);

//        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) dialog.findViewById(R.id.loading);
//        String urlPath = "res://" + context.getPackageName() + "/" + R.mipmap.load;
//        DraweeController draweeController =
//                Fresco.newDraweeControllerBuilder()
//                        .setUri(urlPath)
//                        .setOldController(simpleDraweeView.getController())
//                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
//                        .build();
//        simpleDraweeView.setController(draweeController);


        TextView titleTxtv = (TextView) dialog.findViewById(R.id.tvLoad);
//        if (tip == null || tip.length() == 0) {
//            titleTxtv.setText("加载中");
//        } else {
//            titleTxtv.setText("加载中");
//        }
        return dialog;
    }

}
