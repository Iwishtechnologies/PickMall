package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.USERMOBILE;
import static tech.iwish.pickmall.session.Share_session.USERNAME;

public class SuggestionBoxActivity extends AppCompatActivity {

    EditText editSuggestion;
    Share_session shareSession;
    Map data;
    ImageButton submit;
    ProgressBar progress_bar;
    TextView names;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion_box);

        editSuggestion = findViewById(R.id.editSuggestion);
        submit = findViewById(R.id.submit);
        progress_bar = findViewById(R.id.progress_bar);
        names = findViewById(R.id.names);
        progress_bar.setVisibility(View.GONE);
        back = findViewById(R.id.back);

        shareSession = new Share_session(this);
        data = shareSession.Fetchdata();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        names.setText(data.get(USERNAME).toString());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit.setClickable(false);
                progress_bar.setVisibility(View.VISIBLE);
                if (editSuggestion.getText().toString().trim().isEmpty()) {

                } else {
                    suggestionInsert();
                }
                submit.setClickable(true);
                progress_bar.setVisibility(View.GONE);
            }
        });

    }

    private void suggestionInsert() {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("suggestion", editSuggestion.getText().toString().trim());
            jsonObject.put("number",data.get(USERMOBILE).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.SUGGESTION).post(body).build();
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
                            startActivity(new Intent(SuggestionBoxActivity.this , MessageActivity.class));
                        }
                    }
                }
            }
        });


    }
}



























