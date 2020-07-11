package tech.iwish.pickmall.adapter;

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
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.New_PostList;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.USERMOBILE;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.Viewholder> {

    Context context;
    List<New_PostList> new_postLists;
    Share_session shareSession;
    Map data;

    public TimeLineAdapter(List<New_PostList> new_postLists) {
        this.new_postLists = new_postLists;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news,null);
        context = parent.getContext();
        shareSession = new Share_session(context);
        data = shareSession.Fetchdata();
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        New_PostList list = new_postLists.get(position);

        String a = Constants.IMAGES + list.getImage();
        Glide.with(context).load(a).into(holder.images);
        holder.descripsion.setText(list.getDescription());

        holder.likeSet.setText(list.getLikes());


    }

    @Override
    public int getItemCount() {
        return new_postLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout likeClick, comment;
        TextView likeSet,descripsion;
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

                    break;
            }

        }

        private void likes() {
            OkHttpClient okHttpClient = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("news_sno", new_postLists.get(getAdapterPosition()).getSno());
                jsonObject.put("user_number", data.get(USERMOBILE).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());
            Request request1 = new Request.Builder().url(Constants.NEW_POST_LIKE).post(body).build();
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

    }
}























