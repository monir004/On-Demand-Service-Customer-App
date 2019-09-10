package com.dhakasetup.sakib.dhakasetupprototype;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.HistoryAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.MainAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.OrderAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.SubcatAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Order;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.OrderCustomer;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.OrderItem;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Subcat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.facebook.accountkit.internal.AccountKitController.getApplicationContext;

public class OrderFragment extends Fragment {
    Context context;
    List<OrderCustomer> orderCustomers;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    SharedPreferences settings;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container,false);
        settings = getActivity().getSharedPreferences("customer_app", Context.MODE_PRIVATE);
        //orderLoad(getActivity());
        context = getActivity();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Order History");

        progressBar = view.findViewById(R.id.history_loader);
        recyclerView = view.findViewById(R.id.history_recycler);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        getOrder();
        return view;

    }

    void getOrder(){
        StringRequest request = new StringRequest(Request.Method.POST,
//                "http://www.dhakasetup.com/api/prop.php",
                UrlList.orderAll,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("20619", "getOrder: "+response);
                        try {
                            JSONArray rootArray = new JSONArray(response);

                            orderCustomers = new ArrayList<>();
                            if (rootArray.length()>0){
                                //no_order.setVisibility(View.VISIBLE);
                                //order_progress.setVisibility(View.GONE);


                                for (int i=0; i<rootArray.length(); i++){
                                    OrderCustomer order = new OrderCustomer();
                                    JSONObject result = rootArray.getJSONObject(i);
                                    order.setServiceName(result.getString("sname"));
                                    order.setOrder_id(result.getString("orid"));
                                    order.setNet(result.getString("onet"));
                                    order.setStatus(result.getString("ostatus"));
                                    orderCustomers.add(order);
                                }
                            }


                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            OrderAdapter adapter = new OrderAdapter(context,orderCustomers);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));

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
                params.put("customer_id", settings.getString("customer_id","-1"));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }




}
