package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.SupportActivity;
import tech.iwish.pickmall.activity.SupportAnswerActivity;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.SupportQueryList;

public class SupportAdapter extends RecyclerView.Adapter<SupportAdapter.ViewHolder> {
    SupportActivity context;
    List<SupportQueryList>supportQueryLists;

    public SupportAdapter(SupportActivity context, List<SupportQueryList>supportQueryLists){
        this.context=context;
        this.supportQueryLists=supportQueryLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_support_design , null);
       ViewHolder viewHolder= new ViewHolder(view);
       return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.query.setText(supportQueryLists.get(position).getQuery());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, SupportAnswerActivity.class);
                intent.putExtra("answer",supportQueryLists.get(position).getAnswer());
                intent.putExtra("query",supportQueryLists.get(position).getQuery());
                context.startActivity(intent);
//                Animatoo.animateShrink(context);
            }
        });

    }

    @Override
    public int getItemCount() {
        return supportQueryLists.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        RelativeLayout layout;
        TextViewFont query;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            query=itemView.findViewById(R.id.ques);
            layout=itemView.findViewById(R.id.layout);
        }
    }
}
