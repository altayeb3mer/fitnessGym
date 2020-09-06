package com.example.fitnessgym.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fitnessgym.Adapter.AdapterFood;
import com.example.fitnessgym.Adapter.AdapterFree;
import com.example.fitnessgym.Constants;
import com.example.fitnessgym.Model.ModelFood;
import com.example.fitnessgym.Model.ModelFree;
import com.example.fitnessgym.R;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

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

//    private void initAdapterFree() {
//        modelFreeArrayList = new ArrayList<>();
//        recyclerFree = view.findViewById(R.id.recyclerFree);
//        recyclerFree.setNestedScrollingEnabled(false);
//
//        for (int i = 0; i < 10; i++) {
//            ModelFree modelFree =new ModelFree();
//            modelFree.setTitle("تمارين زومبا لتنحيف الجسم لتنحيف الجسم");
//            modelFree.setCouch("شجون بابكر");
//            modelFree.setCategory("زومبا");
//            modelFreeArrayList.add(modelFree);
//        }
//        if (modelFreeArrayList.size()>0){
//            adapterFree = new AdapterFree(getActivity(),modelFreeArrayList);
//            recyclerFree.setAdapter(adapterFree);
//        }
//    }
    RecyclerView recyclerFree;
    AdapterFree adapterFree;
    ArrayList<ModelFree> modelFreeArrayList;


    public void initAdapterFree() {


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
                    .load("GET", Constants.Video_url+"?vid_tag=free")
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




                                                     modelFreeArrayList = new ArrayList<>();
                                                     recyclerFree = view.findViewById(R.id.recyclerFree);
                                                     recyclerFree.setNestedScrollingEnabled(false);
                                                     JSONArray jsonArray = jsonObject.getJSONArray("data");
                                                     for (int i = 0; i < jsonArray.length(); i++) {
                                                         JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                                         ModelFree modelFree =new ModelFree();
                                                         modelFree.setId(jsonObject1.getString("vid_id"));

                                                         modelFree.setCouch(jsonObject1.getString("coach_name"));
                                                         modelFree.setCategory(jsonObject1.getString("class_name"));
                                                         modelFree.setTitle(jsonObject1.getString("vid_title"));
                                                         modelFree.setVid_url(jsonObject1.getString("vid_url"));

                                                         modelFreeArrayList.add(modelFree);
                                                     }
                                                     if (modelFreeArrayList.size()>0){
                                                         adapterFree = new AdapterFree(getActivity(),modelFreeArrayList);
                                                         recyclerFree.setAdapter(adapterFree);
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
