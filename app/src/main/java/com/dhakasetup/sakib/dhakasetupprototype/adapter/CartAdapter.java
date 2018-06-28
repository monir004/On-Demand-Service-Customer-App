package com.dhakasetup.sakib.dhakasetupprototype.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dhakasetup.sakib.dhakasetupprototype.R;
import com.dhakasetup.sakib.dhakasetupprototype.ServiceItemActivity;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Service;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.ServiceProp;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartVH> {

    private Context context;
    List<ServiceProp> serviceProps;
    List<Service> services;
    NumberFormat nf = NumberFormat.getInstance();

    public CartAdapter(Context context) {
        this.context = context;
        serviceProps = Data.getCart(context).getServiceProps();
        services = Data.getCart(context).getServices();
    }

    @NonNull
    @Override
    public CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item,parent,false);
        return new CartAdapter.CartVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartVH holder, final int position) {
        if(position<services.size()){
            Service prop = services.get(position);
            holder.srvice.setText(prop.getSrvice());
            Picasso.get().load("http://dhakasetup.com/images/services/"+prop.getSrvImage()).into(holder.srvImg);
            holder.unit.setText(nf.format(prop.getCount()));
            holder.price.setText(nf.format(Double.parseDouble(prop.getSrvPrice())*prop.getCount()));
        }
        else {
            ServiceProp prop = serviceProps.get(position - services.size());
            holder.srvice.setText(prop.getService().getSrvice()+" --> "+prop.getPropName());
            Picasso.get().load("http://dhakasetup.com/images/services/"+prop.getService().getSrvImage()).into(holder.srvImg);
            holder.unit.setText(nf.format(prop.getCount()));
            holder.price.setText(nf.format(prop.getPrice()*prop.getCount()));
        }
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String srvID;
                if(position<services.size())
                    srvID = services.get(position).getSrv_sl();
                else
                    srvID = serviceProps.get(position - services.size()).getService().getSrv_sl();
                Intent intent = new Intent(context, ServiceItemActivity.class);
                intent.putExtra("srviceID",srvID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceProps.size()+services.size();
    }

    public class CartVH extends RecyclerView.ViewHolder{
        ImageView srvImg;
        TextView srvice,unit,price;
        RelativeLayout root;
        public CartVH(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.cart_root);
            srvImg = itemView.findViewById(R.id.cart_item_image);
            srvice = itemView.findViewById(R.id.cart_item_srvice);
            unit = itemView.findViewById(R.id.cart_item_unit);
            price = itemView.findViewById(R.id.cart_item_amount);
        }
    }
}
