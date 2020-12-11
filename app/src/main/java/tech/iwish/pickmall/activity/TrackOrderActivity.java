package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ir.hamiss.internetcheckconnection.InternetAvailabilityChecker;
import ir.hamiss.internetcheckconnection.InternetConnectivityListener;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.TrackOrderAdapter;
import tech.iwish.pickmall.adapter.WishListAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.TrackorderList;
import tech.iwish.pickmall.other.WishlistList;
import tech.iwish.pickmall.session.Share_session;

public class TrackOrderActivity extends AppCompatActivity implements InternetConnectivityListener {
      ImageView outfordelivery,shipped,packed,contactus;
      TextViewFont orderid,date;
      LinearLayout shi;
      RecyclerView recyclerView;
      List<TrackorderList>trackorderLists= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        InitializeActivity();
        SetActivityData();
        ActivityAction();
    }
    private void InitializeActivity(){
        packed= findViewById(R.id.packed);
        contactus= findViewById(R.id.contact);
        orderid= findViewById(R.id.orderid);
        date= findViewById(R.id.date);
        shi= findViewById(R.id.shi);
        recyclerView= findViewById(R.id.recycle);
        GetChennelIdOrderID(getIntent().getExtras().getString("orderid"));
        Connectivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TrackOrderActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void SetActivityData(){
       orderid.setText("OrderID : "+getIntent().getExtras().getString("uniqueid"));
       date.setText("Purchase Date - "+getIntent().getExtras().getString("date"));
    }

    private void ActivityAction(){
        contactus.setOnClickListener(v -> {
        startActivity(new Intent(TrackOrderActivity.this,ContactUsActivity.class));
        });
    }
//
    private void GetChennelIdOrderID(String orderid){

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",orderid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.CHENNELID_ORDERID)
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
                                TrackOrder(jsonHelper.GetResult("chennel_id"),jsonHelper.GetResult("shipment_id"),jsonHelper.GetResult("token"));
                                             }
                        }
                        else
                        {
                            TrackOrderActivity.this.runOnUiThread(() -> {

                            });
                        }
                    }

                }
            }
        });


    }


    private void TrackOrder(String chennelid ,String orderid, String token){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://apiv2.shiprocket.in/v1/external/courier/track/shipment/"+orderid+"")
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization","Bearer " + token)
                .addHeader("cache-control", "no-cache")
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
                    JSONObject mainObject,tracking_data,shipment_track_activities = null;
                    try {
                        mainObject = new JSONObject(result);
                        tracking_data = new JSONObject(mainObject.getString("tracking_data"));
                        JsonHelper jsonHelper = new JsonHelper(mainObject.getString("tracking_data"));
                        String trackStatus = tracking_data.getString("track_status");
                       if(trackStatus.equals("1")){
                           JSONArray jsonArray = jsonHelper.setChildjsonArray(tracking_data, "shipment_track_activities");
                           for (int i = 0; i < jsonArray.length(); i++) {
                               jsonHelper.setChildjsonObj(jsonArray, i);
                               trackorderLists.add(new TrackorderList(jsonHelper.GetResult("date"),jsonHelper.GetResult("location"),jsonHelper.GetResult("activity")));
                           }
                           TrackOrderActivity.this.runOnUiThread(() -> {
                               if(trackorderLists.size()==0){
                                   recyclerView.setVisibility(View.GONE);
                                   shi.setVisibility(View.VISIBLE);
                               }
                               else {
                                   recyclerView.setVisibility(View.VISIBLE);
                                   shi.setVisibility(View.GONE);
                                   TrackOrderAdapter trackOrderAdapter= new TrackOrderAdapter(TrackOrderActivity.this,trackorderLists);
                                   recyclerView.setAdapter(trackOrderAdapter);
                               }

                           });



                       }else {

                       }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public void Connectivity(){
        InternetAvailabilityChecker mInternetAvailabilityChecker;
        mInternetAvailabilityChecker = InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker.addInternetConnectivityListener(TrackOrderActivity.this);
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected){
        }
        else {
            startActivity(new Intent(TrackOrderActivity.this,NoInternetConnectionActivity.class));
        }
    }



}