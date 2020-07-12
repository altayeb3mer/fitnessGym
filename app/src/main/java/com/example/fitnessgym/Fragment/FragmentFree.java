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
import com.example.fitnessgym.Adapter.AdapterFree;
import com.example.fitnessgym.Model.ModelClasses;
import com.example.fitnessgym.Model.ModelFree;
import com.example.fitnessgym.R;

import java.util.ArrayList;


public class FragmentFree extends Fragment {


    public FragmentFree() {
        // Required empty public constructor
    }

    //spinner
    Spinner spinnerCategory;
    String[] arrayCat;
    ArrayAdapter<String> adapter1;

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
        initAdapterFree();

    }

    private void initAdapterFree() {
        modelFreeArrayList = new ArrayList<>();
        recyclerFree = view.findViewById(R.id.recyclerFree);
        recyclerFree.setNestedScrollingEnabled(false);

        for (int i = 0; i < 10; i++) {
            ModelFree modelFree =new ModelFree();
            modelFree.setTitle("تمارين زومبا لتنحيف الجسم لتنحيف الجسم");
            modelFree.setCouch("شجون بابكر");
            modelFree.setCategory("زومبا");
            modelFreeArrayList.add(modelFree);
        }
        if (modelFreeArrayList.size()>0){
            adapterFree = new AdapterFree(getActivity(),modelFreeArrayList);
            recyclerFree.setAdapter(adapterFree);
        }
    }
    RecyclerView recyclerFree;
    AdapterFree adapterFree;
    ArrayList<ModelFree> modelFreeArrayList;



    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_free, container, false);
        init();
        return view;
    }
}
