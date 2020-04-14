package tech.iwish.pickmall.ProgressBar;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import tech.iwish.pickmall.R;

public class CustomProgressbar {

    public AlertDialog.Builder builder ;
    public Context context ;

    public CustomProgressbar(Context context){
        this.context = context ;
    }

    public void startprogress(){
        builder = new AlertDialog.Builder(context);
        View view = View.inflate(context,R.layout.row_all_category_item , null);

        builder.setView(view);
        builder.create();
    }


}
