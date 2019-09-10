package com.dhakasetup.sakib.dhakasetupprototype.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dhakasetup.sakib.dhakasetupprototype.R;
import com.dhakasetup.sakib.dhakasetupprototype.ServiceItemActivity;
import com.dhakasetup.sakib.dhakasetupprototype.WorkerActivity;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.WorkerService;

import java.util.List;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.WorkerVH> {

    Context context;
    List<WorkerService> workers;


    public WorkerAdapter(Context con, List<WorkerService> workerServices) {
        context =con;
        workers=workerServices;
    }

    @NonNull
    @Override
    public WorkerVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.worker_item,viewGroup,false);
        return new WorkerAdapter.WorkerVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerVH workerVH, final int i) {
        WorkerService worker = workers.get(i);
        workerVH.mobile.setText(worker.getMobile());
        workerVH.name.setText(worker.getName());
        workerVH.rating.setText(worker.getRating()+"⭐");
        workerVH.price.setText(worker.getPrice());

        workerVH.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,workers.get(i).getName(),Toast.LENGTH_SHORT).show();
                WorkerActivity activity = (WorkerActivity)context;
                Intent intent = new Intent(context, ServiceItemActivity.class);
                intent.putExtra("srviceID",activity.service_id);
                intent.putExtra("srviceName",activity.service_name);
                intent.putExtra("worker_id",workers.get(i).getWorker_id());
                intent.putExtra("worker_name",workers.get(i).getName());
                intent.putExtra("worker_mobile",workers.get(i).getMobile());
                intent.putExtra("worker_price",workers.get(i).getPrice());

                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return workers.size();
    }

    public class WorkerVH extends RecyclerView.ViewHolder{
        TextView name,mobile,rating,price;
        RelativeLayout root;
        public WorkerVH(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.worker_root);
            name = itemView.findViewById(R.id.worker_name);
            mobile = itemView.findViewById(R.id.worker_mobile);
            rating = itemView.findViewById(R.id.worker_rating);
            price = itemView.findViewById(R.id.worker_price);
        }
    }
}
