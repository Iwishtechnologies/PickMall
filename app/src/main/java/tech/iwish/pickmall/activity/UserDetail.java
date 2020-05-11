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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.OkhttpConnection.ConectOkhttp;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.connection.JsonHelper;
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
    ProgressBar progressBar;
    LinearLayout mainview;

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
        progressBar= findViewById(R.id.progress);
        mainview= findViewById(R.id.mainview);

        if(data.get(USER_NUMBER_CHECK) != null){
            startActivity(new Intent(UserDetail.this , MainActivity.class));
        }


        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender= male.getTag().toString();
                male.setAlpha((float) 0.5);
                female.setAlpha((float) 1.0);
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender= female.getTag().toString();
                female.setAlpha((float) 0.5);
                male.setAlpha((float) 1.0);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate(mobile.getText().toString(),gender))
                {
                    progressBar.setVisibility(View.VISIBLE);
                    mainview.setAlpha((float) 0.2);
                    userSession.CreateSession(mobile.getText().toString());
                    ClientData(mobile.getText().toString(),gender);

//                    if( conectOkhttp.ClientData(mobile.getText().toString(),gender))
//                    {
//                        userSession.user_number_check();
//                        GetUserProfile(userSession.getUserDetail().get("UserMobile"));
//                        progressBar.setVisibility(View.GONE);
//                        mainview.setAlpha(1);
//                        Intent intent= new Intent(UserDetail.this,MainActivity.class);
//                        startActivity(intent);
//                        Animatoo.animateFade(UserDetail.this);
//                    }

                }
            }
        });


    }



    public Boolean validate(String mobil,String gen)
    {
        if(mobil.equals(""))
        {
            mobile.setError("Is Empty");
            Toast.makeText(this, "Select incomplete", Toast.LENGTH_SHORT).show();
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

    public void GetUserProfile(String mobile)
    {


        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url("http://173.212.226.143:8086/api/Profile").post(body).build();
        okHttpClient1.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                UserDetail.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        mainview.setAlpha(1);
                        Toast.makeText(UserDetail.this, "Connection Time Out", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {

                    String result = response.body().string();
                    Log.e("response", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                userSession.setUserDetail(jsonHelper.GetResult("name"),jsonHelper.GetResult("gender"),jsonHelper.GetResult("image"),jsonHelper.GetResult("client_id"));

                            }

                            UserDetail.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    mainview.setAlpha(1);
                                    Intent intent= new Intent(UserDetail.this,MainActivity.class);
                                    startActivity(intent);
                                    Animatoo.animateFade(UserDetail.this);
                                }
                            });

                        }
                    }

                }


            }

        });
    }

    public void ClientData(String mobile, String gender)
    {


        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", mobile);
            jsonObject.put("gender", gender);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url("http://173.212.226.143:8086/api/Signup").post(body).build();
        okHttpClient1.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                UserDetail.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        mainview.setAlpha(1);
                        Toast.makeText(UserDetail.this, "Connection Time Out", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {

                    String result = response.body().string();
                    Log.e("response", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            UserDetail.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    userSession.user_number_check();
                                    GetUserProfile(userSession.getUserDetail().get("UserMobile"));
                                }
                            });

                        }
                    }

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
         isDestroyed();
        Toast.makeText(this, "Double tap to Exit", Toast.LENGTH_SHORT).show();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
