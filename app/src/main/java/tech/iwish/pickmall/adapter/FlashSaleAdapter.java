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

import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.FlashSaleProductactivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.other.ProductList;

public class FlashSaleAdapter extends RecyclerView.Adapter<FlashSaleAdapter.Viewholder> {

//    private List<FlashsalemainList> flashsalemainLists ;
    private Context context;
    private List<ProductList> productListList ;

    public FlashSaleAdapter(Context context, List<ProductList> productListList) {
        this.context = context;
        this.productListList = productListList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_flash_sale_main , null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        String a = Constants.IMAGES + productListList.get(position).getPimg();
        Glide.with(context).load(a).into(holder.image_flash_sale);

        holder.actual_price_flash.setText(context.getResources().getString(R.string.rs_symbol)+ productListList.get(position).getActual_price());

        SpannableString content = new SpannableString(context.getResources().getString(R.string.rs_symbol)+productListList.get(position).getDiscount_price());
        content.setSpan(new StrikethroughSpan(), 0, content.length(), 0 );
        holder.dicount_price_flash.setText(content);
        float actual = Float.valueOf(productListList.get(position).getActual_price());
        float dis = Float.valueOf( productListList.get(position).getDiscount_price());
        float disco = dis - actual;
        float fin = disco / dis * 100;
        int aa = (int) fin;

        holder.off.setText(aa+"% off");
        holder.flash_product_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context , FlashSaleProductactivity.class));
            }
        });



    }

    @Override
    public int getItemCount() {
        return productListList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView image_flash_sale ;
        private TextView actual_price_flash , dicount_price_flash,off;
        private RelativeLayout flash_product_linear ;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            image_flash_sale = (ImageView)itemView.findViewById(R.id.image_flash_sale);
            actual_price_flash = (TextView)itemView.findViewById(R.id.actual_price_flash);
            dicount_price_flash = (TextView)itemView.findViewById(R.id.dicount_price_flash);
            off = (TextView)itemView.findViewById(R.id.off);
            flash_product_linear = (RelativeLayout) itemView.findViewById(R.id.flash_product_linear);
        }
    }
}
