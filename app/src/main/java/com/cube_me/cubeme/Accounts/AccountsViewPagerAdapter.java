package com.cube_me.cubeme.Accounts;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cube_me.cubeme.R;

/**
 * Created by FredrickCyril on 7/27/16.
 */
public class AccountsViewPagerAdapter extends FragmentPagerAdapter {


    Context context;
    String[] tabstitle;
    public AccountsViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        tabstitle = context.getResources().getStringArray(R.array.tabTitle_Account);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new AccountViewDetailsFragment();
                break;
            case 1:
                fragment = new AccountViewAttachedFragment();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return tabstitle[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
