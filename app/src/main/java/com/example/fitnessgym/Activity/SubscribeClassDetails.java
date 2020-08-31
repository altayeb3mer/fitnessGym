package com.example.fitnessgym.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessgym.Adapter.AdapterFree;
import com.example.fitnessgym.Adapter.AdapterSubscribeClass;
import com.example.fitnessgym.Constants;
import com.example.fitnessgym.Model.ModelFree;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Utils.ToolbarClass;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.potyvideo.library.AndExoPlayerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubscribeClassDetails extends ToolbarClass {
    public static final int REQUEST_CALL = 1;
    AndExoPlayerView andExoPlayerView;
    String video_url;
    TextView category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_class_details);
        category = findViewById(R.id.category);

        initAdapterFree();

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



    }


    RecyclerView recyclerFree;
    AdapterSubscribeClass adapterFree;
    ArrayList<ModelFree> modelFreeArrayList;
    public void initAdapterFree() {


        if (true) { //Username and Password Validation
            final KProgressHUD progressDialog;// Validation
            progressDialog = KProgressHUD.create(SubscribeClassDetails.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("الرجاء الانتظار")
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();

            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            assert extras != null;
            String class_id = extras.getString("class_id");

            Log.d("login_response", class_id);
            Ion.with(getApplicationContext())
                    .load("GET", Constants.Video_url+"?class_id="+class_id)
                    .setHeader("Cookie","PHPSESSID=hovjuh7hcdh2t70v7hnlb7dj66")
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                                     @Override
                                     public void onCompleted(Exception e, String response) {
                                         progressDialog.dismiss();


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
                                                         modelFree.setClass_id(jsonObject1.getString("class_id"));
                                                         modelFree.setTitle(jsonObject1.getString("vid_title"));
                                                         modelFree.setVid_url(jsonObject1.getString("vid_url"));

                                                         modelFreeArrayList.add(modelFree);
                                                     }
                                                     if (modelFreeArrayList.size()>0){
                                                         adapterFree = new AdapterSubscribeClass(SubscribeClassDetails.this,modelFreeArrayList,andExoPlayerView,category);
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