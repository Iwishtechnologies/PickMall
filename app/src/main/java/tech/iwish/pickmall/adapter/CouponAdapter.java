package tech.iwish.pickmall.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.CouponList;

import static android.content.Context.CLIPBOARD_SERVICE;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {
    private Context context ;
    private List<CouponList> couponLists ;

    public  CouponAdapter(Context context,List<CouponList>couponLists) {
        this.context= context;
        this.couponLists=couponLists;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_coupen_design , null);
        ViewHolder  viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.shimmer.stopShimmer();
        holder.shimmer.setShimmer(null);
        holder.title.setText(couponLists.get(position).getTitle());
        holder.subtitle.setText(couponLists.get(position).getSubtitle());
        holder.prize.setText(couponLists.get(position). getPrize());
        holder.validity.setText(couponLists.get(position).getValidity());
        holder.terms.setText(couponLists.get(position).getTermscondition());
        Glide.with(context).load(Constants.IMAGES+couponLists.get(position).getIcon()).into(holder.icon);
        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager)context.getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", couponLists.get(position).getCode());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Code Copied", Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    public int getItemCount() {
        return couponLists.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        TextViewFont title,subtitle,prize,validity,terms;
        ShapedImageView icon;
        ImageView copy;
        ShimmerFrameLayout shimmer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            subtitle=itemView.findViewById(R.id.subtitle);
            prize=itemView.findViewById(R.id.prize);
            validity=itemView.findViewById(R.id.validity);
            icon=itemView.findViewById(R.id.icon);
            copy=itemView.findViewById(R.id.copy);
            terms=itemView.findViewById(R.id.terms);
            shimmer=itemView.findViewById(R.id.shimmer);
            shimmer.startShimmer();
        }
    }
}
