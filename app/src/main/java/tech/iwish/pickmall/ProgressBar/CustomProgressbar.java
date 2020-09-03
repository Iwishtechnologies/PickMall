package tech.iwish.pickmall.ProgressBar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import tech.iwish.pickmall.R;

public class CustomProgressbar {

    public AlertDialog dialog;
    public Activity activity;

    public CustomProgressbar(Activity activity){
        this.activity = activity;
    }

    public void startprogress(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.row_custom_progress_bar , null));

        dialog = builder.create();
        dialog.show();;

    }

    public void dismissdialog(){
        dialog.dismiss();
    }


}
