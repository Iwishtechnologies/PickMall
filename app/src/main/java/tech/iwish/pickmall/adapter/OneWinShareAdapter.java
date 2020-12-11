package tech.iwish.pickmall.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.OneWinShareList;

public class OneWinShareAdapter extends RecyclerView.Adapter<OneWinShareAdapter.Viewholder> {
    private Context context;
    private List<OneWinShareList> oneWinShareLists;

    public OneWinShareAdapter(Context context, List<OneWinShareList> oneWinShareLists) {
        this.context = context;
        this.oneWinShareLists = oneWinShareLists;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_1_win_design, null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        switch (position){
            case 0:
                holder.imageRacking.setImageDrawable(context.getResources().getDrawable(R.drawable.racnking_1_icon));
                break;
            case 1:
                holder.imageRacking.setImageDrawable(context.getResources().getDrawable(R.drawable.racnking_2_icon));
                break;
            case 2:
                holder.imageRacking.setImageDrawable(context.getResources().getDrawable(R.drawable.racnking_2_icon));
                break;
        }

//      holder.id.setText(oneWinShareLists.get(position).getId());
//      holder.product.setText(oneWinShareLists.get(position).getProduct());
        holder.name.setText(oneWinShareLists.get(position).getClient_name());
        holder.shares.setText(oneWinShareLists.get(position).getTop_client()+" Shares");




    }

    @Override
    public int getItemCount() {
        return oneWinShareLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextViewFont product, name, shares;
        ImageView imageRacking;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageRacking = itemView.findViewById(R.id.id);
            product = itemView.findViewById(R.id.product);
            name = itemView.findViewById(R.id.name);
            shares = itemView.findViewById(R.id.shares);
        }
    }
}
