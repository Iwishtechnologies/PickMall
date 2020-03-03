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

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.ProductActivity;
import tech.iwish.pickmall.activity.ProductDetailsActivity;
import tech.iwish.pickmall.other.ProductList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Viewholder> {

    private List<ProductList> productLists;
    private Context context;

    public ProductAdapter(ProductActivity productActivity, List<ProductList> productListList) {
        this.context = productActivity;
        this.productLists = productListList;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_recycle, null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        String status = productLists.get(position).getStatus();
        if (status.equals("TRUE")) {

            holder.amount.setText(productLists.get(position).getActual_price());
            String a = "http://173.212.226.143:8086/img/" + productLists.get(position).getPimg();
            Glide.with(context).load(a).into(holder.product_img);
            holder.product_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProductDetailsActivity.class);
                    intent.putExtra("product_name", productLists.get(position).getProductName());
                    intent.putExtra("actual_price", productLists.get(position).getActual_price());
                    intent.putExtra("discount_price", productLists.get(position).getDiscount_price());
                    intent.putExtra("product_id", productLists.get(position).getProduct_id());
                    intent.putExtra("product_color", productLists.get(position).getColors());
                    intent.putExtra("product_Image", productLists.get(position).getPimg());
                    context.startActivity(intent);
                }
            });
        }else{
            holder.mainproduct.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {

        return productLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView product_img;
        private LinearLayout product_layout ,mainproduct;
        private TextView amount;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            product_img = (ImageView) itemView.findViewById(R.id.product_img);
            product_layout = (LinearLayout) itemView.findViewById(R.id.product_layout);
            mainproduct = (LinearLayout) itemView.findViewById(R.id.mainproduct);
            amount = (TextView)itemView.findViewById(R.id.amount);
        }
    }
}
















