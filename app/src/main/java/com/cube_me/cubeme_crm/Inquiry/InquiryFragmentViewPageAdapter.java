package com.cube_me.cubeme_crm.Inquiry;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.cube_me.cubeme_crm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FredrickCyril on 1/25/17.
 */

public class InquiryFragmentViewPageAdapter extends FragmentPagerAdapter {

    Context context;
    String[] tabsTitle;


    public InquiryFragmentViewPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        Log.i("InquiryViewPager","Success");
        this.context = context;
        tabsTitle = context.getResources().getStringArray(R.array.tabTitle_InquiryFragment);

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new InquiryFragmentCreated();
                break;

            case 1:
                fragment = new InquiryFragmentAssigned();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabsTitle[position];
    }
}
