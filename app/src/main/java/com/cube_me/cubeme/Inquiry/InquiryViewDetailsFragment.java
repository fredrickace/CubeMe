package com.cube_me.cubeme.Inquiry;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import com.cube_me.cubeme.Estimation.Estimation;
import com.cube_me.cubeme.Estimation.EstimationDatabaseAdapter;
import com.cube_me.cubeme.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InquiryViewDetailsFragment extends Fragment {


    TextView departmentTextView;
    TextView statusTextView;
    TextView assignTextView;
    TextView companyTextView;
    TextView contactPersonTextView;
    TextView subjectTextView;
    TextView descriptionTextView;
    TextView emailTextView;
    TextView contactNumberTextView;
    TextView inquiryInfoTextView;
    TextView createDateTextView;
    TextView editDateTextView;
    TextView createTimeTextView;
    TextView editTimeTextView;
    TextView projectTV;
    TextView contactDesignationTV;
    TextView consultantTV;
    TextView mainContractorTV;
    TextView subContractorTV;
    Intent i;
    Bundle inquiryBundle;
    Inquiry inquiry;
    String inquiryID;

    Context context;

    public InquiryViewDetailsFragment() {
        // REQUIRED EMPTY PUBLIC CONSTRUCTOR
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        View view =  inflater.inflate(R.layout.inquiry_view_details, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //GET INTENT AND RECEIVE THE PARCELABLE

        inquiryBundle = this.getArguments();
        String inqID = inquiryBundle.getString("InquiryId");

        InquiryDatabaseAdapter databaseAdapter = new InquiryDatabaseAdapter(getContext());
        inquiry = databaseAdapter.getInquiry(inqID);

        inquiryID = inquiry.inquiryID;
        inquiryInfoTextView.setText(inquiryID);
        companyTextView.setText(inquiry.inquiryCompanyName);
        assignTextView.setText(inquiry.inquiryAssignTo);
        subjectTextView.setText(inquiry.inquirySubject);
        descriptionTextView.setText(inquiry.inquiryDescription);
        departmentTextView.setText(inquiry.inquiryDepartment);
        statusTextView.setText(inquiry.inquiryStatus);
        contactPersonTextView.setText(inquiry.inquiryContactPerson);
        contactNumberTextView.setText(inquiry.inquiryContactNumber);
        emailTextView.setText(inquiry.inquiryEmailID);
        createDateTextView.setText(inquiry.inquiryCreateDate);
        editDateTextView.setText(inquiry.inquiryLastEditDate);
        createTimeTextView.setText(inquiry.inquiryCreateTime);
        editTimeTextView.setText(inquiry.inquiryLastEditTime);
        projectTV.setText(inquiry.projectType);
        contactDesignationTV.setText(inquiry.contactPersonDesignation);
        consultantTV.setText(inquiry.consultant);
        mainContractorTV.setText(inquiry.mainContractor);
        subContractorTV.setText(inquiry.subContractor);

        if (inquiry.inquiryStatus.equals("Estimation") || inquiry.inquiryStatus.equals("Quotation Sent")) {
            statusTextView.setTextColor(getResources().getColor(R.color.navHeader));
        }else if (inquiry.inquiryStatus.equals("Approved")) {
            statusTextView.setTextColor(getResources().getColor(R.color.statusOkay));
        }else {
            statusTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        contactNumberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = contactNumberTextView.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNo));
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });

        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailID = emailTextView.getText().toString();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("*/*");
                emailIntent.putExtra(Intent.EXTRA_EMAIL,emailID);
                startActivity(emailIntent);
            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //BASIC INIT
        context = getContext();

        inquiryInfoTextView = (TextView) getView().findViewById(R.id.inquiryView_infoTextView);
        companyTextView = (TextView) getView().findViewById(R.id.inquiryView_companyTextView);
        assignTextView = (TextView) getView().findViewById(R.id.inquiryView_assignTextView);
        subjectTextView = (TextView) getView().findViewById(R.id.inquiryView_subjectTextView);
        descriptionTextView = (TextView) getView().findViewById(R.id.inquiryView_descriptionTextView);
        departmentTextView = (TextView) getView().findViewById(R.id.inquiryView_departmentTextView);
        statusTextView = (TextView) getView().findViewById(R.id.inquiryView_statusTextView);
        contactPersonTextView = (TextView) getView().findViewById(R.id.inquiryView_contactPersonTextView);
        contactNumberTextView = (TextView) getView().findViewById(R.id.inquiryView_contactNumberTextView);
        emailTextView = (TextView) getView().findViewById(R.id.inquiryView_emailTextView);
        createDateTextView = (TextView) getView().findViewById(R.id.inquiryView_createdDateTV);
        editDateTextView = (TextView) getView().findViewById(R.id.inquiryView_editDateTV);
        createTimeTextView = (TextView) getView().findViewById(R.id.inquiryView_createdTimeTV);
        editTimeTextView = (TextView) getView().findViewById(R.id.inquiryView_editTimeTV);
        projectTV = (TextView) getView().findViewById(R.id.inquiryView_projectTypeTextView);
        contactDesignationTV = (TextView) getView().findViewById(R.id.inquiryView_contactDesignationTextView);
        consultantTV = (TextView) getView().findViewById(R.id.inquiryView_consultantTextView);
        mainContractorTV = (TextView) getView().findViewById(R.id.inquiryView_mainContractorTextView);
        subContractorTV = (TextView) getView().findViewById(R.id.inquiryView_subContractorTextView);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_inquiry_view_details,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_inquiryView_newEstimation:

                String estId = "EST/" + inquiryID.substring(4);
                EstimationDatabaseAdapter estimationDatabaseAdapter = new EstimationDatabaseAdapter(getContext());
                List<Estimation> estimationList = estimationDatabaseAdapter.getEstimationBaseAll(estId);
                final int estimationCount = estimationList.size();
                if(estimationCount<=0){
                    Intent estimationIntent = new Intent(getContext(), Estimation.class);
                    estimationIntent.putExtra("Flag",Estimation.ESTIMATION_NEW);
                    estimationIntent.putExtra("InquiryID",inquiry.inquiryID);
                    estimationIntent.putExtra("Client",inquiry.inquiryCompanyName);
                    startActivity(estimationIntent);
                }else {
                    new AlertDialog.Builder(getContext()).setMessage("Existing "+estimationCount+" estimation available. Create New?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String inqId = inquiry.inquiryID+".v"+estimationCount;
                                    Intent estimationIntent = new Intent(getContext(), Estimation.class);
                                    estimationIntent.putExtra("Flag",Estimation.ESTIMATION_NEW);
                                    estimationIntent.putExtra("InquiryID",inqId);
                                    estimationIntent.putExtra("Client",inquiry.inquiryCompanyName);
                                    startActivity(estimationIntent);


                                }
                            }).setNegativeButton("No",null).create().show();
                }
                break;

            case R.id.action_inquiryView_editInquiry:
                Intent editInqIntent = new Intent(context,InquiryNew.class);
                editInqIntent.putExtra("InquiryFlag",InquiryNew.INQUIRY_EDIT);
                editInqIntent.putExtra("InquiryId",inquiry.inquiryID);
                startActivity(editInqIntent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
