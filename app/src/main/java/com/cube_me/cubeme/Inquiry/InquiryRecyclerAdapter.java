package com.cube_me.cubeme.Inquiry;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cube_me.cubeme.R;

import java.util.List;

/**
 * Created by FredrickCyril on 7/30/16.
 */
public class InquiryRecyclerAdapter extends RecyclerView.Adapter<InquiryRecyclerAdapter.InquiryViewHolder> {

    static List<Inquiry> data;
    private LayoutInflater layoutInflater;
    public InquiryRecyclerAdapter(Context context, List<Inquiry> data) {
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public InquiryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inquiry_row, parent, false);
        InquiryViewHolder holder = new InquiryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(InquiryViewHolder holder, int position) {

        Inquiry currentInquiry = data.get(position);
        holder.inquiryNoTV.setText(currentInquiry.getInquiryNumber());
        holder.inquirySubjectTV.setText(currentInquiry.getInquirySubject());
        holder.inquiryCompanyTV.setText(currentInquiry.getInquiryCompanyName());
        holder.inquiryStatusTV.setText(currentInquiry.getInquiryStatus());
        if(currentInquiry.getInquiryStatus().equals("Estimation") || currentInquiry.getInquiryStatus().equals("Quotation Stage")){
            holder.inquiryStatusTV.setTextColor(holder.itemView.getResources().getColor(R.color.navHeader));
        }
        if(currentInquiry.getInquiryStatus().equals("Approved")){
            holder.inquiryStatusTV.setTextColor(holder.itemView.getResources().getColor(R.color.statusOkay));
        }
        holder.inquiryAssignedToTV.setText(currentInquiry.getInquiryAssignTo());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class InquiryViewHolder extends RecyclerView.ViewHolder{
        TextView inquiryNoTV;
        TextView inquirySubjectTV;
        TextView inquiryCompanyTV;
        TextView inquiryStatusTV;
        TextView inquiryAssignedToTV;

        public InquiryViewHolder(View itemView) {
            super(itemView);
            inquiryNoTV = (TextView) itemView.findViewById(R.id.inquiryRow_inquiryNoTV);
            inquirySubjectTV = (TextView) itemView.findViewById(R.id.inquiryRow_inquirySubjectTV);
            inquiryCompanyTV = (TextView) itemView.findViewById(R.id.inquiryRow_companyNameTV);
            inquiryStatusTV = (TextView) itemView.findViewById(R.id.inquiryRow_inquiryStatusTV);
            inquiryAssignedToTV = (TextView) itemView.findViewById(R.id.inquiryRow_inquiryAssignedToTV);

        }
    }

    public static void addNewInquiry(Inquiry newInquiry){
        data.add(newInquiry);
        InquiryFragment.recyclerAdapter.notifyDataSetChanged();
    }
}
