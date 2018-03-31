package com.fancy.learncenter.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fancy.learncenter.activity.EditInfoActivity;
import com.fancy.learncenter.activity.FriendsSpaceActivity;
import com.fancy.learncenter.activity.SettingActivity;
import com.fancy.lizi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyselfFragment extends Fragment {


    @Bind(R.id.simpleDraweeView)
    SimpleDraweeView simpleDraweeView;

    public MyselfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myself, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.edit, R.id.xiaoxi,R.id.simpleDraweeView, R.id.ll_my_release,
            R.id.ll_help, R.id.ll_advice, R.id.ll_about, R.id.ll_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit:
                startActivity(new Intent(getActivity(), EditInfoActivity.class));
                break;
            case R.id.xiaoxi:
                break;
            case R.id.simpleDraweeView:
                startActivity(new Intent(getActivity(), FriendsSpaceActivity.class));
                break;
            case R.id.ll_my_release:
                break;
            case R.id.ll_help:
                break;
            case R.id.ll_advice:
                break;
            case R.id.ll_about:
                break;
            case R.id.ll_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }
}
