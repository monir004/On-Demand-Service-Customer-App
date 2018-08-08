package com.dhakasetup.sakib.dhakasetupprototype;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dhakasetup.sakib.dhakasetupprototype.adapter.DatepickerAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {
    TabLayout tabs;
    ViewPager pager;
    Toolbar toolbar;
    SchedulePager adapter;
    TextView subtotal;
    Button confirm;

    Calendar cal;
    Date datetime;
    SimpleDateFormat formatter,day_format;
    String current_date,within_4,current_day,workdatetime;

    String orderid;

    public Calendar workdate;
    public String worktime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        cal = Calendar.getInstance();
        datetime = new Date();
        formatter = new SimpleDateFormat("h:mm a");
        day_format = new SimpleDateFormat("EEE, d MMM yyyy");
        current_date = formatter.format(datetime);
        current_day = day_format.format(datetime);
        cal.add(Calendar.HOUR,4);
        within_4 = formatter.format(cal.getTime());

        subtotal = findViewById(R.id.service1_item_subtotal);
        confirm = findViewById(R.id.place_order_btn);
        toolbar = findViewById(R.id.schedule_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Work will start at");

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);

        orderid = getIntent().getStringExtra("orderid");

        //BottomNavigationView mNavigationBottom = findViewById(R.id.bottom_navigation_bar);
//        mNavigationBottom.getMenu().setGroupCheckable(0, false, true);
//        mNavigationBottom.getMenu().setGroupCheckable(0, true, true);

        tabs = findViewById(R.id.schedule_tablayout);
        pager = findViewById(R.id.schedule_viewpager);


        tabs.addTab(tabs.newTab().setText("Within 4 hours"));
        tabs.addTab(tabs.newTab().setText("Pick a time"));

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));

        adapter = new SchedulePager(getSupportFragmentManager(),this);
        pager.setAdapter(adapter);

        subtotal.setText(nf.format(Data.getCart(this).total()));
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pager.getCurrentItem() == 0){
                    workdatetime = current_date+" - "+within_4;
                    //Toast.makeText(ScheduleActivity.this,workdatetime,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ScheduleActivity.this,ThankActivity.class);
                    intent.putExtra("within_time",workdatetime);
                    intent.putExtra("day",current_day);
                    intent.putExtra("orderid",orderid);
                    startActivity(intent);
                }
                else {
                    Calendar calendar = Calendar.getInstance();
                    if (worktime == null && workdate == null){
                        Toast.makeText(ScheduleActivity.this,"select date and time",Toast.LENGTH_SHORT).show();
                    }
                    else if (worktime == null ){
                        Toast.makeText(ScheduleActivity.this,"select a time",Toast.LENGTH_SHORT).show();
                    }
                    else if (workdate == null ){
                        Toast.makeText(ScheduleActivity.this,"select a date",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        workdatetime = workdate.getTime().toString()+";"+worktime;
                        //Toast.makeText(ScheduleActivity.this,workdatetime,Toast.LENGTH_SHORT).show();

                        SimpleDateFormat f1 = new SimpleDateFormat("EEE, d MMM yyyy");

                        Intent intent = new Intent(ScheduleActivity.this,ThankActivity.class);
                        intent.putExtra("time",worktime);
                        intent.putExtra("day",f1.format(workdate.getTime()));
                        intent.putExtra("orderid",orderid);
                        startActivity(intent);
                    }

                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    public static class WithinHour extends Fragment{
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_within_hour,container,false);
        }

        public static WithinHour newInstance(){
            return new WithinHour();
        }
    }

    public static class PickTimeFragment extends Fragment {

        DatePickerDialog datePickerDialog;
        TextView date_tv;
        LinearLayout layout1,layout2;
        RecyclerView recyclerView;
        DatepickerAdapter adapter;
        public CardView timecard[] = new CardView[4];
        public TextView timetext[] = new TextView[4];
        public boolean time[] = new boolean[4];

        Context context;

        public void setContext(Context context) {
            this.context = context;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_pick_time,container,false);


            recyclerView = view.findViewById(R.id.datepicker_recycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
            recyclerView.setHasFixedSize(true);

            adapter = new DatepickerAdapter(getContext(),this);
            recyclerView.setAdapter(adapter);

            timecard[0] = view.findViewById(R.id.time1);
            timecard[1] = view.findViewById(R.id.time2);
            timecard[2] = view.findViewById(R.id.time3);
            timecard[3] = view.findViewById(R.id.time4);

            timetext[0] = view.findViewById(R.id.time1text);
            timetext[1] = view.findViewById(R.id.time2text);
            timetext[2] = view.findViewById(R.id.time3text);
            timetext[3] = view.findViewById(R.id.time4text);


            for (int i=0; i<4; i++){
                setup(i);
            }



            return view;
        }

        private void setup(final int i) {
            timecard[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(time[i]){
                        ((ScheduleActivity)context).worktime = null;
                    }
                    else if (i==0){
                        ((ScheduleActivity)context).worktime = "9:00 AM - 12:00 PM";
                    }
                    else if (i==1){
                        ((ScheduleActivity)context).worktime = "12:00 PM - 3:00 PM";
                    }
                    else if (i==2){
                        ((ScheduleActivity)context).worktime = "3:00 PM - 6:00 PM";
                    }
                    else{
                        ((ScheduleActivity)context).worktime = "6:00 PM - 9:00 PM";
                    }

                    for (int j=0; j<4; j++){
                        if (j==i && !time[j]){
                            time[j] = true;
                            timecard[j].setCardBackgroundColor(getActivity().getResources().getColor(R.color.colorAccent));
                            timetext[j].setTextColor(getActivity().getResources().getColor(R.color.white));
                        }
                        else {
                            time[j] = false;
                            timecard[j].setCardBackgroundColor(getActivity().getResources().getColor(R.color.white));
                            timetext[j].setTextColor(getActivity().getResources().getColor(R.color.grey));
                        }
                    }
                }
            });
        }

        public static PickTimeFragment newInstance(){
            return new PickTimeFragment();
        }
    }

    public static class SchedulePager extends FragmentPagerAdapter {
        Context context;

        public SchedulePager(FragmentManager fm,Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0){
                return WithinHour.newInstance();
            }
            else{

                PickTimeFragment fragment = PickTimeFragment.newInstance();
                fragment.setContext(context);
                return fragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return "Within 2 hours";
            else
                return "Pick a time";
        }
    }


}


