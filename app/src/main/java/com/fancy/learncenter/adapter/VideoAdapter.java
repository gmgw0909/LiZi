package com.fancy.learncenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.fancy.learncenter.activity.VideoDetailActivity;
import com.fancy.learncenter.adapter.base.CommonRecycleViewAdapter;
import com.fancy.learncenter.adapter.base.CustomViewHold;
import com.fancy.learncenter.utils.Utils;
import com.fancy.lizi.R;

import java.util.ArrayList;

/**
 * Created by hyy on 2018/1/2.
 * describe as
 */

public class VideoAdapter extends CommonRecycleViewAdapter<String> {

    public VideoAdapter(Context mContext, ArrayList<String> itemDatas) {
        super(mContext, R.layout.video_item, itemDatas);

    }

    @Override
    public void bindView(CustomViewHold customViewHold, String item, int position) {

        customViewHold.getRoorView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, VideoDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}
