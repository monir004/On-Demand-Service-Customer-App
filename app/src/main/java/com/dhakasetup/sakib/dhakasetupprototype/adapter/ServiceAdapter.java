package com.dhakasetup.sakib.dhakasetupprototype.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhakasetup.sakib.dhakasetupprototype.R;
import com.dhakasetup.sakib.dhakasetupprototype.model.Number;
import com.dhakasetup.sakib.dhakasetupprototype.model.Phone;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Service;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.ServiceChild;
import com.squareup.picasso.Picasso;

import java.util.List;

import iammert.com.expandablelib.ExpandCollapseListener;
import iammert.com.expandablelib.ExpandableLayout;
import iammert.com.expandablelib.Section;


/**
 * Created by Nazmus Sakib on 05,April,2018
 * sakib6900@gmail.com
 * Project Name: DhakaSetupPrototype
 */
public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceVH> {

    private List<Service> services;
    private Context context;

    public ServiceAdapter(List<Service> services, Context context) {
        this.services = services;
        this.context = context;
    }

    @NonNull
    @Override
    public ServiceVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.service1_item,parent,false);

        return new ServiceVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceVH holder, int position) {
        Service service = services.get(position);

        ExpandableLayout layout = holder.layout;

        Section<Service,ServiceChild> section = new Section<>();
        section.parent = service;
        section.children.add(new ServiceChild(service.getSrvDetails()));
        layout.addSection(section);
        layout.setExpandListener(new ExpandCollapseListener.ExpandListener<Service>() {
            @Override
            public void onExpanded(int i, Service service, View view) {
                view.findViewById(R.id.service1_item_btn).setBackgroundResource(R.drawable.ic_arrow_up_black_24dp);
            }
        });
        layout.setCollapseListener(new ExpandCollapseListener.CollapseListener<Service>() {
            @Override
            public void onCollapsed(int i, Service service, View view) {
                view.findViewById(R.id.service1_item_child).setBackgroundResource(R.drawable.ic_arrow_down_black_24dp);
            }

        });

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ServiceVH extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title,price,desc;
        ExpandableLayout layout;

        public ServiceVH(View itemView) {
            super(itemView);

//            layout = itemView.findViewById(R.id.service1_item_expandable);
//            imageView = itemView.findViewById(R.id.service1_item_imageSrv);
//            title = itemView.findViewById(R.id.service1_item_name);
//            price = itemView.findViewById(R.id.service1_item_price);
//            desc = itemView.findViewById(R.id.service1_item_child);

            layout = itemView.findViewById(R.id.service1_item_expandable);

            layout.setRenderer(new ExpandableLayout.Renderer<Service,ServiceChild>() {
                @Override
                public void renderParent(View view, Service service, boolean b, int i) {
                    imageView = view.findViewById(R.id.service1_item_imageSrv);
                    title = view.findViewById(R.id.service1_item_name);
                    price = view.findViewById(R.id.service1_item_price);

                    view.findViewById(R.id.service1_item_btn).setBackgroundResource(b?R.drawable.ic_arrow_up_black_24dp:R.drawable.ic_arrow_down_black_24dp);
                    Picasso.get().load(service.getSrvImage()).into(imageView);
                    title.setText(service.getSrvice());
                    price.setText(service.getSrvPrice());
                }

                @Override
                public void renderChild(View view, ServiceChild serviceChild, int i, int i1) {
                    desc = view.findViewById(R.id.service1_item_child);
                    desc.setText(serviceChild.getDescription());
                }

            });


        }
    }
}
