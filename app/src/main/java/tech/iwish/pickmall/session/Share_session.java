package tech.iwish.pickmall.session;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class Share_session {

    SharedPreferences Preferences;
    public static final String MyPREFERENCES = "TaxtSharepreferen";

    public static final String LOGIN_CHECk = "0";
    public static final String NAME_ADDRESS = "vikas";
    public static final String NUMBER_ADDRESS = "1234567915";
    public static final String PINCODE_ADDRESS = "417004";
    public static final String HOUSE_NO_ADDRESS = "51551";
    public static final String LOCATION_ADDRESS = "scsdcsdcsdcds";
    public static final String LANDMARK_ADDRESS = "dscsdc";
    public static final String STATE_ADDRESS = "mp";
    public static final String CITY_ADDRESS = "gwalior";
    public static final String USERMOBILE = "UserMobile";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String USER_NUMBER_CHECK = "0";


    SharedPreferences.Editor editor;

    int PRIVATE_MODE = 0;

    public Context context;

    public Share_session(Context context) {
        this.context = context;
        Preferences = context.getSharedPreferences(MyPREFERENCES, PRIVATE_MODE);
        editor = Preferences.edit();
    }

    public Map Fetchdata(){
        return this.Preferences.getAll();
    }

    public void  Login_check(){
        editor.putBoolean(LOGIN_CHECk, true).commit();
    }
    public void  user_number_check(){
        editor.putBoolean(USER_NUMBER_CHECK, true).commit();
    }

    public void address(String name , String number , String pincode , String house_no , String location , String landmark , String state , String city){
        editor.putString(NAME_ADDRESS,name ).commit();
        editor.putString(NUMBER_ADDRESS, number).commit();
        editor.putString(PINCODE_ADDRESS, pincode).commit();
        editor.putString(HOUSE_NO_ADDRESS, house_no).commit();
        editor.putString(LOCATION_ADDRESS, location).commit();
        editor.putString(LANDMARK_ADDRESS, landmark).commit();
        editor.putString(STATE_ADDRESS, state).commit();
        editor.putString(CITY_ADDRESS, city).commit();

    }

    public void CreateSession(String mobile) {
        editor.putString(USERMOBILE, mobile);
        editor.putString(IS_LOGIN, String.valueOf(true));
        editor.commit();

    }



 }
