package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;

public class Sign_InActivity extends AppCompatActivity {

    EditText number, password;
    ImageButton signInButton;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        number = findViewById(R.id.number);
        password = findViewById(R.id.password);
        signInButton = findViewById(R.id.signInButton);
        linearLayout = findViewById(R.id.linearLayout);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linearLayout.setAlpha((float) 0.5);

                OkHttpClient okHttpClient = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("number", number.getText().toString().trim());
                    jsonObject.put("password", password.getText().toString().trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                Request request1 = new Request.Builder().url(Constants.SIGN_IN).post(body).build();
                okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String result = response.body().string();
                            Log.e("result", result);
                            JsonHelper jsonHelper = new JsonHelper(result);
                            if (jsonHelper.isValidJson()) {
                                String responses = jsonHelper.GetResult("response");
                                if (responses.equals("TRUE")) {
                                    Intent intent = new Intent(Sign_InActivity.this, SelectGenderActivity.class);
                                    intent.putExtra("number", number.getText().toString().trim());
                                    startActivity(intent);
                                    linearLayout.setAlpha(1);
                                } else {
                                    Sign_InActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            linearLayout.setAlpha(1);
                                            Toast.makeText(Sign_InActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }
                    }
                });

            }
        });

    }
}