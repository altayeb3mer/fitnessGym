package com.example.fitnessgym.Activity;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

import android.annotation.SuppressLint;

import com.example.fitnessgym.Constants;
import com.google.android.material.textfield.TextInputEditText;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessgym.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Objects;


public class SubscribeActivity extends AppCompatActivity {
    String date = "";
    SharedPreferences sp;
    TextInputEditText phone;
    TextInputEditText email;
    String sub_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);

        sp = getSharedPreferences("data", 0);
        phone.setText(sp.getString("mem_phone","0"));
        email.setText(sp.getString("mem_email",""));
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        assert extras != null;
        ((TextView)findViewById(R.id.class_name)).setText(extras.getString("class_name"));
        findViewById(R.id.subscribe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribe_function();
            }
        });



        initComponent();
    }

    private void initComponent() {
        ((Button) findViewById(R.id.spn_from_date)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDatePickerDark((Button) view);
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.day:
                if (checked)
                    // Pirates are the best
                    sub_type="day";
                    break;
            case R.id.month:
                if (checked)
                    sub_type="month";
                    break;
        }
    }

    private void dialogDatePickerDark(final Button bt) {
        Calendar cur_calender = Calendar.getInstance();
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long date_ship_millis = calendar.getTimeInMillis();

                        ((TextView)findViewById(R.id.spn_from_date)).setText(year+","+monthOfYear+","+dayOfMonth+"");
                        date =year+"/"+monthOfYear+"/"+dayOfMonth+"";
                    }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );
        //set dark theme
        datePicker.setThemeDark(true);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.setMinDate(cur_calender);
        datePicker.show(getFragmentManager(), "Datepickerdialog");
    }

    public void subscribe_function() {

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        assert extras != null;
        String class_id = extras.getString("class_id");

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
            final KProgressHUD progressDialog;// Validation
            progressDialog = KProgressHUD.create(SubscribeActivity.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("الرجاء الانتظار")
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();

            String mem_id = sp.getString("id", "");
            Log.e("id_of_user",mem_id);
            Ion.with(getApplicationContext())
                    .load("POST", Constants.Subscribe_url)
                    .setHeader("Cookie","PHPSESSID=hovjuh7hcdh2t70v7hnlb7dj66")
                    .setBodyParameter("class_id",class_id)
                    .setBodyParameter("mem_id", mem_id)
                    .setBodyParameter("subtype",sub_type)
                    .setBodyParameter("phoneid",phone.getText().toString())
                    .setBodyParameter("emailid",email.getText().toString())
                    .setBodyParameter("join_date","10-10")
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                                     @Override
                                     public void onCompleted(Exception e, String response) {
                                         progressDialog.dismiss();


                                         //Toasty.error(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                                         try {

                                             if ((e != null)) {
                                                 Toasty.warning(getApplicationContext(), "Please Check Internet Connection", Toast.LENGTH_LONG).show();

                                             } else {
                                                 Log.d("login_response", response);



                                                 if (response.contains("Success")) {



                                                     Toasty.success(getApplicationContext(), "Subscribe Successful", Toast.LENGTH_LONG).show();



                                                 } else {
                                                     Toasty.warning(getApplicationContext(), "Check Your Data", Toast.LENGTH_LONG).show();
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