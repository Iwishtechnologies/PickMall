package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.content.Intent;
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

import okhttp3.Callback;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.MainActivity;
import tech.iwish.pickmall.activity.ProductActivity;
import tech.iwish.pickmall.other.ItemList;
import tech.iwish.pickmall.session.Share_session;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Viewholder> {
    private List<ItemList> itemLists ;
    private Context context ;
    private Share_session share_session;

    public ItemAdapter(MainActivity mainActivity, List<ItemList> itemLists) {
        this.context = mainActivity;
        this.itemLists = itemLists;
    }



    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_cat,parent  , false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {


        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , ProductActivity.class);
                intent.putExtra("item",itemLists.get(position).getItem_id());
//                intent.putExtra("image",itemLists.get(position).getIcon_img());
                context.startActivity(intent);
            }
        });
        String a = "http://173.212.226.143:8086/img/"+itemLists.get(position).getIcon_img();
        Glide.with(context).load(a).into(holder.image);
        holder.nameCat.setText(itemLists.get(position).getItem_name());
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView image;
        private LinearLayout layoutItem;
        private TextView nameCat;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.image);
            layoutItem = (LinearLayout)itemView.findViewById(R.id.layoutItem);
            nameCat = (TextView)itemView.findViewById(R.id.nameCat);
        }
    }
}
