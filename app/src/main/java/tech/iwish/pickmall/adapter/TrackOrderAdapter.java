package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.TrackorderList;
import tech.iwish.pickmall.other.WishlistList;

public class TrackOrderAdapter extends RecyclerView.Adapter<TrackOrderAdapter.Viewholder> {
    private Context context ;
    private List<TrackorderList> trackorderLists ;

    public TrackOrderAdapter(Context context,List<TrackorderList>trackorderLists){
        this.context= context;
        this.trackorderLists=trackorderLists;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_trackorder_design , null);
        Viewholder  viewholder =  new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
                holder.title.setText(trackorderLists.get(position).getActivity());
//                holder.subtitle.setText(trackorderLists.get(position).getLocation());
                holder.date.setText(trackorderLists.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return trackorderLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextViewFont title,subtitle,date;
        ImageView bar;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            subtitle=itemView.findViewById(R.id.subtitle);
            bar=itemView.findViewById(R.id.bar);
            date=itemView.findViewById(R.id.date);
        }
    }
}
