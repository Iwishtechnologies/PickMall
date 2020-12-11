package tech.iwish.pickmall.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoginStatusCallback;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.TimerTask;

import ir.hamiss.internetcheckconnection.InternetAvailabilityChecker;
import ir.hamiss.internetcheckconnection.InternetConnectivityListener;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.session.Share_session;

import static android.provider.ContactsContract.Intents.Insert.EMAIL;

public class MobileNOActivity extends AppCompatActivity  implements InternetConnectivityListener,GoogleApiClient.OnConnectionFailedListener {
    EditText mobile;
    Button next;
    ProgressBar progressBar;
    LinearLayout mainview;
    ImageView google;
    TextViewFont error;
    LoginButton facebook;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient googleApiClient;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    ProfileTracker profileTracker;
    Share_session share_session;
    private FacebookCallback<LoginResult> callback;
    private LinearLayout skip_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_n_o);
        share_session = new Share_session(MobileNOActivity.this);
        mobile = findViewById(R.id.mobile);
        next = findViewById(R.id.next);
        progressBar = findViewById(R.id.progress);
        mainview = findViewById(R.id.mainview);
        google = findViewById(R.id.google);
        facebook = findViewById(R.id.facebook);
        error = findViewById(R.id.error);
        skip_login = findViewById(R.id.skip_login);
        Connectivity();
        GoogleSignIn();
        FacebookApi();


        next.setOnClickListener(view -> {
             error.setText(null);
            next.setEnabled(false);
            if (mobile.getText().toString().isEmpty()) {
                Toast.makeText(MobileNOActivity.this, "Field not empty", Toast.LENGTH_SHORT).show();
            } else if (mobile.length() < 10) {
                Toast.makeText(MobileNOActivity.this, "", Toast.LENGTH_SHORT).show();
            } else {
                mobileinsert();
            }


        });

        skip_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MobileNOActivity.this , MainActivity.class));
            }
        });

//facebook
      facebook.setOnClickListener(view -> { FacebookApi();});
    }


    public void FacebookApi(){

        callbackManager = CallbackManager.Factory.create();
        facebook.setReadPermissions("email");

        facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        (object, response) -> {
                            try {
                                if(object.getString("email").isEmpty()){
                                    error.setText("access Denied use different signin method");
                                }else {
//                                    String first_name = object.getString("first_name");
//                                    String last_name = object.getString("last_name");
//                                    String email = object.getString("email");
//                                    String id = object.getString("id");
                                    share_session.CreateSession(object.getString("email"));
                                    GoogleApi(object.getString("email"),object.getString("first_name")+" "+object.getString("last_name"));
                                }

                            } catch (JSONException e) {
                                error.setText("access Denied use different signin method");
                                e.printStackTrace();
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "first_name,last_name,email,id");
                request.setParameters(parameters);
                request.executeAsync();
            }
            @Override
            public void onCancel() {
                Log.e("fghbjnmk,.", "String.valueOf(exception)");
            }
            @Override
            public void onError(FacebookException exception) {
                Log.e("fghbjnmk,.", String.valueOf(exception));
                error.setText("access Denied use different signin method");
            }
        });

    }


    private void mobileinsert() {
        mainview.setAlpha((float) 0.5);
        progressBar.setVisibility(View.VISIBLE);
        next.setEnabled(true);
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("number", mobile.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.MOBILE_NUMBER).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                mainview.setAlpha(1);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MobileNOActivity.this, "Connection Timeout", Toast.LENGTH_SHORT).show();
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
                            MobileNOActivity.this.runOnUiThread(new TimerTask() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(MobileNOActivity.this, OTPActivity.class);
                                    intent.putExtra("number",mobile.getText().toString());
                                    mainview.setAlpha(1);
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(intent);
                                }
                            });

                        }
                    }
                }
            }
        });

    }

    public void Connectivity(){
        InternetAvailabilityChecker mInternetAvailabilityChecker;
        mInternetAvailabilityChecker = InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker.addInternetConnectivityListener(MobileNOActivity.this);
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected){
        }
        else {
            startActivity(new Intent(MobileNOActivity.this,NoInternetConnectionActivity.class));
        }
    }

    public void GoogleSignIn(){
        error.setText(null);
        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) MobileNOActivity.this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainview.setAlpha((float) 0.5);
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,RC_SIGN_IN);
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();


    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(result);
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);


    }

    private void handleSignInResult(GoogleSignInResult result){
        Log.e("result", String.valueOf(result.isSuccess()));
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            Log.e("data", String.valueOf(account));
            GoogleApi(account.getEmail(),account.getDisplayName());
//            userName.setText(account.getDisplayName());
//            userEmail.setText(account.getEmail());
//            userId.setText(account.getId());
            try{
//                Glide.with(this).load(account.getPhotoUrl()).into(profileImage);
            }catch (NullPointerException e){
                Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
            }

        }else{

        }
    }

    private void SaveProfile() {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", mobile.getText().toString().trim());
            jsonObject.put("name", mobile.getText().toString().trim());
            jsonObject.put("", mobile.getText().toString().trim());
            jsonObject.put("number", mobile.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.MOBILE_NUMBER).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                mainview.setAlpha(1);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MobileNOActivity.this, "Connection Timeout", Toast.LENGTH_SHORT).show();
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
                            MobileNOActivity.this.runOnUiThread(new TimerTask() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(MobileNOActivity.this, OTPActivity.class);
                                    intent.putExtra("number",mobile.getText().toString());
                                    mainview.setAlpha(1);
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(intent);
                                }
                            });

                        }
                    }
                }
            }
        });

    }



    public void GoogleApi(final String mobile, String name)
    {
//        File finalFile = new File(getRealPathFromURI(path));
        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", mobile);
            jsonObject.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.GOOGLEAPI).post(body).build();
        okHttpClient1.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                MobileNOActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        mainview.setAlpha(1);
                        Toast.makeText(MobileNOActivity.this, "Connection Timeout", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {

                    String result = response.body().string();
                    Log.e("response", result);
                    final JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("NEW")) {
                            MobileNOActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    Log.e("image",jsonHelper.GetResult("data"));
//                                    Log.e("image",jsonHelper.GetResult("data"));
//                                    share_session.SetProfileImage(jsonHelper.GetResult("data"));
                                    share_session.CreateSession(mobile);
                                    share_session.user_number_check();
                                    GetAddress(mobile);


                                }
                            });
                        }
                        else {
                            MobileNOActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    Log.e("image",jsonHelper.GetResult("data"));
//                                    Log.e("image",jsonHelper.GetResult("data"));
//                                    share_session.SetProfileImage(jsonHelper.GetResult("data"));
                                    share_session.CreateSession(mobile);
                                    share_session.user_number_check();
                                    GetAddress(mobile);


                                }
                            });
                        }
                    }

                }
            }
        });
    }


    public void GetAddress(String mobile)
    {
        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.GETADDRESS).post(body).build();
        okHttpClient1.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                MobileNOActivity.this.runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    mainview.setAlpha(1);
                    Toast.makeText(MobileNOActivity.this, "Connection Time Out", Toast.LENGTH_SHORT).show();
                });
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
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < 1; i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                share_session.address(jsonHelper.GetResult("name"),jsonHelper.GetResult("mobile"),jsonHelper.GetResult("pincode"),jsonHelper.GetResult("house_no"),jsonHelper.GetResult("colony"),jsonHelper.GetResult("landmark"),jsonHelper.GetResult("state"),jsonHelper.GetResult("city"),jsonHelper.GetResult("sno"));

                            }
                            GetUserProfile(share_session.getUserDetail().get("UserMobile"));


                        }
                        else {
                            GetUserProfile(share_session.getUserDetail().get("UserMobile"));
                        }
                    }

                }


            }

        });
    }


    public void GetUserProfile(String mobile)
    {


        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.PROFILE).post(body).build();
        okHttpClient1.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                MobileNOActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        mainview.setAlpha(1);
                        Toast.makeText(MobileNOActivity.this, "Connection Time Out", Toast.LENGTH_SHORT).show();
                    }
                });
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
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                share_session.setUserDetail(jsonHelper.GetResult("name"),jsonHelper.GetResult("gender"),jsonHelper.GetResult("image"),jsonHelper.GetResult("client_id"));

                            }

                            MobileNOActivity.this.runOnUiThread(() -> {
                                progressBar.setVisibility(View.GONE);
                                mainview.setAlpha(1);
                                Intent intent= new Intent(MobileNOActivity.this,MainActivity.class);
                                startActivity(intent);
//                                    Animatoo.animateFade(UserDetail.this);
                            });

                        }
                    }

                }


            }

        });
    }




}