package com.cube_me.cubeme_crm.Quotations;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.Estimation.EstimationJobs;
import com.cube_me.cubeme_crm.R;

import java.util.ArrayList;
import java.util.List;

public class QuotationNew extends AppCompatActivity {

    Intent i;
    ArrayList jobArrayList;
//    List<EstimationJobs> jobsList;
    EstimationJobs job;
    String estimationID;
    String estSubString;
//    String quotationID;
//    String clientName;
    Quotation quotation;

    RecyclerView jobsRecyclerView;
    QuotationJobsListRecyclerAdapter jobListAdapter;
    Context context;
    TextView quotationIDTV;
    TextView totalTV;
    Switch percentageSwitch;
//    float total;
    final static int DISCOUNT_VALUE = 0;
    final static int DISCOUNT_PERCENT = 1;
    int discountFlag;
    EditText discountEditText;
    TextView grossTotalTV;
    TextView clientNameTV;
    EditText titleEditText;
    Spinner statusSpinner;


//    float discount;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quotation_new);

        // TOOLBAR INIT
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("New Quotation");

        //BASIC INIT
        context = getApplicationContext();
        quotation = new Quotation();
        quotation.quotationDiscount = 0;
        quotationIDTV = (TextView) findViewById(R.id.quotationNew_estIDTV);
        totalTV = (TextView) findViewById(R.id.quotationNew_totalTV);
        percentageSwitch = (Switch) findViewById(R.id.quotationNew_percentageSwitch);
        discountFlag = DISCOUNT_VALUE;
        discountEditText = (EditText) findViewById(R.id.quotationNew_discountET);
        grossTotalTV = (TextView) findViewById(R.id.quotationNew_grossTotalTV);
        clientNameTV = (TextView) findViewById(R.id.quotationNew_clientNameTV);
        titleEditText = (EditText) findViewById(R.id.quotationNew_titleET);
        statusSpinner = (Spinner) findViewById(R.id.quotationNew_statusSpinner);
        statusSpinner.setAdapter(BaseActivity.inquiryStatusSpinnerAA);


        //GETTING THE VALUES FROM INTENT
        i = getIntent();
        jobArrayList = i.getParcelableArrayListExtra("JobList");
        quotation.quotationJobs = (List) jobArrayList;
        quotation.quotationTotal= i.getFloatExtra("Total",0);
        totalTV.setText(BaseActivity.decimalFormat.format(quotation.quotationTotal));
        grossTotalTV.setText(BaseActivity.decimalFormat.format(quotation.quotationTotal));
        quotation.quotationClient = i.getStringExtra("ClientName");
        clientNameTV.setText(quotation.quotationClient);
        estimationID = i.getStringExtra("EstimationID");
        estSubString = estimationID.substring(4);
        quotation.quotationNo = "QUO/"+estSubString;
        quotationIDTV.setText(quotation.quotationNo);


        //GETTING THE SWITCH STATE AND ASSIGNING THE FLAG FOR DISCOUNT CALCULATION
        percentageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    discountFlag = DISCOUNT_PERCENT;
                    if(!TextUtils.isEmpty(discountEditText.getText())){
                        discountWithPercent();
                    }
                }
                else {
                    discountFlag = DISCOUNT_VALUE;
                    if(!TextUtils.isEmpty(discountEditText.getText())){
                        discountValue();
                    }
                }
            }
        });



        //SETTING LISTENER ON EDIT TEXT AND CALCULATING THE DISCOUNT VALUE

        discountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(discountEditText.getText())){
                    if(discountFlag == DISCOUNT_PERCENT){
                        discountWithPercent();
                    }else if(discountFlag == DISCOUNT_VALUE){
                        discountValue();
                    }
                }else {
                    grossTotalTV.setText(BaseActivity.decimalFormat.format(quotation.quotationTotal));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        // RECYCLER VIEW
        jobsRecyclerView = (RecyclerView) findViewById(R.id.quotationNew_jobsRecycler);
        jobListAdapter = new QuotationJobsListRecyclerAdapter(quotation.quotationJobs,context);
        jobsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        jobsRecyclerView.setAdapter(jobListAdapter);

    }

    public void discountWithPercent(){
        float temp = 0;
        if(!discountEditText.getText().toString().equals(".")){
            temp = Float.valueOf(discountEditText.getText().toString());;
        }
        quotation.quotationGrossTotal= quotation.quotationTotal - (quotation.quotationTotal*(temp/100));
        grossTotalTV.setText(BaseActivity.decimalFormat.format(quotation.quotationGrossTotal));

    }

    public void discountValue(){
        float temp = 0;
        if(!discountEditText.getText().toString().equals(".")){
           temp = Float.valueOf(discountEditText.getText().toString());;
        }
        quotation.quotationGrossTotal = quotation.quotationTotal - temp;
        grossTotalTV.setText(BaseActivity.decimalFormat.format(quotation.quotationGrossTotal));
    }

    public void setQuotationToParcel(){
        quotation.quotationStatus = statusSpinner.getSelectedItem().toString();
        quotation.setQuotationTitle(titleEditText.getText().toString());
        quotation.setQuotationDiscount(Float.valueOf(discountEditText.getText().toString()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quotation_new,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_quotation_saveAsWord:
                QuotationSaveToWord newDoc = new QuotationSaveToWord();
                newDoc.writeWord();
                break;
            case R.id.action_quotation_saveOffline:
                setQuotationToParcel();
                QuotationDataBaseAdapter quotationDataBaseAdapter = new QuotationDataBaseAdapter(context);
                quotationDataBaseAdapter.deleteQuotation(quotation.quotationNo);
                long resultKey = quotationDataBaseAdapter.insertBaseDate(quotation);
                if(resultKey<0){
                    Toast.makeText(context, "Error While Saving", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Quotation Successfully Saved", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
