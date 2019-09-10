package com.dhakasetup.sakib.dhakasetupprototype.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhakasetup.sakib.dhakasetupprototype.R;
import com.dhakasetup.sakib.dhakasetupprototype.Service1Activity;
import com.dhakasetup.sakib.dhakasetupprototype.ServiceItemActivity;
import com.dhakasetup.sakib.dhakasetupprototype.WorkerActivity;
import com.dhakasetup.sakib.dhakasetupprototype.model.CategoryGrid;
import com.dhakasetup.sakib.dhakasetupprototype.model.ServiceGroup;
import com.dhakasetup.sakib.dhakasetupprototype.viewholder.CategoryGridItemVH;
import com.dhakasetup.sakib.dhakasetupprototype.viewholder.ServiceGroupItemVH;
import com.squareup.picasso.Picasso;

/**
 * Created by Nazmus Sakib on 04,April,2018
 * sakib6900@gmail.com
 * Project Name: DhakaSetupPrototype
 */
public class ServiceGroupAdapter extends RecyclerView.Adapter<ServiceGroupItemVH> {

    private ServiceGroup data;
    Context context;

    public ServiceGroupAdapter(ServiceGroup data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ServiceGroupItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.service_group_single_row,parent,false);
        ServiceGroupItemVH serviceGroupItemVH = new ServiceGroupItemVH(view);
        return serviceGroupItemVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceGroupItemVH holder, final int position) {
        ImageView imageView = holder.imageView;
        TextView textView = holder.textView;

        textView.setText(data.getItems().get(position).getTitle());
        String link = data.getItems().get(position).getImageLink();
        Picasso.get().load(link).resize(100,70).centerCrop().into(imageView);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WorkerActivity.class);
                intent.putExtra("srviceID",data.getItems().get(position).getSrv_sl());
                intent.putExtra("srviceName",data.getItems().get(position).getTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.getItems().size();
    }
}
