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

import com.example.fitnessgym.Adapter.AdapterFood;
import com.example.fitnessgym.Adapter.AdapterProduct;
import com.example.fitnessgym.Model.ModelFood;
import com.example.fitnessgym.Model.ModelProduct;
import com.example.fitnessgym.R;

import java.util.ArrayList;

public class FragmentProduct extends Fragment {

    //spinner
    Spinner spinnerCategory;
    String[] arrayCat;
    ArrayAdapter<String> adapter1;
    View view;

    RecyclerView recyclerProduct;
    AdapterProduct adapterProduct;
    ArrayList<ModelProduct> modelFoodArrayList;

    private void initAdapterProduct() {
        modelFoodArrayList = new ArrayList<>();
        recyclerProduct = view.findViewById(R.id.recyclerFood);
        recyclerProduct.setNestedScrollingEnabled(false);

        for (int i = 0; i < 10; i++) {
            ModelProduct modelProduct =new ModelProduct();
            modelProduct.setPrice("500");
            modelProduct.setTitle("حبل نط");
            modelFoodArrayList.add(modelProduct);
        }
        if (modelFoodArrayList.size()>0){
            adapterProduct = new AdapterProduct(getActivity(),modelFoodArrayList);
            recyclerProduct.setAdapter(adapterProduct);
        }
    }
    private void init() {
        //init spinner
        spinnerCategory = view.findViewById(R.id.spinner);
        arrayCat = new String[]{"كل المنتجات", "معدات رياضية", "ملابس رياضية", "معدات صحية"};
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
        initAdapterProduct();

    }
    public FragmentProduct() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_product, container, false);
        init();

        return view;
    }
}
