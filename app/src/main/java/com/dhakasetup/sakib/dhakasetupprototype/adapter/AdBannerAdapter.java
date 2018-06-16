package com.dhakasetup.sakib.dhakasetupprototype.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dhakasetup.sakib.dhakasetupprototype.R;
import com.dhakasetup.sakib.dhakasetupprototype.model.AdBanner;
import com.dhakasetup.sakib.dhakasetupprototype.viewholder.AdBannerItemVH;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Nazmus Sakib on 03,April,2018
 * sakib6900@gmail.com
 * Project Name: DhakaSetupPrototype
 */
public class AdBannerAdapter extends RecyclerView.Adapter<AdBannerItemVH> {
    private AdBanner data;
    Context context;

    public AdBannerAdapter(AdBanner data, Context context) {
        this.data = data;
        this.context = context;

//        data.add(new String("http://dhakasetup.com/images/ad/1503162069.png"));
//        data.add(new String("http://dhakasetup.com/images/ad/1505300653.jpg"));

    }

    @NonNull
    @Override
    public AdBannerItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AdBannerItemVH adBannerVH;

        view = inflater.inflate(R.layout.ad_banner_single_item,parent,false);
        adBannerVH = new AdBannerItemVH(view);
        return adBannerVH;
    }

    @Override
    public void onBindViewHolder(@NonNull AdBannerItemVH holder, int position) {
        ImageView imageView = holder.imageView;
        String link = data.getImageLinks().get(position);
        Picasso.get().load(link).into(holder.imageView);
    }



    @Override
    public int getItemCount() {
        return data.getImageLinks().size();
    }
}
