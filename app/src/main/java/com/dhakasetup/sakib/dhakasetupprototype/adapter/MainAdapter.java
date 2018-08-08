package com.dhakasetup.sakib.dhakasetupprototype.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dhakasetup.sakib.dhakasetupprototype.R;
import com.dhakasetup.sakib.dhakasetupprototype.Service1Activity;
import com.dhakasetup.sakib.dhakasetupprototype.model.AdBanner;
import com.dhakasetup.sakib.dhakasetupprototype.model.CategoryGrid;
import com.dhakasetup.sakib.dhakasetupprototype.model.ServiceGroup;
import com.dhakasetup.sakib.dhakasetupprototype.viewholder.AdBannerVH;
import com.dhakasetup.sakib.dhakasetupprototype.viewholder.CategoryGridVH;
import com.dhakasetup.sakib.dhakasetupprototype.viewholder.ServiceGroupVH;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Nazmus Sakib on 03,April,2018
 * sakib6900@gmail.com
 * Project Name: DhakaSetupPrototype
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Object> data;
    private final int TYPE_AD_BANNER = 1;
    private final int TYPE_GRID = 2;
    private final int TYPE_SERVICE = 3;

    public MainAdapter(Context context, List<Object> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        int type = -1;
        if(data.get(position) instanceof AdBanner){
            type = TYPE_AD_BANNER;
        }
        else if (data.get(position) instanceof CategoryGrid){
            type = TYPE_GRID;
        }
        else if (data.get(position) instanceof ServiceGroup){
            type = TYPE_SERVICE;
        }
        return type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case TYPE_AD_BANNER:
                view = inflater.inflate(R.layout.ad_banner,parent,false);
                viewHolder = new AdBannerVH(view);
                break;
            case TYPE_GRID:
                view = inflater.inflate(R.layout.category_grid,parent,false);
                TextView news = view.findViewById(R.id.breaking_news);
                news.setSelected(true);
                viewHolder = new CategoryGridVH(view);
                break;
            case TYPE_SERVICE:
                view = inflater.inflate(R.layout.service_group,parent,false);
                viewHolder = new ServiceGroupVH(view);
                break;
            default:
                view = inflater.inflate(R.layout.ad_banner,parent,false);
                viewHolder = new AdBannerVH(view);
                break;
        }

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        switch (viewType){
            case TYPE_AD_BANNER:
                AdBannerVH adBannerVH = (AdBannerVH) holder;
                AdBannerAdapter adBannerAdapter = new AdBannerAdapter((AdBanner) data.get(position),context);
                adBannerVH.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
                adBannerVH.recyclerView.setAdapter(adBannerAdapter);
                break;
            case TYPE_GRID:
                CategoryGridVH categoryGridVH = (CategoryGridVH) holder;
                categoryGridVH.recyclerView.setLayoutManager(new GridLayoutManager(context,4));
                CategoryGridAdapter categoryGridAdapter = new CategoryGridAdapter((CategoryGrid) data.get(position),context);
                categoryGridVH.recyclerView.setAdapter(categoryGridAdapter);
                Log.d("test","MainAdapter onBind");
                break;
            case TYPE_SERVICE:
                ServiceGroupVH serviceGroupVH = (ServiceGroupVH) holder;
                final ServiceGroup serviceGroup = (ServiceGroup) data.get(position);
                serviceGroupVH.title.setText(serviceGroup.getService_group_title());
                serviceGroupVH.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
                ServiceGroupAdapter serviceGroupAdapter = new ServiceGroupAdapter((ServiceGroup) serviceGroup,context);
                serviceGroupVH.recyclerView.setAdapter(serviceGroupAdapter);
                serviceGroupVH.see_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context,""+serviceGroup.getTrend_id(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, Service1Activity.class);
                        intent.putExtra("trend",serviceGroup.getTrend_id());
                        context.startActivity(intent);
                    }
                });
                break;
        }
    }
}
