package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.ShippingAddressAdapter;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.ShippingAddressList;

public class VendorRequestActivity extends AppCompatActivity {
    EditText name,mobile,address,altermobile,pincode,gstin,type;
    Button apply;
    ImageView back;
    ProgressBar progressBar;
    LinearLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_request);
        InitializeActivity();
        ActivityAction();
    }


    public void InitializeActivity(){

        name= findViewById(R.id.name);
        mobile= findViewById(R.id.mobile);
        address= findViewById(R.id.address);
        altermobile= findViewById(R.id.alter_mobile);
        pincode= findViewById(R.id.pincode);
        gstin= findViewById(R.id.gstin);
        type= findViewById(R.id.type);
        apply= findViewById(R.id.apply);
        back= findViewById(R.id.back);
        progressBar= findViewById(R.id.progress);
        main= findViewById(R.id.main);


    }

    public void ActivityAction(){
       apply.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(ValidatteInputs(name.getText().toString(),mobile.getText().toString(),address.getText().toString(),altermobile.getText().toString(),pincode.getText().toString(),gstin.getText().toString(),type.getText().toString())){
                   main.setAlpha((float) 0.5);
                   progressBar.setVisibility(View.VISIBLE);
                   Make_Request(name.getText().toString(),mobile.getText().toString(),address.getText().toString(),altermobile.getText().toString(),pincode.getText().toString(),gstin.getText().toString(),type.getText().toString());
                   Toast.makeText(VendorRequestActivity.this, "Thanks for Interest We Will Contact Soon", Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(VendorRequestActivity.this,Account.class));
                   Animatoo.animateFade(VendorRequestActivity.this);
               }

           }
       });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private  Boolean  ValidatteInputs(String name1,String mobile1,String address1,String altermobile1,String pincode1,String gstin1,String type1){
        Boolean result=true;
        if(name1.isEmpty()){
            name.setError("Is Empty");
            result=false;
            return result;
        }
        if(mobile1.isEmpty()){
            mobile.setError("Is Empty");
            result=false;
            return result;
        }else {
            if(mobile1.length()<10){
                mobile.setError("Invalid number");
                result=false;
                return result;
            }
        }
        if(altermobile1.isEmpty()){
            altermobile.setError("Is Empty");
            result=false;
            return result;
        }else {
            if(altermobile1.length()<10){
                altermobile.setError("Invalid number");
                result=false;
                return result;
            }
        }
        if(address1.isEmpty()){
            address.setError("Is Empty");
            result=false;
            return result;
        }
        if(pincode1.isEmpty()){
            pincode.setError("Is Empty");
            result=false;
            return result;
        }else {
            if(pincode1.length()<6){
                pincode.setError("Invalid Pincode");
                result=false;
                return result;
            }
        }
        if(gstin1.isEmpty()){
            gstin.setError("Is Empty");
            result=false;
            return result;
        }
        if(type1.isEmpty()){
            type.setError("Is Empty");
            result=false;
            return result;
        }

        return result;
    }

    private void Make_Request(String name1,String mobile1,String address1,String altermobile1,String pincode1,String gstin1,String type1){
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",name1);
            jsonObject.put("mobile",mobile1);
            jsonObject.put("address",address1);
            jsonObject.put("altermobile",altermobile1);
            jsonObject.put("pincode",pincode1);
            jsonObject.put("gstin",gstin1);
            jsonObject.put("type",type1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url("http://173.212.226.143:8086/api/Vendor_Request")
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
                            //                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                jsonHelper.setChildjsonObj(jsonArray, i);
//                                 }

                            VendorRequestActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    main.setAlpha(0);
                                }
                            });


                        }
                    }

                }
            }
        });

    }
}

