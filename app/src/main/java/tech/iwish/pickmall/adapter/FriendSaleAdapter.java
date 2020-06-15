package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.FriendsDealsAllActivity;
import tech.iwish.pickmall.activity.MainActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.other.FriendSaleList;

public class FriendSaleAdapter extends RecyclerView.Adapter<FriendSaleAdapter.Viewholder> {

    private List<FriendSaleList> friendSaleLists ;
    private Context context ;

    public FriendSaleAdapter(MainActivity mainActivity, List<FriendSaleList> friendSaleLists) {
        this.context = mainActivity ;
        this.friendSaleLists = friendSaleLists ;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_friend_sale  ,null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        String a = Constants.IMAGES + friendSaleLists.get(position).getPimg();
        Glide.with(context).load(a).into(holder.image_flash_sale);


        holder.one_rus.setText(context.getResources().getString(R.string.rs_symbol)+friendSaleLists.get(position).getActual_price());

        holder.friend_deal_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(context , FriendsDealsAllActivity.class));
                intent.putExtra("item_id","26");
                intent.putExtra("item_type","friend_deal");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendSaleLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView one_rus ;
        private RelativeLayout friend_deal_linear ;
        private ImageView image_flash_sale ;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            one_rus = (TextView)itemView.findViewById(R.id.one_rus);
            friend_deal_linear = (RelativeLayout)itemView.findViewById(R.id.friend_deal_linear);
            image_flash_sale = (ImageView)itemView.findViewById(R.id.image_flash_sale);

        }
    }
}
