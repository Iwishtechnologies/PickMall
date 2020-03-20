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
import tech.iwish.pickmall.activity.FlashSaleProductactivity;
import tech.iwish.pickmall.activity.ProductDetailsActivity;
import tech.iwish.pickmall.other.FlashsalemainList;


public class FlashSaleAllProductAdapter extends RecyclerView.Adapter<FlashSaleAllProductAdapter.Viewholder> {

    private Context context ;
    private List<FlashsalemainList> flashsalemainLists ;

    public FlashSaleAllProductAdapter(FlashSaleProductactivity flashSaleProductactivity, List<FlashsalemainList> flashsalemainLists) {
        this.context = flashSaleProductactivity ;
        this.flashsalemainLists = flashsalemainLists ;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_flash_sale_all_product , null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;

    }



    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {


        holder.amount_flash.setText(context.getResources().getString(R.string.rs_symbol)+ flashsalemainLists.get(position).getActual_price());
        holder.dicount_price_flash.setText(context.getResources().getString(R.string.rs_symbol) + flashsalemainLists.get(position).getDiscount_price());

        String a = "http://173.212.226.143:8086/img/" + flashsalemainLists.get(position).getPimg();
        Glide.with(context).load(a).into(holder.image_flash_sale);
        holder.product_name_flash.setText(flashsalemainLists.get(position).getProductName());
        holder.percent_price.setText(flashsalemainLists.get(position).getDiscount_price_per());
        holder.flash_main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(context , ProductDetailsActivity.class));
                intent.putExtra("product_name", flashsalemainLists.get(position).getProductName());
                intent.putExtra("actual_price", flashsalemainLists.get(position).getActual_price());
                intent.putExtra("discount_price", flashsalemainLists.get(position).getDiscount_price());
                intent.putExtra("product_id", flashsalemainLists.get(position).getProduct_id());
//                intent.putExtra("product_color", flashsalemainLists.get(position).getColors());
                intent.putExtra("product_Image", flashsalemainLists.get(position).getPimg());
                intent.putExtra("product_type", "flashSale");
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return flashsalemainLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView product_name_flash ,amount_flash ,dicount_price_flash , percent_price;
        private ImageView image_flash_sale;
        private LinearLayout flash_main_layout ;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            product_name_flash = (TextView)itemView.findViewById(R.id.product_name_flash);
            amount_flash = (TextView)itemView.findViewById(R.id.amount_flash);
            dicount_price_flash = (TextView)itemView.findViewById(R.id.dicount_price_flash);
            percent_price = (TextView)itemView.findViewById(R.id.percent_price);
            image_flash_sale = (ImageView)itemView.findViewById(R.id.image_flash_sale);
            flash_main_layout = (LinearLayout)itemView.findViewById(R.id.flash_main_layout);

        }
    }
}






















