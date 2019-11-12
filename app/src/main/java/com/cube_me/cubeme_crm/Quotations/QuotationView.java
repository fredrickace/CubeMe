package com.cube_me.cubeme_crm.Quotations;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.Estimation.EstimationJobs;
import com.cube_me.cubeme_crm.R;

import java.util.List;

public class QuotationView extends AppCompatActivity {

    Toolbar toolbar;
    Context context;
    Intent fromIntent;

    //LAYOUT VARIABLES
    TextView quoIdTV;
    TextView clientNameTV;
    TextView quoStatusTV;
    RecyclerView recycler;
    TextView quoTotalTV;
    TextView quoDiscountTV;
    TextView quoGrossTotalTV;
    TextView quoTitleTV;

    QuotationJobsListRecyclerAdapter jobRecyclerAdapter;
    List<EstimationJobs> jobsList;


    Quotation quotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quotation_view);

        //TOOLBAR INIT
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.setTitle();

        //BASIC INIT
        context = getApplicationContext();
        quoIdTV = (TextView) findViewById(R.id.quoView_quoIDTV);
        clientNameTV = (TextView) findViewById(R.id.quoView_clientNameTV);
        quoStatusTV = (TextView) findViewById(R.id.quoView_statusTV);
        quoTotalTV = (TextView) findViewById(R.id.quoView_totalTV);
        quoGrossTotalTV = (TextView) findViewById(R.id.quoView_grossTotalTV);
        quoDiscountTV = (TextView) findViewById(R.id.quoView_discountTV);
        quoTitleTV = (TextView) findViewById(R.id.quoView_TitleTV);


        //GET THE QUOTATION ID FROM THE INTENT
        fromIntent = getIntent();
        quotation = fromIntent.getParcelableExtra("Quotation");
        toolbar.setTitle(quotation.quotationNo);


        //SETTING UP THE JOBS RECYCLER
        recycler = (RecyclerView) findViewById(R.id.quoView_jobsRecycler);
        jobRecyclerAdapter = new QuotationJobsListRecyclerAdapter(quotation.quotationJobs,context);
        recycler.setLayoutManager(new LinearLayoutManager(context));
        recycler.setAdapter(jobRecyclerAdapter);

        //SETTING UP ALL THE FIELDS
        quoIdTV.setText(quotation.quotationNo);
        clientNameTV.setText(quotation.quotationClient);
        quoStatusTV.setText(quotation.quotationStatus);
        quoTotalTV.setText(BaseActivity.decimalFormat.format(quotation.quotationTotal));
        quoGrossTotalTV.setText(BaseActivity.decimalFormat.format(quotation.quotationGrossTotal));
        quoDiscountTV.setText(BaseActivity.decimalFormat.format(quotation.quotationDiscount));
        quoTitleTV.setText(quotation.quotationTitle);



    }
}
