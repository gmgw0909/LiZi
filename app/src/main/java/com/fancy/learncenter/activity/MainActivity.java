package com.fancy.learncenter.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fancy.learncenter.activity.base.BaseActivity;
import com.fancy.learncenter.fragment.FancyFragment;
import com.fancy.learncenter.fragment.FriendFragment;
import com.fancy.learncenter.fragment.HomeFragment;
import com.fancy.learncenter.fragment.MyselfFragment;
import com.fancy.learncenter.fragment.ReleaseFragment;
import com.fancy.learncenter.utils.ToastUtil;
import com.fancy.learncenter.view.PopupWindowRelease;
import com.fancy.lizi.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.framelayout_contain)
    FrameLayout framelayoutContain;
    @Bind(R.id.home_icon)
    ImageView homeIcon;
    @Bind(R.id.home_font)
    TextView homeFont;
    @Bind(R.id.friend_icon)
    ImageView friendIcon;
    @Bind(R.id.friend_font)
    TextView friendFont;
    @Bind(R.id.fancy_icon)
    ImageView fancyIcon;
    @Bind(R.id.fancy_font)
    TextView fancyFont;
    @Bind(R.id.my_icon)
    ImageView myIcon;
    @Bind(R.id.my_font)
    TextView myFont;

    private HomeFragment beautyFragment;
    private FriendFragment kwDiffAndEasyFragment;
    private ReleaseFragment onlineReviewsFragment;
    private FancyFragment turandotFragment;
    private MyselfFragment myselfFragment;
    private Fragment tempFrag;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main, Color.parseColor("#20a4e9"));
        ButterKnife.bind(this);
        showIndex(1);
        //nihaio
        //login
        EMClient.getInstance().login("12345", "12345", new EMCallBack() {

            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        ToastUtil.show("环信登录成功");
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String error) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        ToastUtil.show("环信登录失败");
                    }
                });
            }
        });
    }

    private void showIndex(int index) {
        changeUi(index);
        switch (index) {
            case 1:
                transaction = getSupportFragmentManager().beginTransaction();
                if (beautyFragment == null) {
                    beautyFragment = new HomeFragment();
                    transaction.add(R.id.framelayout_contain, beautyFragment);
                }

                if (tempFrag == beautyFragment) {
                    return;
                } else if (tempFrag != null) {
                    transaction.hide(tempFrag);
                } else {
                    tempFrag = beautyFragment;
                }
                transaction.show(beautyFragment);
                transaction.commit();
                tempFrag = beautyFragment;
                break;
            case 2:
                transaction = getSupportFragmentManager().beginTransaction();
                if (kwDiffAndEasyFragment == null) {
                    kwDiffAndEasyFragment = new FriendFragment();
                    transaction.add(R.id.framelayout_contain, kwDiffAndEasyFragment);
                }

                if (tempFrag == kwDiffAndEasyFragment) {
                    return;
                } else if (tempFrag != null) {
                    transaction.hide(tempFrag);
                }
                transaction.show(kwDiffAndEasyFragment);
                transaction.commit();
                tempFrag = kwDiffAndEasyFragment;
                break;
            case 3:
//                transaction = getSupportFragmentManager().beginTransaction();
//                if (onlineReviewsFragment == null) {
//                    onlineReviewsFragment = new ReleaseFragment();
//                    transaction.add(R.id.framelayout_contain, onlineReviewsFragment);
//                }
//                if (tempFrag == onlineReviewsFragment) {
//                    return;
//                } else if (tempFrag != null) {
//                    transaction.hide(tempFrag);
//                }
//                transaction.show(onlineReviewsFragment);
//                transaction.commit();
//
//                tempFrag = onlineReviewsFragment;

                break;
            case 4:
                transaction = getSupportFragmentManager().beginTransaction();
                if (turandotFragment == null) {
                    turandotFragment = new FancyFragment();
                    transaction.add(R.id.framelayout_contain, turandotFragment);
                }
                if (tempFrag == turandotFragment) {
                    return;
                } else if (tempFrag != null) {
                    transaction.hide(tempFrag);
                }
                transaction.show(turandotFragment);
                transaction.commit();
                tempFrag = turandotFragment;
                break;
            case 5:
                transaction = getSupportFragmentManager().beginTransaction();
                if (myselfFragment == null) {
                    myselfFragment = new MyselfFragment();
                    transaction.add(R.id.framelayout_contain, myselfFragment);
                }
                if (tempFrag == myselfFragment) {
                    return;
                } else if (tempFrag != null) {
                    transaction.hide(tempFrag);
                }
                transaction.show(myselfFragment);
                transaction.commit();
                tempFrag = myselfFragment;
                break;
        }
    }

    @OnClick({R.id.home, R.id.friend, R.id.release, R.id.fancy, R.id.my})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home:
                showIndex(1);
                break;
            case R.id.friend:
                showIndex(2);
                break;
            case R.id.release:
                show();
                break;
            case R.id.fancy:
                showIndex(4);
                break;
            case R.id.my:
                showIndex(5);
                break;
        }
    }

    private void changeUi(int index) {
        homeIcon.setImageResource(R.mipmap.default_home);
        homeFont.setTextColor(getResources().getColor(R.color.black));

        friendIcon.setImageResource(R.mipmap.default_friend);
        friendFont.setTextColor(getResources().getColor(R.color.black));

        fancyIcon.setImageResource(R.mipmap.default_fancy);
        fancyFont.setTextColor(getResources().getColor(R.color.black));


        myIcon.setImageResource(R.mipmap.default_my);
        myFont.setTextColor(getResources().getColor(R.color.black));

        switch (index) {
            case 1:
                homeIcon.setImageResource(R.mipmap.default_home_selected);
                homeFont.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 2:
                friendIcon.setImageResource(R.mipmap.default_friend_select);
                friendFont.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 3:
                break;
            case 4:
                fancyIcon.setImageResource(R.mipmap.default_fancy_select);
                fancyFont.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 5:
                myIcon.setImageResource(R.mipmap.default_my_select);
                myFont.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;

        }
    }

    private void show() {
        PopupWindowRelease popupWindowRelease = new PopupWindowRelease(this, framelayoutContain);
        popupWindowRelease.showPopupWindow();
    }

    /**
     * 定位权限授权回调
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
    }
}
