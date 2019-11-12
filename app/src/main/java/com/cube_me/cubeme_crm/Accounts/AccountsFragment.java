package com.cube_me.cubeme_crm.Accounts;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.ClickListener;
import com.cube_me.cubeme_crm.R;
import com.cube_me.cubeme_crm.RecyclerTouchListener;
import com.cube_me.cubeme_crm.SimpleDividerItemDecoration;
import com.cube_me.cubeme_crm.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cube_me.cubeme_crm.BaseActivity.token;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountsFragment extends Fragment {

    RecyclerView recyclerView;
    static AccountsRecyclerAdapter accountsAdapter;
    FloatingActionButton fab;
    Accounts accounts;
    List<Accounts> accountsList;
    List<Accounts> filteredData;
    public static final int RETURN_TRUE = 0;
    public static final int RETURN_FALSE = 1;
    ProgressDialog progressDialog;
    Context context;
    //WEBSERVICE VOLLEY
    RequestQueue requestQueue;

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


        //BASIC INIT
        accountsList = new ArrayList<>();
        filteredData = new ArrayList<>();
        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_accounts);
        context = getContext();

        requestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Syncing... Please Wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(true);

        if (BaseActivity.networkIsConnected) {
            progressDialog.show();
            new getAccountLive().execute();
        } else {
            Toast.makeText(context, getResources().getString(R.string.WebServiceError), Toast.LENGTH_SHORT).show();
        }
//        accountsAdapter = new AccountsRecyclerAdapter(getContext(), getData());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
//
//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener(){
//            @Override
//            public void onClick(View view, int position) {
//
//                startActivity(new Intent(getContext(), AccountView.class));
////                Toast.makeText(getContext(), "Let's Configure You next", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
//        recyclerView.setAdapter(accountsAdapter);
////
        fab = (FloatingActionButton) getView().findViewById(R.id.accounts_FAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AccountsNew.class);
                startActivityForResult(i, RETURN_TRUE);
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

        if (resultCode == RETURN_TRUE) {
            Accounts newAccount = data.getParcelableExtra("NewAccount");
            accountsList.add(newAccount);
            updateRecyclerView(accountsList);
        }
    }

    public void updateRecyclerView(List<Accounts> data) {
        accountsAdapter = new AccountsRecyclerAdapter(getContext(), data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(accountsAdapter);

    }

    protected List<Accounts> getFilteredResults(String constraint) {
        List<Accounts> results = new ArrayList<>();


        for (Accounts item : accountsList) {
//            Log.i("Filter List",item.accountName.toLowerCase());
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

    //METHOD TO INSERT DUMMY VALUES
//    public List<Accounts> getData(){
//
//        String[] accountName = {"Athab Qatar", "CubeME", "HBK", "Qatar Petroleum", "GCC", "SinoHydro","Woqood","Shell"};
//        String[] accountsContactName = {"Raja","Prabhu","Jack","Mark","Fred","Suresh","Bharath","Julie"};
//        for(int i=0; i<accountName.length && i<accountsContactName.length; i++){
//            Accounts current = new Accounts();
//            current.accountName = accountName[i];
//            current.accountContactName = accountsContactName[i];
//            accountsList.add(current);
//        }
//        return accountsList;
//    }


    //ASYNC TASK TO GET THE LIVE COMPANY DETAILS
    private class getAccountLive extends AsyncTask<String, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {


            StringRequest retrieveCompanyRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.api_company_retrieve_all),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("Company Response", response);
                            try {

                                JSONObject responseObject = new JSONObject(response);
                                JSONArray responseArray = responseObject.getJSONArray("response");
                                //SPLITTING THE RESPONSE INTO INDIVIDUAL COMPANIES
                                for (int i = 0; i < responseArray.length(); i++) {
                                    Accounts temp = new Accounts();
                                    JSONObject companyTemp = responseArray.getJSONObject(i);
                                    Log.i("Company Name", companyTemp.getString("company_name"));
                                    Log.i("Contact Person", companyTemp.getString("company_contact_person_text"));
                                    temp.setAccountId(companyTemp.getInt("company_id"));
                                    temp.setAccountName(companyTemp.getString("company_name"));
                                    temp.setAccountContactName(companyTemp.getString("company_contact_person_text"));
                                    accountsList.add(temp);
                                }
                                accountsAdapter = new AccountsRecyclerAdapter(getContext(), accountsList);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));

                                recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {

                                        Accounts temp = accountsList.get(position);
                                        Intent accountViewIntent = new Intent(getContext(), AccountView.class);
                                        accountViewIntent.putExtra("AccountId",temp.accountId);
                                        startActivity(accountViewIntent);

//                Toast.makeText(getContext(), "Let's Configure You next", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }
                                }));
                                recyclerView.setAdapter(accountsAdapter);
                                progressDialog.cancel();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.cancel();
                    Toast.makeText(context, getResources().getString(R.string.WebServiceError), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                    headers.put("_token", token);
                    return headers;
                }
            };

            requestQueue.add(retrieveCompanyRequest);
            return null;
        }
    }


}
