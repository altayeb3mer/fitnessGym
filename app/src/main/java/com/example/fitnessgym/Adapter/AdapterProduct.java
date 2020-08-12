package com.example.fitnessgym.Adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessgym.Model.ModelClasses;
import com.example.fitnessgym.Model.ModelProduct;
import com.example.fitnessgym.R;

import java.util.ArrayList;


public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {

    ArrayList<ModelProduct> modelProductArrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;

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

        holder.textView_price.setText(item.getPrice());
        holder.textView_title.setText(item.getTitle());

//        Glide.with(activity).load("").into(holder.imageView);

        holder.cardView_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "قريبا" , Toast.LENGTH_SHORT).show();
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

        ViewHolder(View itemView) {
            super(itemView);
            cardView_container = itemView.findViewById(R.id.card_container);
            textView_price = itemView.findViewById(R.id.price);
            textView_title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.img);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


}

