package com.fancy.learncenter.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hyy on 2016/8/5.
 */
public class CustomViewHold extends RecyclerView.ViewHolder {


    private SparseArray<View> views;
    private View roorView;

    public CustomViewHold(View itemView) {
        super(itemView);
        views = new SparseArray<View>();

        this.roorView = itemView;
    }

    public static CustomViewHold creatViewHolder(Context context, ViewGroup parent, int resource) {
        View roorView = LayoutInflater.from(context).inflate(resource, parent, false);
        CustomViewHold mCustomViewHold = new CustomViewHold(roorView);
        return mCustomViewHold;
    }


    public View getRoorView() {
        return roorView;
    }

    public <T extends View> T getView(int resource) {
        View view = views.get(resource);

        if (view == null) {
            view = roorView.findViewById(resource);
            views.put(resource, view);
        }
        return (T) view;
    }
}