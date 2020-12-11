package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.List;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.VendorStoreActivity;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.FllowingList;
import tech.iwish.pickmall.other.WishlistList;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.ViewHolder> {
    private Context context ;
    private List<FllowingList> fllowingLists ;

    public  FollowingAdapter(Context context,List<FllowingList>fllowingLists) {
        this.context= context;
        this.fllowingLists=fllowingLists;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_following_design , null);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.name.setText(fllowingLists.get(position).getName());
        holder.view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, VendorStoreActivity.class);
                intent.putExtra("vendor_id",fllowingLists.get(position).getId());
                context.startActivity(intent);
                Animatoo.animateFade(context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fllowingLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ShapedImageView image;
        TextViewFont name;
        Button view_all;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.image);
            name= itemView.findViewById(R.id.name);
            view_all= itemView.findViewById(R.id.viewall);

        }
    }
}
