package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.NotificationActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.other.NotificationList;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Viewholder> {

    List<NotificationList> notificationLists;
    Context context;

    public NotificationAdapter(NotificationActivity notificationActivity, List<NotificationList> notificationLists) {
        this.context = notificationActivity;
        this.notificationLists = notificationLists;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_design_notification,null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.title.setText(notificationLists.get(position).getTitle());
        holder.body.setText(notificationLists.get(position).getBody());
        holder.time.setText(notificationLists.get(position).getTitle());
        Glide.with(context).load(Constants.IMAGES+notificationLists.get(position).getImage()).centerCrop().placeholder(R.drawable.vendor_default).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return notificationLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ShapedImageView image;
        TextView title,body,time;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.title);
            body= itemView.findViewById(R.id.body);
            time= itemView.findViewById(R.id.time);
            image= itemView.findViewById(R.id.image);
        }
    }
}
