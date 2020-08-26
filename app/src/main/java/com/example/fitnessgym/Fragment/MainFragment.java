package com.example.fitnessgym.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fitnessgym.Activity.MainActivity;
import com.example.fitnessgym.Adapter.AdapterClasses;
import com.example.fitnessgym.Adapter.AdapterFood;
import com.example.fitnessgym.Adapter.AdapterProduct;
import com.example.fitnessgym.Adapter.SlideShow_adapter_main;
import com.example.fitnessgym.Constants;
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
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;
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
        view.findViewById(R.id.seeAllClasses).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(getContext(),MainActivity.class);
              intent.putExtra("page","classes");
              startActivity(intent);

            }
        });
        view.findViewById(R.id.seeAllFood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MainActivity.class);
                intent.putExtra("page","food");
                startActivity(intent);

            }
        });
        view.findViewById(R.id.seeAllPro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MainActivity.class);
                intent.putExtra("page","product");
                startActivity(intent);

            }
        });
        return view;
    }



//    private void initAdapterFood() {
//        modelFoodArrayList = new ArrayList<>();
//        recyclerFood = view.findViewById(R.id.recyclerFood);
//        recyclerFood.setNestedScrollingEnabled(false);
//
//        for (int i = 0; i < 3; i++) {
//            ModelFood modelFood =new ModelFood();
//            modelFood.setCategory("لياقة بدنية");
//            modelFood.setTitle("10 اطعمة صحية لحياة افضل");
//            modelFood.setBody("10 اطعمة صحية لحياة افضل اطعمة صحية اطعمة صحية");
//            modelFoodArrayList.add(modelFood);
//        }
//        if (modelFoodArrayList.size()>0){
//            adapterFood = new AdapterFood(getActivity(),modelFoodArrayList);
//            recyclerFood.setAdapter(adapterFood);
//        }
//    }
    RecyclerView recyclerFood;
    AdapterFood adapterFood;
    ArrayList<ModelFood> modelFoodArrayList;
    public void initAdapterFood() {


        if (true) { //Username and Password Validation



            Ion.with(getContext())
                    .load("POST", Constants.Blog_url)
                    .setHeader("Cookie","PHPSESSID=hovjuh7hcdh2t70v7hnlb7dj66")
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                                     @Override
                                     public void onCompleted(Exception e, String response) {


                                         //Toasty.error(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                                         try {

                                             if ((e != null)) {
                                                 Toasty.warning(getContext(), "Please Check Internet Connection", Toast.LENGTH_LONG).show();

                                             } else {
                                                 Log.d("login_response", response);
                                                 JSONObject jsonObject = new JSONObject(response);



                                                 if (response.contains("data")) {




                                                     modelFoodArrayList = new ArrayList<>();
                                                     recyclerFood = view.findViewById(R.id.recyclerFood);
                                                     recyclerFood.setNestedScrollingEnabled(false);
                                                     JSONArray jsonArray = jsonObject.getJSONArray("data");
                                                     for (int i = 0; i < jsonArray.length(); i++) {
                                                         JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                                                         ModelFood modelFood =new ModelFood();
                                                         modelFood.setId(jsonObject1.getString("id"));

                                                         modelFood.setPost_date(jsonObject1.getString("post_date"));
                                                         modelFood.setCategory(jsonObject1.getString("post_category"));
                                                         modelFood.setTitle(jsonObject1.getString("post_title"));
                                                         modelFood.setBody(jsonObject1.getString("subject"));
                                                         modelFood.setImg(jsonObject1.getString("post_pic"));

                                                         modelFoodArrayList.add(modelFood);
                                                     }
                                                     if (modelFoodArrayList.size()>0){
                                                         adapterFood = new AdapterFood(getActivity(),modelFoodArrayList);
                                                         recyclerFood.setAdapter(adapterFood);
                                                     }

                                                 } else {
                                                     Toasty.warning(getContext(), "Check Your Data", Toast.LENGTH_LONG).show();
//

                                                 }

                                             }

                                         } catch (Exception ex) {
                                         }
                                     }
                                 }
                    );


        }


    }
    ArrayList<ModelProduct> productArrayList;
    AdapterProduct adapterProduct;
    public void initAdapterProduct() {


        if (true) { //Username and Password Validation



            Ion.with(getContext())
                    .load("POST", Constants.Products_url)
                    .setHeader("Cookie","PHPSESSID=hovjuh7hcdh2t70v7hnlb7dj66")
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                                     @Override
                                     public void onCompleted(Exception e, String response) {


                                         //Toasty.error(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                                         try {

                                             if ((e != null)) {
                                                 Toasty.warning(getContext(), "Please Check Internet Connection", Toast.LENGTH_LONG).show();

                                             } else {
                                                 Log.d("login_response", response);
                                                 JSONObject jsonObject = new JSONObject(response);



                                                 if (response.contains("data")) {




                                                     productArrayList = new ArrayList<>();
                                                     recyclerProduct = view.findViewById(R.id.recyclerProduct);
                                                     recyclerProduct.setNestedScrollingEnabled(false);

                                                     JSONArray jsonArray = jsonObject.getJSONArray("data");
                                                     for (int i = 0; i < jsonArray.length(); i++) {
                                                         JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                                                         ModelProduct modelProduct =new ModelProduct();
                                                         modelProduct.setPrice(jsonObject1.getString("price"));
                                                         modelProduct.setId(jsonObject1.getString("pro_id"));
                                                         modelProduct.setTitle(jsonObject1.getString("pro_details"));
                                                         modelProduct.setCategory(jsonObject1.getString("pro_category"));



                                                         modelProduct.setName(jsonObject1.getString("pro_name"));
                                                         productArrayList.add(modelProduct);
                                                     }
                                                     if (productArrayList.size()>0){
                                                         adapterProduct = new AdapterProduct(getActivity(),productArrayList);
                                                         recyclerProduct.setAdapter(adapterProduct);
                                                     }


                                                 } else {
                                                     Toasty.warning(getContext(), "Check Your Data", Toast.LENGTH_LONG).show();
//

                                                 }

                                             }

                                         } catch (Exception ex) {
                                         }
                                     }
                                 }
                    );


        }


    }


    RecyclerView recyclerProduct;


//    private void initAdapterClasses() {
//        modelClassesArrayList = new ArrayList<>();
//        recyclerViewClasses.setNestedScrollingEnabled(false);
//        for (int i = 0; i < 3; i++) {
//            ModelClasses modelClasses =new ModelClasses();
//            modelClasses.setPrice("500");
//            modelClasses.setTitle("رقص افريقي");
//            modelClasses.setCouch("شجون بابكر");
//            modelClassesArrayList.add(modelClasses);
//        }
//        adapterClasses = new AdapterClasses(getActivity(),modelClassesArrayList);
//        if (modelClassesArrayList.size()>0){
//            recyclerViewClasses.setAdapter(adapterClasses);
//        }
//
//    }
    public void initAdapterClasses() {


        if (true) { //Username and Password Validation



            Ion.with(getContext())
                    .load("POST", Constants.Classes_url)
                    .setHeader("Cookie","PHPSESSID=hovjuh7hcdh2t70v7hnlb7dj66")
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                                     @Override
                                     public void onCompleted(Exception e, String response) {


                                         //Toasty.error(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                                         try {

                                             if ((e != null)) {
                                                 Toasty.warning(getContext(), "Please Check Internet Connection", Toast.LENGTH_LONG).show();

                                             } else {
                                                 Log.d("login_response", response);
                                                 JSONObject jsonObject = new JSONObject(response);



                                                 if (response.contains("data")) {




                                                     modelClassesArrayList = new ArrayList<>();
                                                     recyclerViewClasses.setNestedScrollingEnabled(false);

                                                     JSONArray jsonArray = jsonObject.getJSONArray("data");
                                                     for (int i = 0; i < jsonArray.length(); i++) {
                                                         JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                                                         ModelClasses modelClasses =new ModelClasses();
                                                         modelClasses.setId(jsonObject1.getString("class_id"));
                                                         modelClasses.setPrice(jsonObject1.getString("sub_m_price"));
                                                         modelClasses.setSub_d_price(jsonObject1.getString("sub_m_price"));

                                                         modelClasses.setName(jsonObject1.getString("class_name"));
                                                         modelClasses.setCouch(jsonObject1.getString("coach_name"));
                                                         modelClasses.setDuration(jsonObject1.getString("class_duration"));
                                                         modelClasses.setDates(jsonObject1.getString("class_days"));
                                                         modelClasses.setImg(jsonObject1.getString("coach_pic"));
                                                         modelClassesArrayList.add(modelClasses);

                                                         //Save The Token Key In Prefrence

//
                                                     }
                                                     adapterClasses = new AdapterClasses(getActivity(),modelClassesArrayList);
                                                     if (modelClassesArrayList.size()>0){
                                                         recyclerViewClasses.setAdapter(adapterClasses);
                                                     }


                                                 } else {
                                                     Toasty.warning(getContext(), "Check Your Data", Toast.LENGTH_LONG).show();
//

                                                 }

                                             }

                                         } catch (Exception ex) {
                                         }
                                     }
                                 }
                    );


        }


    }

}
