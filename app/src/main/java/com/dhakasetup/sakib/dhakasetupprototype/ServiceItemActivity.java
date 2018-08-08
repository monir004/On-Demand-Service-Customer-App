package com.dhakasetup.sakib.dhakasetupprototype;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dhakasetup.sakib.dhakasetupprototype.adapter.ServiceItemAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Cart;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Category;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Service;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.ServiceProp;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ServiceItemActivity extends AppCompatActivity {
    Toolbar toolbar;
    Context context;
    NumberFormat nf;
    LinearLayout no_prop;
    RecyclerView recyclerView;
    ImageButton plus_no,minus_no,add_more;
    TextView count_tv_no;
    public TextView subtotal,savings;
    List<Category> categories;
    public int cart_counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_item);
        subtotal = findViewById(R.id.service1_item_subtotal);
        savings = findViewById(R.id.service1_item_savings);
        no_prop = findViewById(R.id.service1_item_no_prop);
        recyclerView = findViewById(R.id.service1_item_recycler);
        add_more = findViewById(R.id.service1_item_add_more);
        plus_no = findViewById(R.id.service1_item_plus_no);
        minus_no = findViewById(R.id.service1_item_minus_no);
        count_tv_no = findViewById(R.id.service1_item_count_no);
        count_tv_no.setText("0");
        nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        context = this;
        subtotal.setText(nf.format(Data.getCart(context).total()));

        categories = Data.getInstance(this).getData();
        String srviceID = getIntent().getStringExtra("srviceID");
        final int srvID = Integer.parseInt(srviceID);
        final Service service = Data.getInstance(this).getService(srvID);
        int prop_num = service.getServiceProps().size();
        if(prop_num > 0)
            no_prop.setVisibility(View.GONE);
        else{
            recyclerView.setVisibility(View.GONE);
            Service prevSrv = Data.getCart(this).getService(srvID);
            if (prevSrv != null){
                count_tv_no.setText(String.valueOf(prevSrv.getCount()));
            }
            plus_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count_tv_no.getText().equals("0")){
                        service.setCount(0);
                        Data.getCart(context).addService(service);
                        invalidateOptionsMenu();
                    }
                    Data.getCart(context).getService(srvID).incCount();
                    count_tv_no.setText(String.valueOf(Data.getCart(context).getService(srvID).getCount()));
                    subtotal.setText(nf.format(Data.getCart(context).total()));
                }
            });

            minus_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count_tv_no.getText().equals("0")) return;
                    if (count_tv_no.getText().equals("1")){
                        count_tv_no.setText("0");
                        Data.getCart(context).removeService(Integer.parseInt(service.getSrv_sl()));
                        invalidateOptionsMenu();
                    }
                    else {
                        Data.getCart(context).getService(Integer.parseInt(service.getSrv_sl())).decCount();
                        count_tv_no.setText(String.valueOf(Data.getCart(context).getService(srvID).getCount()));
                    }

                    subtotal.setText(nf.format(Data.getCart(context).total()));
                }
            });
        }

        //Toast.makeText(this,"propsize "+service.getServiceProps().size(),Toast.LENGTH_SHORT).show();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(service.getSrvice());

        List<ServiceProp> props = new ArrayList<>();
        props.add(new ServiceProp(1,"Split Type AC",1,5,1,100.1,service));
        props.add(new ServiceProp(2,"Window type ac",2,4,4,1000.1,service));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ServiceItemAdapter adapter = new ServiceItemAdapter(this,service.getServiceProps());
        recyclerView.setAdapter(adapter);

        add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        Button place_order = findViewById(R.id.place_order_btn);
        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,CartActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        MenuItem menuItem = menu.findItem(R.id.action_cart);
        menuItem.setIcon(MenuPainter.paint(ServiceItemActivity.this,Data.getCart(this).size(),R.drawable.ic_action_cart));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            //
        }
        //Toast.makeText(this,"srvItemactivty",Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.action_cart:

                Intent intent = new Intent(this,CartActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_search:
                Intent intent1 = new Intent(this,SearchActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}
