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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.HistoryAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.MainAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.adapter.SubcatAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Order;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.OrderItem;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Subcat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderFragment extends Fragment {
    Context context;
    TextView tv;
    TabLayout tabs;
    ViewPager pager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order,container,false);

        orderLoad(getActivity());

        tabs = view.findViewById(R.id.order_tablayout);
        pager = view.findViewById(R.id.order_viewpager);


        tabs.addTab(tabs.newTab().setText("On Going Orders"));
        tabs.addTab(tabs.newTab().setText("Order History"));

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));


        return view;

    }

    public void orderLoad(final Context context) {
        SharedPreferences settings = context.getSharedPreferences("dhakasetup",Context.MODE_PRIVATE);
        String userid = settings.getString("userid",null);
        if (userid == null)
            return;
        String url = "http://www.dhakasetup.com/api/order/orderget.php?uid=" + userid;
        StringRequest request = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<Order> orders = new ArrayList<>();
                            Log.d("profileres", response);
                            JSONObject object = new JSONObject(response);
                            boolean found = object.getBoolean("found");
                            if(!found){
                                Data.getOrderPacket(context).setOrders(orders);
                                return;
                            }
                            JSONArray array = object.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++){
                                JSONObject orderObj = array.getJSONObject(i);
                                int order_sl = Integer.parseInt(orderObj.getString("order_sl"));
                                int total_am = Integer.parseInt(orderObj.getString("total_am"));
                                int disc_am = Integer.parseInt(orderObj.getString("disc_am"));
                                int net_am = Integer.parseInt(orderObj.getString("net_am"));
                                int paid_am = Integer.parseInt(orderObj.getString("paid_am"));
                                int due_am = Integer.parseInt(orderObj.getString("due_am"));
                                String oauth_uid = orderObj.getString("oauth_uid");
                                String order_id = orderObj.getString("order_id");
                                String status = orderObj.getString("status");
                                String d_address = orderObj.getString("d_address");
                                String d_date = orderObj.getString("d_date");
                                String d_timerange = orderObj.getString("d_timerange");
                                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String time1 = orderObj.getString("open_time");
                                String time2 = orderObj.getString("close_time");
                                Date open_time = new Date();
                                Date close_time = new Date();
                                try {
                                    open_time = fmt.parse(time1);
                                    close_time = fmt.parse(time2);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Order order = new Order(order_sl,total_am,disc_am,net_am,paid_am,due_am,
                                        oauth_uid,order_id,status,d_address,d_date,d_timerange,
                                        open_time,close_time);
                                JSONArray itemsArray = orderObj.getJSONArray("items");
                                List<OrderItem> items = new ArrayList<>();
                                for (int j = 0; j < itemsArray.length(); j++){
                                    JSONObject orderItemObj = itemsArray.getJSONObject(j);
                                    Log.d("profileres", orderItemObj.toString());
                                    String id = orderItemObj.getString("id");
                                    String order_sl_fk = orderItemObj.getString("order_sl");
                                    String srv_sl = orderItemObj.getString("srv_sl");
                                    String srvice = orderItemObj.getString("srvice");
                                    String srvImage = orderItemObj.getString("srvImage");
                                    String srvQty = orderItemObj.getString("srvQty");
                                    String srvPrice = orderItemObj.getString("srvPrice");
                                    String srvTotal = orderItemObj.getString("srvTotal");
                                    String prop_id = orderItemObj.getString("prop_id");
                                    String prop_name = orderItemObj.getString("prop_name");
                                    String prop_qty = orderItemObj.getString("prop_qty");
                                    String prop_price = orderItemObj.getString("prop_price");
                                    String prop_total = orderItemObj.getString("prop_total");

                                    OrderItem item = new OrderItem(id,order_sl_fk,srv_sl,srvQty,srvPrice,srvTotal,prop_id,prop_qty,prop_price,prop_total,srvice,srvImage,prop_name);
                                    items.add(item);

                                }
                                order.setOrderItems(items);
                                orders.add(order);

                            }
                            Data.getOrderPacket(context).setOrders(orders);
                            Log.d("profileres", "\norder set\n");

                            OrderPagerAdapter adapter = new OrderPagerAdapter(getChildFragmentManager());
                            pager.setAdapter(adapter);
                            tabs.setupWithViewPager(pager);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    public static class OrderPagerAdapter extends FragmentPagerAdapter{

        public OrderPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return HistoryFragment.newInstance(0);
            else
                return HistoryFragment.newInstance(1);
        }

        @Override
        public int getCount() {
            return 2;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return "On Going Order";
            else
                return "Order History";
        }
    }

    public static class HistoryFragment extends Fragment{
        private static final String ARG_PAGE_NUMBER = "page_number";

        public HistoryFragment() {
        }

        public static HistoryFragment newInstance(int page) {
            HistoryFragment fragment = new HistoryFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_PAGE_NUMBER, page);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_history, container, false);

//            TextView txt = (TextView) rootView.findViewById(R.id.page_number_label);
//            int page = getArguments().getInt(ARG_PAGE_NUMBER, -1);
//            txt.setText(String.format("Page %d", page));

            RecyclerView recyclerView = view.findViewById(R.id.history_recycler);
            HistoryAdapter adapter = new HistoryAdapter(getActivity(),getArguments().getInt(ARG_PAGE_NUMBER));
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//            MainAdapter adapter = new MainAdapter(getActivity(), data);
//            recyclerView.setAdapter(adapter);
//            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            return view;
        }
    }
}
