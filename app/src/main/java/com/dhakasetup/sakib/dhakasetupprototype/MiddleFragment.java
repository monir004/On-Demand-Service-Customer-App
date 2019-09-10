package com.dhakasetup.sakib.dhakasetupprototype;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class MiddleFragment extends Fragment {


    private LinearLayout profile,notification,promo;
    private TextView nameTv,phoneTv,versionTv;
    Button logout_btn;
    SharedPreferences setting;

    public MiddleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_middle, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Profile");
        profile = view.findViewById(R.id.middle_profile);
        notification = view.findViewById(R.id.notification_btn);
        promo = view.findViewById(R.id.promo_btn);
        nameTv = view.findViewById(R.id.middle_name);
        phoneTv = view.findViewById(R.id.middle_phone);
        logout_btn = view.findViewById(R.id.logout_btn);
        versionTv = view.findViewById(R.id.app_version);
        versionTv.setText("v  "+BuildConfig.VERSION_NAME);
        setting = getActivity().getSharedPreferences("customer_app",Context.MODE_PRIVATE);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(((AppCompatActivity)getActivity()),ProfileActivity.class);
                startActivity(intent);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),NotificationActivity.class);
                startActivity(intent);
            }
        });
        promo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),PromoActivity.class);
                startActivity(intent);
            }
        });
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tokenClear();


            }
        });
        //getProfile(setting.getString("userid",null),setting.getString("phone",null));
        loginPoint(setting.getString("mobile",null));
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        nameTv.setText(setting.getString("name",null));
        phoneTv.setText(setting.getString("mobile",null));
    }

    public void loginPoint(final String mobile){
        StringRequest request = new StringRequest(Request.Method.POST,
                UrlList.customerLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("20619",response);
                            JSONArray rootArray = new JSONArray(response);
                            JSONObject result = rootArray.getJSONObject(0);

                            setting.edit().putString("customer_id",result.getString("customer_id")).commit();
                            setting.edit().putString("email",result.getString("email")).commit();
                            setting.edit().putString("name",result.getString("name")).commit();
                            setting.edit().putString("address",result.getString("address")).commit();
                            setting.edit().putString("rating",result.getString("rating")).commit();
                            nameTv.setText(setting.getString("name",null));

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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("mobile",mobile);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }

    void tokenClear(){
        StringRequest request = new StringRequest(Request.Method.POST,
//                "http://www.dhakasetup.com/api/prop.php",
                UrlList.customerToken,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("2020", "onResponse: "+response);
                        try {

                            String token = setting.getString("token",null);
                            setting.edit().clear().commit();
                            setting.edit().putString("token",token).commit();

                            Intent intent = new Intent(getContext(),MainActivity.class);
                            intent.putExtra("fragmentNumber",2);
                            startActivity(intent);

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
                params.put("mobile", setting.getString("mobile",null));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }

    /*public void getProfile(final String userid, final String mobile){
        //useridTv.setText(userid);
        phoneTv.setText(mobile);
        Log.d("profileres","profile api pre loaded "+mobile);

        StringRequest request = new StringRequest(Request.Method.GET,
                "http://www.dhakasetup.com/api/profileget.php?uid="+userid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            boolean found = res.getBoolean("found");
                            if (found){
                                String first_name = res.getString("first_name");
                                String email = res.getString("email");
                                String address = res.getString("address");
                                nameTv.setText(first_name);
                                SharedPreferences settings = getActivity().getSharedPreferences("dhakasetup",Context.MODE_PRIVATE);
                                settings.edit().putString("name",first_name).commit();
                                settings.edit().putString("address",address).commit();
                                settings.edit().putString("email",email).commit();

                            }
                            else {
                                profilepost(userid,"---",mobile,"---","---","0");

                            }
                            Log.d("profileres",response);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }

    public void profilepost(final String uid, final String firstname, final String phone, final String email, final String address, final String action){
        StringRequest request = new StringRequest(Request.Method.POST,
                "http://www.dhakasetup.com/api/profilepost.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("profileres",response);
                            getProfile(uid,phone);


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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("uid",uid);
                params.put("firstname",firstname);
                params.put("phone",phone);
                params.put("email",email);
                params.put("address",address);
                params.put("action",action);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }*/

}
