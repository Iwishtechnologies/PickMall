package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfully);

        successButton = (ImageView)findViewById(R.id.successButton);

        myhelperSql = new MyhelperSql(this);
        sqLiteDatabase = myhelperSql.getWritableDatabase();

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





}





























