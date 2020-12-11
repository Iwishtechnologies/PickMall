package tech.iwish.pickmall.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.New_PostAdapter;
import tech.iwish.pickmall.adapter.New_PostCommentAdapter;
import tech.iwish.pickmall.adapter.NewsCommentAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.Comment_list;
import tech.iwish.pickmall.other.New_comment_show;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.USERMOBILE;
import static tech.iwish.pickmall.session.Share_session.USERNAME;

public class CommentBottom extends BottomSheetDialogFragment {

    RecyclerView news_comment_recycleview;
    Share_session shareSession;
    Map data;
    LinearLayout send_comment;
    EditText comment;
    TextView no_comment, likes;
    String sno;
    List<Comment_list> comment_lists = new ArrayList<>();
    List<New_comment_show> new_comment_shows = new ArrayList<>();
    NewsCommentAdapter commentAdapter;
    LinearLayoutManager linearLayoutManager;
    String type;
    New_PostCommentAdapter new_postCommentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment_bottom, null);

        news_comment_recycleview = view.findViewById(R.id.news_comment_recycleview);
        send_comment = view.findViewById(R.id.send_comment);
        comment = view.findViewById(R.id.comment);
        no_comment = view.findViewById(R.id.no_comment);
        likes = view.findViewById(R.id.likes);


        shareSession = new Share_session(getActivity());
        data = shareSession.Fetchdata();

        type = getArguments().getString("type");

        switch (type) {
            case "new_post":
                sno = getArguments().getString("sno");
                New_Post();
                break;
            case "news":
                sno = getArguments().getString("sno");
                news();
                break;
        }


        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                switch (type) {
                    case "new_post":
                        if (comment.getText().toString().isEmpty()) {

                        } else {
                            new_postCommentShow();
                            new_comment_shows.add(new New_comment_show("", "", "", comment.getText().toString(), data.get(USERNAME).toString()));
                            Log.e("aa", String.valueOf(new_comment_shows.size()));
                            Log.e("aa", String.valueOf(new_comment_shows.size()));

                            new_postCommentAdapter = new New_PostCommentAdapter(new_comment_shows);
                            news_comment_recycleview.setAdapter(new_postCommentAdapter);
                            new_postCommentAdapter.notifyDataSetChanged();
                            linearLayoutManager = new LinearLayoutManager(getActivity());
                            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                            news_comment_recycleview.setLayoutManager(linearLayoutManager);
                            linearLayoutManager.isSmoothScrollbarEnabled();
                            linearLayoutManager.setStackFromEnd(true);
                            comment.setText("");
                            comment.setHint("Write...");

                        }
                        break;
                    case "news":
                        if (comment.getText().toString().isEmpty()) {

                        } else {
                            commentsMethod();
                            comment_lists.add(new Comment_list("", "", "", comment.getText().toString(), data.get(USERNAME).toString()));
                            commentAdapter = new NewsCommentAdapter(comment_lists);
                            news_comment_recycleview.setAdapter(commentAdapter);
                            commentAdapter.notifyDataSetChanged();
                            linearLayoutManager = new LinearLayoutManager(getActivity());
                            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                            news_comment_recycleview.setLayoutManager(linearLayoutManager);
                            linearLayoutManager.isSmoothScrollbarEnabled();
                            linearLayoutManager.setStackFromEnd(true);
                            comment.setText("");
                            comment.setHint("Write...");
                        }
                        break;
                }


            }
        });


        return view;
    }

    private void new_postCommentShow() {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("new_sno", getArguments().getString("sno"));
            jsonObject.put("user_number", data.get(USERMOBILE).toString());
            jsonObject.put("comment", comment.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.NEW_POST_COMMENT_SEND).post(body).build();
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

                        }
                    }
                }
            }
        });


    }

    private void news() {

        show_comment();

    }

    private void New_Post() {

        new_post_comment_show();


    }

    private void new_post_comment_show() {


        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        news_comment_recycleview.setLayoutManager(linearLayoutManager);
//        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.isSmoothScrollbarEnabled();
        linearLayoutManager.setStackFromEnd(true);

        comment_lists.clear();
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("new_sno", sno);
            jsonObject.put("user_number", data.get(USERMOBILE).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.NEW_POST_COMMENT).post(body).build();
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


                            if (getActivity() != null) {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        no_comment.setVisibility(View.GONE);
                                    }
                                });

                            }

                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                new_comment_shows.add(new New_comment_show(jsonHelper.GetResult("sno"), jsonHelper.GetResult("user_number"), jsonHelper.GetResult("new_post_sno"), jsonHelper.GetResult("comment"), jsonHelper.GetResult("name")));
                            }

                            if (getActivity() != null) {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new_postCommentAdapter = new New_PostCommentAdapter(new_comment_shows);
                                        news_comment_recycleview.setAdapter(new_postCommentAdapter);

                                    }
                                });

                            }


                        } else {

                            if (getActivity() != null) {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        no_comment.setVisibility(View.VISIBLE);
                                    }
                                });

                            }
                        }

                    }
                }
            }
        });


    }


    private void show_comment() {


        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        news_comment_recycleview.setLayoutManager(linearLayoutManager);
//        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.isSmoothScrollbarEnabled();
        linearLayoutManager.setStackFromEnd(true);

        comment_lists.clear();
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("new_sno", sno);
            jsonObject.put("user_number", data.get(USERMOBILE).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.NEWS_COMMENT).post(body).build();
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


                            if (getActivity() != null) {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        no_comment.setVisibility(View.GONE);
                                        comment_lists.clear();
                                    }
                                });

                            }

                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                comment_lists.add(new Comment_list(jsonHelper.GetResult("sno"), jsonHelper.GetResult("user_number"), jsonHelper.GetResult("news_sno"), jsonHelper.GetResult("comment"), jsonHelper.GetResult("name")));
                            }

                            if (getActivity() != null) {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        commentAdapter = new NewsCommentAdapter(comment_lists);
                                        news_comment_recycleview.setAdapter(commentAdapter);

                                    }
                                });

                            }


                        } else {

                            if (getActivity() != null) {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        no_comment.setVisibility(View.VISIBLE);
                                    }
                                });

                            }
                        }

                    }
                }
            }
        });


    }

    private void commentsMethod() {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("new_sno", getArguments().getString("sno"));
            jsonObject.put("user_number", data.get(USERMOBILE).toString());
            jsonObject.put("comment", comment.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.NEWS_COMMENT_SEND).post(body).build();
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

                        }
                    }
                }
            }
        });


    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                setupFullHeight(bottomSheetDialog);
            }
        });
        return dialog;
    }


    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setPeekHeight(1000);


        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        int windowHeight = getWindowHeight() - 300;
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);


    }

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


}