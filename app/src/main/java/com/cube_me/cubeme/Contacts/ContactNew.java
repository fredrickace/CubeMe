package com.cube_me.cubeme.Contacts;

import android.content.Intent;
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

import static com.cube_me.cubeme.BaseActivity.buildString;

public class ContactNew extends AppCompatActivity {

    Toolbar toolBar;
    AutoCompleteTextView contactAccountName;
    Spinner contactTypeSpinner;
    Button addContactButton;
    EditText contactNameEditText;
    EditText emailEditText;
    EditText contactEmailEditText;
    TextView contactFirstNameTV;
    TextView contactTypeContactTV;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_new_activity);

        i = getIntent();
//        Initialize Toolbar
        toolBar = (Toolbar)findViewById(R.id.app_toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Contact");

        initContactNameAutoFill();
        initContactTypeSpinner();

//      Setting FirstName TextView as mandatory with *
        contactFirstNameTV = (TextView)findViewById(R.id.contact_firstNameTextView);
        String firstName = "First Name ";
        contactFirstNameTV.setText(buildString(firstName));

//      Setting ContactType TextView Mandatory with *
        contactTypeContactTV = (TextView) findViewById(R.id.contact_contactTypeTextView);
        String typeContact = "Type of Contact";
        contactTypeContactTV.setText(buildString(typeContact));


        contactNameEditText = (EditText) findViewById(R.id.contact_firstNameEditText);
        emailEditText = (EditText) findViewById(R.id.contact_emailEditText);
        addContactButton = (Button) findViewById(R.id.contact_addbutton);
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            addNewContact();
            }
        });

//      Setting KeyBoard ActionListener
        emailEditText = (EditText) findViewById(R.id.contact_emailEditText);
        emailEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled =  false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                   addNewContact();
                    handled = true;
                }
                return handled;
            }
        });

    }



    public void addNewContact(){

        Contact newContact = new Contact();
        newContact.setName(contactNameEditText.getText().toString());
        newContact.setEmail(emailEditText.getText().toString());
        newContact.setCompanyName(contactAccountName.getText().toString());
        i.putExtra("NewContact",newContact);
        setResult(ContactsFragment.RETURN_TRUE,i);
        finish();
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
    public void onBackPressed() {

        setResult(ContactsFragment.RETURN_FALSE);
        finish();
        super.onBackPressed();

    }

    private void initContactNameAutoFill(){

        contactAccountName = (AutoCompleteTextView) findViewById(R.id.contact_companyNameAutoComplete);
//        String[] accountName = getResources().getStringArray(R.array.accounts_name);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.accounts_name,R.layout.autocompletetext_layout);
//        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        contactAccountName.setAdapter(adapter);
    }
    private void initContactTypeSpinner(){

        contactTypeSpinner = (Spinner) findViewById(R.id.contact_contactTypeSpinner);
//        String[] typeContact = getResources().getStringArray(R.array.contact_type);
//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplication(),android.R.layout.simple_spinner_dropdown_item,typeContact);
        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.contact_type,R.layout.spinner_layout);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        contactTypeSpinner.setAdapter(spinnerAdapter);
    }
}
