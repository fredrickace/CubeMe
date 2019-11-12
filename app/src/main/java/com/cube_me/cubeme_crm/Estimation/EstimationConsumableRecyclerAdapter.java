package com.cube_me.cubeme_crm.Estimation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.R;

import java.util.List;

/**
 * Created by FredrickCyril on 8/9/16.
 */
public class EstimationConsumableRecyclerAdapter extends RecyclerView.Adapter<EstimationConsumableRecyclerAdapter.ConsumableViewHolder> {

    List<EstimationConsumables> data;
    Context context;
    LayoutInflater inflater;
    int adapterFlag;

    public EstimationConsumableRecyclerAdapter(Context context,List<EstimationConsumables> data, int adapterFlag) {
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.adapterFlag = adapterFlag;
    }

    @Override
    public ConsumableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        ConsumableViewHolder holder = null;
        if(adapterFlag == Estimation.ESTIMATION_NEW_ADAPTER){
            view = inflater.inflate(R.layout.estimation_consumables_row,parent,false);
            holder = new ConsumableViewHolder(view);
        } else if(adapterFlag == EstimationPreview.ESTIMATION_PREVIEW_ADAPTER){
            view = inflater.inflate(R.layout.estimation_consumablepreview_row,parent,false);
            holder = new ConsumableViewHolder(view);
        }


        return holder;
    }

    @Override
    public void onBindViewHolder(ConsumableViewHolder holder, int position) {

        EstimationConsumables estimationConsumables = data.get(position);
        holder.nameTV.setText(estimationConsumables.consumableName);
        holder.UOMTV.setText(estimationConsumables.consumableUOM);
        holder.noOfUnitsTV.setText(BaseActivity.decimalFormat.format(estimationConsumables.consumableNoOfUnits));
        holder.unitPriceTV.setText(BaseActivity.decimalFormat.format(estimationConsumables.consumableUnitPrice));
        holder.totalTV.setText(BaseActivity.decimalFormat.format(estimationConsumables.consumableTotal));
        holder.jobNameTV.setText(estimationConsumables.jobName);
        holder.budgetPriceTV.setText(BaseActivity.decimalFormat.format(estimationConsumables.consumableBudgetPrice));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ConsumableViewHolder extends RecyclerView.ViewHolder{

        TextView nameTV;
        TextView UOMTV;
        TextView noOfUnitsTV;
        TextView unitPriceTV;
        TextView budgetPriceTV;
        TextView totalTV;
        TextView jobNameTV;

        public ConsumableViewHolder(View itemView) {
            super(itemView);
            if(adapterFlag == Estimation.ESTIMATION_NEW_ADAPTER) {
                nameTV = (TextView) itemView.findViewById(R.id.consumableRow_name);
                UOMTV = (TextView) itemView.findViewById(R.id.consumableRow_UOM);
                noOfUnitsTV = (TextView) itemView.findViewById(R.id.consumableRow_noOfUnits);
                unitPriceTV = (TextView) itemView.findViewById(R.id.consumableRow_unitPrice);
                budgetPriceTV = (TextView) itemView.findViewById(R.id.consumableRow_budgetPrice);
                totalTV = (TextView) itemView.findViewById(R.id.consumableRow_totalPrice);
                jobNameTV = (TextView) itemView.findViewById(R.id.consumableRow_jobName);
            }else if(adapterFlag == EstimationPreview.ESTIMATION_PREVIEW_ADAPTER){
                nameTV = (TextView) itemView.findViewById(R.id.consumablePreviewRow_nameTV);
                UOMTV = (TextView) itemView.findViewById(R.id.consumablePreviewRow_UOMTV);
                noOfUnitsTV = (TextView) itemView.findViewById(R.id.consumablePreviewRow_noOfUnitsTV);
                unitPriceTV = (TextView) itemView.findViewById(R.id.consumablePreviewRow_unitPriceTV);
                budgetPriceTV = (TextView) itemView.findViewById(R.id.consumablePreviewRow_budgetPriceTV);
                totalTV = (TextView) itemView.findViewById(R.id.consumablePreviewRow_totalPriceTV);
                jobNameTV = (TextView) itemView.findViewById(R.id.consumablePreviewRow_jobTV);

            }
        }
    }
}
