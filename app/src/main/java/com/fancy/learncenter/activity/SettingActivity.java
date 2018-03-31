package com.fancy.learncenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fancy.learncenter.activity.base.BaseActivity;
import com.fancy.lizi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LeeBoo on 2018/1/22.
 */

public class SettingActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        title.setText("设置");
    }

    @OnClick({R.id.back, R.id.clear_cache, R.id.switch_btn, R.id.modify_pw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.clear_cache:
                break;
            case R.id.switch_btn:
                break;
            case R.id.modify_pw:
                startActivity(new Intent(SettingActivity.this, ModifyPassWordActivity.class));
                break;
        }
    }
}
