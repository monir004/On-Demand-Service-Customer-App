package com.dhakasetup.sakib.dhakasetupprototype.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhakasetup.sakib.dhakasetupprototype.R;
import com.dhakasetup.sakib.dhakasetupprototype.ServiceItemActivity;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Service;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.ServiceProp;

import java.text.NumberFormat;
import java.util.List;

public class ServiceItemAdapter extends RecyclerView.Adapter<ServiceItemAdapter.ServiceItemVH> {

    private Context context;
    List<ServiceProp> serviceProps;
    private static final int  INC = 1,DEC = 2,CHECK = 3,UNCHECK = 4;
    NumberFormat nf = NumberFormat.getInstance(); // get instance


    public ServiceItemAdapter(Context context, List<ServiceProp> serviceProps) {
        this.context = context;
        this.serviceProps = serviceProps;
        nf.setMaximumFractionDigits(2);
    }

    @NonNull
    @Override
    public ServiceItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.service_item_row,parent,false);
        return new ServiceItemVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceItemVH holder, int position) {
        final ServiceProp prop = serviceProps.get(position);
        holder.checkBox.setText(prop.getPropName());
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("cart", "onBindViewHolder(@NonNull final ServiceItemVH holder, int position): ");
                int c = Data.getCart(context).getServiceProp(prop.getId()).getCount();
                int max = Data.getCart(context).getServiceProp(prop.getId()).getMax();
                if (c < max){
                    c++;
                    holder.count.setText(nf.format(c));
                    Log.d("cart", "plus count "+String.valueOf(c)+".");
                    updateTotal(prop,INC);
                }
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = Data.getCart(context).getServiceProp(prop.getId()).getCount();
                int min = Data.getCart(context).getServiceProp(prop.getId()).getMin();
                if (c > min){
                    c--;
                    holder.count.setText(nf.format(c));
                    Log.d("cart", "minus count "+String.valueOf(c)+".");
                    updateTotal(prop,DEC);
                }
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    holder.counter_btn.setVisibility(View.VISIBLE);
                    holder.count.setText(String.valueOf(prop.getMin()));
                    updateTotal(prop,CHECK);
                }
                else{
                    holder.counter_btn.setVisibility(View.INVISIBLE);
                    updateTotal(prop,UNCHECK);
                }
            }
        });
    }

    void updateTotal(ServiceProp prop,int flag){
        ServiceItemActivity activity = (ServiceItemActivity) context;
        //Double subtotal = Double.parseDouble(activity.subtotal.getText().toString());
        Double subtotal = Data.getCart(context).total();
        Double price = prop.getPrice();
        switch (flag){
            case INC:
                subtotal += price;
                Data.getCart(context).updateQty(prop.getId(),1);
                break;
            case DEC:
                subtotal -= price;
                Data.getCart(context).updateQty(prop.getId(),-1);
                break;
            case CHECK:
                int qty = prop.getMin();
                subtotal += price*qty;
                prop.setCount(prop.getMin());
                Data.getCart(context).addServiceProp(prop);
                ((ServiceItemActivity) context).cart_counter++;
                ((ServiceItemActivity) context).invalidateOptionsMenu();
                break;
            case UNCHECK:
                subtotal -= price*prop.getCount();
                Data.getCart(context).removeServiceProp(prop.getId());
                ((ServiceItemActivity) context).cart_counter--;
                ((ServiceItemActivity) context).invalidateOptionsMenu();
                break;

        }
        Log.d("cart","subtotal: "+String.valueOf(subtotal.intValue()));
        activity.subtotal.setText(String.valueOf(nf.format(subtotal)));
    }

    @Override
    public int getItemCount() {
        return serviceProps.size();
    }

    public class ServiceItemVH extends RecyclerView.ViewHolder{
        ImageButton plus,minus;
        TextView count;
        CheckBox checkBox;
        LinearLayout counter_btn;
        public ServiceItemVH(View itemView) {
            super(itemView);
            plus = itemView.findViewById(R.id.service1_item_plus);
            minus = itemView.findViewById(R.id.service1_item_minus);
            count = itemView.findViewById(R.id.service1_item_count);
            checkBox = itemView.findViewById(R.id.service1_item_checkbox);
            counter_btn = itemView.findViewById(R.id.counter_btn);
        }
    }
}
