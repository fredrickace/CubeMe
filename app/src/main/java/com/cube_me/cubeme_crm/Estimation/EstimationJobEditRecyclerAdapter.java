package com.cube_me.cubeme_crm.Estimation;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.R;

import java.util.List;

/**
 * Created by FredrickCyril on 8/16/16.
 */
public class EstimationJobEditRecyclerAdapter extends RecyclerView.Adapter<EstimationJobEditRecyclerAdapter.JobEditHolder> {

    Context context;
    LayoutInflater inflater;
    static List<EstimationJobs> data;
    EstimationJobEditDialog.JobEditCommunicator jobEditCommunicator;

    public EstimationJobEditRecyclerAdapter(Context context, EstimationJobEditDialog.JobEditCommunicator jobEditCommunicator) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        data = Estimation.jobsList;
        this.jobEditCommunicator = jobEditCommunicator;
    }

    @Override
    public JobEditHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.estimation_job_edit_dialog_row,null,false);
        JobEditHolder holder = new JobEditHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final JobEditHolder holder, final int position) {
        final EstimationJobs current = Estimation.jobsList.get(position);
        final TextView estViewJobTV = (TextView) Estimation.jobTotalLinearLayout.findViewWithTag(current.jobName+"Label");
        final View jobView = Estimation.jobTotalLinearLayout.findViewWithTag(current.jobName);
        final TextView jobTotalView = (TextView) Estimation.jobTotalLinearLayout.findViewWithTag(current.jobName+"TV");
        final String oldJobName = current.jobName;

        holder.jobNameTV.setText(current.jobName);
        holder.jobNameET.setText(current.jobName);
        holder.jobDescTV.setText(current.jobDescription);
        holder.jobDescET.setText(current.jobDescription);
        holder.noOfUnitsTV.setText(BaseActivity.decimalFormat.format(current.jobQuantity));
        holder.noOfUnitsET.setText(BaseActivity.decimalFormat.format(current.jobQuantity));
        holder.measurementUnitTV.setText(current.jobUnit);
        holder.measurementUnitET.setText(current.jobUnit);
        holder.finishImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(BaseActivity.ifEditTextNotEmptyErrMsg(holder.jobNameET) && BaseActivity.ifEditTextNotEmptyErrMsg(holder.noOfUnitsET)
                        && BaseActivity.ifEditTextNotEmptyErrMsg(holder.measurementUnitET) && BaseActivity.ifEditTextNotEmpty(holder.jobDescET)) {

                    String jobName = holder.jobNameET.getText().toString();
                    String jobDesc = holder.jobDescET.getText().toString();
                    String jobMeasurement = holder.measurementUnitET.getText().toString();
                    float jobUnit = Float.valueOf(holder.noOfUnitsET.getText().toString());

                    holder.jobNameTV.setText(jobName);
                    holder.jobNameET.setText(jobName);
                    holder.jobDescET.setText(jobDesc);
                    holder.jobDescTV.setText(jobDesc);
                    holder.noOfUnitsTV.setText(String.valueOf(jobUnit));
                    holder.noOfUnitsET.setText(String.valueOf(jobUnit));
                    holder.measurementUnitTV.setText(String.valueOf(jobMeasurement));
                    holder.measurementUnitET.setText(String.valueOf(jobMeasurement));
                    holder.TVLayout.setVisibility(View.VISIBLE);
                    holder.ETLayout.setVisibility(View.GONE);

                    setDatas(current, jobName, oldJobName, jobDesc, jobMeasurement, jobUnit, estViewJobTV, jobView, jobTotalView, position);
                }
            }
        });
        holder.deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context).setTitle("Confirm Delete?").setMessage("Deleting the Job will delete all the Data related to it.").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String jobName = Estimation.jobNames.get(position);
                        Estimation.jobsList.remove(position);
                        Estimation.jobNames.remove(position);
                        EstimationJobEditDialog.estimationJobEditRecyclerAdapter.notifyDataSetChanged();
                        jobEditCommunicator.setDeleteJob(jobName);
                        Estimation.jobTotalLinearLayout.removeView(jobView);
                    }
                }).setNegativeButton("No", null).create().show();

            }
        });
    }

    private void setDatas(EstimationJobs current,String newJobName, String oldJobName, String jobDescription, String jobMeasurement,
                          float jobUnit, TextView estViewJobTV, View jobView, TextView jobTotalView,int position) {

        current.setJobName(newJobName);
        current.setJobDescription(jobDescription);
        current.setJobUnit(jobMeasurement);
        current.setJobQuantity(jobUnit);
        if(!oldJobName.equals(current.jobName)) {
            jobEditCommunicator.setEditJob(oldJobName, current.jobName);
            Estimation.jobNames.remove(position);
            Estimation.jobNames.add(position,current.jobName);
            estViewJobTV.setText(current.jobName+" Total");
            estViewJobTV.setTag(current.jobName+"Label");
            jobView.setTag(current.jobName);
            jobTotalView.setTag(current.jobName+"TV");
        }

    }

    @Override
    public int getItemCount() {
        return Estimation.jobsList.size();
    }


    public class JobEditHolder extends RecyclerView.ViewHolder{

        LinearLayout TVLayout;
        LinearLayout ETLayout;
        TextView jobNameTV;
        TextView jobDescTV;
        TextView measurementUnitTV;
        TextView noOfUnitsTV;
        ImageButton editImageButton;
        ImageButton deleteImageButton;
        EditText jobNameET;
        EditText jobDescET;
        EditText measurementUnitET;
        EditText noOfUnitsET;
        ImageButton finishImageButton;


        public JobEditHolder(View itemView) {
            super(itemView);

            TVLayout = (LinearLayout) itemView.findViewById(R.id.estJobEdit_TVLayout);
            ETLayout = (LinearLayout) itemView.findViewById(R.id.estJobEdit_ETLayout);
            jobNameTV = (TextView) itemView.findViewById(R.id.estJobEdit_jobNameTV);
            jobDescTV = (TextView) itemView.findViewById(R.id.estJobEdit_jobDescTV);
            measurementUnitTV = (TextView)itemView.findViewById(R.id.estJobEdit_jobUnitMeasurementTV);
            noOfUnitsTV = (TextView) itemView.findViewById(R.id.estJobEdit_jobNoOfUnitsTV);
            editImageButton = (ImageButton)itemView.findViewById(R.id.estJobEdit_editButton);
            editImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TVLayout.setVisibility(View.GONE);
                    ETLayout.setVisibility(View.VISIBLE);
                }
            });
            deleteImageButton = (ImageButton) itemView.findViewById(R.id.estJobEdit_removeButton);
            jobNameET = (EditText) itemView.findViewById(R.id.estJobEdit_jobNameEText);
            jobDescET = (EditText) itemView.findViewById(R.id.estJobEdit_jobDescEText);
            measurementUnitET = (EditText) itemView.findViewById(R.id.estJobEdit_jobUnitMeasurementEtext);
            noOfUnitsET = (EditText) itemView.findViewById(R.id.estJobEdit_jobNoOfUnitsEtext);
            finishImageButton = (ImageButton) itemView.findViewById(R.id.estJobEdit_finishImageButton);

        }
    }
}

