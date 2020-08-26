package com.example.fitnessgym.Adapter;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessgym.Activity.ClassDetails;
import com.example.fitnessgym.Activity.SubscribeClassDetails;
import com.example.fitnessgym.Model.ModelClasses;
import com.example.fitnessgym.R;

import java.util.ArrayList;


public class AdapterSelectionItems extends RecyclerView.Adapter<AdapterSelectionItems.ViewHolder> {

    ArrayList<ModelClasses> modelClassesArrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;
    int tmp=0;

    public AdapterSelectionItems(Activity activity, ArrayList<ModelClasses> modelClasses) {
        this.mInflater = LayoutInflater.from(activity);
        this.modelClassesArrayList = modelClasses;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.main_selection_classes_items, parent, false);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelClasses item = modelClassesArrayList.get(position);

        holder.class_name.setText(item.getName());


//        Glide.with(activity).load("").into(holder.imageView);



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
        AppCompatRadioButton class_name;

        ViewHolder(View itemView) {
            super(itemView);
            class_name = itemView.findViewById(R.id.class_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


}

