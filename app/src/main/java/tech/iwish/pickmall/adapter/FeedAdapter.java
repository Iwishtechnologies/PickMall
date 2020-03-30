package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.extended.TextViewFont;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    Context context;
    ArrayList<HashMap<String, String>> feeddata;

    public  void FeedAdapter(Context context, ArrayList<HashMap<String, String>> feeddata)
    {
        this.context=context;
        this.feeddata=feeddata;
    }
    @NonNull
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_feed_desin, null);
       ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return feeddata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ShapedImageView   Profile_Image,Post_Image;
        Button follow_button;
        TextViewFont Profile_name,Post_Date,Post_Tag,Post_comment,Like,Comment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Profile_Image=itemView.findViewById(R.id.profile_image);
            Post_Image=itemView.findViewById(R.id.post_Image);
            follow_button=itemView.findViewById(R.id.follow_button);
            Profile_name=itemView.findViewById(R.id.profile_name);
            Post_Date=itemView.findViewById(R.id.feed_date);
            Post_Tag=itemView.findViewById(R.id.post_tag);
            Post_comment=itemView.findViewById(R.id.post_comment);
            Like=itemView.findViewById(R.id.likes);
            Comment=itemView.findViewById(R.id.post_comment);


        }
    }
}
