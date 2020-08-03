package com.example.fitnessgym.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import es.dmoral.toasty.Toasty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessgym.Constants;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Utils.ToolbarClass;
import com.google.android.material.textfield.TextInputEditText;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class Login extends ToolbarClass {

    TextView txt_create_account;

    TextInputEditText email, password;
    AppCompatButton login_button;
    String editEmail, editPassword;
    SharedPreferences preferences;

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_login, "تسجيل الدخول");
        changeStatusBarColor();

        init();


        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        login_button = findViewById(R.id.login);
        preferences=getSharedPreferences("data",0);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_function();
            }
        });

    }

    private void init() {
        txt_create_account = findViewById(R.id.txt_create_account);
        txt_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
    }

    public void login_function() {
        editEmail = Objects.requireNonNull(email.getText()).toString();
        editPassword = Objects.requireNonNull(password.getText()).toString();

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


            Ion.with(getApplicationContext())
                    .load("POST",Constants.login_url)
                    .setHeader("Cookie","PHPSESSID=hovjuh7hcdh2t70v7hnlb7dj66")
                    .setBodyParameter("username", email.getText().toString())
                    .setBodyParameter("password", password.getText().toString())
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                                     @Override
                                     public void onCompleted(Exception e, String response) {


                                         //Toasty.error(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                                         try {

                                             if ((e != null)) {
                                                 Toasty.warning(getApplicationContext(), "Please Check Internet Connection", Toast.LENGTH_LONG).show();

                                             } else {
                                                 Log.d("login_response", response);
                                                 JSONObject jsonObject = new JSONObject(response);



                                                 if (response.contains("data")) {



                                                     Toasty.success(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();



                                                     JSONArray jsonArray = jsonObject.getJSONArray("data");
                                                     JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                                                     //Save The Token Key In Prefrence
                                                     SharedPreferences.Editor editor = preferences.edit();
                                                     editor.putString("us_name", jsonObject1.getString("us_name"));
                                                     editor.putString("mem_phone", jsonObject1.getString("mem_phone"));
                                                     editor.putString("mem_address", jsonObject1.getString("mem_address"));
                                                     editor.putString("mem_email", jsonObject1.getString("mem_email"));
                                                     editor.putString("acc_type", jsonObject1.getString("acc_type"));


                                                     editor.apply();
                                                     Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                                     startActivity(intent);
//


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


