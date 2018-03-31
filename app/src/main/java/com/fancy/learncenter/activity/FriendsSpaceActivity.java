package com.fancy.learncenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fancy.learncenter.activity.base.BaseActivity;
import com.fancy.learncenter.adapter.FragPagerAdapter;
import com.fancy.learncenter.fragment.PicPostFragment;
import com.fancy.learncenter.fragment.VideoFragment;
import com.fancy.learncenter.view.CircleImageView;
import com.fancy.lizi.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by XINDU_L_011 on 2018/1/22.
 */

public class FriendsSpaceActivity extends BaseActivity {

    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.travel_age)
    TextView travelAge;
    @Bind(R.id.iamge_user)
    CircleImageView iamgeUser;
    @Bind(R.id.ll_head)
    LinearLayout llHead;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    FragPagerAdapter mAdapter;
    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_space);
        ButterKnife.bind(this);
        titles.add("图文");
        titles.add("视频");
        fragments.add(new PicPostFragment());
        fragments.add(new VideoFragment());
        mAdapter = new FragPagerAdapter(getSupportFragmentManager(), fragments, titles);
        //设置 NestedScrollView 的内容是否拉伸填充整个视图，
        //这个设置是必须的，否者我们在里面设置的ViewPager会不可见
        nestedScrollView.setFillViewport(true);
        viewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(viewPager);
    }

    @OnClick({R.id.back, R.id.jubao, R.id.blacklist, R.id.go_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.jubao:
                Intent intent = new Intent(FriendsSpaceActivity.this, FriendsJuBaoActivity.class);
                startActivity(intent);
                break;
            case R.id.blacklist:
                break;
            case R.id.go_home:
                finish();
                break;
        }
    }
}
