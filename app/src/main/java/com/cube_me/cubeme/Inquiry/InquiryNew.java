package com.cube_me.cubeme.Inquiry;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cube_me.cubeme.BaseActivity;
import com.cube_me.cubeme.MainActivity;
import com.cube_me.cubeme.R;

import java.util.Calendar;
import java.util.Random;

public class InquiryNew extends AppCompatActivity {

    Toolbar toolbar;
    Spinner departmentSpinner;
    Spinner statusSpinner;
    AutoCompleteTextView assignAutoText;
    AutoCompleteTextView companyAutoText;
    AutoCompleteTextView contactPersonAutoText;
    EditText contactPersonDesignationET;
    EditText subjectEditText;
    EditText descriptionEditText;
    EditText emailEditText;
    EditText contactNumberEditText;
    EditText contactNumberCodeEditText;
    Button addInquiryButton;
    TextView inquiryInfoTV;
    Spinner projectTypeSpinner;
    AutoCompleteTextView consultantACTV;
    AutoCompleteTextView mainContractorACTV;
    AutoCompleteTextView subContractorACTV;
    String inquiryID;
    String inquiryCreateDate;
    String inquiryCreateTime;
    String inquiryLastEditDate;
    String inquiryLastEditTime;
    String fromActivityName;

    Intent i;
    String inquiryFlag;
    public static final int RETURN_TRUE = 0;
    public static final int RETURN_FALSE = 1;
    public static final String INQUIRY_NEW = "NEW";
    public static final String INQUIRY_EDIT = "EDIT";
    Inquiry newInquiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inquiry_new);
        i = getIntent();


        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Inquiry");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);
        int hours = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        int am_pm = calendar.get(Calendar.AM_PM);
        String AM_PM = "PM";
        if(am_pm == Calendar.AM){
            AM_PM = "AM";
        }

        inquiryLastEditDate = date + "/" + month + "/" + year;
        inquiryCreateDate = inquiryLastEditDate;
        inquiryLastEditTime = hours + ":" + min + ":" + seconds + ":" + AM_PM;
        inquiryCreateTime = inquiryLastEditTime;



        //BASIC INIT
        subjectEditText = (EditText) findViewById(R.id.inquiry_subjectEditText);
        newInquiry = new Inquiry();
        descriptionEditText = (EditText) findViewById(R.id.inquiry_descritptionEditText);
        emailEditText = (EditText) findViewById(R.id.inquiry_emailEditText);
        contactNumberEditText = (EditText) findViewById(R.id.inquiry_contactNumberEditText);
        contactNumberCodeEditText = (EditText) findViewById(R.id.inquiry_contactNumberCountryCodeEditText);
        departmentSpinner = (Spinner) findViewById(R.id.inquiry_deparmentSpinner);
        statusSpinner = (Spinner) findViewById(R.id.inquiry_statusSpinner);
        companyAutoText = (AutoCompleteTextView) findViewById(R.id.inquiry_companyAutoComplete);
        contactPersonAutoText = (AutoCompleteTextView) findViewById(R.id.inquiry_contactPersonAutoComplete);
        contactPersonDesignationET = (EditText) findViewById(R.id.inquiry_contactPersonDesignationET);
        assignAutoText = (AutoCompleteTextView) findViewById(R.id.inquiry_assignAutoText);
        inquiryInfoTV = (TextView) findViewById(R.id.inquiry_info);
        projectTypeSpinner = (Spinner) findViewById(R.id.inquiry_jobsTypeSpinner);
        consultantACTV = (AutoCompleteTextView) findViewById(R.id.inquiry_jobsConsultantACTV);
        mainContractorACTV = (AutoCompleteTextView) findViewById(R.id.inquiry_jobsContractorACTV);
        subContractorACTV = (AutoCompleteTextView) findViewById(R.id.inquiry_jobsSubContractorACTV);
        addInquiryButton = (Button) findViewById(R.id.inquiry_newAddButton);

        //SETTING UP THE FUNCTIONALITY'S BASED ON THE NETWORK STATE
//        if(!BaseActivity.networkIsConnected){
//            addInquiryButton.setEnabled(false);
//        }


        //GENERATING RANDOM INQUIRY NUMBER
        Random random = new Random();
        int inquiryNumber = random.nextInt(1001);
        inquiryID = "Inq# " + inquiryNumber;
        inquiryInfoTV.setText("Inquiry Info  " + inquiryID);


        //INIT DEPARTMENT SPINNER
        ArrayAdapter departmentSpinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.departments, R.layout.spinner_layout);
        departmentSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        departmentSpinner.setAdapter(departmentSpinnerAdapter);
        projectTypeSpinner.setAdapter(departmentSpinnerAdapter);

        //INIT STATUS SPINNER
        ArrayAdapter statusSpinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.inquiry_status, R.layout.spinner_layout);
        statusSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        statusSpinner.setAdapter(statusSpinnerAdapter);

        //INIT AUTOTEXT FOR COMPANY, CONSUTANT, MAIN-CONTRACTOR & SUB-CONTRACTOR
        ArrayAdapter companyAutoTextAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.accounts_name, R.layout.autocompletetext_layout);
        companyAutoText.setAdapter(companyAutoTextAdapter);
        consultantACTV.setAdapter(companyAutoTextAdapter);
        mainContractorACTV.setAdapter(companyAutoTextAdapter);
        subContractorACTV.setAdapter(companyAutoTextAdapter);

        //INIT CONTACT AUTO TEXT
        ArrayAdapter contactAutoTextAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.contact_name, R.layout.autocompletetext_layout);
        contactPersonAutoText.setAdapter(contactAutoTextAdapter);

        //INIT ASSIGN AUTOTEXT
        ArrayAdapter assignAutoTextAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.contact_name, R.layout.autocompletetext_layout);
        assignAutoText.setAdapter(assignAutoTextAdapter);

        assignAutoText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addInquiry();
                    handled = true;
                }
                return handled;
            }
        });


        inquiryFlag = i.getStringExtra("InquiryFlag");
        if (inquiryFlag.equals(INQUIRY_EDIT)) {
            String inqID = i.getStringExtra("InquiryId");
            InquiryDatabaseAdapter databaseAdapter = new InquiryDatabaseAdapter(this);
            newInquiry = databaseAdapter.getInquiry(inqID);

            subjectEditText.setText(newInquiry.inquirySubject);
            inquiryID = newInquiry.inquiryID;
            inquiryCreateDate = newInquiry.inquiryCreateDate;
            inquiryCreateTime = newInquiry.inquiryCreateTime;
            inquiryInfoTV.setText(inquiryID);
            int spinnerPos = departmentSpinnerAdapter.getPosition(newInquiry.inquiryDepartment);
            departmentSpinner.setSelection(spinnerPos);
            int projectPos = departmentSpinnerAdapter.getPosition(newInquiry.projectType);
            projectTypeSpinner.setSelection(projectPos);
            descriptionEditText.setText(newInquiry.inquiryDescription);
            int statusSpinnerPos = statusSpinnerAdapter.getPosition(newInquiry.inquiryStatus);
            statusSpinner.setSelection(statusSpinnerPos);
            companyAutoText.setText(newInquiry.inquiryCompanyName);
            contactPersonAutoText.setText(newInquiry.inquiryContactPerson);
            contactPersonDesignationET.setText(newInquiry.contactPersonDesignation);
            String[] contactNo = newInquiry.inquiryContactNumber.split("-");
            contactNumberCodeEditText.setText(contactNo[0]);
            contactNumberEditText.setText(contactNo[1]);
            emailEditText.setText(newInquiry.inquiryEmailID);
            assignAutoText.setText(newInquiry.inquiryAssignTo);
            consultantACTV.setText(newInquiry.consultant);
            mainContractorACTV.setText(newInquiry.mainContractor);
            subContractorACTV.setText(newInquiry.subContractor);


        }

        //SETTING ADD NEW INQUIRY FUNCTION
        addInquiryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(BaseActivity.networkIsConnected) {
                    addInquiry();
                }else {
                    Toast.makeText(InquiryNew.this, "Network is not Connected. Save Offline", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void setInquiryToParcel() {

        newInquiry.setInquiryCreateDate(inquiryCreateDate);
        newInquiry.setInquiryLastEditDate(inquiryLastEditDate);
        newInquiry.setInquiryCreateTime(inquiryCreateTime);
        newInquiry.setInquiryLastEditTime(inquiryLastEditTime);
        newInquiry.setInquiryID(inquiryID);
        if (BaseActivity.ifEditTextNotEmptyErrMsg(companyAutoText)) {

            newInquiry.setInquiryCompanyName(companyAutoText.getText().toString());
        }
        newInquiry.setInquiryStatus(statusSpinner.getSelectedItem().toString());
        if (BaseActivity.ifEditTextNotEmptyErrMsg(assignAutoText)) {
            newInquiry.setInquiryAssignTo(assignAutoText.getText().toString());
        }
        if (BaseActivity.ifEditTextNotEmptyErrMsg(subjectEditText)) {
            newInquiry.setInquirySubject(subjectEditText.getText().toString());
        }
        if (BaseActivity.ifEditTextNotEmptyErrMsg(descriptionEditText)) {
            newInquiry.setInquiryDescription(descriptionEditText.getText().toString());
        }
        newInquiry.setInquiryDepartment(departmentSpinner.getSelectedItem().toString());
        newInquiry.setProjectType(projectTypeSpinner.getSelectedItem().toString());
        if (BaseActivity.ifEditTextNotEmptyErrMsg(emailEditText)) {
            newInquiry.setInquiryEmailID(emailEditText.getText().toString());
        }
        if (BaseActivity.ifEditTextNotEmptyErrMsg(contactNumberEditText)) {
            String contactNO, contactCode;
            contactNO = contactNumberEditText.getText().toString();
            if (TextUtils.isEmpty(contactNumberCodeEditText.getText())) {
                contactCode = contactNumberCodeEditText.getHint().toString();
            } else {
                contactCode = contactNumberCodeEditText.getText().toString();
            }
            contactNO = contactCode + "-" + contactNO;

            newInquiry.setInquiryContactNumber(contactNO);
        }
        if (BaseActivity.ifEditTextNotEmptyErrMsg(contactPersonAutoText)) {
            newInquiry.setInquiryContactPerson(contactPersonAutoText.getText().toString());
        }
        if (BaseActivity.ifEditTextNotEmptyErrMsg(contactPersonDesignationET)){
            newInquiry.setContactPersonDesignation(contactPersonDesignationET.getText().toString());
        }
        if(BaseActivity.ifEditTextNotEmptyErrMsg(consultantACTV)){
            newInquiry.setConsultant(consultantACTV.getText().toString());
        }
        if(BaseActivity.ifEditTextNotEmptyErrMsg(mainContractorACTV)){
            newInquiry.setMainContractor(mainContractorACTV.getText().toString());
        }
        if(BaseActivity.ifEditTextNotEmptyErrMsg(subContractorACTV)){
            newInquiry.setSubContractor(subContractorACTV.getText().toString());
        }

    }

    public void addInquiry() {

        sendNotification("Notification Will be enabled");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_inquiry_new, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_inquiryNew_save:
                InquiryDatabaseAdapter inquiryDatabaseAdapter = new InquiryDatabaseAdapter(getApplicationContext());

                long deleteResultKey = inquiryDatabaseAdapter.deleteInquiry(inquiryID);
                if (deleteResultKey < 0) {
                    Toast.makeText(this, "No value to delete", Toast.LENGTH_SHORT).show();
                } else {

                }
                setInquiryToParcel();
                long resultKey = inquiryDatabaseAdapter.saveInquiry(newInquiry);
                if (resultKey < 0) {
                    Toast.makeText(InquiryNew.this, "Error Saving Inquiry", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InquiryNew.this, "Inquiry Saved", Toast.LENGTH_SHORT).show();
                }
                setResult(RETURN_FALSE);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    //METHOD FOR PUSH NOTIFICATION
    public void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] pattern = {0,100,1000};
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(message)
                .setContentTitle("CubeME")
                .setColor(getResources().getColor(R.color.colorAccent))
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setSound(sound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noBuilder.build()); //0 = ID of notification
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
