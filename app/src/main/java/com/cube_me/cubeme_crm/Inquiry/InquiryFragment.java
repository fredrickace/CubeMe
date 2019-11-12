package com.cube_me.cubeme_crm.Inquiry;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.R;
import com.cube_me.cubeme_crm.VolleySingleton;
import com.cube_me.cubeme_crm.tabs.SlidingTabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cube_me.cubeme_crm.BaseActivity.token;

/**
 * A simple {@link Fragment} subclass.
 */
public class InquiryFragment extends Fragment {


    SlidingTabLayout tabLayout;
    ViewPager inquiryViewPager;


    FloatingActionButton inquiryAddFAB;
    List<Inquiry> createdInquiryList;
    List<Inquiry> assignedInquiryList;
    List<Inquiry> filteredList;
    Intent i;
    ProgressDialog progressDialog;
    int recyclerViewPosition;
    Context context;

    //FILTER VARIABLES
    String filterStartDate;
    String filterEndDate;
    List<String> filterClientNameList;
    List<String> filterInquiryStatusList;
    List<String> filterAssignedToList;

    DateFormat DATE_FORMAT = new SimpleDateFormat("d/MMM/yyyy");

    //FOR WEBSERVICE THROUGH VOLLEY
    RequestQueue requestQueue;//VOLLEY REQUEST QUEUE

    public InquiryFragment() {
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
        View view = inflater.inflate(R.layout.inquiry_fragment, container, false);
        BaseActivity.appToolbar.setTitle("Inquiry");

        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //BASIC INIT
        context = getContext();
        filterStartDate = "";
        filterEndDate = "";
        filterClientNameList = new ArrayList<>();
        filterInquiryStatusList = new ArrayList<>();
        filterAssignedToList = new ArrayList<>();
        createdInquiryList = new ArrayList<>();
        assignedInquiryList = new ArrayList<>();
        filteredList = new ArrayList<>();
//        createdInquiryList = getInquiryData();
//        filteredList = createdInquiryList;
        requestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();


        // SETTING UP FAB
        inquiryAddFAB = (FloatingActionButton) getView().findViewById(R.id.inquiry_addNewFAB);
        inquiryAddFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view,"We will Rock Baby",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                i = new Intent(getContext(), InquiryNew.class);
                i.putExtra("InquiryFlag", InquiryNew.INQUIRY_NEW);
                startActivity(i);
            }
        });

//        SETTING UP THE INQUIRY FRAGMENT VIEW PAGER
        inquiryViewPager = (ViewPager) getView().findViewById(R.id.inquiryFragment_pageViewer);
        FragmentManager fragmentManager = getChildFragmentManager();
        InquiryFragmentViewPageAdapter pageAdapter = new InquiryFragmentViewPageAdapter(fragmentManager, context);
        inquiryViewPager.setAdapter(pageAdapter);

        //SETTING UP THE SLIDING TAB LAYOUT
        tabLayout = (SlidingTabLayout) getView().findViewById(R.id.inquiryFragment_tab);
        tabLayout.setDistributeEvenly(true);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.ivory));

        tabLayout.setViewPager(inquiryViewPager);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == InquiryNew.RETURN_TRUE) {
//            Inquiry inquiryObj = data.getParcelableExtra("New Inquiry");
//            Toast.makeText(getContext(), inquiryObj.inquirySubject, Toast.LENGTH_SHORT).show(`);
//            createdInquiryList.add(inquiryObj);
//            updateRecycler(createdInquiryList);
//        }
//    }

    public void updateRecycler(List<Inquiry> inquiryList) {
//        recyclerAdapterCreated = new InquiryRecyclerAdapter(getContext(), inquiryList);
//        inquiryCreatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        inquiryCreatedRecyclerView.setAdapter(recyclerAdapterCreated);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_inquiry, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_inquiry_saved:
                Intent savedInquiryIntent = new Intent(getContext(), InquirySaved.class);
                startActivity(savedInquiryIntent);
                break;
            case R.id.action_inquiry_filter:
                openFilter();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = (MenuItem) menu.findItem(R.id.action_inquiry_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filteredList = getFilteredInquiry(query);
                updateRecycler(filteredList);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                filteredList = getFilteredInquiry(query);
                updateRecycler(filteredList);
                return true;
            }
        });
    }

    public List<Inquiry> getFilteredInquiry(String query) {
        List<Inquiry> result = new ArrayList<>();
        for (Inquiry inquiry : filteredList) {
            if (inquiry.inquirySubject.toLowerCase().contains(query) ||
                    (inquiry.inquiryStatus.toLowerCase().contains(query)) ||
                    (inquiry.salesPerson.toLowerCase().contains(query)) ||
                    (inquiry.inquiryCompanyName.toLowerCase().contains(query)) ||
                    (inquiry.inquiryID.toLowerCase().contains(query))) {
                result.add(inquiry);
            }
        }
        return result;
    }

    public List<Inquiry> getFilteredInquiry(String startDate, String endDate, List<String> clientNameList, List<String> inquiryStatusList,
                                            List<String> assignedToList) {
        List<Inquiry> result = new ArrayList<>();
        List<Inquiry> clientFilterList = new ArrayList<>();
        List<Inquiry> inquiryStatusFilteredList = new ArrayList<>();
        List<Inquiry> assignedToFilteredList = new ArrayList<>();
        if (clientNameList.isEmpty()) {
            clientFilterList = createdInquiryList;
        }

        for (Inquiry inquiryCompanyFiltered : createdInquiryList) {

            for (int i = 0; i < clientNameList.size(); i++) {
                if (inquiryCompanyFiltered.inquiryCompanyName.equals(clientNameList.get(i))) {
                    clientFilterList.add(inquiryCompanyFiltered);
                }
            }
        }

        if (inquiryStatusList.isEmpty()) {
            inquiryStatusFilteredList = clientFilterList;
        }
        for (Inquiry inquiryStatusFiltered : clientFilterList) {

            for (int i = 0; i < inquiryStatusList.size(); i++) {
                if (inquiryStatusFiltered.inquiryStatus.equals(inquiryStatusList.get(i))) {
                    inquiryStatusFilteredList.add(inquiryStatusFiltered);
                }
            }

        }

        if (assignedToList.isEmpty()) {
            assignedToFilteredList = inquiryStatusFilteredList;
        }
        for (Inquiry inquiryAssignedToFiltered : inquiryStatusFilteredList) {

            for (int i = 0; i < assignedToList.size(); i++) {
                if (inquiryAssignedToFiltered.salesPerson.equals(assignedToList.get(i))) {
                    assignedToFilteredList.add(inquiryAssignedToFiltered);
                }
            }
//            Log.i("Selected Client",inquiryAssignedToFiltered.inquiryCompanyName);
//            Log.i("Selected Client",inquiryAssignedToFiltered.inquiryStatus);
        }

        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            Date sDate = BaseActivity.getDateAsDate(startDate);
            Date eDate = BaseActivity.getDateAsDate(endDate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sDate);
            Calendar end = Calendar.getInstance();
            end.setTime(eDate);


            while (!calendar.getTime().after(eDate)) {
                Date date = calendar.getTime();

//                    Log.i("Date Range", date.toString());
//                    Log.i("Formated Date", BaseActivity.DATE_FORMAT.format(date));

                calendar.add(Calendar.DATE, 1);
            }
        }
        result = assignedToFilteredList;
        return result;
    }

    void openFilter() {
        InquiryFilterDialog filterDialog = new InquiryFilterDialog();
        Bundle bundle = new Bundle();
        bundle.putString("StartDate", filterStartDate);      /* SENDING THE VALUES TO DIALOG FRAGMENT TO SET UP THE PREVIOUS CHECKED STATE*/
        bundle.putString("EndDate", filterEndDate);
        ArrayList<String> clientList = (ArrayList<String>) filterClientNameList;
        bundle.putSerializable("ClientList", clientList);
        ArrayList<String> statusList = (ArrayList<String>) filterInquiryStatusList;
        bundle.putSerializable("StatusList", statusList);
        ArrayList<String> assignedList = (ArrayList<String>) filterAssignedToList;
        bundle.putSerializable("AssignedList", assignedList);
        filterDialog.setArguments(bundle);
        filterDialog.setCallBack(new InquiryFilterDialog.FilterCommunicator() {
            @Override
            public void getStartDate(String startDate) {
                filterStartDate = startDate;
            }

            @Override
            public void getEndDate(String endDate) {
                filterEndDate = endDate;
            }

            @Override
            public void getClientNameList(List<String> clientNameList) {
                filterClientNameList = clientNameList;
            }

            @Override
            public void getInquiryStatusList(List<String> inquiryStatusList) {
                filterInquiryStatusList = inquiryStatusList;
            }

            @Override
            public void getAssignedToList(List<String> assignedToList) {
                filterAssignedToList = assignedToList;
            }

            @Override
            public void filter() {
                filteredList = getFilteredInquiry(filterStartDate, filterEndDate, filterClientNameList,
                        filterInquiryStatusList, filterAssignedToList);
                updateRecycler(filteredList);
            }
        });
        filterDialog.show(getFragmentManager(), "InquiryFilter");
    }


    /* THIS SET OF CODINGS JUST USED FOR THE DEMO PURPOSES TO LOAD THE RECYCLER VIEW WITH DUMMY VARIABLES*/
    //   ADDING DATA TO THE RECYCLER VIEW ADAPTER
    public static List<Inquiry> getInquiryData() {


        List<Inquiry> list = new ArrayList<>();
        String[] inquiryNo = {"#Wo12", "#Sh29", "#GC32", "#AR112", "#AR102", "#QP27"};
        String[] inquirySubject = {"Door Installation", "Roof Painting", "Door Replacing", "Concrete Breaking", "Fire Doors", "Injection"};
        String[] inquiryCompany = {"CubeME", "HBK", "GCC", "Woqood", "GCC", "Qatar Petroleum"};
        String[] inquiryStatus = {"Open", "Estimation", "Quotation Sent", "Approved", "Rework", "Rejected"};
        String[] inquiryAssignedTo = {"Bharath", "Fazith", "Ram", "Christy", "Christy", "Prabhu"};

        for (int i = 0; i < inquiryNo.length; i++) {
            Inquiry current = new Inquiry();
            current.setInquiryID(inquiryNo[i]);
            current.setInquirySubject(inquirySubject[i]);
            current.setInquiryCompanyName(inquiryCompany[i]);
            current.setInquiryStatus(inquiryStatus[i]);
            current.setSalesPerson(inquiryAssignedTo[i]);
            list.add(current);

        }
        return list;
    }
/**/
}
