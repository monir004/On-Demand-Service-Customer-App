package com.dhakasetup.sakib.dhakasetupprototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dhakasetup.sakib.dhakasetupprototype.adapter.ServiceItemAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Category;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.ServiceProp;

import java.util.ArrayList;
import java.util.List;

public class ServiceItemActivity extends AppCompatActivity {
    Toolbar toolbar;
    public TextView subtotal,savings;
    List<Category> categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_item);
        subtotal = findViewById(R.id.service1_item_subtotal);
        savings = findViewById(R.id.service1_item_savings);

        categories = Data.getInstance(this).getData();
        String srvice = getIntent().getStringExtra("srvice");
        Toast.makeText(this,srvice,Toast.LENGTH_SHORT).show();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All Services");

        List<ServiceProp> props = new ArrayList<>();
        props.add(new ServiceProp("Split Type AC","1","5","5","100000"));
        props.add(new ServiceProp("Window type ac","3","6","4","100"));
        RecyclerView recyclerView = findViewById(R.id.service1_item_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ServiceItemAdapter adapter = new ServiceItemAdapter(this,props);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            //
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
