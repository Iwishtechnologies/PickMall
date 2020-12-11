package tech.iwish.pickmall.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ir.hamiss.internetcheckconnection.InternetAvailabilityChecker;
import ir.hamiss.internetcheckconnection.InternetConnectivityListener;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.BuildConfig;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.MyOrderAdapter;
import tech.iwish.pickmall.adapter.RevardRankAdapter;
import tech.iwish.pickmall.adapter.WishListAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.RankList;
import tech.iwish.pickmall.other.WishlistList;
import tech.iwish.pickmall.session.Share_session;

public class InviteActivity extends AppCompatActivity implements InternetConnectivityListener {
    private static final int MAX_LENGTH = 20;
    Button invite;
    ImageView back;
    TextViewFont invitecode;
    Share_session share_session;
    ProgressBar progressBar;
    ScrollView scrollView;
    RecyclerView recyclerView;
    List<RankList> rankLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        InitializeActivity();
        SetActivityData();
        ActivityAction();
        SetRecycleView();
    }


    protected void InitializeActivity() {

        invite = findViewById(R.id.invite);
        back = findViewById(R.id.back);
        invitecode = findViewById(R.id.invitecode);
        progressBar = findViewById(R.id.progress);
        scrollView = findViewById(R.id.scroll);
        recyclerView = findViewById(R.id.recycle);
        share_session = new Share_session(InviteActivity.this);
        SaveReferrelCode(random(share_session.getUserDetail().get("id")), share_session.getUserDetail().get("UserMobile"));
        Connectivity();
    }


    protected void ActivityAction() {
        back.setOnClickListener(view -> onBackPressed());
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareTextUrl(invitecode.getText().toString());
            }
        });
    }

    protected void SetActivityData() {
//          invitecode.setText(random(share_session.getUserDetail().get("id")));
    }


    public static String random(String id) {
        Random ran = new Random();
        int top = 8;
        char data = ' ';
        String dat = "";

        for (int i = 0; i <= top; i++) {
            data = (char) (ran.nextInt(25) + 97);
            dat = data + dat;
        }
        return dat + id;
    }

    private void SaveReferrelCode(String code, String mobile1) {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ref", code);
            jsonObject.put("mobile", mobile1);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.REFERREL)
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
                    final JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            InviteActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    invitecode.setText(jsonHelper.GetResult("data"));
                                    progressBar.setVisibility(View.GONE);
                                    scrollView.setAlpha(1);

                                }
                            });

                        }
                    }

                }
            }
        });

    }


    private void shareTextUrl(String Code) {

        Uri bmpUri = getLocalBitmapUri(drawableToBitmap(getResources().getDrawable(R.drawable.picbanner))); // see previous remote images section
        Intent shareIntent;
        shareIntent = new Intent();
        //shareIntent.setPackage("com.whatsapp");
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "PICKMALL Online Shopping Everything On Factory Price\n\nPICKMALL, as one of the best online shopping app in India,\n\nyou can shop the latest trendy items with lowest price & high quality at home. Free shipping & Cash on delivery service are supported, all latest trendy products UP TO 90% OFF! \n\nWe Sell Everything On Fair Price Because PICKMALL Is a Platform For Customer Can Buy Everything From Factory’s Without Any Extra Price Or Any Type Of Commission.\n\nHey check out my app at: https://play.google.com/store/apps/details?id=tech.iwish.pickmall \n\n Referrel Code:-" + Code);
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share"));
    }

    private Bitmap getbitmap() {
        final Bitmap[] image = new Bitmap[1];
        Glide.with(this)
                .asBitmap()
                .load(getResources().getDrawable(R.drawable.logo))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        image[0] = resource;
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
        return image[0];
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bmpUri = Uri.fromFile(file);
        return bmpUri;
    }


    public void Connectivity() {
        InternetAvailabilityChecker mInternetAvailabilityChecker;
        mInternetAvailabilityChecker = InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker.addInternetConnectivityListener(InviteActivity.this);
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected) {
        } else {
            startActivity(new Intent(InviteActivity.this, NoInternetConnectionActivity.class));
        }
    }


    private void SetRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InviteActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", share_session.getUserDetail().get("UserMobile"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.REWARD_RANK)
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
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                rankLists.add(new RankList(String.valueOf(i + 1), jsonHelper.GetResult("name"), "₹ " + jsonHelper.GetResult("tot")));
                            }
                            InviteActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (rankLists.size() == 0) {
//                                        no_product.setVisibility(View.VISIBLE);
//                                        shimmer_view.setVisibility(View.GONE);
//                                        recyclerView.setVisibility(View.GONE);
                                    } else {
                                        RevardRankAdapter revardRankAdapter = new RevardRankAdapter(InviteActivity.this, rankLists);
                                        recyclerView.setVisibility(View.VISIBLE);
                                        recyclerView.setAdapter(revardRankAdapter);
                                    }

                                }
                            });

                        }
                    }

                }
            }
        });


    }

}
