package com.cube_me.cubeme.Accounts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube_me.cubeme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountViewDetailsFragment extends Fragment {


    public AccountViewDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.account_view_details_fragment, container, false);
    }

}
