package com.dhakasetup.sakib.dhakasetupprototype.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.dhakasetup.sakib.dhakasetupprototype.R;


/**
 * Created by Nazmus Sakib on 03,April,2018
 * sakib6900@gmail.com
 * Project Name: DhakaSetupPrototype
 */
public class AdBannerItemVH extends RecyclerView.ViewHolder {

    public ImageView imageView;

    public AdBannerItemVH(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.ad_banner_img);
    }
}
