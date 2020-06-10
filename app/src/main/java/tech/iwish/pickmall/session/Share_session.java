package tech.iwish.pickmall.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tech.iwish.pickmall.activity.UserDetail;

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
    public static final String PINCODR_SERVICE_CHECK = "pincode";
    public static final String USERNAME = "username";
    public static final String USERGENDER = "gender";
    public static final String PROFILEIMAGE = "image";
    public static final String ACCOUNTID = "id";
    public static final String WALLET_AMOUNT = "wallet amount";
    public static final String FILTER_LIST = "filter";
    public static final String FILTER_LIST_COLOR = "filter_color";




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
        editor.putString(USERMOBILE, mobile).commit();
        editor.putString(IS_LOGIN, String.valueOf(true)).commit();

    }
    public void pincode_service_check(String pincode) {
        editor.putString(PINCODR_SERVICE_CHECK, pincode).commit();

    }

    public HashMap<String,String> getUserDetail()
    {
        HashMap<String,String> data = new HashMap<>();
        data.put(USERMOBILE,Preferences.getString(USERMOBILE,null));
        data.put(USERNAME,Preferences.getString(USERNAME,null));
        data.put(USERGENDER,Preferences.getString(USERGENDER,null));
        data.put(PROFILEIMAGE,Preferences.getString(PROFILEIMAGE,null));
        data.put(ACCOUNTID,Preferences.getString(ACCOUNTID,null));
        return  data;
    }

    public void UpdateUserDetail(String data,String Parameter)
    {
        if(Parameter.equals("name"))
        {
            editor.putString(USERNAME,data).commit();
        }
        if(Parameter.equals("gender"))
        {
            editor.putString(USERGENDER,data).commit();
        }
        if(Parameter.equals("image"))
        {
            editor.putString(PROFILEIMAGE,data).commit();
        }
    }

    public void setUserDetail(String name , String gender , String image , String account_id)
    {
        editor.putString(USERNAME,name);
        editor.putString(USERGENDER,gender);
        editor.putString(PROFILEIMAGE, image);
        editor.putString(ACCOUNTID,account_id);
        editor.commit();
    }

    public void walletAmount(String amt){
        editor.putString(WALLET_AMOUNT , amt).commit();
    }


    public void Logout(){
        editor.clear().commit();
        context.startActivity(new Intent(context, UserDetail.class));
        Animatoo.animateInAndOut(context);
    }


    public void filterMethod(JSONObject list){
        editor.putString(FILTER_LIST ,list.toString());
        editor.commit();
    }

    public String getfilterMethod(){
        return Preferences.getString(FILTER_LIST,null);
    }

    public void filterSizeRemoveMethod(){
//        editor.putString(FILTER_LIST ,list.toString());
        editor.remove(FILTER_LIST).commit();
    }
    public void filterColorRemoveMethod(){
//        editor.putString(FILTER_LIST ,list.toString());
        editor.remove(FILTER_LIST_COLOR).commit();
    }

    public void filterColor(JSONObject list){
        editor.putString(FILTER_LIST_COLOR ,list.toString());
        editor.commit();
    }
    public  void  SetProfileImage(String image){
        editor.putString(PROFILEIMAGE,image).commit();
    }


 }





























