package com.example.fitnessgym.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fitnessgym.Activity.MainActivity;
import com.example.fitnessgym.Constants;
import com.example.fitnessgym.Model.ModelClasses;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Utils.Global;
import com.google.android.material.textfield.TextInputEditText;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;


public class FragmentTabMyProfile extends Fragment {

    View view;
    LinearLayout progressLay;
    TextInputEditText editTextName, editTextEmail, editTextPhone,editTextAddress;
    AppCompatButton button;
    String s_name="", s_email="", s_phone="", s_address="";

    SharedPreferences preferences;


    public FragmentTabMyProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_tab_my_profile, container, false);
        init();
        return view;
    }

    private void init() {
        progressLay = view.findViewById(R.id.progressLay);
        button = view.findViewById(R.id.btn);

        editTextName = view.findViewById(R.id.name);
        editTextEmail = view.findViewById(R.id.email);
        editTextPhone = view.findViewById(R.id.phone);
        editTextAddress = view.findViewById(R.id.address);

        editTextEmail.addTextChangedListener(emailWatcher);
        editTextName.addTextChangedListener(usernameWatcher);
        editTextPhone.addTextChangedListener(numberWatcher);
        editTextAddress.addTextChangedListener(addressWatcher);


        try {
            SharedPreferences sp = getActivity().getSharedPreferences("data", 0);
            editTextName.setText(sp.getString("us_name", ""));
            editTextEmail.setText(sp.getString("mem_email", ""));
            editTextPhone.setText(sp.getString("mem_phone", ""));
            editTextAddress.setText(sp.getString("mem_address", ""));
        }catch (Exception e){
            Toast.makeText(getContext(), "تعذر الوصول لبياناتك..نرجو التأكد من تسجيلك", Toast.LENGTH_SHORT).show();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( new Global(getContext()).isOnline()){
                    preEditProfile();
                }else {
                    Toast.makeText(getContext(), "خطأ في الاتصال", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void preEditProfile() {
        s_name = editTextName.getText().toString().trim();
        s_email = editTextEmail.getText().toString().trim();
        s_phone = editTextPhone.getText().toString().trim();
        s_address = editTextAddress.getText().toString().trim();

        if (!s_name.equals("")&& !s_email.equals("")&& !s_phone.equals("")&& !s_address.equals("")){
            updateMyProfile();
        }else{
            Toast.makeText(getContext(), "الرجاء تعبئة كل الحقول", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateMyProfile() {
        SharedPreferences sp = getActivity().getSharedPreferences("data", 0);
        String mem_id = sp.getString("id", "");

        progressLay.setVisibility(View.VISIBLE);
        Ion.with(getContext())
                .load("POST", Constants.UpdateProfile_url)
                .setHeader("Cookie", "PHPSESSID=hovjuh7hcdh2t70v7hnlb7dj66")
                .setBodyParameter("log_id", mem_id)
                .setBodyParameter("user_name", s_name)
                .setBodyParameter("email", s_email)
                .setBodyParameter("phone", s_phone)
                .setBodyParameter("address", s_address)
                .asString()
                .setCallback(new FutureCallback<String>() {
                                 @Override
                                 public void onCompleted(Exception e, String response) {


                                     //Toasty.error(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                                     try {

                                         if ((e != null)) {
                                             Toasty.warning(getContext(), "تأكد من اتصالك بالانترنت", Toast.LENGTH_LONG).show();
                                             progressLay.setVisibility(View.GONE);
                                         } else {
                                             Log.d("login_response", response);
                                             JSONObject jsonObject = new JSONObject(response);


                                             if (response.contains("data")) {

                                                 JSONArray jsonArray = jsonObject.getJSONArray("data");
                                                 JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                                                 SharedPreferences.Editor editor = sp.edit();
                                                 editor.putString("id", jsonObject1.getString("id"));
                                                 editor.putString("us_name", jsonObject1.getString("us_name"));
                                                 editor.putString("mem_phone", jsonObject1.getString("mem_phone"));
                                                 editor.putString("mem_address", jsonObject1.getString("mem_address"));
                                                 editor.putString("mem_email", jsonObject1.getString("mem_email"));


                                                 editor.apply();

                                                 dialogMessage("تعديل البروفايل","تم تعديل البيانات الخاصة بك بنجاح ..");

                                             } else {
                                                 Toasty.warning(getContext(), "حدث خطأ الرجاء المحاولة مرة اخرى", Toast.LENGTH_LONG).show();


                                             }

                                         }
                                         progressLay.setVisibility(View.GONE);
                                     } catch (Exception ex) {
                                         Toasty.warning(getContext(), "" + ex.getMessage(), Toast.LENGTH_LONG).show();
                                         progressLay.setVisibility(View.GONE);
                                     }
                                 }
                             }
                );
    }


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

            String check = s.toString();

            if (check.length() < 6|| check.length() > 20) {
                editTextName.setError("مسموع ب 6 احرف الى 20");
            }
        }

    };
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

            String check = s.toString();

            if (check.length() < 4 || check.length() > 40) {
                editTextEmail.setError("يجب ان يحتوي البريد الالكتروني على احرف من 4 الى 20");
            } else if (!check.matches("^[A-za-z0-9.@]+")) {
                editTextEmail.setError("مسموع بالعلامة . والعلامة @");
            } else if (!check.contains("@") || !check.contains(".")) {
                editTextEmail.setError("ليس بريد الكتروني تأكد من صحة المدخلات");
            }

        }

    };
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

           String check = s.toString();

            if (check.length()>10) {
                editTextPhone.setError("مسموع ب 10 ارقام");
            }else if(check.length()<10){
                editTextPhone.setError("مسموع ب 10 ارقام");
            }
        }

    };
    TextWatcher addressWatcher = new TextWatcher() {
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

            String check = s.toString();

            if (check.length() < 6|| check.length() > 20) {
                editTextName.setError("مسموع ب 6 احرف الى 20");
            }
        }

    };



    private void dialogMessage(String title, String msg){
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "تم",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}
