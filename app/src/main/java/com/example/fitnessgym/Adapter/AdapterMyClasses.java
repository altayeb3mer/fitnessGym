package com.example.fitnessgym.Adapter;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessgym.Activity.ClassDetails;
import com.example.fitnessgym.Model.ModelClasses;
import com.example.fitnessgym.R;

import java.util.ArrayList;


public class AdapterMyClasses extends RecyclerView.Adapter<AdapterMyClasses.ViewHolder> {

    ArrayList<ModelClasses> modelClassesArrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;

    public AdapterMyClasses(Activity activity, ArrayList<ModelClasses> modelClasses) {
        this.mInflater = LayoutInflater.from(activity);
        this.modelClassesArrayList = modelClasses;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_pro_class_item, parent, false);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelClasses item = modelClassesArrayList.get(position);

        holder.textView_name.setText(item.getName());
        holder.textView_start.setText(item.getStartIn());
        holder.textView_dates.setText(item.getDates());
        holder.textView_times.setText(item.getTimes());
        holder.textView_duration.setText(item.getDuration());
        holder.textView_subType.setText(item.getSubType());

//        Glide.with(activity).load("").into(holder.imageView);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, ClassDetails.class));
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
        TextView textView_name, textView_start, textView_dates, textView_times, textView_duration, textView_subType;
        AppCompatButton button;

        ViewHolder(View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.name);
            textView_start = itemView.findViewById(R.id.start);
            textView_dates = itemView.findViewById(R.id.dates);
            textView_times = itemView.findViewById(R.id.times);
            textView_duration = itemView.findViewById(R.id.duration);
            textView_subType = itemView.findViewById(R.id.subType);
            button = itemView.findViewById(R.id.btn);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


}

