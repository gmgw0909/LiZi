package com.fancy.learncenter.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.fancy.lizi.R;

/**
 * 创建者：  LeeBoo
 * 创建时间：2016/10/24 14:15
 */
public class LiZhiDialog extends Dialog {

    private static LiZhiDialog dialog;

    //style引用style样式
    public LiZhiDialog(Context context, int width, int height, View layout, int style) {

        super(context, style);

        setContentView(layout);

        Window window = getWindow();

        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.CENTER;

        params.y = -160;// 设置竖直偏移量

        window.setAttributes(params);
    }

    public static void show(Context context, int iconId, String content, boolean twoBtn, String noStr,
                            String yesStr, View.OnClickListener noClickListener, View.OnClickListener yesClickListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog, null);
        TextView textView = (TextView) view.findViewById(R.id.content);
        TextView noBtn = (TextView) view.findViewById(R.id.no_btn);
        TextView yesBtn = (TextView) view.findViewById(R.id.yes_btn);
        ImageView iconIv = (ImageView) view.findViewById(R.id.icon);
        textView.setText(content);
        if (!twoBtn) {
            noBtn.setVisibility(View.GONE);
        } else {
            noBtn.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(noStr)) {
                noBtn.setText(noStr);
            }
            if (noClickListener != null) {
                noBtn.setOnClickListener(noClickListener);
            }
        }
        yesBtn.setText(yesStr);
        iconIv.setImageResource(iconId);
        yesBtn.setOnClickListener(yesClickListener);
        dialog = new LiZhiDialog(context, 0, 0, view, R.style.InsuranceChoose);
        dialog.show();
    }

    public static void showWithOneBtn(Context context, int iconId, String content, String yesStr, View.OnClickListener yesClickListener) {
        show(context, iconId, content, false, "", yesStr, null, yesClickListener);
    }

    public static void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
