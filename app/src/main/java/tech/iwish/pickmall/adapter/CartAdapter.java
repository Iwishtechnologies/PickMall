package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import tech.iwish.pickmall.Interface.RefreshCartAmountInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.CardActivity;
import tech.iwish.pickmall.sqlconnection.MyhelperSql;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> {

    private ArrayList<HashMap<String, String>> cardData;
    private Context context;
    private RefreshCartAmountInterface refreshCartAmountInterface;

    public CartAdapter(CardActivity cardActivity, ArrayList<HashMap<String, String>> product_data , RefreshCartAmountInterface refreshCartAmountInterface) {
        this.context = cardActivity;
        this.cardData = product_data;
        this.refreshCartAmountInterface = refreshCartAmountInterface;
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
//        Toast.makeText(context, ""+cardData.get(position).get("PRODUCT_ID"), Toast.LENGTH_SHORT).show();
//        Toast.makeText(context, ""+cardData.get(position).get("PRODUCT_NAME"), Toast.LENGTH_SHORT).show();
//        Toast.makeText(context, ""+cardData.get(position).get("PRODUCT_QTY"), Toast.LENGTH_SHORT).show();

        String a = "http://173.212.226.143:8086/img/" + cardData.get(position).get("PRODUCT_IMAGE");
        Glide.with(context).load(a).into(holder.card_product_image);
        holder.cart_product_name.setText(cardData.get(position).get("PRODUCT_NAME"));
        holder.cart_product_act_amount.setText(context.getResources().getString(R.string.rs_symbol) + cardData.get(position).get("PRODUCT_AMOUNT"));
        holder.cart_product_size.setText(context.getResources().getString(R.string.size) + cardData.get(position).get("PRODUCT_SIZE"));
        holder.product_qty.setText(context.getResources().getString(R.string.qty) + cardData.get(position).get("PRODUCT_QTY"));

    }

    @Override
    public int getItemCount() {
        return cardData.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView card_product_image;
        private TextView cart_product_name, cart_product_act_amount, cart_product_size,product_qty;
        private TextView card_remove_product;
        private SQLiteDatabase sqLiteDatabase;
        private MyhelperSql myhelperSql;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            card_product_image = (ImageView) itemView.findViewById(R.id.card_product_image);
            cart_product_name = (TextView) itemView.findViewById(R.id.cart_product_name);
            cart_product_act_amount = (TextView) itemView.findViewById(R.id.cart_product_act_amount);
            cart_product_size = (TextView) itemView.findViewById(R.id.cart_product_size);
            card_remove_product = (TextView) itemView.findViewById(R.id.card_remove_product);
            product_qty = (TextView) itemView.findViewById(R.id.product_qty);

            card_remove_product.setOnClickListener(this);
            myhelperSql = new MyhelperSql(context);
            sqLiteDatabase = myhelperSql.getWritableDatabase();

        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.card_remove_product:
                    sqLiteDatabase.delete("PRODUCT_CARD","_id = ?",new String[]{cardData.get(getAdapterPosition()).get("ID")});
                    cardData.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    refreshCartAmountInterface.Amountrefreshcart();
                    break;
            }
        }
    }
}




















