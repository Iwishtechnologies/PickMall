package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.ShippingAddressAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.AddressDataList;
import tech.iwish.pickmall.other.ShippingAddressList;
import tech.iwish.pickmall.session.Share_session;

public class EditAddressActivity extends AppCompatActivity {

    private List<AddressDataList> addressDataLists = new ArrayList<>();
    private RecyclerView address_recycleview;
    int type = 1;
    private LinearLayout new_addresss ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        address_recycleview = (RecyclerView) findViewById(R.id.address_recycleview);
        new_addresss = (LinearLayout) findViewById(R.id.new_addresss);


        Share_session share_session = new Share_session(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        address_recycleview.setLayoutManager(linearLayoutManager);


        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile",share_session.getUserDetail().get("UserMobile") );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.ADDRESS_FETCH)
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
                                addressDataLists.add(new AddressDataList(jsonHelper.GetResult("sno"),jsonHelper.GetResult("mobile"),jsonHelper.GetResult("name"),jsonHelper.GetResult("delivery_number"),jsonHelper.GetResult("pincode"),jsonHelper.GetResult("house_no"),jsonHelper.GetResult("colony"),jsonHelper.GetResult("landmark"),jsonHelper.GetResult("status")));
                            }
                            EditAddressActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ShippingAddressAdapter shippingAddressAdapter = new ShippingAddressAdapter(EditAddressActivity.this, null, type , addressDataLists);
                                    address_recycleview.setAdapter(shippingAddressAdapter);
                                }
                            });

                        }
                    }
                }

            }
        });

        new_addresss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditAddressActivity.this , AddressActivity.class);
                intent.putExtra("type" , "editAddress");
                startActivity(intent);
            }
        });
    }
}
