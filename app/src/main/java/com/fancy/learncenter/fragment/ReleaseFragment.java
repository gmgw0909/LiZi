package com.fancy.learncenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fancy.lizi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReleaseFragment extends Fragment {


    public ReleaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_online_reviews, container, false);
    }

}
