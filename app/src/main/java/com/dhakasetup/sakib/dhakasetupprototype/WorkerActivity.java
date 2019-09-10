package com.dhakasetup.sakib.dhakasetupprototype;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.WorkerAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.WorkerService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkerActivity extends AppCompatActivity {

    public String service_id,service_name;
    Context context;
    List<WorkerService> workers;
    RecyclerView recyclerView;
    LinearLayout main;
    ProgressBar progressBar;
    public SharedPreferences settings;
    String couponCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("srviceName"));
        main=findViewById(R.id.worker_main);
        progressBar=findViewById(R.id.worker_progress);
        settings = getSharedPreferences("customer_app", Context.MODE_PRIVATE);
        couponCode = settings.getString("code","nocode");

        service_id = getIntent().getStringExtra("srviceID");
        service_name = getIntent().getStringExtra("srviceName");
        context = this;

        recyclerView = findViewById(R.id.worker_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        main.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        getWorkers();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    void getWorkers(){
        StringRequest request = new StringRequest(Request.Method.POST,
//                "http://www.dhakasetup.com/api/prop.php",
                UrlList.workerList,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("20619", "onResponse: "+response);
                        try {
                            workers = new ArrayList<>();
                            JSONArray rootArray = new JSONArray(response);
                            for (int i=0; i<rootArray.length(); i++){
                                JSONObject ob = rootArray.getJSONObject(i);
                                WorkerService worker = new WorkerService();
                                worker.setName(ob.getString("wname"));
                                worker.setMobile(ob.getString("wmobile"));
                                worker.setRating(ob.getString("rating"));
                                worker.setPrice(ob.getString("wsprice"));
                                worker.setWorker_id(ob.getString("wid"));
                                workers.add(worker);
                            }
                            WorkerAdapter workerAdapter = new WorkerAdapter(context,workers);
                            recyclerView.setAdapter(workerAdapter);
                            //main.setVisibility(View.VISIBLE);
                            //progressBar.setVisibility(View.GONE);
                            getDiscount();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("service_id", service_id);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    void getDiscount(){
        StringRequest request = new StringRequest(Request.Method.POST,
//                "http://www.dhakasetup.com/api/prop.php",
                UrlList.discount,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("2020", "onResponse: "+response);
                        try {
                            JSONArray rootArray = new JSONArray(response);
                            if (rootArray.length()==0){
                                settings.edit().putString("code","nocode").commit();
                                settings.edit().putString("percent", null).commit();
                                settings.edit().putString("amount", null).commit();

                            } else {
                                JSONObject ob = rootArray.getJSONObject(0);
                                String start = ob.getString("start_date");
                                String end = ob.getString("end_date");
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date startDate = sdf.parse(start);
                                Date endDate = sdf.parse(end);
                                Date date = new Date();

                                Log.d("20619", date + "-" + startDate + "-" + endDate);
                                if (!date.before(startDate) && !date.after(endDate)) {
                                    settings.edit().putString("percent", ob.getString("percent")).commit();
                                    settings.edit().putString("amount", ob.getString("max_amount")).commit();
                                } else {
                                    settings.edit().putString("percent", null).commit();
                                    settings.edit().putString("amount", null).commit();
                                }
                            }

                            main.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("code", couponCode);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }


}
