package com.cube_me.cubeme.Estimation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cube_me.cubeme.BaseActivity;
import com.cube_me.cubeme.Quotations.QuotationNew;
import com.cube_me.cubeme.R;

import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

public class EstimationPreview extends AppCompatActivity {

    Toolbar toolbar;
    Context context;
    final static int ESTIMATION_PREVIEW_ADAPTER = 1;
    Estimation previewEstimation;
    TextView overHeadPercentTV;
    TextView marginPercentTV;
    TextView grandTotalTV;
    TextView estimationCreateDateTV;
    TextView estimationEditDateTV;
    TextView estimationCreateTimeTV;
    TextView estimationEditTimeTV;
    TextView estimationClientTV;
    LinearLayout jobsTotalLayout;

    //DIRECT MATERIAL VARIABLES
    List<EstimationDirectMaterial> directMaterialList;
    RecyclerView directMaterialRecyclerView;
    EstimationDirectMaterialRecyclerAdapter directMaterialPreviewAdapter;
    TextView directMaterialTotalTV;
    LinearLayout directMaterialLayout;

    //MANPOWER VARIABLES
    List<EstimationOtherMaterials> manPowerList;
    RecyclerView manPowerRecyclerView;
    LinearLayout manPowerLayout;
    EstimationOtherMaterialRecyclerAdapter manPowerAdapter;
    TextView manPowerTotalTV;

    //TOOLS VARIABLES
    RecyclerView toolsRecyclerView;
    LinearLayout toolsLayout;
    EstimationOtherMaterialRecyclerAdapter toolsAdapter;
    TextView toolsTotalTV;

    //TRANSPORT VARIABLES
    RecyclerView transportRecyclerView;
    LinearLayout transportLayout;
    EstimationOtherMaterialRecyclerAdapter transportAdapter;
    TextView transportTotalTV;

    //CONSUMABLE VARIABLES
    RecyclerView consumableRecyclerView;
    LinearLayout consumableLayout;
    EstimationConsumableRecyclerAdapter consumableAdapter;
    TextView consumableTotalTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estimation_new_preview);

        context = getApplicationContext();
        Intent i = getIntent();
        previewEstimation = i.getParcelableExtra("Preview");

        //TOOLBAR INIT
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(previewEstimation.estimationID);

        //BASIC INIT
        estimationCreateDateTV = (TextView) findViewById(R.id.estPreview_createdDateTV);
        estimationEditDateTV = (TextView) findViewById(R.id.estPreview_editDateTV);
        estimationCreateTimeTV = (TextView) findViewById(R.id.estPreview_createdTimeTV);
        estimationEditTimeTV = (TextView) findViewById(R.id.estPreview_editTimeTV);
        estimationClientTV = (TextView) findViewById(R.id.estPreview_clientAccountTV);

        //SETTING THE DATE AND TIME FIELDS
        estimationCreateDateTV.setText(previewEstimation.estimationCreateDate);
        estimationEditDateTV.setText(previewEstimation.estimationLastEditDate);
        estimationCreateTimeTV.setText(previewEstimation.estimationCreateTime);
        estimationEditTimeTV.setText(previewEstimation.estimationLastEditTime);
        estimationClientTV.setText(previewEstimation.clientAccount);

        //SETTING ALL THE TOTAL VALUES
        overHeadPercentTV = (TextView) findViewById(R.id.estPreview_overHeadPercent);
        overHeadPercentTV.setText(BaseActivity.decimalFormat.format(previewEstimation.overHeadPercentage));

        jobsTotalLayout = (LinearLayout) findViewById(R.id.estPreview_jobTotalContainer);
        for(int x = 0; x< Estimation.jobsList.size(); x++){
            EstimationJobs jobs = Estimation.jobsList.get(x);
            LayoutInflater jobsLayoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View jobView = jobsLayoutInflater.inflate(R.layout.estimation_newpreview_jobrow,null);
            TextView jobTotalTV = (TextView) jobView.findViewById(R.id.estPreviewjobRow_jobTotal);
            TextView jobTV = (TextView) jobView.findViewById(R.id.estPreviewjobRow_job);
            jobTV.setText(jobs.jobName+" Total: ");
            jobTotalTV.setText("QR. "+BaseActivity.decimalFormat.format(jobs.jobTotal));
            jobsTotalLayout.addView(jobView);
        }

        marginPercentTV = (TextView) findViewById(R.id.estPreview_marginPercent);
        marginPercentTV.setText(BaseActivity.decimalFormat.format(previewEstimation.marginPercentage));

        grandTotalTV = (TextView) findViewById(R.id.estPreview_grandTotal);
        grandTotalTV.setText("QR. "+BaseActivity.decimalFormat.format(previewEstimation.grandTotal));

        directMaterialLayout = (LinearLayout) findViewById(R.id.estPreview_DirectMaterialsLayout);
        if (previewEstimation.estimationDirectMaterialList.size() > 0) {
            directMaterialLayout.setVisibility(View.VISIBLE);
            directMaterialRecyclerView = (RecyclerView) findViewById(R.id.estPreview_DirectMaterialsRecycler);
            directMaterialPreviewAdapter = new EstimationDirectMaterialRecyclerAdapter(previewEstimation.estimationDirectMaterialList, context, ESTIMATION_PREVIEW_ADAPTER);
            directMaterialRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            directMaterialRecyclerView.setAdapter(directMaterialPreviewAdapter);

            //Setting the Total value of DirectMaterial Total
            directMaterialTotalTV = (TextView) findViewById(R.id.estPreview_DirectMaterialTotalTV);
            directMaterialTotalTV.setText(" Direct Materials Total: QR " + BaseActivity.decimalFormat.format(previewEstimation.directMaterialTotal));
        }

        //END OF DIRECT MATERIAL SECTION
        //START OF MANPOWER SECTION

        manPowerLayout = (LinearLayout) findViewById(R.id.estPreview_manPowerLayout);
        manPowerList = previewEstimation.manPowerList;
        if (manPowerList.size() > 0) {
            manPowerLayout.setVisibility(View.VISIBLE);
            manPowerRecyclerView = (RecyclerView) findViewById(R.id.estPreview_manPowerRecycler);
            manPowerAdapter = new EstimationOtherMaterialRecyclerAdapter(context, previewEstimation.manPowerList, ESTIMATION_PREVIEW_ADAPTER);
            manPowerRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            manPowerRecyclerView.setAdapter(manPowerAdapter);

            //SETTING UP TOTAL VALUE IF MANPOWER SECTION
            manPowerTotalTV = (TextView) findViewById(R.id.estPreview_manPowerTotalTV);
            manPowerTotalTV.setText("Man Power Total: QR " + BaseActivity.decimalFormat.format(previewEstimation.manPowerTotal));
        }

        //END OF MANPOWER SECTION
        //START OF TOOLS SECTION

        toolsLayout = (LinearLayout) findViewById(R.id.estPreview_toolsLayout);
        if (previewEstimation.toolsList.size() > 0) {
            toolsLayout.setVisibility(View.VISIBLE);
            toolsRecyclerView = (RecyclerView) findViewById(R.id.estPreview_toolsRecycler);
            toolsAdapter = new EstimationOtherMaterialRecyclerAdapter(context, previewEstimation.toolsList, ESTIMATION_PREVIEW_ADAPTER);
            toolsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            toolsRecyclerView.setAdapter(toolsAdapter);

            //SETTING TOTAL VALUE FOR TOOLS SECTION
            toolsTotalTV = (TextView) findViewById(R.id.estPreview_toolsTotalTV);
            toolsTotalTV.setText("Tools Total: QR " + BaseActivity.decimalFormat.format(previewEstimation.toolsTotal));
        }

        //END OF TOOLS SECTION
        //START OF TRANSPORT SECTION

        transportLayout = (LinearLayout) findViewById(R.id.estPreview_transportLayout);
        if (previewEstimation.transportList.size() > 0) {
            transportLayout.setVisibility(View.VISIBLE);
            transportRecyclerView = (RecyclerView) findViewById(R.id.estPreview_transportRecycler);
            transportAdapter = new EstimationOtherMaterialRecyclerAdapter(context, previewEstimation.transportList, ESTIMATION_PREVIEW_ADAPTER);
            transportRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            transportRecyclerView.setAdapter(transportAdapter);

            //SETTING THE TOTAL VALUE OF TRANSPORT SECTION
            transportTotalTV = (TextView) findViewById(R.id.estPreview_transportTotalTV);
            transportTotalTV.setText("Transport Total: QR " + BaseActivity.decimalFormat.format(previewEstimation.transportTotal));
        }
        //END OF TRANSPORT SECTION
        //START OF CONSUMABLE SECTION

        consumableLayout = (LinearLayout) findViewById(R.id.estPreview_consumablesLayout);
        if (previewEstimation.consumableList.size() > 0) {
            consumableLayout.setVisibility(View.VISIBLE);
            consumableRecyclerView = (RecyclerView) findViewById(R.id.estPreview_consumablesRecycler);
            consumableAdapter = new EstimationConsumableRecyclerAdapter(context, previewEstimation.consumableList, ESTIMATION_PREVIEW_ADAPTER);
            consumableRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            consumableRecyclerView.setAdapter(consumableAdapter);

            //SETTING THE TOTAL VALUE OF CONSUMABLE SECTION
            consumableTotalTV = (TextView) findViewById(R.id.estPreview_consumablesTotalTV);
            consumableTotalTV.setText("Consumable Total: QR " + BaseActivity.decimalFormat.format(previewEstimation.consumablesTotal));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_estimation_view,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.action_EstimationView_Edit:
                Intent editIntent = new Intent(getApplicationContext(),Estimation.class);
                editIntent.putExtra("EstimationEdit", previewEstimation);
                editIntent.putExtra("Flag",Estimation.ESTIMATION_EDIT);
                startActivity(editIntent);
                break;

            //CONVERT ESTIMATION TO QUOTATION
            case R.id.action_EstimationView_convertQuotation:
                List<EstimationJobs> jobsListToSend = new ArrayList<>();
                for(int i =0; i<previewEstimation.jobsList.size(); i++){
                    EstimationJobs job = previewEstimation.jobsList.get(i);
                    float jobTotal = job.jobTotal;
                    float jobQuantity = job.jobQuantity;
                    float jobUnitPrice = jobTotal/jobQuantity;
                    jobUnitPrice = Float.valueOf(BaseActivity.decimalFormat.format(jobUnitPrice));
                    job.setJobUnitPrice(jobUnitPrice);
                    jobsListToSend.add(job);
                }
                ArrayList jobSendList = (ArrayList) jobsListToSend;
                Intent quotationIntent = new Intent(context, QuotationNew.class);
                quotationIntent.putParcelableArrayListExtra("JobList", jobSendList);
                quotationIntent.putExtra("ClientName",previewEstimation.clientAccount);
                quotationIntent.putExtra("EstimationID", previewEstimation.estimationID);
                quotationIntent.putExtra("Total",previewEstimation.grandTotal);
                startActivity(quotationIntent);
                break;

            //DOWNLOAD ESTIMATION AS EXCEL
            case R.id.action_EstimationView_download:
                EstimationSaveToExcel saveToExcel = new EstimationSaveToExcel();
                saveToExcel.getExcel(context, previewEstimation);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
