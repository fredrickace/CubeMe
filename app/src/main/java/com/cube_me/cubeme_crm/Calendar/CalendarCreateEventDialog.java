package com.cube_me.cubeme_crm.Calendar;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.DatePickerFragment;
import com.cube_me.cubeme_crm.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarCreateEventDialog extends DialogFragment {


    EditText eventTitleET;
    EditText eventDateET;
    Spinner eventClientSpinner;
    EditText eventDescriptionET;
    EditText eventReportsET;
    Button eventAddButton;
    CalendarEvent calendarEvent;
    CalendarEventCommunicator communicator;
    Calendar calendar;

    public CalendarCreateEventDialog() {
        // REQUIRED EMPTY PUBLIC CONSTRUCTOR
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        View view = inflater.inflate(R.layout.calendar_create_event_dialog, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // GETTING THE CALENDAR INSTANCE AND GETTING THE CURRENT DATE
        calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        String dateString = date+"/"+month+"/"+year;

        // BASE INIT
        eventDescriptionET = (EditText) getView().findViewById(R.id.calendarEventNew_DescET);
        eventReportsET = (EditText) getView().findViewById(R.id.calendarEventNew_ReportsET);
        eventTitleET = (EditText)getView().findViewById(R.id.calendarEventNew_titleET);
        eventDateET = (EditText) getView().findViewById(R.id.calendarEventNew_DateET);
        eventAddButton = (Button) getView().findViewById(R.id.calendarEventNew_addButton);
        eventDateET.setText(dateString);

        // SETTING THE DATE EDITTEXT TO PICK THE DATE FROM CALENDAR
        eventDateET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                 pickDate();
                }

            }
        });

        eventDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate();
            }
        });

        eventAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarEvent = new CalendarEvent();

                if(BaseActivity.ifEditTextNotEmptyErrMsg(eventTitleET)){
                    calendarEvent.setEventTitle(eventTitleET.getText().toString());
                    calendarEvent.setEventDate(eventDateET.getText().toString());
                    calendarEvent.setEventDescription(eventDescriptionET.getText().toString());
                    calendarEvent.setEventClientName(eventClientSpinner.getSelectedItem().toString());
                    communicator.addNewCalendarEvent(calendarEvent);
                    dismiss();
                }

            }
        });



        eventClientSpinner = (Spinner)getView().findViewById(R.id.calendarEventNew_ClientSpinner);
        ArrayAdapter clientNameAdapter = ArrayAdapter.createFromResource(getContext(),R.array.accounts_name,R.layout.spinner_layout);
        clientNameAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        eventClientSpinner.setAdapter(clientNameAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void pickDate(){

        //SETTING THE COMMUNICATOR FOR INTER FRAGMENT COMMUNICATION
        DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                eventDateET.setText(i2+"/"+i1+"/"+i);
            }
        };
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setCallBack(onDate);
        datePickerFragment.show(getFragmentManager(),"Get Date");
    }
    public void setCallBack(CalendarEventCommunicator newEvent){
        communicator = newEvent;
    }

     interface CalendarEventCommunicator{
        public void addNewCalendarEvent(CalendarEvent calendarEvent);
    }

}

