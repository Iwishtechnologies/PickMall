package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.ProductDetailsActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.other.CardCount;
import tech.iwish.pickmall.other.ProductList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Viewholder> {

    private List<ProductList> productLists;
    private Context context;
    int aaa;
    private String prepaid;

    public ProductAdapter(Context productActivity, List<ProductList> productListList, String prepaid) {
        this.context = productActivity;
        this.productLists = productListList;
        this.prepaid = prepaid;
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
//            holder.product_rationg.setRating((float) 4.5);
            Drawable drawable = holder.product_rationg.getProgressDrawable();
            switch (productLists.get(position).getFakeRating()) {

                case "0.1":
                case "0.2":
                case "0.3":
                case "0.4":
                case "0.5":
                case "0.6":
                case "0.7":
                case "0.8":
                case "0.9":
                case "1":
                case "1.1":
                case "1.2":
                case "1.3":
                case "1..4":
                case "1.5":
                case "1.6":
                case "1.7":
                case "1.8":
                case "1.9":
                case "2":
                case "2.1":
                case "2.2":
                case "2.3":
                case "2.4":
                        drawable.setColorFilter(context.getColor(R.color.progress_rating_red_color), PorterDuff.Mode.SRC_ATOP);
                    break;
                case "2.5":
                case "2.6":
                case "2.7":
                case "2.8":
                case "2.9":
                case "3":
                case "3.1":
                case "3.2":
                case "3.3":
                case "3.4":
                case "3.5":
                case "3.6":
                case "3.7":
                case "3.8":
                case "3.9":
                        drawable.setColorFilter(context.getColor(R.color.progress_rating_yellow_color), PorterDuff.Mode.SRC_ATOP);
                    break;
                case "4":
                case "4.1":
                case "4.2":
                case "4.3":
                case "4.4":
                case "4.5":
                case "4.6":
                case "4.7":
                case "4.8":
                case "4.9":
                case "5":
                        drawable.setColorFilter(context.getColor(R.color.progress_rating_green_color), PorterDuff.Mode.SRC_ATOP);
                    break;
            }

            holder.amount.setText(context.getResources().getString(R.string.rs_symbol) + productLists.get(position).getActual_price());
            SpannableString content = new SpannableString(context.getResources().getString(R.string.rs_symbol) + productLists.get(position).getDiscount_price());
            content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
            holder.discount_price.setText(content);

            float dicountsAmt = Float.parseFloat(productLists.get(position).getActual_price());
            float mrp = Float.parseFloat(productLists.get(position).getDiscount_price());

            float sub = mrp - dicountsAmt;
            float div = sub / mrp;
            aaa = (int) (div * 100);

            holder.per_dicount.setText(" " + String.valueOf(aaa) + "% OFF");
            holder.product_rationg.setRating(Float.parseFloat(productLists.get(position).getFakeRating()));

            holder.product_name.setText(productLists.get(position).getProductName());

            CardCount cardCount = new CardCount();
            cardCount.DicountPercent(productLists.get(position).getActual_price(), productLists.get(position).getDiscount_price());


            String a = Constants.IMAGES + productLists.get(position).getPimg();
            Glide.with(context).load(a).into(holder.product_img);

            holder.product_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, ProductDetailsActivity.class);
                    intent.putExtra("product_name", productLists.get(position).getProductName());
                    intent.putExtra("actual_price", productLists.get(position).getActual_price());
                    intent.putExtra("discount_price", productLists.get(position).getDiscount_price());
                    intent.putExtra("product_id", productLists.get(position).getProduct_id());
                    intent.putExtra("product_Image", productLists.get(position).getPimg());
                    intent.putExtra("vendor_id", productLists.get(position).getVendor_id());
                    intent.putExtra("gst", productLists.get(position).getGst());
                    intent.putExtra("prepaid", prepaid);
                    intent.putExtra("product_type", "allProduct");
                    context.startActivity(intent);
                }
            });
        } else {
            holder.mainproduct.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {

        return productLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView product_img;
        private LinearLayout product_layout, mainproduct;
        private TextView amount, discount_price, product_name, per_dicount;
        private RatingBar product_rationg;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            product_img = (ImageView) itemView.findViewById(R.id.product_img);
            product_layout = (LinearLayout) itemView.findViewById(R.id.product_layout);
            mainproduct = (LinearLayout) itemView.findViewById(R.id.mainproduct);
            amount = (TextView) itemView.findViewById(R.id.amount);
            discount_price = (TextView) itemView.findViewById(R.id.discount_price);
            product_name = (TextView) itemView.findViewById(R.id.product_name);
            per_dicount = (TextView) itemView.findViewById(R.id.per_dicount);
            product_rationg = (RatingBar) itemView.findViewById(R.id.product_rationg);
        }
    }


}
















