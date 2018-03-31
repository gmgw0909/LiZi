package com.fancy.learncenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fancy.learncenter.activity.base.BaseActivity;
import com.fancy.lizi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LeeBoo on 2018/1/22.
 */

public class EditQianMingActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.et_qianming)
    EditText etQianming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_qianming);
        ButterKnife.bind(this);
        title.setText("签名");
    }

    @OnClick({R.id.back, R.id.clear_et, R.id.commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.clear_et:
                break;
            case R.id.commit:
                break;
        }
    }
}
