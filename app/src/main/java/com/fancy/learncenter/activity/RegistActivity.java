package com.fancy.learncenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fancy.learncenter.activity.base.BaseActivity;
import com.fancy.learncenter.bean.BaseDataBean;
import com.fancy.learncenter.bean.RegistDataBean;
import com.fancy.learncenter.net.HttpMehtod;
import com.fancy.learncenter.net.IdeaObserver;
import com.fancy.learncenter.utils.TimeUtil;
import com.fancy.learncenter.utils.ToastUtil;
import com.fancy.learncenter.utils.Utils;
import com.fancy.lizi.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistActivity extends BaseActivity {


    @Bind(R.id.ed_account)
    EditText edAccount;
    @Bind(R.id.ed_yzm)
    EditText edYzm;
    @Bind(R.id.ed_psw)
    EditText edPsw;
    @Bind(R.id.bt_regist)
    Button btRegist;
    @Bind(R.id.tv_forget)
    TextView tvPsw;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.get_yzm)
    TextView getYzm;
    @Bind(R.id.agree_icon)
    ImageView agreeIcon;

    boolean isAgree;
    @Bind(R.id.ed_psw_again)
    EditText edPswAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist, Status.STATUS);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_regist, R.id.tv_forget, R.id.select_agree, R.id.tv_login, R.id.get_yzm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_regist:

                if (Utils.isChinaPhoneLegal(edAccount.getText().toString())) {
                    if (!TextUtils.isEmpty(edYzm.getText().toString())) {
                        if (edPswAgain.getText().toString().equals(edPsw.getText().toString())) {
                            if (isAgree) {
                                regist();
                            } else {
                                ToastUtil.show("请先阅读注册协议并确认");
                            }
                        } else {
                            ToastUtil.show("两次密码不一致");
                        }
                    } else {
                        ToastUtil.show("验证码不能为空");
                    }
                } else {
                    ToastUtil.show("请输入正确的手机号");
                }

                break;
            case R.id.tv_forget:
                break;
            case R.id.select_agree:
                if (isAgree) {
                    isAgree = false;
                    agreeIcon.setImageResource(R.mipmap.register_agree);
                } else {
                    isAgree = true;
                    agreeIcon.setImageResource(R.mipmap.register_agreed);
                }
                break;
            case R.id.get_yzm:
                if (Utils.isChinaPhoneLegal(edAccount.getText().toString())) {
                    getYZM(edAccount.getText().toString());
                } else {
                    ToastUtil.show("请输入正确的手机号");
                }
                break;
            case R.id.tv_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void getYZM(String mobile) {
        getYzm.setClickable(false);
//        ToastUtil.show("开始获取验证码" + mobile);
        HttpMehtod.getInstance().getYZM(mobile, new IdeaObserver<BaseDataBean<String>>() {

            @Override
            public void onSuccess(BaseDataBean<String> stringBaseDataBean) {

                TimeUtil.interval(60, new TimeUtil.TimeCallBack() {
                    @Override
                    public void nextCallBack(long time) {
                        getYzm.setText(time + " s");
                    }

                    @Override
                    public void completeCallBack() {
                        getYzm.setClickable(true);
                        getYzm.setText("获取验证码");
                    }

                    @Override
                    public void startCallBack() {

                    }
                });
            }

            @Override
            public void onFail(String errorCode) {
                getYzm.setClickable(true);
            }

        });
    }


    private void regist() {
        Map map = new HashMap();
        map.put("mobile", edAccount.getText().toString());
        map.put("username", edAccount.getText().toString().substring(6));
        map.put("inputCode", edYzm.getText().toString());
        map.put("password", edPsw.getText().toString());

        HttpMehtod.getInstance().register(map, new IdeaObserver<BaseDataBean<RegistDataBean>>() {

            @Override
            public void onSuccess(BaseDataBean<RegistDataBean> stringBaseDataBean) {
                ToastUtil.show("注册成功");
                finish();
            }

            @Override
            public void onFail(String errorCode) {

            }

        });
    }
}