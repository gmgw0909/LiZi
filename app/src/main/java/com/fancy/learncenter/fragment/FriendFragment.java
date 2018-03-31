package com.fancy.learncenter.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fancy.learncenter.activity.ChatActivity;
import com.fancy.learncenter.adapter.FragPagerAdapter;
import com.fancy.lizi.R;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends Fragment {

    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.nearby_post)
    TextView nearbyPost;
    @Bind(R.id.msg_post)
    TextView msgPost;

    private EaseConversationListFragment conversationListFragment;
    private FriendNearbyFragment friendNearbyFragment;


    public FriendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_firend, container, false);
        ButterKnife.bind(this, view);
        friendNearbyFragment = new FriendNearbyFragment();
        conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(getActivity(), ChatActivity.class)
                        .putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
            }
        });
        //初始化ViewPager
        initViewPager();
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
        return view;
    }

    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                EaseUI.getInstance().getNotifier().onNewMsg(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };

    private void refreshUIWithMessage() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if (conversationListFragment != null) {
                    conversationListFragment.refresh();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
    }

    private void initViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(friendNearbyFragment);
        fragments.add(conversationListFragment);

        List titles = new ArrayList();
        titles.add("");
        titles.add("");
        FragPagerAdapter adapter =
                new FragPagerAdapter(getChildFragmentManager(), fragments, titles);
        //给ViewPager设置适配器
        viewPager.setAdapter(adapter);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    msgPost.setBackgroundResource(R.drawable.fancy_top_right_shape);
                    nearbyPost.setBackground(null);

                    msgPost.setTextColor(getResources().getColor(R.color.white));
                    nearbyPost.setTextColor(getResources().getColor(R.color.colorPrimary));

                } else if (position == 1) {
                    msgPost.setBackground(null);
                    nearbyPost.setBackgroundResource(R.drawable.fancy_top_left_shape);

                    msgPost.setTextColor(getResources().getColor(R.color.colorPrimary));
                    nearbyPost.setTextColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.msg_post, R.id.nearby_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nearby_post:
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.msg_post:
                viewPager.setCurrentItem(1, true);
                break;

        }
    }
}
