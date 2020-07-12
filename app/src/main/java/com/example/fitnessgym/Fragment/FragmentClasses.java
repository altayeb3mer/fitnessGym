package com.example.fitnessgym.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.fitnessgym.Adapter.AdapterClasses;
import com.example.fitnessgym.Adapter.AdapterFood;
import com.example.fitnessgym.Model.ModelClasses;
import com.example.fitnessgym.Model.ModelFood;
import com.example.fitnessgym.R;

import java.util.ArrayList;


public class FragmentClasses extends Fragment {


    public FragmentClasses() {
        // Required empty public constructor
    }

    //spinner
    Spinner spinnerCategory;
    String[] arrayCat;
    ArrayAdapter<String> adapter1;
    View view;
    private void init() {
        //init spinner
        spinnerCategory = view.findViewById(R.id.spinner);
        arrayCat = new String[]{"كل الكلاسات", "زومبا", "ايربكس", "يوغا", "رقص افريقي"};
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
        initAdapterClasses();

    }
    RecyclerView recyclerClasses;
    AdapterClasses adapterClasses;
    ArrayList<ModelClasses> modelFoodArrayList;
    private void initAdapterClasses() {
        modelFoodArrayList = new ArrayList<>();
        recyclerClasses = view.findViewById(R.id.recyclerFood);
        recyclerClasses.setNestedScrollingEnabled(false);

        for (int i = 0; i < 10; i++) {
            ModelClasses modelClasses =new ModelClasses();
            modelClasses.setPrice("500");
            modelClasses.setTitle("رقص افريقي");
            modelClasses.setCouch("شجون بابكر");
            modelFoodArrayList.add(modelClasses);
        }
        if (modelFoodArrayList.size()>0){
            adapterClasses = new AdapterClasses(getActivity(),modelFoodArrayList);
            recyclerClasses.setAdapter(adapterClasses);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_classes, container, false);
        init();
        return view;
    }
}
