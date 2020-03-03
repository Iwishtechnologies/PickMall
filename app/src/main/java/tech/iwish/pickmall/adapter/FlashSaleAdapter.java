package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.MainActivity;
import tech.iwish.pickmall.other.FlashsalemainList;

public class FlashSaleAdapter extends RecyclerView.Adapter<FlashSaleAdapter.Viewholder> {

    private List<FlashsalemainList> flashsalemainLists ;
    private Context context;

    public FlashSaleAdapter(MainActivity mainActivity, List<FlashsalemainList> flashsalemainLists) {
        this.context = mainActivity;
        this.flashsalemainLists = flashsalemainLists;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_flash_sale_main , null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return flashsalemainLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public Viewholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
