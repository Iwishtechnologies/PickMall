package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.New_PostCommentAdapter;
import tech.iwish.pickmall.adapter.TimeLineAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.New_PostList;
import tech.iwish.pickmall.other.New_comment_show;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.activity.Profile.PICK_IMAGE;
import static tech.iwish.pickmall.session.Share_session.USERMOBILE;

public class NewPostActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout upload_img;
    ImageView imageSet, postBtn,againImageSelect;
    private static int RESULT_LOAD_IMAGE = 1;
    Uri imageUri;
    RelativeLayout imageSetRelativeLayout;
    EditText description;
    Share_session shareSession;
    Map data;
    RecyclerView timelineRecyclerview;
    List<New_PostList> new_postLists = new ArrayList<>();
    File imageValue;
    boolean Imagecheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        getSupportActionBar().setTitle("New Post");

        upload_img = findViewById(R.id.upload_img);
        imageSet = findViewById(R.id.imageSet);
        imageSetRelativeLayout = findViewById(R.id.imageSetRelativeLayout);
        postBtn = findViewById(R.id.postBtn);
        description = findViewById(R.id.description);
        timelineRecyclerview = findViewById(R.id.timelineRecyclerview);
        againImageSelect = findViewById(R.id.againImageSelect);

        shareSession = new Share_session(this);
        data = shareSession.Fetchdata();


        TimeLineShow();

        upload_img.setOnClickListener(this);
        postBtn.setOnClickListener(this);
        againImageSelect.setOnClickListener(this);
    }

    private void TimeLineShow() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        timelineRecyclerview.setLayoutManager(linearLayoutManager);

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_number", data.get(USERMOBILE).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.TIMELINE_SHOW).post(body).build();
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


                            if (NewPostActivity.this != null) {

                                NewPostActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                        no_comment.setVisibility(View.GONE);
                                    }
                                });

                            }

                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                new_postLists.add(new New_PostList(jsonHelper.GetResult("sno"), jsonHelper.GetResult("image"), jsonHelper.GetResult("user_number"), jsonHelper.GetResult("description"), jsonHelper.GetResult("date_time"), jsonHelper.GetResult("likes")));
                            }

                            if (NewPostActivity.this != null) {

                                NewPostActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        TimeLineAdapter timeLineAdapter = new TimeLineAdapter(new_postLists);
                                        timelineRecyclerview.setAdapter(timeLineAdapter);

                                    }
                                });

                            }


                        } else {

                            if (NewPostActivity.this != null) {

                                NewPostActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                        no_comment.setVisibility(View.VISIBLE);
                                    }
                                });

                            }
                        }

                    }
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.upload_img:
            case R.id.againImageSelect:
                SelectImage();
                break;
            case R.id.postBtn:
                postBtn.setClickable(false);
                if(Imagecheck){
                    sendData();
                }else {
                    Toast.makeText(this, "Select Image First", Toast.LENGTH_SHORT).show();
                }
                postBtn.setClickable(true);
                break;
        }
    }

    private void sendData() {
        OkHttpClient okHttpClient1 = new OkHttpClient();
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("images",imageValue.getName(), RequestBody.create(MediaType.parse("image/*"), imageValue))
                .addFormDataPart("description", description.getText().toString().trim())
                .addFormDataPart("user_number", data.get(USERMOBILE).toString())
                .build();


        Request request1 = new Request.Builder().url(Constants.SEND_NEW_POST).post(requestBody).build();
        okHttpClient1.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                NewPostActivity.this.runOnUiThread(() -> {
                    Toast.makeText(NewPostActivity.this, "Connection Timeout", Toast.LENGTH_SHORT).show();
                });
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

                            if(NewPostActivity.this != null){
                                NewPostActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        TimeLineShow();
                                        imageSet.setImageResource(0);
                                        description.setHint("Description...");
                                        description.setText("");
                                        upload_img.setClickable(true);
                                        imageSetRelativeLayout.setVisibility(View.GONE);


                                    }
                                });
                            }

                        } else {

                        }
                    }

                }
            }
        });
    }

    private void SelectImage() {

//        Intent i = new Intent(
//                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(i, RESULT_LOAD_IMAGE);


//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"),0);

//        Intent intent = new Intent();
//        intent.setAction(android.content.Intent.ACTION_VIEW);
//        intent.setType("image/*");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);

        ImagePicker.Companion.with(NewPostActivity.this).compress(1024).maxResultSize(1080, 1080).start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData() != null) {

            Uri uri = Uri.parse(data.getData().toString());
            imageValue =  new File(uri.getPath());
            upload_img.setClickable(false);
            upload_img.setVisibility(View.GONE);
            imageSetRelativeLayout.setVisibility(View.VISIBLE);
            imageUri = data.getData();
            imageSet.setImageURI(imageUri);
            Imagecheck = true;

        }




//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//
//            upload_img.setVisibility(View.GONE);
////            imageSet.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//            imageSet.setImageURI(Uri.parse(picturePath));
//            cursor.close();
//        }

//        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
//            imageValue = new File(String.valueOf(data.getData()).substring(7));
//            upload_img.setClickable(false);
//            imageSetRelativeLayout.setVisibility(View.VISIBLE);
//            imageUri = data.getData();
//            imageSet.setImageURI(imageUri);
//        }




    }


}


























