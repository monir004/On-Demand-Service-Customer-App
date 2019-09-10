package com.dhakasetup.sakib.dhakasetupprototype.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dhakasetup.sakib.dhakasetupprototype.OrderActivity;
import com.dhakasetup.sakib.dhakasetupprototype.R;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.OrderCustomer;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderVH> {
    Context context;
    List<OrderCustomer> orders;

    public OrderAdapter(Context context, List<OrderCustomer> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_recycler_item,viewGroup,false);
        return new OrderAdapter.OrderVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderVH orderVH, final int i) {
        orderVH.service.setText(orders.get(i).getServiceName());
        orderVH.price.setText(orders.get(i).getNet());
        orderVH.order_id.setText(orders.get(i).getOrder_id());
        if (orders.get(i).getStatus().equals("placed")){
            int tint = ContextCompat.getColor(context,R.color.served);
            ImageViewCompat.setImageTintList(orderVH.icon, ColorStateList.valueOf(tint));

            GradientDrawable backgroundGradient = (GradientDrawable)orderVH.icon.getBackground();
            backgroundGradient.setStroke(3,context.getResources().getColor(R.color.served));
        }
        else{
            int tint = ContextCompat.getColor(context,R.color.cancelled);
            ImageViewCompat.setImageTintList(orderVH.icon, ColorStateList.valueOf(tint));

            GradientDrawable backgroundGradient = (GradientDrawable)orderVH.icon.getBackground();
            backgroundGradient.setStroke(3,context.getResources().getColor(R.color.cancelled));
        }
        orderVH.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,orders.get(i).getOrder_id(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,OrderActivity.class);
                intent.putExtra("order_id",orders.get(i).getOrder_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderVH extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView service,order_id,price;
        RelativeLayout root;

        public OrderVH(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.history_cart);
            service = itemView.findViewById(R.id.history_date);
            order_id =itemView.findViewById(R.id.history_order);
            price = itemView.findViewById(R.id.history_price);
            root = itemView.findViewById(R.id.history_root);
        }
    }
}
