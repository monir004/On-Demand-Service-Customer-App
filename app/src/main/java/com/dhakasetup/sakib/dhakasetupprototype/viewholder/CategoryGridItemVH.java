package com.dhakasetup.sakib.dhakasetupprototype.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhakasetup.sakib.dhakasetupprototype.R;


/**
 * Created by Nazmus Sakib on 03,April,2018
 * sakib6900@gmail.com
 * Project Name: DhakaSetupPrototype
 */
public class CategoryGridItemVH extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView textView;
    public LinearLayout root;

    public CategoryGridItemVH(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.category_grid_image);
        textView = itemView.findViewById(R.id.category_grid_text);
        root = itemView.findViewById(R.id.category_grid_root);
    }
}
