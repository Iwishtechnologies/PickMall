package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.OkhttpConnection.ConectOkhttp;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.ColorSizeImageAdapter;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.ProductSizeColorList;
import tech.iwish.pickmall.session.UserSession;

public class UserDetail extends AppCompatActivity {
    LinearLayout male,female;
    EditText mobile;
    ImageView  next;
    String gender="empty";
    UserSession userSession;
    ConectOkhttp conectOkhttp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_detail);
        userSession= new UserSession(this);
        conectOkhttp= new ConectOkhttp();

        male= findViewById(R.id.male);
        female= findViewById(R.id.female);
        mobile= findViewById(R.id.mobile);
        next= findViewById(R.id.next);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender= male.getTag().toString();
                male.setAlpha((float) 0.5);
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender= female.getTag().toString();
                female.setAlpha((float) 0.5);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate(mobile.getText().toString(),gender))
                {
                        userSession.CreateSession(mobile.getText().toString());
                       if( conectOkhttp.ClientData(mobile.getText().toString(),gender))
                       {
                           Intent intent= new Intent(UserDetail.this,MainActivity.class);
                           startActivity(intent);
                           Animatoo.animateFade(UserDetail.this);
                       }

                }
            }
        });
    }



    public Boolean validate(String mobil,String gen)
    {
       if(mobil.equals(""))
       {
          mobile.setError("Is Empty");
           Toast.makeText(this, "Select Gender", Toast.LENGTH_SHORT).show();
           return false;
       }

        if(gen.equals("empty"))
        {
            Toast.makeText(this, "Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return  true;
        }

    }



}
