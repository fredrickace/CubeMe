package com.cube_me.cubeme_crm.Estimation;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstimationJobDialog extends DialogFragment {

    EditText jobNameEditText;
    EditText jobUnitEditText;
    EditText jobQuantityEditText;
    EditText jobDescEditText;
    Button jobAddButton;
    ImageButton cancelButton;
    DialogJobCommunicator communicator;
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (DialogJobCommunicator) context;
        this.context = context;
    }



    public EstimationJobDialog() {
        // REQUIRED EMPTY PUBLIC CONSTRUCTOR
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        View view = inflater.inflate(R.layout.estimation_job_dialog, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

//        getDialog().setTitle("Add New Job");
        if(Estimation.jobsList.size()<=0) {
            setCancelable(false);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        jobNameEditText = (EditText) getView().findViewById(R.id.jobDialog_jobName);
        jobDescEditText = (EditText) getView().findViewById(R.id.jobDialog_jobDescription);
        jobUnitEditText = (EditText) getView().findViewById(R.id.jobDialog_jobUnit);
        jobQuantityEditText = (EditText) getView().findViewById(R.id.jobDialog_jobQuantity);
        jobAddButton = (Button) getView().findViewById(R.id.jobDialog_addButton);
        jobAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(BaseActivity.ifEditTextNotEmptyErrMsg(jobNameEditText) && BaseActivity.ifEditTextNotEmptyErrMsg(jobQuantityEditText)
                        && BaseActivity.ifEditTextNotEmptyErrMsg(jobUnitEditText) && BaseActivity.ifEditTextNotEmpty(jobDescEditText)){
                    communicator.setNewJob(jobNameEditText.getText().toString(),jobUnitEditText.getText().toString(),
                            jobQuantityEditText.getText().toString(), jobDescEditText.getText().toString());
                    dismiss();
                }
            }
        });
        cancelButton = (ImageButton) getView().findViewById(R.id.jobDialog_cancelImageButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicator.exitNewEstimation();
                dismiss();
            }
        });
    }


    interface DialogJobCommunicator {
        public void setNewJob(String jobName,  String jobUnit, String jobQuantity, String jobDesc);
        public void exitNewEstimation();
    }
}
