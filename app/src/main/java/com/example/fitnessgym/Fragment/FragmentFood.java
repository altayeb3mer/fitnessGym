package com.example.fitnessgym.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessgym.Adapter.AdapterFood;
import com.example.fitnessgym.Model.ModelFood;
import com.example.fitnessgym.R;

import java.util.ArrayList;


public class FragmentFood extends Fragment {

    //spinner
    Spinner spinnerCategory;
    String[] arrayCat;
    ArrayAdapter<String> adapter1;
    View view;

    public FragmentFood() {
        // Required empty public constructor
    }

    private void init() {
        //init spinner
        spinnerCategory = view.findViewById(R.id.spinner);
        arrayCat = new String[]{"كل التصنيفات", "لياقة بدنية", "تصنيف 2", "تصنيف 3"};
        adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, arrayCat) {
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
//                if (position == 0) {
//                    s_month = "";
//                } else {
//                    s_month = array_month[position];
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //recycler
        initAdapterFood();

    }
    RecyclerView recyclerFood;
    AdapterFood adapterFood;
    ArrayList<ModelFood> modelFoodArrayList;
    private void initAdapterFood() {
        modelFoodArrayList = new ArrayList<>();
        recyclerFood = view.findViewById(R.id.recyclerFood);
        recyclerFood.setNestedScrollingEnabled(false);

        for (int i = 0; i < 10; i++) {
            ModelFood modelFood =new ModelFood();
            modelFood.setCategory("لياقة بدنية");
            modelFood.setTitle("10 اطعمة صحية لحياة افضل");
            modelFood.setBody("10 اطعمة صحية لحياة افضل اطعمة صحية اطعمة صحية");
            modelFoodArrayList.add(modelFood);
        }
        if (modelFoodArrayList.size()>0){
            adapterFood = new AdapterFood(getActivity(),modelFoodArrayList);
            recyclerFood.setAdapter(adapterFood);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_food, container, false);
        init();
        return view;
    }
}
