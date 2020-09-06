package com.example.fitnessgym.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessgym.Activity.MainActivity;
import com.example.fitnessgym.Adapter.AdapterMyClasses;
import com.example.fitnessgym.Constants;
import com.example.fitnessgym.Model.ModelClasses;
import com.example.fitnessgym.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class FragmentTabClass extends Fragment {


    View view;
    RecyclerView recyclerReqClasses;
    RecyclerView recyclerSubClasses;
    AdapterMyClasses adapterClasses;

    ArrayList<ModelClasses> modelReqClassArrayList;
    ArrayList<ModelClasses> modelSubClassArrayList;
    LinearLayout NoItemLay;
    ProgressBar progressLay;

    public FragmentTabClass() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_class, container, false);
        init();
//        initAdapterClasses();


        SharedPreferences sp = getActivity().getSharedPreferences("data", 0);
        String userType = sp.getString("acc_type", "");
        Log.d("acc_type",userType);



        if (isOnline()) {


            assert userType != null;
            if (userType.equalsIgnoreCase("user")){
            GetMyClasses();}

            if (userType.equalsIgnoreCase("coach")){

                GetCoachClass();

            }
        } else {
            Toast.makeText(getActivity(), "تعذر الاتصال بالانترنت", Toast.LENGTH_SHORT).show();
        }

        view.findViewById(R.id.btnClasses).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("page","classes");
                startActivity(intent);
            }
        });

        return view;
    }

    private void GetCoachClass() {








        modelReqClassArrayList = new ArrayList<>();
        progressLay.setVisibility(View.VISIBLE);
        NoItemLay.setVisibility(View.GONE);
        SharedPreferences sp = getActivity().getSharedPreferences("data", 0);
        String mem_id = sp.getString("id", "");

        Ion.with(getContext())
                .load("GET", Constants.Classes_url+"?log_id="+mem_id).progressBar(progressLay)
//                .setHeader("Cookie", "PHPSESSID=hovjuh7hcdh2t70v7hnlb7dj66")
//                .setBodyParameter("mem_id", "1")
                .asString()
                .setCallback(new FutureCallback<String>() {
                                 @Override
                                 public void onCompleted(Exception e, String response) {


                                     //Toasty.error(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                                     try {

                                         if ((e != null)) {
                                             Toasty.warning(getContext(), "تأكد من اتصالك بالانترنت", Toast.LENGTH_LONG).show();
                                             progressLay.setVisibility(View.GONE);
                                         } else {
                                             Log.d("login_response", response);
                                             JSONObject jsonObject = new JSONObject(response);


                                             if (response.contains("data")) {

                                                 JSONArray jsonArray = jsonObject.getJSONArray("data");
                                                 for (int i = 0; i < jsonArray.length(); i++) {
                                                     JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                                     ModelClasses modelClasses = new ModelClasses();
                                                     modelClasses.setId(jsonObject1.getString("class_id"));
                                                     modelClasses.setName(jsonObject1.getString("class_name"));
                                                     modelClasses.setStartIn(jsonObject1.getString("start_date"));
                                                     modelClasses.setDates(jsonObject1.getString("class_days"));
                                                     modelClasses.setTimes(jsonObject1.getString("class_time"));
                                                     modelClasses.setDuration(jsonObject1.getString("class_duration")+" "+"ساعة");


                                                     //sub_type

//                                                     modelClasses.setStartIn("1-7-2020  02:00:00");
//                                                     modelClasses.setDates("الحد - الاثنين - الخميس");
//                                                     modelClasses.setTimes("02:00:00");
//                                                     modelClasses.setDuration("2 ساعة");
//                                                     modelClasses.setSubType("شهري");



                                                     modelReqClassArrayList.add(modelClasses);
                                                 }

                                                 initAdapterReqClasses(modelReqClassArrayList);

                                             } else {
                                                 initAdapterReqClasses(modelReqClassArrayList);
//

                                             }

                                         }
                                         progressLay.setVisibility(View.GONE);
                                     } catch (Exception ex) {
                                         Toasty.warning(getContext(), "" + ex.getMessage(), Toast.LENGTH_LONG).show();
                                         progressLay.setVisibility(View.GONE);
                                     }
                                 }
                             }
                );

    }

    private void init() {
        progressLay = view.findViewById(R.id.progressLay);
        NoItemLay = view.findViewById(R.id.NoItemLay);
        recyclerReqClasses = view.findViewById(R.id.recyclerFood);
        recyclerSubClasses = view.findViewById(R.id.recyclerSubscribe);

        recyclerReqClasses.setNestedScrollingEnabled(false);
        recyclerSubClasses.setNestedScrollingEnabled(false);

    }

    private void initAdapterReqClasses(ArrayList<ModelClasses> list) {


//        for (int i = 0; i < 10; i++) {
//            ModelClasses modelClasses =new ModelClasses();
//            modelClasses.setName("زومبا");
//            modelClasses.setStartIn("1-7-2020  02:00:00");
//            modelClasses.setDates("الحد - الاثنين - الخميس");
//            modelClasses.setTimes("02:00:00");
//            modelClasses.setDuration("2 ساعة");
//            modelClasses.setSubType("شهري");
//            modelFoodArrayList.add(modelClasses);
//        }
        if (list.size() > 0) {
            adapterClasses = new AdapterMyClasses(getActivity(), list);
            recyclerReqClasses.setAdapter(adapterClasses);
            progressLay.setVisibility(View.GONE);
        } else {
//            Toast.makeText(getContext(), "لم يت العثور على كلاسات", Toast.LENGTH_SHORT).show();
            NoItemLay.setVisibility(View.VISIBLE);
            progressLay.setVisibility(View.GONE);
        }
    }
    private void initAdapterSubClasses(ArrayList<ModelClasses> list) {


//        for (int i = 0; i < 10; i++) {
//            ModelClasses modelClasses =new ModelClasses();
//            modelClasses.setName("زومبا");
//            modelClasses.setStartIn("1-7-2020  02:00:00");
//            modelClasses.setDates("الحد - الاثنين - الخميس");
//            modelClasses.setTimes("02:00:00");
//            modelClasses.setDuration("2 ساعة");
//            modelClasses.setSubType("شهري");
//            modelFoodArrayList.add(modelClasses);
//        }
    if (list.size() > 0) {
            adapterClasses = new AdapterMyClasses(getActivity(), list);
            recyclerSubClasses.setAdapter(adapterClasses);
            progressLay.setVisibility(View.GONE);
        } else {
//            Toast.makeText(getContext(), "لم يت العثور على كلاسات", Toast.LENGTH_SHORT).show();
//            NoItemLay.setVisibility(View.VISIBLE);
//            progressLay.setVisibility(View.GONE);
        }
    }

    private void GetMyClasses() {
        modelReqClassArrayList = new ArrayList<>();
        modelSubClassArrayList = new ArrayList<>();
        progressLay.setVisibility(View.VISIBLE);
        NoItemLay.setVisibility(View.GONE);
        SharedPreferences sp = getActivity().getSharedPreferences("data", 0);
        String mem_id = sp.getString("id", "");

        Ion.with(getContext())
                .load("GET", Constants.Subscribe_url+"?mem_id="+mem_id).progressBar(progressLay)
//                .setHeader("Cookie", "PHPSESSID=hovjuh7hcdh2t70v7hnlb7dj66")
//                .setBodyParameter("mem_id", "1")
                .asString()
                .setCallback(new FutureCallback<String>() {
                                 @Override
                                 public void onCompleted(Exception e, String response) {


                                     //Toasty.error(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                                     try {

                                         if ((e != null)) {
                                             Toasty.warning(getContext(), "تأكد من اتصالك بالانترنت", Toast.LENGTH_LONG).show();
                                             progressLay.setVisibility(View.GONE);
                                         } else {
                                             Log.d("login_response", response);
                                             JSONObject jsonObject = new JSONObject(response);


                                             if (response.contains("data")) {

                                                 JSONArray jsonArray = jsonObject.getJSONArray("data");
                                                 for (int i = 0; i < jsonArray.length(); i++) {

                                                     JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                                     ModelClasses modelClasses = new ModelClasses();
                                                     modelClasses.setSub_id(jsonObject1.getString("sub_id"));
                                                         modelClasses.setId(jsonObject1.getString("class_id"));
                                                         modelClasses.setName(jsonObject1.getString("class_name"));
                                                         modelClasses.setStartIn(jsonObject1.getString("start_date"));
                                                         modelClasses.setDates(jsonObject1.getString("class_days"));
                                                         modelClasses.setTimes(jsonObject1.getString("class_time"));
                                                         modelClasses.setDuration(jsonObject1.getString("class_duration") + " " + "ساعة");
                                                         modelClasses.setSubType(jsonObject1.getString("sub_type"));
                                                         modelClasses.setMem_status(jsonObject1.getString("mem_status"));

                                                         //sub_type

//                                                     modelClasses.setStartIn("1-7-2020  02:00:00");
//                                                     modelClasses.setDates("الحد - الاثنين - الخميس");
//                                                     modelClasses.setTimes("02:00:00");
//                                                     modelClasses.setDuration("2 ساعة");
//                                                     modelClasses.setSubType("شهري");

                                                     if (jsonObject1.getString("mem_status").equalsIgnoreCase("Requested")) {

                                                         modelReqClassArrayList.add(modelClasses);
                                                     }
                                                     else {
                                                         modelSubClassArrayList.add(modelClasses);
                                                     }
                                                     }

                                                     initAdapterReqClasses(modelReqClassArrayList);
                                                 initAdapterSubClasses(modelSubClassArrayList);

                                             } else {
                                                 initAdapterReqClasses(modelReqClassArrayList);
//

                                             }

                                         }
                                         progressLay.setVisibility(View.GONE);
                                     } catch (Exception ex) {
                                         Toasty.warning(getContext(), "" + ex.getMessage(), Toast.LENGTH_LONG).show();
                                         progressLay.setVisibility(View.GONE);
                                     }
                                 }
                             }
                );

    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

//    private void ShowSnakBar(String msg) {
//        Snackbar snackbar = Snackbar.make(container, msg, Snackbar.LENGTH_LONG);
//        View snackview = snackbar.getView();
//        snackview.setBackgroundColor(Color.GRAY);
//        TextView masseage;
//        masseage = snackview.findViewById(R.id.snackbar_text);
//        masseage.setTextSize(14);
//        masseage.setTextColor(Color.WHITE);
//        snackbar.show();
//    }

}
