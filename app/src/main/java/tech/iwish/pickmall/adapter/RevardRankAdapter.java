package tech.iwish.pickmall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.InviteActivity;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.RankList;

public class RevardRankAdapter extends RecyclerView.Adapter<RevardRankAdapter.ViewHolder> {
    InviteActivity inviteActivity;
    List<RankList>rankLists;


    public RevardRankAdapter(InviteActivity inviteActivity, List<RankList>rankLists){
        this.inviteActivity=inviteActivity;
        this.rankLists=rankLists;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(inviteActivity).inflate(R.layout.row_dessign_invite_earn , null);
        ViewHolder viewHolder= new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rank.setText(rankLists.get(position).getId());
        holder.name.setText(rankLists.get(position).getName());
        holder.amt.setText(rankLists.get(position).getReward());

    }

    @Override
    public int getItemCount() {
        return rankLists.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        TextViewFont rank,name,amt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rank=itemView.findViewById(R.id.rank);
            name=itemView.findViewById(R.id.name);
            amt=itemView.findViewById(R.id.rewards);
        }
    }
}
