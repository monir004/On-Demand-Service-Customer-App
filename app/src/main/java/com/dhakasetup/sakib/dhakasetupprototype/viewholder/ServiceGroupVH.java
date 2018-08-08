package com.dhakasetup.sakib.dhakasetupprototype.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dhakasetup.sakib.dhakasetupprototype.R;


/**
 * Created by Nazmus Sakib on 04,April,2018
 * sakib6900@gmail.com
 * Project Name: DhakaSetupPrototype
 */
public class ServiceGroupVH extends RecyclerView.ViewHolder {

    public RecyclerView recyclerView;
    public TextView title;
    public Button see_all;

    public ServiceGroupVH(View itemView) {
        super(itemView);
        recyclerView = itemView.findViewById(R.id.recycler_service_group);
        title = itemView.findViewById(R.id.service_group_title);
        see_all = itemView.findViewById(R.id.service_group_see_all);
    }
}
