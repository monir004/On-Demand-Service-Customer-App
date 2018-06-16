package com.dhakasetup.sakib.dhakasetupprototype.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhakasetup.sakib.dhakasetupprototype.R;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Service;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Nazmus Sakib on 05,April,2018
 * sakib6900@gmail.com
 * Project Name: DhakaSetupPrototype
 */
public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceVH> {

    private List<Service> services;
    private Context context;

    public ServiceAdapter(List<Service> services, Context context) {
        this.services = services;
        this.context = context;
    }

    @NonNull
    @Override
    public ServiceVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item,parent,false);
        return new ServiceVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceVH holder, int position) {
        Service service = services.get(position);
        Picasso.get().load(service.getImgLink()).into(holder.imageView);
        holder.title.setText(service.getTitle());
        holder.price.setText(service.getPrice());
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ServiceVH extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title,price;

        public ServiceVH(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.service_image);
            title = itemView.findViewById(R.id.service_title);
            price = itemView.findViewById(R.id.service_price);


        }
    }
}
