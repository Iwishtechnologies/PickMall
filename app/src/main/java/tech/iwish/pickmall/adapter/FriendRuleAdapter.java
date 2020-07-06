package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.fragment.RuleBottomFragment;
import tech.iwish.pickmall.other.RuleList;

public class FriendRuleAdapter extends RecyclerView.Adapter<FriendRuleAdapter.Viewholder>{

    private Context context ;
    private List<RuleList> ruleListList ;

    public FriendRuleAdapter(FragmentActivity activity, List<RuleList> ruleListList) {
        this.context = activity ;
        this.ruleListList = ruleListList ;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_rule_layout , null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.rule_title.setText(ruleListList.get(position).getTitle());
        holder.subtitle_rule.setText(ruleListList.get(position).getSubtitle());

        String a = Constants.IMAGES +ruleListList.get(position).getIcon();
        Glide.with(context).load(a).into(holder.icon_rule);
        holder.rule.setOnClickListener(v -> {
           context.startActivity(new Intent(context,RuleBottomFragment.class).putExtra("sno",ruleListList.get(position).getSno()));
        });
    }

    @Override
    public int getItemCount() {
        return ruleListList.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView rule_title , subtitle_rule;
        private ImageView icon_rule ;
        LinearLayout rule;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            rule_title = (TextView)itemView.findViewById(R.id.rule_title);
            subtitle_rule = (TextView)itemView.findViewById(R.id.subtitle_rule);
            icon_rule = (ImageView)itemView.findViewById(R.id.icon_rule);
            rule = itemView.findViewById(R.id.rule);

        }
    }
}
