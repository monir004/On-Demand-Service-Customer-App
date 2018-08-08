package com.dhakasetup.sakib.dhakasetupprototype;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.accountkit.ui.LoginType;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    LinearLayout loginLayout, logoutLayout;
    Button loginBtn,logoutBtn,editBtn;
    TextView phoneTv;
    EditText usernameET,addressET,emailET;
    String userid,phoneNum;
    SharedPreferences settings;
    Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        loginLayout = view.findViewById(R.id.login);
        logoutLayout = view.findViewById(R.id.logout);
        loginBtn = view.findViewById(R.id.loginBtn);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        editBtn = view.findViewById(R.id.editBtn);
        phoneTv = view.findViewById(R.id.phoneTextView);
        usernameET = view.findViewById(R.id.usernameET);
        addressET = view.findViewById(R.id.addressET);
        emailET = view.findViewById(R.id.emailEditText);
        context = getActivity();
        settings = getActivity().getSharedPreferences("dhakasetup",Context.MODE_PRIVATE);
        userid = settings.getString("userid",null);
        phoneNum = settings.getString("phone",null);
        if(userid != null){
            setProfile(userid,phoneNum);
        }
        else {
            loginLayout.setVisibility(View.GONE);
            logoutLayout.setVisibility(View.VISIBLE);
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).startLoginPage(LoginType.PHONE);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.edit().clear().commit();
                loginLayout.setVisibility(View.GONE);
                logoutLayout.setVisibility(View.VISIBLE);
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editBtn.getText().equals("SAVE")){
                    usernameET.setFocusable(false);
                    emailET.setFocusable(false);
                    addressET.setFocusable(false);
                    editBtn.setText("UPDATE");
                    editBtn.setBackgroundColor(Color.parseColor("#FF5722"));
                    userid = getActivity().getSharedPreferences("dhakasetup",Context.MODE_PRIVATE).getString("userid",null);
                    profilepost(userid,
                                usernameET.getText().toString(),
                                phoneTv.getText().toString(),
                                emailET.getText().toString(),
                                addressET.getText().toString(),"1");
                }
                else {
                    usernameET.setFocusableInTouchMode(true);
                    emailET.setFocusableInTouchMode(true);
                    addressET.setFocusableInTouchMode(true);
                    editBtn.setText("SAVE");
                    editBtn.setBackgroundColor(Color.GRAY);
                }
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().show();

        return view;
    }

    public void setProfile(final String userid, final String phoneNum){
        //useridTv.setText(userid);
        phoneTv.setText(phoneNum);
        loginLayout.setVisibility(View.VISIBLE);
        logoutLayout.setVisibility(View.GONE);
        Log.d("profileres","profile api pre loaded "+phoneNum);

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
                                usernameET.setText(first_name);
                                addressET.setText(address);
                                emailET.setText(email);
                                SharedPreferences settings = getActivity().getSharedPreferences("dhakasetup",Context.MODE_PRIVATE);
                                settings.edit().putString("name",first_name).commit();
                                settings.edit().putString("address",address).commit();
                                settings.edit().putString("email",email).commit();

                            }
                            else {
                                profilepost(userid,"---",phoneNum,"---","---","0");

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
                            setProfile(uid,phone);


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
    }
}
