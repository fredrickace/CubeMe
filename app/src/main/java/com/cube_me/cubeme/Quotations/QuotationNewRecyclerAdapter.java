package com.cube_me.cubeme.Quotations;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cube_me.cubeme.BaseActivity;
import com.cube_me.cubeme.Estimation.EstimationJobs;
import com.cube_me.cubeme.R;

import java.util.List;

/**
 * Created by FredrickCyril on 9/7/16.
 */
public class QuotationNewRecyclerAdapter extends RecyclerView.Adapter<QuotationNewRecyclerAdapter.QuotationNewViewHolder> {

    List<EstimationJobs> data;
    Context context;
    LayoutInflater inflater;

    public QuotationNewRecyclerAdapter(List<EstimationJobs> data, Context context) {
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public QuotationNewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.quotation_new_recycler_row,parent,false);
        QuotationNewViewHolder holder = new QuotationNewViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(QuotationNewViewHolder holder, int position) {
        if(position%2 != 0){
            holder.rootLayout.setBackgroundColor(context.getResources().getColor(R.color.recyclerViewBG));
        }
        EstimationJobs jobs = data.get(position);
        holder.jobNameTV.setText(jobs.jobName);
        holder.jobUnitPriceTV.setText(BaseActivity.decimalFormat.format(jobs.jobUnitPrice));
        holder.jobUnitTV.setText(jobs.jobUnit);
        holder.jobQuantityTV.setText(BaseActivity.decimalFormat.format(jobs.jobQuantity));
        holder.jobTotalTV.setText(BaseActivity.decimalFormat.format(jobs.jobTotal));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class QuotationNewViewHolder extends RecyclerView.ViewHolder{

        LinearLayout rootLayout;
        TextView jobNameTV;
        TextView jobUnitPriceTV;
        TextView jobUnitTV;
        TextView jobQuantityTV;
        TextView jobTotalTV;

        public QuotationNewViewHolder(View itemView) {
            super(itemView);
            rootLayout = (LinearLayout) itemView.findViewById(R.id.quotationNewRow_LL);
            jobNameTV = (TextView) itemView.findViewById(R.id.quotationNewRow_jobNameTV);
            jobUnitPriceTV = (TextView) itemView.findViewById(R.id.quotationNewRow_jobUnitPriceTV);
            jobUnitTV = (TextView) itemView.findViewById(R.id.quotationNewRow_jobUnitTV);
            jobQuantityTV = (TextView) itemView.findViewById(R.id.quotationNewRow_jobQuantityTV);
            jobTotalTV = (TextView) itemView.findViewById(R.id.quotationNewRow_jobTotalTV);
        }
    }
}
