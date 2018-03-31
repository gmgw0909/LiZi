package com.fancy.learncenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fancy.learncenter.activity.base.BaseActivity;
import com.fancy.learncenter.bean.BaseDataBean;
import com.fancy.learncenter.common.Constant;
import com.fancy.learncenter.net.HttpMehtod;
import com.fancy.learncenter.net.IdeaObserver;
import com.fancy.learncenter.utils.LogUtil;
import com.fancy.learncenter.utils.ToastUtil;
import com.fancy.learncenter.utils.Utils;
import com.fancy.lizi.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.ed_account)
    EditText edAccount;
    @Bind(R.id.ed_psw)
    EditText edPsw;
    @Bind(R.id.bt_login)
    Button btLogin;
    @Bind(R.id.tv_forget)
    TextView tvForget;
    @Bind(R.id.tv_regist)
    TextView tvRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login, Status.STATUS);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_login, R.id.tv_forget, R.id.tv_regist})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.bt_login:

                if (Utils.isChinaPhoneLegal(edAccount.getText().toString())) {
                    login();
                } else {
                    ToastUtil.show("请输入正确的账号");
                }
                break;
            case R.id.tv_forget:
                intent = new Intent(this, ForgetActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_regist:
                intent = new Intent(this, RegistActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void login() {
        Map map = new HashMap();
        map.put("accountName", edAccount.getText().toString());
        map.put("password", edPsw.getText().toString());
        map.put("rememberMe", "1");
        HttpMehtod.getInstance().login(map, new IdeaObserver<BaseDataBean>() {
            @Override
            public void onSuccess(BaseDataBean stringBaseDataBean) {
                if (stringBaseDataBean.getCode() == IdeaObserver.SUCCESS_CODE) {
                    gotoMain();
                } else {
                }
            }

            @Override
            public void onFail(String errorCode) {
                LogUtil.MyLog("login", "===login==onFail==" + errorCode);
            }
        });
    }

    private void gotoMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
