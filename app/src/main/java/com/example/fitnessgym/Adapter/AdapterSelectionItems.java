package com.example.fitnessgym.Adapter;


import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.fitnessgym.Constants;
import com.example.fitnessgym.Fragment.FragmentTabVideos;
import com.example.fitnessgym.Model.ModelClasses;
import com.example.fitnessgym.R;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;


public class AdapterSelectionItems extends RecyclerView.Adapter<AdapterSelectionItems.ViewHolder> {

    ArrayList<ModelClasses> modelClassesArrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;


    Uri localFilePath;
    String type;
    String class_id;
    AppCompatButton upload_btn;
    RadioGroup radioGroupClasses;
    boolean isSelected  = false;
    int previousSelectedPosition = -1;


    public AdapterSelectionItems(Activity activity, ArrayList<ModelClasses> modelClasses, String type, AppCompatButton upload_btn,RadioGroup radioGroupClasses) {
        this.mInflater = LayoutInflater.from(activity);
        this.modelClassesArrayList = modelClasses;
        this.type=type;
        this.localFilePath=localFilePath;
        this.upload_btn=upload_btn;
        this.activity = activity;
        this.radioGroupClasses=radioGroupClasses;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.main_selection_classes_items, parent, false);


        return new ViewHolder(view);
    }

    private RadioButton selectedRadioButton;
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelClasses item = modelClassesArrayList.get(position);
       // RadioGroup radioGroup = new RadioGroup(activity);
        holder.class_name.setText(item.getName());

//        holder.linearLayoutRadioButton.addView(radioGroup);
//
////        new RadioGroup(activity).addView(holder.class_name);
//

        if(holder.class_name.getParent() != null) {
            ((ViewGroup)holder.class_name.getParent()).removeView(holder.class_name); // <- fix
        radioGroupClasses.addView(holder.class_name);}
        RadioButton radioButton = holder.class_name;
        radioButton.setChecked(modelClassesArrayList.get(position).getSelected());

        radioButton.setOnClickListener(new View.OnClickListener() {

                                           @Override
                                           public void onClick(View v) {

                                               // Set unchecked all other elements in the list, so to display only one selected radio button at a time
                                               for(ModelClasses model: modelClassesArrayList)
                                                   model.setSelected(false);

                                               // Set "checked" the model associated to the clicked radio button
                                               modelClassesArrayList.get(position).setSelected(true);
                                               class_id=item.getId();

                                               // If current view (RadioButton) differs from previous selected radio button, then uncheck selectedRadioButton
                                               if(null != selectedRadioButton && !v.equals(selectedRadioButton))
                                                   selectedRadioButton.setChecked(false);

                                               // Replace the previous selected radio button with the current (clicked) one, and "check" it
                                               selectedRadioButton = (RadioButton) v;
                                               selectedRadioButton.setChecked(true);

                                           }
                                       });




upload_btn.setOnClickListener(new View.OnClickListener() {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {

        if(FragmentTabVideos.localFilePath!=null){
        uploadVideo(item.getCouch_id(),class_id);}


        else Toasty.warning(activity, "الرجاء اختيار فيديو", Toast.LENGTH_LONG).show();
    }
});

//        Glide.with(activity).load("").into(holder.imageView);



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
        AppCompatRadioButton class_name;
        LinearLayout linearLayoutRadioButton;
        RadioGroup radioGroupClasses;

        ViewHolder(View itemView) {
            super(itemView);
            class_name = itemView.findViewById(R.id.class_name);

//            radioGroupClasses=itemView.findViewById(R.id.classes);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void uploadVideo(String coach_id, String class_id){


        final KProgressHUD progressDialog;// Validation
        progressDialog = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("الرجاء الانتظار")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        Log.d("login_response", String.valueOf(FragmentTabVideos.localFilePath));




       String path = getPath(activity,FragmentTabVideos.localFilePath);

        final File fileToUpload = new File(path);
        Log.d("login_response", class_id +"_______"+coach_id);
        Ion.with(activity)
                .load("POST", Constants.Video_url)
                .setTimeout(60 * 60 * 1000)
                .setMultipartFile("file","video/mp4", fileToUpload)
                .setMultipartParameter("vid_title","")
                .setMultipartParameter("coach_id",coach_id)
                .setMultipartParameter("class_id",class_id)
                .setMultipartParameter("vid_tag",type)

                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String response) {
                        progressDialog.dismiss();


                        //Toasty.error(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                        try {

                            if ((e != null)) {
                                Toasty.warning(activity, "تحقق من اتصال الانترنت  "+ e, Toast.LENGTH_LONG).show();

                            } else {

                                Log.d("login_response", response);





                                if (response.contains("Upload")) {

                                    Toasty.success(activity, response, Toast.LENGTH_LONG).show();




                                } else {
                                    Toasty.warning(activity, "Check Your Data", Toast.LENGTH_LONG).show();
//

                                }

                            }

                        } catch (Exception ex) {
                        }
                    }
                    });
    }
//    public String getPath(Uri uri) {
//        String[] projection = { MediaStore.Video.Media.DATA };
//        Cursor cursor =activity.getContentResolver ().query(uri, projection, null, null, null);
//
//        if (cursor != null) {
//            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
//            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
//            int column_index = cursor
//                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
//            cursor.moveToFirst();
//
//            Log.d("login_response", String.valueOf(cursor.getString(column_index))+"__________"+uri);
//
//            if (cursor.getType(column_index) == FIELD_TYPE_STRING) {
//                return cursor.getString(column_index);
//            }
//            else  return null;
//        } else
//            return null;
//    }





    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}
