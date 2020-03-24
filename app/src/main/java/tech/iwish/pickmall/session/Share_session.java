package tech.iwish.pickmall.session;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class Share_session {

    SharedPreferences Preferences;
    public static final String MyPREFERENCES = "TaxtSharepreferen";

    public static final String Image_Url= "http://173.212.226.143:8086/img";


    SharedPreferences.Editor editor;

    int PRIVATE_MODE = 0;

    public Context context;

    public Share_session(Context context) {
        this.context = context;
        Preferences = context.getSharedPreferences(MyPREFERENCES, PRIVATE_MODE);
        editor = Preferences.edit();
    }

    public Map Fetchdata(){
        editor.putString(Image_Url , "http://173.212.226.143:8086/img");
        return this.Preferences.getAll();
    }



 }
