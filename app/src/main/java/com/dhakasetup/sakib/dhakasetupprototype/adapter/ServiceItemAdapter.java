package com.dhakasetup.sakib.dhakasetupprototype.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.ServiceProp;

import java.util.List;

public class ServiceItemAdapter extends RecyclerView.Adapter<ServiceItemAdapter.ServiceItemVH> {

    private Context context;
    List<ServiceProp> serviceProps;
    private static final int  INC = 1,DEC = 2,CHECK = 3,UNCHECK = 4;

    public ServiceItemAdapter(Context context, List<ServiceProp> serviceProps) {
        this.context = context;
        this.serviceProps = serviceProps;
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
                int c = Integer.parseInt(prop.getCount());
                int max = Integer.parseInt(prop.getMax());
                if (c < max){
                    c++;
                    prop.setCount(String.valueOf(c));
                    holder.count.setText(String.valueOf(c));
                    updateTotal(prop,INC);
                }
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = Integer.parseInt(prop.getCount());
                int min = Integer.parseInt(prop.getMin());
                if (c > min){
                    c--;
                    prop.setCount(String.valueOf(c));
                    holder.count.setText(String.valueOf(c));
                    updateTotal(prop,DEC);
                }
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    holder.counter_btn.setVisibility(View.VISIBLE);
                    holder.count.setText(prop.getMin());
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
        int subtotal = Integer.parseInt(activity.subtotal.getText().toString());
        int price = Integer.parseInt(prop.getPrice());
        switch (flag){
            case INC:
                subtotal += price;
                break;
            case DEC:
                subtotal -= price;
                break;
            case CHECK:
                subtotal += price*Integer.parseInt(prop.getMin());
                prop.setCount(String.valueOf(prop.getMin()));
                break;
            case UNCHECK:
                subtotal -= price*Integer.parseInt(prop.getCount());
                break;

        }

        activity.subtotal.setText(String.valueOf(subtotal));
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
