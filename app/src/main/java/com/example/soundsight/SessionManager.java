package com.example.soundsight;

import android.content.SharedPreferences;
import android.content.Context;

public class SessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_IMPAIRMENT_TYPE = "impairmentType";
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    // Default constructor
    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Set login status and user details
    public void setLogin(boolean isLoggedIn, String email, String impairmentType) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_IMPAIRMENT_TYPE, impairmentType);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public String getImpairmentType() {
        return sharedPreferences.getString(KEY_IMPAIRMENT_TYPE, "");
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
