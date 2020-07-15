package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
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
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.ProductDetailsActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.other.CardCount;
import tech.iwish.pickmall.other.ProductList;
import tech.iwish.pickmall.other.ProductOfferlist;

public class AllOfferProductAdapter extends RecyclerView.Adapter<AllOfferProductAdapter.Viewholder> {
    private List<ProductOfferlist> productLists;
    private Context context;
    int aaa,extradiscount=0;
    private String prepaid;
    int Finalamount=0;

    public AllOfferProductAdapter(Context productActivity, List<ProductOfferlist> productListList, String prepaid) {
        this.context = productActivity;
        this.productLists = productListList;
        this.prepaid = prepaid;
    }

    @NonNull
    @Override
    public AllOfferProductAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_recycle, null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }




    @Override
    public void onBindViewHolder(@NonNull AllOfferProductAdapter.Viewholder holder, final int position) {

        String status = productLists.get(position).getStatus();

        if(productLists.get(position).getProduct_id().isEmpty()){

        }else {
            holder.shimmer.setShimmer(null);
            holder.shimmer.stopShimmer();


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
                Log.e("prepaid",prepaid);
                if(prepaid.equals("prepaid")){
                    holder.offer.setVisibility(View.VISIBLE);
                    holder.off.setText(aaa+"% off");
                    holder.per_dicount.setVisibility(View.GONE);
                }

                holder.per_dicount.setText(" " + String.valueOf(aaa) + "% OFF");
                holder.product_rationg.setRating(Float.parseFloat(productLists.get(position).getFakeRating()));

                holder.product_name.setText(productLists.get(position).getProductName());

                CardCount cardCount = new CardCount();
                cardCount.DicountPercent(productLists.get(position).getActual_price(), productLists.get(position).getDiscount_price());


                String a = Constants.IMAGES + productLists.get(position).getPimg();
                Glide.with(context).load(a).into(holder.product_img);

                Log.e("fggd",productLists.get(position).getExtraDiscount());
                if(productLists.get(position).getExtraDiscount().equals("null")){
                }else {
                    float actual= Float.parseFloat(productLists.get(position).getActual_price());
                    float per = actual/ 100 ;
                    float disprice = per *Float.parseFloat(productLists.get(position).getExtraDiscount());
                    Finalamount= (int) (actual-disprice);
                    productLists.get(position).setActual_price(String.valueOf(Finalamount));
                    holder.mainoffer.setVisibility(View.VISIBLE);
                    holder.mainoff.setText(productLists.get(position).getExtraDiscount()+"% off");
                    holder.amount.setText(context.getResources().getString(R.string.rs_symbol)+Finalamount);
                }

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
        LinearLayout offer,mainoffer;
        TextView off,mainoff;
        ShimmerFrameLayout shimmer;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            product_img = (ImageView) itemView.findViewById(R.id.product_img);
            product_layout = (LinearLayout) itemView.findViewById(R.id.product_layout);
            mainproduct = (LinearLayout) itemView.findViewById(R.id.mainproduct);
            amount = (TextView) itemView.findViewById(R.id.amount);
            discount_price = (TextView) itemView.findViewById(R.id.discount_price);
            product_name = (TextView) itemView.findViewById(R.id.product_name);
            per_dicount = (TextView) itemView.findViewById(R.id.per_dicount);
            off = (TextView) itemView.findViewById(R.id.off);
            mainoff = (TextView) itemView.findViewById(R.id.mainoff);
            offer = itemView.findViewById(R.id.offer);
            mainoffer = itemView.findViewById(R.id.mainoffer);
            product_rationg = (RatingBar) itemView.findViewById(R.id.product_rationg);
            shimmer = (ShimmerFrameLayout)itemView.findViewById(R.id.shimmer);

        }
    }
}
