package tech.iwish.pickmall.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
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

public class ReturnOrderActivity extends AppCompatActivity {
    ShapedImageView productImage;
    TextViewFont name ,returncharges,orderAmount;
    EditText reason;
    ImageView upload1,upload2,upload3;
    Button submit,Image1,Image2,Image3;
    private Uri mLastPhoto;
    private int REQUEST_TAKE_PICTURE=1;
    int UploadCode=0,opencode=0;
    File path1,path2,path3;
    Boolean img1=false,img2=false,img3=false;
    LinearLayout imageview ,mainview;
    ProgressBar progressBar;
    Share_session share_session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_order);
        InitializeActivity();
        SetActivityData();
        ActivityAction();
    }

    private void InitializeActivity(){
        share_session= new Share_session(ReturnOrderActivity.this);
        productImage=findViewById(R.id.productImage);
        name= findViewById(R.id.productName);
        returncharges=findViewById(R.id.charges);
        orderAmount=findViewById(R.id.OrderAmount);
        reason=findViewById(R.id.reason);
        submit=findViewById(R.id.submit);
        Image1=findViewById(R.id.Image1);
        Image2=findViewById(R.id.Image2);
        Image3=findViewById(R.id.Image3);
        upload1=findViewById(R.id.upload1);
        upload2=findViewById(R.id.upload2);
        upload3=findViewById(R.id.upload3);
        imageview=findViewById(R.id.Imagesview);
        mainview=findViewById(R.id.mainview);
        progressBar=findViewById(R.id.progress);
        Log.e("dsfsfdsag",getIntent().getExtras().getString("orderId"));
    }
    private void ActivityAction(){
     Image1.setOnClickListener(view -> { UploadCode=1;ImagePicker.Companion.with(ReturnOrderActivity.this).compress(1024).maxResultSize(1080, 1080).start(); });
     Image2.setOnClickListener(view -> { UploadCode=2;ImagePicker.Companion.with(ReturnOrderActivity.this).compress(1024).maxResultSize(1080, 1080).start(); });
     Image3.setOnClickListener(view -> { UploadCode=3;ImagePicker.Companion.with(ReturnOrderActivity.this).compress(1024).maxResultSize(1080, 1080).start(); });
     submit.setOnClickListener(view ->{if(ValidateInput(reason.getText().toString())){mainview.setAlpha((float) 0.2);progressBar.setVisibility(View.VISIBLE);  MakeRequest(path1,path2,path3,getIntent().getExtras().getString("orderId"),reason.getText().toString(),getIntent().getExtras().getString("orerAmt")); } });
     }
    private void SetActivityData(){
        Glide.with(ReturnOrderActivity.this).load(Constants.IMAGES+getIntent().getExtras().getString("image")).into(productImage);
        name.setText(getIntent().getExtras().getString("name"));
        returncharges.setText("Order Return Charges  ₹"+"70");
        orderAmount.setText("₹"+getIntent().getExtras().getString("orerAmt"));
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null) {
            imageview.setVisibility(View.VISIBLE);
          if(UploadCode==1){
              img1=true;
              path1 = new File(String.valueOf(data.getData()).substring(7));
              upload1.setImageURI(data.getData());
          }
          if(UploadCode==2){
              path2 = new File(String.valueOf(data.getData()).substring(7));
              img2=true;
              upload2.setImageURI(data.getData());
          }
          if(UploadCode==3){
              img3=true;
              path3 = new File(String.valueOf(data.getData()).substring(7));
              upload3.setImageURI(data.getData());
          }
        }
    }


    public void MakeRequest(File path1,File path2,File path3,String ooderid,String reason,String amt)
    {
        Log.e("id",share_session.GetAddrssId());
        OkHttpClient okHttpClient1 = new OkHttpClient();
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image1", "name", RequestBody.create(MEDIA_TYPE_PNG,path1))
                .addFormDataPart("image2", "name", RequestBody.create(MEDIA_TYPE_PNG,path2))
                .addFormDataPart("image3", "name", RequestBody.create(MEDIA_TYPE_PNG,path3))
                .addFormDataPart("oid",ooderid)
                .addFormDataPart("reason",reason)
                .addFormDataPart("pamt",amt)
                .addFormDataPart("padd",share_session.GetAddrssId())
                .build();


        Request request1 = new Request.Builder().url(Constants.RETURNORDER).post(requestBody).build();
        okHttpClient1.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                ReturnOrderActivity.this.runOnUiThread(() -> {mainview.setAlpha(1);progressBar.setVisibility(View.GONE);Toast.makeText(ReturnOrderActivity.this, "Connection Timeout", Toast.LENGTH_SHORT).show(); });
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
                            ReturnOrderActivity.this.runOnUiThread(() -> {
                              mainview.setAlpha(1);
                              progressBar.setVisibility(View.GONE);
                                Toast.makeText(ReturnOrderActivity.this, "return request received", Toast.LENGTH_SHORT).show();
                            });
                        }else {
                            ReturnOrderActivity.this.runOnUiThread(() -> {
                                mainview.setAlpha(1);
                                progressBar.setVisibility(View.VISIBLE);
                                Toast.makeText(ReturnOrderActivity.this, "Connection Timeout", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }

                }
            }
        });
    }

    private boolean ValidateInput(String  reas){
        if(reas.isEmpty()){
            reason.setError("Write Reason");
            return false;
        }else {
            if(img1){
               if(img2){
                   if(img3){
                       return true;
                   }
                   else {
                       Toast.makeText(this, "Image3 Empty", Toast.LENGTH_SHORT).show();
                       return false;
                   }
               }else {
                   Toast.makeText(this, "Image2 Empty", Toast.LENGTH_SHORT).show();
                   return false;
               }
            }else {
                Toast.makeText(this, "Image1 Empty", Toast.LENGTH_SHORT).show();
                return false;
            }

        }
    }
}