package tech.iwish.pickmall.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.session.Share_session;

public class Profile extends AppCompatActivity {
    ImageView back,pickimage,profileImage,edit_name,edit_gender;
    TextViewFont name, mobile,r_no,gender;
    public static final int PICK_IMAGE = 1;
    Share_session share_session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        InitializeActivity();
        setActivityData();

        pickimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        edit_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Profile.this,EditProfileActivity.class);
                intent.putExtra("action","name");
                startActivity(intent);
                Animatoo.animateInAndOut(Profile.this);
            }
        });
        edit_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Profile.this,EditProfileActivity.class);
                intent.putExtra("action","gender");
                startActivity(intent);
                Animatoo.animateInAndOut(Profile.this);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri ImageUri = data.getData();
        File ImagePath = new File(ImageUri.getPath());
        String filename = String.valueOf(ImagePath).substring(String.valueOf(ImagePath).lastIndexOf("/") + 1);
        UploadProfileImage(share_session.getUserDetail().get("UserMobile"), String.valueOf(ImagePath),filename);
    }



    public void UploadProfileImage(String mobile, String path,String name)
    {
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
        OkHttpClient okHttpClient1 = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image",name , RequestBody.create(MEDIA_TYPE_PNG,path))
                .addFormDataPart("mobile", mobile)
                .build();



        Request request1 = new Request.Builder().url("http://173.212.226.143:8086/api/Signup").post(requestBody).build();
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
//                            res =true;

                        }
                    }

                }
            }
        });
//        return res;
    }


    public void setActivityData()
    {
        name.setText(share_session.getUserDetail().get("username"));
        mobile.setText(share_session.getUserDetail().get("UserMobile"));
        gender.setText(share_session.getUserDetail().get("gender"));
        r_no.setText(share_session.getUserDetail().get("id"));
        if(!(share_session.getUserDetail().get("image").equals("Set Now ")))
        {
            Glide.with(Profile.this).load("173.212.226.143:8086/Androidapi/"+share_session.getUserDetail().get("image")).into(profileImage);
        }
    }

    public void InitializeActivity()
    {
        back = findViewById(R.id.back);
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        gender = findViewById(R.id.gender);
        r_no = findViewById(R.id.rno);
        pickimage = findViewById(R.id.pickimage);
        profileImage = findViewById(R.id.image);
        edit_gender = findViewById(R.id.edit_gender);
        edit_name = findViewById(R.id.edit_name);
        share_session= new Share_session(Profile.this);
    }



}
