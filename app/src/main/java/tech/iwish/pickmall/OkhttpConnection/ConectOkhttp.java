package tech.iwish.pickmall.OkhttpConnection;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.activity.NewPostActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.USERMOBILE;

public class ConectOkhttp {
    Share_session share_session;


    public boolean res = false;

    private void formdata() {
        //        Form data
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("item_id", "38")
                .build();
        Request request1 = new Request.Builder().url("http://173.212.226.143:8086/categoryapi").post(requestBody).build();
        okHttpClient.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }


    private void OkhttpAll_request() {
        //  OkHttp   get
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://173.212.226.143:8086/main/silder/images")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
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


//        okhttps post request

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", "7");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url("http://173.212.226.143:8086/categoryapi").post(body).build();


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

                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);

                            }
                        }
                    }
                }
            }
        });


//        try {
//            Response response = okHttpClient.newCall(request1).execute();
//            Log.e("response" , response.body().string());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }


    private void multipartformdata() {

        File file = null;

        OkHttpClient okHttpClient1 = new OkHttpClient();
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("images", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
//            .addFormDataPart("images",file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file))
                .addFormDataPart("description", "")
                .build();


        Request request1 = new Request.Builder().url(Constants.SEND_NEW_POST).post(requestBody).build();
        okHttpClient1.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
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


                        }
                    }

                }
            }
        });


    }


}
