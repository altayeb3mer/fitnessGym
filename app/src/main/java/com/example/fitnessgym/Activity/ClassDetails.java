package com.example.fitnessgym.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClassDetails extends ToolbarClass {


    RecyclerView recyclerView;
    AdapterClassesOurChoose adapterClassesOurChoose;
    TextView class_name,couch_name,price_m,price_d,duration,days;
    ImageView image;
    String class_id;

    SharedPreferences sp;

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_class_details, "تفاصيل كلاس");
        sp = getSharedPreferences("data", 0);

        class_name = findViewById(R.id.class_name);
        couch_name = findViewById(R.id.couch);
        price_m = findViewById(R.id.price_m);
        price_d = findViewById(R.id.price_d);
        duration = findViewById(R.id.duration);
        days = findViewById(R.id.dates);
        image = findViewById(R.id.img);

        findViewById(R.id.join_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mem_id = sp.getString("id", "");
                if(mem_id.equals("")){



                    Toast.makeText(getApplicationContext(), "يرجى تسجيل الدخول" , Toast.LENGTH_SHORT).show();
                }
else{
                Intent intent = getIntent();
                Bundle extras = intent.getExtras();
                assert extras != null;
                String class_id = extras.getString("id");

                Intent intent1 = new Intent(getApplicationContext(),SubscribeActivity.class);
                intent1.putExtra("class_id", class_id);
                intent1.putExtra("class_name", extras.getString("class_name"));
               startActivity(intent1);}

            }
        });


//
//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//        assert extras != null;
//        class_name.setText(extras.getString("class_name"));
//        couch_name.setText(extras.getString("couch_name"));
//        price_d.setText(extras.getString("price_d"));
//
//        price_m.setText(extras.getString("price_m"));
//
//        days.setText(extras.getString("days"));
//
//        duration.setText(extras.getString("duration"));
//
//        Picasso
//                .with(getApplicationContext())
//                .load(extras.getString("image"))
//                .into(image);
        getDetailsOfClass();
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
                                                 Toasty.warning(getApplicationContext(), "تحقق من اتصال الانترنت", Toast.LENGTH_LONG).show();

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
                                                         modelClasses.setSub_d_price(jsonObject1.getString("sub_d_price"));

                                                         modelClasses.setName(jsonObject1.getString("class_name"));
                                                         modelClasses.setCouch(jsonObject1.getString("coach_name"));
                                                         modelClasses.setDuration(jsonObject1.getString("class_duration"));
                                                         modelClasses.setDates(jsonObject1.getString("class_days"));
                                                         modelClassesArrayList.add(modelClasses);

                                                         //Save The Token Key In Prefrence
                                                         //



//
                                                     }
                                                     adapterClasses = new AdapterClassesOurChoose(ClassDetails.this,modelClassesArrayList);
                                                     if (modelClassesArrayList.size()>0){
                                                         recyclerViewClasses.setAdapter(adapterClasses);
                                                     }


                                                 } else {
                                                     Toasty.warning(getApplicationContext(), "تحقق من البيانات", Toast.LENGTH_LONG).show();
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




    public void getDetailsOfClass() {


        if (true) { //Username and Password Validation

            SharedPreferences sp = getSharedPreferences("data", 0);
            String mem_id = sp.getString("id", "");
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            assert extras != null;
            String class_id = extras.getString("id");
            Ion.with(getApplicationContext())
                    .load("POST", Constants.Classes_url+"?class_id="+class_id+"&mem_id="+mem_id)
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




                                                     JSONArray jsonArray = jsonObject.getJSONArray("data");
                                                     for (int i = 0; i < jsonArray.length(); i++) {
                                                         JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                                            class_name.setText(jsonObject1.getString("class_name"));
                                                            couch_name.setText(jsonObject1.getString("coach_name"));
                                                            price_d.setText(jsonObject1.getString("sub_d_price"));

                                                            price_m.setText(jsonObject1.getString("sub_m_price"));

                                                            days.setText(jsonObject1.getString("class_days"));

                                                            duration.setText(jsonObject1.getString("class_duration"));

                                                            Picasso
                                                                    .with(getApplicationContext())
                                                                    .load(jsonObject1.getString("coach_pic"))
                                                                    .into(image);

                                                            String status=jsonObject1.getString("status");

                                                         SharedPreferences sp = getSharedPreferences("data", 0);
                                                         String userType = sp.getString("acc_type", "");
                                                         Log.d("acc_type",userType);
                                                         assert userType != null;
                                                            if(status.equals("FALSE")&&userType.equalsIgnoreCase("user")){

                                                                findViewById(R.id.join_button).setVisibility(View.VISIBLE);
                                                            }
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
