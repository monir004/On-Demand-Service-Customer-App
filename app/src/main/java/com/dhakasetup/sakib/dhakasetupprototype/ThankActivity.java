package com.dhakasetup.sakib.dhakasetupprototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThankActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView time,date,order;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank);

        toolbar = findViewById(R.id.thank_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order placed");

        btn = findViewById(R.id.thank_cont);
        time = findViewById(R.id.thank_time);
        date = findViewById(R.id.thank_date);
        order = findViewById(R.id.thank_order);

        String within_time = getIntent().getStringExtra("within_time");
        String time1 = getIntent().getStringExtra("time");
        String day = getIntent().getStringExtra("day");
        String orderid = getIntent().getStringExtra("orderid");
        order.setText("Order ID : "+orderid);
        if (day != null){
            date.setText(day);
        }
        if (within_time != null){
            time.setText(within_time);
        }
        if (time1 != null){
            time.setText(time1);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThankActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
