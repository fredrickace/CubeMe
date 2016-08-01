package com.cube_me.cubeme.Inquiry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cube_me.cubeme.R;

import java.util.Random;

public class InquiryNew extends AppCompatActivity {

    Toolbar toolbar;
    Spinner departmentSpinner;
    Spinner statusSpinner;
    AutoCompleteTextView assignAutoText;
    AutoCompleteTextView companyAutoText;
    AutoCompleteTextView contactAutoText;
    Button addInquiryButton;
    TextView inquiryInfoTV;
    String inquiryNumberText;
    EditText subjectEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inquiry_new);

        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Inquiry");

//        Basic Init
        subjectEditText = (EditText) findViewById(R.id.inquiry_subjectEditText);

//        Generating Random Inquiry Number
        Random random = new Random();
        int inquiryNumber = random.nextInt(1001);
        inquiryNumberText = "#Inq "+ inquiryNumber ;
        inquiryInfoTV = (TextView) findViewById(R.id.inquiry_info);
        inquiryInfoTV.setText("Inquiry Info  " + inquiryNumberText);



//        Init Department spinner
        departmentSpinner = (Spinner) findViewById(R.id.inquiry_deparmentSpinner);
        ArrayAdapter departmentSpinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.departments,R.layout.spinner_layout);
        departmentSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        departmentSpinner.setAdapter(departmentSpinnerAdapter);

//        Init Status Spinner

        statusSpinner = (Spinner) findViewById(R.id.inquiry_statusSpinner);
        ArrayAdapter statusSpinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.inquiry_status,R.layout.spinner_layout);
        statusSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        statusSpinner.setAdapter(statusSpinnerAdapter);

//      Init Company AutoText

        companyAutoText = (AutoCompleteTextView) findViewById(R.id.inquiry_companyAutoComplete);
        ArrayAdapter companyAutoTextAdapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.accounts_name, R.layout.autocompletetext_layout);
        companyAutoText.setAdapter(companyAutoTextAdapter);

//        Init Contact Auto Text
        contactAutoText = (AutoCompleteTextView) findViewById(R.id.inquiry_contactPersonAutoComplete);
        ArrayAdapter contactAutoTextAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.contact_name,R.layout.autocompletetext_layout);
        contactAutoText.setAdapter(contactAutoTextAdapter);


//        Init Assign AutoText

        assignAutoText = (AutoCompleteTextView)findViewById(R.id.inquiry_assignAutoText);
        ArrayAdapter assignAutoTextAdapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.contact_name,R.layout.autocompletetext_layout);
        assignAutoText.setAdapter(assignAutoTextAdapter);

        assignAutoText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    addInquiry();
                    handled = true;
                }
                return handled;
            }
        });

//        Init Add New Button
        addInquiryButton = (Button) findViewById(R.id.inquiry_newAddButton);
        addInquiryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInquiry();
            }
        });

    }

    public void addInquiry(){

        Inquiry newInquiry = new Inquiry();
        newInquiry.setInquiryNumber(inquiryNumberText);
        newInquiry.setInquiryCompanyName(companyAutoText.getText().toString());
        newInquiry.setInquiryStatus(statusSpinner.getSelectedItem().toString());
        newInquiry.setInquiryAssignTo(assignAutoText.getText().toString());
        newInquiry.setInquirySubject(subjectEditText.getText().toString());
        InquiryRecyclerAdapter.addNewInquiry(newInquiry);
        onBackPressed();
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
}
