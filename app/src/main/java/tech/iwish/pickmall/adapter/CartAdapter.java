package tech.iwish.pickmall.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tech.iwish.pickmall.Interface.CardQtyAmountRef;
import tech.iwish.pickmall.Interface.FlashsaleTimeIdInterface;
import tech.iwish.pickmall.Interface.RefreshCartAmountInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.CardActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.countdowntime.CountdownTime;
import tech.iwish.pickmall.other.CardCount;
import tech.iwish.pickmall.session.Share_session;
import tech.iwish.pickmall.sqlconnection.MyhelperSql;

import static tech.iwish.pickmall.session.Share_session.USERMOBILE;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> implements FlashsaleTimeIdInterface {

    private ArrayList<HashMap<String, String>> cardData;
    private Context context;
    private RefreshCartAmountInterface refreshCartAmountInterface;
    private CardQtyAmountRef cardQtyAmountRef;
    private String qtychechker = null ;


    public CartAdapter(Context cardActivity, ArrayList<HashMap<String, String>> product_data, RefreshCartAmountInterface refreshCartAmountInterface , CardQtyAmountRef cardQtyAmountRef) {
        this.context = cardActivity;
        this.cardData = product_data;
        this.refreshCartAmountInterface = refreshCartAmountInterface;
        this.cardQtyAmountRef = cardQtyAmountRef ;

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


            SpannableString content = new SpannableString(context.getResources().getString(R.string.rs_symbol) + cardData.get(position).get("PRODUCT_AMOUNT_DICOUNT"));
            content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
            holder.dicount_price.setText(content);
            String a = Constants.IMAGES + cardData.get(position).get("PRODUCT_IMAGE");
            Glide.with(context).load(a).into(holder.card_product_image);
            holder.cart_product_name.setText(cardData.get(position).get("PRODUCT_NAME"));

//            holder.percent_price.setText(cardData.get(position).get("PRODUCT_NAME"));

            holder.cart_product_act_amount.setText(context.getResources().getString(R.string.rs_symbol) + cardData.get(position).get("PRODUCT_AMOUNT"));
            holder.cart_product_size.setText(context.getResources().getString(R.string.size) + cardData.get(position).get("PRODUCT_SIZE"));
            if(qtychechker == null){
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


        }

        if(cardData.get(position).get("PRODUCT_TYPE").equals("flashsale")){
            holder.timesetcountdown.setVisibility(View.VISIBLE);
            new CountdownTime(context,holder.timesetcountdown,null , this).Flashsaletimeset();
        }else {
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
        private TextView cart_product_name, cart_product_act_amount, cart_product_size, product_qty , timesetcountdown;
        private TextView card_remove_product, dicount_price, wishlist_btn , percent_price;
        private SQLiteDatabase sqLiteDatabase;
        private MyhelperSql myhelperSql;
        private LinearLayout remove_button_layout, qty_layout, check;
        private Spinner qty_spinner;
        private PopupWindow popupWindow;
        private String firstTimeCheck;


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
            qty_spinner = (Spinner) itemView.findViewById(R.id.qty_spinner);

            card_remove_product.setOnClickListener(this);
            myhelperSql = new MyhelperSql(context);
            sqLiteDatabase = myhelperSql.getWritableDatabase();

            wishlist_btn.setOnClickListener(this);

            qty_layout.setOnClickListener(this);


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
            sqLiteDatabase.execSQL("UPDATE PRODUCT_CARD SET PRODUCT_QTY = "+value +" WHERE _id= "+cardData.get(getAdapterPosition()).get("ID")+"");
            cardQtyAmountRef.cardqtyAmountref();
            product_qty.setText(value);
            notifyDataSetChanged();

        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.card_remove_product:
                    sqLiteDatabase.delete("PRODUCT_CARD", "_id = ?", new String[]{cardData.get(getAdapterPosition()).get("ID")});
                    cardData.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    refreshCartAmountInterface.Amountrefreshcart();
                    break;
                case R.id.wishlist_btn:
                    productwishlist();
                    break;
                case R.id.qty_layout:
                    qty_spinner.performClick();
                    this.firstTimeCheck = "csdcdcdcsdc";
                    break;
            }
        }

        private void productwishlist() {
            Share_session share_session = new Share_session(context);
            Map data = share_session.Fetchdata();
            CardCount cardCount = new CardCount();
            cardCount.save_wishlist(cardData.get(getAdapterPosition()).get("PRODUCT_TYPE"), cardData.get(getAdapterPosition()).get("PRODUCT_ID"), data.get(USERMOBILE).toString());
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    }
//                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }


    }
}




















