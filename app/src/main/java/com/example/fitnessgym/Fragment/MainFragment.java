package com.example.fitnessgym.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessgym.Adapter.SlideShow_adapter_main;
import com.example.fitnessgym.Model.ModelSliderImg;
import com.example.fitnessgym.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.google.android.material.shape.ShapePathModel;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class MainFragment extends Fragment {


    CircleIndicator indicator;
    Handler handler;
    Runnable runnable;
    Timer timer;
    ViewPager viewPager_slid_img;
    SlideShow_adapter_main slideShow_adapter_main;
    ArrayList<ModelSliderImg> modelSliderImgArrayList;
    private void init() {
        viewPager_slid_img = view.findViewById(R.id.viewpager_slid_img);
        modelSliderImgArrayList = new ArrayList<>();
        indicator = view.findViewById(R.id.indicator);
        for (int i = 0; i <5 ; i++) {
            ModelSliderImg modelSliderImg = new ModelSliderImg();
            modelSliderImg.setTitle("ماالا تمشي النادي");
            modelSliderImg.setBody("ماالا تمشي النادي ماالا تمشي النادي ماالا تمشي النادي ماالا تمشي النادي");
            modelSliderImg.setImg_url("https://images.theabcdn.com/i/36187172");
            modelSliderImgArrayList.add(modelSliderImg);
        }

        if (modelSliderImgArrayList.size()>0){
            slideShow_adapter_main = new SlideShow_adapter_main(getContext(),modelSliderImgArrayList);
            viewPager_slid_img.setAdapter(slideShow_adapter_main);
            indicator.setViewPager(viewPager_slid_img);
            AutoSwipingImg();
        }
    }
    private void AutoSwipingImg() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                int i = viewPager_slid_img.getCurrentItem();
                if (i==modelSliderImgArrayList.size()-1){
                    i=0;
                    viewPager_slid_img.setCurrentItem(i);
                }else{
                    i++;
                    viewPager_slid_img.setCurrentItem(i);
                }


            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 6000, 6000);
    }
    public MainFragment() {
        // Required empty public constructor
    }


    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);
//        container2 = view.findViewById(R.id.container);
        init();
        return view;
    }


}
