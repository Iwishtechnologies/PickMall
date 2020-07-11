package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.MyOederActitvity;
import tech.iwish.pickmall.activity.OrderDetailActivity;
import tech.iwish.pickmall.activity.ProductDetailsActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.OrderList;
import tech.iwish.pickmall.other.WishlistList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    private MyOederActitvity context ;
    private List<OrderList> orderLists ;
    HashMap<String,String>product= new HashMap<>();

    public  MyOrderAdapter(MyOederActitvity  context,List<OrderList>orderLists) {
        this.context= context;
        this.orderLists=orderLists;
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
        Log.e("name",orderLists.get(position).getProduct_name());
        holder.name.setText(orderLists.get(position).getProduct_name());
        Glide.with(context).load(Constants.IMAGES+orderLists.get(position).getPimg()).placeholder(R.drawable.male_icon).into(holder.image);
        holder.status.setText(orderLists.get(position).getOrder_status());
        if (orderLists.get(position).getOrder_status()=="CANCELLED")
        {
          holder.dot.setImageDrawable(context.getDrawable(R.drawable.red_dot));
        }
        holder.product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,OrderDetailActivity.class);
                intent.putExtra("ProductName",orderLists.get(position).getProduct_name());
                intent.putExtra("orderid",orderLists.get(position).getOrderid());
                intent.putExtra("address",orderLists.get(position).getDelhivery_address());
                intent.putExtra("orderdate",orderLists.get(position).getDatetime());
                intent.putExtra("orderStatus",orderLists.get(position).getOrder_status());
                intent.putExtra("oederAmount",orderLists.get(position).getOrder_amount());
                intent.putExtra("color",orderLists.get(position).getColor());
                intent.putExtra("size",orderLists.get(position).getSize());
                intent.putExtra("qty",orderLists.get(position).getQty());
                intent.putExtra("image",orderLists.get(position).getPimg());
                intent.putExtra("actual_price",orderLists.get(position).getActual_price());
                intent.putExtra("selling_price",orderLists.get(position).getDiscount_price());
                intent.putExtra("shipping_charge",orderLists.get(position).getShipping_charge());
                intent.putExtra("total_amount",orderLists.get(position).getOrder_amount());
                intent.putExtra("product_id",orderLists.get(position).getProduct_id());
                intent.putExtra("vendor_id",orderLists.get(position).getVendor_id());
                intent.putExtra("delivery_date",orderLists.get(position).getDelivery_date());
                intent.putExtra("ordertype",orderLists.get(position).getOrdertype());
                intent.putExtra("uniqueid",orderLists.get(position).getUnique_id());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return orderLists.size();
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
