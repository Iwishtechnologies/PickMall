package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.SupportAdapter;
import tech.iwish.pickmall.adapter.WishListAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.SupportQueryList;
import tech.iwish.pickmall.other.WishlistList;

public class SupportActivity extends AppCompatActivity {
    LinearLayout mainView;
    ImageView back;
   RecyclerView recyclerView;
    LinearLayout shimmer_view;

    List<SupportQueryList>supportQueryLists= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        InitializeActivity();
        SetRecycleView();
        ActivityAction();
    }

    private void InitializeActivity(){
        mainView= findViewById(R.id.mainView);
        recyclerView= findViewById(R.id.recycle);
        shimmer_view= findViewById(R.id.shimmerView);
        back= findViewById(R.id.back);
    }

    private void ActivityAction(){
         back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 onBackPressed();
             }
         });
    }



    private void SetRecycleView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SupportActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.SUPPORTQUERY)
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

                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                supportQueryLists.add(new SupportQueryList(jsonHelper.GetResult("id"),jsonHelper.GetResult("query"),jsonHelper.GetResult("answer")));
                                  }
                            SupportActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(supportQueryLists.size()==0){
                                        shimmer_view.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.GONE);
                                    }
                                    else {
                                        SupportAdapter supportAdapter = new SupportAdapter(SupportActivity.this, supportQueryLists);
                                        shimmer_view.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.VISIBLE);
                                        recyclerView.setAdapter(supportAdapter);
                                    }

                                }
                            });

                        }
                    }

                }
            }
        });


    }
}
