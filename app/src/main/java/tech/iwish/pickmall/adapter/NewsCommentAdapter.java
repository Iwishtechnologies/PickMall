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

public class NewsCommentAdapter extends RecyclerView.Adapter<NewsCommentAdapter.Viewholder> {

    List<Comment_list> comment_lists;
    Context context;

    public NewsCommentAdapter(List<Comment_list> comment_lists) {

    this.comment_lists = comment_lists;

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

        Comment_list list = comment_lists.get(position);

        holder.names.setText(list.getName());
        holder.comments.setText(list.getComment());

    }

    @Override
    public int getItemCount() {
        return comment_lists.size();
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
