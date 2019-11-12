package com.cube_me.cubeme_crm.Inquiry;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cube_me.cubeme_crm.Accounts.Accounts;
import com.cube_me.cubeme_crm.Accounts.AccountsWebService;
import com.cube_me.cubeme_crm.AddressObject;
import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.CRMWebServices;
import com.cube_me.cubeme_crm.Contacts.Contact;
import com.cube_me.cubeme_crm.Contacts.ContactWebService;
import com.cube_me.cubeme_crm.Email;
import com.cube_me.cubeme_crm.MainActivity;
import com.cube_me.cubeme_crm.MasterAppConstants;
import com.cube_me.cubeme_crm.MasterInquirySource;
import com.cube_me.cubeme_crm.MasterProjectType;
import com.cube_me.cubeme_crm.MasterSalesPerson;
import com.cube_me.cubeme_crm.NetworkChangeReceiver;
import com.cube_me.cubeme_crm.ObservableObject;
import com.cube_me.cubeme_crm.PhoneNumber;
import com.cube_me.cubeme_crm.ProjectDivision;
import com.cube_me.cubeme_crm.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class InquiryNew extends AppCompatActivity implements Observer{


    Context context;

    //VIEWS
    Toolbar toolbar;
    Spinner departmentSpinner;
    TextView inquiryStatusTV;
    TextView inquiryStagesTV;
    Spinner assignToSpinner;
    Spinner companySpinner;
    Spinner contactPersonSpinner;
    TextView contactPersonDesignationTV;
    EditText subjectEditText;
    EditText descriptionEditText;
    Spinner emailSpinner;
    Spinner contactNumberSpinner;
    Button addInquiryButton;
    TextView inquiryInfoTV;
    TextView companyNameTV;
    Spinner companyPhoneNoSpinner;
    Spinner companyEmailSpinner;
    Spinner companyAddressSpinner;
    TextView subjectTV;
    TextView descriptionTV;
    TextView divisionTV;
    TextView contactPersonTV;
    Spinner projectTypeSpinner;
    Spinner consultantSpinner;
    Spinner mainContractorSpinner;
    Spinner subContractorSpinner;
    EditText inquiryCommentsET;
    TextView companyEmailLabelTV;
    EditText clientRefNoET;


    String inquiryID;
    String inquiryCreateDate;
    String inquiryCreateTime;
    String inquiryLastEditDate;
    String inquiryLastEditTime;
    String fromActivityName;

    //SPINNER ADAPTERS
    final static String ADAPTER_SET = "AdapterSet";
    final static String ADAPTER_NOT_SET = "AdapterNotSet";
    ArrayAdapter departmentSpinnerAdapter;
    ArrayAdapter contactSpinnerAdapter;
    ArrayAdapter companySpinnerAdapter;
    ArrayAdapter assignSpinnerAdapter;
    ArrayAdapter contactPhoneNoAdapter;
    String contactPhoneNoSpinnerFLag;
    ArrayAdapter contactEmailAdapter;
    String contactEmailSpinnerFlag;
    ArrayAdapter companyPhoneNoAdapter;
    String companyPhoneNoSpinnerFlag;
    ArrayAdapter companyEmailAdapter;
    String companyEmailSpinnerAdapterFlag;
    ArrayAdapter companyAddressAdapter;
    String companyAddressSpinnerFlag;


    //WEBSERVICES
    CRMWebServices webServices;
    ContactWebService contactWebService;
    AccountsWebService accountsWebService;
    InquiryWebServices inquiryWebServices;

    MasterAppConstants appConstants;
    Accounts inquiryAccount;
    ProjectDivision projectDepartment;


    // VARIABLES FOR SETTING CONTACT SPINNER
    final static boolean SET_COMPANY_SPINNER = true;
    final static boolean DONOT_SET_COMPANY_SPINNER = false;


    Intent i;
    String inquiryFlag;
    String onlineFlag;
    public static final int RETURN_TRUE = 0;
    public static final int RETURN_FALSE = 1;
    public static final String INQUIRY_NEW = "NEW";
    public static final String INQUIRY_EDIT = "EDIT";
    public static final String INQUIRY_ONLINE = "ONLINE";
    public static final String INQUIRY_OFFLINE = "OFFLINE";
    Inquiry newInquiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inquiry_new);
        ObservableObject.getInstance().addObserver(this);//ADDING OBSERVER FOR LISTENING NETWORK CHANGE


        i = getIntent();
        inquiryFlag = i.getStringExtra("InquiryFlag");/* GETTING THE INQUIRY FLAG - THIS FLAG INDICATES IF THE INTENT CREATED IS FOR THE NEW
                                                        INQUIRY OR EDIT INQUIRY*/


        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
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
        if (am_pm == Calendar.AM) {
            AM_PM = "AM";
        }

        inquiryLastEditDate = date + "/" + month + "/" + year;
        inquiryCreateDate = inquiryLastEditDate;
        inquiryLastEditTime = hours + ":" + min + ":" + seconds + ":" + AM_PM;
        inquiryCreateTime = inquiryLastEditTime;


        //BASIC INIT
        context = InquiryNew.this;
        subjectEditText = (EditText) findViewById(R.id.inquiry_subjectEditText);
        newInquiry = new Inquiry();
        descriptionEditText = (EditText) findViewById(R.id.inquiry_descritptionEditText);
        emailSpinner = (Spinner) findViewById(R.id.inquiry_emailSpinner);
        contactNumberSpinner = (Spinner) findViewById(R.id.inquiry_contactNumberSpinner);
        departmentSpinner = (Spinner) findViewById(R.id.inquiry_deparmentSpinner);
        inquiryStatusTV = (TextView) findViewById(R.id.inquiry_statusTV);
        inquiryStagesTV = (TextView) findViewById(R.id.inquiry_stageTV);
        companySpinner = (Spinner) findViewById(R.id.inquiry_companySpinner);
        contactPersonSpinner = (Spinner) findViewById(R.id.inquiry_contactPersonSpinner);
        contactPersonDesignationTV = (TextView) findViewById(R.id.inquiry_contactPersonDesignationTextView);
        assignToSpinner = (Spinner) findViewById(R.id.inquiry_assignToSpinner);
        inquiryInfoTV = (TextView) findViewById(R.id.inquiry_info);
        projectTypeSpinner = (Spinner) findViewById(R.id.inquiry_jobsTypeSpinner);
        consultantSpinner = (Spinner) findViewById(R.id.inquiry_jobsConsultantSpinner);
        mainContractorSpinner = (Spinner) findViewById(R.id.inquiry_jobsContractorSpinner);
        subContractorSpinner = (Spinner) findViewById(R.id.inquiry_jobsSubContractorSpinner);
        addInquiryButton = (Button) findViewById(R.id.inquiry_newAddButton);
        subjectTV = (TextView) findViewById(R.id.inquiry_subjectTextView);
        descriptionTV = (TextView) findViewById(R.id.inquiry_descriptionTextView);
        divisionTV = (TextView) findViewById(R.id.inquiry_departmentTextView);
        contactPersonTV = (TextView) findViewById(R.id.inquiry_contactPersonTextView);
        companyNameTV = (TextView) findViewById(R.id.inquiry_companyTextView);
        companyPhoneNoSpinner = (Spinner) findViewById(R.id.inquiry_companyPhoneNumberSpinner);
        companyEmailSpinner = (Spinner) findViewById(R.id.inquiry_companyEmailSpinner);
        companyAddressSpinner = (Spinner) findViewById(R.id.inquiry_companyAddressSpinner);
        companyAddressSpinner.setAdapter(null);
        inquiryCommentsET = (EditText) findViewById(R.id.inquiry_commentsEditText);
        clientRefNoET = (EditText) findViewById(R.id.inquiry_referenceNoET);
        companyEmailLabelTV = (TextView) findViewById(R.id.inquiry_companyEmailLabelTV);




        /*SETTING SPINNER ADAPTER FLAGS TO NOT SET - THIS FLAG WILL BE USED TO CHECK IF THERE ARE VALUES IN THE SPINNER WHILE TRYING TO RETRIEVE
         VALUES FROM THE SPINNER
          */
        contactPhoneNoSpinnerFLag = ADAPTER_NOT_SET;
        contactEmailSpinnerFlag = ADAPTER_NOT_SET;
        companyPhoneNoSpinnerFlag = ADAPTER_NOT_SET;
        companyEmailSpinnerAdapterFlag = ADAPTER_NOT_SET;
        companyAddressSpinnerFlag = ADAPTER_NOT_SET;

        //SETTING UP THE * FOR MANDATORY LABELS
        subjectTV.setText(BaseActivity.buildString(subjectTV.getText().toString()));
        descriptionTV.setText(BaseActivity.buildString(descriptionTV.getText().toString()));
        divisionTV.setText(BaseActivity.buildString(divisionTV.getText().toString()));
        contactPersonTV.setText(BaseActivity.buildString(contactPersonTV.getText().toString()));
        companyNameTV.setText(BaseActivity.buildString(companyNameTV.getText().toString()));

        //CHECKING IF THE INTENT IS FOR NEW/ EDIT INQUIRY AND SETTING THE VALUES ACCORDINGLY
        if (inquiryFlag.equals(INQUIRY_EDIT)) {
            String inqID = i.getStringExtra("InquiryId");
            onlineFlag = i.getStringExtra("ONLINE/OFFLINE");
            if (onlineFlag.equals(INQUIRY_OFFLINE)) {
                InquiryDatabaseAdapter databaseAdapter = new InquiryDatabaseAdapter(this);
                newInquiry = databaseAdapter.getInquiry(inqID);
            } else if (onlineFlag.equals(INQUIRY_ONLINE)) {
                newInquiry = i.getParcelableExtra("Inquiry");
            }

            subjectEditText.setText(newInquiry.inquirySubject);
            inquiryID = newInquiry.inquiryID;
            inquiryCreateDate = newInquiry.inquiryCreateDate;
            inquiryCreateTime = newInquiry.inquiryCreateTime;
            inquiryInfoTV.setText(inquiryID);
            inquiryStatusTV.setText(newInquiry.inquiryStatus);
            inquiryStagesTV.setText(newInquiry.inquiryStage);
            if (!newInquiry.inquiryComments.equals("null")) {
                inquiryCommentsET.setText(newInquiry.inquiryComments);
            }
            if(!newInquiry.clientReferenceNo.equals("null")){
                clientRefNoET.setText(newInquiry.clientReferenceNo);
            }

//            companyEmailSpinner.setText(newInquiry.inquiryCompanyEmailID);
//            companyPhoneNoSpinner.setText(newInquiry.inquiryCompanyPhoneNumber);
//            emailSpinner.setText(newInquiry.inquiryContactEmailID);
//            contactNumberSpinner.setText(newInquiry.inquiryContactPhoneNumber);
            contactPersonDesignationTV.setText(newInquiry.contactPersonDesignation);

            descriptionEditText.setText(newInquiry.inquiryDescription);


        }

        //SETTING UP THE INQUIRY WEBSERVICES AND SETTING UP THE FUNCTION FOR CODE GENERATION
        inquiryWebServices = new InquiryWebServices(context);
        inquiryWebServices.setCallBack(new InquiryWebServices.InquiryWebServicesCommunicator() {
            @Override
            public void onSelectedInquiry(Inquiry inquiry) {

            }

            @Override
            public void onGeneratedSalesCode(String salesCode) {
                inquiryInfoTV.setText(salesCode);
                newInquiry.setInquiryID(salesCode);//SETTING THE INQUIRY CODE/NO
            }

            @Override
            public void onCreateInquiryResponse(String Message, boolean status) {
                Toast.makeText(context, Message, Toast.LENGTH_SHORT).show();
                onBackPressed();

            }
        });


        //SETTING UP THE VALUES IN FIELDS FROM SERVER
        webServices = new CRMWebServices(context);
        webServices.setCallBack(new CRMWebServices.MasterWebServiceCommunicator() {
            @Override
            public void setProjectType(final List<MasterProjectType> projectTypeList) {
                ArrayList<String> projectType = new ArrayList<String>();
                projectType.add("None");
                for (int i = 0; i < projectTypeList.size(); i++) {
                    MasterProjectType temp = projectTypeList.get(i);
                    projectType.add(temp.projectTypeName);
                }
                ArrayAdapter projectTypeSpinnerAdapter = new ArrayAdapter<>(context, R.layout.spinner_layout, projectType);
                projectTypeSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                projectTypeSpinner.setAdapter(projectTypeSpinnerAdapter);
                if (inquiryFlag.equals(INQUIRY_EDIT)) {

                    int projectPos = projectTypeSpinnerAdapter.getPosition(newInquiry.projectType);
                    projectTypeSpinner.setSelection(projectPos);
                }

                projectTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (!projectTypeSpinner.getSelectedItem().equals("None")) {
                            MasterProjectType temp = projectTypeList.get(i - 1);
                            newInquiry.setProjectType(temp.projectTypeName);
                            newInquiry.setProjectTypeID(temp.projectTypeID);//SETTING THE PROJECT TYPE ID
                        }else {
                            newInquiry.setProjectTypeID(-1); //RESETTING THE PROJECT TYPE ID TO NULL VALUE
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void setProjectDivision(final List<ProjectDivision> projectDivisionList) {
                Log.i("DepartmentList Size", String.valueOf(projectDivisionList.size()));
                ArrayList<String> divisionType = new ArrayList<String>();
                divisionType.add("None");
                for (int i = 0; i < projectDivisionList.size(); i++) {
                    ProjectDivision temp = projectDivisionList.get(i);
                    String divisionName = temp.divisionName + " - " + temp.divisionAbbr;
                    Log.i("Department Name", divisionName);
                    divisionType.add(divisionName);
                }
                //INIT DEPARTMENT SPINNER
                departmentSpinnerAdapter = new ArrayAdapter<>(context, R.layout.spinner_layout, divisionType);
                departmentSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                departmentSpinner.setAdapter(departmentSpinnerAdapter);
                if (inquiryFlag.equals(INQUIRY_EDIT)) {
                    int spinnerPos = departmentSpinnerAdapter.getPosition(newInquiry.inquiryDepartment + " - " + newInquiry.inquiryDepartmentCode);
                    departmentSpinner.setSelection(spinnerPos);
                }

                //SETTING UP THE LISTENER FOR THE DEPARTMENT ID
                departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (!departmentSpinner.getSelectedItem().equals("None")) {
                            projectDepartment = projectDivisionList.get(i - 1);
                            newInquiry.setInquiryDepartmentId(projectDepartment.divisionID);//MANDATORY FIELD - SETTING THE NEW INQUIRY DEPARTMENT ID
                            if (inquiryFlag.equals(INQUIRY_NEW)) {

                                if (!companySpinner.getSelectedItem().equals("None")) {
                                    inquiryWebServices.getSalesCode(projectDepartment.divisionID, appConstants.inquiryPrefix, inquiryAccount.accountName);
                                }
                            }

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void setInquirySource(List<MasterInquirySource> masterInquirySourceList) {

            }

            @Override
            public void setSalesPerson(final List<MasterSalesPerson> masterSalesPersonList) {
                ArrayList<String> salesPerson = new ArrayList<String>();
                for (int i = 0; i < masterSalesPersonList.size(); i++) {
                    MasterSalesPerson temp = masterSalesPersonList.get(i);
                    salesPerson.add(temp.salesPersonName);
                }
                //INIT ASSIGN TO SALESPERSON SPINNER
                assignSpinnerAdapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, salesPerson);
                assignSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                assignToSpinner.setAdapter(assignSpinnerAdapter);

                if (inquiryFlag.equals(INQUIRY_EDIT)) {
                    int curPosition = assignSpinnerAdapter.getPosition(newInquiry.salesPerson);
                    assignToSpinner.setSelection(curPosition);
                }

                assignToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        MasterSalesPerson masterSalesPerson = masterSalesPersonList.get(i);
                        newInquiry.setSalesPersonID(masterSalesPerson.salesPersonID);//SETTING THE SALES PERSON ID IN NEW INQUIRY
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void setAppConstants(MasterAppConstants masterAppConstants) {
                appConstants = masterAppConstants;
//                webServices.getAppConstants();
                webServices.getProjectType();
                webServices.getProjectDivision();
                webServices.getSalesPerson();
                accountsWebService.getBasicAccounts();
                contactWebService.getContacts();
            }
        });
        webServices.getAppConstants();
//        webServices.getProjectType();
//        webServices.getProjectDivision();
//        webServices.getSalesPerson();


        //WEBSERVICES FOR ACCOUNTS
        accountsWebService = new AccountsWebService(context);
        accountsWebService.setCallBack(new AccountsWebService.AccountsWebServiceCommunicator() {
            @Override
            public void onBasicAccountDetailsReceived(List<Accounts> accountsList) {
                setCompanySpinner(accountsList);
            }
        });

//        accountsWebService.getBasicAccounts();

        //WEB SERVICES FOR THE CONTACT DETAILS
        contactWebService = new ContactWebService(context);
        contactWebService.setCallBack(new ContactWebService.ContactsWebServiceInterface() {
            @Override
            public void returnAllContacts(List<Contact> contactList) {
                setContactPersonSpinner(contactList, DONOT_SET_COMPANY_SPINNER);
            }

            @Override
            public void onReturnCompanyContacts(List<Contact> contactList) {

                setContactPersonSpinner(contactList, SET_COMPANY_SPINNER);

            }

            @Override
            public void returnSpecificContact(Contact contact) {

            }
        });
//        contactWebService.getContacts();

        //SETTING ADD NEW INQUIRY FUNCTION
        addInquiryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BaseActivity.networkIsConnected) {
                    addInquiry();
                } else {
                    Toast.makeText(InquiryNew.this, "Network is not Connected. Save Offline", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void setContactPhoneNumberSpinner(List<PhoneNumber> phoneNumberList) {
        if(phoneNumberList.size()>0) {
            contactPhoneNoSpinnerFLag = ADAPTER_SET;
        }else {
            contactPhoneNoSpinnerFLag = ADAPTER_NOT_SET;
        }

        ArrayList<String> phoneNumber = new ArrayList<>();
        for (int i = 0; i < phoneNumberList.size(); i++) {
            PhoneNumber temp = phoneNumberList.get(i);
            phoneNumber.add(temp.accountPhoneNumber);
        }

        contactPhoneNoAdapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, phoneNumber);
        contactPhoneNoAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        contactNumberSpinner.setAdapter(contactPhoneNoAdapter);

        //CONTACT PHONE NUMBER WILL BE ASSIGNED IN sendInquiryToParcel()
    }

    public void setContactEmailSpinner(List<Email> emailList) {
        if(emailList.size()>0) {
            contactEmailSpinnerFlag = ADAPTER_SET;
        }else {
            contactEmailSpinnerFlag = ADAPTER_NOT_SET;
        }

        ArrayList<String> email = new ArrayList<>();
        for (int i = 0; i < emailList.size(); i++) {
            Email temp = emailList.get(i);
            email.add(temp.accountEmail);
        }

        contactEmailAdapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, email);
        contactEmailAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        emailSpinner.setAdapter(contactEmailAdapter);
        /*CONTACT EMAIL IS NOT ASSIGNED HERE THROUGH THE LISTENER. IT CAUSES USAGE OF RESOURCES. CONTACT EMAIL IS ADDED TO
        NEWINQUIRY IN SEND TO PARCEL FUNCTION */
    }

    public void setCompanySpinner(final List<Accounts> accountsList) {
        ArrayList<String> accountSpinnerList = new ArrayList<String>();
        accountSpinnerList.add("None");
        for (int i = 0; i < accountsList.size(); i++) {
            Accounts temp = accountsList.get(i);
            accountSpinnerList.add(temp.accountName);
        }

        companySpinnerAdapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, accountSpinnerList);
        companySpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        companySpinner.setAdapter(companySpinnerAdapter);
        consultantSpinner.setAdapter(companySpinnerAdapter);
        mainContractorSpinner.setAdapter(companySpinnerAdapter);
        subContractorSpinner.setAdapter(companySpinnerAdapter);

        if (inquiryFlag.equals(INQUIRY_EDIT)) {
            int spinnerPos = companySpinnerAdapter.getPosition(newInquiry.inquiryCompanyName);
            companySpinner.setSelection(spinnerPos);
            if(!newInquiry.consultant.equals("null")){
                int consultantSpinnerPosition = companySpinnerAdapter.getPosition(newInquiry.consultant);
                consultantSpinner.setSelection(consultantSpinnerPosition);
            }
            if(!newInquiry.subContractor.equals("null")){
                int subContractorSpinnerPos = companySpinnerAdapter.getPosition(newInquiry.subContractor);
                subContractorSpinner.setSelection(subContractorSpinnerPos);
            }
            if(!newInquiry.mainContractor.equals("null")){
                int mainContractorSpinnerPos = companySpinnerAdapter.getPosition(newInquiry.mainContractor);
                mainContractorSpinner.setSelection(mainContractorSpinnerPos);
            }
        }

        companySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!companySpinner.getSelectedItem().equals("None")) {
                    inquiryAccount = accountsList.get(i - 1);
                    String companyName = inquiryAccount.accountId + appConstants.stringSeparator + inquiryAccount.accountName + appConstants.stringSeparator
                            + inquiryAccount.accountAbbreviation;
                    Log.i("FormattedCompany Name", companyName);
                    newInquiry.setInquiryCompanyName(companyName);//MANDATORYFIELD - SETTING THE COMPANY NAME TO NEW INQUIRY

                    setCompanyEmailSpinner(inquiryAccount.emailList);
                    setCompanyNumberSpinner(inquiryAccount.phoneNumberList);
                    setCompanyAddressSpinner(inquiryAccount.addressObjectList);
                    contactWebService.getCompanyContactList(inquiryAccount.accountId);
                    if (inquiryFlag.equals(INQUIRY_NEW)) {
                        if (!departmentSpinner.getSelectedItem().equals("None")) {
                            inquiryWebServices.getSalesCode(projectDepartment.divisionID, appConstants.inquiryPrefix, inquiryAccount.accountName);
                        }
                    }


                } else {
                    setCompanyNumberSpinner(new ArrayList<PhoneNumber>());
                    setCompanyEmailSpinner(new ArrayList<Email>());
                    setCompanyAddressSpinner(new ArrayList<AddressObject>());
                    newInquiry.setInquiryCompanyName(null);
//                    companyPhoneNoAdapter.clear();
//                    companyEmailAdapter.clear();
                    contactWebService.getContacts();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void setCompanyNumberSpinner(List<PhoneNumber> phoneNumberList) {
        if(phoneNumberList.size()>0) {
            companyPhoneNoSpinnerFlag = ADAPTER_SET;
        }else {
            companyPhoneNoSpinnerFlag = ADAPTER_NOT_SET;
        }
        ArrayList<String> phoneNumber = new ArrayList<>();
        for (int i = 0; i < phoneNumberList.size(); i++) {
            PhoneNumber temp = phoneNumberList.get(i);
            phoneNumber.add(temp.accountPhoneNumber);
        }

        companyPhoneNoAdapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, phoneNumber);
        companyPhoneNoAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        companyPhoneNoSpinner.setAdapter(companyPhoneNoAdapter);

        //COMPANY PHONE NUMBER WILL BE ASSIGNED IN sendInquiryToParcel()
    }

    public void setCompanyAddressSpinner(List<AddressObject> addressObjectList) {
        if(addressObjectList.size()>0){
            companyAddressSpinnerFlag = ADAPTER_SET;
        }else {
            companyAddressSpinnerFlag = ADAPTER_NOT_SET;
        }

        ArrayList<String> address = new ArrayList<>();
        for (int i = 0; i < addressObjectList.size(); i++) {
            AddressObject temp = addressObjectList.get(i);
            address.add(temp.addressDetail);
        }

        companyAddressAdapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, address);
        companyAddressAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        companyAddressSpinner.setAdapter(companyAddressAdapter);

        //COMPANY ADDRESS WILL BE ASSIGNED IN sendInquiryToParcel()
    }

    public void setCompanyEmailSpinner(List<Email> emailList) {
        if(emailList.size()>0){
            companyEmailSpinnerAdapterFlag = ADAPTER_SET;
        }else {
            companyEmailSpinnerAdapterFlag = ADAPTER_NOT_SET;
        }

        ArrayList<String> email = new ArrayList<>();
        for (int i = 0; i < emailList.size(); i++) {
            Email temp = emailList.get(i);
            email.add(temp.accountEmail);
        }

        companyEmailAdapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, email);
        companyEmailAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        companyEmailSpinner.setAdapter(companyEmailAdapter);

        //COMPANY EMAIL WILL BE ASSIGNED IN sendInquiryToParcel()
    }


    public void setContactPersonSpinner(final List<Contact> contactList, boolean setContactsCompany) {
        final ArrayList<String> contactsList = new ArrayList<String>();
        contactsList.add("None");
        for (int i = 0; i < contactList.size(); i++) {
            Contact temp = contactList.get(i);
            String firstName = temp.contactName;
            String secondName = temp.contactSecondName;


            contactsList.add(getFormattedName(firstName, secondName));
        }
        contactSpinnerAdapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, contactsList);
        contactSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        contactPersonSpinner.setAdapter(contactSpinnerAdapter);

        if (inquiryFlag.equals(INQUIRY_EDIT) && (setContactsCompany == SET_COMPANY_SPINNER)) {
            int currentPosition = contactSpinnerAdapter.getPosition(newInquiry.inquiryContactPerson);
            contactPersonSpinner.setSelection(currentPosition);
        }

        contactPersonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //SETTING THE COMPANY SPINNER POSITION TO THE SELECTED CONTACT PERSON'S COMPANY
                if (!contactPersonSpinner.getSelectedItem().equals("None")) {
                    Contact temp = contactList.get(i - 1);
                    String contactPerson = temp.contactID + appConstants.stringSeparator + getFormattedName(temp.contactName, temp.contactSecondName);
                    Log.i("ContactPerson Formatted", contactPerson);
                    newInquiry.setInquiryContactPerson(contactPerson);//MANDATORY FIELD - SETTING THE CONTACT PERSON IN NEW INQUIRY
                    if (!temp.contactDesignation.equals("null")) {
                        contactPersonDesignationTV.setText(temp.contactDesignation);
                        newInquiry.setContactPersonDesignation(temp.contactDesignation);/*NON MANDATORY FIELD - SETTING THE DESIGNATION IN
                                                                                            NEW INQUIRY*/
                    }
                    setContactPhoneNumberSpinner(temp.phoneNumberList);
                    setContactEmailSpinner(temp.emailList);
                    int spinnerPos = companySpinnerAdapter.getPosition(temp.companyName);
                    companySpinner.setSelection(spinnerPos);
                } else {
                    contactPersonDesignationTV.setText("");
                    newInquiry.setInquiryContactPerson("null");
                    newInquiry.setContactPersonDesignation("null");
                    setContactPhoneNumberSpinner(new ArrayList<PhoneNumber>());
                    setContactEmailSpinner(new ArrayList<Email>());

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public String getFormattedName(String firstName, String secondName) {
        String name = "";
        if (!firstName.equals("null") && !secondName.equals("null")) {
            name = firstName + " " + secondName;
        } else if (!firstName.equals("null") && secondName.equals("null")) {
            name = firstName;
        } else if (firstName.equals("null") && !secondName.equals("null")) {
            name = secondName;
        }
        return name;
    }

    public void setInquiryToParcel() {



        int mandatoryCount = 0; // MANDATORY COUNT SHOULD BE EQUAL TO 7
        newInquiry.setInquiryCreateDate(inquiryCreateDate);
        newInquiry.setInquiryLastEditDate(inquiryLastEditDate);
        newInquiry.setInquiryCreateTime(inquiryCreateTime);
        newInquiry.setInquiryLastEditTime(inquiryLastEditTime);

        //SETTING THE MANDATORY FIELDS
        if (BaseActivity.ifEditTextNotEmptyErrMsg(subjectEditText)) {
            newInquiry.setInquirySubject(subjectEditText.getText().toString());
            mandatoryCount++;
        }

        if(BaseActivity.ifEditTextNotEmptyErrMsg(descriptionEditText)){
            newInquiry.setInquiryDescription(descriptionEditText.getText().toString());
            mandatoryCount++;
        }

        if(!departmentSpinner.getSelectedItem().toString().equals("None")){
            mandatoryCount++;
//            newInquiry.setInquiryDepartment(departmentSpinner.getSelectedItem().toString());
        }
        if(!companySpinner.getSelectedItem().toString().equals("None")){
            mandatoryCount++;
//            newInquiry.setInquiryCompanyName(companySpinner.getSelectedItem().toString());
        }
        if(!contactPersonSpinner.getSelectedItem().toString().equals("None")){
            mandatoryCount++;
//            newInquiry.setInquiryContactPerson(contactPersonSpinner.getSelectedItem().toString());
        }

        if(companyAddressSpinnerFlag.equals(ADAPTER_SET)){
            mandatoryCount++;
            newInquiry.setInquiryCompanyAddress(companyAddressSpinner.getSelectedItem().toString());
        }

        if (companyPhoneNoSpinnerFlag.equals(ADAPTER_SET)){
            mandatoryCount++;
            newInquiry.setInquiryCompanyPhoneNumber(companyPhoneNoSpinner.getSelectedItem().toString());
        }


        //SETTING THE NON MANDATORY FIELDS

        if(BaseActivity.ifEditTextNotEmpty(inquiryCommentsET)){
            newInquiry.setInquiryComments(inquiryCommentsET.getText().toString());
        }else {
            newInquiry.setInquiryComments("null");
        }

        if(BaseActivity.ifEditTextNotEmpty(clientRefNoET)){
            newInquiry.setClientReferenceNo(clientRefNoET.getText().toString());
        }else {
            newInquiry.setClientReferenceNo("null");
        }


        if(contactEmailSpinnerFlag.equals(ADAPTER_SET)){
            newInquiry.setInquiryContactEmailID(companyEmailSpinner.getSelectedItem().toString());
        }else {
            newInquiry.setInquiryContactEmailID("null");
        }
        if(contactPhoneNoSpinnerFLag.equals(ADAPTER_SET)){
            newInquiry.setInquiryContactPhoneNumber(contactNumberSpinner.getSelectedItem().toString());
        }else {
            newInquiry.setInquiryContactPhoneNumber("null");
        }

        if(companyEmailSpinnerAdapterFlag.equals(ADAPTER_SET)){
            newInquiry.setInquiryCompanyEmailID(companyEmailSpinner.getSelectedItem().toString());
        }else {
            newInquiry.setInquiryCompanyEmailID("null");
        }

        if(!consultantSpinner.getSelectedItem().toString().equals("None")){
            newInquiry.setConsultant(consultantSpinner.getSelectedItem().toString());
        }else {
            newInquiry.setConsultant("null");
        }

        if(!subContractorSpinner.getSelectedItem().toString().equals("None")){
            newInquiry.setSubContractor(subContractorSpinner.getSelectedItem().toString());
        }else {
            newInquiry.setSubContractor("null");
        }
        if(!mainContractorSpinner.getSelectedItem().toString().equals("None")){
            newInquiry.setMainContractor(mainContractorSpinner.getSelectedItem().toString());
            Log.i("MainContractor", newInquiry.mainContractor);
        }else {
            newInquiry.setMainContractor("null");
        }

        if(mandatoryCount == 7) {
            if (inquiryFlag.equals(INQUIRY_EDIT)) {
                inquiryWebServices.postInquiryLive(newInquiry, inquiryWebServices.UPDATE);
            } else {
                inquiryWebServices.postInquiryLive(newInquiry, inquiryWebServices.POST);
            }
        }else {
            Toast.makeText(context, "Please Fill All the Mandatory Fields", Toast.LENGTH_SHORT).show();
        }





//        if (BaseActivity.ifEditTextNotEmptyErrMsg(companySpinner)) {
//
//            newInquiry.setInquiryCompanyName(companySpinner.getText().toString());
//        }
//        newInquiry.setInquiryStatus(inquiryStatusTV.getSelectedItem().toString());
//        if (BaseActivity.ifEditTextNotEmptyErrMsg(assignToSpinner)) {
//            newInquiry.setSalesPerson(assignToSpinner.getText().toString());
//        }
//        if (BaseActivity.ifEditTextNotEmptyErrMsg(subjectEditText)) {
//            newInquiry.setInquirySubject(subjectEditText.getText().toString());
//        }
//        if (BaseActivity.ifEditTextNotEmptyErrMsg(descriptionEditText)) {
//            newInquiry.setInquiryDescription(descriptionEditText.getText().toString());
//        }
//        newInquiry.setInquiryDepartment(departmentSpinner.getSelectedItem().toString());
//        newInquiry.setProjectType(projectTypeSpinner.getSelectedItem().toString());
//        if (BaseActivity.ifEditTextNotEmptyErrMsg(emailSpinner)) {
//            newInquiry.setInquiryCompanyEmailID(emailSpinner.getText().toString());
//        }
//        newInquiry.setInquiryCompanyPhoneNumber(contactNumberSpinner.getText().toString());
//        if (BaseActivity.ifEditTextNotEmptyErrMsg(contactPersonSpinner)) {
//            newInquiry.setInquiryContactPerson(contactPersonSpinner.getText().toString());
//        }
//        newInquiry.setContactPersonDesignation(contactPersonDesignationTV.getText().toString());
//
//        if (BaseActivity.ifEditTextNotEmptyErrMsg(consultantSpinner)) {
//            newInquiry.setConsultant(consultantSpinner.getText().toString());
//        }
//        if (BaseActivity.ifEditTextNotEmptyErrMsg(mainContractorSpinner)) {
//            newInquiry.setMainContractor(mainContractorSpinner.getText().toString());
//        }
//        if (BaseActivity.ifEditTextNotEmptyErrMsg(subContractorSpinner)) {
//            newInquiry.setSubContractor(subContractorSpinner.getText().toString());
//        }

    }

    public void addInquiry() {

        Log.i("Inquiry Create", "Section Reached");
        setInquiryToParcel();
//        sendNotification("Notification Will be enabled");


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
        long[] pattern = {0, 100, 1000};
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(message)
                .setContentTitle("CubeME")
                .setColor(getResources().getColor(R.color.colorAccent))
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setSound(sound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noBuilder.build()); //0 = ID of notification
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }


    @Override
    public void update(Observable observable, Object o) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            BaseActivity.networkIsConnected = true;
//            networkChangeCommunicator.onNetworkConnected();
            addInquiryButton.setVisibility(View.VISIBLE);
            Toast.makeText(context, "NetWork is Connected", Toast.LENGTH_SHORT).show();
        }
        else {
            BaseActivity.networkIsConnected = false;
//            networkChangeCommunicator.onNetworkDisconnected();
            addInquiryButton.setVisibility(View.GONE);
            Toast.makeText(context, "Network Unavailable. Switching to Offline Mode", Toast.LENGTH_SHORT).show();
        }

    }
}


//            int statusSpinnerPos = statusSpinnerAdapter.getPosition(newInquiry.inquiryStatus);
//            inquiryStatusTV.setSelection(statusSpinnerPos);
//            companySpinner.setText(newInquiry.inquiryCompanyName);
//            contactPersonSpinner.setText(newInquiry.inquiryContactPerson);
//            if (newInquiry.contactPersonDesignation != null) {
//                contactPersonDesignationTV.setText(newInquiry.contactPersonDesignation);
//            }
//            contactNumberSpinner.setText(newInquiry.inquiryCompanyPhoneNumber);
//            emailSpinner.setText(newInquiry.inquiryCompanyEmailID);
//            assignToSpinner.setText(newInquiry.salesPerson);
//            consultantSpinner.setText(newInquiry.consultant);
//            mainContractorSpinner.setText(newInquiry.mainContractor);
//            subContractorSpinner.setText(newInquiry.subContractor);


//SETTING UP THE FUNCTIONALITY'S BASED ON THE NETWORK STATE
//        if(!BaseActivity.networkIsConnected){
//            addInquiryButton.setEnabled(false);
//        }


//GENERATING RANDOM INQUIRY NUMBER
//        Random random = new Random();
//        int inquiryNumber = random.nextInt(1001);
//        inquiryID = "Inq# " + inquiryNumber;
//        inquiryInfoTV.setText("Inquiry Info  " + inquiryID);


//        //INIT STATUS SPINNER
//        ArrayAdapter statusSpinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.inquiry_status, R.layout.spinner_layout);
//        statusSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
//        inquiryStatusTV.setAdapter(statusSpinnerAdapter);

//INIT AUTOTEXT FOR COMPANY, CONSUTANT, MAIN-CONTRACTOR & SUB-CONTRACTOR
//        ArrayAdapter companySpinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.accounts_name, R.layout.autocompletetext_layout);
//        companySpinner.setAdapter(companySpinnerAdapter);
//        consultantSpinner.setAdapter(companySpinnerAdapter);
//        mainContractorSpinner.setAdapter(companySpinnerAdapter);
//        subContractorSpinner.setAdapter(companySpinnerAdapter);
//        //INIT CONTACT AUTO TEXT
//        ArrayAdapter contactAutoTextAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.contact_name, R.layout.autocompletetext_layout);
//        contactPersonSpinner.setAdapter(contactAutoTextAdapter);
//
//        //INIT ASSIGN AUTOTEXT
//        ArrayAdapter assignAutoTextAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.contact_name, R.layout.autocompletetext_layout);
//        assignToSpinner.setAdapter(assignAutoTextAdapter);

//        assignToSpinner.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//                boolean handled = false;
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    addInquiry();
//                    handled = true;
//                }
//                return handled;
//            }
//        });