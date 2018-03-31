package com.fancy.learncenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fancy.learncenter.adapter.VideoAdapter;
import com.fancy.lizi.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {

    VideoAdapter videoAdapter;
    ArrayList<String> videoDatas;

    @Bind(R.id.recycleView)
    RecyclerView recycleView;

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this, view);
        initVideo();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initVideo() {
        videoDatas = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            videoDatas.add("");
        }
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 2);
        recycleView.setLayoutManager(gridLayoutManager2);
        videoAdapter = new VideoAdapter(getActivity(), videoDatas);
        recycleView.setAdapter(videoAdapter);
    }
}
