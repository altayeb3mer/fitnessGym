package com.example.fitnessgym.Adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
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
import com.potyvideo.library.AndExoPlayerView;

import java.util.ArrayList;


public class AdapterFree extends RecyclerView.Adapter<AdapterFree.ViewHolder> {

    ArrayList<ModelFree> modelFreeArrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;

    AndExoPlayerView andExoPlayerView;
    TextView category;
    int x = 0;

    public AdapterFree(Activity activity, ArrayList<ModelFree> modelFrees,TextView category , AndExoPlayerView andExoPlayerView) {
        this.mInflater = LayoutInflater.from(activity);
        this.modelFreeArrayList = modelFrees;
        this.activity = activity;
        this.category = category;
        this.andExoPlayerView=andExoPlayerView;
        x=0;

    }
    public AdapterFree(Activity activity, ArrayList<ModelFree> modelFrees) {
        this.mInflater = LayoutInflater.from(activity);
        this.modelFreeArrayList = modelFrees;
        this.activity = activity;
        x=1;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.free_rec_item, parent, false);


        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelFree item = modelFreeArrayList.get(position);

        holder.textView_title.setText(item.getTitle());
        holder.textView_couch.setText("المدرب : " + " " + item.getCouch());
        holder.textView_category.setText("الصف : "+" "+item.getCategory());
        Log.e("coach_name_kk",item.getCouch());

//        Glide.with(activity).load("").into(holder.imageView);

        holder.lay_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(x==0) {
                    category.setText(item.getCategory());
                    andExoPlayerView.setSource(item.getVid_url());
                }
else if(x==1){
                Intent intent =new Intent(activity, FreeClassDetails.class);
                intent.putExtra("class_name", item.getCategory());
                intent.putExtra("couch_name", item.getCouch());
                intent.putExtra("video_url", item.getVid_url());
                activity.startActivity(intent);
            }}
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

