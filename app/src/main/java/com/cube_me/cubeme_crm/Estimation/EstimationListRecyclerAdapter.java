package com.cube_me.cubeme_crm.Estimation;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.R;

import java.util.List;

/**
 * Created by FredrickCyril on 9/4/16.
 */
public class EstimationListRecyclerAdapter extends RecyclerView.Adapter<EstimationListRecyclerAdapter.EstimationFragmentViewHolder> {

    List<Estimation> data;
    Context context;
    LayoutInflater inflater;

    public EstimationListRecyclerAdapter(List<Estimation> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public EstimationFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.estimation_recycler_row,parent,false);
        EstimationFragmentViewHolder holder = new EstimationFragmentViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(EstimationFragmentViewHolder holder, int position) {
        Estimation current = data.get(position);
        if(position%2 == 0){
            holder.layout.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.recyclerViewBG));
        }
        holder.estNoTV.setText(current.estimationID);
        holder.estValueTV.setText("QR "+BaseActivity.decimalFormat.format(current.grandTotal));
        holder.estClientTV.setText(current.clientAccount);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class EstimationFragmentViewHolder extends RecyclerView.ViewHolder{

        CardView layout;
        TextView estNoTV;
        TextView estValueTV;
        TextView estClientTV;

        public EstimationFragmentViewHolder(View itemView) {
            super(itemView);

            layout = (CardView) itemView.findViewById(R.id.estimationRecyclerRow_layout);
            estNoTV = (TextView) itemView.findViewById(R.id.estimationRecyclerRow_estimationNoTV);
            estValueTV = (TextView) itemView.findViewById(R.id.estimationRecyclerRow_estimationValue);
            estClientTV = (TextView) itemView.findViewById(R.id.estimationRecyclerRow_estimationClient);
        }
    }
}
