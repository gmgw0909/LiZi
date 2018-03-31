package com.fancy.learncenter.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.fancy.learncenter.activity.base.BaseActivity;
import com.fancy.learncenter.utils.ToastUtil;
import com.fancy.lizi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetActivity extends BaseActivity {


    @Bind(R.id.ed_account)
    EditText edAccount;
    @Bind(R.id.ed_yzm)
    EditText edYzm;
    @Bind(R.id.ed_psw)
    EditText edPsw;
    @Bind(R.id.ed_psw_again)
    EditText edPswAgain;
    @Bind(R.id.bt_regist)
    Button btRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget, Status.STATUS);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_regist)
    public void onViewClicked() {
        ToastUtil.show("确认重置");
    }
}
