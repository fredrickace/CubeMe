package com.cube_me.cubeme_crm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by FredrickCyril on 8/31/16.
 */
public class DatePickerFragment extends DialogFragment {

    DatePickerDialog.OnDateSetListener dateSetListener;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);
        return new DatePickerDialog(getActivity(),dateSetListener,year,month,date);

    }

    public void setCallBack(DatePickerDialog.OnDateSetListener onDate) {
        dateSetListener = onDate;
    }



}


