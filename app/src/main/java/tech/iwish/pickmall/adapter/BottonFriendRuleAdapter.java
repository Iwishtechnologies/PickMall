package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.BottomFragmentRuleList;

public class BottonFriendRuleAdapter extends RecyclerView.Adapter<BottonFriendRuleAdapter.Viewholder> {
    Context context;
    List<BottomFragmentRuleList>bottomFragmentRuleLists;

    public BottonFriendRuleAdapter(Context context,List<BottomFragmentRuleList>bottomFragmentRuleLists){
        this.bottomFragmentRuleLists=bottomFragmentRuleLists;
        this.context=context;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_rules_design , null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
            holder.dealid.setText(String.valueOf(position+1)+". ");
            holder.rule.setText(bottomFragmentRuleLists.get(position).getRule());
    }

    @Override
    public int getItemCount() {
        return bottomFragmentRuleLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextViewFont rule , dealid;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            dealid = itemView.findViewById(R.id.dealid);
            rule = itemView.findViewById(R.id.rule);
        }
    }
}
