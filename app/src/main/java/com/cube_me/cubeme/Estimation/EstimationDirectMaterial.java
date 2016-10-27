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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cube_me.cubeme.BaseActivity;
import com.cube_me.cubeme.R;

public class EstimationDirectMaterial extends AppCompatActivity implements Parcelable {

    Toolbar toolbar;
    String materialName;
    String materialDimension;
    String materialUOM;
    float materialUnitPrice;
    float materialNoOfUnits;
    float materialTotal;
    float materialBudget;
    String jobName;

    EditText materialNameEditText;
    EditText materialDimensionEditText;
    EditText materialUOMEditText;
    EditText materialUnitPriceEditText;
    EditText materialNoOfUnitsEditText;
    EditText materialBudgetEditText;
    TextView materialTotalTV;
    Button materialAddButton;
    Spinner materialJobSpinner;
    Intent i;


    //SETTERS

    public void setMaterialBudget(float materialBudget) {
        this.materialBudget = materialBudget;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }


    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }


    public void setMaterialDimension(String materialDimension) {
        this.materialDimension = materialDimension;
    }


    public void setMaterialUOM(String materialUOM) {
        this.materialUOM = materialUOM;
    }


    public void setMaterialUnitPrice(float materialUnitPrice) {
        this.materialUnitPrice = materialUnitPrice;
    }


    public void setMaterialNoOfUnits(float materialNoOfUnits) {
        this.materialNoOfUnits = materialNoOfUnits;
    }

    public void setMaterialTotal(float materialTotal) {
        this.materialTotal = materialTotal;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estimation_directmaterial_add);

        i = getIntent();
        String title = i.getStringExtra("Title");
       // INIT TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(title);

        //INITIALIZING ALL THE FIELDS
        materialNameEditText = (EditText) findViewById(R.id.directMaterialNew_nameEditText);
        materialDimensionEditText = (EditText) findViewById(R.id.directMaterialNew_dimensionEditText);
        materialUOMEditText = (EditText) findViewById(R.id.directMaterialNew_UOMEditText);
        materialUnitPriceEditText = (EditText) findViewById(R.id.directMaterialNew_unitPriceEditText);
        materialNoOfUnitsEditText = (EditText) findViewById(R.id.directMaterialNew_noOfUnitsEditText);
        materialTotalTV = (TextView) findViewById(R.id.directMaterialNew_totalTV);
        materialJobSpinner = (Spinner) findViewById(R.id.directMaterialNew_jobSpinner);
        materialJobSpinner.setAdapter(Estimation.jobNameAdapter);
        materialBudgetEditText = (EditText) findViewById(R.id.directMaterialNew_budgetEditText);
        //SETTING UP FIELDS IF THE INTENT CALL IS FOR EDIT

        if (i.getStringExtra("ID").equals("Edit")) {

            EstimationDirectMaterial editEstimationDirectMaterial = i.getParcelableExtra("Edit Material");
            materialNameEditText.setText(editEstimationDirectMaterial.materialName);
            materialDimensionEditText.setText(editEstimationDirectMaterial.materialDimension);
            materialUOMEditText.setText(editEstimationDirectMaterial.materialUOM);
            materialNoOfUnitsEditText.setText(BaseActivity.decimalFormat.format(editEstimationDirectMaterial.materialNoOfUnits));
            materialBudgetEditText.setText(BaseActivity.decimalFormat.format(editEstimationDirectMaterial.materialBudget));
            materialUnitPriceEditText.setText(BaseActivity.decimalFormat.format(editEstimationDirectMaterial.materialUnitPrice));
            materialTotalTV.setText(String.valueOf(editEstimationDirectMaterial.materialTotal));
            int spinnerPos = Estimation.jobNameAdapter.getPosition(editEstimationDirectMaterial.jobName);
            materialJobSpinner.setSelection(spinnerPos);

        }

        //SETTING UP KEYBOARD LISTENER FOR DYNAMIC CALCULATION
        materialUnitPriceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(materialUnitPriceEditText.getText())) {

                    materialUnitPriceEditText.setError("Enter the Material Unit Price");
                    materialTotalTV.setText("0.0");
                } else if (!TextUtils.isEmpty(materialNoOfUnitsEditText.getText())) {

                    materialNoOfUnits = Float.valueOf(materialNoOfUnitsEditText.getText().toString());
                    materialUnitPrice = Float.valueOf(materialUnitPriceEditText.getText().toString());
                    materialTotalTV.setText(String.valueOf(materialNoOfUnits * materialUnitPrice));
                }
            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        materialNoOfUnitsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if (TextUtils.isEmpty(materialNoOfUnitsEditText.getText())) {
                    materialTotalTV.setText("0");
                }

                if (!TextUtils.isEmpty(materialNoOfUnitsEditText.getText())) {


                    if (TextUtils.isEmpty(materialUnitPriceEditText.getText())) {
                        materialUnitPriceEditText.setError("Enter the Material Unit Price");
                    } else {
                        materialNoOfUnits = Float.valueOf(materialNoOfUnitsEditText.getText().toString());
                        materialUnitPrice = Float.valueOf(materialUnitPriceEditText.getText().toString());
                        materialTotalTV.setText(String.valueOf(materialNoOfUnits * materialUnitPrice));
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //SETTING UP ADD BUTTON
        materialAddButton = (Button) findViewById(R.id.directMaterialNew_addButton);

        materialAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count = 0;

                final EstimationDirectMaterial estimationDirectMaterial = new EstimationDirectMaterial();

                estimationDirectMaterial.setJobName(materialJobSpinner.getSelectedItem().toString());
                if (BaseActivity.ifEditTextNotEmptyErrMsg(materialNameEditText)) {
                    estimationDirectMaterial.setMaterialName(materialNameEditText.getText().toString());
                    count++;
                }
                if (BaseActivity.ifEditTextNotEmpty(materialDimensionEditText)) {
                    estimationDirectMaterial.setMaterialDimension(materialDimensionEditText.getText().toString());
                    count++;
                } else {
                    estimationDirectMaterial.setMaterialDimension("NA");
                    count++;
                }
                if (BaseActivity.ifEditTextNotEmptyErrMsg(materialUOMEditText)) {
                    estimationDirectMaterial.setMaterialUOM(materialUOMEditText.getText().toString());
                    count++;
                }
                if(BaseActivity.ifEditTextNotEmptyErrMsg(materialBudgetEditText)){
                    estimationDirectMaterial.setMaterialBudget(Float.parseFloat(materialBudgetEditText.getText().toString()));
                    count ++;
                }
                if (BaseActivity.ifEditTextNotEmptyErrMsg(materialUnitPriceEditText)) {
                    estimationDirectMaterial.setMaterialUnitPrice(Float.parseFloat(materialUnitPriceEditText.getText().toString()));
                    count++;
                }
                if (BaseActivity.ifEditTextNotEmptyErrMsg(materialNoOfUnitsEditText)) {
                    estimationDirectMaterial.setMaterialNoOfUnits(Float.parseFloat(materialNoOfUnitsEditText.getText().toString()));
                    count++;
                }

                estimationDirectMaterial.setMaterialTotal(Float.parseFloat(materialTotalTV.getText().toString()));

                if (count == 6) {
                    new AlertDialog.Builder(EstimationDirectMaterial.this).setMessage(R.string.confirmAdd).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            addNewMaterial(estimationDirectMaterial);
                        }
                    }).setNegativeButton("No", null).create().show();
                }

            }
        });

    }

    public void addNewMaterial(EstimationDirectMaterial data) {

        jobName = materialJobSpinner.getSelectedItem().toString();
        if (i.getStringExtra("ID").equals("Edit")) {
            i.putExtra("DirectMaterial", data);
            i.putExtra("Position", i.getIntExtra("Position", 0));
            setResult(Estimation.DIRECT_MATERIAL_EDIT, i);
            finish();
        } else {
            i.putExtra("DirectMaterial", data);
            setResult(Estimation.DIRECT_MATERIAL_NEW, i);
            finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(0, i);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.materialName);
        dest.writeString(this.materialDimension);
        dest.writeString(this.materialUOM);
        dest.writeFloat(this.materialUnitPrice);
        dest.writeFloat(this.materialBudget);
        dest.writeFloat(this.materialNoOfUnits);
        dest.writeFloat(this.materialTotal);
        dest.writeString(this.jobName);
    }

    public EstimationDirectMaterial() {
    }

    protected EstimationDirectMaterial(Parcel in) {
        this.materialName = in.readString();
        this.materialDimension = in.readString();
        this.materialUOM = in.readString();
        this.materialUnitPrice = in.readFloat();
        this.materialBudget = in.readFloat();
        this.materialNoOfUnits = in.readFloat();
        this.materialTotal = in.readFloat();
        this.jobName = in.readString();
    }

    public static final Parcelable.Creator<EstimationDirectMaterial> CREATOR = new Parcelable.Creator<EstimationDirectMaterial>() {
        @Override
        public EstimationDirectMaterial createFromParcel(Parcel source) {
            return new EstimationDirectMaterial(source);
        }

        @Override
        public EstimationDirectMaterial[] newArray(int size) {
            return new EstimationDirectMaterial[size];
        }
    };
}
