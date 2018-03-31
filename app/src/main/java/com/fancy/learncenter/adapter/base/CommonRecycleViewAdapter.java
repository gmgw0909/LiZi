package com.fancy.learncenter.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by Hyy on 2016/8/5.
 */
public abstract class CommonRecycleViewAdapter<T> extends BaseRecyclerAdapter<CustomViewHold> {

    protected Context mContext;
    int resource;
    ArrayList<T> itemDatas;

    public CommonRecycleViewAdapter(Context mContext, int resource, ArrayList<T> itemDatas) {
        this.mContext = mContext;
        this.resource = resource;
        this.itemDatas = itemDatas;
    }


    @Override
    public CustomViewHold getViewHolder(View view) {
        return new CustomViewHold(view);
    }

    @Override
    public CustomViewHold onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        return CustomViewHold.creatViewHolder(mContext, parent, resource);
    }

    @Override
    public void onBindViewHolder(CustomViewHold holder, int position, boolean isItem) {
        bindView(holder, itemDatas.get(position), position);
    }

    @Override
    public int getAdapterItemCount() {
        return itemDatas.size();
    }

    public abstract void bindView(CustomViewHold customViewHold, T item, int position);

    public void notifyDataSetChanged(ArrayList<T> newDatas) {
        itemDatas.clear();
        itemDatas.addAll(newDatas);
        this.notifyDataSetChanged();
    }

}
