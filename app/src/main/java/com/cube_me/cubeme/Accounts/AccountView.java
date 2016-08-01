package com.cube_me.cubeme.Accounts;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.cube_me.cubeme.R;
import com.cube_me.cubeme.tabs.SlidingTabLayout;

import java.util.zip.Inflater;

public class AccountView extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager accountsViewViewPager;
    SlidingTabLayout accountsViewSlidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_view);

//        Setting Up toolbar
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Athab Qatar");

//        Setting Up ViewPager
        accountsViewViewPager = (ViewPager)findViewById(R.id.accountsViewViewPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        AccountsViewPagerAdapter accountsViewAdapter = new AccountsViewPagerAdapter(fragmentManager, getApplicationContext());
        accountsViewViewPager.setAdapter(accountsViewAdapter);

//        Setting Up SlidingTabLayout
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
        }
        return super.onOptionsItemSelected(item);

    }
}
