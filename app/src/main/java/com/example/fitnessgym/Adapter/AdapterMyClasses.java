package com.example.fitnessgym.Adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import com.example.fitnessgym.Activity.ClassDetails;
import com.example.fitnessgym.Activity.SubscribeClassDetails;
import com.example.fitnessgym.Constants;
import com.example.fitnessgym.Model.ModelClasses;
import com.example.fitnessgym.R;
import com.google.android.material.textfield.TextInputEditText;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Objects;


public class AdapterMyClasses extends RecyclerView.Adapter<AdapterMyClasses.ViewHolder> {

    ArrayList<ModelClasses> modelClassesArrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;
    public ViewGroup viewGroup;
    int tmp=0;

    public AdapterMyClasses(Activity activity, ArrayList<ModelClasses> modelClasses) {
        this.mInflater = LayoutInflater.from(activity);
        this.modelClassesArrayList = modelClasses;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_pro_class_item, parent, false);
        viewGroup = parent;
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelClasses item = modelClassesArrayList.get(position);

        SharedPreferences sp = activity.getSharedPreferences("data", 0);
        String userType = sp.getString("acc_type", "");
        Log.d("acc_type",userType);
            assert userType != null;
            if (userType.equalsIgnoreCase("user")) {

                holder.textView_name.setText(item.getName());
                holder.textView_start.setText(item.getStartIn());
                holder.textView_dates.setText(item.getDates());
                holder.textView_times.setText(item.getTimes());
                holder.textView_duration.setText(item.getDuration());
                holder.textView_subType.setText(item.getSubType());

                if(item.getMem_status().equalsIgnoreCase("Requested")){
                    holder.button.setText("اكمال الدفع");
                }
                else {
                    holder.button.setText("التوجه الى الكلاس");
                    holder.buttonDelete.setVisibility(View.GONE);
                }
            }
            else if (userType.equalsIgnoreCase("coach")){


                holder.textView_name.setText(item.getName());
                holder.textView_start.setText(item.getStartIn());
                holder.textView_dates.setText(item.getDates());
                holder.textView_times.setText(item.getTimes());
                holder.textView_duration.setText(item.getDuration());
                holder.textView_subType.setVisibility(View.GONE);

            }


//        Glide.with(activity).load("").into(holder.imageView);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(item.getMem_status().equalsIgnoreCase("Requested")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("اكمال اجراءات الدفع");
                    View viewInflated = LayoutInflater.from(activity).inflate(R.layout.dialog_layout, viewGroup, false);
// Set up the input

// Specify the type of input expected; this, for example, sets the input as a password, and will mask the tex
// Set up the buttons
                    builder.setView(viewInflated);
                    AutoCompleteTextView acc_name = viewInflated.findViewById(R.id.acc_name);
                    AutoCompleteTextView acc_no = viewInflated.findViewById(R.id.acc_no);
                    AutoCompleteTextView notes = viewInflated.findViewById(R.id.notes);



                        String account_num, note, account_name = "";
                        account_num = Objects.requireNonNull(acc_no.getText()).toString();
                        note = Objects.requireNonNull(notes.getText()).toString();


                        String finalAccount_name = account_name;
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(acc_name.getText().toString().equals("")||acc_no.getText().toString().equals("")){

                                    Toasty.warning(activity, "الرجاء ملئ كل البيانات", Toast.LENGTH_LONG).show();

                                }
                                else
                                insertAccountNo(acc_name.getText().toString(), acc_no.getText().toString(), notes.getText().toString(), item.getSub_id());
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        builder.show();

                }
//
//                activity.startActivity(new Intent(activity, ClassDetails.class));

                else {
                    Intent intent = new Intent(activity, SubscribeClassDetails.class);
                    intent.putExtra("class_name", item.getName());
                    intent.putExtra("couch_name", item.getCouch());
                    intent.putExtra("price_d", item.getSub_d_price());
                    intent.putExtra("price_m", item.getPrice());
                    intent.putExtra("days", item.getDates());
                    intent.putExtra("duration", item.getDuration());
                    intent.putExtra("class_id", item.getId());
                    activity.startActivity(intent);
                }
//                Toast.makeText(activity, "جاري التنفيذ..", Toast.LENGTH_SHORT).show();
            }
        });

       holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               delete_sub(item.getSub_id(),position);
           }
       });

    }

    private void delete_sub(String sub_id,int position) {
        Ion.with(activity)
                .load("DELETE", Constants.Subscribe_url+"?sub_id="+sub_id)
                .setHeader("Cookie","PHPSESSID=hovjuh7hcdh2t70v7hnlb7dj66")
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



                                             if (response.contains("Success")) {

                                                 Toasty.success(activity, "تم مسح الطلب", Toast.LENGTH_LONG).show();
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
    private void insertAccountNo(String acc_name,String acc_no,String notes ,String sub_id) {
        Log.d("login_response", acc_name+sub_id);
        Ion.with(activity)
                .load("POST", Constants.Subscribe_url)
                .setHeader("Cookie","PHPSESSID=hovjuh7hcdh2t70v7hnlb7dj66")
                .setBodyParameter("acc_no",acc_no)
                .setBodyParameter("sub_id",sub_id)
                .setBodyParameter("acc_name",acc_name)
                .setBodyParameter("notes",notes)
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



                                             if (response.contains("Success")) {

                                                 Toasty.success(activity, "تم ارسال بياناتك", Toast.LENGTH_LONG).show();








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
        AppCompatButton button ,buttonDelete;

        ViewHolder(View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.name);
            textView_start = itemView.findViewById(R.id.start);
            textView_dates = itemView.findViewById(R.id.dates);
            textView_times = itemView.findViewById(R.id.times);
            textView_duration = itemView.findViewById(R.id.duration);
            textView_subType = itemView.findViewById(R.id.subType);
            button = itemView.findViewById(R.id.btn);
            buttonDelete = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    public void removeAt(int position) {
        modelClassesArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, modelClassesArrayList.size());
    }

}

