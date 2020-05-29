package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.ProductActivity;
import tech.iwish.pickmall.activity.Searchactivity;
import tech.iwish.pickmall.other.SearchList;

public class SearchBarAdapter extends RecyclerView.Adapter<SearchBarAdapter.Viewholder> implements Filterable {

    private Context context;
    private List<SearchList> searchListList;

    public SearchBarAdapter(Searchactivity searchactivity, List<SearchList> searchListList) {
        this.context = searchactivity;
        this.searchListList = searchListList;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_searchbar, null);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        holder.product_name.setText(searchListList.get(position).getName());

        holder.searchLayoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("type","searchActivity");
                intent.putExtra("name",searchListList.get(position).getName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return searchListList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();


                return null;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            }
        };
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView product_name;
        private LinearLayout searchLayoutClick;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            product_name = (TextView) itemView.findViewById(R.id.product_name);
            searchLayoutClick = (LinearLayout)itemView.findViewById(R.id.searchLayoutClick);


        }
    }
}
