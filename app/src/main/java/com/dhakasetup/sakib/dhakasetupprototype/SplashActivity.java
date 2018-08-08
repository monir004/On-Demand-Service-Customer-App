package com.dhakasetup.sakib.dhakasetupprototype;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    public static boolean LOAD = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Data.getCart(this).clear();
        Data.getInstance(this).load(this);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (LOAD){
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    handler.postDelayed(this, 500);
                }
            }
        };

        handler.postDelayed(runnable, 1000);
    }


}
