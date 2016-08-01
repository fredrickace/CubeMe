package com.cube_me.cubeme.Accounts;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cube_me.cubeme.R;

import static com.cube_me.cubeme.BaseActivity.buildString;

public class AccountsNew extends AppCompatActivity {

    Spinner contactSpinner;
    Button accountNewButton;
    EditText companyNameEditText;
    AutoCompleteTextView parentCompanyAutoCompleteTV;
    AutoCompleteTextView contactPersonAutoCompleteTV;
    TextView companyNameTV;
    TextView contactPersonTV;
    EditText contactWebEditText;
    ImageButton accountAddImageButton;
    LinearLayout newPhoneLinearLayout;
    LinearLayout accountShippingAddressLinearLayout;
    CheckBox shippingAddressCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accounts_new);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Add Account");

//        Basic Init
        accountShippingAddressLinearLayout = (LinearLayout) findViewById(R.id.account_shippingAddressLayout);
//        Setting Up CheckBox for Shipping address automation

        shippingAddressCheckBox = (CheckBox) findViewById(R.id.account_shippingAddressCheckBox);
        shippingAddressCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    accountShippingAddressLinearLayout.setVisibility(View.GONE);

                }
                else {
                    accountShippingAddressLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
//        initContactSpinner();

        initParentCompanyAutoComplete();
        initContactPersonAutoComplete();

        companyNameEditText = (EditText) findViewById(R.id.account_companyNameEditText);

//        Setting Company Name Mandatory with *
        companyNameTV = (TextView)findViewById(R.id.account_companyNameTextView);
        companyNameTV.setText(buildString(companyNameTV.getText().toString()));

//        Setting Company Contact Person Mandatory with *
        contactPersonTV = (TextView)findViewById(R.id.account_contactPersonTextView);
        contactPersonTV.setText(buildString(contactPersonTV.getText().toString()));


        accountNewButton = (Button) findViewById(R.id.account_newAddButton);
        accountNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addNewAccount();

            }
        });

//        Setting the keyboard Event Listener on keyBoard
        contactWebEditText = (EditText)findViewById(R.id.account_webEditText);
        contactWebEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addNewAccount();
                    handled = true;
                }
                return handled;
            }

        });

//        Setting Up the dynamic UI addition of new Phone number layout
        newPhoneLinearLayout = (LinearLayout)findViewById(R.id.phoneRowContainer);
        accountAddImageButton = (ImageButton) findViewById(R.id.Account_addImageButton);
        accountAddImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View newView = layoutInflater.inflate(R.layout.account_phoneno_row,null);
                EditText newContactTV = (EditText) newView.findViewById(R.id.account_newContactNoTextView);
                EditText newCountryCode = (EditText)newView.findViewById(R.id.account_newContactCountryCodeEditText);
                newCountryCode.setHint("+974");
                newCountryCode.setMaxWidth(4);
                EditText newPhoneNo = (EditText) newView.findViewById(R.id.account_newContactPhoneEditText);
                newPhoneLinearLayout.addView(newView);

            }
        });

    }

    private void addNewAccount(){
        Accounts newAccount = new Accounts(companyNameEditText.getText().toString(),contactPersonAutoCompleteTV.getText().toString());
        AccountsRecyclerAdapter.addNewAccounts(newAccount);
        onBackPressed();
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
    public void initParentCompanyAutoComplete(){

        parentCompanyAutoCompleteTV = (AutoCompleteTextView) findViewById(R.id.account_parentCompanyAutoComplete);
        ArrayAdapter parentCompanyAdapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.accounts_name,R.layout.autocompletetext_layout);
        parentCompanyAutoCompleteTV.setAdapter(parentCompanyAdapter);
    }
    public void initContactPersonAutoComplete(){

        contactPersonAutoCompleteTV = (AutoCompleteTextView)findViewById(R.id.account_contactPersonAutoComplete);
        ArrayAdapter  contactPersonAdapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.contact_name,R.layout.autocompletetext_layout);
        contactPersonAutoCompleteTV.setAdapter(contactPersonAdapter);
    }



//    public void initContactSpinner(){
//
//        contactSpinner = (Spinner) findViewById(R.id.spinner_contactPerson);
//
//        String[] items = getResources().getStringArray(R.array.contact_spinner);
//
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.contact_spinner,R.layout.spinner_layout);
//        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
//        contactSpinner.setAdapter(adapter);
//
////        contactSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////            @Override
////            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                Toast.makeText(AccountsNew.this, "Spinner OKAY", Toast.LENGTH_SHORT).show();
////            }
////
////            @Override
////            public void onNothingSelected(AdapterView<?> parent) {
////                Toast.makeText(AccountsNew.this, "You didn't Select Any :)", Toast.LENGTH_SHORT).show();
////            }
////        });
//
//    }
}
