package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.sqlconnection.MyhelperSql;

import static tech.iwish.pickmall.session.Share_session.USERMOBILE;

public class SuccessfullyActivity extends AppCompatActivity {

    private ImageView successButton;
    private SQLiteDatabase sqLiteDatabase;
    private MyhelperSql myhelperSql;
    private Button failed;
    Timer myTimer;
    TextView time;
    public int counter=10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_successfully);
           successButton = (ImageView)findViewById(R.id.successButton);
           time = findViewById(R.id.time);

           myhelperSql = new MyhelperSql(this);
           sqLiteDatabase = myhelperSql.getWritableDatabase();
            Timer();
           sqLiteDatabase.execSQL("delete from "+ "PRODUCT_CARD");

           successButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   startActivity(new Intent(SuccessfullyActivity.this , MainActivity.class));
               }
           });

       }









    @Override
    public void onBackPressed() {
        startActivity(new Intent(SuccessfullyActivity.this,MainActivity.class));
    }

    private void Timer(){
        new CountDownTimer(10000, 1000){
            public void onTick(long millisUntilFinished){
                time.setText("Automatically finish  00:"+String.valueOf(counter));
                counter--;
            }
            public  void onFinish(){
                time.setVisibility(View.GONE);
                startActivity(new Intent(SuccessfullyActivity.this , MainActivity.class));
            }
        }.start();
    }



}





























