package com.example.fitnessgym.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessgym.Adapter.AdapterFood;
import com.example.fitnessgym.Adapter.AdapterFoodOurChoose;
import com.example.fitnessgym.Model.ModelFood;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Utils.ToolbarClass;

import java.util.ArrayList;

public class FoodDetails extends ToolbarClass {
    RecyclerView recyclerFood;
    AdapterFoodOurChoose adapterFood;
    ArrayList<ModelFood> modelFoodArrayList;
    private void initAdapterFood() {
        modelFoodArrayList = new ArrayList<>();
        recyclerFood = findViewById(R.id.recyclerFood);
        recyclerFood.setNestedScrollingEnabled(false);

        for (int i = 0; i < 10; i++) {
            ModelFood modelFood =new ModelFood();
            modelFood.setCategory("لياقة بدنية");
            modelFood.setTitle("10 اطعمة صحية لحياة افضل");
            modelFood.setBody("10 اطعمة صحية لحياة افضل اطعمة صحية اطعمة صحية");
            modelFoodArrayList.add(modelFood);
        }
        if (modelFoodArrayList.size()>0){
            adapterFood = new AdapterFoodOurChoose(this,modelFoodArrayList);
            recyclerFood.setAdapter(adapterFood);
        }
    }
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_food_details, "تغذية ورجيم");

        initAdapterFood();

    }
}
