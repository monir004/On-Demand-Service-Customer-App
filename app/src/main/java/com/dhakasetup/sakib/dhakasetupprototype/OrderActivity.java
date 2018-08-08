package com.dhakasetup.sakib.dhakasetupprototype;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.dhakasetup.sakib.dhakasetupprototype.adapter.CartAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.HistoryItemAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Order;

import java.util.List;

public class OrderActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView orderid_tv,status,total,disc,net,paid,due,address,open_time,close_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderid_tv = findViewById(R.id.order_id);
        status = findViewById(R.id.order_status);
        total = findViewById(R.id.order_total);
        disc = findViewById(R.id.order_discount);
        net = findViewById(R.id.order_net);
        paid = findViewById(R.id.order_paid);
        due = findViewById(R.id.order_due);
        address = findViewById(R.id.order_delivery_address);
        open_time = findViewById(R.id.order_placed);
        close_time = findViewById(R.id.order_closed);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Back to orders");

        String orderid = getIntent().getStringExtra("order");
        Order order = getOrder(orderid);
        orderid_tv.setText(String.valueOf(order.getOrder_sl()+1000));
        status.setText(order.getStatus());
        total.setText(String.valueOf(order.getTotal_am()));
        disc.setText(String.valueOf(order.getDisc_am()));
        net.setText(String.valueOf(order.getNet_am()));
        paid.setText(String.valueOf(order.getPaid_am()));
        due.setText(String.valueOf(order.getDue_am()));
        address.setText(order.getD_address());
        open_time.setText(order.getOpen_time().toString());
        close_time.setText(order.getClose_time().toString());
        address.setText(order.getD_address());

        if (order.getStatus().equals("Served")){
            status.setBackgroundColor(ContextCompat.getColor(this,R.color.served));
        }
        else if (order.getStatus().equals("Cancelled")){
            status.setBackgroundColor(ContextCompat.getColor(this,R.color.cancelled));
        }

        RecyclerView recyclerView = findViewById(R.id.order_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        HistoryItemAdapter adapter = new HistoryItemAdapter(this,orderid);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public Order getOrder(String orderid){
        List<Order> orders = Data.getOrderPacket(this).getOrders();
        for (int i=0; i<orders.size(); i++){
            if (orders.get(i).getOrder_id().equals(orderid)){
                return orders.get(i);
            }
        }
        return null;
    }
}
