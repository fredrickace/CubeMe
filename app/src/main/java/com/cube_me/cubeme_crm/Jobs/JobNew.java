package com.cube_me.cubeme_crm.Jobs;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cube_me.cubeme_crm.AttachmentListRecyclerAdapter;
import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.DatePickerFragment;
import com.cube_me.cubeme_crm.R;

import java.util.ArrayList;
import java.util.List;

public class JobNew extends AppCompatActivity implements AttachmentListRecyclerAdapter.AttachmentRecyclerCommunicator {


    Toolbar toolbar;

    Job jobNew;
    ImageButton startDateImgButton;
    ImageButton endDateImgButton;
    TextView startDateTV;
    TextView endDateTV;
    Button attachDocButton;
    public List<String> attachmentNameList;
    List<Uri> attachmentList;
    AttachmentListRecyclerAdapter attachmentListRecyclerAdapter;
    RecyclerView attachmentRecycler;
    public final static int PICK_FILE = 20;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_new);

        //TOOLBAR INIT
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("New Job");


        //BASIC INIT
        jobNew = new Job();
        startDateTV = (TextView) findViewById(R.id.jobsNew_startDateTV);
        endDateTV = (TextView) findViewById(R.id.jobsNew_EndDateTV);
        attachDocButton = (Button) findViewById(R.id.jobsNew_addAttachementsButton);
        attachmentNameList = new ArrayList<>();
        attachmentList = new ArrayList<>();
        attachmentRecycler = (RecyclerView) findViewById(R.id.jobsNew_attachmentsRecycler);
        context = getApplicationContext();

        //SETTING UP THE ATTACHMENT RECYCLER VIEW
        attachmentListRecyclerAdapter = new AttachmentListRecyclerAdapter(attachmentNameList, context, this);
//        attachmentListRecyclerAdapter.setCallBack(new AttachmentListRecyclerAdapter.AttachmentRecyclerCommunicator() {
//            @Override
//            public void deleteAttachedPosition(int position) {
//                attachmentNameList.remove(position);
//                updateAttachmentRecycler();
//            }
//
//        });
        attachmentRecycler.setLayoutManager(new LinearLayoutManager(context));
        attachmentRecycler.setAdapter(attachmentListRecyclerAdapter);

        attachDocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFile();
            }
        });

        //SETTING JOB CREATE & EDIT -- DATE & TIME
        jobNew.setJobEditDate(BaseActivity.getCurrentDate());
        jobNew.setJobEditTime(BaseActivity.getCurrentTime());


        startDateImgButton = (ImageButton) findViewById(R.id.jobsNew_startDateImgButton);
        startDateImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStartDateTV();
            }
        });
        endDateImgButton = (ImageButton) findViewById(R.id.jobsNew_EndDateImgButton);
        endDateImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEndDateTV();
            }
        });
    }

    public void updateAttachmentRecycler() {
        attachmentListRecyclerAdapter = new AttachmentListRecyclerAdapter(attachmentNameList, context, this);
        attachmentRecycler.setLayoutManager(new LinearLayoutManager(context));
        attachmentRecycler.setAdapter(attachmentListRecyclerAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void setStartDateTV() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setCallBack(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                jobNew.setJobStartDate(String.valueOf(i2 + "/" + BaseActivity.MonthName(i1) + "/" + i));
                startDateTV.setText(jobNew.jobStartDate);
            }
        });
        datePickerFragment.show(getSupportFragmentManager(), "StartDate");
    }

    void setEndDateTV() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setCallBack(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                jobNew.setJobEndDate(String.valueOf(i2 + "/" + BaseActivity.MonthName(i1) + "/" + i));
                endDateTV.setText(jobNew.jobEndDate);
            }
        });
        datePickerFragment.show(getSupportFragmentManager(), "EndDate");
    }

    protected void pickFile() {

        //FILE PICKER
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, PICK_FILE);
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);

        if (reqCode == PICK_FILE && resCode == RESULT_OK && data != null) {

            //GETTING THE FILE NAME & SIZE OF THE FILE IMPORTED
            Uri selectedFile = data.getData();
            attachmentList.add(selectedFile);
            Log.i("Attachment List Size", String.valueOf(attachmentList.size()));
            String fileName = null;
            String fileSize = null;
            String scheme = selectedFile.getScheme();
            if (scheme.equals("file")) {
                fileName = selectedFile.getLastPathSegment();
            } else if (scheme.equals("content")) {
                fileName = "Find more Answers";
                Cursor cursor = getApplicationContext().getContentResolver().query(selectedFile, null, null, null, null);
                if (cursor != null && cursor.getCount() != 0) {
                    int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                    cursor.moveToFirst();
                    fileName = cursor.getString(columnIndex);
                    fileSize = cursor.getString(sizeIndex);
                    attachmentNameList.add(fileName);
                    updateAttachmentRecycler();
                }
            }
            Log.i("File Name", fileName);
            Log.i("File Size", fileSize);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.move_up,R.anim.move_down);

    }

    @Override
    public void deleteAttachedPosition(int position) {
        attachmentNameList.remove(position);
        attachmentList.remove(position);
        Log.i("Attachment List Size", String.valueOf(attachmentList.size()));
        updateAttachmentRecycler();
    }
}
