package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.WalletList;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.Viewholder> {
    private Context context ;
    private List<WalletList> walletLists ;

    public WalletAdapter(Context context, List<WalletList> walletLists){
        this.walletLists=walletLists;
        this.context=context;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_wallethistory_design , null);
        Viewholder viewholder= new Viewholder(view);
        return viewholder;
            }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.amount.setText(walletLists.get(position).getAmount());
        holder.date.setText(walletLists.get(position).getDate());
        holder.status.setText(walletLists.get(position).getType());
        holder.description.setText(walletLists.get(position).getDescription());
        if (walletLists.get(position).getType().equals("Credit")){
            holder.type.setText("Amount Add");
        }
        else {
            holder.type.setText("Paid to");
        }

    }

    @Override
    public int getItemCount() {
        return walletLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextViewFont type, description,amount,status,date;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            type=itemView.findViewById(R.id.type);
            description=itemView.findViewById(R.id.description);
            amount=itemView.findViewById(R.id.amount);
            status=itemView.findViewById(R.id.status);
            date=itemView.findViewById(R.id.date);
        }
    }
}
