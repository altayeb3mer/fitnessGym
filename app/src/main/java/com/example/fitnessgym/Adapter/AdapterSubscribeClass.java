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
import com.example.fitnessgym.Activity.SubscribeClassDetails;
import com.example.fitnessgym.Model.ModelClasses;
import com.example.fitnessgym.Model.ModelFree;
import com.example.fitnessgym.R;
import com.potyvideo.library.AndExoPlayerView;

import java.util.ArrayList;


public class AdapterSubscribeClass extends RecyclerView.Adapter<AdapterSubscribeClass.ViewHolder> {

    ArrayList<ModelFree> modelFreeArrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;
    AndExoPlayerView andExoPlayerView;
    TextView category;
    int tmp = 0;

    public AdapterSubscribeClass(Activity activity, ArrayList<ModelFree> modelFrees,AndExoPlayerView andExoPlayerView,TextView category) {
        this.mInflater = LayoutInflater.from(activity);
        this.modelFreeArrayList = modelFrees;
        this.activity = activity;
        this.andExoPlayerView=andExoPlayerView;
        this.category = category;
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
        try {

            andExoPlayerView.setSource( modelFreeArrayList.get(0).getVid_url());
        } catch (Exception e) {
            String error = e.getMessage();
            Toast.makeText(activity, "" + error, Toast.LENGTH_SHORT).show();

        }
        category.setText( modelFreeArrayList.get(0).getCategory());
//        Glide.with(activity).load("").into(holder.imageView);

        holder.lay_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Intent intent =new Intent(activity, SubscribeClassDetails.class);
//                intent.putExtra("class_name", item.getCategory());
//                intent.putExtra("couch_name", item.getCouch());
//                intent.putExtra("video_url", item.getVid_url());
//                intent.putExtra("class_id", item.getClass_id());
//                Log.e("tmpVlaue", String.valueOf(tmp));
//                tmp++;
//                activity.startActivity(intent);


                try {

                    andExoPlayerView.setSource(item.getVid_url());
                } catch (Exception e) {
                    String error = e.getMessage();
                    Toast.makeText(activity, "" + error, Toast.LENGTH_SHORT).show();
                    Log.e("tmpVlaue", String.valueOf(item.getCategory()));


                }

                category.setText("التصنيف:"+item.getCategory());
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

