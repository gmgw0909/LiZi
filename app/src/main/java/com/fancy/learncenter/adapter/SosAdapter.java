package com.fancy.learncenter.adapter;

import android.content.Context;

import com.fancy.learncenter.adapter.base.CommonRecycleViewAdapter;
import com.fancy.learncenter.adapter.base.CustomViewHold;
import com.fancy.lizi.R;

import java.util.ArrayList;

/**
 * Created by hyy on 2018/1/2.
 * describe as
 */

public class SosAdapter extends CommonRecycleViewAdapter<String> {

    public SosAdapter(Context mContext, ArrayList<String> itemDatas) {
        super(mContext, R.layout.fragment_home_jiuzu_item, itemDatas);
    }

    @Override
    public void bindView(CustomViewHold customViewHold, String item, int position) {

    }
}
