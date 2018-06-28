package com.dhakasetup.sakib.dhakasetupprototype.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dhakasetup.sakib.dhakasetupprototype.MainActivity;
import com.dhakasetup.sakib.dhakasetupprototype.OrderActivity;
import com.dhakasetup.sakib.dhakasetupprototype.R;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Order;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryVH> {

    Context context;
    List<Order> orders;
    int frag;

    public HistoryAdapter(Context context, int f) {
        this.context = context;
        frag = f;
        if (f == 0){
            orders = Data.getOrderPacket(context).getGoing();
        }
        else {
            orders = Data.getOrderPacket(context).getServed();
        }
    }

    @NonNull
    @Override
    public HistoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_recycler_item,parent,false);
        return new HistoryVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryVH holder, int position) {
        final Order o = orders.get(position);
        if (o.getStatus().equals("Served")){
            int tint = ContextCompat.getColor(context,R.color.served);
            ImageViewCompat.setImageTintList(holder.icon, ColorStateList.valueOf(tint));

            GradientDrawable backgroundGradient = (GradientDrawable)holder.icon.getBackground();
            backgroundGradient.setStroke(3,context.getResources().getColor(R.color.served));
        }
        else if (o.getStatus().equals("Cancelled")){
            int tint = ContextCompat.getColor(context,R.color.cancelled);
            ImageViewCompat.setImageTintList(holder.icon, ColorStateList.valueOf(tint));

            GradientDrawable backgroundGradient = (GradientDrawable)holder.icon.getBackground();
            backgroundGradient.setStroke(3,context.getResources().getColor(R.color.cancelled));
        }
        else {
            int tint = ContextCompat.getColor(context,R.color.ongoing);
            ImageViewCompat.setImageTintList(holder.icon, ColorStateList.valueOf(tint));

            GradientDrawable backgroundGradient = (GradientDrawable)holder.icon.getBackground();
            backgroundGradient.setStroke(3,context.getResources().getColor(R.color.ongoing));
        }
        holder.order.setText(o.getOrder_id());
        holder.price.setText(String.valueOf(o.getTotal_am()));
        holder.date.setText(o.getOpen_time().toString());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderActivity.class);
                intent.putExtra("order",o.getOrder_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    public class HistoryVH extends RecyclerView.ViewHolder{

        ImageView icon;
        TextView price,order,date;
        RelativeLayout root;

        public HistoryVH(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.history_root);
            icon = itemView.findViewById(R.id.history_cart);
            price = itemView.findViewById(R.id.history_price);
            order = itemView.findViewById(R.id.history_order);
            date = itemView.findViewById(R.id.history_date);
        }
    }
}
