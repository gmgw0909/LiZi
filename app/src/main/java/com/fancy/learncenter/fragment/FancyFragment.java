package com.fancy.learncenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fancy.learncenter.adapter.FragPagerAdapter;
import com.fancy.learncenter.utils.DimenUtil;
import com.fancy.learncenter.view.LPopWindow;
import com.fancy.lizi.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FancyFragment extends Fragment {

    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.pic_post)
    TextView picPost;
    @Bind(R.id.video_post)
    TextView videoPost;

    public FancyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fancy, container, false);
        ButterKnife.bind(this, view);
        initViewPager();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new PicPostFragment());
        fragments.add(new VideoFragment());

        List titles = new ArrayList();
        titles.add("视频");
        titles.add("音频");
        FragPagerAdapter adapter = new FragPagerAdapter(getChildFragmentManager(), fragments, titles);
        //给ViewPager设置适配器
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    picPost.setBackground(null);
                    videoPost.setBackgroundResource(R.drawable.fancy_top_right_shape);
                    picPost.setTextColor(getResources().getColor(R.color.colorPrimary));
                    videoPost.setTextColor(getResources().getColor(R.color.white));

                } else if (position == 1) {
                    picPost.setBackgroundResource(R.drawable.fancy_top_left_shape);
                    videoPost.setBackground(null);
                    picPost.setTextColor(getResources().getColor(R.color.white));
                    videoPost.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.pic_post, R.id.video_post, R.id.shaixuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pic_post:
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.video_post:
                viewPager.setCurrentItem(1, true);
                break;
            case R.id.shaixuan:
                initSXPopWindow(view);
                break;
        }
    }

    //筛选PopWindow
    LPopWindow lPopWindow;

    private void initSXPopWindow(View view) {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_shaixuan, null);
        TextView item1 = (TextView) contentView.findViewById(R.id.item1);
        TextView item2 = (TextView) contentView.findViewById(R.id.item2);
        TextView item3 = (TextView) contentView.findViewById(R.id.item3);
        TextView item4 = (TextView) contentView.findViewById(R.id.item4);
        TextView item5 = (TextView) contentView.findViewById(R.id.item5);
        TextView item6 = (TextView) contentView.findViewById(R.id.item6);
        initItem(item1);
        initItem(item2);
        initItem(item3);
        initItem(item4);
        initItem(item5);
        initItem(item6);
        contentView.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lPopWindow.dismiss();
            }
        });
        contentView.findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lPopWindow.dismiss();
            }
        });
        lPopWindow = new LPopWindow.Builder(getActivity())
                .setView(contentView)
                .size(DimenUtil.dip2px(200), ViewGroup.LayoutParams.MATCH_PARENT)
                .setAnimationStyle(R.style.DialogLeft)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .build()
                .showAtLocation(view, Gravity.RIGHT, 0, 0);
    }

    //筛选PopWindow item
    private void initItem(final TextView tv) {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                    tv.setBackgroundResource(R.drawable.btn_xuanzhong);
                    tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    tv.setBackgroundResource(R.drawable.shape_line_white);
                    tv.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });
    }
}
