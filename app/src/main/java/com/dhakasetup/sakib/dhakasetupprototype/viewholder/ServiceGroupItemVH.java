package com.dhakasetup.sakib.dhakasetupprototype.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhakasetup.sakib.dhakasetupprototype.R;


/**
 * Created by Nazmus Sakib on 04,April,2018
 * sakib6900@gmail.com
 * Project Name: DhakaSetupPrototype
 */
public class ServiceGroupItemVH extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView textView;

    public ServiceGroupItemVH(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.service_image);
        textView = itemView.findViewById(R.id.service_txt);
    }
}
