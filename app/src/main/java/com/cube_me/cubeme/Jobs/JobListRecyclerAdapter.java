package com.cube_me.cubeme.Jobs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cube_me.cubeme.R;

import java.util.List;

/**
 * Created by FredrickCyril on 10/6/16.
 */

public class JobListRecyclerAdapter extends RecyclerView.Adapter<JobListRecyclerAdapter.JobListViewHolder> {

    List<Job> data;
    LayoutInflater inflater;
    Context context;

    public JobListRecyclerAdapter(List<Job> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public JobListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.joblist_recycler_row,parent,false);
        JobListViewHolder holder = new JobListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(JobListViewHolder holder, int position) {

        if(position%2 == 0){
            holder.layout.setBackgroundColor(holder.itemView.getResources().getColor(R.color.recyclerViewBG));
        }
        Job current = data.get(position);
        holder.jobNoTV.setText(current.jobNumber);
        holder.jobNameTV.setText(current.jobName);
        holder.clientNameTV.setText(current.jobClient);
        holder.assignedToTV.setText(current.jobAssigned);
        holder.startDateTV.setText(current.jobStartDate);
        holder.endDateTV.setText(current.jobEndDate);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class JobListViewHolder extends RecyclerView.ViewHolder{

        LinearLayout layout;
        TextView jobNoTV;
        TextView jobNameTV;
        TextView clientNameTV;
        TextView assignedToTV;
        TextView startDateTV;
        TextView endDateTV;
        public JobListViewHolder(View itemView) {
            super(itemView);

            layout = (LinearLayout) itemView.findViewById(R.id.jobListRow_layout);
            jobNoTV = (TextView) itemView.findViewById(R.id.jobListRow_jobNoTV);
            jobNameTV = (TextView) itemView.findViewById(R.id.jobListRow_jobNameTV);
            clientNameTV = (TextView) itemView.findViewById(R.id.jobListRow_clientNameTV);
            assignedToTV = (TextView) itemView.findViewById(R.id.jobListRow_assignedToTV);
            startDateTV = (TextView) itemView.findViewById(R.id.jobListRow_startDateTV);
            endDateTV = (TextView) itemView.findViewById(R.id.jobListRow_endDateTV);
        }
    }
}
