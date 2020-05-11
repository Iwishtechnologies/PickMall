package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.MyOrderAdapter;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.session.Share_session;

public class InviteActivity extends AppCompatActivity {
    private static final int MAX_LENGTH =20;
    Button invite;
    ImageView back;
    TextViewFont invitecode;
    Share_session share_session;
    ProgressBar progressBar;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        InitializeActivity();
        SetActivityData();
        ActivityAction();
    }



    protected void InitializeActivity(){
        invite= findViewById(R.id.invite);
        back= findViewById(R.id.back);
        invitecode= findViewById(R.id.invitecode);
        progressBar= findViewById(R.id.progress);
        scrollView= findViewById(R.id.scroll);
        share_session= new Share_session(InviteActivity.this);
        SaveReferrelCode(random(share_session.getUserDetail().get("id")),share_session.getUserDetail().get("UserMobile"));
    }


    protected void  ActivityAction(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
       shareTextUrl();
            }
        });
    }

    protected void SetActivityData(){
//          invitecode.setText(random(share_session.getUserDetail().get("id")));
    }


    public static String random(String id) {
        Random ran = new Random();
        int top = 8;
        char data = ' ';
        String dat = "";

        for (int i=0; i<=top; i++) {
            data = (char)(ran.nextInt(25)+97);
            dat = data + dat ;
        }
        return dat + id;
    }

    private void SaveReferrelCode(String code,String mobile1){
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ref",code);
            jsonObject.put("mobile",mobile1);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url("http://173.212.226.143:8086/api/referrel")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    final JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            InviteActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    invitecode.setText(jsonHelper.GetResult("data"));
                                    progressBar.setVisibility(View.GONE);
                                    scrollView.setAlpha(1);

                                }
                            });

                        }
                    }

                }
            }
        });

    }


    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Share pickmall & raen money");
        share.putExtra(Intent.EXTRA_TEXT,"tech.iwish.pickmall");
        startActivity(Intent.createChooser(share, "Share link!"));
    }

}
