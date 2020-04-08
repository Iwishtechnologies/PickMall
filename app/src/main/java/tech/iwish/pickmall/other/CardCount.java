package tech.iwish.pickmall.other;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.sqlconnection.MyhelperSql;

public class CardCount {

    public Context context;
    public static String perameterApi ;

    public static String card_count(Context context) {
        MyhelperSql myhelperSql = new MyhelperSql(context);
        String query = "Select * from PRODUCT_CARD";
        SQLiteDatabase sqLiteDatabase = myhelperSql.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{});
        return String.valueOf(cursor.getCount());
    }

    public void  save_wishlist(String product_type, String product_id , String user_number) {


//        switch (product_type) {
//            case "product":
//                perameterApi = "wishlist/product";
//                break;
//            case "flashsale":
//                perameterApi = "wishlist/flashsale";
//                break;
//        }

//        Log.e("wish list" , perameterApi);
//        Log.e("mob" , user_number);


        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", product_id);
            jsonObject.put("product_type", product_type);
            jsonObject.put("user_number", user_number);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url("http://173.212.226.143:8086/api/wishlist/product").post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.e("response", response.body().string());
                }
            }
        });


    }


}

























