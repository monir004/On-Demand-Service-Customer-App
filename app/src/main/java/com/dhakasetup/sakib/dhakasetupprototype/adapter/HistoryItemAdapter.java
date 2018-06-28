package com.dhakasetup.sakib.dhakasetupprototype.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dhakasetup.sakib.dhakasetupprototype.R;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Order;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.OrderItem;

import java.util.List;

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.HistoryItemVH> {

    Context context;
    List<OrderItem> items;

    public HistoryItemAdapter(Context context, String orderid) {
        this.context = context;
        List<Order> orders = Data.getOrderPacket(context).getOrders();
        for (int i = 0;  i<orders.size(); i++){
            if (orders.get(i).getOrder_id().equals(orderid)){
                items = orders.get(i).getOrderItems();
                Log.d("profileres", "items size -> "+ items.size());
                Log.d("profileres", "items name -> "+ items.get(0).getSrvice()+items.get(1).getSrvice());
                break;
            }
        }
    }

    @NonNull
    @Override
    public HistoryItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_service_item,parent,false);
        return new HistoryItemVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryItemVH holder, int position) {
        OrderItem item = items.get(position);
        String name,price;
        if (item.getProp_id().equals("0") || item.getProp_id()==null){
            name = item.getSrvice();
            price = item.getSrvPrice();
        }
        else {
            name = item.getSrvice()+"-->"+item.getProp_name();
            price = item.getProp_qty();
        }
        holder.name.setText(name);
        holder.price.setText(price);
        Log.d("profileres", "item.getProp_id() >"+item.getSrv_sl()+"<");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HistoryItemVH extends RecyclerView.ViewHolder{
        TextView name,price;

        public HistoryItemVH(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.order_service_name);
            price = itemView.findViewById(R.id.order_service_price);
        }
    }
}
