package com.cube_me.cubeme.Estimation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cube_me.cubeme.BaseActivity;
import com.cube_me.cubeme.R;

public class EstimationOtherMaterials extends AppCompatActivity implements Parcelable {

    Toolbar toolbar;
    String otherMaterialName;
    float otherMaterialDayHour;
    float otherMaterialUnit;
    float otherMaterialPricePerUnit;
    float otherMaterialBudgetPerUnit;
    String otherMaterialUOM;
    float otherMaterialTotal;
    String jobName;

    Intent i;

    EditText otherMaterialNameEditText;
    EditText otherMaterialDayHourEditText;
    TextView otherMaterialDayHourTV;
    EditText otherMaterialHoursEditText;
    Spinner otherMaterialUOMSpinner;
    TextView otherMaterialTotalTV;
    Button otherMaterialAddButton;
    TextView otherMaterialHoursTV;
    EditText otherMaterialPricePerUnitEditText;
    TextView otherMaterialPricePerUnitTV;
    TextView otherMaterialBudgetPerUnitTV;
    EditText otherMaterialBudgetPerUnitEditText;
    Spinner otherMaterialJobNameSpinner;

    EstimationOtherMaterials estimationOtherMaterials;

    ArrayAdapter otherMaterialAA;

    //SETTERS

    public void setOtherMaterialBudgetPerUnit(float otherMaterialBudgetPerUnit) {
        this.otherMaterialBudgetPerUnit = otherMaterialBudgetPerUnit;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setOtherMaterialName(String otherMaterialName) {
        this.otherMaterialName = otherMaterialName;
    }

    public void setOtherMaterialDayHour(float otherMaterialDayHour) {
        this.otherMaterialDayHour = otherMaterialDayHour;
    }

    public void setOtherMaterialUnit(float otherMaterialUnit) {
        this.otherMaterialUnit = otherMaterialUnit;
    }

    public void setOtherMaterialPricePerUnit(float otherMaterialPricePerUnit) {
        this.otherMaterialPricePerUnit = otherMaterialPricePerUnit;
    }

    public void setOtherMaterialUOM(String otherMaterialUOM) {
        this.otherMaterialUOM = otherMaterialUOM;
    }

    public void setOtherMaterialTotal(float otherMaterialTotal) {
        this.otherMaterialTotal = otherMaterialTotal;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estimation_othermaterials_add);


        i = getIntent();
        String title = i.getStringExtra("Title");

        //INIT TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(title);

        //BASIC INIT
        otherMaterialNameEditText = (EditText) findViewById(R.id.otherMaterialNew_nameEditText);
        otherMaterialDayHourEditText = (EditText) findViewById(R.id.otherMaterialNew_dayHourEditText);
        otherMaterialDayHourTV = (TextView) findViewById(R.id.otherMaterialNew_dayHourTV);
        otherMaterialHoursEditText = (EditText) findViewById(R.id.otherMaterialNew_noOfResourcesET);
        otherMaterialPricePerUnitEditText = (EditText) findViewById(R.id.otherMaterialNew_priceHourEditText);
        otherMaterialTotalTV = (TextView) findViewById(R.id.otherMaterialNew_totalTV);
        otherMaterialHoursTV = (TextView) findViewById(R.id.otherMaterialNew_noOfResourcesTV);
        otherMaterialPricePerUnitTV = (TextView) findViewById(R.id.otherMaterialNew_priceHourTV);
        otherMaterialBudgetPerUnitTV = (TextView) findViewById(R.id.otherMaterialNew_BudgetHourTV);
        otherMaterialBudgetPerUnitEditText = (EditText) findViewById(R.id.otherMaterialNew_BudgetEditText);
        otherMaterialJobNameSpinner = (Spinner) findViewById(R.id.otherMaterialNew_jobSpinner);
        otherMaterialJobNameSpinner.setAdapter(Estimation.jobNameAdapter);
        otherMaterialUOMSpinner = (Spinner) findViewById(R.id.otherMaterialNew_UOMSpinner);
        otherMaterialAA = ArrayAdapter.createFromResource(this,R.array.dayHour,R.layout.spinner_layout);
        otherMaterialAA.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        otherMaterialUOMSpinner.setAdapter(otherMaterialAA);
        //SETTING UP TEXTVIEW FIELDS IF THE INTENT CALL IS FROM TRANSPORT
        if(i.getStringExtra("ID").equals("NewTransport") || (i.getStringExtra("ID").equals("EditTransport"))){
            otherMaterialAA = ArrayAdapter.createFromResource(this,R.array.dayTrip,R.layout.spinner_layout);
            otherMaterialAA.setDropDownViewResource(R.layout.spinner_dropdown_layout);
            otherMaterialUOMSpinner.setAdapter(otherMaterialAA);
        }
        otherMaterialUOMSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String temp = otherMaterialUOMSpinner.getSelectedItem().toString();
//                switch (temp){
//                    case "Days":
                        otherMaterialDayHourTV.setText(otherMaterialUOMSpinner.getSelectedItem().toString());
                        otherMaterialPricePerUnitTV.setText("Price/"+otherMaterialUOMSpinner.getSelectedItem().toString());
                        otherMaterialBudgetPerUnitTV.setText("Budget/"+otherMaterialUOMSpinner.getSelectedItem().toString());

//                        break;
//                    case "Hours":
//                        otherMaterialDayHourTV.setText("Hours");
//                        otherMaterialPricePerUnitTV.setText("Price/Hour");
//                        break;
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        //SETTING UP TEXTVIEW FIELDS IF THE INTENT CALL IS FROM TRANSPORT
        if(i.getStringExtra("ID").equals("NewTransport") || (i.getStringExtra("ID").equals("EditTransport"))){
            setTransportTV();
        }
        //SETTING UP FIELDS IF THE INTENT CALL IS FROM EDIT
        if (i.getStringExtra("ID").equals("EditManPower") || i.getStringExtra("ID").equals("EditTools")
                || i.getStringExtra("ID").equals("EditTransport")) {
            estimationOtherMaterials = i.getParcelableExtra("ParcelEdit");
            setFields(estimationOtherMaterials);

        }


       // KEYBOARD EVENT LISTENER CALCULATING THE TOTAL

        otherMaterialDayHourEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(otherMaterialDayHourEditText.getText())) {
                    otherMaterialTotalTV.setText("0");
                } else if (!TextUtils.isEmpty(otherMaterialHoursEditText.getText()) && !TextUtils.isEmpty(otherMaterialPricePerUnitEditText.getText())) {

                    otherMaterialDayHour = Float.valueOf(otherMaterialDayHourEditText.getText().toString());
                    otherMaterialUnit = Float.valueOf(otherMaterialHoursEditText.getText().toString());
                    otherMaterialPricePerUnit = Float.valueOf(otherMaterialPricePerUnitEditText.getText().toString());
                    otherMaterialTotalTV.setText(String.valueOf(otherMaterialDayHour * otherMaterialUnit * otherMaterialPricePerUnit));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        otherMaterialHoursEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(otherMaterialHoursEditText.getText())) {
                    otherMaterialTotalTV.setText("0");
                } else if (!TextUtils.isEmpty(otherMaterialDayHourEditText.getText()) && !TextUtils.isEmpty(otherMaterialPricePerUnitEditText.getText())) {

                    otherMaterialDayHour = Float.valueOf(otherMaterialDayHourEditText.getText().toString());
                    otherMaterialUnit = Float.valueOf(otherMaterialHoursEditText.getText().toString());
                    otherMaterialPricePerUnit = Float.valueOf(otherMaterialPricePerUnitEditText.getText().toString());
                    otherMaterialTotalTV.setText(String.valueOf(otherMaterialDayHour * otherMaterialUnit * otherMaterialPricePerUnit));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        otherMaterialPricePerUnitEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(otherMaterialPricePerUnitEditText.getText())) {
                    otherMaterialTotalTV.setText("0");
                } else if (!TextUtils.isEmpty(otherMaterialHoursEditText.getText()) && !TextUtils.isEmpty(otherMaterialDayHourEditText.getText())) {

                    otherMaterialDayHour = Float.valueOf(otherMaterialDayHourEditText.getText().toString());
                    otherMaterialUnit = Float.valueOf(otherMaterialHoursEditText.getText().toString());
                    otherMaterialPricePerUnit = Float.valueOf(otherMaterialPricePerUnitEditText.getText().toString());
                    otherMaterialTotalTV.setText(String.valueOf(otherMaterialDayHour * otherMaterialUnit * otherMaterialPricePerUnit));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //ADDING THE MATERIAL WHEN THE BUTTON IS CLICKED
        otherMaterialAddButton = (Button) findViewById(R.id.otherMaterialNew_addButton);
        otherMaterialAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count = 0;

                final EstimationOtherMaterials newMaterial = new EstimationOtherMaterials();

                newMaterial.setJobName(otherMaterialJobNameSpinner.getSelectedItem().toString());
                newMaterial.setOtherMaterialUOM(otherMaterialUOMSpinner.getSelectedItem().toString());

                if (BaseActivity.ifEditTextNotEmptyErrMsg(otherMaterialNameEditText)) {
                    newMaterial.setOtherMaterialName(otherMaterialNameEditText.getText().toString());
                    count++;

                }
                if (BaseActivity.ifEditTextNotEmptyErrMsg(otherMaterialDayHourEditText)) {
                    newMaterial.setOtherMaterialDayHour(Float.parseFloat(otherMaterialDayHourEditText.getText().toString()));
                    count++;
                }
                if (BaseActivity.ifEditTextNotEmptyErrMsg(otherMaterialHoursEditText)) {
                    newMaterial.setOtherMaterialUnit(Float.parseFloat(otherMaterialHoursEditText.getText().toString()));
                    count++;
                }
                if (BaseActivity.ifEditTextNotEmptyErrMsg(otherMaterialPricePerUnitEditText)) {
                    newMaterial.setOtherMaterialPricePerUnit(Float.parseFloat(otherMaterialPricePerUnitEditText.getText().toString()));
                    count++;
                }
                if (BaseActivity.ifEditTextNotEmptyErrMsg(otherMaterialBudgetPerUnitEditText)){
                    newMaterial.setOtherMaterialBudgetPerUnit(Float.parseFloat(otherMaterialBudgetPerUnitEditText.getText().toString()));
                    count++;
                }



                newMaterial.setOtherMaterialTotal(Float.parseFloat(otherMaterialTotalTV.getText().toString()));

                if (count == 5) {

                    new AlertDialog.Builder(EstimationOtherMaterials.this).setMessage(R.string.confirmAdd).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            addNewMaterial(newMaterial);
                        }
                    }).setNegativeButton("No", null).create().show();


                }
            }
        });


    }

    public void setTransportTV()
    {
        otherMaterialPricePerUnitTV.setText("Price/Trip");
    }
    public void setFields(EstimationOtherMaterials estimationOtherMaterials){

        int jobSpinnerPos = Estimation.jobNameAdapter.getPosition(estimationOtherMaterials.jobName);
        otherMaterialJobNameSpinner.setSelection(jobSpinnerPos);
        int UOMSpinnerPos = otherMaterialAA.getPosition(estimationOtherMaterials.otherMaterialUOM);
        otherMaterialUOMSpinner.setSelection(UOMSpinnerPos);
        otherMaterialNameEditText.setText(estimationOtherMaterials.otherMaterialName);
        otherMaterialDayHourEditText.setText(BaseActivity.decimalFormat.format(estimationOtherMaterials.otherMaterialDayHour));
        otherMaterialHoursEditText.setText(BaseActivity.decimalFormat.format(estimationOtherMaterials.otherMaterialUnit));
        otherMaterialBudgetPerUnitEditText.setText(BaseActivity.decimalFormat.format(estimationOtherMaterials.otherMaterialBudgetPerUnit));
        otherMaterialPricePerUnitEditText.setText(BaseActivity.decimalFormat.format(estimationOtherMaterials.otherMaterialPricePerUnit));
        otherMaterialTotalTV.setText(BaseActivity.decimalFormat.format(estimationOtherMaterials.otherMaterialTotal));
    }

    public void addNewMaterial(EstimationOtherMaterials newMaterial) {
        if (i.getStringExtra("ID").equals("EditManPower")) {
            i.putExtra("OtherMaterial", newMaterial);
            i.putExtra("Position", i.getIntExtra("Position", 0));
            setResult(Estimation.MAN_POWER_EDIT, i);
            finish();
        } else if (i.getStringExtra("ID").equals("NewManPower")) {
            i.putExtra("OtherMaterial", newMaterial);
            setResult(Estimation.MAN_POWER_NEW, i);
            finish();
        } else if (i.getStringExtra("ID").equals("NewTools")) {
            i.putExtra("Tools", newMaterial);
            setResult(Estimation.TOOL_NEW, i);
            finish();
        } else if(i.getStringExtra("ID").equals("EditTools")){
            i.putExtra("Tools",newMaterial);
            i.putExtra("Position",i.getIntExtra("Position",0));
            setResult(Estimation.TOOL_EDIT,i);
            finish();
        }else if (i.getStringExtra("ID").equals("NewTransport")){
            i.putExtra("Transport", newMaterial);
            setResult(Estimation.TRANSPORT_NEW,i);
            finish();
        }else if(i.getStringExtra("ID").equals("EditTransport")){
            i.putExtra("Transport", newMaterial);
            i.putExtra("Position", i.getIntExtra("Position",0));
            setResult(Estimation.TRANSPORT_EDIT, i);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED, i);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

       //METHOD TO VALIDATE IF THE EDIT TEXT IS EMPTY

    public static boolean checkEmpty() {
        return false;
    }


    //PARCELABLE
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.otherMaterialName);
        dest.writeFloat(this.otherMaterialDayHour);
        dest.writeFloat(this.otherMaterialUnit);
        dest.writeFloat(this.otherMaterialPricePerUnit);
        dest.writeFloat(this.otherMaterialBudgetPerUnit);
        dest.writeString(this.otherMaterialUOM);
        dest.writeFloat(this.otherMaterialTotal);
        dest.writeString(this.jobName);
    }

    public EstimationOtherMaterials() {
    }

    protected EstimationOtherMaterials(Parcel in) {
        this.otherMaterialName = in.readString();
        this.otherMaterialDayHour = in.readFloat();
        this.otherMaterialUnit = in.readFloat();
        this.otherMaterialPricePerUnit = in.readFloat();
        this.otherMaterialBudgetPerUnit = in.readFloat();
        this.otherMaterialUOM = in.readString();
        this.otherMaterialTotal = in.readFloat();
        this.jobName = in.readString();
    }

    public static final Parcelable.Creator<EstimationOtherMaterials> CREATOR = new Parcelable.Creator<EstimationOtherMaterials>() {
        @Override
        public EstimationOtherMaterials createFromParcel(Parcel source) {
            return new EstimationOtherMaterials(source);
        }

        @Override
        public EstimationOtherMaterials[] newArray(int size) {
            return new EstimationOtherMaterials[size];
        }
    };
}
