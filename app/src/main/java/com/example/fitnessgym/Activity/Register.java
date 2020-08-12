package com.example.fitnessgym.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import es.dmoral.toasty.Toasty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fitnessgym.Constants;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Utils.ToolbarClass;
import com.google.android.material.textfield.TextInputEditText;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.util.Charsets;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

public class Register extends ToolbarClass {
    TextInputEditText edtusername, edtemail, edtpass, edtcnfpass, edtnumber,edtaddress;
    AppCompatButton button;
    SharedPreferences preferences;

    String check;

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_register, "حساب جديد");
        changeStatusBarColor();


        edtusername = findViewById(R.id.user_name);
        edtemail = findViewById(R.id.email);
        edtpass = findViewById(R.id.password);
        edtcnfpass = findViewById(R.id.re_password);
        edtnumber = findViewById(R.id.phone);
        edtaddress = findViewById(R.id.address);
        preferences=getSharedPreferences("data",0);

        button =findViewById(R.id.register);


        edtusername.addTextChangedListener(usernameWatcher);
        edtemail.addTextChangedListener(emailWatcher);
        edtpass.addTextChangedListener(passWatcher);
        edtcnfpass.addTextChangedListener(cnfpassWatcher);
        edtnumber.addTextChangedListener(numberWatcher);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_data();

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


    public void send_data(){

        //validateName() &&  && validateNumber()
        if (validateUserName()&& validateEmail() && validatePass() && validateCnfPass()){
            Log.d("here1","here1");
            // validateUserName()&& validateEmail() && validatePass() && validateCnfPass()




            //Validation Success

            Ion.with(getApplicationContext())
                    .load("POST",Constants.Rigiser_url)
                    .setBodyParameter("address",edtaddress.getText().toString())
                    .setBodyParameter("user_name",edtusername.getText().toString())
                    .setBodyParameter("email",edtemail.getText().toString())
                    .setBodyParameter("password",edtcnfpass.getText().toString())
                    .setBodyParameter("password2",edtcnfpass.getText().toString())
                    .setBodyParameter("phone",edtnumber.getText().toString())
                    .asString(Charsets.UTF_8)
                    .setCallback(new FutureCallback<String>() {

                                     @Override
                                     public void onCompleted(Exception e, String response) {

                                         Log.e("registerrrrrrrrr", response);


                                         //Toasty.error(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                                         try{
                                             Log.d("here","here3");

                                             if ((e != null)) {
                                                 Toasty.warning(getApplicationContext(),"Please Check Internet Connection", Toast.LENGTH_LONG).show();
                                             }else {

                                                 Log.d("login_response", response);
                                                 JSONObject jsonObject = new JSONObject(response);



                                                 if (response.contains("data")) {



                                                     Toasty.success(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();



                                                     JSONArray jsonArray = jsonObject.getJSONArray("data");
                                                     JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                                                     //Save The Token Key In Prefrence
                                                     SharedPreferences.Editor editor = preferences.edit();
                                                     editor.putString("id", jsonObject1.getString("id"));
                                                     editor.putString("us_name", jsonObject1.getString("us_name"));
                                                     editor.putString("mem_phone", jsonObject1.getString("mem_phone"));
                                                     editor.putString("mem_address", jsonObject1.getString("mem_address"));
                                                     editor.putString("mem_email", jsonObject1.getString("mem_email"));
                                                     editor.putString("acc_type", jsonObject1.getString("acc_type"));



                                                     editor.apply();
                                                     Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                                     startActivity(intent);
//ring("test","empty"));


                                                 }
//                                                 } else if (!response.contains("token")&&response.contains("email")){
//                                                     Toasty.warning(getApplicationContext(), R.string.email_exist, Toast.LENGTH_SHORT).show();
//
//                                                 }else if (response.contains("username")&&!response.contains("token")){
//                                                     Toasty.warning(getApplicationContext(), R.string.username_exist, Toast.LENGTH_SHORT).show();
//
//                                                 }
                                                 else if (response.contains("username")&&response.contains("email")&&!response.contains("token")){
                                                     Toasty.warning(getApplicationContext(), "This Username and Email already exists", Toast.LENGTH_SHORT).show();

                                                 }

                                             }

                                         }catch(Exception ex){}
                                     }
                                 }
                    );



        }
        Log.d("fdljvnd","will");
    }


    private boolean validateUserName() {

        check = edtusername.getText().toString();

        return !(check.length() < 6 || check.length() > 20);

    }

    //TextWatcher for First Name -----------------------------------------------------


    //TextWatcher for User Name -----------------------------------------------------

    TextWatcher usernameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //none
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //none
        }

        @Override
        public void afterTextChanged(Editable s) {

            check = s.toString();

            if (check.length() < 6|| check.length() > 20) {
                edtusername.setError("First Name Must consist of 6 to 20 characters");
            }
        }

    };

    //TextWatcher for Last Name -----------------------------------------------------



    //TextWatcher for Email -----------------------------------------------------

    TextWatcher emailWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //none
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //none
        }

        @Override
        public void afterTextChanged(Editable s) {

            check = s.toString();

            if (check.length() < 4 || check.length() > 40) {
                edtemail.setError("Email Must consist of 4 to 20 characters");
            } else if (!check.matches("^[A-za-z0-9.@]+")) {
                edtemail.setError("Only . and @ characters allowed");
            } else if (!check.contains("@") || !check.contains(".")) {
                edtemail.setError("Enter Valid Email");
            }

        }

    };

    //TextWatcher for pass -----------------------------------------------------

    TextWatcher passWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //none
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //none
        }

        @Override
        public void afterTextChanged(Editable s) {

            check = s.toString();

            if (check.length() < 4 || check.length() > 20) {
                edtpass.setError("password is too short. It must contain at least 8 characters and not just numeric.");
            } else if (!check.matches("^[A-za-z0-9@]+")) {
                edtemail.setError("Only @ special character allowed");
            }
        }

    };

    //TextWatcher for repeat Password -----------------------------------------------------

    TextWatcher cnfpassWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //none
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //none
        }

        @Override
        public void afterTextChanged(Editable s) {

            check = s.toString();

            if (!check.equals(edtpass.getText().toString())) {
                edtcnfpass.setError("Both the passwords do not match");
            }
        }

    };


    //TextWatcher for Mobile -----------------------------------------------------

    TextWatcher numberWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //none
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //none
        }

        @Override
        public void afterTextChanged(Editable s) {

            check = s.toString();

            if (check.length()>10) {
                edtnumber.setError("Number cannot be grated than 10 digits");
            }else if(check.length()<10){
                edtnumber.setError("Number should be 10 digits");
            }
        }

    };

    private boolean validateEmail() {

        check = edtemail.getText().toString();

        if (check.length() < 4 || check.length() > 40) {
            return false;
        } else if (!check.matches("^[A-za-z0-9.@]+")) {
            return false;
        } else if (!check.contains("@") || !check.contains(".")) {
            return false;
        }

        return true;
    }


    private boolean validateCnfPass() {

        check = edtcnfpass.getText().toString();

        return check.equals(edtpass.getText().toString());
    }

    private boolean validatePass() {


        check = edtpass.getText().toString();

        if (check.length() < 8 || check.length() > 20) {
            return false;
        } else if (!check.matches("^[A-za-z0-9.@]+")) {
            return false;
        }

        return true;
    }
}
