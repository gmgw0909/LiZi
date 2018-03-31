package com.fancy.learncenter.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.andview.refreshview.XRefreshView;
import com.fancy.learncenter.adapter.FriendAdapter;
import com.fancy.learncenter.adapter.PicAdapter;
import com.fancy.learncenter.adapter.SosAdapter;
import com.fancy.learncenter.adapter.VideoAdapter;
import com.fancy.learncenter.bean.BaseDataBean;
import com.fancy.learncenter.net.HttpMehtod;
import com.fancy.learncenter.net.IdeaObserver;
import com.fancy.lizi.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    RecyclerView recycleView_sos;
    SosAdapter sosAdapter;

    RecyclerView recycleView_friend;
    FriendAdapter friendAdapter;

    PicAdapter picAdapter;
    RecyclerView picRecyclerView;

    VideoAdapter videoAdapter;
    ArrayList<String> videoDatas;
    View view;
    View topView;

    @Bind(R.id.xRefreshView)
    XRefreshView xRefreshView;
    @Bind(R.id.rootView)
    LinearLayout rootView;
    @Bind(R.id.recycleView)
    RecyclerView recycleView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_beauty, container, false);
        ButterKnife.bind(this, view);

        topView = inflater.inflate(R.layout.fragment_home_top, container, false);
        initView();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initView() {
        videoDatas = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            videoDatas.add(" ");
        }

        xRefreshView.setPullRefreshEnable(false);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 2);
        recycleView.setLayoutManager(gridLayoutManager2);
        videoAdapter = new VideoAdapter(getActivity(), videoDatas);
        recycleView.setAdapter(videoAdapter);
        videoAdapter.setHeaderView(topView, recycleView);

        initPic();
        initFriend();
        initSos();

        getVideoPost();
    }

    private void initPic() {
        picRecyclerView = (RecyclerView) topView.findViewById(R.id.reecycleView_pic);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        picRecyclerView.setLayoutManager(linearLayoutManager);
        picAdapter = new PicAdapter(getActivity(), videoDatas);
        picRecyclerView.setAdapter(picAdapter);
    }

    private void initFriend() {

        recycleView_friend = (RecyclerView) topView.findViewById(R.id.recycleView_friend);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recycleView_friend.setLayoutManager(linearLayoutManager);
        friendAdapter = new FriendAdapter(getActivity(), videoDatas);
        recycleView_friend.setAdapter(friendAdapter);
    }


    private void initSos() {

        recycleView_sos = (RecyclerView) topView.findViewById(R.id.recycleView_sos);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recycleView_sos.setLayoutManager(linearLayoutManager);
        sosAdapter = new SosAdapter(getActivity(), videoDatas);
        recycleView_sos.setAdapter(sosAdapter);
    }

    private void getVideoPost() {
        Map map = new HashMap();
        map.put("type", "3");
        map.put("gid", "3");
        map.put("ord", "newest");
        map.put("pageSize", "10");
        map.put("pn", "1");
        HttpMehtod.getInstance().getPost(map, new IdeaObserver<BaseDataBean>() {
            @Override
            public void onSuccess(BaseDataBean baseDataBean) {

            }

            @Override
            public void onFail(String errorCode) {

            }
        });

    }


}
