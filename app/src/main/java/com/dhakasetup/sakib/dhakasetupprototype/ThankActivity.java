package com.dhakasetup.sakib.dhakasetupprototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThankActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView order_tv,service,price,workername,workermobile;
    ProgressBar progressBar;
    LinearLayout rootLayout;
    Button btn;
    String orderid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank);

        toolbar = findViewById(R.id.thank_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order placed");

        btn = findViewById(R.id.thank_cont);
        progressBar = findViewById(R.id.thank_progress);
        rootLayout=findViewById(R.id.thank_root);
        order_tv=findViewById(R.id.thank_order_id);
        service=findViewById(R.id.thank_service);
        price=findViewById(R.id.thank_price);
        workername=findViewById(R.id.thank_worker);
        workermobile=findViewById(R.id.thank_mobile);

        rootLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);



        orderid = getIntent().getStringExtra("order_id");
        order_tv.setText(orderid);
        /*
        order.setText("Order ID : "+orderid);
        if (day != null){
            date.setText(day);
        }
        if (within_time != null){
            time.setText(within_time);
        }
        if (time1 != null){
            time.setText(time1);
        }
        */

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OrderActivity.class);
                intent.putExtra("order_id",orderid);
                startActivity(intent);
            }
        });

        getOrder();
    }

    void getOrder(){
        StringRequest request = new StringRequest(Request.Method.POST,
//                "http://www.dhakasetup.com/api/prop.php",
                UrlList.orderGet,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("20619", "getOrder: "+response);
                        try {
                            JSONArray rootArray = new JSONArray(response);
                            JSONObject result = rootArray.getJSONObject(0);

                            service.setText(result.getString("sname"));
                            price.setText(result.getString("onet"));
                            workername.setText(result.getString("wname"));
                            workermobile.setText(result.getString("wmobile"));
                            progressBar.setVisibility(View.GONE);
                            rootLayout.setVisibility(View.VISIBLE);

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
}
