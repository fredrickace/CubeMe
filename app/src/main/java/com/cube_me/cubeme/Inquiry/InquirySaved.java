package com.cube_me.cubeme.Inquiry;

import android.content.Context;
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

import com.cube_me.cubeme.ClickListener;
import com.cube_me.cubeme.EditDeleteFragment;
import com.cube_me.cubeme.R;
import com.cube_me.cubeme.RecyclerTouchListener;
import com.cube_me.cubeme.SimpleDividerItemDecoration;

import java.util.List;

public class InquirySaved extends AppCompatActivity implements EditDeleteFragment.EditDeleteCommunicator{

    Toolbar toolbar;
    RecyclerView inquiryRV;
    InquiryRecyclerAdapter inquiryRecyclerAdapter;
    List<Inquiry> inquiryList;
    Context context;
    int recyclerViewPosition;
    InquiryDatabaseAdapter inquiryDatabaseAdapter;


    public static final String INQUIRY_FLAG = "InquiryNew";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inquiry_saved);

        //TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Offline Inquiries");

        context = getApplicationContext();
        inquiryDatabaseAdapter = new InquiryDatabaseAdapter(context);
        inquiryList = inquiryDatabaseAdapter.getAllInquiries();

        //BASIC INIT

        inquiryRV = (RecyclerView) findViewById(R.id.inquirySaved_recycler);
        inquiryRecyclerAdapter = new InquiryRecyclerAdapter(context,inquiryList);
        inquiryRV.addItemDecoration(new SimpleDividerItemDecoration(context));
        inquiryRV.addOnItemTouchListener(new RecyclerTouchListener(context, inquiryRV, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                recyclerViewPosition = position;
                EditDeleteFragment editDeleteFragment = new EditDeleteFragment();
                editDeleteFragment.show(getSupportFragmentManager(),"Edit");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        inquiryRV.setLayoutManager(new LinearLayoutManager(context));
        inquiryRV.setAdapter(inquiryRecyclerAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateRecycler();
    }

    public void updateRecycler(){
        inquiryList = inquiryDatabaseAdapter.getAllInquiries();
        inquiryRecyclerAdapter = new InquiryRecyclerAdapter(context,inquiryList);
        inquiryRV.setLayoutManager(new LinearLayoutManager(context));
        inquiryRV.setAdapter(inquiryRecyclerAdapter);
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

    @Override
    public void deleteFromDialog() {
        Inquiry inquiry = inquiryList.get(recyclerViewPosition);
        final String inquiryID = inquiry.inquiryID;
        new AlertDialog.Builder(InquirySaved.this).setMessage("Delete "+inquiry.inquiryID+"?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                inquiryDatabaseAdapter.deleteInquiry(inquiryID);
                updateRecycler();
            }
        }).setNegativeButton("No",null).create().show();

    }

    @Override
    public void editFromDialog() {
        Inquiry inquiry = inquiryList.get(recyclerViewPosition);
        Intent editInqIntent = new Intent(context,InquiryNew.class);
//        editInqIntent.putExtra("Flag",INQUIRY_FLAG);
        editInqIntent.putExtra("InquiryFlag",InquiryNew.INQUIRY_EDIT);
//        editInqIntent.putExtra("EditInquiry",inquiry);
        editInqIntent.putExtra("InquiryId",inquiry.inquiryID);
        startActivity(editInqIntent);

    }

    @Override
    public void viewFromDialog() {
        Inquiry inquiry = inquiryList.get(recyclerViewPosition);
        Intent editInqIntent = new Intent(context,InquiryView.class);
        editInqIntent.putExtra("ViewInquiry",inquiry);
        startActivity(editInqIntent);

    }
}
