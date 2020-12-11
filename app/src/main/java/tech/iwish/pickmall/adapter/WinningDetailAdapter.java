package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.WinningDetailActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.WinList;
import tech.iwish.pickmall.other.WishlistList;

public class WinningDetailAdapter extends RecyclerView.Adapter<WinningDetailAdapter.Viewholder> {
    private Context context ;
    private List<WinList> winLists ;

    public  WinningDetailAdapter(Context context,List<WinList>winLists) {
        this.context= context;
        this.winLists=winLists;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_winnig_design , null);
        Viewholder viewholder =  new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        if(position==0){
            holder.name.setText(winLists.get(position).getClient_name());
            holder.item.setText(winLists.get(position).getProduct_name());
            Glide.with(context).load(Constants.IMAGES+winLists.get(position).getProduct_image()).into(holder.image);
            holder.layout.setBackground(context.getDrawable(R.drawable.yellow_win_background));
            if ((winLists.get(position).getProduct_type().equals("friend_deal"))){
                holder.type.setText("Win in Friends Deal ");
            }
            if ((winLists.get(position).getProduct_type().equals("One_win"))){
                holder.type.setText("Win in One Win ");
            }
            if ((winLists.get(position).getProduct_type().equals("90Rs"))){
                holder.type.setText("Win in 90Rs ");
            }
         }else if(position==1){
            holder.name.setText(winLists.get(position).getClient_name());
            holder.item.setText(winLists.get(position).getProduct_name());
            Glide.with(context).load(Constants.IMAGES+winLists.get(position).getProduct_image()).into(holder.image);
            holder.layout.setBackground(context.getDrawable(R.drawable.blue_win_background));
            if ((winLists.get(position).getProduct_type().equals("friend_deal"))){
                holder.type.setText("Win in Friends Deal ");
            }
            if ((winLists.get(position).getProduct_type().equals("One_win"))){
                holder.type.setText("Win in One Win ");
            }
            if ((winLists.get(position).getProduct_type().equals("90Rs"))){
                holder.type.setText("Win in 90Rs ");
            }
        }else if(position==2){
            holder.name.setText(winLists.get(position).getClient_name());
            holder.item.setText(winLists.get(position).getProduct_name());
            Glide.with(context).load(Constants.IMAGES+winLists.get(position).getProduct_image()).into(holder.image);
            holder.layout.setBackground(context.getDrawable(R.drawable.blue_win_background));
            if ((winLists.get(position).getProduct_type().equals("friend_deal"))){
                holder.type.setText("Win in Friends Deal ");
            }
            if ((winLists.get(position).getProduct_type().equals("One_win"))){
                holder.type.setText("Win in One Win ");
            }
            if ((winLists.get(position).getProduct_type().equals("90Rs"))){
                holder.type.setText("Win in 90Rs ");
            }
        }else {
            holder.name.setText(winLists.get(position).getClient_name());
            holder.item.setText(winLists.get(position).getProduct_name());
            Glide.with(context).load(Constants.IMAGES+winLists.get(position).getProduct_image()).into(holder.image);
            holder.layout.setBackground(context.getDrawable(R.drawable.white_win_background));
            if ((winLists.get(position).getProduct_type().equals("friend_deal"))){
                holder.type.setText("Win in Friends Deal ");
            }
            if ((winLists.get(position).getProduct_type().equals("One_win"))){
                holder.type.setText("Win in One Win ");
            }
            if ((winLists.get(position).getProduct_type().equals("90Rs"))){
                holder.type.setText("Win in 90Rs ");
            }
        }

    }

    @Override
    public int getItemCount() {
        return winLists.size();
    }

    public class Viewholder  extends  RecyclerView.ViewHolder{
        TextViewFont type,name,item;
        ShapedImageView image;
        LinearLayout layout;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
        type= itemView.findViewById(R.id.type);
        name= itemView.findViewById(R.id.name);
        item= itemView.findViewById(R.id.item);
        image= itemView.findViewById(R.id.image);
        layout= itemView.findViewById(R.id.layout);
        }
    }
}
