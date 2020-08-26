package com.example.fitnessgym.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fitnessgym.Adapter.AdapterSales;
import com.example.fitnessgym.Constants;
import com.example.fitnessgym.Model.ModelOrders;
import com.example.fitnessgym.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragmentTabSales extends Fragment {
    View view;
    SharedPreferences sp;


    public FragmentTabSales() {
        // Required empty public constructor
    }

    ArrayList<ModelOrders> ordersArrayList;
    AdapterSales adapterOrders;
    RecyclerView recyclerOrders;
    public void initAdapterSales() {


        if (true) { //Username and Password Validation

            sp = getActivity().getSharedPreferences("data", 0);
            String mem_id = sp.getString("id","");

            Ion.with(getContext())
                    .load("GET", Constants.Order_url+"?mem_id="+mem_id)
                    .setHeader("Cookie","PHPSESSID=hovjuh7hcdh2t70v7hnlb7dj66")
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                                     @Override
                                     public void onCompleted(Exception e, String response) {


                                         //Toasty.error(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                                         try {

                                             if ((e != null)) {
                                                 Toasty.warning(getContext(), "Please Check Internet Connection", Toast.LENGTH_LONG).show();

                                             } else {
                                                 Log.d("login_response", response);
                                                 JSONObject jsonObject = new JSONObject(response);



                                                 if (response.contains("data")) {




                                                     ordersArrayList = new ArrayList<>();
                                                     Log.d("login_response", "1");
                                                     recyclerOrders = view.findViewById(R.id.recyclerOrders);

                                                     recyclerOrders.setNestedScrollingEnabled(false);
                                                     Log.d("login_response", String.valueOf(jsonObject.getJSONArray("data")));
                                                     JSONArray jsonArray = jsonObject.getJSONArray("data");
                                                     Log.d("login_response", String.valueOf(jsonArray.length()));
                                                     for (int i = 0; i < jsonArray.length(); i++) {

                                                         JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                                                         ModelOrders modelorders =new ModelOrders();
                                                         modelorders.setOr_id(jsonObject1.getString("or_id"));
                                                         modelorders.setMem_id(jsonObject1.getString("mem_id"));
                                                         modelorders.setPro_id(jsonObject1.getString("pro_id"));

                                                         modelorders.setOrder_date(jsonObject1.getString("order_date"));
                                                         modelorders.setPro_name(jsonObject1.getString("pro_name"));
                                                         modelorders.setOrder_status(jsonObject1.getString("order_status"));
                                                         modelorders.setPro_details(jsonObject1.getString("pro_details"));
                                                         modelorders.setPro_category(jsonObject1.getString("pro_category"));
                                                         modelorders.setPro_price(jsonObject1.getString("price"));
                                                         Log.d("login_response", "2");



                                                         modelorders.setOrder_status(jsonObject1.getString("order_status"));
                                                         ordersArrayList.add(modelorders);
                                                     }
                                                     if (ordersArrayList.size()>0){
                                                         adapterOrders = new AdapterSales(getActivity(), ordersArrayList);
                                                         recyclerOrders.setAdapter(adapterOrders);
                                                     }


                                                 } else {
                                                     Toasty.warning(getContext(), "لا توجد مشتريات", Toast.LENGTH_LONG).show();
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
        view = inflater.inflate(R.layout.fragment_tab_sales, container, false);

        //init();
        initAdapterSales();
        return view;
    }
}
