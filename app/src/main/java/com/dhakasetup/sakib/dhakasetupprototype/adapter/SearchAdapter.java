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
import com.dhakasetup.sakib.dhakasetupprototype.SearchActivity;
import com.dhakasetup.sakib.dhakasetupprototype.Service1Activity;
import com.dhakasetup.sakib.dhakasetupprototype.ServiceItemActivity;
import com.dhakasetup.sakib.dhakasetupprototype.SubcatActivity;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Category;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Service;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Subcat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchVH> {

    List<Category> categories = new ArrayList<>();
    List<Object> objects = new ArrayList<>();
    Context context;

    public SearchAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
        for (Category c : categories){
            objects.add(c);
            for (Subcat s : c.getSubcats()){
                s.setCat_id(c.getCat_id());
                objects.add(s);
                for (Service srv : s.getServices()){
                    objects.add(srv);
                }
            }
        }
    }

    @NonNull
    @Override
    public SearchVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchview_item,parent,false);
        return new SearchVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchVH holder, int position) {
        final Object obj = objects.get(position);
        if(obj instanceof Category){
            holder.text.setText(((Category) obj).getCat_name());
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,SubcatActivity.class);
                    intent.putExtra("cat_id",((Category) obj).getCat_id());
                    intent.putExtra("cat_name",((Category) obj).getCat_name());
                    context.startActivity(intent);
                }
            });
        }
        else if(obj instanceof Subcat){
            holder.text.setText(((Subcat) obj).getSubCat_name());
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Service1Activity.class);
                    intent.putExtra("subcat_id",((Subcat) obj).getSubCat_id());
                    context.startActivity(intent);
                }
            });
        }
        else if(obj instanceof Service){
            holder.text.setText(((Service) obj).getSrvice());
            Picasso.get().load("http://dhakasetup.com/images/services/"+((Service) obj).getSrvImage()).into(holder.image);
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ServiceItemActivity.class);
                    intent.putExtra("srviceID",((Service) obj).getSrv_sl());
                    context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public static class SearchVH extends RecyclerView.ViewHolder{
        LinearLayout root;
        ImageView image;
        TextView text;

        public SearchVH(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.search_root);
            image = itemView.findViewById(R.id.searchIcon);
            text = itemView.findViewById(R.id.searchTitle);
        }
    }

    public void setFilter(List<Object> objs){
        objects = new ArrayList<>();
        objects.addAll(objs);
        notifyDataSetChanged();
    }
}
