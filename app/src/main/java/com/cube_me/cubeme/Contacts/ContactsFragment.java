package com.cube_me.cubeme.Contacts;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cube_me.cubeme.BaseActivity;
import com.cube_me.cubeme.ClickListener;
import com.cube_me.cubeme.R;
import com.cube_me.cubeme.RecyclerTouchListener;
import com.cube_me.cubeme.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    RecyclerView contactRecylcerView;
    FloatingActionButton newFAB;
    static ContactsRecyclerAdapter contactsAdapter;


    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contacts_fragment, container, false);
        BaseActivity.appToolbar.setTitle("Contacts");

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Setting Up RecylcerView
        contactRecylcerView = (RecyclerView) getView().findViewById(R.id.recyler_contacts);
        contactsAdapter = new ContactsRecyclerAdapter(getContext(),getData());

        contactRecylcerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contactRecylcerView.setItemAnimator(new DefaultItemAnimator());
        contactRecylcerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));

        contactRecylcerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), contactRecylcerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getContext(), "Will Be configured", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getContext(), "Long Click will also be configured", Toast.LENGTH_SHORT).show();
            }
        }));
        contactRecylcerView.setAdapter(contactsAdapter);

//        Setting Up Floating Action Button
        newFAB = (FloatingActionButton) getView().findViewById(R.id.contactAddFAB);
        newFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),ContactNew.class));
            }
        });


    }

    public static List<Contact> getData()
    {
        List<Contact> data = new ArrayList<>();
        String[] contactName = {"Fredrick","Abisha","Ajay","RKS","Naresh","Shiny","Lincy","Prashant"};
        String[] contactCompanyName = {"CrossAppFactory","Abi's Bakes","HappeMiles","Cognizant","SeedReaps","Arizona Bio","Accenture","Renault-Nissan"};
        String[] contactEmail = {"fred@crossappfactory.com","abi@abibakes.com","ajay@happemiles.com","rahul1805@cognizant.com","naresh@seedreaps.com","shiny@priya.com","lincy@accenture.com","prashant@renault-nissan.com"};
        for(int i = 0; i<contactCompanyName.length && i<contactCompanyName.length && i<contactEmail.length; i++)
        {
            Contact currentContact = new Contact();
            currentContact.setName(contactName[i]);
            currentContact.setCompanyName(contactCompanyName[i]);
            currentContact.setEmail(contactEmail[i]);
            data.add(currentContact);
        }
        return data;

    }
}
