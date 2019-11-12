package com.cube_me.cubeme_crm.Contacts;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.ClickListener;
import com.cube_me.cubeme_crm.R;
import com.cube_me.cubeme_crm.RecyclerSwipeListener;
import com.cube_me.cubeme_crm.RecyclerTouchListener;
import com.cube_me.cubeme_crm.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    RecyclerView contactRecylcerView;
    FloatingActionButton newFAB;
    ContactsRecyclerAdapter contactsAdapter;
    public static final int RETURN_TRUE = 0;
    public static final int RETURN_FALSE = 1;
    Intent i;
    List<Contact> contactList;
    List<Contact> filteredList;
    Context context;
    ContactWebService contactWebService;


    public ContactsFragment() {
        // REQUIRED EMPTY PUBLIC CONSTRUCTOR
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contacts_fragment, container, false);
        BaseActivity.appToolbar.setTitle("Contacts");
        setHasOptionsMenu(true);

        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_contacts_fragment, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.action_contacts_Search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filteredList = getFilteredList(query);
                updateRecycler(filteredList);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                filteredList = getFilteredList(query);
                updateRecycler(filteredList);
                return true;
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        filteredList = new ArrayList<>();
        contactList = new ArrayList<>();

        //BASIC INIT
        context = getContext();
        contactWebService = new ContactWebService(context);

        contactWebService.setCallBack(new ContactWebService.ContactsWebServiceInterface() {
            @Override
            public void returnAllContacts(List<Contact> contactsList) {
                contactList = contactsList;
                Log.i("Contact Communication", "Success");
                // SETTING UP RECYCLERVIEW
                contactRecylcerView = (RecyclerView) getView().findViewById(R.id.recyler_contacts);
                contactsAdapter = new ContactsRecyclerAdapter(getContext(), contactsList);
                contactRecylcerView.setLayoutManager(new LinearLayoutManager(getContext()));
                contactRecylcerView.setItemAnimator(new DefaultItemAnimator());
                contactRecylcerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
                RecyclerSwipeListener recyclerSwipeListener = new RecyclerSwipeListener(context, contactRecylcerView, new RecyclerSwipeListener.SwipeListenerCommunicator() {
                    @Override
                    public void onLeftSwipe(int position) {
                        Toast.makeText(context, "Left Swiped" + position +" clicked", Toast.LENGTH_SHORT).show();
                        contactsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onRightSwipe(int position) {
                        Toast.makeText(context, "Right swiped", Toast.LENGTH_SHORT).show();

                    }
                });
                ItemTouchHelper itemTouchHelper = recyclerSwipeListener.getItemTouchListener();
//                itemTouchHelper.attachToRecyclerView(null);
                itemTouchHelper.attachToRecyclerView(contactRecylcerView);


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
            }

            @Override
            public void onReturnCompanyContacts(List<Contact> contactList) {

            }

            @Override
            public void returnSpecificContact(Contact contact) {

            }
        });
        contactWebService.getContacts();



        //SETTING UP FLOATING ACTION BUTTON
        newFAB = (FloatingActionButton) getView().findViewById(R.id.contactAddFAB);
        newFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getContext(), ContactNew.class);
                startActivityForResult(i, RETURN_TRUE);
            }
        });
    }

    public void updateRecycler(List<Contact> contacts){
        contactsAdapter = new ContactsRecyclerAdapter(getContext(), contacts);
        contactRecylcerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contactRecylcerView.setAdapter(contactsAdapter);

    }
    public List<Contact> getFilteredList(String query) {

        List<Contact> results = new ArrayList<>();
        for (Contact contact : contactList) {
            if(contact.contactName.toLowerCase().contains(query) || contact.companyName.toLowerCase().contains(query)){
                results.add(contact);
            }
        }
        return results;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RETURN_TRUE) {

            Contact newContact = new Contact();
            newContact = data.getParcelableExtra("NewContact");
            contactList.add(newContact);
            contactsAdapter.notifyDataSetChanged();
        }
    }

//    public List<Contact> getData() {
//
//        String[] contactName = {"Fredrick", "Abisha", "Ajay", "RKS", "Naresh", "Shiny", "Lincy", "Prashant"};
//        String[] contactCompanyName = {"CrossAppFactory", "Abi's Bakes", "HappeMiles", "Cognizant", "SeedReaps", "Arizona Bio", "Accenture", "Renault-Nissan"};
//        String[] contactEmail = {"fred@crossappfactory.com", "abi@abibakes.com", "ajay@happemiles.com", "rahul1805@cognizant.com", "naresh@seedreaps.com", "shiny@priya.com", "lincy@accenture.com", "prashant@renault-nissan.com"};
//        List<AddressObject> addressObjectList = new ArrayList<>();
//        List<Email> emailList = new ArrayList<>();
//        List<PhoneNumber> phoneNumberList = new ArrayList<>();
//        for (int i = 0; i < contactCompanyName.length && i < contactCompanyName.length && i < contactEmail.length; i++) {
//            Contact currentContact = new Contact();
//            currentContact.setContactName(contactName[i]);
//            currentContact.setCompanyName(contactCompanyName[i]);
//            currentContact.setEmail(contactEmail[i]);
//            currentContact.setAddressList(addressObjectList);
//            currentContact.setEmailList(emailList);
//            currentContact.setPhoneNumberList(phoneNumberList);
//            contactList.add(currentContact);
//        }
//        return contactList;
//
//    }
}
