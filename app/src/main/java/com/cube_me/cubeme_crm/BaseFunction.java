package com.cube_me.cubeme_crm;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.DatePicker;
import android.widget.Toast;

/**
 * Created by FredrickCyril on 10/20/16.
 */

public class BaseFunction extends Activity{
    Context context;
    String temp;
    FragmentManager fragmentManager;
    Activity activity;

    //Permission code that will be checked in the method onRequestPermissionsResult
    public static final int READ_PERMISSION_CODE = 23;
    public static final int WRITE_PERMISSION_CODE = 24;


    public BaseFunction(Context context) {
        this.context = context;
        activity = (Activity) context;
//        fragmentManager = activity.getFragmentManager();

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

    //SECTION TO CHECK & ENABLE THE PERMISSION FOR MARSHMALLOW
    //We are calling this method to check the permission status

    public boolean isWriteStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }
    public boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }





    //Requesting permission
    public void requestStoragePermission(int requestCode){

        switch (requestCode){
            case READ_PERMISSION_CODE:
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.READ_EXTERNAL_STORAGE)){
                    //If the user has denied the permission previously your code will come to this block
                    //Here you can explain why you need this permission
                    //Explain here why you need this permission
                    Toast.makeText(context, "Grant Permission to Download the File", Toast.LENGTH_SHORT).show();
                }

                //And finally ask for the permission
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PERMISSION_CODE);
                break;
            case WRITE_PERMISSION_CODE:
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    //If the user has denied the permission previously your code will come to this block
                    //Here you can explain why you need this permission
                    //Explain here why you need this permission
                    Toast.makeText(context, "Grant Permission to Download the File", Toast.LENGTH_SHORT).show();
                }

                //And finally ask for the permission
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                        , WRITE_PERMISSION_CODE);
                break;
        }

    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Toast.makeText(getApplicationContext(), "Inside result", Toast.LENGTH_SHORT).show();
        switch (requestCode){
            case READ_PERMISSION_CODE:
                Toast.makeText(context, "Test for app permission", Toast.LENGTH_SHORT).show();
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    //Displaying a toast
                    Toast.makeText(context,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
                }else{
                    //Displaying another toast if permission is not granted
                    Toast.makeText(context,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
                }
                break;

            case WRITE_PERMISSION_CODE:
                Toast.makeText(getApplicationContext(), "Test for app permission", Toast.LENGTH_SHORT).show();
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    //Displaying a toast
                    Toast.makeText(context,"Permission granted now you can write the storage",Toast.LENGTH_LONG).show();
                }else{
                    //Displaying another toast if permission is not granted
                    Toast.makeText(context,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
                }
                break;

        }

//
//        //Checking the request code of our request
//        if(requestCode == READ_PERMISSION_CODE){
//
//            //If permission is granted
//
//        }
    }
}
