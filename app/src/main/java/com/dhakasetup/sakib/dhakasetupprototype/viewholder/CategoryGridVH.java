package com.dhakasetup.sakib.dhakasetupprototype.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dhakasetup.sakib.dhakasetupprototype.R;


/**
 * Created by Nazmus Sakib on 03,April,2018
 * sakib6900@gmail.com
 * Project Name: DhakaSetupPrototype
 */
public class CategoryGridVH extends RecyclerView.ViewHolder {

    public RecyclerView recyclerView;
    public CategoryGridVH(View itemView) {
        super(itemView);
        recyclerView = itemView.findViewById(R.id.recycler_category_grid);
    }
}
