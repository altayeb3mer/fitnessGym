package com.example.fitnessgym.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fitnessgym.Adapter.AdapterClasses;
import com.example.fitnessgym.Adapter.AdapterFood;
import com.example.fitnessgym.Constants;
import com.example.fitnessgym.Model.ModelClasses;
import com.example.fitnessgym.Model.ModelFood;
import com.example.fitnessgym.R;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.util.Charsets;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


public class FragmentClasses extends Fragment {


    public FragmentClasses() {
        // Required empty public constructor
    }

    //spinner
    Spinner spinnerCategory;
   public static String[] arrayCat,arrIdCat;
    ArrayAdapter<String> adapter1;
    View view;
    private void init() {

        //init spinner
       // Log.e("CatArray", String.valueOf(arrayCat.length));
        spinnerCategory = view.findViewById(R.id.spinner);
       // arrayCat = new String[]{"كل الكلاسات", "زومبا", "ايربكس", "يوغا", "رقص افريقي"};
        adapter1 = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()), R.layout.spinner_item, arrayCat) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = null;
                v = super.getDropDownView(position, null, parent);
                // If this is the selected item position
//                if (position == 0) {
//                    v.setBackgroundColor(Color.WHITE);
//                } else {
//                    if (position % 2 == 0) {
//                        v.setBackgroundColor(getResources().getColor(R.color.spinner_bg_design1));
//                    } else {
//                        v.setBackgroundColor(getResources().getColor(R.color.spinner_bg_design2));
//                    }
//
//                }
                return v;
            }
        };
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter1);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("category",arrayCat[position]+position);
                if(position==0){
                    initAdapterClasses(1);

                }
                else
                     initAdapterSpecificClasses(arrIdCat[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //recycler


    }
    AdapterClasses adapterClasses;
    ArrayList<ModelClasses> modelClassesArrayList;
    RecyclerView recyclerViewClasses;
    public void initAdapterClasses(int tmp) {


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



                                                     recyclerViewClasses = view.findViewById(R.id.recyclerClasses);

                                                     modelClassesArrayList = new ArrayList<>();
                                                     recyclerViewClasses.setNestedScrollingEnabled(false);

                                                     JSONArray jsonArray = jsonObject.getJSONArray("data");
                                                     arrayCat = new String[jsonArray.length()+1];
                                                     arrIdCat = new String[jsonArray.length()+1];
                                                     arrayCat[0]="الكل";
                                                     arrIdCat[0]="";
                                                     for (int i = 0; i < jsonArray.length(); i++) {
                                                         JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                                                         ModelClasses modelClasses =new ModelClasses();
                                                         modelClasses.setId(jsonObject1.getString("class_id"));
                                                         modelClasses.setPrice(jsonObject1.getString("sub_m_price"));
                                                         modelClasses.setSub_d_price(jsonObject1.getString("sub_m_price"));
                                                        arrayCat[i+1]=jsonObject1.getString("class_name");
                                                        Log.e("array_cat",arrayCat[i]);
                                                        arrIdCat[i+1]=jsonObject1.getString("class_id");

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

                                                     if(tmp==0){
                                                     init();}


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




    public void initAdapterSpecificClasses(String class_id) {


        if (true) { //Username and Password Validation

            final KProgressHUD progressDialog;// Validation
            progressDialog = KProgressHUD.create(getActivity())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("الرجاء الانتظار")
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();



                Ion.with(getContext())
                        .load("POST", Constants.Classes_url + "?class_id=" + class_id)
                        .setHeader("Cookie", "PHPSESSID=hovjuh7hcdh2t70v7hnlb7dj66")
                        .asString(Charsets.UTF_8)
                        .setCallback(new FutureCallback<String>() {

                                         @Override
                                         public void onCompleted(Exception e, String response) {
                                             progressDialog.dismiss();


                                             //Toasty.error(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                                             try {

                                                 if ((e != null)) {
                                                     Toasty.warning(getContext(), "Please Check Internet Connection", Toast.LENGTH_LONG).show();

                                                 } else {
                                                     Log.d("login_response", response);
                                                     JSONObject jsonObject = new JSONObject(response);


                                                     if (response.contains("data")) {


                                                         recyclerViewClasses = view.findViewById(R.id.recyclerClasses);

                                                         modelClassesArrayList = new ArrayList<>();
                                                         recyclerViewClasses.setNestedScrollingEnabled(false);

                                                         JSONArray jsonArray = jsonObject.getJSONArray("data");


                                                         for (int i = 0; i < jsonArray.length(); i++) {
                                                             JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                                                             ModelClasses modelClasses = new ModelClasses();
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

                                                         adapterClasses = new AdapterClasses(getActivity(), modelClassesArrayList);
                                                         if (modelClassesArrayList.size() > 0) {
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_classes, container, false);
        initAdapterClasses(0);
        //init();
        return view;
    }
}
