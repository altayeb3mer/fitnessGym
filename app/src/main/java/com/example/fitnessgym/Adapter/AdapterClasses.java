package com.example.fitnessgym.Adapter;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessgym.Activity.ClassDetails;
import com.example.fitnessgym.Model.ModelClasses;
import com.example.fitnessgym.R;

import java.util.ArrayList;


public class AdapterClasses extends RecyclerView.Adapter<AdapterClasses.ViewHolder> {

    ArrayList<ModelClasses> modelClassesArrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;

    public AdapterClasses(Activity activity, ArrayList<ModelClasses> modelClasses) {
        this.mInflater = LayoutInflater.from(activity);
        this.modelClassesArrayList = modelClasses;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.main_class_rec_items, parent, false);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelClasses item = modelClassesArrayList.get(position);

        holder.textView_price.setText(item.getPrice());
        holder.textView_title.setText(item.getTitle());
        holder.textView_couch.setText("المدرب : " + " " + item.getCouch());

//        Glide.with(activity).load("").into(holder.imageView);

        holder.cardView_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent =new Intent(activity, ClassDetails.class);
                intent.putExtra("class_name", item.getName());
                intent.putExtra("couch_name", item.getCouch());
                intent.putExtra("price_d", item.getSub_d_price());
                intent.putExtra("price_m", item.getPrice());
                intent.putExtra("days", item.getDates());
                intent.putExtra("duration", item.getDuration());
                activity.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return modelClassesArrayList.size();
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
        TextView textView_title, textView_price, textView_couch;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            cardView_container = itemView.findViewById(R.id.card_container);
            textView_price = itemView.findViewById(R.id.price);
            textView_title = itemView.findViewById(R.id.title);
            textView_couch = itemView.findViewById(R.id.couch);
            imageView = itemView.findViewById(R.id.img);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


}

