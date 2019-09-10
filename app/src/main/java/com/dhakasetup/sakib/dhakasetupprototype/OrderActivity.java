package com.dhakasetup.sakib.dhakasetupprototype;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
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
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.CartAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.HistoryItemAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.OrderAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Order;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.OrderCustomer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.facebook.accountkit.internal.AccountKitController.getApplicationContext;

public class OrderActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView orderid_tv,servicename,worker,mobile,address,price,status,datetv,quantity;
    ProgressBar progressBar;
    String orderid;
    LinearLayout root;
    Context context;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderid_tv = findViewById(R.id.order_id);
        servicename = findViewById(R.id.order_service);
        worker = findViewById(R.id.order_worker);
        mobile = findViewById(R.id.order_mobile);
        address = findViewById(R.id.order_address);
        price = findViewById(R.id.order_price);
        status = findViewById(R.id.order_status);
        datetv = findViewById(R.id.order_date);
        quantity = findViewById(R.id.order_qty);
        progressBar = findViewById(R.id.order_loader);
        ratingBar = findViewById(R.id.worker_rating_bar);
        root = findViewById(R.id.order_root);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Back to orders");

        orderid = getIntent().getStringExtra("order_id");
        context=this;

        progressBar.setVisibility(View.VISIBLE);
        root.setVisibility(View.GONE);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingChange(String.valueOf(rating));
            }
        });
        getOrder();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(this,MainActivity.class);
                intent.putExtra("fragmentNumber",3);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }


    void getOrder(){
        StringRequest request = new StringRequest(Request.Method.POST,
                UrlList.orderGet,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("20619", "getOrder: "+response);
                        try {
                            JSONArray rootArray = new JSONArray(response);
                            JSONObject obj = rootArray.getJSONObject(0);

                            orderid_tv.setText(obj.getString("orid"));
                            status.setText(obj.getString("ostatus"));
                            servicename.setText(obj.getString("sname"));
                            worker.setText(obj.getString("wname"));
                            mobile.setText(obj.getString("wmobile"));
                            address.setText(obj.getString("oloc"));
                            datetv.setText(obj.getString("odate"));
                            price.setText(obj.getString("onet"));
                            quantity.setText(obj.getString("oqty"));
                            if (obj.isNull("orworker"))
                                ratingBar.setRating(new Float(0.00));
                            else
                                ratingBar.setRating(Float.valueOf(obj.getString("orworker")));

                            if (status.getText().toString().equals("placed")){
                                status.setBackgroundColor(ContextCompat.getColor(context,R.color.served));
                            }
                            else{
                                status.setBackgroundColor(ContextCompat.getColor(context,R.color.cancelled));
                            }

                            progressBar.setVisibility(View.GONE);
                            root.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("order_id", orderid);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    void ratingChange(final String rating){
        StringRequest request = new StringRequest(Request.Method.POST,
//                "http://www.dhakasetup.com/api/prop.php",
                UrlList.rating,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("2020", "onResponse: "+response);
                        try {


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
                params.put("order_id", orderid);
                params.put("rating", rating);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(OrderActivity.this);
        queue.add(request);
    }
}
