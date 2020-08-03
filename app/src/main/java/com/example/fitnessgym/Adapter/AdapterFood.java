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
import com.example.fitnessgym.Activity.FoodDetails;
import com.example.fitnessgym.Model.ModelFood;
import com.example.fitnessgym.R;

import java.util.ArrayList;


public class AdapterFood extends RecyclerView.Adapter<AdapterFood.ViewHolder> {

    ArrayList<ModelFood> modelFoodArrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;

    public AdapterFood(Activity activity, ArrayList<ModelFood> modelFoodArrayList) {
        this.mInflater = LayoutInflater.from(activity);
        this.modelFoodArrayList = modelFoodArrayList;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.main_food_rec_items, parent, false);



        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelFood item = modelFoodArrayList.get(position);

        holder.textView_category.setText(item.getCategory());
        holder.textView_title.setText(item.getTitle());
        holder.textView_body.setText(item.getBody());

//        Glide.with(activity).load("").into(holder.imageView);

        holder.cardView_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "" + item.getTitle(), Toast.LENGTH_SHORT).show();

                Intent intent =new Intent(activity, FoodDetails.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("body", item.getBody());
                intent.putExtra("category", item.getCategory());
                intent.putExtra("image", item.getImg());
                intent.putExtra("post_date", item.getPost_date());
                activity.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return modelFoodArrayList.size();
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
        TextView textView_title, textView_body, textView_category;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            cardView_container = itemView.findViewById(R.id.card_container);
            textView_category = itemView.findViewById(R.id.category);
            textView_title = itemView.findViewById(R.id.title);
            textView_body = itemView.findViewById(R.id.body);
            imageView = itemView.findViewById(R.id.img);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


}

