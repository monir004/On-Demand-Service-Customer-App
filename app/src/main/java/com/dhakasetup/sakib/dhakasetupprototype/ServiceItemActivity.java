package com.dhakasetup.sakib.dhakasetupprototype;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.ServiceItemAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.WorkerAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Cart;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Category;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Service;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.ServiceProp;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.WorkerService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceItemActivity extends AppCompatActivity {
    Toolbar toolbar;
    Context context;
    NumberFormat nf;
    LinearLayout no_prop;
    RecyclerView recyclerView;
    ImageButton plus_no,minus_no,add_more;
    TextView count_tv_no;
    public TextView subtotal,savings,net;
    List<Category> categories;
    public int cart_counter = 0;
    SharedPreferences settings;
    TextView servicetv,workertv,mobiletv,addresstv,datetv;

    String srviceID,srviceName,worker_id,worker_name,worker_mobile,worker_price,customer_id,customer_name,customer_address;
    Double discount,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_item);
        subtotal = findViewById(R.id.service1_item_subtotal);
        savings = findViewById(R.id.service1_item_savings2);
        net = findViewById(R.id.service1_item_savings);
        no_prop = findViewById(R.id.service1_item_no_prop);
        recyclerView = findViewById(R.id.service1_item_recycler);
        add_more = findViewById(R.id.service1_item_add_more);
        plus_no = findViewById(R.id.service1_item_plus_no);
        minus_no = findViewById(R.id.service1_item_minus_no);
        count_tv_no = findViewById(R.id.service1_item_count_no);
        servicetv = findViewById(R.id.service1_service_name);
        workertv =findViewById(R.id.service1_worker_name);
        mobiletv=findViewById(R.id.service1_worker_mobile);
        addresstv=findViewById(R.id.service1_customer_address);
        datetv = findViewById(R.id.service1_delivery_date);
        count_tv_no.setText("0");
        nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        context = this;
        //subtotal.setText(nf.format(Data.getCart(context).total()));

        categories = Data.getInstance(this).getData();

         srviceID = getIntent().getStringExtra("srviceID");
         srviceName = getIntent().getStringExtra("srviceName");
         worker_id = getIntent().getStringExtra("worker_id");
         worker_name = getIntent().getStringExtra("worker_name");
         worker_mobile = getIntent().getStringExtra("worker_mobile");
         worker_price = getIntent().getStringExtra("worker_price");

        settings = getSharedPreferences("customer_app", Context.MODE_PRIVATE);
        customer_id = settings.getString("customer_id",null);
        customer_name = settings.getString("name",null);
        customer_address = settings.getString("address",null);

        discount = Double.parseDouble(settings.getString("percent","0"))/100;
        amount = Double.parseDouble(settings.getString("amount","0"));

        servicetv.setText(srviceName);
        workertv.setText(worker_name);
        mobiletv.setText(worker_mobile);
        addresstv.setText(customer_address);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        datetv.setText(df.format(c));

        final int srvID = Integer.parseInt(srviceID);
        final Service service = Data.getInstance(this).getService(srvID);
        int prop_num=0;
        if (service.getServiceProps()!=null){
            prop_num = service.getServiceProps().size();
        }
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
                    /*if (count_tv_no.getText().equals("0")){
                        service.setCount(0);
                        Data.getCart(context).addService(service);
                        invalidateOptionsMenu();
                    }
                    Data.getCart(context).getService(srvID).incCount();
                    count_tv_no.setText(String.valueOf(Data.getCart(context).getService(srvID).getCount()));
                    //subtotal.setText(nf.format(Data.getCart(context).total()));*/

                    Integer counter = Integer.parseInt(count_tv_no.getText().toString());
                    counter++;
                    count_tv_no.setText(counter.toString());

                    Double qty = Double.parseDouble(count_tv_no.getText().toString());
                    Double price = Double.parseDouble(worker_price);

                    Double subtot = price*qty;
                    Double saved = (subtot*discount);
                    if (saved > amount)
                        saved = amount;
                    Double netAm = subtot - saved;

                    subtotal.setText(String.valueOf(subtot));
                    savings.setText(String.valueOf(saved));
                    net.setText(String.valueOf(netAm));
                }
            });

            minus_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count_tv_no.getText().equals("0")) return;
                    /*if (count_tv_no.getText().equals("1")){
                        count_tv_no.setText("0");
                        Data.getCart(context).removeService(Integer.parseInt(service.getSrv_sl()));
                        invalidateOptionsMenu();
                    }
                    else {
                        Data.getCart(context).getService(Integer.parseInt(service.getSrv_sl())).decCount();
                        count_tv_no.setText(String.valueOf(Data.getCart(context).getService(srvID).getCount()));
                    }
                    subtotal.setText(nf.format(Data.getCart(context).total()));*/

                    Integer counter = Integer.parseInt(count_tv_no.getText().toString());
                    counter--;
                    count_tv_no.setText(counter.toString());

                    Double qty = Double.parseDouble(count_tv_no.getText().toString());
                    Double price = Double.parseDouble(worker_price);


                    Double subtot = price*qty;
                    Double saved = (subtot*discount);
                    if (saved > amount)
                        saved = amount;
                    Double netAm = subtot - saved;

                    subtotal.setText(String.valueOf(subtot));
                    savings.setText(String.valueOf(saved));
                    net.setText(String.valueOf(netAm));
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

                //Intent intent = new Intent(context,WorkerActivity.class);
                //intent.putExtra("srviceID",getIntent().getStringExtra("srviceID"));
                //startActivity(intent);
                Toast.makeText(ServiceItemActivity.this,"Please wait!",Toast.LENGTH_SHORT).show();
                postOrder();
            }
        });

    }


    void postOrder(){
        StringRequest request = new StringRequest(Request.Method.POST,
//                "http://www.dhakasetup.com/api/prop.php",
                UrlList.orderPost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("20619", "postOrder: "+response);
                        try {
                            JSONObject ob = new JSONObject(response);
                            Intent intent = new Intent(ServiceItemActivity.this,ThankActivity.class);
                            intent.putExtra("order_id",ob.getString("order_id"));
                            startActivity(intent);

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
                params.put("delivery_location", customer_address);
                params.put("delivery_date", datetv.getText().toString());
                params.put("total_amount",subtotal.getText().toString());
                params.put("discount_amount",savings.getText().toString());
                params.put("net_amount",net.getText().toString());
                params.put("qty",count_tv_no.getText().toString());
                params.put("customer_id",customer_id);
                params.put("worker_id",worker_id);
                params.put("service_id",srviceID);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
        getMenuInflater().inflate(R.menu.menu_main,menu);

        MenuItem menuItem = menu.findItem(R.id.action_cart);
        menuItem.setIcon(MenuPainter.paint(ServiceItemActivity.this,Data.getCart(this).size(),R.drawable.ic_action_cart));
        */
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
