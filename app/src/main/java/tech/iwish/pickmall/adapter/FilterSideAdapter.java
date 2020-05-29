package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.iwish.pickmall.Interface.FiltersInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.FilterActivity;
import tech.iwish.pickmall.other.FIltersideList;

public class FilterSideAdapter extends RecyclerView.Adapter<FilterSideAdapter.Viewholder> {

    List<FIltersideList> fIltersideLists;
    Context context;
    private int cuurentposition = RecyclerView.NO_POSITION;
    private FiltersInterface filtersInterface;
    private String checker = null;


    public FilterSideAdapter(FilterActivity filterActivity, List filterSideList, FiltersInterface filtersInterface) {
        this.context = filterActivity;
        this.fIltersideLists = filterSideList;
        this.filtersInterface = filtersInterface;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_filter_side, null);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {

        holder.filter_name.setText(fIltersideLists.get(position).getFilter_name());

        if (checker == null) {
            this.checker = "sdsdcdd";
            filtersInterface.filterid(fIltersideLists.get(0).getFilter_name());
        }

        holder.filter_name_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cuurentposition = position;
                notifyDataSetChanged();
            }
        });

        if (cuurentposition == position) {

            holder.filter_name_click.setBackgroundColor(context.getResources().getColor(android.R.color.white));
            filtersInterface.filterid(fIltersideLists.get(cuurentposition).getFilter_name());

        } else {
            holder.filter_name_click.setBackgroundColor(context.getResources().getColor(R.color.silderColor));
        }
//
    }

    @Override
    public int getItemCount() {
        return fIltersideLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView filter_name;
        LinearLayout filter_name_click;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            filter_name = (TextView) itemView.findViewById(R.id.filter_name);
            filter_name_click = itemView.findViewById(R.id.filter_name_click);

        }
    }
}























