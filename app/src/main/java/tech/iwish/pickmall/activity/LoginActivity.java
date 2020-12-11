package tech.iwish.pickmall.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.tasks.Task;

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
import tech.iwish.pickmall.adapter.FlashSaleAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.countdowntime.CountdownTime;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.ProductList;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private Button createAccountButton;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;
    ImageView GOOGLE, facebook;
    LoginButton loginButton;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private FacebookCallback<LoginResult> callback;
    private static final String MOBILE = "mobile";
    private static final String NAME = "name";
    private AccessTokenTracker accessTokenTracker;
    ProfileTracker profileTracker;
    TextView signin;
    private EditText name, number, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        GOOGLE = findViewById(R.id.google);
        loginButton = findViewById(R.id.facebook);

        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signin);


        signin.setOnClickListener(view -> {
          startActivity(new Intent(LoginActivity.this,Sign_InActivity.class));
        });
        createAccountButton = (Button) findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(intent);
//                Animatoo.animateFade(LoginActivity.this);


                if (name.getText().toString().trim().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fill name", Toast.LENGTH_SHORT).show();
                } else if (number.getText().toString().trim().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fill number", Toast.LENGTH_SHORT).show();
                } else if (email.getText().toString().trim().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fill email", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().trim().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "fill password", Toast.LENGTH_SHORT).show();
                } else if (number.length() <= 6) {
                    Toast.makeText(LoginActivity.this, "fill password", Toast.LENGTH_SHORT).show();
                } else if (password.length() <= 6) {
                    Toast.makeText(LoginActivity.this, "fill password", Toast.LENGTH_SHORT).show();
                } else {
                    sigup();
                }

            }
        });

//        google api
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        GOOGLE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });

//        facebbok api

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                nextActivity(newProfile);
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();


        callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                nextActivity(profile);
                Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }
        };

        loginButton.setReadPermissions(EMAIL);
        loginButton.registerCallback(callbackManager, callback);

    }

    private void sigup() {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name.getText().toString().trim());
            jsonObject.put("number", number.getText().toString().trim());
            jsonObject.put("email", email.getText().toString().trim());
            jsonObject.put("password", password.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.REGISTER).post(body).build();
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
                            startActivity(new Intent(LoginActivity.this , OTPActivity.class).putExtra("number",number.getText().toString().trim()));
                        }else {
                            LoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "Number already register", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            }
        });

    }


    protected void getUserDetails(LoginResult loginResult) {
        GraphRequest data_request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), (json_object, response) -> {

                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();


    }


    @Override
    protected void onResume() {
        super.onResume();
        //Facebook login
        Profile profile = Profile.getCurrentProfile();
        nextActivity(profile);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        //Facebook login
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(result);
        }


        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void gotoProfile() {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(googleSignInResult -> handleSignInResult(googleSignInResult));
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.e("result", String.valueOf(result.isSuccess()));
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            Log.e("data", String.valueOf(account));
//            userName.setText(account.getDisplayName());
//            userEmail.setText(account.getEmail());
//            userId.setText(account.getId());
            try {
//                Glide.with(this).load(account.getPhotoUrl()).into(profileImage);
            } catch (NullPointerException e) {
                Toast.makeText(getApplicationContext(), "image not found", Toast.LENGTH_LONG).show();
            }

        } else {

        }
    }


    private void nextActivity(Profile profile) {
        if (profile != null) {
            Intent main = new Intent(LoginActivity.this, MainActivity.class);
            main.putExtra("name", profile.getFirstName());
            main.putExtra("surname", profile.getLastName());
            main.putExtra("imageUrl", profile.getProfilePictureUri(200, 200).toString());
//            startActivity(main);
        }
    }


}
