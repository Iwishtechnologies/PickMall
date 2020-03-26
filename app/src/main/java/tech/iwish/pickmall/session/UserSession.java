package tech.iwish.pickmall.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import tech.iwish.pickmall.activity.MainActivity;

/**
 * Created by rrdreamtechnology on 25/03/2020.
 */

public class UserSession {


    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "UserSessionPref";

//    private static final String USERMOBILE = "UserMobile";
//
//    private static final String IS_LOGIN = "IsLoggedIn";

//    private static final String PREF_N/


    // Constructor
    public UserSession(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    /**
     * save user profile information or create login session
     */
//    public void CreateSession(String mobile) {
//        editor.putString(USERMOBILE, mobile);
//        editor.putString(IS_LOGIN, String.valueOf(true));
//        editor.commit();
//
//    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        // user name
//        user.put(SUPERVISORID, pref.getString(SUPERVISORID, null));
//
//        // user email id
//        user.put(SUPERVISORNAME, pref.getString(SUPERVISORNAME, null));
//
//        // user phone number
//        user.put(HOSPITALID, pref.getString(HOSPITALID, null));
//        user.put(PHOTO, pref.getString(PHOTO, null));
//        user.put(HOSPITALNAME, pref.getString(HOSPITALNAME, null));

        // user avatar
//        user.put(KEY_PHOTO, pref.getString(KEY_PHOTO, null)) ;

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
//        editor.putBoolean(IS_LOGIN,false);
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }


}