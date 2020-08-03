package com.example.fitnessgym.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessgym.Adapter.AdapterClasses;
import com.example.fitnessgym.Adapter.AdapterClassesOurChoose;
import com.example.fitnessgym.Adapter.AdapterFoodOurChoose;
import com.example.fitnessgym.Constants;
import com.example.fitnessgym.Model.ModelClasses;
import com.example.fitnessgym.Model.ModelFood;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Utils.ToolbarClass;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClassDetails extends ToolbarClass {


    RecyclerView recyclerView;
    AdapterClassesOurChoose adapterClassesOurChoose;
    TextView class_name,couch_name,price_m,price_d,duration,days;

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_class_details, "تفاصيل كلاس");

        class_name = findViewById(R.id.class_name);
        couch_name = findViewById(R.id.couch);
        price_m = findViewById(R.id.price_m);
        price_d = findViewById(R.id.price_d);
        duration = findViewById(R.id.duration);
        days = findViewById(R.id.dates);



        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        assert extras != null;
        class_name.setText(extras.getString("class_name"));
        couch_name.setText(extras.getString("couch_name"));
        price_d.setText(extras.getString("price_d"));

        price_m.setText(extras.getString("price_m"));

        days.setText(extras.getString("days"));

        duration.setText(extras.getString("duration"));



        initAdapterClasses();



    }


//    RecyclerView recyclerClasses;
//    AdapterClassesOurChoose adapterClasses;
//    ArrayList<ModelClasses> modelFoodArrayList;
//    private void initAdapterClasses() {
//        modelFoodArrayList = new ArrayList<>();
//        recyclerClasses = findViewById(R.id.recyclerFood);
//        recyclerClasses.setNestedScrollingEnabled(false);
//
//        for (int i = 0; i < 10; i++) {
//            ModelClasses modelClasses =new ModelClasses();
//            modelClasses.setPrice("500");
//            modelClasses.setTitle("رقص افريقي");
//            modelClasses.setCouch("شجون بابكر");
//            modelFoodArrayList.add(modelClasses);
//        }
//        if (modelFoodArrayList.size()>0){
//            adapterClasses = new AdapterClassesOurChoose(this,modelFoodArrayList);
//            recyclerClasses.setAdapter(adapterClasses);
//        }
//    }



    RecyclerView recyclerViewClasses;
    AdapterClassesOurChoose adapterClasses;
    ArrayList<ModelClasses> modelClassesArrayList;
    public void initAdapterClasses() {


        if (true) { //Username and Password Validation



            Ion.with(getApplicationContext())
                    .load("POST", Constants.Classes_url)
                    .setHeader("Cookie","PHPSESSID=hovjuh7hcdh2t70v7hnlb7dj66")
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                                     @Override
                                     public void onCompleted(Exception e, String response) {


                                         //Toasty.error(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                                         try {

                                             if ((e != null)) {
                                                 Toasty.warning(getApplicationContext(), "Please Check Internet Connection", Toast.LENGTH_LONG).show();

                                             } else {
                                                 Log.d("login_response", response);
                                                 JSONObject jsonObject = new JSONObject(response);



                                                 if (response.contains("data")) {



                                                     recyclerViewClasses = findViewById(R.id.recyclerClasses);
                                                     modelClassesArrayList = new ArrayList<>();
                                                     recyclerViewClasses.setNestedScrollingEnabled(false);

                                                     JSONArray jsonArray = jsonObject.getJSONArray("data");
                                                     for (int i = 0; i < jsonArray.length(); i++) {
                                                         JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                                                         ModelClasses modelClasses =new ModelClasses();
                                                         modelClasses.setPrice(jsonObject1.getString("sub_m_price"));
                                                         modelClasses.setSub_d_price(jsonObject1.getString("sub_m_price"));

                                                         modelClasses.setName(jsonObject1.getString("class_name"));
                                                         modelClasses.setCouch(jsonObject1.getString("coach_name"));
                                                         modelClasses.setDuration(jsonObject1.getString("class_duration"));
                                                         modelClasses.setDates(jsonObject1.getString("class_days"));
                                                         modelClassesArrayList.add(modelClasses);

                                                         //Save The Token Key In Prefrence

//
                                                     }
                                                     adapterClasses = new AdapterClassesOurChoose(ClassDetails.this,modelClassesArrayList);
                                                     if (modelClassesArrayList.size()>0){
                                                         recyclerViewClasses.setAdapter(adapterClasses);
                                                     }


                                                 } else {
                                                     Toasty.warning(getApplicationContext(), "Check Your Data", Toast.LENGTH_LONG).show();
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
