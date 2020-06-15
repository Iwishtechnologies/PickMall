package tech.iwish.pickmall.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.widget.Toast;

import tech.iwish.pickmall.Interface.InterNetConnectionCheckInteface;


public class InterNetConnection extends BroadcastReceiver {

    public boolean aBoolean;


    @Override
    public void onReceive(Context context, Intent intent) {


        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null) {
            aBoolean = true;
            CheckInternet(true);

//            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
//            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
//            }

        } else {
            aBoolean = false;
            CheckInternet(false);
        }
    }

    public boolean CheckInternet(boolean b){
        return aBoolean;
    }


}
