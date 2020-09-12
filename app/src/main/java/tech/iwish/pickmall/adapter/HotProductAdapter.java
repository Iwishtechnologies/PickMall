package tech.iwish.pickmall.adapter;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.iwish.pickmall.Interface.Progressbar_product_inteface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.RetrofitInterface.FrontShareProductImageInterface;
import tech.iwish.pickmall.RetrofitModel.FrontProductShareList;
import tech.iwish.pickmall.activity.ProductDetailsActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.other.CardCount;
import tech.iwish.pickmall.other.HotproductList;

public class HotProductAdapter extends RecyclerView.Adapter<HotProductAdapter.Viewholder> {

    List<HotproductList> productLists;
    Context context;
    int aaa, extradiscount = 0;
    private String prepaid = "noprepaid";
    Progressbar_product_inteface progressbar_product_inteface;

    public HotProductAdapter(List<HotproductList> hotproductLists ,  Progressbar_product_inteface progressbar_product_inteface) {
        this.productLists = hotproductLists;
        this.progressbar_product_inteface = progressbar_product_inteface;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product_recycle, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        String status = productLists.get(position).getStatus();
        if (productLists.get(position).getProduct_id().isEmpty()) {

        } else {
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
                        drawable.setColorFilter(context.getResources().getColor(R.color.progress_rating_red_color), PorterDuff.Mode.SRC_ATOP);
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
                        drawable.setColorFilter(context.getResources().getColor(R.color.progress_rating_yellow_color), PorterDuff.Mode.SRC_ATOP);
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
                        drawable.setColorFilter(context.getResources().getColor(R.color.progress_rating_green_color), PorterDuff.Mode.SRC_ATOP);
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
//                Log.e("prepaid", prepaid);

                if (prepaid != null) {
                    if (prepaid.equals("prepaid")) {
                        holder.offer.setVisibility(View.VISIBLE);
                        holder.off.setText(aaa + "% off");
                        holder.per_dicount.setVisibility(View.GONE);
                    }
                } else {
                    Log.e("pr", "null");
                }

                holder.per_dicount.setText(" " + String.valueOf(aaa) + "% OFF");
                holder.product_rationg.setRating(Float.parseFloat(productLists.get(position).getFakeRating()));

                holder.product_name.setText(productLists.get(position).getProductName());

                CardCount cardCount = new CardCount();
                cardCount.DicountPercent(productLists.get(position).getActual_price(), productLists.get(position).getDiscount_price());

                String a = Constants.IMAGES + productLists.get(position).getPimg();
                Glide.with(context).load(a).into(holder.product_img);

                if (productLists.get(position).getHot_product().equals("True")) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 0, 0, 0);
                    final RelativeLayout.LayoutParams pro = (RelativeLayout.LayoutParams) holder.product_img.getLayoutParams();
                    pro.setMargins(0, 0, 0, 0);
                    holder.product_img.setLayoutParams(pro);
                    holder.hotlayout.setLayoutParams(layoutParams);
                    holder.product_name.setVisibility(View.GONE);
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

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView product_img;
        private LinearLayout product_layout, mainproduct;
        private TextView amount, discount_price, product_name, per_dicount;
        private RatingBar product_rationg;
        LinearLayout offer, mainoffer;
        RelativeLayout hotlayout;
        TextView off, mainoff;
        ShimmerFrameLayout shimmer;

        FrontProductShareList list;
        ArrayList<Uri> imageUriArray = new ArrayList<>();

        //        ******************************************************
        ImageView whatsapp_layout;

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
            hotlayout = itemView.findViewById(R.id.hotlayout);
            product_rationg = (RatingBar) itemView.findViewById(R.id.product_rationg);
            shimmer = (ShimmerFrameLayout) itemView.findViewById(R.id.shimmer);

//            **********************
            whatsapp_layout = itemView.findViewById(R.id.whatsapp_layout);


            whatsapp_layout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.whatsapp_layout) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.resell_dialog_amt, null);
                builder.setView(view);
                ImageView cross_img = view.findViewById(R.id.cross_img);
                EditText EditTextAmt = view.findViewById(R.id.EditTextAmt);
                TextView submit = view.findViewById(R.id.submit);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                cross_img.setOnClickListener(v1 -> {
                    alertDialog.dismiss();
                });
                submit.setOnClickListener(v1 -> {
                    int val = 0;


                    if (!EditTextAmt.getText().toString().trim().isEmpty())
                        val = Integer.parseInt(EditTextAmt.getText().toString().trim());
                    if (EditTextAmt.getText().toString().trim().isEmpty())
                        Toast.makeText(context, "Pleace Enter Amount", Toast.LENGTH_SHORT).show();
                    else if (val < Integer.parseInt(productLists.get(getAdapterPosition()).getActual_price()))
                        Toast.makeText(context, "Pleace Valid Amount", Toast.LENGTH_SHORT).show();
                    else {

                        progressbar_product_inteface.progressbar_product_intefaces("PROGRESSBAR_START");
                        alertDialog.dismiss();

                        Call<FrontProductShareList> frontProductShareListCall = FrontShareProductImageInterface.ProductFrontShare().getData(productLists.get(getAdapterPosition()).getProduct_id());
                        frontProductShareListCall.enqueue(new Callback<FrontProductShareList>() {
                            @Override
                            public void onResponse(Call<FrontProductShareList> call, Response<FrontProductShareList> response) {

                                list = response.body();
                                assert list != null;
                                if (list.getResponse().equals("TRUE")) {

                                    String productDes = "", productoverview = "";

                                    if (list.getProductDescription() != null) {
                                        for (int i = 0; i < list.getProductDescription().size(); i++) {
                                            productDes += list.getProductDescription().get(i).getDescriptionTitle() + ": " + list.getProductDescription().get(i).getDescriptionData() + "\n";
                                        }
                                    }
                                    if (list.getProductOverview() != null) {
//                                        Toast.makeText(context, "" + list.getProductOverview().size(), Toast.LENGTH_SHORT).show();
                                        for (int i = 0; i < list.getProductOverview().size(); i++) {
                                            productoverview += list.getProductOverview().get(i).getTitle() + ": " + list.getProductOverview().get(i).getOverview() + "\n\n";
                                        }
                                    }

                                    if (list.getProductImage() != null) {
                                        for (int j = 0; j < list.getProductImage().size(); j++) {
                                            int finalJ = j;
                                            String finalProductDes = productDes;
                                            String finalProductoverview = productoverview;
                                            Log.e("onResponse: ", productLists.get(getAdapterPosition()).getProduct_id());
                                            Glide.with(context).asBitmap().load(Constants.IMAGES + list.getProductImage().get(j).getImage()).into(new CustomTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                    mulitplyImageshare(resource);
                                                    if (finalJ == list.getProductImage().size() - 1) {
                                                        String allproduct = "*" + "Product Description" + "*" + "\n" + finalProductDes + "\n\n" + "*" + "Amount" + "*" + "\n" + EditTextAmt.getText().toString() + "\n\n" + "*" + "Product Overview" + "*" + "\n" + finalProductoverview + "\n\n";
                                                        shareintent(allproduct);
                                                    }
                                                }

                                                @Override
                                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                                }
                                            });

                                        }
                                    }


                                }

                            }

                            @Override
                            public void onFailure(Call<FrontProductShareList> call, Throwable t) {

                            }
                        });
                    }
                });

            }

        }

        private void mulitplyImageshare(Bitmap bmp) {
            Uri bmpUri = getLocalBitmapUri(bmp); // see previous remote images section
            imageUriArray.add(bmpUri);
        }

        private void shareintent(String msg) {

            String productName = productLists.get(getAdapterPosition()).getProductName();

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
//            intent.putExtra(Intent.EXTRA_TEXT, "*" + productName + "*" + "\n\n" + msg);
            intent.setType("text/html");
            intent.setType("image/jpeg");
            intent.setPackage("com.whatsapp");
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUriArray);
            context.startActivity(intent);
            imageUriArray.clear();
            Toast.makeText(context, "Copy to Clipboard", Toast.LENGTH_SHORT).show();
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", "*" + productName + "*" + "\n\n" + msg);
            clipboard.setPrimaryClip(clip);
            progressbar_product_inteface.progressbar_product_intefaces("PROGRESSBAR_STOP");

        }

        private Uri getLocalBitmapUri(Bitmap bmp) {
            Uri bmpUri = null;
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bmpUri = Uri.fromFile(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return bmpUri;
        }


    }
}
