package com.cube_me.cubeme;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.app.FragmentManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;

/**
 * Created by FredrickCyril on 10/20/16.
 */

public class BaseFunction{
    Context context;
    String temp;
    FragmentManager fragmentManager;
    public BaseFunction(Context context) {
        this.context = context;
        Activity activity = (Activity) context;
        fragmentManager = activity.getFragmentManager();

    }

    public String pickDate() {
//         temp;
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setCallBack(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                temp = i2 + "/" + BaseActivity.MonthName(i1) + "/" + i;

            }
        });
//        datePickerFragment.show(fragmentManager,"StartDate");
        return temp;
    }
}
