package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.WishListAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.WishlistList;

import static tech.iwish.pickmall.session.Share_session.USER_NUMBER_CHECK;

public class ForgotpasswordActivity extends AppCompatActivity {
    TextViewFont signin,resend,time;
    EditText number,otp,passwords,Confirmpassword;
    Button generate,reset;
    ProgressBar progress;
    LinearLayout linearLayout;
    Timer myTimer;
    public int counter=60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        InitializeActitvity();
        SetActivityData();
        ActivityAction();
    }

    private void InitializeActitvity(){
        signin= findViewById(R.id.signin);
        number= findViewById(R.id.number);
        otp= findViewById(R.id.otp);
        passwords= findViewById(R.id.passwords);
        signin= findViewById(R.id.signin);
        Confirmpassword= findViewById(R.id.Confirmpassword);
        generate= findViewById(R.id.generate);
        reset= findViewById(R.id.reset);
        progress= findViewById(R.id.progress);
        linearLayout= findViewById(R.id.linearLayout);
        resend= findViewById(R.id.resend);
        time= findViewById(R.id.time);
    }

    private void SetActivityData(){
        otp.setVisibility(View.GONE);
        passwords.setVisibility(View.GONE);
        Confirmpassword.setVisibility(View.GONE);
        reset.setVisibility(View.GONE);
    }
    private void ActivityAction(){
       signin.setOnClickListener(view -> {
           startActivity(new Intent(ForgotpasswordActivity.this,Sign_InActivity.class));
       });
       generate.setOnClickListener(view -> {
           if(ValidateInput(number.getText().toString())){
               progress.setVisibility(View.VISIBLE);
               linearLayout.setAlpha((float) 0.2);
                RequestOTP(number.getText().toString());
           }
       });
       reset.setOnClickListener(view -> {
           if(ValidatePassword(otp.getText().toString(),passwords.getText().toString(),Confirmpassword.getText().toString()))
           {
               progress.setVisibility(View.VISIBLE);
               linearLayout.setAlpha((float) 0.2);
               CheckOTP(otp.getText().toString(),passwords.getText().toString(),number.getText().toString());
           }

       });
        resend.setOnClickListener(view -> {
            progress.setVisibility(View.VISIBLE);
            linearLayout.setAlpha((float) 0.2);
            RequestOTP(number.getText().toString());
        });

    }

    private Boolean ValidateInput(String num){
        if(num.length()==10){
            return true;
        }else {
            return false;
        }
    }

    private Boolean ValidatePassword(String ot,String pass,String conf){
        if(ot.length()==4){
            if(pass.equals(conf)){
                if(pass.length()>8){
                    return true;
                }else {
                    passwords.setError("minium 8 digit password");
                    Confirmpassword.setError("minium 8 digit password");
                    return false;
                }
            }
            else {
//                passwords.setError("minium 8 digit password");
                Confirmpassword.setError("password did not match" );
                return false;
            }
        } else {
            otp.setError("Invalid OTP");
            return false;
        }
    }

    private void RequestOTP(String num){
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile",num);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.FORGOTPASSWORD)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                ForgotpasswordActivity.this.runOnUiThread(() -> {
                    progress.setVisibility(View.GONE);
                    linearLayout.setAlpha(1);
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();

                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            ForgotpasswordActivity.this.runOnUiThread(() -> {
                                progress.setVisibility(View.GONE);
                                linearLayout.setAlpha(1);
                                number.setVisibility(View.GONE);
                             generate.setVisibility(View.GONE);
                             otp.setVisibility(View.VISIBLE);
                             passwords.setVisibility(View.VISIBLE);
                             Confirmpassword.setVisibility(View.VISIBLE);
                             reset.setVisibility(View.VISIBLE);
                             resend.setVisibility(View.GONE);
                             time.setVisibility(View.VISIBLE);
                             Timer();
                            });

                        }else {
                            progress.setVisibility(View.GONE);
                            linearLayout.setAlpha(1);
                            ForgotpasswordActivity.this.runOnUiThread(() -> {
                                Toast.makeText(ForgotpasswordActivity.this, jsonHelper.GetResult("data"), Toast.LENGTH_SHORT).show();

                            });
                        }
                    }

                }
            }
        });
    }

    private void CheckOTP(String otp1,String pass,String mobile){
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("otp",otp1);
            jsonObject.put("pass",pass);
            jsonObject.put("mobile",mobile);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.VARIFYOTP)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                ForgotpasswordActivity.this.runOnUiThread(() -> {
                    progress.setVisibility(View.GONE);
                    linearLayout.setAlpha(1);
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();

                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            ForgotpasswordActivity.this.runOnUiThread(() -> {
                                progress.setVisibility(View.GONE);
                                linearLayout.setAlpha(1);
                               Toast.makeText(ForgotpasswordActivity.this, jsonHelper.GetResult("data"), Toast.LENGTH_SHORT).show();
                               startActivity(new Intent(ForgotpasswordActivity.this,Sign_InActivity.class));
                            });

                        }else {

                            ForgotpasswordActivity.this.runOnUiThread(() -> {
                                progress.setVisibility(View.GONE);
                                linearLayout.setAlpha(1);
                                Toast.makeText(ForgotpasswordActivity.this, jsonHelper.GetResult("data"), Toast.LENGTH_SHORT).show();

                            });
                        }
                    }

                }
            }
        });
    }

    private void Timer(){
        new CountDownTimer(60000, 1000){
            public void onTick(long millisUntilFinished){
                time.setText("resend OTP  00:"+String.valueOf(counter));
                counter--;
            }
            public  void onFinish(){
                time.setVisibility(View.GONE);
                resend.setVisibility(View.VISIBLE);
            }
        }.start();
    }
}