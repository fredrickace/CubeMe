package com.cube_me.cubeme.Inquiry;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.cube_me.cubeme.R;
import com.cube_me.cubeme.tabs.SlidingTabLayout;


public class InquiryView extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager inquiryViewPager;
    SlidingTabLayout inquirySlideTab;

    Intent i;
    Inquiry inquiry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inquiry_view);

        //INIT TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Inquiry");


        //GETTING THE INQUIRY FROM THE CALLING INTENT
        i = getIntent();
        inquiry = i.getParcelableExtra("ViewInquiry");

        //SETTING UP THE VIEWPAGER
        inquiryViewPager = (ViewPager) findViewById(R.id.inquiryView_ViewPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        InquiryViewPageAdapter viewPageAdapter = new InquiryViewPageAdapter(fragmentManager,getApplicationContext(),inquiry.inquiryID);
        inquiryViewPager.setAdapter(viewPageAdapter);

        //SETTING UP THE SLIDING TAB
        inquirySlideTab = (SlidingTabLayout) findViewById(R.id.inquiryView_SlideTab);
        inquirySlideTab.setDistributeEvenly(true);
        inquirySlideTab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        inquirySlideTab.setSelectedIndicatorColors(getResources().getColor(R.color.ivory));
        inquirySlideTab.setViewPager(inquiryViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
