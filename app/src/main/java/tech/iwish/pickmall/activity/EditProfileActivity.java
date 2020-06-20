package tech.iwish.pickmall.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

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
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.session.Share_session;

public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener , InternetConnectivityListener {
    EditText data;
    Button   save;
    Intent intent;
    Share_session  share_session;
    String gender;
    Spinner select_gender;
    List<String> option = new ArrayList<String>();
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        intent= getIntent();
        InitializeActivity();




       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onBackPressed();
           }
       });
       save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(ValidateInput(data.getText().toString()))
               {
                   if(intent.getStringExtra("action").equals("name"))
                   {
                       SaveProfileData(share_session.getUserDetail().get("UserMobile"),intent.getStringExtra("action"),data.getText().toString());
                       share_session.UpdateUserDetail(data.getText().toString(),intent.getStringExtra("action"));
                       startActivity(new Intent(EditProfileActivity.this,Profile.class) );
//                       Animatoo.animateDiagonal(EditProfileActivity.this);
                   }
               }
               if(intent.getStringExtra("action").equals("gender"))
               {
                   SaveProfileData(share_session.getUserDetail().get("UserMobile"),intent.getStringExtra("action"),gender);
                   share_session.UpdateUserDetail(gender,intent.getStringExtra("action"));
                   startActivity(new Intent(EditProfileActivity.this,Profile.class) );
                   Animatoo.animateDiagonal(EditProfileActivity.this);
               }
           }
       });
    }


    public void InitializeActivity() {
        data = findViewById(R.id.data);
        save = findViewById(R.id.save);
        back = findViewById(R.id.back);
        Connectivity();
        select_gender = findViewById(R.id.gender);
        select_gender.setOnItemSelectedListener(EditProfileActivity.this);
        share_session= new Share_session(EditProfileActivity.this);

        if(intent.getStringExtra("action").equals("name"))
        {
            select_gender.setVisibility(View.GONE);
        }
        if(intent.getStringExtra("action").equals("gender"))
        {
            data.setVisibility(View.GONE);
        }

        // Spinner Dialog elements
        option.add("Male");
        option.add("Female");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, option);

        // Dialog layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        select_gender.setAdapter(dataAdapter);


    }

    public boolean ValidateInput(String data) {
        if (!data.isEmpty())
        {
            return true;
        }
        else {
            return false;
        }

    }


    public void SaveProfileData(String mobile ,String parameter, String data) {


        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", mobile);
            jsonObject.put("data", data);
            jsonObject.put("parameter", parameter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url("http://173.212.226.143:8086/api/EditProfile").post(body).build();
        okHttpClient1.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {

                    String result = response.body().string();
                    Log.e("response", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {

                        }
                    }

                }
            }
        });
    }

    //Performing action onItemSelected and onNothing selected

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
//        Toast.makeText(getApplicationContext(), option.get(position), Toast.LENGTH_LONG).show();
        gender= option.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void Connectivity(){
        InternetAvailabilityChecker mInternetAvailabilityChecker;
        mInternetAvailabilityChecker = InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker.addInternetConnectivityListener(EditProfileActivity.this);
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected){
        }
        else {
            startActivity(new Intent(EditProfileActivity.this,NoInternetConnectionActivity.class));
        }
    }



}
