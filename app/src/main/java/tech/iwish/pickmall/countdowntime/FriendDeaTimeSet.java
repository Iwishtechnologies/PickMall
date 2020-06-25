package tech.iwish.pickmall.countdowntime;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.activity.OneRsShareActivity;
import tech.iwish.pickmall.activity.PaymentOptionActivity;
import tech.iwish.pickmall.activity.ProductDetailsActivity;
import tech.iwish.pickmall.activity.SaveAddressActivity;
import tech.iwish.pickmall.adapter.ProductSizeAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.ProductSizeColorList;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.USERMOBILE;

public class FriendDeaTimeSet {

    private String product_id, client_number, ProductTime , refer_code;

    public long mTimeLeftInMillis;
    private CountDownTimer mCountDownTimer;
    Activity activity;
    TextView timeset;
    String item_type;

    public FriendDeaTimeSet(String product_id, String client_number, Activity activity, TextView textView, String item_type) {
        this.product_id = product_id;
        this.client_number = client_number;
        this.activity = activity;
        this.timeset = textView;
        this.item_type = item_type;
    }

    public void Time_12_H() {

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", product_id);
            jsonObject.put("client_id", client_number);
            jsonObject.put("item_type", item_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.FRIENDDEAL_TIME_SET)
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
                    Log.e("result", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                ProductTime = jsonHelper.GetResult("date_time");
                                refer_code = jsonHelper.GetResult("code");
                            }

                            Calendar ProductClientTime = Calendar.getInstance();
                            Calendar ProductSetTime = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy:hh:mm:ss");



                            /*=========================     Times  set   =====================    */
                            String[] DateTimeBreak = ProductTime.split("=");
                            String[] DateBreak = DateTimeBreak[0].split("-");
                            String[] TimeBreak = DateTimeBreak[1].split(":");
                            String h = TimeBreak[0];
                            String m = TimeBreak[1];
                            String s = TimeBreak[2];
                            String d = DateBreak[0];
                            String mo = DateBreak[1];
                            String y = DateBreak[2];


//                            ProductClientTime.set(Calendar.HOUR, Integer.parseInt(h));
//                            ProductClientTime.set(Calendar.MONTH, Integer.parseInt(m));
//                            ProductClientTime.set(Calendar.SECOND, Integer.parseInt(s));
//                            ProductClientTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(d));
//                            ProductClientTime.set(Calendar.MONTH, Integer.parseInt(mo)-1);
//                            ProductClientTime.set(Calendar.YEAR, Integer.parseInt(y));

                            ProductSetTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(h));
                            ProductSetTime.set(Calendar.MINUTE, Integer.parseInt(m));
                            ProductSetTime.set(Calendar.SECOND, Integer.parseInt(s));
                            ProductSetTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(d) + 1);
                            ProductSetTime.set(Calendar.MONTH, Integer.parseInt(mo) - 1);
                            ProductSetTime.set(Calendar.YEAR, Integer.parseInt(y));
                            Calendar a = ProductSetTime;
//                            a.setTime(ProductSetTime.getTime());
//                            a = ProductClientTime;
                            a.add(ProductSetTime.HOUR_OF_DAY, 24);
                            a.add(ProductSetTime.MINUTE, 0);
                            a.add(ProductSetTime.SECOND, -1);
                            a.set(ProductSetTime.DAY_OF_MONTH, Integer.parseInt(d) + 1);
                            a.set(ProductSetTime.MONTH, Integer.parseInt(mo) - 1);
                            a.set(ProductSetTime.YEAR, Integer.parseInt(y));


                            /*=========================     Current Times    =====================    */

                            DateFormat sdfss = new SimpleDateFormat("HH:mm:ss");
                            String stringDate = sdfss.format(new Date());
                            String[] breaktime = stringDate.split(":");
                            Calendar currenttime = Calendar.getInstance();
                            currenttime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(breaktime[0]));
                            currenttime.set(Calendar.MINUTE, Integer.parseInt(breaktime[1]));
                            currenttime.set(Calendar.SECOND, Integer.parseInt(breaktime[2]));

                            Log.e("ppp", sdf.format(ProductSetTime.getTime()));
                            Log.e("ppp", sdf.format(a.getTime()));
                            Log.e("ppp", sdf.format(currenttime.getTime()));


                            if (a.before(currenttime)) {
//                                update status
                                Log.e("ppp", "before");
                                RemoveFriendProduct();
                            } else if (a.after(currenttime)) {
                                Log.e("ppp", "after");
                                long finaltime = a.getTimeInMillis() - currenttime.getTimeInMillis();
                                mTimeLeftInMillis = finaltime;
                                ((activity)).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        startTimer();
                                    }
                                });
                            } else if (a.equals(currenttime)) {

                            }

                        }
                    }
                }

            }
        });


    }

    public void Time_24_H() {

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", product_id);
            jsonObject.put("client_id", client_number);
            jsonObject.put("item_type", item_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.FRIENDDEAL_TIME_SET)
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
                    Log.e("result", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                ProductTime = jsonHelper.GetResult("date_time");
                                refer_code = jsonHelper.GetResult("code");
                            }

                            Calendar ProductClientTime = Calendar.getInstance();
                            Calendar ProductSetTime = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy:hh:mm:ss");



                            /*=========================     Times  set   =====================    */
                            String[] DateTimeBreak = ProductTime.split("=");
                            String[] DateBreak = DateTimeBreak[0].split("-");
                            String[] TimeBreak = DateTimeBreak[1].split(":");
                            String h = TimeBreak[0];
                            String m = TimeBreak[1];
                            String s = TimeBreak[2];
                            String d = DateBreak[0];
                            String mo = DateBreak[1];
                            String y = DateBreak[2];


//                            ProductClientTime.set(Calendar.HOUR, Integer.parseInt(h));
//                            ProductClientTime.set(Calendar.MONTH, Integer.parseInt(m));
//                            ProductClientTime.set(Calendar.SECOND, Integer.parseInt(s));
//                            ProductClientTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(d));
//                            ProductClientTime.set(Calendar.MONTH, Integer.parseInt(mo)-1);
//                            ProductClientTime.set(Calendar.YEAR, Integer.parseInt(y));

                            ProductSetTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(h) + 24);
                            ProductSetTime.set(Calendar.MINUTE, Integer.parseInt(m));
                            ProductSetTime.set(Calendar.SECOND, Integer.parseInt(s) - 1);
                            ProductSetTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(d));
                            ProductSetTime.set(Calendar.MONTH, Integer.parseInt(mo) + 0);
                            ProductSetTime.set(Calendar.YEAR, Integer.parseInt(y));
                            Calendar a = ProductSetTime;
//                            a.setTime(ProductSetTime.getTime());
//                            a = ProductClientTime;
                            a.add(ProductSetTime.HOUR_OF_DAY, Integer.parseInt(h) + 24);
                            a.add(ProductSetTime.MINUTE, Integer.parseInt(m));
                            a.add(ProductSetTime.SECOND, -1);
                            a.set(ProductSetTime.DAY_OF_MONTH, Integer.parseInt(d));
                            a.set(ProductSetTime.MONTH, Integer.parseInt(mo) + 0);
                            a.set(ProductSetTime.YEAR, Integer.parseInt(y));


                            /*=========================     Current Times    =====================    */

                            DateFormat sdfss = new SimpleDateFormat("HH:mm:ss");
                            String stringDate = sdfss.format(new Date());
                            String[] breaktime = stringDate.split(":");
                            Calendar currenttime = Calendar.getInstance();
                            currenttime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(breaktime[0]));
                            currenttime.set(Calendar.MINUTE, Integer.parseInt(breaktime[1]));
                            currenttime.set(Calendar.SECOND, Integer.parseInt(breaktime[2]));

                            Log.e("ppp", sdf.format(ProductSetTime.getTime()));
                            Log.e("ppp", sdf.format(a.getTime()));
                            Log.e("ppp", sdf.format(currenttime.getTime()));


                            if (a.before(currenttime)) {
//                                update status
                                Log.e("ppp", "before");
                                RemoveFriendProduct();
                            } else if (a.after(currenttime)) {
                                Log.e("ppp", "after");
                                long finaltime = a.getTimeInMillis() - currenttime.getTimeInMillis();
                                mTimeLeftInMillis = finaltime;
                                ((activity)).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        startTimer();
                                    }
                                });
                            } else if (a.equals(currenttime)) {

                            }

                        }
                    }
                }

            }
        });


    }

    private void RemoveFriendProduct() {

        Share_session shareSession = new Share_session(activity);
        Map data = shareSession.Fetchdata();

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("client_number", data.get(USERMOBILE).toString());
            jsonObject.put("product_id", product_id);
            jsonObject.put("refer_code", refer_code);
            jsonObject.put("item_type", item_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.FRIENDEAL_PRODUCT_REMOVE).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
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


    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }

    private void updateCountDownText() {

//        https://stackoverflow.com/questions/12475873/android-countdown-timer-time

        int days = (int) ((mTimeLeftInMillis / 1000) / 86400);
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        Log.e("ppp", String.valueOf(days));

        String timeLeftFormatted;
        if (days > 0) {

            hours = (int) (((mTimeLeftInMillis / 1000) - (days
                    * 86400)) / 3600);

            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%dD:%d:%02d:%02d", days, hours, minutes, seconds);
        } else if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        Log.e("ppp:", timeLeftFormatted);
        timeset.setText(timeLeftFormatted);

    }


}
