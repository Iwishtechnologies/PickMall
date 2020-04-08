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
import tech.iwish.pickmall.other.ShippingAddressList;

public class ShippingAddressAdapter extends RecyclerView.Adapter<ShippingAddressAdapter.ViewHolder> {
    private Context context ;
    private List<ShippingAddressList>shipping_address_data ;

    public ShippingAddressAdapter(Context context , List<ShippingAddressList>shipping_address_data){
    this.context=context;
    this.shipping_address_data=shipping_address_data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_shipping_address_desihn , null);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.name.setText(shipping_address_data.get(position).getName());
         holder.city_state.setText(shipping_address_data.get(position).getCity_State());
         holder.pincode.setText(shipping_address_data.get(position).getPin());
         holder.address_type.setText(shipping_address_data.get(position).getType());
         holder.address.setText(shipping_address_data.get(position).getAddress());


    }

    @Override
    public int getItemCount() {
        return shipping_address_data.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        TextViewFont address,city_state,pincode,address_type,name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address= itemView.findViewById(R.id.address);
            city_state= itemView.findViewById(R.id.city_state);
            pincode= itemView.findViewById(R.id.pincode);
            address_type= itemView.findViewById(R.id.address_type);
            name= itemView.findViewById(R.id.shipping_name);
        }
    }
}
