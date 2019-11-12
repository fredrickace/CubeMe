package com.cube_me.cubeme_crm.Estimation;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cube_me.cubeme_crm.ClickListener;
import com.cube_me.cubeme_crm.EditDeleteCallBackFragment;
import com.cube_me.cubeme_crm.EditDeleteFragment;
import com.cube_me.cubeme_crm.R;
import com.cube_me.cubeme_crm.RecyclerTouchListener;
import com.cube_me.cubeme_crm.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class EstimationSaved extends AppCompatActivity implements EditDeleteFragment.EditDeleteCommunicator{

    Toolbar toolbar;
    RecyclerView recyclerView;
    List<Estimation> estimationList;
    List<EstimationDirectMaterial> DMList;
    Estimation estimation;
    EstimationListRecyclerAdapter recyclerAdapter;
    EstimationDatabaseAdapter estimationDatabaseAdapter;
    public int recyclerPosition;

    TextView EstIdTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estimation_saved);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Saved Estimations");
        estimationList = new ArrayList<>();
        DMList = new ArrayList<>();

        estimation = new Estimation();
        estimationDatabaseAdapter = new EstimationDatabaseAdapter(this);
        estimationList = estimationDatabaseAdapter.getBaseData();

//        Estimation current = estimationList.get(2);
//        Toast.makeText(EstimationSaved.this, current.estimationID, Toast.LENGTH_SHORT).show();

        recyclerView = (RecyclerView) findViewById(R.id.estSaved_Recycler);
        recyclerAdapter = new EstimationListRecyclerAdapter(estimationList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                recyclerPosition = position;
                EditDeleteFragment editDeleteFragment = new EditDeleteFragment();
                editDeleteFragment.show(getSupportFragmentManager(),"Edit");


            }

            @Override
            public void onLongClick(final View view, final int position) {

            }
        }));
        recyclerView.setAdapter(recyclerAdapter);

    }

    public void updateSavedRecycler(){
        recyclerAdapter = new EstimationListRecyclerAdapter(estimationList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setAdapter(recyclerAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    public void getEstimationFromDB(){
        //        EstIdTV = (TextView) view.findViewById(R.id.estSavedRow_estID);
        String EstID = estimationList.get(recyclerPosition).estimationID;


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

    @Override
    public void deleteFromDialog() {

        final String EstID = estimationList.get(recyclerPosition).estimationID;
        new AlertDialog.Builder(EstimationSaved.this).setMessage(R.string.confirmDelete).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                estimationList.remove(recyclerPosition);
                int deleteResultKey = estimationDatabaseAdapter.deleteEstimation(EstID);
                if (deleteResultKey < 0) {
                    Toast.makeText(EstimationSaved.this, "No value to delete", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EstimationSaved.this, "Successfully Deleted the records", Toast.LENGTH_SHORT).show();
                }
                updateSavedRecycler();
            }
        }).setNegativeButton("No", null).create().show();
    }

    @Override
    public void editFromDialog() {

        getEstimationFromDB();
        Intent editIntent = new Intent(getApplicationContext(),Estimation.class);
        editIntent.putExtra("EstimationEdit", estimation);
        editIntent.putExtra("Flag",Estimation.ESTIMATION_EDIT);
        startActivity(editIntent);

    }

    @Override
    public void viewFromDialog() {

        getEstimationFromDB();
        Intent previewIntent = new Intent(getApplicationContext(), EstimationPreview.class);
        previewIntent.putExtra("Preview", estimation);
        startActivity(previewIntent);
    }
}



