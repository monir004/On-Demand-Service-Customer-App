package com.dhakasetup.sakib.dhakasetupprototype;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FcmInstanceId extends FirebaseInstanceIdService {
    SharedPreferences sharedPreferences;

    @Override
    public void onTokenRefresh() {
        String recentToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("20619", "onTokenRefresh: "+recentToken);
        sharedPreferences = getApplicationContext().getSharedPreferences("customer_app",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("token",recentToken).commit();
    }
}
