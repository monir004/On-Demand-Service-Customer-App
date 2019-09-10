package com.dhakasetup.sakib.dhakasetupprototype;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PromoActivity extends AppCompatActivity {

    EditText promoET;
    Button promo_btn;
    TextView promo_code,promo_duration,promo_percent;
    SharedPreferences settings;
    LinearLayout root;
    ProgressBar loader;
    String pcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);
        promoET = findViewById(R.id.promoET);
        promo_btn = findViewById(R.id.promo_btn);
        promo_code = findViewById(R.id.promo_code);
        promo_duration = findViewById(R.id.promo_duration);
        promo_percent = findViewById(R.id.promo_perecent);
        root = findViewById(R.id.promo_root);
        loader = findViewById(R.id.promo_loader);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Promo code");

        settings = getSharedPreferences("customer_app", Context.MODE_PRIVATE);

        pcode = settings.getString("code","nocode");
        getPromoCode();

        promo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.setVisibility(View.GONE);
                loader.setVisibility(View.VISIBLE);
                pcode = promoET.getText().toString();
                getPromoCode();
            }
        });
    }

    void getPromoCode(){
        StringRequest request = new StringRequest(Request.Method.POST,
//                "http://www.dhakasetup.com/api/prop.php",
                UrlList.discount,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("20619", "onResponse: "+response);
                        try {
                            JSONArray rootArray = new JSONArray(response);
                            if (rootArray.length()==0){
                                settings.edit().putString("code","nocode").commit();
                                Toast.makeText(PromoActivity.this,"No such promo code",Toast.LENGTH_SHORT).show();
                                root.setVisibility(View.GONE);
                                loader.setVisibility(View.GONE);
                                return;
                            }
                            for (int i=0; i<rootArray.length(); i++){
                                JSONObject ob = rootArray.getJSONObject(i);
                                String start = ob.getString("start_date");
                                String end = ob.getString("end_date");
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date startDate = sdf.parse(start);
                                Date endDate = sdf.parse(end);
                                Date date = new Date();

                                Log.d("20619", date+"-"+startDate+"-"+endDate);
                                if (!date.before(startDate) && !date.after(endDate)) {
                                    settings.edit().putString("code",ob.getString("coupon_code")).commit();
                                    Toast.makeText(PromoActivity.this,"Successfully Applied",Toast.LENGTH_SHORT).show();
                                    promo_code.setText(ob.getString("coupon_code"));
                                    promo_duration.setText("From "+
                                            ob.getString("start_date")+" to "+
                                            ob.getString("end_date"));
                                    promo_percent.setText(ob.getString("percent")+"% discount upto max "+ob.getString("max_amount"));
                                    root.setVisibility(View.VISIBLE);
                                    loader.setVisibility(View.GONE);
                                } else {
                                    settings.edit().putString("code","nocode").commit();
                                    Toast.makeText(PromoActivity.this,"Invalid Coupon",Toast.LENGTH_SHORT).show();
                                    root.setVisibility(View.GONE);
                                    loader.setVisibility(View.GONE);
                                }
                            }

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
                params.put("code", pcode);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}
