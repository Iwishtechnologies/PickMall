package tech.iwish.pickmall.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.Interface.CardQtyAmountRef;
import tech.iwish.pickmall.Interface.FlashsaleTimeIdInterface;
import tech.iwish.pickmall.Interface.RefreshCartAmountInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.CardActivity;
import tech.iwish.pickmall.activity.ProductDetailsActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.CardCount;
import tech.iwish.pickmall.other.ProductSizeColorList;
import tech.iwish.pickmall.session.Share_session;
import tech.iwish.pickmall.sqlconnection.MyhelperSql;

import static tech.iwish.pickmall.session.Share_session.USERMOBILE;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> implements FlashsaleTimeIdInterface {

    private ArrayList<HashMap<String, String>> cardData;
    private Context context;
    private RefreshCartAmountInterface refreshCartAmountInterface;
    private CardQtyAmountRef cardQtyAmountRef;
    private String qtychechker = null;
    private List<ProductSizeColorList> productSizeColorLists = new ArrayList<>();


    public CartAdapter(Context cardActivity, ArrayList<HashMap<String, String>> product_data, RefreshCartAmountInterface refreshCartAmountInterface, CardQtyAmountRef cardQtyAmountRef) {
        this.context = cardActivity;
        this.cardData = product_data;
        this.refreshCartAmountInterface = refreshCartAmountInterface;
        this.cardQtyAmountRef = cardQtyAmountRef;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_cart_design, null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        if (context instanceof CardActivity) {

            holder.stock_check();
            SpannableString content = new SpannableString(context.getResources().getString(R.string.rs_symbol) + cardData.get(position).get("PRODUCT_AMOUNT_DICOUNT"));
            content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
            holder.dicount_price.setText(content);
            String a = Constants.IMAGES + cardData.get(position).get("PRODUCT_IMAGE");
            Glide.with(context).load(a).into(holder.card_product_image);
            holder.cart_product_name.setText(cardData.get(position).get("PRODUCT_NAME"));
            holder.percent_price.setText(cardData.get(position).get("PRODUCT_DICOUNT_PERCEN"));
            holder.cart_product_act_amount.setText(context.getResources().getString(R.string.rs_symbol) + cardData.get(position).get("PRODUCT_AMOUNT"));
            holder.cart_product_size.setText(context.getResources().getString(R.string.size) + cardData.get(position).get("PRODUCT_SIZE"));
            if (qtychechker == null) {
                holder.product_qty.setText(cardData.get(position).get("PRODUCT_QTY"));
            }
            holder.remove_button_layout.setVisibility(View.VISIBLE);
            holder.qty_layout.setVisibility(View.VISIBLE);


        } else {


            SpannableString content = new SpannableString(context.getResources().getString(R.string.rs_symbol) + cardData.get(position).get("PRODUCT_AMOUNT_DICOUNT"));
            content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
            holder.dicount_price.setText(content);
            String a = Constants.IMAGES + cardData.get(position).get("PRODUCT_IMAGE");
            Glide.with(context).load(a).into(holder.card_product_image);
            holder.cart_product_name.setText(cardData.get(position).get("PRODUCT_NAME"));
            holder.cart_product_act_amount.setText(context.getResources().getString(R.string.rs_symbol) + cardData.get(position).get("PRODUCT_AMOUNT"));
            holder.cart_product_size.setText(context.getResources().getString(R.string.size) + cardData.get(position).get("PRODUCT_SIZE"));
            holder.product_qty.setText(cardData.get(position).get("PRODUCT_QTY"));
            holder.remove_button_layout.setVisibility(View.GONE);
            holder.qty_layout.setVisibility(View.GONE);
//            holder.percent_price.setText(cardData.get(position).get("PRODUCT_DICOUNT_PERCEN"));
            float actual = Float.valueOf(cardData.get(position).get("PRODUCT_AMOUNT"));
            float dis = Float.valueOf(cardData.get(position).get("PRODUCT_AMOUNT_DICOUNT"));
            float disco = dis - actual;
            float fin = disco / dis * 100;
            int aa = (int) fin;
//            int discount=Integer.parseInt(cardData.get(position).get("PRODUCT_AMOUNT"))/Integer.valueOf(cardData.get(position).get("PRODUCT_AMOUNT_DICOUNT"));
            holder.percent_price.setText(String.valueOf(aa));


        }

        if (cardData.get(position).get("PRODUCT_TYPE").equals("flashsale")) {
            holder.timesetcountdown.setVisibility(View.VISIBLE);
//            new CountdownTime();
        } else {
            holder.timesetcountdown.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return cardData.size();
    }

    @Override
    public void flashsaleId(String saleid) {

    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener, AdapterView.OnItemSelectedListener {

        private ImageView card_product_image;
        private TextView cart_product_name, cart_product_act_amount, cart_product_size, product_qty, timesetcountdown;
        private TextView card_remove_product, dicount_price, wishlist_btn, percent_price;
        private SQLiteDatabase sqLiteDatabase;
        private MyhelperSql myhelperSql;
        private LinearLayout remove_button_layout, qty_layout, check;
        private Spinner qty_spinner;
        private String firstTimeCheck;
        private LinearLayout mainlayout_card;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            card_product_image = (ImageView) itemView.findViewById(R.id.card_product_image);
            cart_product_name = (TextView) itemView.findViewById(R.id.cart_product_name);
            timesetcountdown = (TextView) itemView.findViewById(R.id.timesetcountdown);
            cart_product_act_amount = (TextView) itemView.findViewById(R.id.cart_product_act_amount);
            cart_product_size = (TextView) itemView.findViewById(R.id.cart_product_size);
            card_remove_product = (TextView) itemView.findViewById(R.id.card_remove_product);
            product_qty = (TextView) itemView.findViewById(R.id.product_qty);
            dicount_price = (TextView) itemView.findViewById(R.id.dicount_price);
            wishlist_btn = (TextView) itemView.findViewById(R.id.wishlist_btn);
            percent_price = (TextView) itemView.findViewById(R.id.percent_price);
            remove_button_layout = (LinearLayout) itemView.findViewById(R.id.remove_button_layout);
            qty_layout = (LinearLayout) itemView.findViewById(R.id.qty_layout);
            mainlayout_card = (LinearLayout) itemView.findViewById(R.id.mainlayout_card);
            qty_spinner = (Spinner) itemView.findViewById(R.id.qty_spinner);

            card_remove_product.setOnClickListener(this);
            myhelperSql = new MyhelperSql(context);
            sqLiteDatabase = myhelperSql.getWritableDatabase();

            wishlist_btn.setOnClickListener(this);

            qty_layout.setOnClickListener(this);
            mainlayout_card.setOnClickListener(this);


            String[] mobile = new String[]{
                    "1",
                    "2",
                    "3",
                    "more"
            };
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    context, R.layout.textview_for_spinner, mobile);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.textview_for_spinner);
            qty_spinner.setAdapter(spinnerArrayAdapter);
            qty_spinner.setOnItemSelectedListener(this);


        }


        private void updateqty(String value) {

            qtychechker = "dvdfvdfvdfbndf";
            sqLiteDatabase.execSQL("UPDATE PRODUCT_CARD SET PRODUCT_QTY = " + value + " WHERE _id= " + cardData.get(getAdapterPosition()).get("ID") + "");
            cardQtyAmountRef.cardqtyAmountref();
            product_qty.setText(value);
            notifyDataSetChanged();

        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.card_remove_product:

                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            sqLiteDatabase.delete("PRODUCT_CARD", "_id = ?", new String[]{cardData.get(getAdapterPosition()).get("ID")});
                            cardData.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            refreshCartAmountInterface.Amountrefreshcart();
                        }
                    });
                    builder.setMessage("Are you sure you want to remove the item ?");
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            builder.setCancelable(true);
                        }
                    });
                    builder.show();
                    break;
                case R.id.wishlist_btn:
                    productwishlist();
                    break;
                case R.id.qty_layout:
                    qty_spinner.performClick();
                    this.firstTimeCheck = "csdcdcdcsdc";
                    break;
                case R.id.mainlayout_card:
                    productshow();
                    break;
            }
        }

        private void productwishlist() {

            Share_session share_session = new Share_session(context);
            Map data = share_session.Fetchdata();
            CardCount cardCount = new CardCount();
            cardCount.save_wishlist(cardData.get(getAdapterPosition()).get("PRODUCT_TYPE"), cardData.get(getAdapterPosition()).get("PRODUCT_ID"), data.get(USERMOBILE).toString());
            sqLiteDatabase.delete("PRODUCT_CARD", "_id = ?", new String[]{cardData.get(getAdapterPosition()).get("ID")});
            cardData.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            refreshCartAmountInterface.Amountrefreshcart();
            Toast toast = Toast.makeText(context, "", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setText("Move to Save For Later successfully");
            View view = LayoutInflater.from(context).inflate(R.layout.custom_toast, null);
            toast.setView(view);
            toast.show();


        }


        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            ((TextView) view).setText(null);

            if (firstTimeCheck != null) {
//                if (!product_qty.getText().toString().equals(adapterView.getItemAtPosition(i).toString())) {
                if (i != 3) {
//                        product_qty.setText(adapterView.getItemAtPosition(i).toString());
                    updateqty(adapterView.getItemAtPosition(i).toString());
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    final View view1 = LayoutInflater.from(context).inflate(R.layout.custom_qty_design, null);
                    builder.setView(view1);
                    final EditText qty_edittext = (EditText) view1.findViewById(R.id.qty_edittext);
                    LinearLayout cancletextview = (LinearLayout) view1.findViewById(R.id.cancletextview);
                    LinearLayout savetextview = (LinearLayout) view1.findViewById(R.id.savetextview);
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    cancletextview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    savetextview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!qty_edittext.getText().toString().isEmpty()) {
                                updateqty(qty_edittext.getText().toString().trim());
                                alertDialog.dismiss();

                            } else {
                                alertDialog.dismiss();
                            }
                        }
                    });

                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }


        private void productshow() {

            String type = null;

            switch (cardData.get(getAdapterPosition()).get("PRODUCT_TYPE")) {
                case "allProduct":
                    type = "allProduct";
                    break;
                case "flashSale":
                    type = "flashSale";
                    break;
                case "frienddeal":
                    type = "friendsaleoneRs";
                    break;
            }

            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("product_name", cardData.get(getAdapterPosition()).get("PRODUCT_NAME"));
            intent.putExtra("actual_price", cardData.get(getAdapterPosition()).get("PRODUCT_AMOUNT"));
            intent.putExtra("discount_price", cardData.get(getAdapterPosition()).get("PRODUCT_AMOUNT_DICOUNT"));
            intent.putExtra("product_id", cardData.get(getAdapterPosition()).get("PRODUCT_ID"));
            intent.putExtra("product_Image", cardData.get(getAdapterPosition()).get("PRODUCT_IMAGE"));
            intent.putExtra("product_color", cardData.get(getAdapterPosition()).get("PRODUCT_COLOR"));
            intent.putExtra("product_type", type);
            intent.putExtra("vendor_id", cardData.get(getAdapterPosition()).get("VENDOR_ID"));
            intent.putExtra("discount_price_per", cardData.get(getAdapterPosition()).get("PRODUCT_DICOUNT_PERCEN"));
            intent.putExtra("gst", cardData.get(getAdapterPosition()).get("GST"));
            intent.putExtra("vendor_id", cardData.get(getAdapterPosition()).get("VENDOR_ID"));

            context.startActivity(intent);

        }

        protected void stock_check() {

            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("product_id", cardData.get(getAdapterPosition()).get("PRODUCT_ID"));
                jsonObject.put("product_size", cardData.get(getAdapterPosition()).get("PRODUCT_SIZE"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());
            Request request = new Request.Builder().post(body)
                    .url(Constants.PRODUCT_STOCK_CHECHK)
                    .build();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        Log.e("result", result);
                        JsonHelper jsonHelper = new JsonHelper(result);
                        if (jsonHelper.isValidJson()) {
                            String responses = jsonHelper.GetResult("response");
                            if (responses.equals("TRUE")) {
                                JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonHelper.setChildjsonObj(jsonArray, i);
                                    productSizeColorLists.add(new ProductSizeColorList(jsonHelper.GetResult("sno"), jsonHelper.GetResult("product_id"), jsonHelper.GetResult("imgname"), jsonHelper.GetResult("size"), jsonHelper.GetResult("color"), jsonHelper.GetResult("qty"), jsonHelper.GetResult("count_size")));
                                }
                            }
                        }
                    }

                }
            });
        }

    }


}




















