package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.FriendsDealsAllActivity;
import tech.iwish.pickmall.activity.ProductDetailsActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.other.FriendSale;

public class FriendSaleAllProductAdapter extends RecyclerView.Adapter<FriendSaleAllProductAdapter.Viewholder> {

    private Context context;
    private List<FriendSale> friendSaleLists;
    private String item_type;

    public FriendSaleAllProductAdapter(FriendsDealsAllActivity friendsDealsAllActivity, List<FriendSale> friendSaleLists , String item_type) {
        this.context = friendsDealsAllActivity;
        this.friendSaleLists = friendSaleLists;
        this.item_type = item_type;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_friend_sale_product_all, null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        holder.one_rs_product_name.setText(friendSaleLists.get(position).getProductName());
        holder.product_amount.setText(context.getResources().getString(R.string.rs_symbol) + friendSaleLists.get(position).getActual_price());
        String a = Constants.IMAGES + friendSaleLists.get(position).getPimg();
        Glide.with(context).load(a).into(holder.one_rs_image);

        holder.start_deal_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("product_name", friendSaleLists.get(position).getProductName());
                intent.putExtra("actual_price", friendSaleLists.get(position).getActual_price());
                intent.putExtra("discount_price", friendSaleLists.get(position).getDiscount_price());
                intent.putExtra("product_id", friendSaleLists.get(position).getProduct_id());
                intent.putExtra("product_Image", friendSaleLists.get(position).getPimg());
                intent.putExtra("vendor_id", friendSaleLists.get(position).getVendor_id());
                intent.putExtra("new_user_request", friendSaleLists.get(position).getNew_users_atleast());
                intent.putExtra("item_type", item_type);
                intent.putExtra("product_type", "friendsaleoneRs");
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return friendSaleLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView one_rs_image;
        private TextView one_rs_product_name, product_amount;
        private LinearLayout one_rs_layout, start_deal_layout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            one_rs_image = (ImageView) itemView.findViewById(R.id.one_rs_image);
            one_rs_product_name = (TextView) itemView.findViewById(R.id.one_rs_product_name);
            product_amount = (TextView) itemView.findViewById(R.id.product_amount);
            one_rs_layout = (LinearLayout) itemView.findViewById(R.id.one_rs_layout);
            start_deal_layout = (LinearLayout) itemView.findViewById(R.id.start_deal_layout);

        }
    }
}





















