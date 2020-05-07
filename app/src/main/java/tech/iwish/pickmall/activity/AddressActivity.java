package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.FriendSaleAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.FriendSaleList;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.USERMOBILE;

public class AddressActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {


    private Button address_confirm;
    private TextInputLayout address_name, address_number, address_pincode, address_house_no, address_colony, address_landmark, address_text_city, address_text_state;
    private EditText name_add, number_add, pincode_add, house_add, colony_add, landmark_add;
    private TextView address_city, address_state;
    private Share_session shareSession;
    private Map data;
    private boolean checkinsertdata = false;
    private boolean pincode_check = false;

    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        setTitle("Address");


        address_confirm = (Button) findViewById(R.id.address_confirm);

        address_name = (TextInputLayout) findViewById(R.id.address_name);
        address_number = (TextInputLayout) findViewById(R.id.address_number);
        address_pincode = (TextInputLayout) findViewById(R.id.address_pincode);
        address_house_no = (TextInputLayout) findViewById(R.id.address_house_no);
        address_colony = (TextInputLayout) findViewById(R.id.address_colony);
        address_landmark = (TextInputLayout) findViewById(R.id.address_landmark);

        name_add = (EditText) findViewById(R.id.name_add);
        number_add = (EditText) findViewById(R.id.number_add);
        pincode_add = (EditText) findViewById(R.id.pincode_add);
        house_add = (EditText) findViewById(R.id.house_add);
        colony_add = (EditText) findViewById(R.id.colony_add);
        landmark_add = (EditText) findViewById(R.id.landmark_add);

        address_city = (TextView) findViewById(R.id.address_city);
        address_state = (TextView) findViewById(R.id.address_state);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        progressbar.setVisibility(View.GONE);

        address_confirm.setOnClickListener(this);

        name_add.setOnFocusChangeListener(this);
        number_add.setOnFocusChangeListener(this);
        pincode_add.setOnFocusChangeListener(this);
        house_add.setOnFocusChangeListener(this);
        colony_add.setOnFocusChangeListener(this);
        landmark_add.setOnFocusChangeListener(this);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.address_confirm:
                confir_address();
                break;
        }
    }

    private void confir_address() {
        if (validation()) {
            savedata();
        } else {
            Toast.makeText(this, "Address not save", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean savedata() {

        progressbar.setVisibility(View.VISIBLE);
        address_confirm.setClickable(false);

        shareSession = new Share_session(this);
        data = shareSession.Fetchdata();
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_name", address_name.getEditText().getText().toString().trim());
            jsonObject.put("user_number", address_number.getEditText().getText().toString().trim());
            jsonObject.put("user_pincode", address_pincode.getEditText().getText().toString().trim());
            jsonObject.put("user_house_no", address_house_no.getEditText().getText().toString().trim());
            jsonObject.put("user_colony", address_colony.getEditText().getText().toString().trim());
            jsonObject.put("user_landmark", address_landmark.getEditText().getText().toString().trim());
            jsonObject.put("city", address_city.getText().toString());
            jsonObject.put("state", address_state.getText().toString());
            jsonObject.put("user_number", data.get(USERMOBILE).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.USER_ADDRESS).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.e("response", result);
                    final JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {

                            if (AddressActivity.this != null) {


                                AddressActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        address_confirm.setClickable(true);
                                        progressbar.setVisibility(View.GONE);

                                        shareSession = new Share_session(AddressActivity.this);
                                        data = shareSession.Fetchdata();
                                        shareSession.address(address_name.getEditText().getText().toString().trim(), address_number.getEditText().getText().toString().trim(), address_pincode.getEditText().getText().toString().trim(), address_house_no.getEditText().getText().toString().trim(), address_colony.getEditText().getText().toString().trim(), address_landmark.getEditText().getText().toString().trim(), "", "");

                                        String type = getIntent().getStringExtra("type");
                                        Intent intent;
                                        switch (type) {
                                            case "CardActivity":
                                                intent = new Intent(AddressActivity.this, SaveAddressActivity.class);
                                                intent.putExtra("type", "CardActivity");
                                                startActivity(intent);
                                                break;
                                            case "friendDeal_one_rs":
                                                Toast.makeText(AddressActivity.this, "friendDeal_one_rs", Toast.LENGTH_SHORT).show();
                                                break;
                                            case "buy_now":
                                                intent = new Intent(AddressActivity.this, SaveAddressActivity.class);
                                                intent.putExtra("product_name", getIntent().getStringExtra("product_name"));
                                                intent.putExtra("select_size", getIntent().getStringExtra("select_size"));
                                                intent.putExtra("actual_price", getIntent().getStringExtra("actual_price"));
                                                intent.putExtra("discount_price", getIntent().getStringExtra("discount_price"));
                                                intent.putExtra("imagename", getIntent().getStringExtra("imagename"));
                                                intent.putExtra("product_qty", getIntent().getStringExtra("product_qty"));
                                                intent.putExtra("type", "buy_now");
                                                startActivity(intent);
                                                break;

                                        }


/*
                                            if (getIntent().getStringExtra("type") != null) {
                                            Intent intent = new Intent(AddressActivity.this, SaveAddressActivity.class);
                                            intent.putExtra("product_name", getIntent().getStringExtra("product_name"));
                                            intent.putExtra("select_size", getIntent().getStringExtra("select_size"));
                                            intent.putExtra("actual_price", getIntent().getStringExtra("actual_price"));
                                            intent.putExtra("discount_price", getIntent().getStringExtra("discount_price"));
                                            intent.putExtra("imagename", getIntent().getStringExtra("imagename"));
                                            intent.putExtra("product_qty", getIntent().getStringExtra("product_qty"));
                                            intent.putExtra("type", "buy_now");
                                            startActivity(intent);
                                        } else {
                                            Intent intent = new Intent(AddressActivity.this, SaveAddressActivity.class);
                                            intent.putExtra("type", "CardActivity");
                                            startActivity(intent);
                                        }
*/

                                    }
                                });

                            }

                        }
                    }
                }
            }
        });

        return checkinsertdata;
    }

    private boolean validation() {

        if (address_name.getEditText().getText().toString().isEmpty()) {
            address_name.setError("fill ");
            return false;
        }
        if (address_number.getEditText().getText().toString().isEmpty()) {
            address_number.setError("fill ");
            return false;
        }
        if (address_number.getEditText().length() < 10) {
            address_number.setError("number eer");
            return false;
        }
        if (address_pincode.getEditText().getText().toString().isEmpty()) {
            address_pincode.setError("fill ");
            return false;
        }
        if (address_pincode.getEditText().length() < 6) {
            address_pincode.setError("pincode not");
            return false;
        }

        if (address_house_no.getEditText().getText().toString().isEmpty()) {
            address_house_no.setError("fill ");
            return false;
        }
        if (address_colony.getEditText().getText().toString().isEmpty()) {
            address_colony.setError("fill ");
            return false;
        }
        if (!pincode_city_fetch()) {
            Toast.makeText(this, "Select city & state", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (address_landmark.getEditText().getText().toString().isEmpty()) {
            return true;
        }
        address_name.setError(null);
        address_number.setError(null);
        address_pincode.setError(null);
        address_house_no.setError(null);
        address_colony.setError(null);
        address_landmark.setError(null);

        return true;
    }


    @Override
    public void onFocusChange(View view, boolean b) {

        int id = view.getId();
        switch (id) {
            case R.id.name_add:
                if (!b) {
                    if (address_name.getEditText().getText().toString().trim().isEmpty()) {
                        address_name.setError("Field can't be empty ");
                    } else {
                        address_name.setError(null);
                    }
                }
                break;
            case R.id.number_add:
                if (!b) {
                    if (address_number.getEditText().getText().toString().isEmpty()) {
                        address_number.setError("fill ");
                    } else if (address_number.getEditText().length() < 10) {
                        address_number.setError("number eer");
                    } else {
                        address_number.setError(null);
                    }
                }
                break;
            case R.id.house_add:
                if (!b) {
                    if (address_house_no.getEditText().getText().toString().isEmpty()) {
                        address_house_no.setError("fill ");
                    } else {
                        address_house_no.setError(null);
                    }
                }
                break;
            case R.id.colony_add:
                if (!b) {
                    if (address_colony.getEditText().getText().toString().isEmpty()) {
                        address_colony.setError("fill ");
                    }
                }
                break;
            case R.id.landmark_add:
                if (!b) {

                }
                break;
            case R.id.pincode_add:
                if (!b) {
                    if (address_pincode.getEditText().getText().toString().isEmpty()) {
                        address_pincode.setError("fill ");
                    }
                    if (address_pincode.getEditText().length() < 6) {
                        address_pincode.setError("pincode not");
                    }
                    pincode_city_fetch();
                }
                break;
        }
    }

    private boolean pincode_city_fetch() {


        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("pincode", address_pincode.getEditText().getText().toString().trim());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.USER_PINCODE).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.e("response", result);
                    final JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                AddressActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        address_city.setText(jsonHelper.GetResult("city"));
                                        address_state.setText(jsonHelper.GetResult("state"));
                                        pincode_check = true;
                                    }
                                });
                            }
                        } else {
                            AddressActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pincode_check = false;
                                    Toast.makeText(AddressActivity.this, "This Pincode not Delivery", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                }
            }
        });
        return pincode_check;
    }


}





















