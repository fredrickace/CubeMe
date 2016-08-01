package com.cube_me.cubeme.Calendar;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cube_me.cubeme.BaseActivity;
import com.cube_me.cubeme.R;
import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {

    protected CustomCalendarView customCalendarView;
    protected FloatingActionButton fab;
    protected Calendar currentCalendar;

    public CalendarFragment() {
        // Required empty public constructor
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
        BaseActivity.appToolbar.setTitle("Calendar");


        fab = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Yet to Be Configured", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

//         Initialize CustomCalendar

        customCalendarView = (CustomCalendarView) rootView.findViewById(R.id.customCalendarView);
        currentCalendar = Calendar.getInstance(Locale.getDefault());
//        customCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        customCalendarView.setShowOverflowDate(false);
        customCalendarView.refreshCalendar(currentCalendar);
        customCalendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                Toast.makeText(rootView.getContext(), df.format(date), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMonthChanged(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("MMM-yyyy");
                Toast.makeText(rootView.getContext(), df.format(date), Toast.LENGTH_SHORT).show();

            }
        });
        // Inflate the layout for this fragment
        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
