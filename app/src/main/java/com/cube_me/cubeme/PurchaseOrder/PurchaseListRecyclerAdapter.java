package com.cube_me.cubeme.PurchaseOrder;

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

public class PurchaseListRecyclerAdapter extends RecyclerView.Adapter<PurchaseListRecyclerAdapter.PurchaseListViewHolder>{

    List<PurchaseOrder> data;
    Context context;
    LayoutInflater inflater;

    public PurchaseListRecyclerAdapter(List<PurchaseOrder> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PurchaseListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.purchaselist_recycler_row,parent,false);
        PurchaseListViewHolder holder = new PurchaseListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PurchaseListViewHolder holder, int position) {

        if(position%2 == 0){
            holder.layout.setBackgroundColor(holder.itemView.getResources().getColor(R.color.recyclerViewBG));
        }
        PurchaseOrder current = data.get(position);
        holder.clientNameTV.setText(current.clientName);
        holder.poNoTV.setText(current.PONumber);
        holder.poTitleTV.setText(current.POTitle);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class PurchaseListViewHolder extends RecyclerView.ViewHolder{

        LinearLayout layout;
        TextView poNoTV;
        TextView poTitleTV;
        TextView clientNameTV;
        public PurchaseListViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.poListRecyclerRow_layout);
            poNoTV = (TextView) itemView.findViewById(R.id.poListRecyclerRow_poNOTV);
            poTitleTV = (TextView) itemView.findViewById(R.id.poListRecyclerRow_poTitleTV);
            clientNameTV = (TextView) itemView.findViewById(R.id.poListRecyclerRow_companyTV);
        }
    }
}
