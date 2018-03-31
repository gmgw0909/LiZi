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

public class ReleaseGraphicNextActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_releasegraphic_next, Status.Default);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.next, R.id.item1, R.id.item2, R.id.item3,
            R.id.item4, R.id.item5, R.id.item6, R.id.item7, R.id.item8})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.next:
                LiZhiDialog.showWithOneBtn(this, R.mipmap.bg_chenggong, "恭喜您，发布成功！",
                        "立即查看", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                                LiZhiDialog.dismissDialog();
                            }
                        });
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

    //标签选择
    private void chooseItem(TextView v) {
        if (v.getCurrentTextColor() == getResources().getColor(R.color.text_gray)) {
            v.setBackgroundResource(R.drawable.shape_line_red);
            v.setTextColor(getResources().getColor(R.color.red));
        } else {
            v.setBackgroundResource(R.drawable.shape_line_gray);
            v.setTextColor(getResources().getColor(R.color.text_gray));
        }
    }

}
