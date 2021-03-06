package com.fancy.learncenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.fancy.learncenter.activity.GraphicDetailsActivity;
import com.fancy.learncenter.adapter.base.CommonRecycleViewAdapter;
import com.fancy.learncenter.adapter.base.CustomViewHold;
import com.fancy.lizi.R;

import java.util.ArrayList;

/**
 * Created by hyy on 2018/1/2.
 * describe as
 */

public class PicAdapter extends CommonRecycleViewAdapter<String> {

    public PicAdapter(Context mContext, ArrayList<String> itemDatas) {
        super(mContext, R.layout.pic_item, itemDatas);
    }

    @Override
    public void bindView(CustomViewHold customViewHold, String item, int position) {
        customViewHold.getRoorView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GraphicDetailsActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}
