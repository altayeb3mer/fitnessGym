package com.example.fitnessgym.Adapter;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessgym.Activity.ClassDetails;
import com.example.fitnessgym.Activity.FreeClassDetails;
import com.example.fitnessgym.Model.ModelClasses;
import com.example.fitnessgym.Model.ModelFree;
import com.example.fitnessgym.R;

import java.util.ArrayList;


public class AdapterFree extends RecyclerView.Adapter<AdapterFree.ViewHolder> {

    ArrayList<ModelFree> modelFreeArrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;

    public AdapterFree(Activity activity, ArrayList<ModelFree> modelFrees) {
        this.mInflater = LayoutInflater.from(activity);
        this.modelFreeArrayList = modelFrees;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.free_rec_item, parent, false);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelFree item = modelFreeArrayList.get(position);

        holder.textView_title.setText(item.getTitle());
        holder.textView_couch.setText("المدرب : " + " " + item.getCouch());
        holder.textView_category.setText("الصف : "+" "+item.getCouch());

//        Glide.with(activity).load("").into(holder.imageView);

        holder.lay_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, FreeClassDetails.class));
//                Toast.makeText(activity, "تمارين زومبا", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return modelFreeArrayList.size();
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
        RelativeLayout lay_container;
        TextView textView_title, textView_couch, textView_category;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            lay_container = itemView.findViewById(R.id.container_lay);
            textView_title = itemView.findViewById(R.id.title);
            textView_couch = itemView.findViewById(R.id.couch);
            textView_category = itemView.findViewById(R.id.category);
            imageView = itemView.findViewById(R.id.img);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


}

