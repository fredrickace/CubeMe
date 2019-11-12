package com.cube_me.cubeme_crm.Inquiry;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.R;

import java.util.List;

/**
 * Created by FredrickCyril on 7/30/16.
 */
public class InquiryRecyclerAdapter extends RecyclerView.Adapter<InquiryRecyclerAdapter.InquiryViewHolder> {

    List<Inquiry> data;
    private LayoutInflater layoutInflater;
    public InquiryRecyclerAdapter(Context context, List<Inquiry> data) {
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
        Log.i("Recycler Size",String.valueOf(data.size()));
    }

    @Override
    public InquiryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inquiry_row, parent, false);
        InquiryViewHolder holder = new InquiryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(InquiryViewHolder holder, int position) {


        if(position%2 == 0){
            holder.layout.setBackgroundColor(holder.itemView.getResources().getColor(R.color.recyclerViewBG));
        }
        Inquiry currentInquiry = data.get(position);
        holder.inquiryNoTV.setText(currentInquiry.inquiryID);
        holder.inquirySubjectTV.setText(currentInquiry.inquirySubject);
        holder.inquiryCompanyTV.setText(currentInquiry.inquiryCompanyName);
        holder.inquiryStatusTV.setText(" "+BaseActivity.convertFirstLetterUpperCase(currentInquiry.inquiryStatus));
//        if(currentInquiry.inquiryStatus.equals("Estimation") || currentInquiry.inquiryStatus.equals("Quotation Sent")){
//            holder.inquiryStatusTV.setTextColor(holder.itemView.getResources().getColor(R.color.navHeader));
//        }
//        if(currentInquiry.inquiryStatus.equals("Approved")){
//            holder.inquiryStatusTV.setTextColor(holder.itemView.getResources().getColor(R.color.statusOkay));
//        }
        holder.inquiryStageTV.setText(BaseActivity.convertFirstLetterUpperCase(currentInquiry.inquiryStage));
        holder.inquiryCreateDateTV.setText(currentInquiry.inquiryCreateDate);
//        holder.inquiryDueDateTV.setText(currentInquiry.inquiryDueDate);
        holder.consultantTV.setText(currentInquiry.consultant);
        holder.mainContractorTV.setText(currentInquiry.mainContractor);
        holder.subContractorTV.setText(currentInquiry.subContractor);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class InquiryViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layout;
        TextView inquiryNoTV;
        TextView inquirySubjectTV;
        TextView inquiryCompanyTV;
        TextView inquiryStatusTV;
        TextView inquiryStageTV;
//        TextView inquiryAssignedToTV;
        TextView inquiryCreateDateTV;
        TextView inquiryDueDateTV;
        TextView consultantTV;
        TextView mainContractorTV;
        TextView subContractorTV;

        public InquiryViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.inquiryRow_layout);
            inquiryNoTV = (TextView) itemView.findViewById(R.id.inquiryRow_inquiryNoTV);
            inquirySubjectTV = (TextView) itemView.findViewById(R.id.inquiryRow_inquirySubjectTV);
            inquiryCompanyTV = (TextView) itemView.findViewById(R.id.inquiryRow_companyNameTV);
            inquiryStatusTV = (TextView) itemView.findViewById(R.id.inquiryRow_inquiryStatusTV);
            inquiryStageTV = (TextView) itemView.findViewById(R.id.inquiryRow_inquiryStageTV);
            inquiryCreateDateTV = (TextView) itemView.findViewById(R.id.inquiryRow_createDate);
//            inquiryDueDateTV = (TextView) itemView.findViewById(R.id.inquiryRow_dueDateTV);
            consultantTV = (TextView) itemView.findViewById(R.id.inquiryRow_ConsultantTV);
            mainContractorTV = (TextView) itemView.findViewById(R.id.inquiryRow_mainContractorTV);
            subContractorTV = (TextView) itemView.findViewById(R.id.inquiryRow_subContractorTV);

        }
    }

}
