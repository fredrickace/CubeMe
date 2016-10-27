package com.cube_me.cubeme.Inquiry;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cube_me.cubeme.ClickListener;
import com.cube_me.cubeme.EditDeleteFragment;
import com.cube_me.cubeme.Estimation.Estimation;
import com.cube_me.cubeme.Estimation.EstimationDatabaseAdapter;
import com.cube_me.cubeme.Estimation.EstimationFragmentRecyclerAdapter;
import com.cube_me.cubeme.Estimation.EstimationPreview;
import com.cube_me.cubeme.R;
import com.cube_me.cubeme.RecyclerTouchListener;
import com.cube_me.cubeme.EditDeleteCallBackFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InquiryViewRelatedFragment extends Fragment{

    RecyclerView estimationRecycler;
    int estimationRecyclerPosition;
    EstimationFragmentRecyclerAdapter estimationRecyclerAdapter;
    List<Estimation> estimationList;
    EstimationDatabaseAdapter estimationDatabaseAdapter;

    TextView noDataTV;
    String inquiryID;
    Inquiry inquiry;
    Bundle bundle;
    Context context;
    String estimationId;

    Estimation estimation;


    public InquiryViewRelatedFragment() {
        // REQUIRED EMPTY PUBLIC CONSTRUCTOR
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        View view =  inflater.inflate(R.layout.inquiry_view_related, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        estimationList = estimationDatabaseAdapter.getEstimationForInquiry(estimationId);
        estimationRecyclerAdapter = new EstimationFragmentRecyclerAdapter(estimationList,context);
        estimationRecycler.setAdapter(estimationRecyclerAdapter);
//        estimationList = estimationDatabaseAdapter.getEstimationForInquiry(estimationId);
        if(estimationList.size()<=0){
            noDataTV.setVisibility(View.VISIBLE);
        }else {
            noDataTV.setVisibility(View.GONE);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();

        noDataTV = (TextView) getView().findViewById(R.id.inquiryViewRelated_NODATATV);

        bundle = getActivity().getIntent().getExtras();
        inquiry = bundle.getParcelable("ViewInquiry");
        inquiryID = inquiry.inquiryID;
        estimationId = "EST/"+ inquiryID.substring(4);
        //GETTING ESTIMATION LIST FROM DATABASE
        estimationDatabaseAdapter = new EstimationDatabaseAdapter(context);

        //SETTING UP THE RECYCLER FOR ESTIMATIONS
        estimationRecycler = (RecyclerView) getView().findViewById(R.id.inquiryViewRelated_estimationRecycler);
        estimationRecyclerAdapter = new EstimationFragmentRecyclerAdapter(estimationList,context);
        estimationRecycler.addOnItemTouchListener(new RecyclerTouchListener(context, estimationRecycler, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                estimationRecyclerPosition = position;
                EditDeleteCallBackFragment editDeleteCallBackFragment = new EditDeleteCallBackFragment();
                editDeleteCallBackFragment.setCallBack(communicator);
                editDeleteCallBackFragment.show(getFragmentManager(),"Edit Delete");


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        estimationRecycler.setLayoutManager(new LinearLayoutManager(context));
        estimationRecycler.setAdapter(estimationRecyclerAdapter);
    }

    public void getEstimationFromDB(){
        //        EstIdTV = (TextView) view.findViewById(R.id.estSavedRow_estID);
        String EstID = estimationList.get(estimationRecyclerPosition).estimationID;


        // GETTING BASE TABLE FULL DETAILS
        estimation = estimationDatabaseAdapter.getEstimationBase(EstID);

        // GETTING JOB DATA
        estimation.jobsList = estimationDatabaseAdapter.getJobData(EstID);


        // GETTING DIRECT MATERIAL DATA
        estimation.estimationDirectMaterialList = estimationDatabaseAdapter.getDMData(EstID);

        // GETTING MANPOWER DATA
        estimation.manPowerList = estimationDatabaseAdapter.getMPTable(EstID);

        // GETTING TOOLS DATA
        estimation.toolsList = estimationDatabaseAdapter.getToolsTable(EstID);

        // GETTING TRANSPORT DATA
        estimation.transportList = estimationDatabaseAdapter.getTransportTable(EstID);

        // GETTING CONSUMABLE DATA
//                EstimationConsumables consumables;
        estimation.consumableList =estimationDatabaseAdapter.getConsumableData(EstID);
//                consumables = estimation.consumableList.get(0);
//                Toast.makeText(EstimationSaved.this, consumables.consumableName, Toast.LENGTH_SHORT).show();

    }

    EditDeleteFragment.EditDeleteCommunicator communicator = new EditDeleteFragment.EditDeleteCommunicator() {
        @Override
        public void deleteFromDialog() {
            final String EstID = estimationList.get(estimationRecyclerPosition).estimationID;
            new AlertDialog.Builder(getContext()).setMessage(R.string.confirmDelete).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    estimationList.remove(estimationRecyclerPosition);
                    int deleteResultKey = estimationDatabaseAdapter.deleteEstimation(EstID);
                    if (deleteResultKey < 0) {
                        Toast.makeText(context, "No value to delete", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Successfully Deleted the records", Toast.LENGTH_SHORT).show();
                    }
                    onResume();
                }
            }).setNegativeButton("No", null).create().show();
        }

        @Override
        public void editFromDialog() {
            getEstimationFromDB();
            Intent editIntent = new Intent(context,Estimation.class);
            editIntent.putExtra("EstimationEdit", estimation);
            editIntent.putExtra("Flag",estimation.ESTIMATION_EDIT);
            startActivity(editIntent);
        }

        @Override
        public void viewFromDialog() {
            getEstimationFromDB();
            Intent previewIntent = new Intent(context, EstimationPreview.class);
            previewIntent.putExtra("Preview", estimation);
            startActivity(previewIntent);
        }
    };


}
