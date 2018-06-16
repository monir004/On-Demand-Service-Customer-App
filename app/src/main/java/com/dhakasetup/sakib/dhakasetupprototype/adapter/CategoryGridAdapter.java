package com.dhakasetup.sakib.dhakasetupprototype.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhakasetup.sakib.dhakasetupprototype.R;
import com.dhakasetup.sakib.dhakasetupprototype.SubcatActivity;
import com.dhakasetup.sakib.dhakasetupprototype.model.CategoryGrid;
import com.dhakasetup.sakib.dhakasetupprototype.model.CategoryGridItem;
import com.dhakasetup.sakib.dhakasetupprototype.viewholder.CategoryGridItemVH;
import com.squareup.picasso.Picasso;


/**
 * Created by Nazmus Sakib on 03,April,2018
 * sakib6900@gmail.com
 * Project Name: DhakaSetupPrototype
 */
public class CategoryGridAdapter extends RecyclerView.Adapter<CategoryGridItemVH> {
    private CategoryGrid data;
    Context context;

    public CategoryGridAdapter(CategoryGrid data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryGridItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CategoryGridItemVH categoryGridItemVH;

        view = inflater.inflate(R.layout.category_grid_single_item,parent,false);
        categoryGridItemVH = new CategoryGridItemVH(view);
        return categoryGridItemVH;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryGridItemVH holder, final int position) {
        ImageView imageView = holder.imageView;
        TextView textView = holder.textView;
        LinearLayout root = holder.root;

        final CategoryGridItem item = data.getItems().get(position);

        textView.setText(data.getItems().get(position).getTitle());
        String link = data.getItems().get(position).imageLink;
        Picasso.get().load(link).resize(100,70).centerCrop().into(imageView);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"fetched from "+item.getTitle()+" API",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, SubcatActivity.class);
                intent.putExtra("cat_id",data.getItems().get(position).cat_id);
                intent.putExtra("cat_name",data.getItems().get(position).getTitle());
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return data.getItems().size();
    }
}
