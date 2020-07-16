package tech.iwish.pickmall.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
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
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.Interface.Comment_Interface;
import tech.iwish.pickmall.Interface.New_PostIInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.NewsActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.Comment_list;
import tech.iwish.pickmall.other.New_PostList;
import tech.iwish.pickmall.other.NewsList;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.USERMOBILE;

public class New_PostAdapter extends RecyclerView.Adapter<New_PostAdapter.Viewholder> {


    New_PostIInterface new_postIInterface;
    Context context;
    NewsActivity newsActivity;
    Share_session shareSession;
    Map data;
    List<New_PostList> new_postLists;

    public New_PostAdapter(List<New_PostList> new_postLists, New_PostIInterface new_postIInterface ,NewsActivity newsActivity) {
        this.new_postLists = new_postLists;
        this.new_postIInterface = new_postIInterface;
        this.newsActivity=newsActivity;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_new_post, null);
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

        LinearLayout likeClick, comment, shareLinearLayout;
        TextView likeSet, descripsion;
        ImageView images;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            likeClick = itemView.findViewById(R.id.likeClick);
            likeSet = itemView.findViewById(R.id.likeSet);
            comment = itemView.findViewById(R.id.comment);
            images = itemView.findViewById(R.id.images);
            descripsion = itemView.findViewById(R.id.descripsion);
            shareLinearLayout = itemView.findViewById(R.id.shareLinearLayout);

            likeClick.setOnClickListener(this);
            comment.setOnClickListener(this);
            shareLinearLayout.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            int id = view.getId();

            switch (id) {
                case R.id.likeClick:
                    likes();
                    break;
                case R.id.comment:
                    new_postIInterface.newPostInterface(new_postLists.get(getAdapterPosition()).getSno());
                    break;
                case R.id.shareLinearLayout:
                    share();
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

        private void share() {

            Uri bmpUri = getLocalBitmapUri(getbitmap());
            Intent shareIntent;
            shareIntent = new Intent();
            shareIntent.setPackage("com.whatsapp");
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, new_postLists.get(getAdapterPosition()).getDescription());
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(shareIntent, "Share Opportunity"));
                    }

        private Bitmap getbitmap() {

             Bitmap image = null;
            try {
                URL url = new URL( Constants.IMAGES+new_postLists.get(getAdapterPosition()).getImage());
                 image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch(IOException e) {
                System.out.println(e);
            }
            return image ;
        }


        private Uri getLocalBitmapUri(Bitmap bmp) {
            Uri bmpUri = null;
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
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


    }


//    =========================================================

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }


}
















