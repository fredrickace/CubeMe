package com.cube_me.cubeme.Estimation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cube_me.cubeme.BaseActivity;
import com.cube_me.cubeme.R;

import java.util.List;

/**
 * Created by FredrickCyril on 8/7/16.
 */
public class EstimationOtherMaterialRecyclerAdapter extends RecyclerView.Adapter<EstimationOtherMaterialRecyclerAdapter.OtherViewHolder> {

    LayoutInflater inflater;
    Context context;
    List<EstimationOtherMaterials> data;
    int adapterFlag;

    public EstimationOtherMaterialRecyclerAdapter(Context context, List<EstimationOtherMaterials> data, int adapterFlag) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.adapterFlag = adapterFlag;

    }

    @Override
    public OtherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        OtherViewHolder holder = null;
        if (adapterFlag == Estimation.ESTIMATION_NEW_ADAPTER) {
            view = inflater.inflate(R.layout.estimation_othermaterial_row, parent, false);
            holder = new OtherViewHolder(view);
        } else if (adapterFlag == EstimationPreview.ESTIMATION_PREVIEW_ADAPTER) {
            view = inflater.inflate(R.layout.estimation_othermaterialpreview_row, parent, false);
            holder = new OtherViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(OtherViewHolder holder, int position) {

        EstimationOtherMaterials estimationOtherMaterials = data.get(position);
        holder.OMNameTV.setText(estimationOtherMaterials.otherMaterialName);
        holder.OMUOMTV.setText(estimationOtherMaterials.otherMaterialUOM);
        holder.OMUnitTV.setText(BaseActivity.decimalFormat.format(estimationOtherMaterials.otherMaterialDayHour));
        holder.OMNoOfResTV.setText(BaseActivity.decimalFormat.format(estimationOtherMaterials.otherMaterialUnit));
        holder.OMPricePerUnitTV.setText(BaseActivity.decimalFormat.format(estimationOtherMaterials.otherMaterialPricePerUnit));
        holder.OMBudgetPerUnitTV.setText(BaseActivity.decimalFormat.format(estimationOtherMaterials.otherMaterialBudgetPerUnit));
        holder.OMTotalTV.setText(BaseActivity.decimalFormat.format(estimationOtherMaterials.otherMaterialTotal));
        holder.OMJobTV.setText(estimationOtherMaterials.jobName);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class OtherViewHolder extends RecyclerView.ViewHolder {

        TextView OMNameTV;
        TextView OMUOMTV;
        TextView OMUnitTV;
        TextView OMNoOfResTV;
        TextView OMPricePerUnitTV;
        TextView OMBudgetPerUnitTV;
        TextView OMTotalTV;
        TextView OMJobTV;

        public OtherViewHolder(View itemView) {
            super(itemView);
            if (adapterFlag == Estimation.ESTIMATION_NEW_ADAPTER) {
                OMNameTV = (TextView) itemView.findViewById(R.id.otherMaterialRow_name);
                OMUOMTV = (TextView) itemView.findViewById(R.id.otherMaterialRow_UOM);
                OMUnitTV = (TextView) itemView.findViewById(R.id.otherMaterialRow_day);
                OMNoOfResTV = (TextView) itemView.findViewById(R.id.otherMaterialRow_hours);
                OMPricePerUnitTV = (TextView) itemView.findViewById(R.id.otherMaterialRow_pricePerHour);
                OMBudgetPerUnitTV = (TextView) itemView.findViewById(R.id.otherMaterialRow_budget);
                OMTotalTV = (TextView) itemView.findViewById(R.id.otherMaterialRow_totalPrice);
                OMJobTV = (TextView) itemView.findViewById(R.id.otherMaterialRow_jobName);

            } else if (adapterFlag == EstimationPreview.ESTIMATION_PREVIEW_ADAPTER) {
                OMNameTV = (TextView) itemView.findViewById(R.id.otherMaterialPreviewRow_name);
                OMUnitTV = (TextView) itemView.findViewById(R.id.otherMaterialPreviewRow_noOfUnits);
                OMUOMTV = (TextView) itemView.findViewById(R.id.otherMaterialPreviewRow_UOM);
                OMNoOfResTV = (TextView) itemView.findViewById(R.id.otherMaterialPreviewRow_noOfResources);
                OMPricePerUnitTV = (TextView) itemView.findViewById(R.id.otherMaterialPreviewRow_pricePerUnit);
                OMTotalTV = (TextView) itemView.findViewById(R.id.otherMaterialPreviewRow_totalPrice);
                OMJobTV = (TextView) itemView.findViewById(R.id.otherMaterialPreviewRow_jobName);
                OMBudgetPerUnitTV = (TextView) itemView.findViewById(R.id.otherMaterialPreviewRow_BudgetPerUnit);
            }
        }
    }
}
