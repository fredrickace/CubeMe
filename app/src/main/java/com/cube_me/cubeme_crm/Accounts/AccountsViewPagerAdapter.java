package com.cube_me.cubeme_crm.Accounts;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.cube_me.cubeme_crm.Inquiry.Inquiry;
import com.cube_me.cubeme_crm.R;

/**
 * Created by FredrickCyril on 7/27/16.
 */
public class AccountsViewPagerAdapter extends FragmentPagerAdapter {


    Context context;
    String[] tabsTitle;
    Accounts accounts;
    public AccountsViewPagerAdapter(FragmentManager fm, Context context, Accounts accounts) {
        super(fm);
        this.context = context;
        this.accounts = accounts;
        tabsTitle = context.getResources().getStringArray(R.array.tabTitle_Account);

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new AccountViewDetailsFragment();
                Log.i("Fragment creation","Check");
                Bundle bundle = new Bundle();
                bundle.putParcelable("Account",accounts);
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new AccountViewAttachedFragment();
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
