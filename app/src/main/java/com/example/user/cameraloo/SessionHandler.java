package com.example.user.cameraloo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Date;

/**
 * Created by Abhi on 20 Jan 2018 020.
 */

public class SessionHandler {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "username";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logs in the user by saving user details and setting session
     *
     * @param username
     * @param
     */
    public void loginUser(String username) {
        mEditor.putString(KEY_USERNAME, username);

        mEditor.commit();
    }
    public String getUser(){
        String name;
        name = mPreferences.getString(KEY_USERNAME,"");
        return name;
    }

    /**
     * Checks whether user is logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        if (mPreferences.getAll().size()>0) {
            Log.d("isLoggedIn","sini true");
            int n = mPreferences.getAll().size();
            Log.d("isLoggedIn",Integer.toString(n));
            return true;
        }
        else
            return false;


    }

    /**
     * Fetches and returns user details
     *
     * @return user details
     */


    /**
     * Logs out user by clearing the session
     */
    public void logoutUser() {
        mEditor.clear();
        mEditor.commit();
    }
}