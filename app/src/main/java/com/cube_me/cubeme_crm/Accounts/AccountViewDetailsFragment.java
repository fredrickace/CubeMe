package com.cube_me.cubeme_crm.Accounts;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cube_me.cubeme_crm.PhoneNumber;
import com.cube_me.cubeme_crm.AddressObject;
import com.cube_me.cubeme_crm.Email;
import com.cube_me.cubeme_crm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountViewDetailsFragment extends Fragment {


    Accounts accounts;
    Bundle bundle;

    TextView accountNameTV;
    TextView accountCodeTV;
    TextView accountContactPersonTV;
    TextView accountWebTV;
    LinearLayout phoneNoLayout;
    LinearLayout addressLayout;
    LinearLayout emailLayout;


    public AccountViewDetailsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.account_view_details_fragment, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i("AccountDetailsTab","Success");
        //BASIC INIT
        bundle = this.getArguments();
        accounts = bundle.getParcelable("Account");
        accountNameTV = (TextView) getView().findViewById(R.id.accountViewdetails_AccountNameTV);
        accountCodeTV = (TextView) getView().findViewById(R.id.accountViewdetails_CodeTV);
        accountContactPersonTV = (TextView) getView().findViewById(R.id.accountViewdetails_contactPersonTV);
        accountWebTV = (TextView) getView().findViewById(R.id.accountViewdetails_webTV);
        phoneNoLayout = (LinearLayout) getView().findViewById(R.id.accountViewDetails_phoneRowContainer);
        addressLayout = (LinearLayout) getView().findViewById(R.id.accountViewDetails_addressRowContainer);
        emailLayout = (LinearLayout) getView().findViewById(R.id.accountViewDetails_emailRowContainer);

        //SETTING ALL THE VALUES FROM THE BUNDLE
        accountNameTV.setText(accounts.accountName);
        accountCodeTV.setText(accounts.accountCode);
        accountContactPersonTV.setText(accounts.accountContactName);
        accountWebTV.setText(accounts.accountWeb);

        //RETRIEVING THE PHONE NUMBERS FROM THE PHONE NUMBER LIST AND INFLATING THEM IN DYNAMIC LAYOUT
        for(int i = 0; i<accounts.phoneNumberList.size(); i++){
            PhoneNumber phoneNumber = accounts.phoneNumberList.get(i);
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View phoneNumberView = layoutInflater.inflate(R.layout.phoneno_tv_dynamicrow,null);

            TextView phoneNoLabelTV = (TextView) phoneNumberView.findViewById(R.id.phoneRowTV_label);
            TextView phoneNoTV = (TextView) phoneNumberView.findViewById(R.id.phoneRowTV);

            phoneNoLabelTV.setText("Phone - "+ phoneNumber.accountPhoneLabel.substring(0,1).toUpperCase()+ phoneNumber.accountPhoneLabel.substring(1));
            phoneNoTV.setText(phoneNumber.accountPhoneNumber);

            phoneNoLayout.addView(phoneNumberView);
        }

        //RETRIEVING THE ADDRESS FROM THE ADDRESS LIST AND INFLATING THEM IN DYNAMIC LAYOUT
        for (int i = 0; i<accounts.addressObjectList.size(); i++){
            AddressObject addressObject = accounts.addressObjectList.get(i);
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addressView = layoutInflater.inflate(R.layout.phoneno_tv_dynamicrow, null);

            TextView addressLabelTV = (TextView) addressView.findViewById(R.id.phoneRowTV_label);
            TextView addressTV = (TextView) addressView.findViewById(R.id.phoneRowTV);

            addressLabelTV.setText("Address - "+ addressObject.addressLabelName.substring(0,1).toUpperCase()+ addressObject.addressLabelName.substring(1));
            String[] address = addressObject.addressDetail.split(",");
            String temp = "";
            for (int j = 0; j<address.length; j++){
                temp += address[j] +"\n";
                Log.i("AddressTV", address[j]);
            }
            addressTV.setText(temp);

            addressLayout.addView(addressView);
        }

        //RETRIEVING THE EMAIL DETAILS FROM THE EMAIL LIST AND INFLATING THEM IN THE DYNAMIC LAYOUT
        for(int i = 0; i<accounts.emailList.size(); i++){
            Email email = accounts.emailList.get(i);
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View emailView = layoutInflater.inflate(R.layout.email_tv_dynamicrow,null);

            TextView emailLabelTV = (TextView) emailView.findViewById(R.id.emailRowTV_label);
            TextView emailTV = (TextView) emailView.findViewById(R.id.emailRowTV);

            emailLabelTV.setText("Email - "+ email.accountEmailLabel.substring(0,1).toUpperCase() + email.accountEmailLabel.substring(1));

            emailTV.setText(email.accountEmail);
            emailLayout.addView(emailView);
        }



    }

}
