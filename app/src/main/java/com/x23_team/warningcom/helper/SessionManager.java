package com.x23_team.warningcom.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.x23_team.warningcom.app.AppConfig;

/**
 * Created by weste on 5/10/2017.
 */

public class SessionManager {
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Login";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    private static String Key_API = "KEY";
    private static String Key_PASS = "PASS";
    private static String Key_EMAIL = "EMAIL";
    private static String Key_UserId = "UserId";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public String getPASS() {
        return pref.getString(Key_PASS,"");
    }
    public String getUserId(){ return pref.getString( Key_UserId,"" );}

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.putString(Key_UserId, AppConfig.KEY_UserID);
        editor.putString(Key_EMAIL, AppConfig.KEY_EMAIL);


        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public String getKey(){
        return pref.getString(Key_API,"");
    }
    public String getEmail(){
        return pref.getString( Key_EMAIL,"" );
    }
}
