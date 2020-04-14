package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.ProductDetailsActivity;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.WishlistList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    private Context context ;
    private List<WishlistList> wishlistLists ;

    public  MyOrderAdapter(Context context,List<WishlistList>wishlistLists) {
        this.context= context;
        this.wishlistLists=wishlistLists;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_design_myorder , null);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String a = "http://173.212.226.143:8086/img/" + wishlistLists.get(position).getPimg();
        Glide.with(context).load(a).into(holder.image);
        holder.name.setText(wishlistLists.get(position).getProductName());
        holder.product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(context , ProductDetailsActivity.class));
                intent.putExtra("product_name", wishlistLists.get(position).getProductName());
                intent.putExtra("actual_price", wishlistLists.get(position).getActual_price());
                intent.putExtra("discount_price", wishlistLists.get(position).getDiscount_price());
                intent.putExtra("product_id", wishlistLists.get(position).getProduct_id());
//                intent.putExtra("product_color", flashsalemainLists.get(position).getColors());
                intent.putExtra("product_Image", wishlistLists.get(position).getPimg());
                intent.putExtra("vendor_id", wishlistLists.get(position).getVendor_id());
                intent.putExtra("product_type", "flashSale");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wishlistLists.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        ShapedImageView image,dot;
        TextViewFont name,status;
        LinearLayout product;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            dot=itemView.findViewById(R.id.dot);
            name=itemView.findViewById(R.id.name);
            status=itemView.findViewById(R.id.status);
            product=itemView.findViewById(R.id.product);
        }
    }
}
