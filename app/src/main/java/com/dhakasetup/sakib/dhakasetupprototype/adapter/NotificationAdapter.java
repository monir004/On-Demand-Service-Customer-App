package com.dhakasetup.sakib.dhakasetupprototype.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dhakasetup.sakib.dhakasetupprototype.R;
import com.dhakasetup.sakib.dhakasetupprototype.model.Notification_Item;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationVH> {

    List<Notification_Item> notificationItems;

    public NotificationAdapter(List<Notification_Item> notificationItems) {
        this.notificationItems = notificationItems;
    }

    @NonNull
    @Override
    public NotificationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item,parent,false);
        return new NotificationVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationVH holder, int position) {
        holder.message.setText(notificationItems.get(position).getMessage());
        holder.date.setText(notificationItems.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return notificationItems.size();
    }

    public class NotificationVH extends RecyclerView.ViewHolder {

        TextView message,date;

        public NotificationVH(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.notification_message);
            date = itemView.findViewById(R.id.notification_date);
        }
    }
}
