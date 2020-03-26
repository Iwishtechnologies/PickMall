package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.Map;

import tech.iwish.pickmall.OkhttpConnection.ConectOkhttp;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.session.Share_session;
import tech.iwish.pickmall.session.UserSession;

import static tech.iwish.pickmall.session.Share_session.USER_NUMBER_CHECK;

public class UserDetail extends AppCompatActivity {

    private LinearLayout male,female;
    private EditText mobile;
    private ImageView next;
    private String gender="empty";
    private Share_session userSession;
    private ConectOkhttp conectOkhttp;
    private Map data ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_user_detail);
        userSession= new Share_session(this);
        data = userSession.Fetchdata();
        conectOkhttp= new ConectOkhttp();

        male= findViewById(R.id.male);
        female= findViewById(R.id.female);
        mobile= findViewById(R.id.mobile);
        next= findViewById(R.id.next);

        if(data.get(USER_NUMBER_CHECK).toString() != null){
            startActivity(new Intent(UserDetail.this , MainActivity.class));
        }


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
                        userSession.user_number_check();
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
        if(mobil.length() < 10)
        {
            mobile.setError("Is Empty");
            Toast.makeText(this, "Number incomplete", Toast.LENGTH_SHORT).show();
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
