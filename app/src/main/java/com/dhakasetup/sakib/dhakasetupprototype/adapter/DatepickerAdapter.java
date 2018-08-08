package com.dhakasetup.sakib.dhakasetupprototype.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhakasetup.sakib.dhakasetupprototype.R;
import com.dhakasetup.sakib.dhakasetupprototype.ScheduleActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DatepickerAdapter extends RecyclerView.Adapter <DatepickerAdapter.DateVH>{

    private String date,month,year,day;
    private Context context;
    Calendar cal;
    boolean[] selected;

    public DatepickerAdapter(Context context){
        this.context = context;
        selected = new boolean[7];
    }

    @NonNull
    @Override
    public DateVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.datepicker_item,parent,false);
        return new DateVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DateVH holder, final int position) {
        setup(holder,position);
        if (selected[position]){
            holder.root.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            holder.date.setTextColor(context.getResources().getColor(R.color.white));
            holder.month.setTextColor(context.getResources().getColor(R.color.white));
            holder.day.setTextColor(context.getResources().getColor(R.color.white));
            ((ScheduleActivity)context).workdate = holder.date.getText().toString()+holder.month.getText().toString()+holder.day.getText().toString();
        }
        else {
            holder.root.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            holder.date.setTextColor(context.getResources().getColor(R.color.grey));
            holder.month.setTextColor(context.getResources().getColor(R.color.grey));
            holder.day.setTextColor(context.getResources().getColor(R.color.grey));
        }
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected[position] = !selected[position];
                for (int i = 0; i < 7; i++) {
                    if (i != position){
                        selected[i] = false;
                    }
                }
                ((ScheduleActivity)context).workdate = null;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public void setup(DateVH holder, int position){
        cal= Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date d = new Date();
        /*try {

            d = sdf.parse("2018-08-29 8:34:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

        cal.add(Calendar.DATE,position);

        year = new SimpleDateFormat("yyyy").format(cal.getTime());
        month = new SimpleDateFormat("MMM").format(cal.getTime());
        date = new SimpleDateFormat("dd").format(cal.getTime());
        day = new SimpleDateFormat("EEE").format(cal.getTime());

        holder.date.setText(date);
        holder.month.setText(month);
        holder.day.setText(day);

    }

    public class DateVH extends RecyclerView.ViewHolder{
        TextView day,month,date;
        CardView root;
        public DateVH(View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.month);
            date = itemView.findViewById(R.id.date);
            day = itemView.findViewById(R.id.day);
            root = itemView.findViewById(R.id.layout_datepicker);
        }
    }
}
