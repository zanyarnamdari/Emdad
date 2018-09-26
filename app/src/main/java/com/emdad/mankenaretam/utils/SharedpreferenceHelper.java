package com.emdad.mankenaretam.utils;

/**
 * Created by HP on 2018/02/02.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

//import com.emdad.mankenaretam.MaterialDesignLogInForm;
import com.emdad.mankenaretam.MaterialDesignLogInForm;
import com.emdad.mankenaretam.model.User;

import java.util.HashMap;

//import com.rayagroup.order.LoginActivity;


public class SharedpreferenceHelper {


    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;


    private static final String PREF_NAME = "emdad";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_ID = "id";
    public static final String KEY_NATIONALCODEE = "nationalCode";
    public static final String KEY_FIRSNAME="firstname";
    public static final String KEY_LASTNAME="lasttname";
    public static final String KEY_PHONE="phone";
    private static final String cod = "cod";




    public SharedpreferenceHelper(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createLoginSession(User user){

        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_FIRSNAME,user.getName());
        editor.putString(KEY_LASTNAME,user.getFamily());
        editor.putString(KEY_NATIONALCODEE, String.valueOf(user.getNationalCode()));
        editor.putString(KEY_PHONE, String.valueOf(user.getMobileNumber()));
        editor.putLong(KEY_ID,  user.getMobileUserId());

        // commit changes
        editor.commit();
    }


    public void getttt(String f){

        editor.putString(cod,f);


        editor.commit();
    }

    public void createLoginSession(String id) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID, id);
        editor.commit();
    }






    public HashMap<String, String> getUserDetails(){

        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_ID, pref.getString(KEY_ID, null));


        // return user
        return user;
    }

    public long getStoredId() {

        return pref.getLong(KEY_ID,0);
    }

    public String getCod() {

        return pref.getString(cod,null);
    }




    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, MaterialDesignLogInForm.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

}






