package com.fancy.learncenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fancy.learncenter.activity.base.BaseActivity;
import com.fancy.learncenter.view.LiZhiDialog;
import com.fancy.lizi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LeeBoo on 2018/1/22.
 */

public class FriendsJuBaoActivity extends BaseActivity {

    @Bind(R.id.btn1)
    TextView btn1;
    @Bind(R.id.btn2)
    TextView btn2;
    @Bind(R.id.btn3)
    TextView btn3;
    @Bind(R.id.btn4)
    TextView btn4;
    @Bind(R.id.btn5)
    TextView btn5;
    @Bind(R.id.btn6)
    TextView btn6;
    @Bind(R.id.title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_jubao);
        ButterKnife.bind(this);
        title.setText("举报");
    }

    @OnClick({R.id.commit, R.id.back, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.commit:
                LiZhiDialog.showWithOneBtn(this, R.mipmap.bg_jubao_tuxing, "举报资料提交成功！",
                        "知道了", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                                LiZhiDialog.dismissDialog();
                            }
                        });
                break;
            case R.id.btn1:
                chooseItem(btn1);
                break;
            case R.id.btn2:
                chooseItem(btn2);
                break;
            case R.id.btn3:
                chooseItem(btn3);
                break;
            case R.id.btn4:
                chooseItem(btn4);
                break;
            case R.id.btn5:
                chooseItem(btn5);
                break;
            case R.id.btn6:
                chooseItem(btn6);
                break;
        }
    }

    //标签选择
    private void chooseItem(TextView v) {
        if (v.getCurrentTextColor() == getResources().getColor(R.color.text_normal)) {
            v.setBackgroundResource(R.drawable.shape_line_red);
            v.setTextColor(getResources().getColor(R.color.red));
        } else {
            v.setBackgroundResource(R.drawable.shape_line_gray);
            v.setTextColor(getResources().getColor(R.color.text_normal));
        }
    }
}
