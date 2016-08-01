package com.cube_me.cubeme;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.cube_me.cubeme.Accounts.AccountsFragment;
import com.cube_me.cubeme.Calendar.CalendarFragment;
import com.cube_me.cubeme.Contacts.ContactsFragment;
import com.cube_me.cubeme.Inquiry.InquiryFragment;

public class BaseActivity extends AppCompatActivity {

    public static Toolbar appToolbar;
    public DrawerLayout fullView;
    public NavigationView mNavigationView;
    private final String BACK_TAG = "Tag";


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

    }

    //    public void initToolBar() {
//        //Tool bar set up
//        appToolbar = (Toolbar) findViewById(R.id.app_toolbar);
//        setSupportActionBar(appToolbar);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    }
//
//
    public void initNavigationView() {

//        Drawer Layout & Navigation view Setup
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
        mNavigationView.getMenu().getItem(0).setChecked(true);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                fullView.closeDrawers();

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

//                Unchecking all the selected Items
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
                }
                return false;
            }
        });
    }

    //    Adding * to the String
    public static SpannableStringBuilder buildString(String data)
    {
        String colored = " *";
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(data);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();
        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
//        ForegroundColorSpan
    }
}
