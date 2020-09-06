package com.example.fitnessgym.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.fitnessgym.Activity.MainActivity;
import com.example.fitnessgym.Adapter.AdapterClasses;
import com.example.fitnessgym.Adapter.AdapterSelectionItems;
import com.example.fitnessgym.Constants;

import com.example.fitnessgym.Model.ModelClasses;
import com.example.fitnessgym.R;
import com.example.fitnessgym.VideoActivity;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.example.fitnessgym.VideoActivity.REQUEST_PICK_VIDEO;



public class FragmentTabVideos extends Fragment {
    SharedPreferences sp;
    LinearLayout uploadVideo;
    private Uri video;
   public static Uri localFilePath;
    String type;
    AppCompatButton upload_btn;
    RadioGroup radioButtonClasses;


    public FragmentTabVideos() {
        // Required empty public constructor
    }




    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_videos, container, false);

        uploadVideo = view.findViewById(R.id.upload_video);
        upload_btn = view.findViewById(R.id.btn_upload);
        radioButtonClasses = view.findViewById(R.id.classes);
        initAdapterClasses();
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            uploadVideo.setEnabled(true);

            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        } else {

            uploadVideo.setEnabled(true);

        }

        uploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickVideoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                pickVideoIntent.setType("video/*");
                startActivityForResult(pickVideoIntent, REQUEST_PICK_VIDEO);
            }
        });
        view.findViewById(R.id.radioGroupType).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClickedType();
            }
        });

        return view;
    }


    public void onRadioButtonClickedType() {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.payed:
                if (checked)
                    // Pirates are the best
                    type="pay";
                break;
            case R.id.free:
                if (checked)
                    type="free";
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_PICK_VIDEO) {
                if (data != null) {
                    Toast.makeText(getActivity(), "Video content URI: " + data.getData(),
                            Toast.LENGTH_LONG).show();
                    localFilePath = data.getData();
//                    video = data.getData();
//                    videoPath = getPath(video);
//                    initializePlayer(video);
                    // uploadFile(video.getPath());

                }
            }
        }
        else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Sorry, there was an error!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                uploadVideo.setEnabled(true);

            }
        }
    }

    AdapterSelectionItems adapterClasses;
    ArrayList<ModelClasses> modelClassesArrayList;
    RecyclerView recyclerViewClasses;
    public void initAdapterClasses() {


        if (true) { //Username and Password
            final KProgressHUD progressDialog;// Validation
            progressDialog = KProgressHUD.create(getActivity())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("الرجاء الانتظار")
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();

            sp = Objects.requireNonNull(getActivity()).getSharedPreferences("data", 0);
            String log_id= sp.getString("id","");
            Log.d("login_response", log_id);
            Ion.with(Objects.requireNonNull(getContext()))
                    .load("GET", Constants.Classes_url+"?log_id="+log_id)
                    .setHeader("Cookie","PHPSESSID=hovjuh7hcdh2t70v7hnlb7dj66")
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                                     @Override
                                     public void onCompleted(Exception e, String response) {
                                         progressDialog.dismiss();
                                         //Toasty.error(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                                         try {

                                             if ((e != null)) {
                                                 Toasty.warning(getContext(), "تحقق من اتصال الانترنت", Toast.LENGTH_LONG).show();

                                             } else {
                                                 Log.d("login_response", response);
                                                 JSONObject jsonObject = new JSONObject(response);



                                                 if (response.contains("data")) {



                                                     recyclerViewClasses = view.findViewById(R.id.recyclerClasses);
                                                     Log.d("login_response", "1");
                                                     modelClassesArrayList = new ArrayList<>();
                                                     recyclerViewClasses.setNestedScrollingEnabled(false);

                                                     JSONArray jsonArray = jsonObject.getJSONArray("data");
                                                     Log.d("login_response", "1");
                                                     for (int i = 0; i < jsonArray.length(); i++) {
                                                         JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                                         Log.d("login_response", "1");
                                                         ModelClasses modelClasses =new ModelClasses();
                                                         modelClasses.setId(jsonObject1.getString("class_id"));
                                                         modelClasses.setName(jsonObject1.getString("class_name"));
                                                         modelClasses.setCouch_id(jsonObject1.getString("coach_id"));
                                                         modelClassesArrayList.add(modelClasses);
                                                         Log.d("login_response", jsonObject1.getString("class_name"));
                                                         //Save The Token Key In Prefrence

//
                                                     }
                                                     Log.d("login_response", String.valueOf(localFilePath));
                                                     adapterClasses = new AdapterSelectionItems(getActivity(),modelClassesArrayList,type,upload_btn,radioButtonClasses);
                                                     Log.d("login_response", "size of model class"+modelClassesArrayList.size());
                                                     if (modelClassesArrayList.size()>0){
                                                         recyclerViewClasses.setAdapter(adapterClasses);
                                                     }




                                                 } else {
                                                     Toasty.warning(getContext(), "Check Your Data", Toast.LENGTH_LONG).show();
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


