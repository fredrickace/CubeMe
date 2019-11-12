package com.cube_me.cubeme_crm.Inquiry;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.DatePickerFragment;
import com.cube_me.cubeme_crm.MultipleSelectionSpinner;
import com.cube_me.cubeme_crm.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class InquiryFilterDialog extends DialogFragment {


    CheckBox selectAllCB;
    Button addFilterButton;
    FilterCommunicator filterCommunicator;

    LinearLayout startDateLL;
    CheckBox startDateCB;
    TextView startDateTV;
    ImageButton startDateIB;
    LinearLayout endDateLL;
    CheckBox endDateCB;
    TextView endDateTV;
    ImageButton endDateIB;
    LinearLayout clientNameLL;
    CheckBox clientNameCB;
    MultipleSelectionSpinner clientNameMultiSpinner;
    ImageButton clientnameIB;
    TextView clientNameTV;
    LinearLayout inquiryStatusLL;
    CheckBox inquiryStatusCB;
    MultipleSelectionSpinner inquiryStatusMultiSpinner;
    ImageButton inquiryStatusIB;
    TextView inquiryStatusTV;
    LinearLayout assignedToLL;
    CheckBox assignedToCB;
    MultipleSelectionSpinner assignedToMultiSpinner;
    ImageButton assignedToIB;
    TextView assignedToTV;


    String startDate;
    String endDate;
    String[] inquiryStatusData;
    List<String> inquiryStatusSelectedList;
    String[] clientNameData;
    List<String> clientNameSelectedList;
    String[] assignedToData;
    List<String> assignedToSelectedList;


    public InquiryFilterDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.inquiry_filter_dialog, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        startDate = "";
        endDate = "";
        inquiryStatusSelectedList = new ArrayList<>();
        clientNameSelectedList = new ArrayList<>();
        assignedToSelectedList = new ArrayList<>();


        //BASIC INIT
        selectAllCB = (CheckBox) getView().findViewById(R.id.inquiryFilter_selectAllCB);
        startDateLL = (LinearLayout) getView().findViewById(R.id.inquiryFilter_StartDateLL);
        startDateCB = (CheckBox) getView().findViewById(R.id.inquiryFilter_startDateCB);
        startDateIB = (ImageButton) getView().findViewById(R.id.inquiryFilter_StartDateImgButton);
        startDateTV = (TextView) getView().findViewById(R.id.inquiryFilter_StartDateTV);
        endDateLL = (LinearLayout) getView().findViewById(R.id.inquiryFilter_EndDateLL);
        endDateCB = (CheckBox) getView().findViewById(R.id.inquiryFilter_EndDateCB);
        endDateTV = (TextView) getView().findViewById(R.id.inquiryFilter_EndDateTV);
        endDateIB = (ImageButton) getView().findViewById(R.id.inquiryFilter_EndDateImgButton);
        inquiryStatusLL = (LinearLayout) getView().findViewById(R.id.inquiryFilter_inquiryStatusLL);
        inquiryStatusCB = (CheckBox) getView().findViewById(R.id.inquiryFilter_inquiryStatusCB);
        addFilterButton = (Button) getView().findViewById(R.id.inquiryfilter_addFilterButton);


        //INQUIRY STATUS MULTI SELECT SPINNER SELECTION
        inquiryStatusData = getActivity().getResources().getStringArray(R.array.inquiry_status);
        inquiryStatusCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    inquiryStatusLL.setVisibility(View.VISIBLE);
                } else {
                    inquiryStatusLL.setVisibility(View.GONE);
                    inquiryStatusSelectedList = new ArrayList<>();
                    inquiryStatusTV.setText("");
                    selectAllCB.setText("Select All");
                    selectAllCB.setChecked(false);
                }
            }
        });

        inquiryStatusTV = (TextView) getView().findViewById(R.id.inquiryFilter_inquiryStatusTV);
        inquiryStatusIB = (ImageButton) getView().findViewById(R.id.inquiryFilter_inquiryStatusIB);
        inquiryStatusMultiSpinner = new MultipleSelectionSpinner(getContext());
        inquiryStatusMultiSpinner.setItems(inquiryStatusData);
        inquiryStatusMultiSpinner.spinnerCallBack(new MultipleSelectionSpinner.SpinnerCommunication() {
            @Override
            public void getSpinnerSelectedItems() {
                inquiryStatusSelectedList = inquiryStatusMultiSpinner.getSelectedItemsAsString();
                inquiryStatusTV.setText(buildStringForTV(inquiryStatusSelectedList));
            }
        });

        inquiryStatusIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inquiryStatusMultiSpinner.setSelection(inquiryStatusSelectedList);
                inquiryStatusMultiSpinner.performClick();
            }
        });


        //START DATE SECTION
        startDateCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    startDateLL.setVisibility(View.VISIBLE);


                } else {
                    startDateLL.setVisibility(View.GONE);
                    startDate = "";
                    startDateTV.setText(startDate);
                    selectAllCB.setText("Select All");
                    selectAllCB.setChecked(false);
                }
            }
        });
        startDateIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatePickerFragment startDateFragment = new DatePickerFragment();
                startDateFragment.setCallBack(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        startDate = i2 + "/" + BaseActivity.MonthName(i1) + "/" + i;
                        startDateTV.setText(startDate);
                    }
                });
                startDateFragment.show(getFragmentManager(), "StartDate");
            }
        });

        //END DATE SECTION
        endDateCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    endDateLL.setVisibility(View.VISIBLE);
                } else {
                    endDateLL.setVisibility(View.GONE);
                    endDate = "";
                    endDateTV.setText(endDate);
                    selectAllCB.setText("Select All");
                    selectAllCB.setChecked(false);
                }
            }
        });
        endDateIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment endDateFragment = new DatePickerFragment();
                endDateFragment.setCallBack(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        endDate = i2 + "/" + BaseActivity.MonthName(i1) + "/" + i;
                        endDateTV.setText(endDate);
                    }
                });
                endDateFragment.show(getFragmentManager(), "EndDate");
            }
        });

        //CLIENT NAME SECTION
        clientNameData = getActivity().getResources().getStringArray(R.array.accounts_name);
        clientNameLL = (LinearLayout) getView().findViewById(R.id.inquiryFilter_clientNameLL);
        clientNameCB = (CheckBox) getView().findViewById(R.id.inquiryFilter_clientNameCB);
        clientNameTV = (TextView) getView().findViewById(R.id.inquiryFilter_clientNameTV);
        clientnameIB = (ImageButton) getView().findViewById(R.id.inquiryFilter_clientNameIB);
        clientNameCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    clientNameLL.setVisibility(View.VISIBLE);
                } else {
                    clientNameLL.setVisibility(View.GONE);
                    clientNameTV.setText("");
                    clientNameSelectedList = new ArrayList<>();
                    selectAllCB.setText("Select All");
                    selectAllCB.setChecked(false);
                }
            }
        });
        clientNameMultiSpinner = new MultipleSelectionSpinner(getContext());
        clientNameMultiSpinner.setItems(clientNameData);
        clientNameMultiSpinner.spinnerCallBack(new MultipleSelectionSpinner.SpinnerCommunication() {
            @Override
            public void getSpinnerSelectedItems() {
                clientNameSelectedList = clientNameMultiSpinner.getSelectedItemsAsString();
                clientNameTV.setText(buildStringForTV(clientNameSelectedList));

            }
        });

        clientnameIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientNameMultiSpinner.setSelection(clientNameSelectedList);
                clientNameMultiSpinner.performClick();
            }
        });

        //ASSIGNED TO SECTION
        assignedToLL = (LinearLayout) getView().findViewById(R.id.inquiryFilter_assignedToLL);
        assignedToCB = (CheckBox) getView().findViewById(R.id.inquiryFilter_assignedToCB);
        assignedToTV = (TextView) getView().findViewById(R.id.inquiryFilter_assignedToTV);
        assignedToIB = (ImageButton) getView().findViewById(R.id.inquiryFilter_assignedToIB);
        assignedToMultiSpinner = new MultipleSelectionSpinner(getContext());
        assignedToData = getActivity().getResources().getStringArray(R.array.contact_name);
        assignedToMultiSpinner.setItems(assignedToData);
        assignedToCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    assignedToLL.setVisibility(View.VISIBLE);
                } else {
                    assignedToLL.setVisibility(View.GONE);
                    assignedToSelectedList = new ArrayList<>();
                    assignedToTV.setText("");
                    selectAllCB.setText("Select All");
                    selectAllCB.setChecked(false);
                }
            }
        });
        assignedToIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assignedToMultiSpinner.spinnerCallBack(new MultipleSelectionSpinner.SpinnerCommunication() {
                    @Override
                    public void getSpinnerSelectedItems() {
                        assignedToSelectedList = assignedToMultiSpinner.getSelectedItemsAsString();
                        assignedToTV.setText(buildStringForTV(assignedToSelectedList));

                    }
                });
                assignedToMultiSpinner.setSelection(assignedToSelectedList);
                assignedToMultiSpinner.performClick();
            }
        });


        //SELECTING ALL FUNCTION
        selectAllCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    startDateCB.setChecked(true);
//                    startDateCB.setClickable(false);
                    endDateCB.setChecked(true);
//                    endDateCB.setClickable(false);
                    clientNameCB.setChecked(true);
//                    clientNameCB.setClickable(false);
                    inquiryStatusCB.setChecked(true);
//                    inquiryStatusCB.setClickable(false);
                    assignedToCB.setChecked(true);
//                    assignedToCB.setClickable(false);
                    selectAllCB.setText("Unselect All");
                } else {
                    if (selectAllCB.getText().equals("Unselect All")) {
                        startDateCB.setChecked(false);
//                    startDateCB.setClickable(true);
                        endDateCB.setChecked(false);
//                    endDateCB.setClickable(true);
                        clientNameCB.setChecked(false);
//                    clientNameCB.setClickable(true);
                        inquiryStatusCB.setChecked(false);
//                    inquiryStatusCB.setClickable(true);
                        assignedToCB.setChecked(false);
//                    assignedToCB.setClickable(true);
                        selectAllCB.setText("Select All");
                    }

                }
            }
        });

        //GETTING VALUES FROM BASE ACTIVITY
        Bundle bundle = getArguments();
        startDate = bundle.getString("StartDate");
        endDate = bundle.getString("EndDate");

        inquiryStatusSelectedList = (ArrayList<String>) bundle.getSerializable("StatusList");
        clientNameSelectedList = (ArrayList) getArguments().getSerializable("ClientList");
        assignedToSelectedList = (ArrayList) getArguments().getSerializable("AssignedList");

        if (!startDate.isEmpty()) {
            startDateCB.setChecked(true);
            startDateTV.setText(startDate);
        }
        if (!endDate.isEmpty()) {
            endDateCB.setChecked(true);
            endDateTV.setText(endDate);
        }
        if (!inquiryStatusSelectedList.isEmpty()) {
            inquiryStatusCB.setChecked(true);
            inquiryStatusTV.setText(buildStringForTV(inquiryStatusSelectedList));
        }
        if (!clientNameSelectedList.isEmpty()) {
            clientNameCB.setChecked(true);
            clientNameTV.setText(buildStringForTV(clientNameSelectedList));
        }
        if (!assignedToSelectedList.isEmpty()) {
            assignedToCB.setChecked(true);
            assignedToTV.setText(buildStringForTV(assignedToSelectedList));
        }

        //ADD FILTER SECTION
        addFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterCommunicator.getStartDate(startDate);
                filterCommunicator.getEndDate(endDate);
                filterCommunicator.getClientNameList(clientNameSelectedList);
                filterCommunicator.getInquiryStatusList(inquiryStatusSelectedList);
                filterCommunicator.getAssignedToList(assignedToSelectedList);
                filterCommunicator.filter();
                dismiss();

            }
        });
        //END OF ONACTIVITYCREATED
    }

    public StringBuilder buildStringForTV(List<String> dataList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dataList.size(); i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(dataList.get(i));
        }
        return sb;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void setCallBack(FilterCommunicator filterCommunicator) {
        this.filterCommunicator = filterCommunicator;
    }


    public interface FilterCommunicator {
        public void getStartDate(String startDate);

        public void getEndDate(String endDate);

        public void getClientNameList(List<String> clientNameList);

        public void getInquiryStatusList(List<String> inquiryStatusList);

        public void getAssignedToList(List<String> assignedToList);

        public void filter();
    }
}
