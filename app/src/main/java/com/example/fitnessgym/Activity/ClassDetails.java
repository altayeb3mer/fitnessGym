package com.example.fitnessgym.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fitnessgym.Adapter.AdapterClasses;
import com.example.fitnessgym.Adapter.AdapterClassesOurChoose;
import com.example.fitnessgym.Adapter.AdapterFoodOurChoose;
import com.example.fitnessgym.Model.ModelClasses;
import com.example.fitnessgym.Model.ModelFood;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Utils.ToolbarClass;

import java.util.ArrayList;

public class ClassDetails extends ToolbarClass {


    RecyclerView recyclerView;
    AdapterClassesOurChoose adapterClassesOurChoose;

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_class_details, "تفاصيل كلاس");
        initAdapterClasses();

    }


    RecyclerView recyclerClasses;
    AdapterClassesOurChoose adapterClasses;
    ArrayList<ModelClasses> modelFoodArrayList;
    private void initAdapterClasses() {
        modelFoodArrayList = new ArrayList<>();
        recyclerClasses = findViewById(R.id.recyclerFood);
        recyclerClasses.setNestedScrollingEnabled(false);

        for (int i = 0; i < 10; i++) {
            ModelClasses modelClasses =new ModelClasses();
            modelClasses.setPrice("500");
            modelClasses.setTitle("رقص افريقي");
            modelClasses.setCouch("شجون بابكر");
            modelFoodArrayList.add(modelClasses);
        }
        if (modelFoodArrayList.size()>0){
            adapterClasses = new AdapterClassesOurChoose(this,modelFoodArrayList);
            recyclerClasses.setAdapter(adapterClasses);
        }
    }

}
