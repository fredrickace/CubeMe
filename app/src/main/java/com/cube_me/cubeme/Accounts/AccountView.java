package com.cube_me.cubeme.Accounts;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cube_me.cubeme.Inquiry.Inquiry;
import com.cube_me.cubeme.Inquiry.InquiryNew;
import com.cube_me.cubeme.R;
import com.cube_me.cubeme.tabs.SlidingTabLayout;

public class AccountView extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager accountsViewViewPager;
    SlidingTabLayout accountsViewSlidingTabLayout;
    public static final String activityName = "ACCOUNT_VIEW";
    NewInquiryCommunicator communicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_view);


        //SETTING UP TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Athab Qatar");

        //SETTING UP VIEWPAGER
        accountsViewViewPager = (ViewPager)findViewById(R.id.accountsViewViewPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        AccountsViewPagerAdapter accountsViewAdapter = new AccountsViewPagerAdapter(fragmentManager, getApplicationContext());
        accountsViewViewPager.setAdapter(accountsViewAdapter);

        //SETTING UP SLIDINGTABLAYOUT
        accountsViewSlidingTabLayout = (SlidingTabLayout)findViewById(R.id.accountViewSlideTab);
        accountsViewSlidingTabLayout.setDistributeEvenly(true);
        accountsViewSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        accountsViewSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.ivory));
        accountsViewSlidingTabLayout.setViewPager(accountsViewViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_accounts_viewdetail,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.nav_accountViewDetail_new:

                //ADDING A DIALOG BOX TO SELECT THE SUB CATEGORIES
                CharSequence colors[] = new CharSequence[] {"Inquiry", "Estimations","Budgets","Quotations", "Jobs"};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("New");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on colors[which]
                        switch (which){
                            case 0:
                                Intent i = new Intent(getApplicationContext(),InquiryNew.class);
                                i.putExtra("InquiryFlag",InquiryNew.INQUIRY_NEW);
                                startActivity(i);
                                break;

                        }
                    }
                });
                builder.show();
                break;

            case R.id.nav_accountViewDetail_edit:

        }
        return super.onOptionsItemSelected(item);

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == InquiryNew.RETURN_TRUE) {
//            Inquiry inquiry = data.getParcelableExtra("New Inquiry");
//            Toast.makeText(AccountView.this, inquiry.inquirySubject, Toast.LENGTH_SHORT).show();
//            AccountViewAttachedFragment.inquiryList.add(inquiry);
////            AccountViewAttachedFragment.sendInquiryToFragment(communicator);
////            communicator.setNewInquiry(inquiry);
//        }
//    }


    interface NewInquiryCommunicator {
        public void setNewInquiry(Inquiry inquiry);
    }
}
