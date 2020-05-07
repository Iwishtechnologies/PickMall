package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tech.iwish.pickmall.Interface.AddressInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.EditAddressActivity;
import tech.iwish.pickmall.activity.ShipingAddressActivity;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.AddressDataList;
import tech.iwish.pickmall.other.ShippingAddressList;

public class ShippingAddressAdapter extends RecyclerView.Adapter<ShippingAddressAdapter.ViewHolder> {
    private final int shipingLayout = 0;
    private Context context;
    private List<ShippingAddressList> shipping_address_data;
    private List<AddressDataList> addressDataLists ;
    int type;
    private int currentSelectedPosition = RecyclerView.NO_POSITION;
    private AddressInterface addressInterface;

    public ShippingAddressAdapter(Context context,  List<ShippingAddressList> shippingAddressLists,
                                  int type, List<AddressDataList> addressDataLists ,
                                  AddressInterface addressInterface) {

        this.context = context;
        this.shipping_address_data = shippingAddressLists;
        this.type = type;
        this.addressDataLists = addressDataLists ;
        this.addressInterface = addressInterface;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view ;
        ViewHolder viewHolder;
        
        switch (type) {
            case 0:
                 view = LayoutInflater.from(context).inflate(R.layout.row_shipping_address_desihn, null);
                 viewHolder = new ViewHolder(view);
                break;
            case 1:
                 view = LayoutInflater.from(context).inflate(R.layout.row_change_address, null);
                viewHolder = new ViewHolder(view);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + viewType);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        switch (type) {
            case 0:
                holder.name.setText(shipping_address_data.get(position).getName());
                holder.city_state.setText(shipping_address_data.get(position).getCity_State());
                holder.pincode.setText(shipping_address_data.get(position).getPin());
                holder.address_type.setText(shipping_address_data.get(position).getType());
                holder.address.setText(shipping_address_data.get(position).getAddress());
                break;
            case 1:
                holder.address_name.setText(addressDataLists.get(position).getName());
                holder.address_number.setText(addressDataLists.get(position).getDelivery_number());
                holder.address_full_address.setText(addressDataLists.get(position).getHouse_no()
                        + ","
                        + addressDataLists.get(position).getColony()
                        + " "
                        + addressDataLists.get(position).getLandmark()
                        + " "
                        + addressDataLists.get(position).getPincode()
                );
                holder.main_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentSelectedPosition = position;
                        notifyDataSetChanged();
                    }
                });
                if(currentSelectedPosition == position){
                    holder.radio_button.isSelected();
                    addressInterface.address_select(currentSelectedPosition);
                    holder.radio_button.setChecked(true);
                }else {
                    holder.radio_button.setChecked(false);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return   (type == 0) ? shipping_address_data.size() : addressDataLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextViewFont address, city_state, pincode, address_type, name , address_name,address_number,address_full_address;
        private RadioButton radio_button ;
        private RelativeLayout main_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            city_state = itemView.findViewById(R.id.city_state);
            pincode = itemView.findViewById(R.id.pincode);
            address_type = itemView.findViewById(R.id.address_type);
            name = itemView.findViewById(R.id.shipping_name);
            address_name = itemView.findViewById(R.id.address_name);
            address_number = itemView.findViewById(R.id.address_number);
            address_full_address = itemView.findViewById(R.id.address_full_address);
            radio_button = (RadioButton)itemView.findViewById(R.id.radio_button);
            main_layout= (RelativeLayout) itemView.findViewById(R.id.main_layout);



        }
    }
}
