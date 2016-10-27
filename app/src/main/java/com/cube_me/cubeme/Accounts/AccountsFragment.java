package com.cube_me.cubeme.Accounts;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
public class AccountsFragment extends Fragment {

    RecyclerView recyclerView;
    static AccountsRecyclerAdapter accountsAdapter;
    FloatingActionButton fab;
    List<Accounts> accountsList;
    List<Accounts> filteredData;
    public static final int RETURN_TRUE = 0;
    public static final int RETURN_FALSE = 1;

    public AccountsFragment() {
        // REQUIRED EMPTY PUBLIC CONSTRUCTOR
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        View viewRoot = inflater.inflate(R.layout.accounts_fragments, container, false);
        BaseActivity.appToolbar.setTitle("Accounts");

        return viewRoot;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        accountsList = new ArrayList<>();
        filteredData = new ArrayList<>();
        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_accounts);

        accountsAdapter = new AccountsRecyclerAdapter(getContext(), getData());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener(){
            @Override
            public void onClick(View view, int position) {

                startActivity(new Intent(getContext(), AccountView.class));
//                Toast.makeText(getContext(), "Let's Configure You next", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recyclerView.setAdapter(accountsAdapter);
//
        fab = (FloatingActionButton) getView().findViewById(R.id.accounts_FAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),AccountsNew.class);
                startActivityForResult(i,RETURN_TRUE);
            }
        });


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_accounts, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.action_Accounts_Search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                filteredData = getFilteredResults(query);
                updateRecyclerView(filteredData);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                filteredData = getFilteredResults(query);
                updateRecyclerView(filteredData);
                return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RETURN_TRUE){
            Accounts newAccount = data.getParcelableExtra("NewAccount");
            accountsList.add(newAccount);
            updateRecyclerView(accountsList);
        }
    }

    public void updateRecyclerView(List<Accounts> data){
        accountsAdapter = new AccountsRecyclerAdapter(getContext(), data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(accountsAdapter);

    }

    protected List<Accounts> getFilteredResults(String constraint) {
        List<Accounts> results = new ArrayList<>();

        for (Accounts item : accountsList) {
            if (item.accountName.toLowerCase().contains(constraint) || item.accountContactName.toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_Accounts_Inquiry: {


            }
        }
        return super.onOptionsItemSelected(item);


    }

    public List<Accounts> getData(){

        String[] accountName = {"Athab Qatar", "CubeME", "HBK", "Qatar Petroleum", "GCC", "SinoHydro","Woqood","Shell"};
        String[] accountsContactName = {"Raja","Prabhu","Jack","Mark","Fred","Suresh","Bharath","Julie"};
        for(int i=0; i<accountName.length && i<accountsContactName.length; i++){
            Accounts current = new Accounts();
            current.accountName = accountName[i];
            current.accountContactName = accountsContactName[i];
            accountsList.add(current);
        }
        return accountsList;
    }

}
