package com.cube_me.cubeme_crm.Reports;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.BaseFunction;
import com.cube_me.cubeme_crm.DatePickerFragment;
import com.cube_me.cubeme_crm.Estimation.Estimation;
import com.cube_me.cubeme_crm.Estimation.EstimationListRecyclerAdapter;
import com.cube_me.cubeme_crm.Inquiry.Inquiry;
import com.cube_me.cubeme_crm.Inquiry.InquiryRecyclerAdapter;
import com.cube_me.cubeme_crm.Jobs.Job;
import com.cube_me.cubeme_crm.Jobs.JobListRecyclerAdapter;
import com.cube_me.cubeme_crm.PurchaseOrder.PurchaseListRecyclerAdapter;
import com.cube_me.cubeme_crm.PurchaseOrder.PurchaseOrder;
import com.cube_me.cubeme_crm.Quotations.Quotation;
import com.cube_me.cubeme_crm.Quotations.QuotationViewListRecyclerAdapter;
import com.cube_me.cubeme_crm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {


    LinearLayout calendarActivityLL;
    LinearLayout salesActivityLL;
    Spinner reportTypeSpinner;
    ArrayAdapter reportTypeAdapter;
    ArrayAdapter clientNameAdapter;
    ArrayAdapter salesPersonAdapter;
    ArrayAdapter moduleAdapter;
    Context context;
    TextView noReportTV;
    BaseFunction cubeUtils;

    //CALENDAR REPORT VARIABLES
    Spinner calendarClientSpinner;
    Spinner calendarSalesPersonSpinner;
    ImageButton calendarStartDateIB;
    ImageButton calendarEndDateIB;
    TextView calendarStartDateTV;
    TextView calendarEndDateTV;
    LinearLayout calendarResultLL;
    ImageButton calendarFilterIB;
    ImageButton calendarFilterDownIB;

    //SALES REPORT VARIABLES
    LinearLayout salesResultLL;
    LinearLayout SRInqLL;
    LinearLayout SREstLL;
    LinearLayout SRQuoLL;
    LinearLayout SRPoLL;
    LinearLayout SRJobsLL;
    RecyclerView SRInqRecycler;
    RecyclerView SREstRecycler;
    RecyclerView SRQuoRecycler;
    RecyclerView SRPoRecycler;
    RecyclerView SRJobsRecycler;
    ImageButton salesFilterIB;
    ImageButton salesFilterDownIB;
    ImageButton salesStartDateIB;
    ImageButton salesEndDateIB;
    Spinner salesModuleSpinner;
    Spinner salesClientSpinner;
    Spinner salesPersonSpinner;
    InquiryRecyclerAdapter inquiryRecyclerAdapter;
    EstimationListRecyclerAdapter estimationRecyclerAdapter;
    QuotationViewListRecyclerAdapter quotationRecyclerAdapter;
    PurchaseListRecyclerAdapter purchaseRecyclerAdapter;
    JobListRecyclerAdapter jobRecyclerAdapter;
    List<Inquiry> inquiryList;
    List<Estimation> estimationList;
    List<Quotation> quotationList;
    List<PurchaseOrder> poList;
    List<Job> jobList;


    Animation slideInAnimation;
    Animation slideOutAnimation;

    public ReportFragment() {
        // REQUIRED EMPTY PUBLIC CONSTRUCTOR
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        View view = inflater.inflate(R.layout.report_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BaseActivity.appToolbar.setTitle("Reports");

        //BASIC INIT
        //CALENDAR REPORT SECTION
        calendarActivityLL = (LinearLayout) getView().findViewById(R.id.reportFrag_calendarReportLL);
        salesActivityLL = (LinearLayout) getView().findViewById(R.id.reportFrag_salesReportLL);
        reportTypeSpinner = (Spinner) getView().findViewById(R.id.reportFrag_reportTypeSpinner);
        calendarClientSpinner = (Spinner) getView().findViewById(R.id.reportFrag_calendarClientSpinner);
        calendarSalesPersonSpinner = (Spinner) getView().findViewById(R.id.reportFrag_calendarSalesPersonSpinner);
        calendarStartDateIB = (ImageButton) getView().findViewById(R.id.reportFrag_calendarStartDateImgButton);
        calendarEndDateIB = (ImageButton) getView().findViewById(R.id.reportFrag_calendarEndDateImgButton);
        calendarStartDateTV = (TextView) getView().findViewById(R.id.reportFrag_calendarStartDateTV);
        calendarEndDateTV = (TextView) getView().findViewById(R.id.reportFrag_calendarEndDateTV);
        calendarFilterDownIB = (ImageButton) getView().findViewById(R.id.reportFrag_calendarFilterDownIB);
        context = getContext();
        slideInAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_top_left);
        slideOutAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_left);
        noReportTV = (TextView) getView().findViewById(R.id.reportFrag_NoReportTV);
        calendarResultLL = (LinearLayout) getView().findViewById(R.id.reportFrag_calendarResultLL);
        calendarFilterIB = (ImageButton) getView().findViewById(R.id.reportFrag_calendarFilterIB);
        cubeUtils = new BaseFunction(context);

        //SALES REPORT SECTION
        salesResultLL = (LinearLayout) getView().findViewById(R.id.reportFrag_salesResultLL);
        salesFilterIB = (ImageButton) getView().findViewById(R.id.reportFrag_SRFilterIB);
        salesFilterDownIB = (ImageButton) getView().findViewById(R.id.reportFrag_reportFilterDownIB);
        salesModuleSpinner = (Spinner) getView().findViewById(R.id.reportFrag_SRModuleSpinner);
        salesClientSpinner = (Spinner) getView().findViewById(R.id.reportFrag_SRClientSpinner);
        salesPersonSpinner = (Spinner) getView().findViewById(R.id.reportFrag_SRSalesPersonSpinner);
        salesStartDateIB = (ImageButton) getView().findViewById(R.id.reportFrag_SRStartDateImgButton);
        salesEndDateIB = (ImageButton) getView().findViewById(R.id.reportFrag_SREndDateImgButton);
        SRInqLL = (LinearLayout) getView().findViewById(R.id.reportFrag_SRInquiryRecyclerLL);
        SREstLL = (LinearLayout) getView().findViewById(R.id.reportFrag_SREstimationRecyclerLL);
        SRQuoLL = (LinearLayout) getView().findViewById(R.id.reportFrag_SRQuotationRecyclerLL);
        SRPoLL = (LinearLayout) getView().findViewById(R.id.reportFrag_SRPoRecyclerLL);
        SRJobsLL = (LinearLayout) getView().findViewById(R.id.reportFrag_SRJobsRecyclerLL);
        SRInqRecycler = (RecyclerView) getView().findViewById(R.id.reportFrag_SRInquiryRecycler);
        SREstRecycler = (RecyclerView) getView().findViewById(R.id.reportFrag_SREstimationRecycler);
        SRQuoRecycler = (RecyclerView) getView().findViewById(R.id.reportFrag_SRQuotationRecycler);
        SRPoRecycler = (RecyclerView) getView().findViewById(R.id.reportFrag_SRPoRecycler);
        SRJobsRecycler = (RecyclerView) getView().findViewById(R.id.reportFrag_SRJobsRecycler);


        //SETTING ALL ARRAY ADAPTERS
        reportTypeAdapter = ArrayAdapter.createFromResource(context, R.array.reportType, R.layout.spinner_layout);
        reportTypeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        reportTypeSpinner.setAdapter(reportTypeAdapter);
        clientNameAdapter = ArrayAdapter.createFromResource(context, R.array.accounts_name, R.layout.spinner_layout);
        clientNameAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        calendarClientSpinner.setAdapter(clientNameAdapter);
        salesPersonAdapter = ArrayAdapter.createFromResource(context, R.array.contact_name, R.layout.spinner_layout);
        salesPersonAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        calendarSalesPersonSpinner.setAdapter(salesPersonAdapter);
        moduleAdapter = ArrayAdapter.createFromResource(context,R.array.modules,R.layout.spinner_layout);
        moduleAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);


        //CALENDAR REPORT SECTION
        calendarStartDateIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateTV(calendarStartDateTV);
            }
        });
        calendarEndDateIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateTV(calendarEndDateTV);
            }
        });

        /*SETTING UP THE CALENDAR RESULT LINEAR LAYOUT*/
        calendarFilterIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation moveUpAnimation = AnimationUtils.loadAnimation(context,R.anim.move_up);
                Animation slideFromBottom = AnimationUtils.loadAnimation(context,R.anim.slide_in_bottom);
                calendarActivityLL.setAnimation(moveUpAnimation);
                calendarActivityLL.setVisibility(View.GONE);
                calendarResultLL.setAnimation(slideFromBottom);
                calendarResultLL.setVisibility(View.VISIBLE);
            }
        });
        calendarFilterDownIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Animation slideInTop = AnimationUtils.loadAnimation(context,R.anim.slide_in_top);
                Animation moveDown = AnimationUtils.loadAnimation(context,R.anim.move_down);
                calendarResultLL.setAnimation(slideInTop);
                calendarResultLL.setVisibility(View.GONE);
                calendarActivityLL.setAnimation(moveDown);
                calendarActivityLL.setVisibility(View.VISIBLE);
            }
        });

        //SALES REPORT SECTION
        salesPersonSpinner.setAdapter(salesPersonAdapter);
        salesClientSpinner.setAdapter(clientNameAdapter);
        salesModuleSpinner.setAdapter(moduleAdapter);
        salesFilterIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation moveUpAnimation = AnimationUtils.loadAnimation(context,R.anim.move_up);
                Animation slideFromBottom = AnimationUtils.loadAnimation(context,R.anim.slide_in_bottom);
                salesActivityLL.setAnimation(moveUpAnimation);
                salesActivityLL.setVisibility(View.GONE);
                salesResultLL.setAnimation(slideFromBottom);
                salesResultLL.setVisibility(View.VISIBLE);
                Log.i("OnClick","Success");
                setSalesReport();
            }
        });

        salesFilterDownIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation slideInTop = AnimationUtils.loadAnimation(context,R.anim.slide_in_top);
                Animation moveDown = AnimationUtils.loadAnimation(context,R.anim.move_down);
                salesResultLL.setAnimation(slideInTop);
                salesResultLL.setVisibility(View.GONE);
                salesActivityLL.setAnimation(moveDown);
                salesActivityLL.setVisibility(View.VISIBLE);
            }
        });

        //SETTING UP THE REPORT LAYOUT BASED ON THE SPINNER SELECTION WITH ANIMATIONS
        reportTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Animation slideInLeftAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_top_left);
                Animation slideInRightAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
                Animation slideOutLeftAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_left);
                Animation slideOutRightAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
                Animation moveDownAnimation = AnimationUtils.loadAnimation(context,R.anim.move_down);
                Animation slideInLeftStraight = AnimationUtils.loadAnimation(context,R.anim.slide_in_left_straight);
                Animation moveUpAnimation = AnimationUtils.loadAnimation(context,R.anim.move_up);

                if (reportTypeSpinner.getSelectedItem().equals("Calendar Report")) {

                    if(noReportTV.getVisibility() == View.VISIBLE){
                        noReportTV.setVisibility(View.GONE);
                    }
                    if(salesActivityLL.getVisibility() == View.VISIBLE) {
                        salesActivityLL.setAnimation(slideOutRightAnimation);
                        salesActivityLL.setVisibility(View.GONE);
                    }
                    if(salesResultLL.getVisibility() == View.VISIBLE){
                        salesResultLL.setAnimation(slideOutRightAnimation);
                        salesResultLL.setVisibility(View.GONE);
                    }
                    calendarActivityLL.setAnimation(slideInLeftAnimation);
                    calendarActivityLL.setVisibility(View.VISIBLE);
                } else if (reportTypeSpinner.getSelectedItem().equals("Sales Report")) {

                    if(noReportTV.getVisibility() == View.VISIBLE){
                        noReportTV.setVisibility(View.GONE);
                    }
                    if(calendarResultLL.getVisibility() == View.VISIBLE){
                        calendarResultLL.setAnimation(slideOutLeftAnimation);
                        calendarResultLL.setVisibility(View.GONE);
                    }
                    if(calendarActivityLL.getVisibility() == View.VISIBLE) {
                        calendarActivityLL.setAnimation(slideOutLeftAnimation);
                        calendarActivityLL.setVisibility(View.GONE);
                    }
                    salesActivityLL.setAnimation(slideInRightAnimation);
                    salesActivityLL.setVisibility(View.VISIBLE);
                } else {
                    noReportTV.setAnimation(moveDownAnimation);
                    noReportTV.setVisibility(View.VISIBLE);
                    if(calendarResultLL.getVisibility() == View.VISIBLE){
                        calendarResultLL.setAnimation(moveUpAnimation);
                        calendarResultLL.setVisibility(View.GONE);
                    }
                    if(salesResultLL.getVisibility() == View.VISIBLE){
                        salesResultLL.setAnimation(moveUpAnimation);
                        salesResultLL.setVisibility(View.GONE);
                    }
                    if (calendarActivityLL.getVisibility() == View.VISIBLE) {
                        calendarActivityLL.setAnimation(slideOutLeftAnimation);
                        new CountDownTimer(1000,1000){

                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                calendarActivityLL.setVisibility(View.GONE);
                            }
                        }.start();

                    }
                    if (salesActivityLL.getVisibility() == View.VISIBLE) {

                        salesActivityLL.setAnimation(slideOutRightAnimation);
                        new CountDownTimer(1000,1000){

                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                salesActivityLL.setVisibility(View.GONE);
                            }
                        }.start();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }

    public String setDateTV(final TextView textView){
        String temp="";
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setCallBack(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String startDate = i2 + "/" + BaseActivity.MonthName(i1) + "/" + i;
                textView.setText(startDate);
            }
        });
        datePickerFragment.show(getFragmentManager(), "Date");
        return temp;
    }

    public void setSalesReport(){

        Log.i("In setSalesReport","Success");
        String clientName = salesClientSpinner.getSelectedItem().toString();
        String moduleName = salesModuleSpinner.getSelectedItem().toString();
        String salesPerson = salesPersonSpinner.getSelectedItem().toString();
        inquiryList = new ArrayList<>();
        Inquiry sample = new Inquiry();
        sample.setInquiryID("INQ/HBK-CUBE/FRD/Jul-2016/1");
        sample.setInquirySubject("FireRated Doors");
        sample.setInquiryCompanyName("HBK");
        sample.setInquiryStatus("Approved");
        sample.setSalesPerson("Charles");
        inquiryList.add(sample);

        inquiryRecyclerAdapter = new InquiryRecyclerAdapter(context,inquiryList);
        SRInqRecycler.setLayoutManager(new LinearLayoutManager(context));
        SRInqRecycler.setAdapter(inquiryRecyclerAdapter);

        estimationList = new ArrayList<>();
        Estimation estSample = new Estimation();
        estSample.setEstimationID("EST/HBK-CUBE/FRD/Jul-2016/1");
        estSample.setClientAccount("HBK");
        estSample.setGrandTotal(Float.valueOf("96000"));

        estimationList.add(estSample);
        estimationRecyclerAdapter = new EstimationListRecyclerAdapter(estimationList,context);
        SREstRecycler.setLayoutManager(new LinearLayoutManager(context));
        SREstRecycler.setAdapter(estimationRecyclerAdapter);

        quotationList = new ArrayList<>();
        Quotation check = new Quotation();
        check.setQuotationClient("HBK");
        check.setQuotationTitle("Doors Inquiry");
        check.setQuotationTotal(8000);
        check.setQuotationNo("Quo/HBK/Cub/DRS/29-July-2016");
        check.setQuotationStatus("Approved");
        check.setQuotationCreateDate("17/Feb/2016");
        check.setQuotationEditDate("17/May/2016");
        quotationList.add(check);

        quotationRecyclerAdapter = new QuotationViewListRecyclerAdapter(quotationList,context);
        SRQuoRecycler.setLayoutManager(new LinearLayoutManager(context));
        SRQuoRecycler.setAdapter(quotationRecyclerAdapter);

        poList = new ArrayList<>();
        PurchaseOrder poCheck = new PurchaseOrder();
        poCheck.setClientName("HBK");
        poCheck.setPONumber("PO/HBK/DRS/CUBE/12-May-2016");
        poCheck.setPOTitle("Fixing Doors");
        poList.add(poCheck);

        purchaseRecyclerAdapter = new PurchaseListRecyclerAdapter(poList,context);
        SRPoRecycler.setLayoutManager(new LinearLayoutManager(context));
        SRPoRecycler.setAdapter(purchaseRecyclerAdapter);

        jobList = new ArrayList<>();
        Job jobCheck = new Job();
        jobCheck.setJobNumber("JOB/HBK/DRS/CUBE/1234");
        jobCheck.setJobName("Door Job");
        jobCheck.setJobClient("HBK");
        jobCheck.setJobStartDate("12/Jun/2016");
        jobCheck.setJobEndDate("1/Oct/2016");
        jobCheck.setJobAssigned("Fazith");
        jobList.add(jobCheck);

        jobRecyclerAdapter = new JobListRecyclerAdapter(jobList,context);
        SRJobsRecycler.setLayoutManager(new LinearLayoutManager(context));
        SRJobsRecycler.setAdapter(jobRecyclerAdapter);
    }

}
