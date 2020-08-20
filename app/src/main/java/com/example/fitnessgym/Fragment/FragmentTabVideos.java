package com.example.fitnessgym.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessgym.R;


public class FragmentTabVideos extends Fragment {


    public FragmentTabVideos() {
        // Required empty public constructor
    }




    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_videos, container, false);
        return view;
    }
}
