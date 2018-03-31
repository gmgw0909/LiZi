package com.fancy.learncenter.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fancy.learncenter.adapter.base.CommonRecycleViewAdapter;
import com.fancy.learncenter.adapter.base.CustomViewHold;
import com.fancy.lizi.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by hyy on 2018/1/18.
 * describe as
 */

public class GraphicAdapter extends CommonRecycleViewAdapter<String> {
    public GraphicAdapter(Context mContext, ArrayList<String> itemDatas) {
        super(mContext, R.layout.activity_releasegraphic_item, itemDatas);
    }

    @Override
    public void bindView(CustomViewHold customViewHold, String item, final int position) {
        FrameLayout image_temp = customViewHold.getView(R.id.image_temp);

        image_temp.setVisibility(View.VISIBLE);
        SimpleDraweeView simpleDraweeView = customViewHold.getView(R.id.simpleDraweeView);
        if (TextUtils.isEmpty(item)) {
            image_temp.setVisibility(View.GONE);
        } else {

            simpleDraweeView.setImageURI(Uri.fromFile(new File(item)));
        }

        EditText et_content = customViewHold.getView(R.id.et_content);


        et_content.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    focusPosition = position;
                } else {
                    // 此处为失去焦点时的处理内容
                }
            }
        });

        if (picCallBack != null) {
            simpleDraweeView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if (picCallBack != null) {
                        picCallBack.picCallBack(focusPosition, position);
                    }

                    return false;
                }
            });
        }
    }

    public int focusPosition = 0;

    private SetPicCallBack picCallBack;

    public void setPicCallBack(SetPicCallBack picCallBack) {
        this.picCallBack = picCallBack;
    }

    public interface SetPicCallBack {
        abstract void picCallBack(int focusPosition, int position);
    }
}
