package com.dhakasetup.sakib.dhakasetupprototype;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dhakasetup.sakib.dhakasetupprototype.adapter.CartAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.SubcatAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Category;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Service;

import java.text.NumberFormat;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    Toolbar toolbar;
    Context context;
    ImageButton add_more;
    public TextView subtotal,savings;
    List<Category> categories;
    public int cart_counter = 0;
    NumberFormat nf ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);

        subtotal = findViewById(R.id.service1_item_subtotal);
        savings = findViewById(R.id.service1_item_savings);
        add_more = findViewById(R.id.cart_add_more);

        categories = Data.getInstance(this).getData();
        context = this;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cart");

        RecyclerView recyclerView = findViewById(R.id.recycler_cart);
            /*recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            int cat_id = 20;
            String cat_name = "ac";
            //load(cat_id,cat_name,recyclerView);*/
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CartAdapter adapter = new CartAdapter(this);
        recyclerView.setAdapter(adapter);
        subtotal.setText(nf.format(Data.getCart(this).total()));

        add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        MenuItem menuItem = menu.findItem(R.id.action_cart);
        menuItem.setIcon(MenuPainter.paint(CartActivity.this,Data.getCart(this).size(),R.drawable.ic_action_cart));


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            //
        }
        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent intent = new Intent(this,CartActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_search:
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
