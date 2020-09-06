package com.example.fitnessgym.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import com.example.fitnessgym.Adapter.AdapterFood;
import com.example.fitnessgym.Constants;
import com.example.fitnessgym.Model.ModelFood;
import com.example.fitnessgym.R;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

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
    public void initAdapterFood() {


        if (true) { //Username and Password Validation
            final KProgressHUD progressDialog;// Validation
            progressDialog = KProgressHUD.create(getActivity())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("الرجاء الانتظار")
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();


            Ion.with(getContext())
                    .load("POST", Constants.Blog_url)
                    .setHeader("Cookie","PHPSESSID=hovjuh7hcdh2t70v7hnlb7dj66")
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                                     @Override
                                     public void onCompleted(Exception e, String response) {
                                         progressDialog.dismiss();


                                         //Toasty.error(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                                         try {

                                             if ((e != null)) {
                                                 Toasty.warning(getContext(), "تحقق من اتصال الانترنت", Toast.LENGTH_LONG).show();

                                             } else {
                                                 Log.d("login_response", response);
                                                 JSONObject jsonObject = new JSONObject(response);



                                                 if (response.contains("data")) {




                                                     modelFoodArrayList = new ArrayList<>();
                                                     recyclerFood = view.findViewById(R.id.recyclerFood);
                                                     recyclerFood.setNestedScrollingEnabled(false);
                                                     JSONArray jsonArray = jsonObject.getJSONArray("data");
                                                     for (int i = 0; i < jsonArray.length(); i++) {
                                                         JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                                                         ModelFood modelFood =new ModelFood();
                                                         modelFood.setId(jsonObject1.getString("id"));

                                                         modelFood.setPost_date(jsonObject1.getString("post_date"));
                                                         modelFood.setCategory(jsonObject1.getString("post_category"));
                                                         modelFood.setTitle(jsonObject1.getString("post_title"));
                                                         modelFood.setBody(jsonObject1.getString("subject"));
                                                         modelFood.setImg(jsonObject1.getString("post_pic"));

                                                         modelFoodArrayList.add(modelFood);
                                                     }
                                                     if (modelFoodArrayList.size()>0){
                                                         adapterFood = new AdapterFood(getActivity(),modelFoodArrayList);
                                                         recyclerFood.setAdapter(adapterFood);
                                                     }

                                                 } else {
                                                     Toasty.warning(getContext(), "Check Your Data", Toast.LENGTH_LONG).show();
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_food, container, false);
        init();
        return view;
    }
}
