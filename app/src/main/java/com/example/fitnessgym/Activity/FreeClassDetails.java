package com.example.fitnessgym.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessgym.Adapter.AdapterFree;
import com.example.fitnessgym.Model.ModelFree;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Utils.ToolbarClass;
import com.example.fitnessgym.Utils.URL;
import com.potyvideo.library.AndExoPlayerView;

import java.util.ArrayList;

public class FreeClassDetails extends ToolbarClass {

    public static final int REQUEST_CALL = 1;
    AndExoPlayerView andExoPlayerView;

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onCreate(R.layout.activity_free_class_details, "الصف المجاني");
        andExoPlayerView = findViewById(R.id.player);


                 try {
                        andExoPlayerView.setSource("https://www.dailymotion.com/video/x3rni5b");
                    } catch (Exception e) {
                        String error = e.getMessage();
                        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
                    }

        initAdapterFree();

    }
    private void initAdapterFree() {
        modelFreeArrayList = new ArrayList<>();
        recyclerFree = findViewById(R.id.recyclerFree);
        recyclerFree.setNestedScrollingEnabled(false);

        for (int i = 0; i < 5; i++) {
            ModelFree modelFree =new ModelFree();
            modelFree.setTitle("تمارين زومبا لتنحيف الجسم لتنحيف الجسم");
            modelFree.setCouch("شجون بابكر");
            modelFree.setCategory("زومبا");
            modelFreeArrayList.add(modelFree);
        }
        if (modelFreeArrayList.size()>0){
            adapterFree = new AdapterFree(this,modelFreeArrayList);
            recyclerFree.setAdapter(adapterFree);
        }
    }
    RecyclerView recyclerFree;
    AdapterFree adapterFree;
    ArrayList<ModelFree> modelFreeArrayList;

}
