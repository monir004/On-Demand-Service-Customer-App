package com.dhakasetup.sakib.dhakasetupprototype.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dhakasetup.sakib.dhakasetupprototype.R;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Service;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Subcat;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubcatAdapter extends RecyclerView.Adapter<SubcatAdapter.SubcatVH> {
    private List<Subcat> subcats;
    private Context context;

    public SubcatAdapter(List<Subcat> subcats, Context context) {
        this.subcats = subcats;
        this.context = context;
    }

    @NonNull
    @Override
    public SubcatVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcat_item,parent,false);
        return new SubcatVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubcatVH holder, final int position) {
        final Subcat subcat = subcats.get(position);
        //Picasso.get().load(subcat.getSubCat_name()).into(holder.imageView);
        holder.title.setText(subcat.getSubCat_name());
        LinearLayout root = holder.root;
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,subcat.getSubCat_name(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return subcats.size();
    }

    public class SubcatVH extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title,price;
        LinearLayout root;

        public SubcatVH(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.subcat_image);
            title = itemView.findViewById(R.id.subcat_name);
            root = itemView.findViewById(R.id.subcat_root);
        }
    }
}
