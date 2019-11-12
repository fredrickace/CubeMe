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
 * Created by FredrickCyril on 8/7/16.
 */
public class EstimationDirectMaterialRecyclerAdapter extends RecyclerView.Adapter<EstimationDirectMaterialRecyclerAdapter.MaterialViewHolder> {

    LayoutInflater layoutInflater;
    List<EstimationDirectMaterial> data;
    Context context;
    int adapter;

    public EstimationDirectMaterialRecyclerAdapter(List<EstimationDirectMaterial> data, Context context, int adapter) {
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
        this.adapter = adapter;
    }

    @Override
    public MaterialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        MaterialViewHolder holder = null;

        if (adapter == Estimation.ESTIMATION_NEW_ADAPTER) {
            view = layoutInflater.inflate(R.layout.estimation_directmaterial_row, parent, false);
            holder = new MaterialViewHolder(view);

        } else if (adapter == EstimationPreview.ESTIMATION_PREVIEW_ADAPTER) {
            view = layoutInflater.inflate(R.layout.estimation_directmaterialpreview_row, parent, false);
            holder = new MaterialViewHolder(view);

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(MaterialViewHolder holder, int position) {

        EstimationDirectMaterial current = data.get(position);
        holder.materialNameTv.setText(current.materialName);
        holder.materialNoOfUnitsTV.setText(BaseActivity.decimalFormat.format(current.materialNoOfUnits));
        holder.materialUnitPriceTV.setText(BaseActivity.decimalFormat.format(current.materialUnitPrice));
        holder.materialDimensionTV.setText(current.materialDimension);
        holder.materialTotalTV.setText(BaseActivity.decimalFormat.format(current.materialTotal));
        holder.materialJobNameTV.setText(current.jobName);
        holder.materialBudgetTV.setText(BaseActivity.decimalFormat.format(current.materialBudget));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MaterialViewHolder extends RecyclerView.ViewHolder {

        TextView materialNameTv;
        TextView materialNoOfUnitsTV;
        TextView materialUnitPriceTV;
        TextView materialDimensionTV;
        TextView materialTotalTV;
        TextView materialJobNameTV;
        TextView materialBudgetTV;

        public MaterialViewHolder(View itemView) {
            super(itemView);
            if (adapter == Estimation.ESTIMATION_NEW_ADAPTER) {
                materialNameTv = (TextView) itemView.findViewById(R.id.directMaterialRow_name);
                materialNoOfUnitsTV = (TextView) itemView.findViewById(R.id.directMaterialRow_noOfUnits);
                materialUnitPriceTV = (TextView) itemView.findViewById(R.id.directMaterialRow_unitPrice);
                materialDimensionTV = (TextView) itemView.findViewById(R.id.directMaterialRow_dimension);
                materialTotalTV = (TextView) itemView.findViewById(R.id.directMaterialRow_totalPrice);
                materialJobNameTV = (TextView) itemView.findViewById(R.id.directMaterialRow_jobName);
                materialBudgetTV = (TextView) itemView.findViewById(R.id.directMaterialRow_budget);
            }
            if (adapter == EstimationPreview.ESTIMATION_PREVIEW_ADAPTER) {
                materialNameTv = (TextView) itemView.findViewById(R.id.directMaterialPreviewRow_name);
                materialNoOfUnitsTV = (TextView) itemView.findViewById(R.id.directMaterialPreviewRow_NoOfUnits);
                materialUnitPriceTV = (TextView) itemView.findViewById(R.id.directMaterialPreviewRow_UnitPrice);
                materialDimensionTV = (TextView) itemView.findViewById(R.id.directMaterialPreviewRow_dimension);
                materialTotalTV = (TextView) itemView.findViewById(R.id.directMaterialPreviewRow_Total);
                materialJobNameTV = (TextView) itemView.findViewById(R.id.directMaterialPreviewRow_jobName);
                materialBudgetTV = (TextView) itemView.findViewById(R.id.directMaterialPreviewRow_budget);
            }
        }
    }
}
