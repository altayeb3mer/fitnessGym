package com.example.fitnessgym.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "fcmsharedprefdemo";
    private static final String KEY_ACCESS_TOKEN = "token";

    private static Context mCtx;
    private static SharedPrefManager mInstance;

    public SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null)
            mInstance = new SharedPrefManager(context);
        return mInstance;
    }


    public void storeToken(String token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ACCESS_TOKEN, "Bearer" + " " + token);
        editor.apply();
    }


    public String GetToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, "");
    }


    public void PutOpenState(boolean val) {
        SharedPreferences sp = mCtx.getSharedPreferences(SHARED_PREF_NAME, mCtx.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("is_first_open", val);
        edit.apply();
    }

    public boolean Is_first_open() {
        SharedPreferences sp = mCtx.getSharedPreferences(SHARED_PREF_NAME, mCtx.MODE_PRIVATE);
        return sp.getBoolean("is_first_open", true);
    }

    public boolean FirstTradeOfferDetailsImgTuch() {
        SharedPreferences sp = mCtx.getSharedPreferences(SHARED_PREF_NAME, mCtx.MODE_PRIVATE);
        return sp.getBoolean("is_first", true);
    }




}