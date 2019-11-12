package com.cube_me.cubeme_crm;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cube_me.cubeme_crm.Accounts.AccountsFragment;
import com.cube_me.cubeme_crm.Calendar.CalendarFragment;
import com.cube_me.cubeme_crm.Contacts.ContactsFragment;
import com.cube_me.cubeme_crm.Estimation.EstimationFragment;
import com.cube_me.cubeme_crm.Inquiry.InquiryFragment;
import com.cube_me.cubeme_crm.Jobs.JobFragment;
import com.cube_me.cubeme_crm.PurchaseOrder.PurchaseFragment;
import com.cube_me.cubeme_crm.Quotations.QuotationFragment;
import com.cube_me.cubeme_crm.Reports.ReportFragment;

import org.apache.poi.util.StringUtil;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BaseActivity extends AppCompatActivity {

    public static Toolbar appToolbar;
    public DrawerLayout fullView;
    public NavigationView mNavigationView;

    private final String BACK_TAG = "Tag";
    public static DecimalFormat decimalFormat;
    public static ArrayAdapter inquiryStatusSpinnerAA;
    public static ArrayAdapter clientNameAutoCompleteAA;
    public static Calendar calendar;
    public static DateFormat DATE_FORMAT;
    ConnectivityManager connectivityManager;
    NetworkInfo activeNetworkOn;
    public static boolean networkIsConnected;

    public static int socketTimeout = 30000;
    SharedPreferences loginPreferences;
    public static String token;


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);

        appToolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(appToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        appToolbar.setTitle("Calendar");
        initNavigationView();

        //BASIC INIT

        token = loginPreferences.getString("Token", "LoginToken");
        calendar = Calendar.getInstance();
        DATE_FORMAT = new SimpleDateFormat("d/MMM/yyyy");
        decimalFormat = new DecimalFormat("#.#");
        inquiryStatusSpinnerAA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.inquiry_status, R.layout.spinner_layout);
        inquiryStatusSpinnerAA.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        clientNameAutoCompleteAA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.accounts_name, R.layout.autocompletetext_layout);

        //DETERMINING THE STATUS OF THE INTERNET CONNECTION
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetworkOn = connectivityManager.getActiveNetworkInfo();
        networkIsConnected = activeNetworkOn != null && activeNetworkOn.isConnectedOrConnecting();

        if (!networkIsConnected) {
            Toast.makeText(this, "Internet is not Connected!!! Working Offline", Toast.LENGTH_SHORT).show();
        }


    }

//        public void initToolBar() {
//        //Tool bar set up
//        appToolbar = (Toolbar) findViewById(R.id.app_toolbar);
//        setSupportActionBar(appToolbar);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    }
//

    //NAVIGATION VIEW SECTION
    public void initNavigationView() {


//        Drawer Layout & Navigation view Setup
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
        mNavigationView.getMenu().getItem(0).setChecked(true);

        //SETTING UP THE USER NAME IN THE NAV HEADER

        loginPreferences = this.getSharedPreferences("com.cube_me.cubeme", MODE_PRIVATE);
        String userName = loginPreferences.getString("UserName", "UserName");
        TextView navHeaderUserNameTV = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.navHeader_userNameTV);
        navHeaderUserNameTV.setText(userName);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                fullView.closeDrawers();

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                // UN CHECK ALL THE SELECTED ITEMS
                int size = mNavigationView.getMenu().size();
                for (int i = 0; i < size; i++) {
                    mNavigationView.getMenu().getItem(i).setChecked(false);
                }


                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.nav_calendar:
                        item.setChecked(true);
                        CalendarFragment calendarFragment = new CalendarFragment();
                        transaction.replace(R.id.activity_main, calendarFragment, BACK_TAG);
                        transaction.commit();
                        break;

                    case R.id.nav_accounts:
                        item.setChecked(true);
                        AccountsFragment accountsFragment = new AccountsFragment();
                        transaction.replace(R.id.activity_main, accountsFragment, BACK_TAG);
//                        transaction.addToBackStack(BACK_TAG);
                        transaction.commit();
                        break;

                    case R.id.nav_contacts:
                        item.setChecked(true);
                        ContactsFragment contactsFragment = new ContactsFragment();
                        transaction.replace(R.id.activity_main, contactsFragment, BACK_TAG);
                        transaction.commit();
                        break;

                    case R.id.nav_inquiry:
                        item.setChecked(true);
                        transaction.replace(R.id.activity_main, new InquiryFragment(), BACK_TAG);
                        transaction.commit();
                        break;

                    case R.id.nav_estimation:
                        item.setChecked(true);
                        transaction.replace(R.id.activity_main, new EstimationFragment(), BACK_TAG);
                        transaction.commit();
                        break;
                    case R.id.nav_quotations:
                        item.setChecked(true);
                        transaction.replace(R.id.activity_main, new QuotationFragment(), BACK_TAG);
                        transaction.commit();
                        break;
                    case R.id.nav_purchaseorders:
                        item.setChecked(true);
                        transaction.replace(R.id.activity_main, new PurchaseFragment(), BACK_TAG);
                        transaction.commit();
                        break;
                    case R.id.nav_signOut:
                        setResult(0);
                        finish();
                        break;
                    case R.id.nav_jobs:
                        item.setChecked(true);
                        transaction.replace(R.id.activity_main, new JobFragment(), BACK_TAG);
                        transaction.commit();
                        break;
                    case R.id.nav_reports:
                        item.setChecked(true);
                        transaction.replace(R.id.activity_main, new ReportFragment(), BACK_TAG);
                        transaction.commit();
                        break;
                }
                return false;
            }
        });
    }

    public static String getCurrentDate() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);

        return date + "/" + MonthName(month) + "/" + year;
    }

    public static String getCurrentTime() {

        int hours = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        int am_pm = calendar.get(Calendar.AM_PM);
        String AM_PM = "PM";
        if (am_pm == Calendar.AM) {
            AM_PM = "AM";
        }
        return hours + ":" + min + ":" + seconds + ":" + AM_PM;
    }

    public static Date getDateAsDate(String date) {
        Date tempDate = null;
        String[] dateString = date.split("/");
        try {
            tempDate = DATE_FORMAT.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tempDate;
    }

    public static String getFormattedDate(String date) {
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        Date testDate = null;
        String newFormat = "space";
        try {
            testDate = sourceFormat.parse(date);
            DateFormat formatter = new SimpleDateFormat("d/MMM/yyyy");
            newFormat = formatter.format(testDate);
            Log.i("Formatted Date", newFormat);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return newFormat;

    }


    //METHOD TO UPPERCASE THE FIRST LETTER OF A WORD

    public static String convertFirstLetterUpperCase(String word){

        return word.substring(0,1).toUpperCase() + word.substring(1);
    }

    //METHOD TO RETURN HEADERS FOR WEBSERVICES
    public static Map<String, String> getHeaders() {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("_token", token);
        return headers;
    }

    public static int MonthNumber(String monthName) {
        int monthNumber = 0;
        if (monthName.equals("Jan")) {
            monthNumber = 1;
        }
        if (monthName.equals("Feb")) {
            monthNumber = 2;
        }
        if (monthName.equals("Mar")) {
            monthNumber = 3;
        }
        if (monthName.equals("Apr")) {
            monthNumber = 4;
        }
        if (monthName.equals("May")) {
            monthNumber = 5;
        }
        if (monthName.equals("Jun")) {
            monthNumber = 6;
        }
        if (monthName.equals("Jul")) {
            monthNumber = 7;
        }
        if (monthName.equals("Aug")) {
            monthNumber = 8;
        }
        if (monthName.equals("Sep")) {
            monthNumber = 9;
        }
        if (monthName.equals("Oct")) {
            monthNumber = 10;
        }
        if (monthName.equals("Nov")) {
            monthNumber = 11;
        }
        if (monthName.equals("Dec")) {
            monthNumber = 12;
        }
        return monthNumber;
    }

    public static String MonthName(int month) {
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return monthNames[month];
    }

    // ADDING * TO THE STRING
    public static SpannableStringBuilder buildString(String data) {
        String colored = " *";
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(data);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();
        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
        //ForegroundColorSpan
    }


    //Function to check if EDITTEXT field is empty

    public static boolean ifEditTextNotEmptyErrMsg(EditText editText) {
        boolean valid = true;
        if (TextUtils.isEmpty(editText.getText())) {
            valid = false;
            editText.setError("Field Required");

        }
        return valid;
    }

    public static boolean ifEditTextNotEmpty(EditText editText) {
        boolean valid = true;
        if (TextUtils.isEmpty(editText.getText())) {
            valid = false;

        }
        return valid;
    }


}
