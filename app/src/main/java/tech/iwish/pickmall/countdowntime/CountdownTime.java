package tech.iwish.pickmall.countdowntime;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import tech.iwish.pickmall.Interface.FlashsaleTimeIdInterface;

public class CountdownTime {

    private long mTimeLeftInMillis;
    TextView settime;
    LinearLayout removelayout;
    Context context;
    String time_id;
    FlashsaleTimeIdInterface flashsaleTimeIdInterface;
    private CountDownTimer mCountDownTimer;

/*

    public CountdownTime(Context context, TextView textView, LinearLayout removelayout, FlashsaleTimeIdInterface flashsaleTimeIdInterface) {
        this.settime = textView;
        this.removelayout = removelayout;
        this.context = context;
        this.flashsaleTimeIdInterface = flashsaleTimeIdInterface;
    }
//    countdown

    public String Flashsaletimeset() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.FLASH_SALE_TIME)
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
                    Log.e("response", result);

                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {

                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
//                            time_id = jsonHelper.GetResult("saleid");
                            for (int i = 0; i < 1; i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);

                                final String startdatetime = jsonHelper.GetResult("startdatetime");
                                final String enddatetime = jsonHelper.GetResult("enddatetime");

                                flashsaleTimeIdInterface.flashsaleId(time_id);
                                final String id = jsonHelper.GetResult("saleid");
                                final String[] strt = startdatetime.split(" ", 2);
                                final String[] end = enddatetime.split(" ", 2);
                                String strTime = strt[1];
                                String endTime = end[1];
                                Log.e("str", strTime);
                                try {
                                    final String str_hourse[] = strTime.split(":");
                                    final String end_time[] = endTime.split(":");
                                    DateFormat formatter = new SimpleDateFormat("hh:mm");
                                    Date dateStart = (Date) formatter.parse(strTime);
                                    Date dateEnd = (Date) formatter.parse(endTime);
                                    long StartTimeLong = dateStart.getTime();
                                    long EndTimeLong = dateEnd.getTime();
//                                            check time
                                    final Timer t = new Timer();
                                    t.scheduleAtFixedRate(new TimerTask() {
                                        @Override
                                        public void run() {
//                                          AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                            Calendar calendar = Calendar.getInstance();
                                            Calendar current_time = Calendar.getInstance();
                                            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(str_hourse[0]));
                                            calendar.set(Calendar.MINUTE, Integer.parseInt(str_hourse[1]));
                                            calendar.set(Calendar.SECOND, 0);

                                            Calendar endTimeCheck = Calendar.getInstance();
                                            endTimeCheck.set(Calendar.HOUR_OF_DAY, Integer.parseInt(end_time[0]));
                                            endTimeCheck.set(Calendar.MINUTE, Integer.parseInt(end_time[1]));
                                            endTimeCheck.set(Calendar.SECOND, 0);

                                            if (calendar.before(current_time)) {
                                                Log.e("before", "before");
                                            } else if (calendar.after(current_time)) {
                                                Log.e("after", "after");
                                            }


                                            if (calendar.before(current_time)) {


                                                ((Activity) context).runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (context instanceof MainActivity) {
                                                            removelayout.setVisibility(View.VISIBLE);
                                                        }
                                                    }
                                                });

                                                if (endTimeCheck.before(current_time)) {
                                                    flashsaletimeremove(id);
                                                } else if (endTimeCheck.after(current_time)) {
                                                    long a = endTimeCheck.getTimeInMillis() - current_time.getTimeInMillis();
                                                    countdown(a, id);
                                                    t.cancel();
                                                }
                                            } else if (calendar.equals(current_time)) {

//                                                ((Activity) context).runOnUiThread(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//                                                        if (context instanceof MainActivity) {
//                                                            removelayout.setVisibility(View.VISIBLE);
//                                                        }
//                                                    }
//                                                });

                                                long a = endTimeCheck.getTimeInMillis() - current_time.getTimeInMillis();
                                                countdown(a, id);
                                                t.cancel();
                                            } else if (calendar.after(current_time)) {


                                                ((Activity) context).runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (context instanceof MainActivity) {
                                                            removelayout.setVisibility(View.GONE);
                                                        }
                                                    }
                                                });

                                            }
                                        }
                                    }, 1000, 1000);

                                } catch (Exception e) {

                                    Log.e("Exception is ", e.toString());
                                }

                            }
                        } else {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (context instanceof MainActivity) {
                                        removelayout.setVisibility(View.GONE);
                                    }
                                }
                            });

                        }
                    }

                }
            }
        });
        return time_id;
    }

    private void countdown(long data, final String id) {

        mTimeLeftInMillis = data;

        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new CountDownTimer(mTimeLeftInMillis, 1000) {
                    @Override
                    public void onTick(long l) {

                        mTimeLeftInMillis = l;
                        updateCountDownText();

                    }

                    @Override
                    public void onFinish() {
                        flashsaletimeremove(id);
                    }
                }.start();
            }
        });
    }

    private void updateCountDownText() {

        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        Log.e("updateCountDownText: ", timeLeftFormatted);
        settime.setText(timeLeftFormatted);

    }

    private void flashsaletimeremove(String id) {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.FLASH_SALE_TIME_REMOVE).post(body).build();
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
//                            MainActivity.this.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {

//                                }
//                            });
                        }
                    }
                }
            }
        });


    }
*/


//    countdown start
//    24 houres count down


    public CountdownTime(TextView settime) {
        this.settime = settime;
        time_count_24_hourse();
    }

    public void time_count_24_hourse() {


        DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String stringDate = sdf.format(new Date());
        String[] breaktime = stringDate.split(":");

        Calendar currenttime = Calendar.getInstance();
        currenttime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(breaktime[0]));
        currenttime.set(Calendar.MINUTE, Integer.parseInt(breaktime[1]));
        currenttime.set(Calendar.SECOND, Integer.parseInt(breaktime[2]));

        Calendar times = Calendar.getInstance();
        times.set(Calendar.HOUR_OF_DAY, 23);
        times.set(Calendar.MINUTE, 59);
        times.set(Calendar.SECOND, 59);
        long b = times .getTimeInMillis() - currenttime.getTimeInMillis();
        mTimeLeftInMillis = b;
        startTimer();

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

        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        Log.e("updateCountDownText: ", timeLeftFormatted);
        settime.setText(timeLeftFormatted);

    }

}
