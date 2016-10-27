package com.cube_me.cubeme.Estimation;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.cube_me.cubeme.R;
import com.cube_me.cubeme.SimpleDividerItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstimationJobEditDialog extends DialogFragment{

    RecyclerView recyclerView;
    static EstimationJobEditRecyclerAdapter estimationJobEditRecyclerAdapter;
    Context context;
    JobEditCommunicator jobEditCommunicator;

    public EstimationJobEditDialog() {
        // REQUIRED EMPTY PUBLIC CONSTRUCTOR
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        jobEditCommunicator = (JobEditCommunicator) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        View view = inflater.inflate(R.layout.estimation_job_edit_dialog, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        getDialog().setTitle("Edit/Remove Jobs");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

//        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        getDialog().getWindow().setTitleColor(getResources().getColor(R.color.navHeader));

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = getContext();
        recyclerView = (RecyclerView) getView().findViewById(R.id.jobDialog_editRecycler);
        estimationJobEditRecyclerAdapter = new EstimationJobEditRecyclerAdapter(context,jobEditCommunicator);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(context));
        recyclerView.setAdapter(estimationJobEditRecyclerAdapter);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if(Estimation.jobNames.size()<=0){
            Estimation.callJobDialogFragment();
        }
    }

    public interface JobEditCommunicator{

        public void setEditJob(String oldJobName, String newJobName);
        public void setDeleteJob(String jobName);
    }
}
