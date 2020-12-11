package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import ir.hamiss.internetcheckconnection.InternetAvailabilityChecker;
import ir.hamiss.internetcheckconnection.InternetConnectivityListener;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.Interface.AddressInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.ShippingAddressAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.AddressDataList;
import tech.iwish.pickmall.other.ShippingAddressList;
import tech.iwish.pickmall.session.Share_session;

public class EditAddressActivity extends AppCompatActivity implements View.OnClickListener, InternetConnectivityListener {

    private List<AddressDataList> addressDataLists = new ArrayList<>();
    private RecyclerView address_recycleview;
    int type = 1;
    private LinearLayout new_addresss;
    private Button deliver_btn;
    private AddressInterface addressInterface;
    private String posi , types;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        Connectivity();

        address_recycleview = (RecyclerView) findViewById(R.id.address_recycleview);
        new_addresss = (LinearLayout) findViewById(R.id.new_addresss);
        deliver_btn = (Button) findViewById(R.id.deliver_btn);

        Share_session share_session = new Share_session(this);

        deliver_btn.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        address_recycleview.setLayoutManager(linearLayoutManager);


        types = getIntent().getStringExtra("type");


        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", share_session.getUserDetail().get("UserMobile"));

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
                                addressDataLists.add(new AddressDataList(jsonHelper.GetResult("sno"), jsonHelper.GetResult("mobile"), jsonHelper.GetResult("name"), jsonHelper.GetResult("delivery_number"), jsonHelper.GetResult("pincode"), jsonHelper.GetResult("house_no"), jsonHelper.GetResult("colony"), jsonHelper.GetResult("landmark"), jsonHelper.GetResult("city"), jsonHelper.GetResult("state"), jsonHelper.GetResult("address_type"), jsonHelper.GetResult("status")));
                            }
                            if (EditAddressActivity.this != null) {

                                EditAddressActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ShippingAddressAdapter shippingAddressAdapter = new ShippingAddressAdapter(EditAddressActivity.this, null, type, addressDataLists, addressInterface);
                                        address_recycleview.setAdapter(shippingAddressAdapter);
                                    }
                                });
                            }
                        }
                    }
                }

            }
        });

        new_addresss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent;
                switch (types) {
                    case "CardActivity":
                        intent = new Intent(EditAddressActivity.this, AddressActivity.class);
                        intent.putExtra("type", "CardActivity");
                        startActivity(intent);
                        break;
                    case "friendDeal_one_rs":
                        intent = new Intent(EditAddressActivity.this, AddressActivity.class);
                        intent.putExtra("product_name", getIntent().getStringExtra("product_name"));
                        intent.putExtra("select_size", getIntent().getStringExtra("select_size"));
                        intent.putExtra("actual_price", getIntent().getStringExtra("actual_price"));
                        intent.putExtra("discount_price", getIntent().getStringExtra("discount_price"));
                        intent.putExtra("imagename", getIntent().getStringExtra("imagename"));
                        intent.putExtra("product_qty", getIntent().getStringExtra("product_qty"));
                        intent.putExtra("product_id", getIntent().getStringExtra("product_id"));
                        intent.putExtra("select_color", getIntent().getStringExtra("select_color"));
                        intent.putExtra("product_type", getIntent().getStringExtra("product_type"));
                        intent.putExtra("gst", getIntent().getStringExtra("gst"));
                        intent.putExtra("new_user_request", getIntent().getStringExtra("new_user_request"));
                        intent.putExtra("item_type", getIntent().getStringExtra("item_type"));
                        intent.putExtra("type", "friendDeal_one_rs");
                        startActivity(intent);
                        break;
                    case "buy_now":
                        intent = new Intent(EditAddressActivity.this, AddressActivity.class);
                        intent.putExtra("product_name", getIntent().getStringExtra("product_name"));
                        intent.putExtra("select_size", getIntent().getStringExtra("select_size"));
                        intent.putExtra("actual_price", getIntent().getStringExtra("actual_price"));
                        intent.putExtra("discount_price", getIntent().getStringExtra("discount_price"));
                        intent.putExtra("imagename", getIntent().getStringExtra("imagename"));
                        intent.putExtra("product_qty", getIntent().getStringExtra("product_qty"));
                        intent.putExtra("product_id", getIntent().getStringExtra("product_id"));
                        intent.putExtra("select_color", getIntent().getStringExtra("select_color"));
                        intent.putExtra("product_type", getIntent().getStringExtra("product_type"));
                        intent.putExtra("gst", getIntent().getStringExtra("gst"));
                        intent.putExtra("type", "buy_now");
                        startActivity(intent);
                        break;

                }

            }
        });


        addressInterface = new AddressInterface() {
            @Override
            public void address_select(final int pos) {

                position = pos;
                posi = String.valueOf(pos);

            }
        };

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.deliver_btn:
                if (posi == null) {
                    Toast.makeText(EditAddressActivity.this, "Address not select", Toast.LENGTH_SHORT).show();
                } else {

                    Share_session shareSession = new Share_session(this);
                    shareSession.address(addressDataLists.get(position).getName(), addressDataLists.get(position).getDelivery_number(), addressDataLists.get(position).getPincode(), addressDataLists.get(position).getHouse_no(), addressDataLists.get(position).getColony(), addressDataLists.get(position).getLandmark(), addressDataLists.get(position).getState(), addressDataLists.get(position).getCity(),addressDataLists.get(position).getSno());

                    if (getIntent().getStringExtra("context") != null) {
                        startActivity(new Intent(EditAddressActivity.this, CardActivity.class));
                    } else {

//                        if (getIntent().getStringExtra("product_qty") != null) {
//                            Intent intent = new Intent(EditAddressActivity.this, SaveAddressActivity.class);
//                            intent.putExtra("product_name", getIntent().getStringExtra("product_name"));
//                            intent.putExtra("select_size", getIntent().getStringExtra("select_size"));
//                            intent.putExtra("actual_price", getIntent().getStringExtra("actual_price"));
//                            intent.putExtra("discount_price", getIntent().getStringExtra("discount_price"));
//                            intent.putExtra("imagename", getIntent().getStringExtra("imagename"));
//                            intent.putExtra("product_qty", getIntent().getStringExtra("product_qty"));
//                            intent.putExtra("product_id", getIntent().getStringExtra("product_id"));
//                            intent.putExtra("select_color", getIntent().getStringExtra("select_color"));
//                            intent.putExtra("product_type", getIntent().getStringExtra("product_type"));
//                            intent.putExtra("gst", getIntent().getStringExtra("gst"));
//                            intent.putExtra("type", "buy_now");
//                            startActivity(intent);
//
//                        }else if (getIntent().getStringExtra("type").equals("friendDeal_one_rs")){
//                            Intent intent = new Intent();
//                            intent = new Intent(EditAddressActivity.this, AddressActivity.class);
//                            intent.putExtra("product_name", getIntent().getStringExtra("product_name"));
//                            intent.putExtra("select_size", getIntent().getStringExtra("select_size"));
//                            intent.putExtra("actual_price", getIntent().getStringExtra("actual_price"));
//                            intent.putExtra("discount_price", getIntent().getStringExtra("discount_price"));
//                            intent.putExtra("imagename", getIntent().getStringExtra("imagename"));
//                            intent.putExtra("product_qty", getIntent().getStringExtra("product_qty"));
//                            intent.putExtra("product_id", getIntent().getStringExtra("product_id"));
//                            intent.putExtra("select_color", getIntent().getStringExtra("select_color"));
//                            intent.putExtra("product_type", getIntent().getStringExtra("product_type"));
//                            intent.putExtra("gst", getIntent().getStringExtra("gst"));
//                            intent.putExtra("new_user_request", getIntent().getStringExtra("new_user_request"));
//                            intent.putExtra("item_type", getIntent().getStringExtra("item_type"));
//                            intent.putExtra("type", "friendDeal_one_rs");
//                            startActivity(intent);


                        Intent intent;
                        switch (types) {
                            case "CardActivity":
                                intent = new Intent(EditAddressActivity.this, SaveAddressActivity.class);
                                intent.putExtra("type", "CardActivity");
                                startActivity(intent);
                                break;
                            case "friendDeal_one_rs":
                                intent = new Intent(EditAddressActivity.this, SaveAddressActivity.class);
                                intent.putExtra("product_name", getIntent().getStringExtra("product_name"));
                                intent.putExtra("select_size", getIntent().getStringExtra("select_size"));
                                intent.putExtra("actual_price", getIntent().getStringExtra("actual_price"));
                                intent.putExtra("discount_price", getIntent().getStringExtra("discount_price"));
                                intent.putExtra("imagename", getIntent().getStringExtra("imagename"));
                                intent.putExtra("product_qty", getIntent().getStringExtra("product_qty"));
                                intent.putExtra("product_id", getIntent().getStringExtra("product_id"));
                                intent.putExtra("select_color", getIntent().getStringExtra("select_color"));
                                intent.putExtra("product_type", getIntent().getStringExtra("product_type"));
                                intent.putExtra("gst", getIntent().getStringExtra("gst"));
                                intent.putExtra("new_user_request", getIntent().getStringExtra("new_user_request"));
                                intent.putExtra("item_type", getIntent().getStringExtra("item_type"));
                                intent.putExtra("type", "friendDeal_one_rs");
                                startActivity(intent);
                                break;
                            case "buy_now":
                                intent = new Intent(EditAddressActivity.this, SaveAddressActivity.class);
                                intent.putExtra("product_name", getIntent().getStringExtra("product_name"));
                                intent.putExtra("select_size", getIntent().getStringExtra("select_size"));
                                intent.putExtra("actual_price", getIntent().getStringExtra("actual_price"));
                                intent.putExtra("discount_price", getIntent().getStringExtra("discount_price"));
                                intent.putExtra("imagename", getIntent().getStringExtra("imagename"));
                                intent.putExtra("product_qty", getIntent().getStringExtra("product_qty"));
                                intent.putExtra("product_id", getIntent().getStringExtra("product_id"));
                                intent.putExtra("select_color", getIntent().getStringExtra("select_color"));
                                intent.putExtra("product_type", getIntent().getStringExtra("product_type"));
                                intent.putExtra("gst", getIntent().getStringExtra("gst"));
                                intent.putExtra("type", "buy_now");
                                startActivity(intent);
                                break;

                        }


//                        }else {
//                            Intent intent = new Intent(EditAddressActivity.this, SaveAddressActivity.class);
//                            intent.putExtra("type", "CardActivity");
//                            startActivity(intent);
//                        }

                    }


                }
                break;
        }
    }


    public void Connectivity(){
        InternetAvailabilityChecker mInternetAvailabilityChecker;
        mInternetAvailabilityChecker = InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker.addInternetConnectivityListener(EditAddressActivity.this);
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected){
        }
        else {
            startActivity(new Intent(EditAddressActivity.this,NoInternetConnectionActivity.class));
        }
    }


}












