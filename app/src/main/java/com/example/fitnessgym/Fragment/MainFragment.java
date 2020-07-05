package com.example.fitnessgym.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessgym.Adapter.AdapterClasses;
import com.example.fitnessgym.Adapter.AdapterFood;
import com.example.fitnessgym.Adapter.AdapterProduct;
import com.example.fitnessgym.Adapter.SlideShow_adapter_main;
import com.example.fitnessgym.Model.ModelClasses;
import com.example.fitnessgym.Model.ModelFood;
import com.example.fitnessgym.Model.ModelProduct;
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
        recyclerViewClasses = view.findViewById(R.id.recyclerClasses);
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

    AdapterClasses adapterClasses;
    ArrayList<ModelClasses> modelClassesArrayList;
    RecyclerView recyclerViewClasses;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);
//        container2 = view.findViewById(R.id.container);
        init();
        initAdapterClasses();
        initAdapterProduct();
        initAdapterFood();
        return view;
    }


    RecyclerView recyclerFood;
    AdapterFood adapterFood;
    ArrayList<ModelFood> modelFoodArrayList;
    private void initAdapterFood() {
        modelFoodArrayList = new ArrayList<>();
        recyclerFood = view.findViewById(R.id.recyclerFood);
        recyclerFood.setNestedScrollingEnabled(false);

        for (int i = 0; i < 3; i++) {
            ModelFood modelFood =new ModelFood();
            modelFood.setCategory("لياقة بدنية");
            modelFood.setTitle("10 اطعمة صحية لحياة افضل");
            modelFood.setBody("10 اطعمة صحية لحياة افضل اطعمة صحية اطعمة صحية");
            modelFoodArrayList.add(modelFood);
        }
        if (modelFoodArrayList.size()>0){
            adapterFood = new AdapterFood(getActivity(),modelFoodArrayList);
            recyclerFood.setAdapter(adapterFood);
        }
    }

    ArrayList<ModelProduct> productArrayList;
    AdapterProduct adapterProduct;
    private void initAdapterProduct() {
        productArrayList = new ArrayList<>();
        recyclerProduct = view.findViewById(R.id.recyclerProduct);
        recyclerProduct.setNestedScrollingEnabled(false);

        for (int i = 0; i < 3; i++) {
            ModelProduct modelProduct =new ModelProduct();
            modelProduct.setPrice("500");
            modelProduct.setTitle("حبل نط");
            productArrayList.add(modelProduct);
        }
        if (productArrayList.size()>0){
            adapterProduct = new AdapterProduct(getActivity(),productArrayList);
            recyclerProduct.setAdapter(adapterProduct);
        }

    }

    RecyclerView recyclerProduct;


    private void initAdapterClasses() {
        modelClassesArrayList = new ArrayList<>();
        recyclerViewClasses.setNestedScrollingEnabled(false);
        for (int i = 0; i < 3; i++) {
            ModelClasses modelClasses =new ModelClasses();
            modelClasses.setPrice("500");
            modelClasses.setTitle("رقص افريقي");
            modelClasses.setCouch("شجون بابكر");
            modelClassesArrayList.add(modelClasses);
        }
        adapterClasses = new AdapterClasses(getActivity(),modelClassesArrayList);
        if (modelClassesArrayList.size()>0){
            recyclerViewClasses.setAdapter(adapterClasses);
        }

    }


}
