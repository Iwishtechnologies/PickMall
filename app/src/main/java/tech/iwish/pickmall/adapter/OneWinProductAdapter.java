package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
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

import java.util.ArrayList;
import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.One_winActivity;
import tech.iwish.pickmall.activity.Product;
import tech.iwish.pickmall.activity.ProductDetailsActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.fragment.ProductSideColorBottomFragment;
import tech.iwish.pickmall.other.FriendSale;

public class OneWinProductAdapter extends RecyclerView.Adapter<OneWinProductAdapter.Viewholder> {

    private Context context;
    private List<FriendSale> one_win_list;
    private String item_type;

    public OneWinProductAdapter(One_winActivity one_winActivity, List<FriendSale> one_win_list , String item_type) {
        this.context = one_winActivity;
        this.one_win_list = one_win_list;
        this.item_type = item_type;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_one_win_product, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        String a = Constants.IMAGES + one_win_list.get(position).getPimg();
        Glide.with(context).load(a).into(holder.product_image);

        holder.productName.setText(one_win_list.get(position).getProductName());
        holder.product_amt.setText(context.getResources().getString(R.string.rs_symbol) + one_win_list.get(position).getActual_price());
        holder.user_request_line.setText("Request new user "+one_win_list.get(position).getNew_users_atleast());


        SpannableString content = new SpannableString(context.getResources().getString(R.string.rs_symbol) + one_win_list.get(position).getDiscount_price());
        content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
        holder.product_dis_amt.setText(content);


        holder.layout_onw_win.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("product_name", one_win_list.get(position).getProductName());
                intent.putExtra("actual_price", one_win_list.get(position).getActual_price());
                intent.putExtra("discount_price", one_win_list.get(position).getDiscount_price());
                intent.putExtra("product_id", one_win_list.get(position).getProduct_id());
                intent.putExtra("product_Image", one_win_list.get(position).getPimg());
                intent.putExtra("vendor_id", one_win_list.get(position).getVendor_id());
                intent.putExtra("new_user_request", one_win_list.get(position).getNew_users_atleast());
                intent.putExtra("item_type", item_type);
                intent.putExtra("product_type", "friendsaleoneRs");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return one_win_list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView productName, user_request_line, product_amt, product_dis_amt;
        private ImageView product_image;
        private RelativeLayout layout_onw_win;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productName);
            user_request_line = itemView.findViewById(R.id.user_request_line);
            product_amt = itemView.findViewById(R.id.product_amt);
            product_dis_amt = itemView.findViewById(R.id.product_dis_amt);

            product_image = itemView.findViewById(R.id.product_image);

            layout_onw_win = itemView.findViewById(R.id.layout_onw_win);
        }
    }
}















