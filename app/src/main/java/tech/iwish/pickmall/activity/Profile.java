package tech.iwish.pickmall.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

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
import tech.iwish.pickmall.connection.ConnectionServer;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.session.Share_session;

public class Profile extends AppCompatActivity implements InternetConnectivityListener {
    ImageView back,pickimage,profileImage,edit_name,edit_gender;
    TextViewFont name, mobile,r_no,gender,type;
    public static final int PICK_IMAGE = 1;
    Share_session share_session;
    private String [] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        InitializeActivity();
        setActivityData();

        pickimage.setOnClickListener(view -> ImagePicker.Companion.with(Profile.this).compress(1024).maxResultSize(1080, 1080).start());


        back.setOnClickListener(view -> {
            Intent intent= new Intent(Profile.this,Account.class);
            startActivity(intent);
//            Animatoo.animateInAndOut(Profile.this);
        });

        edit_name.setOnClickListener(view -> {
            Intent intent= new Intent(Profile.this,EditProfileActivity.class);
            intent.putExtra("action","name");
            startActivity(intent);
//            Animatoo.animateInAndOut(Profile.this);
        });
        edit_gender.setOnClickListener(view -> {
            Intent intent= new Intent(Profile.this,EditProfileActivity.class);
            intent.putExtra("action","gender");
            startActivity(intent);
//            Animatoo.animateInAndOut(Profile.this);
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Uri ImageUri = data.getData();
            File ImagePath = new File(ImageUri.getPath());
            String filename = String.valueOf(ImagePath).substring(String.valueOf(ImagePath).lastIndexOf("/") + 1);
            UploadProfileImage(share_session.getUserDetail().get("UserMobile"),ImageUri ,filename);
        }
    }

    public void UploadProfileImage(String mobile, Uri path,String name) {
        File finalFile = new File(String.valueOf(path).substring(7));
        OkHttpClient okHttpClient1 = new OkHttpClient();
         final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", name, RequestBody.create(MEDIA_TYPE_PNG,finalFile))
                .addFormDataPart("mobile",mobile)
                .build();


        Request request1 = new Request.Builder().url(Constants.UPLOAD_PROFILE).post(requestBody).build();
        okHttpClient1.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Profile.this.runOnUiThread(() -> Toast.makeText(Profile.this, "Connection Timeout", Toast.LENGTH_SHORT).show());
                Log.e("error", String.valueOf(e));
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
                            Profile.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("image",jsonHelper.GetResult("data"));
                                    share_session.SetProfileImage(jsonHelper.GetResult("data"));
                                    restartActivity(Profile.this);

                                }
                            });
                        }
                    }

                }
            }
        });
    }



    public void setActivityData()
    {
        if (share_session.getUserDetail().get("UserMobile").contains("@")) {
            type.setText("Email");
            mobile.setText(share_session.getUserDetail().get("UserMobile"));
        }
        else {
            mobile.setText(share_session.getUserDetail().get("UserMobile"));
        }
        name.setText(share_session.getUserDetail().get("username"));
        mobile.setText(share_session.getUserDetail().get("UserMobile"));
        gender.setText(share_session.getUserDetail().get("gender"));
        r_no.setText(share_session.getUserDetail().get("id"));
        if(!(share_session.getUserDetail().get("image").equals("Set Now ")))
        {
            Log.e("imag",Constants.IMAGES+share_session.getUserDetail().get("image"));
            Glide.with(Profile.this).load(Constants.IMAGES+share_session.getUserDetail().get("image")).into(profileImage);
        }
    }

    public void InitializeActivity()
    {
        int requestCode = 200;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }

        back = findViewById(R.id.back);
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        gender = findViewById(R.id.gender);
        r_no = findViewById(R.id.rno);
        pickimage = findViewById(R.id.pickimage);
        profileImage = findViewById(R.id.image);
        edit_gender = findViewById(R.id.edit_gender);
        edit_name = findViewById(R.id.edit_name);
        type = findViewById(R.id.type);
        share_session= new Share_session(Profile.this);
        Connectivity();
    }

    private String getRealPathFromURI(Uri uri) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = Profile.this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }


    public static void restartActivity(Activity activity){
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate();
        } else {
            activity.finish();
            activity.startActivity(activity.getIntent());
        }
    }

    public void Connectivity(){
        InternetAvailabilityChecker mInternetAvailabilityChecker;
        mInternetAvailabilityChecker = InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker.addInternetConnectivityListener(Profile.this);
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected){
        }
        else {
            startActivity(new Intent(Profile.this,NoInternetConnectionActivity.class));
        }
    }

}
