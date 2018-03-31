package com.fancy.learncenter.adapter;

import android.content.Context;

import com.fancy.learncenter.adapter.base.CommonRecycleViewAdapter;
import com.fancy.learncenter.adapter.base.CustomViewHold;
import com.fancy.lizi.R;

import java.util.ArrayList;

/**
 * Created by hyy on 2018/1/4.
 * describe as
 */

public class FriendMsgAdapter extends CommonRecycleViewAdapter<String> {

    public FriendMsgAdapter(Context mContext, ArrayList<String> itemDatas) {
        super(mContext, R.layout.firend_msg_item, itemDatas);
    }

    @Override
    public void bindView(CustomViewHold customViewHold, String item, int position) {

    }
}
