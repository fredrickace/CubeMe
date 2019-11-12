package com.cube_me.cubeme_crm.Estimation;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.R;

public class EstimationConsumables extends AppCompatActivity implements Parcelable {

    String consumableName;
    String consumableUOM;
    float consumableUnitPrice;
    float consumableBudgetPrice;
    float consumableNoOfUnits;
    float consumableTotal;
    String jobName;
    Toolbar toolbar;

    Intent i;

    EditText consumableNameEditText;
    EditText consumableUOMEditText;
    EditText consumableUnitPriceEditText;
    EditText consumableBudgetEditText;
    EditText consumableNoOfUnitsEditText;
    TextView consumableTotalTV;
    Button consumableAddNewButton;
    Spinner consumableJobSpinner;

    EstimationConsumables newConsumables;
    int count;

    //    Setters

    public void setConsumableBudgetPrice(float consumableBudgetPrice) {
        this.consumableBudgetPrice = consumableBudgetPrice;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setConsumableName(String consumableName) {
        this.consumableName = consumableName;
    }

    public void setConsumableUOM(String consumableUOM) {
        this.consumableUOM = consumableUOM;
    }

    public void setConsumableUnitPrice(Float consumableUnitPrice) {
        this.consumableUnitPrice = consumableUnitPrice;
    }

    public void setConsumableNoOfUnits(Float consumableNoOfUnits) {
        this.consumableNoOfUnits = consumableNoOfUnits;
    }

    public void setConsumableTotal(Float consumableTotal) {
        this.consumableTotal = consumableTotal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estimation_consumables);


        i = getIntent();
        String title = i.getStringExtra("Title");

        //INIT TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(title);

        //BASIC INIT
        consumableNameEditText = (EditText) findViewById(R.id.consumablesNew_nameEditText);
        consumableUOMEditText = (EditText) findViewById(R.id.consumablesNew_UOMEditText);
        consumableUnitPriceEditText = (EditText) findViewById(R.id.consumablesNew_unitPriceEditText);
        consumableBudgetEditText = (EditText) findViewById(R.id.consumablesNew_BudgetPriceEditText);
        consumableNoOfUnitsEditText = (EditText) findViewById(R.id.consumablesNew_noOfUnitsEditText);
        consumableTotalTV = (TextView) findViewById(R.id.consumablesNew_totalTV);
        consumableAddNewButton = (Button) findViewById(R.id.consumablesNew_addButton);
        consumableJobSpinner = (Spinner) findViewById(R.id.consumablesNew_jobSpinner);
        consumableJobSpinner.setAdapter(Estimation.jobNameAdapter);

        newConsumables = new EstimationConsumables();

        //POPULATING THE EDITTEXT FIELDS IF THE INTENT CALL IS FOR EDIT
        if(i.getStringExtra("ID").equals("EditConsumable")){
            newConsumables = i.getParcelableExtra("ParcelEdit");
            consumableNameEditText.setText(newConsumables.consumableName);
            consumableUOMEditText.setText(newConsumables.consumableUOM);
            consumableUnitPriceEditText.setText(BaseActivity.decimalFormat.format(newConsumables.consumableUnitPrice));
            consumableBudgetEditText.setText(BaseActivity.decimalFormat.format(newConsumables.consumableBudgetPrice));
            consumableNoOfUnitsEditText.setText(BaseActivity.decimalFormat.format(newConsumables.consumableNoOfUnits));
            consumableTotalTV.setText(BaseActivity.decimalFormat.format(newConsumables.consumableTotal));

            int jobPos = Estimation.jobNameAdapter.getPosition(newConsumables.jobName);
            consumableJobSpinner.setSelection(jobPos);
        }


        //SETTING UP ON TEXT CHANGED LISTENER ON UNIT PRICE EDIT TEXT

        consumableUnitPriceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!BaseActivity.ifEditTextNotEmptyErrMsg(consumableUnitPriceEditText)){
                    consumableTotalTV.setText("0");
                }else if(BaseActivity.ifEditTextNotEmptyErrMsg(consumableNoOfUnitsEditText)){

                    consumableNoOfUnits = Float.valueOf(consumableNoOfUnitsEditText.getText().toString());
                    consumableUnitPrice = Float.valueOf(consumableUnitPriceEditText.getText().toString());
                    consumableTotal = consumableNoOfUnits * consumableUnitPrice;
                    consumableTotalTV.setText(String.valueOf(consumableTotal));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //SETTING UP ON TEXT CHANGER LISTENER ON NOOFUNITS EDITTEXT

        consumableNoOfUnitsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!BaseActivity.ifEditTextNotEmptyErrMsg(consumableNoOfUnitsEditText)){
                    consumableTotalTV.setText("0");
                }else if(BaseActivity.ifEditTextNotEmptyErrMsg(consumableUnitPriceEditText)){

                    consumableNoOfUnits = Float.valueOf(consumableNoOfUnitsEditText.getText().toString());
                    consumableUnitPrice = Float.valueOf(consumableUnitPriceEditText.getText().toString());
                    consumableTotal = consumableNoOfUnits * consumableUnitPrice;
                    consumableTotalTV.setText(BaseActivity.decimalFormat.format(consumableTotal));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //SETTING UP ADD NEW BUTTON
        consumableAddNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newConsumables.setJobName(consumableJobSpinner.getSelectedItem().toString());
                count = 0;
                if(BaseActivity.ifEditTextNotEmptyErrMsg(consumableNameEditText)){
                    newConsumables.setConsumableName(consumableNameEditText.getText().toString());
                    count ++;
                }
                if(TextUtils.isEmpty(consumableUOMEditText.getText().toString())){
                    newConsumables.setConsumableUOM("-");
                }else {
                    newConsumables.setConsumableUOM(consumableUOMEditText.getText().toString());
                }
                if(BaseActivity.ifEditTextNotEmptyErrMsg(consumableUnitPriceEditText)){
                    newConsumables.setConsumableUnitPrice(Float.parseFloat(consumableUnitPriceEditText.getText().toString()));
                    count ++;
                }
                if(BaseActivity.ifEditTextNotEmptyErrMsg(consumableNoOfUnitsEditText)){
                    newConsumables.setConsumableNoOfUnits(Float.parseFloat(consumableNoOfUnitsEditText.getText().toString()));
                    count++;
                }
                if(BaseActivity.ifEditTextNotEmptyErrMsg(consumableBudgetEditText)){
                    newConsumables.setConsumableBudgetPrice(Float.parseFloat(consumableBudgetEditText.getText().toString()));
                    count++;
                }
                newConsumables.setConsumableTotal(Float.valueOf(consumableTotalTV.getText().toString()));


                if(count == 4){
                    new AlertDialog.Builder(EstimationConsumables.this).setMessage(R.string.confirmAdd).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            addNewConsumable();
                        }
                    }).setNegativeButton("No", null).create().show();

                }
            }
        });
    }

    void addNewConsumable(){
        if(i.getStringExtra("ID").equals("NewConsumable")) {
            i.putExtra("Consumable", newConsumables);
            setResult(Estimation.CONSUMABLES_NEW, i);
            finish();
        }
        if(i.getStringExtra("ID").equals("EditConsumable")){
            i.putExtra("Consumable", newConsumables);
            i.putExtra("Position", i.getIntExtra("Position",0));
            setResult(Estimation.CONSUMABLES_EDIT,i);
            finish();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                setResult(0,i);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public EstimationConsumables() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.consumableName);
        dest.writeString(this.consumableUOM);
        dest.writeFloat(this.consumableUnitPrice);
        dest.writeFloat(this.consumableBudgetPrice);
        dest.writeFloat(this.consumableNoOfUnits);
        dest.writeFloat(this.consumableTotal);
        dest.writeString(this.jobName);
    }

    protected EstimationConsumables(Parcel in) {
        this.consumableName = in.readString();
        this.consumableUOM = in.readString();
        this.consumableUnitPrice = in.readFloat();
        this.consumableBudgetPrice = in.readFloat();
        this.consumableNoOfUnits = in.readFloat();
        this.consumableTotal = in.readFloat();
        this.jobName = in.readString();
    }

    public static final Creator<EstimationConsumables> CREATOR = new Creator<EstimationConsumables>() {
        @Override
        public EstimationConsumables createFromParcel(Parcel source) {
            return new EstimationConsumables(source);
        }

        @Override
        public EstimationConsumables[] newArray(int size) {
            return new EstimationConsumables[size];
        }
    };
}
