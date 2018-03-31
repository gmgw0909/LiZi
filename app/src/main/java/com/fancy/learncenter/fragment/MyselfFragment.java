package com.fancy.learncenter.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fancy.learncenter.activity.EditInfoActivity;
import com.fancy.learncenter.activity.FriendsSpaceActivity;
import com.fancy.learncenter.activity.LoginActivity;
import com.fancy.learncenter.activity.SettingActivity;
import com.fancy.learncenter.bean.BaseDataBean;
import com.fancy.learncenter.bean.RegistDataBean;
import com.fancy.learncenter.net.HttpMehtod;
import com.fancy.learncenter.net.IdeaObserver;
import com.fancy.lizi.R;
import com.yixia.camera.util.Log;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyselfFragment extends Fragment {


    @Bind(R.id.simpleDraweeView)
    SimpleDraweeView simpleDraweeView;
    @Bind(R.id.zan)
    TextView zan;
    @Bind(R.id.fans)
    TextView fans;
    @Bind(R.id.guanzhu)
    TextView guanzhu;
    @Bind(R.id.guanzhuwo)
    TextView guanzhuwo;
    @Bind(R.id.userName)
    TextView userName;
    @Bind(R.id.lvnian)
    TextView lvnian;

    public MyselfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myself, container, false);
        ButterKnife.bind(this, view);
        getUser();
        return view;
    }

    private void getUser() {
        HttpMehtod.getInstance().getUser(new IdeaObserver<BaseDataBean<RegistDataBean>>() {

            @Override
            public void onSuccess(BaseDataBean<RegistDataBean> bean) {
                RegistDataBean data = bean.getData();
                if (data != null) {
                    if (!TextUtils.isEmpty(data.getUsername())) {
                        userName.setText(data.getUsername());
                    } else {
                        userName.setText("");
                    }
                    if (!TextUtils.isEmpty(data.getAvatar())) {
                        simpleDraweeView.setImageURI(data.getAvatar());
                    }
                    zan.setText(data.getFavors() + "");
                    fans.setText(data.getFans() + "");
                    guanzhuwo.setText(data.getFans() + "");
                    guanzhu.setText(data.getFollows() + "");
                }
            }

            @Override
            public void onFail(String errorCode) {
                Log.d("Ok==errorCode==", errorCode);
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.edit, R.id.xiaoxi, R.id.simpleDraweeView, R.id.ll_my_release,
            R.id.ll_help, R.id.ll_advice, R.id.ll_about, R.id.ll_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit:
                startActivity(new Intent(getActivity(), EditInfoActivity.class));
                break;
            case R.id.xiaoxi:
                startActivity(new Intent(getActivity(), LoginActivity.class));
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
