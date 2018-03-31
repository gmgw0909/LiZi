package com.fancy.learncenter.view;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fancy.lizi.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * LeeBoo
 */

public class PopupWindowBiaoQian extends BasedPopupWindow {

    @Bind(R.id.item1)
    TextView item1;
    @Bind(R.id.item2)
    TextView item2;
    @Bind(R.id.item3)
    TextView item3;
    @Bind(R.id.item4)
    TextView item4;
    @Bind(R.id.item5)
    TextView item5;
    @Bind(R.id.item6)
    TextView item6;
    @Bind(R.id.item7)
    TextView item7;
    @Bind(R.id.item8)
    TextView item8;
    @Bind(R.id.cancel)
    TextView tvCanCel;
    @Bind(R.id.ok)
    TextView tvOk;
    private View popuView;
    private View locationView;

    List<TextView> views = new ArrayList<>();
    List<String> list = new ArrayList<>();

    public PopupWindowBiaoQian(Activity mContext, View viewGroup) {
        super(mContext);
        popuView = LayoutInflater.from(mContext).inflate(R.layout.pupopwinodw_biaoqian, (ViewGroup) viewGroup, false);
        this.locationView = viewGroup;
        initPopuWindow();
    }

    private void initPopuWindow() {
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        update();
        setContentView(popuView);
        ButterKnife.bind(this, popuView);
        popuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        views.add(item1);
        views.add(item2);
        views.add(item3);
        views.add(item4);
        views.add(item5);
        views.add(item6);
        views.add(item7);
        views.add(item8);
    }


    @OnClick({R.id.cancel, R.id.item1, R.id.item2, R.id.item3,
            R.id.item4, R.id.item5, R.id.item6, R.id.item7, R.id.item8})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.item1:
                chooseItem(item1);
                break;
            case R.id.item2:
                chooseItem(item2);
                break;
            case R.id.item3:
                chooseItem(item3);
                break;
            case R.id.item4:
                chooseItem(item4);
                break;
            case R.id.item5:
                chooseItem(item5);
                break;
            case R.id.item6:
                chooseItem(item6);
                break;
            case R.id.item7:
                chooseItem(item7);
                break;
            case R.id.item8:
                chooseItem(item8);
                break;
        }
    }

    @Override
    public void showPopupWindow() {
        super.showPopupWindow();
        showAtLocation(locationView, Gravity.BOTTOM, 0, 0);
    }

    //标签选择
    private void chooseItem(TextView v) {
        if (v.getCurrentTextColor() == getContentView().getResources().getColor(R.color.text_gray)) {
            v.setBackgroundResource(R.drawable.shape_line_red);
            v.setTextColor(getContentView().getResources().getColor(R.color.red));
        } else {
            v.setBackgroundResource(R.drawable.shape_line_gray);
            v.setTextColor(getContentView().getResources().getColor(R.color.text_gray));
        }
    }

    public void setOnChooseItem(final OnChooseItem onChooseItem) {
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < views.size(); i++) {
                    if (views.get(i).getCurrentTextColor() == getContentView().getResources().getColor(R.color.red)) {
                        list.add(views.get(i).getText().toString());
                    }
                }
                onChooseItem.getItems(list);
                list.clear();
                dismiss();
            }
        });
    }

    public interface OnChooseItem {
        void getItems(List<String> list);
    }
}
