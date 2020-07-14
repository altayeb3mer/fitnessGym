package com.example.fitnessgym.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessgym.Adapter.AdapterClasses;
import com.example.fitnessgym.Adapter.AdapterMyClasses;
import com.example.fitnessgym.Model.ModelClasses;
import com.example.fitnessgym.R;

import java.util.ArrayList;


public class FragmentTabClass extends Fragment {



    public FragmentTabClass() {
        // Required empty public constructor
    }





    View view;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_class, container, false);
        initAdapterClasses();
        return view;
    }

    RecyclerView recyclerClasses;
    AdapterMyClasses adapterClasses;
    ArrayList<ModelClasses> modelFoodArrayList;
    private void initAdapterClasses() {
        modelFoodArrayList = new ArrayList<>();
        recyclerClasses = view.findViewById(R.id.recyclerFood);
        recyclerClasses.setNestedScrollingEnabled(false);

        for (int i = 0; i < 10; i++) {
            ModelClasses modelClasses =new ModelClasses();
            modelClasses.setName("زومبا");
            modelClasses.setStartIn("1-7-2020  02:00:00");
            modelClasses.setDates("الحد - الاثنين - الخميس");
            modelClasses.setTimes("02:00:00");
            modelClasses.setDuration("2 ساعة");
            modelClasses.setSubType("شهري");
            modelFoodArrayList.add(modelClasses);
        }
        if (modelFoodArrayList.size()>0){
            adapterClasses = new AdapterMyClasses(getActivity(),modelFoodArrayList);
            recyclerClasses.setAdapter(adapterClasses);
        }
    }
}
