package com.cube_me.cubeme_crm.Calendar;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.ClickListener;
import com.cube_me.cubeme_crm.DatePickerFragment;
import com.cube_me.cubeme_crm.R;
import com.cube_me.cubeme_crm.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment implements View.OnClickListener{

    FloatingActionButton datePickFAB;
    FloatingActionButton newEventFAB;
    RecyclerView calendarEventRecyclerView;
    CalendarEventRecyclerAdapter calendarEventRecyclerAdapter;
    Context context;
    List<CalendarEvent> calendarEventsList;

    public CalendarFragment() {
        // REQUIRED EMPTY PUBLIC CONSTRUCTOR
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.calendar_fragment, container, false);

        // BASIC INIT
        context = getContext();
        BaseActivity.appToolbar.setTitle("Calendar");
        calendarEventsList = new ArrayList<>();

        datePickFAB = (FloatingActionButton) rootView.findViewById(R.id.calendarFragment_pickDate);
        newEventFAB = (FloatingActionButton) rootView.findViewById(R.id.calendarFragment_addEvent);
        datePickFAB.setOnClickListener(this);
        newEventFAB.setOnClickListener(this);

        //SETTING UP THE RECYCLER VIEW
        calendarEventRecyclerView = (RecyclerView) rootView.findViewById(R.id.calendarFragment_recycler);
        calendarEventRecyclerAdapter = new CalendarEventRecyclerAdapter(calendarEventsList,context);
        calendarEventRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        calendarEventRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, calendarEventRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        calendarEventRecyclerView.setAdapter(calendarEventRecyclerAdapter);
        //END OF RECYCLER VIEW SECTION

        return rootView;

    }

    private void showDatePicker() {

        DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Toast.makeText(getContext(), ""+i, Toast.LENGTH_SHORT).show();
            }
        };
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setCallBack(onDate);
        datePickerFragment.show(getFragmentManager(),"Date Picker");


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.calendarFragment_addEvent:

                CalendarCreateEventDialog.CalendarEventCommunicator communicator = new CalendarCreateEventDialog.CalendarEventCommunicator() {
                    @Override
                    public void addNewCalendarEvent(CalendarEvent calendarEvent) {
                        calendarEventsList.add(calendarEvent);
                        calendarEventRecyclerAdapter.notifyDataSetChanged();
                    }
                };
                CalendarCreateEventDialog newDialog = new CalendarCreateEventDialog();
                newDialog.setCallBack(communicator);
                newDialog.setStyle(DialogFragment.STYLE_NORMAL,R.style.CustomDialog);
                newDialog.show(getFragmentManager(),"Add Event");
                break;

            case R.id.calendarFragment_pickDate:

                showDatePicker();
                break;
        }

    }


}
