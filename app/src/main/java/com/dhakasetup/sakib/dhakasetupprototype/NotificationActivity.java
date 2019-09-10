package com.dhakasetup.sakib.dhakasetupprototype;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.NotificationAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.model.Notification_Item;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {
    RecyclerView recycler;
    ProgressBar progressBar;
    Toolbar toolbar;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notifications");

        recycler = findViewById(R.id.notification_recycler);
        progressBar = findViewById(R.id.loading_notification);
        context = this;

        recycler.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        loadNotifications(getSharedPreferences("dhakasetup",MODE_PRIVATE).getString("phone",null));
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (isTaskRoot()){
            Intent intent = new Intent(this,SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else
            finish();
        return true;
    }


    public void loadNotifications(final String phone){
        Log.d("profileres", "NotificationActivity: "+phone);
        StringRequest request = new StringRequest(Request.Method.POST,
                "http://dhakasetup.com/api/notification.php?phone='"+phone+"'",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<Notification_Item> notifications = new ArrayList<>();
                        try {
                            //Log.d("profileres",response);
                            JSONObject rootObject = new JSONObject(response);
                            JSONArray data = rootObject.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++){
                                JSONObject item = data.getJSONObject(i);
                                Notification_Item notification_item = new Notification_Item();
                                notification_item.setMessage(item.getString("message"));
                                notification_item.setDate(item.getString("created_at"));
                                notifications.add(notification_item);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.GONE);
                        NotificationAdapter adapter = new NotificationAdapter(notifications);
                        recycler.hasFixedSize();
                        recycler.setLayoutManager(new LinearLayoutManager(context));
                        recycler.setAdapter(adapter);
                        recycler.setVisibility(View.VISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String s = "";
                try {
                    s = new String(error.networkResponse.data,"UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("profileres",String.valueOf(error.networkResponse.statusCode+s));
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("phone",phone);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        request.setShouldCache(false);
        queue.add(request);
    }

}
