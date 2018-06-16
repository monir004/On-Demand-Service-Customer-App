package com.dhakasetup.sakib.dhakasetupprototype;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.accountkit.ui.LoginType;

public class ProfileFragment extends Fragment {

    LinearLayout loginLayout, logoutLayout;
    Button loginBtn,logoutBtn;
    TextView phoneTv,useridTv;
    String userid,phoneNum;
    SharedPreferences settings;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        loginLayout = view.findViewById(R.id.login);
        logoutLayout = view.findViewById(R.id.logout);
        loginBtn = view.findViewById(R.id.loginBtn);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        phoneTv = view.findViewById(R.id.phoneTextView);
        useridTv = view.findViewById(R.id.useridTextView);
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

        return view;
    }

    public void setProfile(String userid,String phoneNum){
        useridTv.setText(userid);
        phoneTv.setText(phoneNum);
        loginLayout.setVisibility(View.VISIBLE);
        logoutLayout.setVisibility(View.GONE);
    }
}
