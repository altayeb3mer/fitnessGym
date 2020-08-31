package com.example.fitnessgym.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import com.example.fitnessgym.Adapter.AdapterFree;
import com.example.fitnessgym.Constants;
import com.example.fitnessgym.Model.ModelFree;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Utils.ToolbarClass;
import com.example.fitnessgym.Utils.URL;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.potyvideo.library.AndExoPlayerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FreeClassDetails extends ToolbarClass {

    public static final int REQUEST_CALL = 1;
    AndExoPlayerView andExoPlayerView;
    TextView category;

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onCreate(R.layout.activity_free_class_details, "الصف المجاني");
        category = findViewById(R.id.category);
        andExoPlayerView = findViewById(R.id.player);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

                 try {
                     assert extras != null;
                     andExoPlayerView.setSource(extras.getString("video_url"));
                    } catch (Exception e) {
                        String error = e.getMessage();
                        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
                    }
       category.setText("التصنيف:"+extras.getString("class_name"));

        initAdapterFree();

    }
    RecyclerView recyclerFree;
    AdapterFree adapterFree;
    ArrayList<ModelFree> modelFreeArrayList;


    public void initAdapterFree() {


        if (true) { //Username and Password Validation



            Ion.with(getApplicationContext())
                    .load("GET", Constants.Video_url+"?vid_tag=free")
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




                                                     modelFreeArrayList = new ArrayList<>();
                                                     recyclerFree = findViewById(R.id.recyclerFree);
                                                     recyclerFree.setNestedScrollingEnabled(false);
                                                     JSONArray jsonArray = jsonObject.getJSONArray("data");
                                                     for (int i = 0; i < jsonArray.length(); i++) {
                                                         JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                                         ModelFree modelFree =new ModelFree();
                                                         modelFree.setId(jsonObject1.getString("vid_id"));

                                                         modelFree.setCouch(jsonObject1.getString("coach_name"));
                                                         modelFree.setCategory(jsonObject1.getString("class_name"));
                                                         modelFree.setTitle(jsonObject1.getString("vid_title"));
                                                         modelFree.setVid_url(jsonObject1.getString("vid_url"));

                                                         modelFreeArrayList.add(modelFree);
                                                     }
                                                     if (modelFreeArrayList.size()>0){
                                                         adapterFree = new AdapterFree(FreeClassDetails.this,modelFreeArrayList,category,andExoPlayerView);
                                                         recyclerFree.setAdapter(adapterFree);
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
