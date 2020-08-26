package com.example.fitnessgym.Adapter;


import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import com.example.fitnessgym.Constants;
import com.example.fitnessgym.Model.ModelOrders;
import com.example.fitnessgym.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;


public class AdapterSales extends RecyclerView.Adapter<AdapterSales.ViewHolder> {

    ArrayList<ModelOrders> modelOrdersArrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;
    SharedPreferences sp;

    public AdapterSales(Activity activity, ArrayList<ModelOrders> modelOrders) {
        this.mInflater = LayoutInflater.from(activity);
        this.modelOrdersArrayList = modelOrders;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.main_orders_rec_items, parent, false);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelOrders item = modelOrdersArrayList.get(position);

        holder.textView_price.setText(item.getPro_price());
        holder.textView_title.setText("اسم المنتج :"+" " +item.getPro_name());
        holder.textView_order_status.setText("حالة الطلب :"+" " +item.getOrder_status());
        holder.textView_details.setText("تفاصيل المنتج : "+" " +item.getPro_details());
        String or_id = item.getOr_id();


//        Glide.with(activity).load("").into(holder.imageView);

        holder.btn_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(activity, "قريبا" , Toast.LENGTH_SHORT).show();

                deleteFunction(or_id,position);








            }
        });
    }


    @Override
    public int getItemCount() {
        return modelOrdersArrayList.size();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView_container;
        TextView textView_title, textView_price,textView_details,textView_order_status;
        ImageView imageView;
        AppCompatButton btn_sale;

        ViewHolder(View itemView) {
            super(itemView);
            cardView_container = itemView.findViewById(R.id.card_container);
            textView_price = itemView.findViewById(R.id.price);
            textView_title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.img);
            btn_sale = itemView.findViewById(R.id.btnSale);
            textView_details = itemView.findViewById(R.id.pro_details);
            textView_order_status = itemView.findViewById(R.id.order_status);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    public void deleteFunction(String or_id,int position) {


        if (true) { //Username and Password Validation

//            Progress Bar while connection establishes
//
//            final KProgressHUD progressDialog;
//            ImageView imageView = new ImageView(this);
//            imageView.setBackgroundResource(R.drawable.logo);
////            AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
////            drawable.start();
//
//
//            progressDialog = KProgressHUD.create(Login.this)
//                    .setCustomView(imageView)
//                    .setLabel("Please wait")
//                    .setCancellable(false)
//                    .setAnimationSpeed(2)
//                    .setDimAmount(0.5f)
//                    .show();


            Ion.with(activity)
                    .load("DELETE", Constants.Order_url+"?or_id="+or_id)
                    .setHeader("Cookie","PHPSESSID=hovjuh7hcdh2t70v7hnlb7dj66")
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                                     @Override
                                     public void onCompleted(Exception e, String response) {

                                         Log.d("login_response", response);

                                         //Toasty.error(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                                         try {

                                             if ((e != null)) {
                                                 Toasty.warning(activity, "Please Check Internet Connection", Toast.LENGTH_LONG).show();

                                             } else {



                                                 if (response.contains("Success")) {

                                                     Toasty.success(activity, "Order deleted", Toast.LENGTH_LONG).show();
                                                     removeAt(position);







                                                 } else {
                                                     Toasty.warning(activity, "Check Your Data", Toast.LENGTH_LONG).show();
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
    public void removeAt(int position) {
        modelOrdersArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, modelOrdersArrayList.size());
    }

}

