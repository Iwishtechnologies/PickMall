package tech.iwish.pickmall.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.Interface.Comment_Interface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.NewsList;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.USERMOBILE;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Viewholder> {

    List<NewsList> newsLists;
    Context context;
    Comment_Interface comment_Interface;
    Share_session shareSession;
    Map data;
    boolean likeCheck = false;

    public NewsAdapter(List<NewsList> newsLists, Comment_Interface comment_interface) {
        this.newsLists = newsLists;
        this.comment_Interface = comment_interface;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news, null);
        context = parent.getContext();
        shareSession = new Share_session(context);
        data = shareSession.Fetchdata();
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        NewsList list = newsLists.get(position);


        String a = Constants.IMAGES + list.getImage();
        Glide.with(context).load(a).into(holder.images);

        holder.descripsion.setText(list.getDescription());
        holder.likeSet.setText(list.getLikes());


    }

    @Override
    public int getItemCount() {
        return newsLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout likeClick, comment;
        TextView likeSet, descripsion;
        ImageView images;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            likeClick = itemView.findViewById(R.id.likeClick);
            likeSet = itemView.findViewById(R.id.likeSet);
            comment = itemView.findViewById(R.id.comment);
            images = itemView.findViewById(R.id.images);
            descripsion = itemView.findViewById(R.id.descripsion);

            likeClick.setOnClickListener(this);
            comment.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int id = view.getId();

            switch (id) {
                case R.id.likeClick:
                    likes();
                    break;
                case R.id.comment:
                    comment_Interface.commentInterface(newsLists.get(getAdapterPosition()).getSno());
                    break;
            }

        }

        private void likes() {

            likeCheck = true;


            OkHttpClient okHttpClient = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("news_sno", newsLists.get(getAdapterPosition()).getSno());
                jsonObject.put("user_number", data.get(USERMOBILE).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());
            Request request1 = new Request.Builder().url(Constants.LIKES).post(body).build();
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


                                if (((Activity) context) != null) {

                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            String msg = jsonHelper.GetResult("msg");
                                            String like = likeSet.getText().toString().trim();
                                            int likes = Integer.parseInt(like);
                                            if (msg.equals("like")) {
                                                int addLike = likes + 1;
                                                likeSet.setText(String.valueOf(addLike));
                                            } else {
                                                int unLike = likes - 1;
                                                likeSet.setText(String.valueOf(unLike));
                                            }
                                        }
                                    });

                                }
                            }
                        }
                    }
                }
            });

        }
    }
}





















