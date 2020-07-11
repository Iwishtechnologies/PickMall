package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.other.Comment_list;
import tech.iwish.pickmall.other.New_comment_show;

public class New_PostCommentAdapter extends RecyclerView.Adapter<New_PostCommentAdapter.Viewholder> {


    List<New_comment_show> new_comment_shows;
    Context context;

    public New_PostCommentAdapter(List<New_comment_show> new_comment_shows) {
        this.new_comment_shows = new_comment_shows;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news_comment,null);
        context = parent.getContext();

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        New_comment_show list = new_comment_shows.get(position);

        holder.names.setText(list.getName());
        holder.comments.setText(list.getComment());

    }

    @Override
    public int getItemCount() {
        return new_comment_shows.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView names ,comments;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            names = itemView.findViewById(R.id.names);
            comments = itemView.findViewById(R.id.comments);

        }
    }
}
