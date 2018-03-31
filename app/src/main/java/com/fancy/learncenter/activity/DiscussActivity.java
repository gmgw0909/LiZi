package com.fancy.learncenter.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fancy.learncenter.activity.base.BaseActivity;
import com.fancy.learncenter.adapter.DiscussAdapter;
import com.fancy.learncenter.utils.KeyboardChangeListener;
import com.fancy.lizi.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LeeBoo on 2018/1/22.
 */

public class DiscussActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    @Bind(R.id.rl_send)
    RelativeLayout rlSend;
    @Bind(R.id.ll_send)
    View llSend;

    DiscussAdapter discussAdapter;
    ArrayList<String> discussDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);
        ButterKnife.bind(this);
        title.setText("评论列表");

        discussDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            discussDatas.add("");
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);
        discussAdapter = new DiscussAdapter(this, discussDatas);
        recycleView.setAdapter(discussAdapter);
        //键盘打开关闭监听器
        new KeyboardChangeListener(this).setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                if (isShow) {
                    llSend.setVisibility(View.VISIBLE);
                    rlSend.setVisibility(View.GONE);
                } else {
                    llSend.setVisibility(View.GONE);
                    rlSend.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

}
