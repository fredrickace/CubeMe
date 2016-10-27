package com.cube_me.cubeme.Estimation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cube_me.cubeme.BaseActivity;
import com.cube_me.cubeme.ClickListener;
import com.cube_me.cubeme.R;
import com.cube_me.cubeme.RecyclerTouchListener;
import com.cube_me.cubeme.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Estimation extends AppCompatActivity implements Parcelable, EstimationJobDialog.DialogJobCommunicator, EstimationJobEditDialog.JobEditCommunicator {

    Toolbar toolbar;
    Context context;
    float totalCount;
    EstimationDirectMaterial estimationDirectMaterial;
    EstimationOtherMaterials estimationOtherMaterials;
    EstimationConsumables estimationConsumables;
    TextView estimationIDTV;
    TextView grandTotalTV;
    EditText overHeadsEditText;
    EditText marginEditText;
    TextView accountTV;
    Button addNewEstimationButton;


    EstimationJobs estimationJobs;
    public static List<EstimationJobs> jobsList;
    static ArrayList<String> jobNames;
    static ArrayAdapter<String> jobNameAdapter;
    ImageButton jobAddImageButton;
    static LinearLayout jobTotalLinearLayout;
    static FragmentManager fragmentManager;
    int backCount = 0;
    Intent callingIntent;

    float grandTotal;
    float sumTotal; /*THIS TOTAL IS EXCLUDING THE OVERHEAD COST*/
    float directMaterialTotal;
    float manPowerTotal;
    float toolsTotal;
    float transportTotal;
    float consumablesTotal;
    float overHeads;
    float overHeadPercentage;
    float margin;
    float marginPercentage;
    public String estimationID;
    public String clientAccount;
    public String estimationCreateDate;
    public String estimationLastEditDate;
    public String estimationCreateTime;
    public String estimationLastEditTime;

    public ProgressDialog progressDialog;

    final static int ESTIMATION_NEW_ADAPTER = 0;


    public static final String ESTIMATION_EDIT = "EstimationEdit";

    public static final String ESTIMATION_NEW = "EstimationNew";

    Estimation estimation;

    //SETTERS
    public void setEstimationCreateDate(String estimationCreateDate) {
        this.estimationCreateDate = estimationCreateDate;
    }

    public void setEstimationLastEditDate(String estimationLastEditDate) {
        this.estimationLastEditDate = estimationLastEditDate;
    }

    public void setEstimationCreateTime(String estimationCreateTime) {
        this.estimationCreateTime = estimationCreateTime;
    }

    public void setEstimationLastEditTime(String estimationLastEditTime) {
        this.estimationLastEditTime = estimationLastEditTime;
    }

    public void setClientAccount(String clientAccount) {
        this.clientAccount = clientAccount;
    }

    public void setEstimationID(String estimationID) {
        this.estimationID = estimationID;
    }

    public void setMargin(float margin) {
        this.margin = margin;
    }

    public void setOverHeadPercentage(float overHeadPercentage) {
        this.overHeadPercentage = overHeadPercentage;
    }

    public void setMarginPercentage(float marginPercentage) {
        this.marginPercentage = marginPercentage;
    }

    public void setEstimationDirectMaterialList(List<EstimationDirectMaterial> estimationDirectMaterialList) {
        this.estimationDirectMaterialList = estimationDirectMaterialList;
    }

    public void setManPowerList(List<EstimationOtherMaterials> manPowerList) {
        this.manPowerList = manPowerList;
    }

    public void setToolsList(List<EstimationOtherMaterials> toolsList) {
        this.toolsList = toolsList;
    }

    public void setTransportList(List<EstimationOtherMaterials> transportList) {
        this.transportList = transportList;
    }

    public void setConsumableList(List<EstimationConsumables> consumableList) {
        this.consumableList = consumableList;
    }


    public void setGrandTotal(float grandTotal) {
        this.grandTotal = grandTotal;
    }

    public void setDirectMaterialTotal(float directMaterialTotal) {
        this.directMaterialTotal = directMaterialTotal;
    }

    public void setManPowerTotal(float manPowerTotal) {
        this.manPowerTotal = manPowerTotal;
    }

    public void setToolsTotal(float toolsTotal) {
        this.toolsTotal = toolsTotal;
    }

    public void setTransportTotal(float transportTotal) {
        this.transportTotal = transportTotal;
    }

    public void setConsumablesTotal(float consumablesTotal) {
        this.consumablesTotal = consumablesTotal;
    }

    public void setOverHeads(float overHeads) {
        this.overHeads = overHeads;
    }

    //DIRECT MATERIALS VARIABLES
    RecyclerView directMaterialRecyclerView;
    LinearLayout directMaterialLinearLayout;
    ImageButton addDirectMaterialImageButton;
    ImageButton directMaterialFoldImageButton;
    ImageButton directMaterialUnfoldImageButton;
    TextView directMaterialTotalTV;
    EstimationDirectMaterialRecyclerAdapter estimationDirectMaterialRecyclerAdapter;
    public List<EstimationDirectMaterial> estimationDirectMaterialList;
    final static int DIRECT_MATERIAL_NEW = 1;
    final static int DIRECT_MATERIAL_EDIT = 2;


    //MAN POWER VARIABLES
    RecyclerView manPowerRecyclerView;
    LinearLayout manPowerLinearLayout;
    ImageButton addManPowerImageButton;
    ImageButton manPowerFoldImageButton;
    ImageButton manPowerUnfoldImageButton;
    TextView manPowerTotalTV;
    EstimationOtherMaterialRecyclerAdapter manPowerRecyclerAdapter;
    public List<EstimationOtherMaterials> manPowerList;
    final static int MAN_POWER_NEW = 3;
    final static int MAN_POWER_EDIT = 4;

    //TOOLS AND PLANT VARIABLES
    RecyclerView toolsRecyclerView;
    LinearLayout toolsLinearLayout;
    ImageButton addToolsImageButton;
    ImageButton toolsFoldImageButton;
    ImageButton toolsUnFoldImageButton;
    TextView toolsTotalTV;
    EstimationOtherMaterialRecyclerAdapter toolsRecyclerAdapter;
    public List<EstimationOtherMaterials> toolsList;
    final static int TOOL_NEW = 5;
    final static int TOOL_EDIT = 6;

    //TRANSPORT VARIABLES
    RecyclerView transportRecyclerView;
    LinearLayout transportLinearLayout;
    ImageButton addTransportImageButton;
    ImageButton transportFoldImageButton;
    ImageButton transportUnfoldImageButton;
    TextView transportTotalTV;
    EstimationOtherMaterialRecyclerAdapter transportRecyclerAdapter;
    public List<EstimationOtherMaterials> transportList;
    final static int TRANSPORT_NEW = 7;
    final static int TRANSPORT_EDIT = 8;

    //CONSUMABLE VARIABLES
    RecyclerView consumableRecyclerView;
    LinearLayout consumableLinearLayout;
    ImageButton addConsumableImageButton;
    ImageButton consumableFoldImageButton;
    ImageButton consumableUnfoldImageButton;
    TextView consumableTotalTV;
    EstimationConsumableRecyclerAdapter consumableRecyclerAdapter;
    public List<EstimationConsumables> consumableList;
    final static int CONSUMABLES_NEW = 9;
    final static int CONSUMABLES_EDIT = 10;

    String flag;


    //CHECKING THE JOBLIST AND INFLATING THE JOBDIALOGFRAGMENT IF JOBLIST IS EMPTY
    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (jobsList.size() <= 0) {
            callJobDialogFragment();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estimation_new);


        //INIT TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Add Estimation");

        //BASIC INIT
        estimationIDTV = (TextView) findViewById(R.id.estNew_IDTV);
        grandTotalTV = (TextView) findViewById(R.id.estNew_grandTotalTV);
        directMaterialTotalTV = (TextView) findViewById(R.id.estNew_directMaterialTotalTV);
        consumableTotalTV = (TextView) findViewById(R.id.estNew_consumablesTotalTV);
        transportTotalTV = (TextView) findViewById(R.id.estNew_transportTotalTV);
        toolsTotalTV = (TextView) findViewById(R.id.estNew_toolsTotalTV);
        manPowerTotalTV = (TextView) findViewById(R.id.estNew_manPowerTotalTV);
        overHeadsEditText = (EditText) findViewById(R.id.estNew_overHeadEditText);
        marginEditText = (EditText) findViewById(R.id.estNew_marginEditText);
        jobTotalLinearLayout = (LinearLayout) findViewById(R.id.estNew_jobTotalContainer);
        addNewEstimationButton = (Button) findViewById(R.id.estNew_addNewEstimation);
        accountTV = (TextView) findViewById(R.id.estNew_clientAccountTV);

        //GETTING THE CURRENT DATE
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

        estimationLastEditDate = date + "/" + month + "/" + year;
        estimationCreateDate = estimationLastEditDate;
        estimationLastEditTime = hours + ":" + min + ":" + seconds + ":" + AM_PM;
        estimationCreateTime = estimationLastEditTime;


        callingIntent = getIntent();
        flag = callingIntent.getStringExtra("Flag");
        if (flag.equals(ESTIMATION_NEW)) {


            estimation = new Estimation();
            grandTotal = 0;
            sumTotal = 0;
            directMaterialTotal = 0;
            manPowerTotal = 0;
            toolsTotal = 0;
            transportTotal = 0;
            consumablesTotal = 0;
            overHeads = 0;
            estimationDirectMaterialList = new ArrayList<>();
            manPowerList = new ArrayList<>();
            toolsList = new ArrayList<>();
            transportList = new ArrayList<>();
            consumableList = new ArrayList<>();
            //INITIATING ESTIMATION JOBS
            jobNames = new ArrayList<String>();
            jobsList = new ArrayList<>();
            String inquiryID = callingIntent.getStringExtra("InquiryID");
            estimationID = "EST/" + inquiryID.substring(4);
            estimationIDTV.setText(estimationID);
            clientAccount = callingIntent.getStringExtra("Client");
            accountTV.setText(clientAccount);


        } else if (flag.equals(ESTIMATION_EDIT)) {

            estimation = callingIntent.getParcelableExtra("EstimationEdit");
            grandTotal = estimation.grandTotal;
            sumTotal = 0;
            directMaterialTotal = estimation.directMaterialTotal;
            manPowerTotal = estimation.manPowerTotal;
            toolsTotal = estimation.toolsTotal;
            transportTotal = estimation.transportTotal;
            consumablesTotal = estimation.consumablesTotal;
            overHeads = 0;
            overHeadPercentage = estimation.overHeadPercentage;
            marginPercentage = estimation.marginPercentage;
            estimationDirectMaterialList = estimation.estimationDirectMaterialList;
            manPowerList = estimation.manPowerList;
            toolsList = estimation.toolsList;
            transportList = estimation.transportList;
            consumableList = estimation.consumableList;
            jobNames = new ArrayList<String>();
            jobsList = estimation.jobsList;
            estimationID = estimation.estimationID;
            clientAccount = estimation.clientAccount;
            estimationCreateDate = estimation.estimationCreateDate;
            estimationCreateTime = estimation.estimationCreateTime;
            estimationIDTV.setText(estimationID);
            accountTV.setText(clientAccount);
            grandTotalTV.setText(BaseActivity.decimalFormat.format(grandTotal));
            directMaterialTotalTV.setText(BaseActivity.decimalFormat.format(directMaterialTotal));
            manPowerTotalTV.setText(BaseActivity.decimalFormat.format(manPowerTotal));
            toolsTotalTV.setText(BaseActivity.decimalFormat.format(toolsTotal));
            transportTotalTV.setText(BaseActivity.decimalFormat.format(transportTotal));
            consumableTotalTV.setText(BaseActivity.decimalFormat.format(consumablesTotal));
            overHeadsEditText.setText(BaseActivity.decimalFormat.format(overHeadPercentage));
            marginEditText.setText(BaseActivity.decimalFormat.format(marginPercentage));
            setJobsOnEdit();

            new AlertDialog.Builder(Estimation.this).setMessage("Edit as New Version or Edit as Same Version?")
                    .setPositiveButton("New Version", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EstimationDatabaseAdapter estimationDatabaseAdapter = new EstimationDatabaseAdapter(context);
                            String[] splitIDName = estimationID.split("\\.");

                            List<Estimation> estimationList = estimationDatabaseAdapter.getEstimationForInquiry(splitIDName[0]);
                            int versionCount = estimationList.size();
                            estimationID = splitIDName[0] + ".v" + versionCount;
                            estimationIDTV.setText(estimationID);
                        }
                    }).setNegativeButton("Same Version", null).create().show();

        }
        fragmentManager = getSupportFragmentManager();

        context = getApplicationContext();
        jobAddImageButton = (ImageButton) findViewById(R.id.estNew_addJobImageButton);
        jobAddImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callJobDialogFragment();
            }
        });

        //SETTING THE SPINNER ARRAY ADAPTER COMMON FOR ALL
        jobNameAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, Estimation.jobNames);
        jobNameAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);


        //SETTING UP THE GRAND TOTAL CALCULATING THE OVERHEAD AND MARGIN
        overHeadsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                grandTotal = getGrandTotal();
                grandTotalTV.setText(BaseActivity.decimalFormat.format(grandTotal));
                populateJobsTotal();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        marginEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                grandTotal = getGrandTotal();
                grandTotalTV.setText(BaseActivity.decimalFormat.format(grandTotal));
                populateJobsTotal();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        addNewEstimationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(BaseActivity.networkIsConnected) {
                    sendEstimation();
                }else {
                    Toast.makeText(context, "Network Unavailable. Save Offline", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //START OF DIRECT MATERIAL SECTION
        //SETTING FOLDING AND UNFOLDING OF DIRECT MATERIAL VIEWS
        directMaterialLinearLayout = (LinearLayout) findViewById(R.id.estNew_directMaterialFoldLinearLayout);
        directMaterialLinearLayout.setVisibility(View.GONE);
        directMaterialUnfoldImageButton = (ImageButton) findViewById(R.id.estNew_directMaterialUnfoldImageButton);
        directMaterialFoldImageButton = (ImageButton) findViewById(R.id.estNew_directMaterialFoldImageButton);
        directMaterialFoldImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                directMaterialFoldImageButton.setVisibility(View.INVISIBLE);
                directMaterialUnfoldImageButton.setVisibility(View.VISIBLE);
                directMaterialLinearLayout.setVisibility(View.GONE);
            }
        });

        directMaterialUnfoldImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                directMaterialFoldImageButton.setVisibility(View.VISIBLE);
                directMaterialUnfoldImageButton.setVisibility(View.INVISIBLE);
                directMaterialLinearLayout.setVisibility(View.VISIBLE);
            }
        });

        //SETTING UP RECYCLERVIEW FOR ADDING A NEW DIRECTMATERIAL
        directMaterialTotalTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                grandTotal = getGrandTotal();
                grandTotalTV.setText(BaseActivity.decimalFormat.format(grandTotal));
            }
        });
        directMaterialRecyclerView = (RecyclerView) findViewById(R.id.estNew_directMaterialRecycler);
        setDMRecyclerAdapter();
        directMaterialRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, directMaterialRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                EstimationDirectMaterial toEdit = estimationDirectMaterialList.get(position);
                Intent DMEditIntent = new Intent(Estimation.this, EstimationDirectMaterial.class);
                DMEditIntent.putExtra("Edit Material", toEdit);
                DMEditIntent.putExtra("ID", "Edit");
                DMEditIntent.putExtra("Title", "Materials");
                DMEditIntent.putExtra("Position", position);
                startActivityForResult(DMEditIntent, DIRECT_MATERIAL_EDIT);

            }

            @Override
            public void onLongClick(View view, int position) {

                final int positionFinal = position;

                // SETTING UP CONFIRM DIALOGS FOR ADDING DIRECTMATERIAL
                new AlertDialog.Builder(Estimation.this).setMessage(R.string.confirmDelete).
                        setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                estimationDirectMaterialList.remove(positionFinal);
//                                estimationDirectMaterialRecyclerAdapter.notifyDataSetChanged();
                                setDMRecyclerAdapter();
                                populateDMTotalTV();
                                populateJobsTotal();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create().show();


            }
        }));
        directMaterialRecyclerView.setAdapter(estimationDirectMaterialRecyclerAdapter);


        addDirectMaterialImageButton = (ImageButton) findViewById(R.id.estNew_directMaterialAddNewButton);
        addDirectMaterialImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Estimation.this, EstimationDirectMaterial.class);
                i1.putExtra("ID", "New");
                i1.putExtra("Title", "Materials");
                startActivityForResult(i1, DIRECT_MATERIAL_NEW);

            }
        });

        //END OF DIRECTMATERIAL SECTION

        //START OF MANPOWER SECTION
        //SETTING FOLDING AND UNFOLDING OF MANPOWER VIEWS
        manPowerLinearLayout = (LinearLayout) findViewById(R.id.estNew_manPowerFoldLinearLayout);
        manPowerLinearLayout.setVisibility(View.GONE);
        manPowerUnfoldImageButton = (ImageButton) findViewById(R.id.estNew_manPowerUnfoldImageButton);
        manPowerFoldImageButton = (ImageButton) findViewById(R.id.estNew_manPowerFoldImageButton);
        manPowerFoldImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                manPowerFoldImageButton.setVisibility(View.INVISIBLE);
                manPowerUnfoldImageButton.setVisibility(View.VISIBLE);
                manPowerLinearLayout.setVisibility(View.GONE);
            }
        });

        manPowerUnfoldImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                manPowerFoldImageButton.setVisibility(View.VISIBLE);
                manPowerUnfoldImageButton.setVisibility(View.INVISIBLE);
                manPowerLinearLayout.setVisibility(View.VISIBLE);
            }
        });

        //SETTING UP RECYCLERVIEW FOR ADDING A NEW MANPOWER ROW
        manPowerTotalTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                grandTotal = getGrandTotal();
                grandTotalTV.setText(BaseActivity.decimalFormat.format(grandTotal));
            }
        });
        manPowerRecyclerView = (RecyclerView) findViewById(R.id.estNew_manPowerRecycler);
        setManPowerRecyclerAdapter();
        manPowerRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, manPowerRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                EstimationOtherMaterials editManPower = manPowerList.get(position);
                Intent editManPowerIntent = new Intent(context, EstimationOtherMaterials.class);
                editManPowerIntent.putExtra("ParcelEdit", editManPower);
                editManPowerIntent.putExtra("ID", "EditManPower");
                editManPowerIntent.putExtra("Title", "Man Power");
                editManPowerIntent.putExtra("Position", position);
                startActivityForResult(editManPowerIntent, MAN_POWER_EDIT);
            }

            @Override
            public void onLongClick(View view, int position) {


                final int editManPosition = position;
                //SETTING UP CONFIRM DIALOGS FOR ADDING DIRECTMATERIAL
                new AlertDialog.Builder(Estimation.this).setMessage(R.string.confirmDelete).
                        setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                manPowerList.remove(editManPosition);
                                setManPowerRecyclerAdapter();
                                populateManPowerTotalTV();
                                populateJobsTotal();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create().show();

            }
        }));
        manPowerRecyclerView.setAdapter(manPowerRecyclerAdapter);

        addManPowerImageButton = (ImageButton) findViewById(R.id.estNew_manPowerAddNewImageButton);
        addManPowerImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(context, EstimationOtherMaterials.class);
                i2.putExtra("ID", "NewManPower");
                i2.putExtra("Title", "Man Power");
                startActivityForResult(i2, MAN_POWER_NEW);
            }
        });

        //  END OF MANPOWER SECTION

        // START OF TOOLS & PLANTS SECTION
        //SETTING FOLDING AND UNFOLDING OF TOOLS VIEWS
        toolsLinearLayout = (LinearLayout) findViewById(R.id.estNew_toolsFoldLinearLayout);
        toolsLinearLayout.setVisibility(View.GONE);
        toolsUnFoldImageButton = (ImageButton) findViewById(R.id.estNew_toolsUnfoldImageButton);
        toolsFoldImageButton = (ImageButton) findViewById(R.id.estNew_toolsFoldImageButton);
        toolsFoldImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toolsFoldImageButton.setVisibility(View.INVISIBLE);
                toolsUnFoldImageButton.setVisibility(View.VISIBLE);
                toolsLinearLayout.setVisibility(View.GONE);
            }
        });

        toolsUnFoldImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toolsFoldImageButton.setVisibility(View.VISIBLE);
                toolsUnFoldImageButton.setVisibility(View.INVISIBLE);
                toolsLinearLayout.setVisibility(View.VISIBLE);
            }
        });

        //SETTING UP THE RECYCLERVIEW FOR ADDING NEW TOOLS&PLANTS
        toolsTotalTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                grandTotal = getGrandTotal();
                grandTotalTV.setText(BaseActivity.decimalFormat.format(grandTotal));
            }
        });
        toolsRecyclerView = (RecyclerView) findViewById(R.id.estNew_toolsRecycler);
        setToolsRecyclerAdapter();
        toolsRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, toolsRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                EstimationOtherMaterials editTools = toolsList.get(position);
                Intent editToolsIntent = new Intent(context, EstimationOtherMaterials.class);
                editToolsIntent.putExtra("ID", "EditTools");
                editToolsIntent.putExtra("ParcelEdit", editTools);
                editToolsIntent.putExtra("Title", "Tools");
                editToolsIntent.putExtra("Position", position);
                startActivityForResult(editToolsIntent, TOOL_EDIT);

            }

            @Override
            public void onLongClick(View view, final int position) {


                // SETTING UP CONFIRM DIALOGS FOR ADDING DIRECTMATERIAL
                new AlertDialog.Builder(Estimation.this).setMessage(R.string.confirmDelete).
                        setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                toolsList.remove(position);
                                setToolsRecyclerAdapter();
                                populateToolTotalTV();
                                populateJobsTotal();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create().show();
            }
        }));

        toolsRecyclerView.setAdapter(toolsRecyclerAdapter);

        //SETTING UP ADD BUTTON TO ADD TOOLS
        addToolsImageButton = (ImageButton) findViewById(R.id.estNew_toolsAddNewImageButton);
        addToolsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addToolsIntent = new Intent(context, EstimationOtherMaterials.class);
                addToolsIntent.putExtra("ID", "NewTools");
                addToolsIntent.putExtra("Title", "Tools");
                startActivityForResult(addToolsIntent, TOOL_NEW);
            }
        });

        // END OF TOOLS SECTION

        // START OF TRANSPORT SECTION
        //SETTING FOLDING AND UNFOLDING OF TOOLS VIEWS
        transportLinearLayout = (LinearLayout) findViewById(R.id.estNew_transportFoldLinearLayout);
        transportLinearLayout.setVisibility(View.GONE);
        transportUnfoldImageButton = (ImageButton) findViewById(R.id.estNew_transportUnfoldImageButton);
        transportFoldImageButton = (ImageButton) findViewById(R.id.estNew_transportFoldImageButton);
        transportFoldImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                transportFoldImageButton.setVisibility(View.INVISIBLE);
                transportUnfoldImageButton.setVisibility(View.VISIBLE);
                transportLinearLayout.setVisibility(View.GONE);
            }
        });

        transportUnfoldImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                transportFoldImageButton.setVisibility(View.VISIBLE);
                transportUnfoldImageButton.setVisibility(View.INVISIBLE);
                transportLinearLayout.setVisibility(View.VISIBLE);
            }
        });

        //SETTING UP ADD BUTTON TO ADD TRANSPORT
        addTransportImageButton = (ImageButton) findViewById(R.id.estNew_transportAddNewImageButton);
        addTransportImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTransportIntent = new Intent(context, EstimationOtherMaterials.class);
                addTransportIntent.putExtra("ID", "NewTransport");
                addTransportIntent.putExtra("Title", "Transport");
                startActivityForResult(addTransportIntent, TRANSPORT_NEW);
            }
        });


        //SETTING UP THE RECYCLER VIEW FOR ADDING NEW TRANSPORT
        transportTotalTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                grandTotal = getGrandTotal();
                grandTotalTV.setText(BaseActivity.decimalFormat.format(grandTotal));
            }
        });

        transportRecyclerView = (RecyclerView) findViewById(R.id.estNew_transportRecycler);
        setTransportRecyclerAdapter();
        transportRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, transportRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                estimationOtherMaterials = transportList.get(position);
                Intent transportEditIntent = new Intent(context, EstimationOtherMaterials.class);
                transportEditIntent.putExtra("ID", "EditTransport");
                transportEditIntent.putExtra("ParcelEdit", estimationOtherMaterials);
                transportEditIntent.putExtra("Title", "Transport");
                transportEditIntent.putExtra("Position", position);
                startActivityForResult(transportEditIntent, TRANSPORT_EDIT);
            }

            @Override
            public void onLongClick(View view, final int position) {

                new AlertDialog.Builder(Estimation.this).setMessage(R.string.confirmDelete).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        transportList.remove(position);
                        setTransportRecyclerAdapter();
                        populateTransportTV();
                        populateJobsTotal();
                    }
                }).setNegativeButton("No", null).create().show();
            }
        }));
        transportRecyclerView.setAdapter(transportRecyclerAdapter);

        //END OF TRANSPORT SECTION
        //START OF CONSUMABLE SECTION

        //SETTING FOLD AND UNFOLD OF CONSUMABLE SECTION
        consumableLinearLayout = (LinearLayout) findViewById(R.id.estNew_consumablesFoldLinearLayout);
        consumableLinearLayout.setVisibility(View.GONE);
        consumableUnfoldImageButton = (ImageButton) findViewById(R.id.estNew_consumablesUnfoldImageButton);
        consumableFoldImageButton = (ImageButton) findViewById(R.id.estNew_consumablesFoldImageButton);
        consumableFoldImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                consumableFoldImageButton.setVisibility(View.INVISIBLE);
                consumableUnfoldImageButton.setVisibility(View.VISIBLE);
                consumableLinearLayout.setVisibility(View.GONE);
            }
        });

        consumableUnfoldImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                consumableFoldImageButton.setVisibility(View.VISIBLE);
                consumableUnfoldImageButton.setVisibility(View.INVISIBLE);
                consumableLinearLayout.setVisibility(View.VISIBLE);
            }
        });

        //SETTING ADD IMAGE BUTTON TO ADD THE NEW CONSUMABLE
        addConsumableImageButton = (ImageButton) findViewById(R.id.estNew_consumablesAddNewImageButton);
        addConsumableImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newConsumableIntent = new Intent(context, EstimationConsumables.class);
                newConsumableIntent.putExtra("ID", "NewConsumable");
                newConsumableIntent.putExtra("Title", "Consumables");
                startActivityForResult(newConsumableIntent, CONSUMABLES_NEW);
            }
        });

        //SETTING UP THE RECYCLER VIEW OF THE CONSUMABLES
        consumableTotalTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                grandTotal = getGrandTotal();
                grandTotalTV.setText(BaseActivity.decimalFormat.format(grandTotal));
            }
        });

        consumableRecyclerView = (RecyclerView) findViewById(R.id.estNew_consumablesRecycler);
        setConsumablesRecyclerAdapter();
        consumableRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, consumableRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                estimationConsumables = consumableList.get(position);
                Intent editConsumableIntent = new Intent(context, EstimationConsumables.class);
                editConsumableIntent.putExtra("ID", "EditConsumable");
                editConsumableIntent.putExtra("Title", "Consumables");
                editConsumableIntent.putExtra("Position", position);
                editConsumableIntent.putExtra("ParcelEdit", estimationConsumables);
                startActivityForResult(editConsumableIntent, CONSUMABLES_EDIT);
            }

            @Override
            public void onLongClick(View view, final int position) {

                new AlertDialog.Builder(Estimation.this).setMessage(R.string.confirmDelete).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        consumableList.remove(position);
                        setConsumablesRecyclerAdapter();
                        populateConsumableTV();
                        populateJobsTotal();

                    }
                }).setNegativeButton("No", null).create().show();

            }
        }));
        consumableRecyclerView.setAdapter(consumableRecyclerAdapter);

        //END OF CONSUMABLES SECTION

    }

    public void sendEstimation() {
//        setEstimationToParcel();
//        callingIntent.putExtra("New Estimation", estimation);
//        setResult(EstimationFragment.RETURN_TRUE,callingIntent);
//        finish();
    }

    public float getGrandTotal() {

        overHeads = 0;
        grandTotal = 0;
        sumTotal = 0;
        margin = 0;
        overHeadPercentage = 0;
        marginPercentage = 0;
        directMaterialTotal = Float.valueOf(directMaterialTotalTV.getText().toString());
        manPowerTotal = Float.valueOf(manPowerTotalTV.getText().toString());
        toolsTotal = Float.valueOf(toolsTotalTV.getText().toString());
        transportTotal = Float.valueOf(transportTotalTV.getText().toString());
        consumablesTotal = Float.valueOf(consumableTotalTV.getText().toString());
        sumTotal = directMaterialTotal + manPowerTotal + toolsTotal + transportTotal + consumablesTotal;
        if (!TextUtils.isEmpty(overHeadsEditText.getText())) {
            if (!overHeadsEditText.getText().toString().equals(".")) {
                overHeadPercentage = Float.valueOf(overHeadsEditText.getText().toString());
            }
            overHeads = (sumTotal * overHeadPercentage) / 100;
        }
        grandTotal = sumTotal + overHeads;

        if (!TextUtils.isEmpty((marginEditText.getText()))) {
            if (!marginEditText.getText().toString().equals(".")) {
                marginPercentage = Float.valueOf(marginEditText.getText().toString());
            }

            margin = (grandTotal * marginPercentage) / 100;
        }

        grandTotal += margin;

        grandTotal = Float.valueOf(BaseActivity.decimalFormat.format(grandTotal));
//        grandTotal = Math.round(grandTotal*100)/100;

        return grandTotal;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        totalCount = 0;
        int editPosition;

        switch (resultCode) {

            case DIRECT_MATERIAL_NEW:

                estimationDirectMaterial = data.getParcelableExtra("DirectMaterial");
                estimationDirectMaterialList.add(estimationDirectMaterial);
                setDMRecyclerAdapter(); // Setting up Recycler View
                populateDMTotalTV();
                populateJobsTotal();

                break;

            case DIRECT_MATERIAL_EDIT:

                estimationDirectMaterial = data.getParcelableExtra("DirectMaterial");
                editPosition = data.getIntExtra("Position", 0);
                estimationDirectMaterialList.set(editPosition, estimationDirectMaterial);
                setDMRecyclerAdapter();
                populateDMTotalTV();
                populateJobsTotal();

                break;

            case MAN_POWER_NEW:

                estimationOtherMaterials = data.getParcelableExtra("OtherMaterial");
                manPowerList.add(estimationOtherMaterials);
                setManPowerRecyclerAdapter();
                populateManPowerTotalTV();
                populateJobsTotal();

                break;

            case MAN_POWER_EDIT:

                estimationOtherMaterials = data.getParcelableExtra("OtherMaterial");
                editPosition = data.getIntExtra("Position", 0);
                manPowerList.set(editPosition, estimationOtherMaterials);
                setManPowerRecyclerAdapter();
                populateManPowerTotalTV();
                populateJobsTotal();

                break;

            case TOOL_NEW:

                estimationOtherMaterials = data.getParcelableExtra("Tools");
                toolsList.add(estimationOtherMaterials);
                setToolsRecyclerAdapter();
                populateToolTotalTV();
                populateJobsTotal();

                break;

            case TOOL_EDIT:

                estimationOtherMaterials = data.getParcelableExtra("Tools");
                editPosition = data.getIntExtra("Position", 0);
                toolsList.set(editPosition, estimationOtherMaterials);
                setToolsRecyclerAdapter();
                populateToolTotalTV();
                populateJobsTotal();

                break;

            case TRANSPORT_NEW:

                estimationOtherMaterials = data.getParcelableExtra("Transport");
                transportList.add(estimationOtherMaterials);
                setTransportRecyclerAdapter();
                populateTransportTV();
                populateJobsTotal();

                break;

            case TRANSPORT_EDIT:

                estimationOtherMaterials = data.getParcelableExtra("Transport");
                editPosition = data.getIntExtra("Position", 0);
                transportList.set(editPosition, estimationOtherMaterials);
                setTransportRecyclerAdapter();
                populateTransportTV();
                populateJobsTotal();

                break;

            case CONSUMABLES_NEW:
                estimationConsumables = data.getParcelableExtra("Consumable");
                consumableList.add(estimationConsumables);
                setConsumablesRecyclerAdapter();
                populateConsumableTV();
                populateJobsTotal();

                break;

            case CONSUMABLES_EDIT:
                estimationConsumables = data.getParcelableExtra("Consumable");
                editPosition = data.getIntExtra("Position", 0);
                consumableList.set(editPosition, estimationConsumables);
                setConsumablesRecyclerAdapter();
                populateConsumableTV();
                populateJobsTotal();

                break;

        }
    }

    public void setDMRecyclerAdapter() {
        estimationDirectMaterialRecyclerAdapter = new EstimationDirectMaterialRecyclerAdapter(estimationDirectMaterialList, context, ESTIMATION_NEW_ADAPTER);
        directMaterialRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        directMaterialRecyclerView.setItemAnimator(new DefaultItemAnimator());
        directMaterialRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(context));
        directMaterialRecyclerView.setAdapter(estimationDirectMaterialRecyclerAdapter);
    }

    public void setManPowerRecyclerAdapter() {
        manPowerRecyclerAdapter = new EstimationOtherMaterialRecyclerAdapter(context, manPowerList, ESTIMATION_NEW_ADAPTER);
        manPowerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        manPowerRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(context));
        manPowerRecyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    public void setToolsRecyclerAdapter() {
        toolsRecyclerAdapter = new EstimationOtherMaterialRecyclerAdapter(context, toolsList, ESTIMATION_NEW_ADAPTER);
        toolsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        toolsRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(context));
        toolsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public void setTransportRecyclerAdapter() {
        transportRecyclerAdapter = new EstimationOtherMaterialRecyclerAdapter(context, transportList, ESTIMATION_NEW_ADAPTER);
        transportRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        transportRecyclerView.setItemAnimator(new DefaultItemAnimator());
        transportRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(context));
    }

    public void setConsumablesRecyclerAdapter() {
        consumableRecyclerAdapter = new EstimationConsumableRecyclerAdapter(context, consumableList, ESTIMATION_NEW_ADAPTER);
        consumableRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        consumableRecyclerView.setItemAnimator(new DefaultItemAnimator());
        consumableRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(context));
    }

    public void populateDMTotalTV() {

        totalCount = 0;
        for (int x = 0; x < estimationDirectMaterialList.size(); x++) {
            estimationDirectMaterial = estimationDirectMaterialList.get(x);
            totalCount = totalCount + estimationDirectMaterial.materialTotal;
        }
        directMaterialTotalTV.setText(BaseActivity.decimalFormat.format(totalCount));

    }

    public void populateToolTotalTV() {

        totalCount = 0;
        for (int x = 0; x < toolsList.size(); x++) {
            estimationOtherMaterials = toolsList.get(x);
            totalCount = totalCount + estimationOtherMaterials.otherMaterialTotal;
        }
        toolsTotalTV.setText(BaseActivity.decimalFormat.format(totalCount));
    }

    public void populateManPowerTotalTV() {

        totalCount = 0;
        for (int x = 0; x < manPowerList.size(); x++) {
            estimationOtherMaterials = manPowerList.get(x);
            totalCount = totalCount + estimationOtherMaterials.otherMaterialTotal;
        }
        manPowerTotalTV.setText(BaseActivity.decimalFormat.format(totalCount));
    }

    public void populateTransportTV() {

        totalCount = 0;
        for (int x = 0; x < transportList.size(); x++) {
            estimationOtherMaterials = transportList.get(x);
            totalCount = totalCount + estimationOtherMaterials.otherMaterialTotal;
        }
        transportTotalTV.setText(BaseActivity.decimalFormat.format(totalCount));
    }

    public void populateConsumableTV() {

        totalCount = 0;
        for (int x = 0; x < consumableList.size(); x++) {
            estimationConsumables = consumableList.get(x);
            totalCount = totalCount + estimationConsumables.consumableTotal;
        }
        consumableTotalTV.setText(BaseActivity.decimalFormat.format(totalCount));
    }

    public void populateJobsTotal() {

        for (int x = 0; x < jobsList.size(); x++) {
            estimationJobs = jobsList.get(x);
            String jobNameRef = estimationJobs.jobName;
            float jobTotal = 0;
            for (int z = 0; z < estimationDirectMaterialList.size(); z++) {
                estimationDirectMaterial = estimationDirectMaterialList.get(z);
                if (jobNameRef.equals(estimationDirectMaterial.jobName)) {
                    jobTotal += estimationDirectMaterial.materialTotal;
                }
            }

            for (int z = 0; z < manPowerList.size(); z++) {
                estimationOtherMaterials = manPowerList.get(z);
                if (jobNameRef.equals(estimationOtherMaterials.jobName)) {
                    jobTotal += estimationOtherMaterials.otherMaterialTotal;
                }
            }

            for (int z = 0; z < toolsList.size(); z++) {
                estimationOtherMaterials = toolsList.get(z);
                if (jobNameRef.equals(estimationOtherMaterials.jobName)) {
                    jobTotal += estimationOtherMaterials.otherMaterialTotal;
                }
            }

            for (int z = 0; z < transportList.size(); z++) {
                estimationOtherMaterials = transportList.get(z);
                if (jobNameRef.equals(estimationOtherMaterials.jobName)) {
                    jobTotal += estimationOtherMaterials.otherMaterialTotal;
                }

            }

            for (int z = 0; z < consumableList.size(); z++) {
                estimationConsumables = consumableList.get(z);
                if (jobNameRef.equals(estimationConsumables.jobName)) {
                    jobTotal += estimationConsumables.consumableTotal;
                }
            }

            overHeads = 0;
            if (!TextUtils.isEmpty(overHeadsEditText.getText())) {
                if (!overHeadsEditText.getText().toString().equals(".")) {
                    overHeads = Float.valueOf(overHeadsEditText.getText().toString());
                }
            }
            jobTotal = jobTotal + (overHeads * jobTotal) / 100;

            margin = 0;
            if (!TextUtils.isEmpty(marginEditText.getText())) {
                if (!marginEditText.getText().toString().equals(".")) {
                    margin = (Float.valueOf(marginEditText.getText().toString()));
                }
            }
            jobTotal = jobTotal + (margin * jobTotal) / 100;

            jobTotal = Float.valueOf(BaseActivity.decimalFormat.format(jobTotal));

            estimationJobs.setJobTotal(jobTotal);
            TextView jobTotalTv = (TextView) jobTotalLinearLayout.findViewWithTag(jobNameRef + "TV");
            jobTotalTv.setText(BaseActivity.decimalFormat.format(jobTotal));

        }
    }


    public void setEstimationToParcel() {

        estimation.setEstimationCreateDate(estimationCreateDate);
        estimation.setEstimationLastEditDate(estimationLastEditDate);
        estimation.setEstimationCreateTime(estimationCreateTime);
        estimation.setEstimationLastEditTime(estimationLastEditTime);

        estimation.setClientAccount(clientAccount);
        estimation.setEstimationID(estimationID);
        estimation.setDirectMaterialTotal(directMaterialTotal);
        estimation.setEstimationDirectMaterialList(estimationDirectMaterialList);

        estimation.setManPowerTotal(manPowerTotal);
        estimation.setManPowerList(manPowerList);

        estimation.setToolsTotal(toolsTotal);
        estimation.setToolsList(toolsList);

        estimation.setTransportTotal(transportTotal);
        estimation.setTransportList(transportList);

        estimation.setConsumablesTotal(consumablesTotal);
        estimation.setConsumableList(consumableList);

        estimation.setGrandTotal(grandTotal);
        estimation.setOverHeads(overHeads);
        estimation.setOverHeadPercentage(overHeadPercentage);
        estimation.setMarginPercentage(marginPercentage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_estimation_new, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_EstimationNew_jobEdit:
                FragmentManager fragmentManager = getSupportFragmentManager();
                EstimationJobEditDialog estimationJobEditDialog = new EstimationJobEditDialog();
                estimationJobEditDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomFullScreenDialog);
                estimationJobEditDialog.show(fragmentManager, "EditJob");
                break;

            // SAVING ESTIMATION
            case R.id.action_EstimationNew_save:
                EstimationDatabaseAdapter estimationDatabaseAdapter = new EstimationDatabaseAdapter(context);

                setEstimationToParcel();// Need to set estimation object to all the current values

                String errorFlag = "Success";
                progressDialog = ProgressDialog.show(Estimation.this, "Saving the Estimation", "Please Wait", true);

                // DELETING FIRST THE EXISTING TABLE WITH THIS ID

                int deleteResultKey = estimationDatabaseAdapter.deleteEstimation(estimationID);
                if (deleteResultKey < 0) {
                    Toast.makeText(Estimation.this, "No value to delete", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(Estimation.this, "Successfully Deleted the records", Toast.LENGTH_SHORT).show();
                }
                // SAVING ESTIMATION BASE DETAILS
                long resultKey = estimationDatabaseAdapter.insertBaseData(estimation);
                if (resultKey < 0) {
                    errorFlag = "Error";
                    Toast.makeText(Estimation.this, "Error Inserting the Values", Toast.LENGTH_SHORT).show();
                } else {
                }

                // SAVING JOB DETAILS
                long jobResultKey = estimationDatabaseAdapter.insertJobTable(jobsList, estimationID);
                if (jobResultKey < 0) {
                    errorFlag = "Error";
                    Toast.makeText(Estimation.this, "Error Inserting the Values", Toast.LENGTH_SHORT).show();
                } else {
                }

                // SAVING DIRECT MATERIALS
                if (estimationDirectMaterialList.size() > 0) {
                    long DMResultKey = estimationDatabaseAdapter.insertDMData(estimationDirectMaterialList, estimationID);
                    if (DMResultKey < 0) {
                        errorFlag = "Error";
                        Toast.makeText(Estimation.this, "Error Inserting the Values", Toast.LENGTH_SHORT).show();
                    } else {
                    }

                }

                // SAVING MANPOWER DETAILS
                if (manPowerList.size() > 0) {
                    long MPResultKey = estimationDatabaseAdapter.insertMPTable(manPowerList, estimationID);
                    if (MPResultKey < 0) {
                        errorFlag = "Error";
                        Toast.makeText(Estimation.this, "Error Inserting the Values", Toast.LENGTH_SHORT).show();
                    } else {
                    }
                }

                // SAVING TOOLS DETAILS
                if (toolsList.size() > 0) {
                    long toolsResultKey = estimationDatabaseAdapter.insertToolsTable(toolsList, estimationID);
                    if (toolsResultKey < 0) {
                        errorFlag = "Error";
                        Toast.makeText(Estimation.this, "Error Inserting the Values", Toast.LENGTH_SHORT).show();
                    } else {
                    }
                }

                // SAVING TRANSPORT SECTION
                if (transportList.size() > 0) {
                    long transportResultKey = estimationDatabaseAdapter.insertTransportTable(transportList, estimationID);
                    if (transportResultKey < 0) {
                        errorFlag = "Error";
                        Toast.makeText(Estimation.this, "Error Inserting the Values", Toast.LENGTH_SHORT).show();
                    } else {
                    }
                }

                // SAVING CONSUMABLE SECTION
                if (consumableList.size() > 0) {
                    long consumableResultKey = estimationDatabaseAdapter.insertConsumableData(consumableList, estimationID);
                    if (consumableResultKey < 0) {
                        errorFlag = "Error";
                        Toast.makeText(Estimation.this, "Error Inserting the Values", Toast.LENGTH_SHORT).show();
                    } else {
                    }
                }
                progressDialog.cancel();
                if (errorFlag.equals("Error")) {
                    Toast.makeText(Estimation.this, "Error Saving Estimation", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Estimation.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
            // END OF ESTIMATION SAVE

            //CONVERT ESTIMATION TO QUOTATION
            case R.id.action_EstimationNew_download:
                setEstimationToParcel();
                EstimationSaveToExcel saveToExcel = new EstimationSaveToExcel();
                saveToExcel.getExcel(context, estimation);
                break;


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (backCount == 1) {
            backCount = 0;
            setResult(EstimationFragment.RETURN_FALSE, callingIntent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Press Back again to exit page", Toast.LENGTH_SHORT).show();
            backCount++;
            new CountDownTimer(3000, 1000) {

                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    backCount = 0;
                }
            }.start();
        }

        return;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeFloat(this.grandTotal);
        dest.writeFloat(this.directMaterialTotal);
        dest.writeFloat(this.manPowerTotal);
        dest.writeFloat(this.toolsTotal);
        dest.writeFloat(this.transportTotal);
        dest.writeFloat(this.consumablesTotal);
        dest.writeFloat(this.overHeads);
        dest.writeFloat(this.overHeadPercentage);
        dest.writeFloat(this.marginPercentage);
        dest.writeString(this.estimationID);
        dest.writeString(this.clientAccount);
        dest.writeString(this.estimationCreateDate);
        dest.writeString(this.estimationLastEditDate);
        dest.writeString(this.estimationCreateTime);
        dest.writeString(this.estimationLastEditTime);

        dest.writeTypedList(this.estimationDirectMaterialList);
        dest.writeTypedList(this.manPowerList);
        dest.writeTypedList(this.toolsList);
        dest.writeTypedList(this.transportList);
        dest.writeTypedList(this.consumableList);
        dest.writeTypedList(this.jobsList);
    }

    public Estimation() {
    }

    protected Estimation(Parcel in) {
        this.grandTotal = in.readFloat();
        this.directMaterialTotal = in.readFloat();
        this.manPowerTotal = in.readFloat();
        this.toolsTotal = in.readFloat();
        this.transportTotal = in.readFloat();
        this.consumablesTotal = in.readFloat();
        this.overHeads = in.readFloat();
        this.overHeadPercentage = in.readFloat();
        this.marginPercentage = in.readFloat();
        this.estimationID = in.readString();
        this.clientAccount = in.readString();
        this.estimationCreateDate = in.readString();
        this.estimationLastEditDate = in.readString();
        this.estimationCreateTime = in.readString();
        this.estimationLastEditTime = in.readString();

        this.estimationDirectMaterialList = in.createTypedArrayList(EstimationDirectMaterial.CREATOR);
        this.manPowerList = in.createTypedArrayList(EstimationOtherMaterials.CREATOR);
        this.toolsList = in.createTypedArrayList(EstimationOtherMaterials.CREATOR);
        this.transportList = in.createTypedArrayList(EstimationOtherMaterials.CREATOR);
        this.consumableList = in.createTypedArrayList(EstimationConsumables.CREATOR);
        this.jobsList = in.createTypedArrayList(EstimationJobs.CREATOR);

    }

    public static final Parcelable.Creator<Estimation> CREATOR = new Parcelable.Creator<Estimation>() {
        @Override
        public Estimation createFromParcel(Parcel source) {
            return new Estimation(source);
        }

        @Override
        public Estimation[] newArray(int size) {
            return new Estimation[size];
        }
    };


    //METHOD TO CREATE THE NEW DIALOG FRAGMENT FOR ADDING NEW JOBS

    public static void callJobDialogFragment() {


        EstimationJobDialog estimationJobDialog = new EstimationJobDialog();
        estimationJobDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        estimationJobDialog.show(fragmentManager, "JobDialog");

    }


    // METHOD TO SET THE JOB LAYOUTS DURING THE EDIT
    public void setJobsOnEdit() {

        for (int i = 0; i < jobsList.size(); i++) {
            estimationJobs = jobsList.get(i);
            jobNames.add(estimationJobs.jobName);
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View jobTotalView = layoutInflater.inflate(R.layout.estimation_new_jobrow, null);
            jobTotalView.setTag(estimationJobs.jobName);
            TextView jobLabelTV = (TextView) jobTotalView.findViewById(R.id.jobRow_TotalLabelTV);
            jobLabelTV.setTag(estimationJobs.jobName + "Label");
            TextView jobTotalTV = (TextView) jobTotalView.findViewById(R.id.jobRow_TotalTV);
            jobTotalTV.setTag(estimationJobs.jobName + "TV");
            TextView jobTotalDenomTV = (TextView) jobTotalView.findViewById(R.id.jobRow_denominationTV);
            jobLabelTV.setText(estimationJobs.jobName + " total");
            jobTotalTV.setText(BaseActivity.decimalFormat.format(estimationJobs.jobTotal));
            jobTotalLinearLayout.addView(jobTotalView);
        }
    }

    //OVERRIDDEN METHOD FROM JOBDIALOG FRAGMENT INTERFACE COMMUNICATOR
    @Override
    public void setNewJob(String jobName, String jobUnit, String jobQuantity) {

        estimationJobs = new EstimationJobs();
        jobNames.add(jobName);

        estimationJobs.setJobName(jobName);
        estimationJobs.setJobUnit(jobUnit);
        estimationJobs.setJobQuantity(Float.valueOf(jobQuantity));
        estimationJobs.setJobTotal(0);
        jobsList.add(estimationJobs);

        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View jobTotalView = layoutInflater.inflate(R.layout.estimation_new_jobrow, null);
        jobTotalView.setTag(jobName);
        TextView jobLabelTV = (TextView) jobTotalView.findViewById(R.id.jobRow_TotalLabelTV);
        jobLabelTV.setTag(jobName + "Label");
        TextView jobTotalTV = (TextView) jobTotalView.findViewById(R.id.jobRow_TotalTV);
        TextView jobTotalDenomTV = (TextView) jobTotalView.findViewById(R.id.jobRow_denominationTV);
        jobLabelTV.setText(jobName + " total");
        jobTotalTV.setTag(jobName + "TV");
        jobTotalLinearLayout.addView(jobTotalView);

    }

    // OVERRIDDEN METHOD FROM JOBDIALOG FRAGMENT TO EXIT THE ACTIVITY IF THE JOB IS NOT ADDED
    @Override
    public void exitNewEstimation() {
        if (jobsList.size() <= 0) {
            setResult(EstimationFragment.RETURN_FALSE, callingIntent);
            finish();
        }
    }

    @Override
    public void setEditJob(String oldJobName, String newJobName) {
        for (int x = 0; x < estimationDirectMaterialList.size(); x++) {
            estimationDirectMaterial = estimationDirectMaterialList.get(x);
            if (estimationDirectMaterial.jobName.equals(oldJobName)) {
                estimationDirectMaterial.setJobName(newJobName);
            }
            setDMRecyclerAdapter();
        }
        for (int x = 0; x < manPowerList.size(); x++) {
            estimationOtherMaterials = manPowerList.get(x);
            if (estimationOtherMaterials.jobName.equals(oldJobName)) {
                estimationOtherMaterials.setJobName(newJobName);
            }
            setManPowerRecyclerAdapter();
        }
        for (int x = 0; x < toolsList.size(); x++) {
            estimationOtherMaterials = toolsList.get(x);
            if (estimationOtherMaterials.jobName.equals(oldJobName)) {
                estimationOtherMaterials.setJobName(newJobName);
            }
            setToolsRecyclerAdapter();
        }
        for (int x = 0; x < transportList.size(); x++) {
            estimationOtherMaterials = transportList.get(x);
            if (estimationOtherMaterials.jobName.equals(oldJobName)) {
                estimationOtherMaterials.setJobName(newJobName);
            }
            setTransportRecyclerAdapter();
        }
        for (int x = 0; x < consumableList.size(); x++) {
            estimationConsumables = consumableList.get(x);
            if (estimationConsumables.jobName.equals(oldJobName)) {
                estimationConsumables.setJobName(newJobName);
            }
            setConsumablesRecyclerAdapter();
        }
    }

    @Override
    public void setDeleteJob(String jobName) {
        for (int x = 0; x < estimationDirectMaterialList.size(); x++) {
            estimationDirectMaterial = estimationDirectMaterialList.get(x);
            if (estimationDirectMaterial.jobName.equals(jobName)) {
                estimationDirectMaterialList.remove(x);
            }
        }
        setDMRecyclerAdapter();
        populateDMTotalTV();

        for (int x = 0; x < manPowerList.size(); x++) {
            estimationOtherMaterials = manPowerList.get(x);
            if (estimationOtherMaterials.jobName.equals(jobName)) {
                manPowerList.remove(x);
            }
        }
        setManPowerRecyclerAdapter();
        populateManPowerTotalTV();

        for (int x = 0; x < toolsList.size(); x++) {
            estimationOtherMaterials = toolsList.get(x);
            if (estimationOtherMaterials.jobName.equals(jobName)) {
                toolsList.remove(x);
            }
        }
        setToolsRecyclerAdapter();
        populateToolTotalTV();

        for (int x = 0; x < transportList.size(); x++) {
            estimationOtherMaterials = transportList.get(x);
            if (estimationOtherMaterials.jobName.equals(jobName)) {
                transportList.remove(x);
            }
        }
        setTransportRecyclerAdapter();
        populateTransportTV();

        for (int x = 0; x < consumableList.size(); x++) {
            estimationConsumables = consumableList.get(x);
            if (estimationConsumables.jobName.equals(jobName)) {
                consumableList.remove(x);
            }
        }
        setConsumablesRecyclerAdapter();
        populateConsumableTV();
    }

}
