package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.other.FriendSale;

public class GroupbyAdapter extends RecyclerView.Adapter<GroupbyAdapter.Viewholder> {

    private Context context;
    private List<FriendSale> friendSaleLists;

    public GroupbyAdapter(FragmentActivity activity, List<FriendSale> friendSaleLists) {
        this.context = activity;
        this.friendSaleLists = friendSaleLists;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_group_friend,null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        String a = Constants.IMAGES + friendSaleLists.get(position).getPimg();
        Glide.with(context).load(a).into(holder.image);

        holder.product_name.setText(friendSaleLists.get(position).getProductName());
        holder.product_amount.setText(context.getResources().getString(R.string.rs_symbol)+friendSaleLists.get(position).getActual_price());

        SpannableString content = new SpannableString(context.getResources().getString(R.string.rs_symbol) + friendSaleLists.get(position).getDiscount_price());
        content.setSpan(new StrikethroughSpan(), 0, content.length(), 0 );
        holder.product_diccount.setText(content);


    }

    @Override
    public int getItemCount() {
        return friendSaleLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView product_name,product_amount,product_diccount;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            product_name = itemView.findViewById(R.id.product_name);
            product_amount = itemView.findViewById(R.id.product_amount);
            product_diccount = itemView.findViewById(R.id.product_diccount);


        }
    }
}
