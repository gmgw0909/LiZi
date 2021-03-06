package com.fancy.learncenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fancy.learncenter.adapter.PicAdapter;
import com.fancy.lizi.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PicPostFragment extends Fragment {

    PicAdapter picAdapter;
    ArrayList<String> itemDatas;

    @Bind(R.id.recycleView)
    RecyclerView recycleView;

    public PicPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pic_post, container, false);
        ButterKnife.bind(this, view);
        initPic();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initPic() {
        itemDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            itemDatas.add("");
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recycleView.setLayoutManager(linearLayoutManager);
        picAdapter = new PicAdapter(getActivity(), itemDatas);
        recycleView.setAdapter(picAdapter);
    }
}
