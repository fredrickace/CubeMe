package com.cube_me.cubeme_crm.Inquiry;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cube_me.cubeme_crm.R;

/**
 * Created by FredrickCyril on 9/17/16.
 */
public class InquiryViewPageAdapter extends FragmentPagerAdapter{

    Context context;
    String[] tabsTitle;
    int inquiryID;
    String isOnline;

    public InquiryViewPageAdapter(FragmentManager fm, Context context,int inquiryID, String isOnline) {
        super(fm);
        this.context = context;
        tabsTitle = context.getResources().getStringArray(R.array.tabTitle_Inquiry);
        this.inquiryID = inquiryID;
        this.isOnline = isOnline;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new InquiryViewDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("InquiryId",inquiryID);
                bundle.putString("ONLINE/OFFLINE",isOnline);
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new InquiryViewRelatedFragment();
//                Bundle bundle1 = new Bundle();
//                bundle1.putString("InquiryId",inquiryObj.inquiryID);
//                fragment.setArguments(bundle1);
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabsTitle[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
