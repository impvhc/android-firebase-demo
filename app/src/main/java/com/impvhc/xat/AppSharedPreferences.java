package com.impvhc.xat;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by victor on 8/7/17.
 */

public class AppSharedPreferences {
    private static AppSharedPreferences sInstance;
    private SharedPreferences mSharedPreferences;
    private Context mContext;

    /**/
    public static final String EMPTY = "";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    /**/

    public AppSharedPreferences(Context context) {
        this.mContext = context;
        mSharedPreferences = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
    }

    public void putEmail(String email){
        putString(EMAIL,email);
    }

    public String getEmail(){
        return getString(EMAIL);
    }

    public void putPassword(String password){
        putString(PASSWORD,password);
    }

    public String getPassword(){
        return getString(PASSWORD);
    }

    /*Default Shared Preferences*/
    private void putString(String key, String value){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    private void putInt(String key, int value){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key,value);
        editor.apply();
    }

    private void putBoolean(String key, boolean value){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    private String getString(String key){
        return mSharedPreferences.getString(key,EMPTY);
    }

    private int getInt(String key, int value){
        return mSharedPreferences.getInt(key,0);
    }

    private boolean getBoolean(String key, boolean value){
        return mSharedPreferences.getBoolean(key,false);
    }
    /**/
}
