package com.dhakasetup.sakib.dhakasetupprototype;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;


import com.dhakasetup.sakib.dhakasetupprototype.adapter.Service1Adapter;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.ServiceAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.model.Number;
import com.dhakasetup.sakib.dhakasetupprototype.model.Phone;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Service;

import java.util.ArrayList;
import java.util.List;

import iammert.com.expandablelib.ExpandCollapseListener;
import iammert.com.expandablelib.ExpandableLayout;
import iammert.com.expandablelib.Section;

public class Service1Activity extends AppCompatActivity {
    Toolbar toolbar;
    List<Service> serviceList ;
    String subcat_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service1);

        int subcat = getIntent().getIntExtra("subcat",-1);
        int cat = getIntent().getIntExtra("cat",-1);
        serviceList = Data.getInstance(this).getData().get(cat).getSubcats().get(subcat).getServices();
        subcat_name = Data.getInstance(this).getData().get(cat).getSubcats().get(subcat).getSubCat_name();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Secondary Category");
        toolbar.setTitle(subcat_name);

        /*
        // sample code snippet to set the text content on the ExpandableTextView
        ExpandableLayout layout = (ExpandableLayout) findViewById(R.id.service1_item_expandable);

        layout.setRenderer(new ExpandableLayout.Renderer<Phone,Number>() {
            @Override
            public void renderParent(View view, Phone phone, boolean b, int i) {
                ((TextView)view.findViewById(R.id.service1_item_name)).setText(phone.getName());
                view.findViewById(R.id.service1_item_arrow).setBackgroundResource(b?R.drawable.ic_arrow_up_black_24dp:R.drawable.ic_arrow_down_black_24dp);

            }

            @Override
            public void renderChild(View view, Number number, int i, int i1) {
                ((TextView)view.findViewById(R.id.service1_item_child)).setText(number.getName());
            }
        });
        Section<Phone,Number> section = new Section<>();
        section.parent = new Phone("Pure Tech 200 GPD Commercial RO Water Purifier");
        section.children.add(new Number("Cooling of the AC depends on multiple factors such as age of the AC, quality of the gas filled, pressure of the gas etc. and Servicing alone does not guarantee improvement in the cooling."));
        layout.addSection(section);
        layout.setExpandListener(new ExpandCollapseListener.ExpandListener<Phone>() {
            @Override
            public void onExpanded(int i, Phone phone, View view) {
                view.findViewById(R.id.service1_item_arrow).setBackgroundResource(R.drawable.ic_arrow_up_black_24dp);
            }
        });
        layout.setCollapseListener(new ExpandCollapseListener.CollapseListener<Phone>() {
            @Override
            public void onCollapsed(int i, Phone phone, View view) {
                view.findViewById(R.id.service1_item_arrow).setBackgroundResource(R.drawable.ic_arrow_down_black_24dp);
            }
        });*/


        List<Service> services = new ArrayList<>();
        Service service = new Service();
        service.setSrvImage("http://dhakasetup.com/images/services/gas%20charge.jpg");
        service.setSrvice("Gas charge");
        service.setSrvPrice("3000");
        service.setSrvDetails("ac");
        services.add(service);
        ExpandableListView expandableListView = findViewById(R.id.exp_listview);

        Service1Adapter adapter = new Service1Adapter(serviceList,this);
        expandableListView.setAdapter(adapter);

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