package com.example.fitnessgym.Adapter;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.fitnessgym.Model.ModelClasses;
import com.example.fitnessgym.Model.ModelProduct;
import com.example.fitnessgym.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {

    ArrayList<ModelProduct> modelProductArrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;
    SharedPreferences sp;

    public AdapterProduct(Activity activity, ArrayList<ModelProduct> modelProducts) {
        this.mInflater = LayoutInflater.from(activity);
        this.modelProductArrayList = modelProducts;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.main_product_rec_items, parent, false);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelProduct item = modelProductArrayList.get(position);
        Picasso
                .with(activity)
                .load(item.getImg()).placeholder(R.drawable.rec1_sample)
                .into(holder.imageView);
        holder.textView_price.setText(item.getPrice());
        holder.textView_title.setText(item.getName());
        String product_id = item.getId();
        sp = activity.getSharedPreferences("data", 0);
        String mem_id = sp.getString("id", "");

//        Glide.with(activity).load("").into(holder.imageView);

        holder.btn_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(activity, "قريبا" , Toast.LENGTH_SHORT).show();

                saleFunction(product_id,mem_id);





            }
        });
    }


    @Override
    public int getItemCount() {
        return modelProductArrayList.size();
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
        TextView textView_title, textView_price;
        ImageView imageView;
        AppCompatButton btn_sale;

        ViewHolder(View itemView) {
            super(itemView);
            cardView_container = itemView.findViewById(R.id.card_container);
            textView_price = itemView.findViewById(R.id.price);
            textView_title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.img);
            btn_sale = itemView.findViewById(R.id.btnSale);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    public void saleFunction(String pro_id,String mem_id) {


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
                    .load("POST", Constants.Order_url)
                    .setHeader("Cookie","PHPSESSID=hovjuh7hcdh2t70v7hnlb7dj66")
                    .setBodyParameter("pro_id",pro_id)
                    .setBodyParameter("mem_id", mem_id)
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                                     @Override
                                     public void onCompleted(Exception e, String response) {

                                         Log.d("login_response", response);

                                         //Toasty.error(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                                         try {

                                             if ((e != null)) {
                                                 Toasty.warning(activity, "تحقق من اتصال الانترنت", Toast.LENGTH_LONG).show();

                                             } else {



                                                 if (response.contains("success")) {

                                                     Toasty.success(activity, "تم ارسال الطلب", Toast.LENGTH_LONG).show();





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

}

