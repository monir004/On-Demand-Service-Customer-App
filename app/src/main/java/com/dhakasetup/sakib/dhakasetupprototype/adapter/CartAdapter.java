package com.dhakasetup.sakib.dhakasetupprototype.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhakasetup.sakib.dhakasetupprototype.R;
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
    public void onBindViewHolder(@NonNull CartVH holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return serviceProps.size()+services.size();
    }

    public class CartVH extends RecyclerView.ViewHolder{
        ImageView srvImg;
        TextView srvice,unit,price;
        public CartVH(View itemView) {
            super(itemView);
            srvImg = itemView.findViewById(R.id.cart_item_image);
            srvice = itemView.findViewById(R.id.cart_item_srvice);
            unit = itemView.findViewById(R.id.cart_item_unit);
            price = itemView.findViewById(R.id.cart_item_amount);
        }
    }
}
