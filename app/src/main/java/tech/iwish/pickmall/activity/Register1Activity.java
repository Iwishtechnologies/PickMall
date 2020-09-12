package tech.iwish.pickmall.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
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

import java.io.IOException;
import java.util.Map;

import ir.hamiss.internetcheckconnection.InternetAvailabilityChecker;
import ir.hamiss.internetcheckconnection.InternetConnectivityListener;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.session.Share_session;

public class Register1Activity extends AppCompatActivity  implements InternetConnectivityListener,GoogleApiClient.OnConnectionFailedListener {

    private Button registerButton ,sign_in;
    private boolean doubleBackToExitPressedOnce = false;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient googleApiClient;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    ProfileTracker profileTracker;
    Share_session share_session;
    ImageView google;
    LoginButton facebook;
    TextViewFont skip;
    private FacebookCallback<LoginResult> callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        InitializeActivity();
        ActivityAction();
        Connectivity();
        GoogleSignIn();


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
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                                    if(object.getString("email").isEmpty()){
    //                                    error.setText("access Denied use different signin method");
                                        Toast.makeText(Register1Activity.this, "Access Denied use different Signin Method", Toast.LENGTH_SHORT).show();
                                    }else {
    //                                    String first_name = object.getString("first_name");
    //                                    String last_name = object.getString("last_name");
    //                                    String email = object.getString("email");
    //                                    String id = object.getString("id");
                                        share_session.CreateSession(object.getString("email"));
                                        LoginManager.getInstance().logOut();
                                        GoogleApi(object.getString("email"),object.getString("first_name")+" "+object.getString("last_name"));
    //                                    LoginManager.getInstance().logOut();
                                    }
                                }

                            } catch (JSONException e) {
                                Toast.makeText(Register1Activity.this, "Access Denied use different Signin Method", Toast.LENGTH_SHORT).show();
//                                error.setText("access Denied use different signin method");
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
                Toast.makeText(Register1Activity.this, "Access Denied use different Signin Method", Toast.LENGTH_SHORT).show();
//                error.setText("access Denied use different signin method");
            }
        });

    }


    private  void InitializeActivity(){
        registerButton = (Button)findViewById(R.id.registerButton);
        sign_in = (Button)findViewById(R.id.sign_in);
        google = findViewById(R.id.google);
        skip = findViewById(R.id.skip);
        facebook = findViewById(R.id.facebook);
        share_session = new Share_session(Register1Activity.this);
    }

    private void ActivityAction(){
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(Register1Activity.this , LoginActivity.class);
            startActivity(intent);
        });
        sign_in.setOnClickListener(view -> {
            Intent intent = new Intent(Register1Activity.this , Sign_InActivity.class);
            startActivity(intent);
        });
        facebook.setOnClickListener(view -> { FacebookApi();});
        skip.setOnClickListener(view -> {
            share_session.Firsttime();
            startActivity(new Intent(Register1Activity.this,MainActivity.class));
        });
    }


    public void GoogleSignIn(){
        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) Register1Activity.this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mainview.setAlpha((float) 0.5);
//                progressBar.setVisibility(View.VISIBLE);
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,RC_SIGN_IN);
            }
        });
    }

    public void Connectivity(){
        InternetAvailabilityChecker mInternetAvailabilityChecker;
        mInternetAvailabilityChecker = InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker.addInternetConnectivityListener((InternetConnectivityListener) Register1Activity.this);
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected){
        }
        else {
            startActivity(new Intent(Register1Activity.this,NoInternetConnectionActivity.class));
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(result);
        }
        else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }



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
                new Handler(getMainLooper()).post(() -> Toast.makeText(Register1Activity.this, "Connection Timeout", Toast.LENGTH_SHORT).show());
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
                            new Handler(getMainLooper()).post(() -> {
                                share_session.CreateSession(mobile);
                                share_session.user_number_check();
                                GetAddress(mobile);
                            });
                        }
                        else {
                            Register1Activity.this.runOnUiThread(() -> {
                                share_session.CreateSession(mobile);
                                share_session.user_number_check();
                                GetAddress(mobile);
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
                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(Register1Activity.this, "Connection Time Out", Toast.LENGTH_SHORT).show());
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
                Register1Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        progressBar.setVisibility(View.GONE);
//                        mainview.setAlpha(1);
                        Toast.makeText(Register1Activity.this, "Connection Time Out", Toast.LENGTH_SHORT).show();
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

                            Register1Activity.this.runOnUiThread(() -> {
                                Intent intent= new Intent(Register1Activity.this,MainActivity.class);
                                startActivity(intent);
                            });

                        }
                    }

                }


            }

        });
    }
}
