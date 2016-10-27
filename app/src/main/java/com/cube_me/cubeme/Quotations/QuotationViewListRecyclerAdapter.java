package com.cube_me.cubeme.Quotations;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cube_me.cubeme.BaseActivity;
import com.cube_me.cubeme.R;

import java.util.List;

/**
 * Created by FredrickCyril on 10/4/16.
 */

public class QuotationViewListRecyclerAdapter extends RecyclerView.Adapter<QuotationViewListRecyclerAdapter.QuotationViewListHolder> {

    List<Quotation> data;
    Context context;
    LayoutInflater layoutInflater;

    public QuotationViewListRecyclerAdapter(List<Quotation> data, Context context) {
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public QuotationViewListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.quotation_listview_recycler_row,parent,false);
        QuotationViewListHolder holder  = new QuotationViewListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(QuotationViewListHolder holder, int position) {

        Quotation current = data.get(position);
        if (position%2 == 0){
            holder.layout.setBackgroundColor(holder.itemView.getResources().getColor(R.color.recyclerViewBG));
        }
        holder.quotationNoTV.setText(current.quotationNo);
        holder.titleTV.setText(current.quotationTitle);
        holder.totalTV.setText("Quotation Value:"+BaseActivity.decimalFormat.format(current.quotationTotal));
        holder.clientNameTV.setText(current.quotationClient);
        holder.statusTV.setText(current.quotationStatus);
        holder.createdDateTV.setText(current.quotationCreateDate);
        holder.editDateTV.setText(current.quotationEditDate);
    }

    

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class QuotationViewListHolder extends RecyclerView.ViewHolder{

        LinearLayout layout;
        TextView quotationNoTV;
        TextView titleTV;
        TextView totalTV;
        TextView clientNameTV;
        TextView statusTV;
        TextView createdDateTV;
        TextView editDateTV;
        public QuotationViewListHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.quoViewRecyclerRow_Layout);
            quotationNoTV = (TextView) itemView.findViewById(R.id.quoViewRecyclerRow_QuoNoTV);
            titleTV = (TextView) itemView.findViewById(R.id.quoViewRecyclerRow_QuoTitleTV);
            totalTV = (TextView) itemView.findViewById(R.id.quoViewRecyclerRow_QuoTotalTV);
            clientNameTV = (TextView) itemView.findViewById(R.id.quoViewRecyclerRow_QuoClientNameTV);
            statusTV = (TextView) itemView.findViewById(R.id.quoViewRecyclerRow_QuoStatusTV);
            createdDateTV = (TextView) itemView.findViewById(R.id.quoViewRecyclerRow_QuoCreateDateTV);
            editDateTV = (TextView) itemView.findViewById(R.id.quoViewRecyclerRow_QuoEditDateTV);
        }
    }
}
