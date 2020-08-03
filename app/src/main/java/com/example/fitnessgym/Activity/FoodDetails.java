package com.example.fitnessgym.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import com.example.fitnessgym.Adapter.AdapterFood;
import com.example.fitnessgym.Adapter.AdapterFoodOurChoose;
import com.example.fitnessgym.Constants;
import com.example.fitnessgym.Model.ModelFood;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Utils.ToolbarClass;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodDetails extends ToolbarClass {
    RecyclerView recyclerFood;
    AdapterFoodOurChoose adapterFood;
    ArrayList<ModelFood> modelFoodArrayList;
    public void initAdapterFood() {


        if (true) { //Username and Password Validation



            Ion.with(getApplicationContext())
                    .load("POST", Constants.Blog_url)
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




                                                     modelFoodArrayList = new ArrayList<>();
                                                     recyclerFood = findViewById(R.id.recyclerFood);
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
                                                         adapterFood = new AdapterFoodOurChoose(FoodDetails.this,modelFoodArrayList);
                                                         recyclerFood.setAdapter(adapterFood);
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
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_food_details, "تغذية ورجيم");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        assert extras != null;

        ((TextView)findViewById(R.id.category)).setText("التصنيف:"+extras.getString("category"));
        ((TextView)findViewById(R.id.title)).setText(extras.getString("title"));
        ((TextView)findViewById(R.id.body)).setText(extras.getString("body"));
        ((TextView)findViewById(R.id.post_date)).setText("التاريخ: "+extras.getString("post_date"));


        initAdapterFood();

    }
}
