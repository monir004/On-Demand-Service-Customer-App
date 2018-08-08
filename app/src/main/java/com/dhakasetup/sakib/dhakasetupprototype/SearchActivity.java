package com.dhakasetup.sakib.dhakasetupprototype;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.dhakasetup.sakib.dhakasetupprototype.adapter.SearchAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Category;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Order;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Service;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Subcat;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    SearchAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Object> objects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = findViewById(R.id.searchtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.searchRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        setup_data();
        adapter = new SearchAdapter(Data.getInstance(this).getData(),this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.mSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setIconified(false);
        searchView.setQueryHint("Search for a service");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                List<Object> new_objects = new ArrayList<>();
                for (Object obj : objects){
                    String name = "";
                    if(obj instanceof Service){
                        name = ((Service) obj).getSrvice();
                    }
                    else if(obj instanceof Subcat){
                        name = ((Subcat) obj).getSubCat_name();
                    }
                    else if (obj instanceof Category){
                        name = ((Category) obj).getCat_name();
                    }
                    name = name.toLowerCase();
                    if (name.contains(newText)){
                        new_objects.add(obj);
                    }
                }
                adapter.setFilter(new_objects);
                return true;
            }
        });
        return true;
    }

    public void setup_data(){
        List<Category> categories = Data.getInstance(this).getData();
        objects = new ArrayList<>();
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
}
