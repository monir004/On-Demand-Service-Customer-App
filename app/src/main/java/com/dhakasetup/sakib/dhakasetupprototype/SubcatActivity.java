package com.dhakasetup.sakib.dhakasetupprototype;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.ServiceAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.SubcatAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.model.CategoryOld;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Category;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Service;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Subcat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SubcatActivity extends AppCompatActivity {

    ServiceAdapter adapter;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        Data.getInstance(this).test();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All Services");


        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);


        List<CategoryOld> categoryOldList = new ArrayList<>();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        String cat_name = getIntent().getStringExtra("cat_name");
        //init(categoryOldList,viewPager,viewPagerAdapter,tabLayout,cat_name);
        init1(viewPager,viewPagerAdapter,cat_name);
        Log.d("category",""+ categoryOldList.size());

        /*List<Service> subcats = new ArrayList<>();

        int cat_id = getIntent().getIntExtra("cat_id",20);
        String cat_name = getIntent().getStringExtra("cat_name");


        viewPagerAdapter.addFragment(SubcatFragment.newInstance(20,subcats),cat_name);
        viewPagerAdapter.addFragment(SubcatFragment.newInstance(cat_id),cat_name);
        viewPagerAdapter.addFragment(SubcatFragment.newInstance(cat_id),cat_name);

        viewPager.setAdapter(viewPagerAdapter);*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        MenuItem menuItem = menu.findItem(R.id.action_cart);
        menuItem.setIcon(MenuPainter.paint(SubcatActivity.this,Data.getCart(this).size(),R.drawable.ic_action_cart));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            //
        }
        Toast.makeText(this,"subcatActivity",Toast.LENGTH_SHORT).show();
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

    public void init1(ViewPager viewPager, ViewPagerAdapter viewPagerAdapter, String catg_name){
        List<Category> categories = Data.getInstance(this).getData();
        for (int i=0; i<categories.size(); i++){
            String cat_name = categories.get(i).getCat_name();
//            int section = Integer.parseInt(categories.get(i).getCat_id());
            List<Subcat> subcatList = categories.get(i).getSubcats();
            viewPagerAdapter.addFragment(SubcatFragment.newInstance(i,subcatList),cat_name);
            Log.d("category_id", "onResponse: section="+i+", cat_id="+cat_name);
            tabLayout.addTab(tabLayout.newTab().setText(cat_name));

        }
        tabLayout.setTabGravity(TabLayout.MODE_SCROLLABLE);
        this.viewPager.setAdapter(viewPagerAdapter);

        this.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(this.viewPager));

        for (int i=0; i<tabLayout.getTabCount(); i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if(tab.getText().equals(catg_name))
                tab.select();
        }
    }

    /*private void init(final List<CategoryOld> categories, final ViewPager viewPager, final ViewPagerAdapter viewPagerAdapter, final TabLayout tabLayout, final String cat_name) {
        final ProgressDialog prog1 = new ProgressDialog(this);
        prog1.setMessage("Loading from init API");
        prog1.show();

        StringRequest request = new StringRequest(Request.Method.GET,
                "http://www.dhakasetup.com/api/category.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        prog1.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int categoryCounter = jsonObject.getInt("categoryCounter");
                            JSONArray categoryArray = jsonObject.getJSONArray("categoryArray");
                            for (int i = 0; i < categoryArray.length(); i++){
                                JSONObject categoryObj = categoryArray.getJSONObject(i);
                                CategoryOld categoryOld = new CategoryOld();
                                categoryOld.setCat_id(categoryObj.getString("cat_id"));
                                categoryOld.setCat_name(categoryObj.getString("cat_name"));
                                categoryOld.setSer_counter(categoryObj.getInt("serviceCounter"));
                                Log.d("categoryOld","*** "+ categoryOld.getCat_name()+" has "+ categoryOld.getSer_counter()+" subcats ****");
                                JSONArray serviceArray = categoryObj.getJSONArray("serviceArray");
                                for (int j = 0; j< categoryOld.getSer_counter(); j++){
                                    JSONObject serviceObj = serviceArray.getJSONObject(j);
                                    String imgLinl = "http://www.dhakasetup.com/images/subcats/"+serviceObj.getString("srvImage");
                                    String title = serviceObj.getString("srvice");
                                    String price = "৳ " + serviceObj.getString("srvPrice");
                                    String details = serviceObj.getString("srvDetails");
                                    Service service = new Service(imgLinl,title,price);
                                    categoryOld.getServiceList().add(service);
                                    Log.d("categoryOld",title+price+imgLinl);
                                }
                                categories.add(categoryOld);

                            }
                            for (int i=0; i<categories.size(); i++){
                                int section = Integer.parseInt(categories.get(i).getCat_id());
                                List<Service> services = categories.get(i).getServiceList();
                                String cat_name = categories.get(i).getCat_name();
                                viewPagerAdapter.addFragment(SubcatFragment.newInstance(section,services),cat_name);
                                Log.d("category_id", "onResponse: section="+section+", cat_id="+cat_name);
                                tabLayout.addTab(tabLayout.newTab().setText(cat_name));

                            }
                            tabLayout.setTabGravity(TabLayout.MODE_SCROLLABLE);
                            viewPager.setAdapter(viewPagerAdapter);

                            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

                            for (int i=0; i<tabLayout.getTabCount(); i++){
                                TabLayout.Tab tab = tabLayout.getTabAt(i);
                                if(tab.getText().equals(cat_name))
                                    tab.select();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }*/




    public static class SubcatFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        List<Subcat> subcats;

        public SubcatFragment() {
            subcats = new ArrayList<>();
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static SubcatFragment newInstance(int sectionNumber, List<Subcat> subcats) {
            SubcatFragment fragment = new SubcatFragment();
            fragment.subcats = subcats;
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_subcat, container, false);

            RecyclerView recyclerView = rootView.findViewById(R.id.subcat_recycler);
            /*recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            int cat_id = 20;
            String cat_name = "ac";
            //load(cat_id,cat_name,recyclerView);*/
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            SubcatAdapter adapter = new SubcatAdapter(subcats,getArguments().getInt(ARG_SECTION_NUMBER),getContext());
            recyclerView.setAdapter(adapter);
            return rootView;
        }

        /*@Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            RecyclerView recyclerView = view.findViewById(R.id.service_recycler);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            ServiceAdapter adapter = new ServiceAdapter(subcats,getContext());
            recyclerView.setAdapter(adapter);
        }*/
        /*
        private void load(int cat, String cat_name, final RecyclerView recyclerView) {
            final ProgressDialog prog = new ProgressDialog(this.getActivity());
            prog.setMessage("Loading from API");
            prog.show();
            final List<Service> services = new ArrayList<Service>();

            StringRequest request = new StringRequest(Request.Method.GET,
                    "http://www.dhakasetup.com/api/service.php?cat="+cat,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            prog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("result");
                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject serviceObj = jsonArray.getJSONObject(i);
                                    String imgLinl = "http://www.dhakasetup.com/images/subcats/"+serviceObj.getString("srvImage");
                                    String title = serviceObj.getString("srvice");
                                    String price = "৳ " + serviceObj.getString("srvPrice");
                                    String details = serviceObj.getString("srvDetails");
                                    Service service = new Service(imgLinl,title,price);
                                    services.add(service);
                                }

                                ServiceAdapter adapter = new ServiceAdapter(services,getContext());
                                recyclerView.setAdapter(adapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            RequestQueue queue = Volley.newRequestQueue(this.getContext());
            queue.add(request);
        }*/
    }
}
