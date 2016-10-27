package com.cube_me.cubeme.Inquiry;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
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
import android.widget.Button;
import android.widget.Toast;

import com.cube_me.cubeme.BaseActivity;
import com.cube_me.cubeme.ClickListener;
import com.cube_me.cubeme.R;
import com.cube_me.cubeme.RecyclerTouchListener;
import com.cube_me.cubeme.SimpleDividerItemDecoration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InquiryFragment extends Fragment {

    RecyclerView inquiryRecyclerView;
    InquiryRecyclerAdapter recyclerAdapter;
    FloatingActionButton inquiryAddFAB;
    List<Inquiry> inquiryList;
    List<Inquiry> filteredList;
    Intent i;

    //FILTER VARIABLES
    String filterStartDate;
    String filterEndDate;
    List<String> filterClientNameList;
    List<String> filterInquiryStatusList;
    List<String> filterAssignedToList;

    DateFormat DATE_FORMAT = new SimpleDateFormat("d/MMM/yyyy");

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
        filterStartDate = "";
        filterEndDate = "";
        filterClientNameList = new ArrayList<>();
        filterInquiryStatusList = new ArrayList<>();
        filterAssignedToList = new ArrayList<>();
        inquiryList = new ArrayList<>();
        filteredList = new ArrayList<>();
        inquiryList = getInquiryData();
        filteredList = inquiryList;

        //SETTING UP THE RECYCLER VIEW
        inquiryRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_inquiry);
        recyclerAdapter = new InquiryRecyclerAdapter(getContext(), getInquiryData());
        inquiryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        inquiryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        inquiryRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        inquiryRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), inquiryRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        inquiryRecyclerView.setAdapter(recyclerAdapter);

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
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == InquiryNew.RETURN_TRUE) {
//            Inquiry inquiry = data.getParcelableExtra("New Inquiry");
//            Toast.makeText(getContext(), inquiry.inquirySubject, Toast.LENGTH_SHORT).show();
//            inquiryList.add(inquiry);
//            updateRecycler(inquiryList);
//        }
//    }

    public void updateRecycler(List<Inquiry> inquiryList) {
        recyclerAdapter = new InquiryRecyclerAdapter(getContext(), inquiryList);
        inquiryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        inquiryRecyclerView.setAdapter(recyclerAdapter);

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
                    (inquiry.inquiryAssignTo.toLowerCase().contains(query)) ||
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
        if(clientNameList.isEmpty()){
            clientFilterList = inquiryList;
        }

        for (Inquiry inquiryCompanyFiltered : inquiryList) {

            for(int i = 0; i<clientNameList.size(); i++){
                if(inquiryCompanyFiltered.inquiryCompanyName.equals(clientNameList.get(i))){
                    clientFilterList.add(inquiryCompanyFiltered);
                }
            }
        }

        if(inquiryStatusList.isEmpty()){
            inquiryStatusFilteredList = clientFilterList;
        }
        for (Inquiry inquiryStatusFiltered : clientFilterList){

            for(int i = 0; i<inquiryStatusList.size(); i++){
                if(inquiryStatusFiltered.inquiryStatus.equals(inquiryStatusList.get(i))){
                    inquiryStatusFilteredList.add(inquiryStatusFiltered);
                }
            }

        }

        if(assignedToList.isEmpty()){
            assignedToFilteredList = inquiryStatusFilteredList;
        }
        for (Inquiry inquiryAssignedToFiltered : inquiryStatusFilteredList){

            for(int i = 0; i<assignedToList.size(); i++){
                if(inquiryAssignedToFiltered.inquiryAssignTo.equals(assignedToList.get(i))){
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
        bundle.putString("StartDate",filterStartDate);      /* SENDING THE VALUES TO DIALOG FRAGMENT TO SET UP THE PREVIOUS CHECKED STATE*/
        bundle.putString("EndDate",filterEndDate);
        ArrayList<String> clientList = (ArrayList<String>) filterClientNameList;
        bundle.putSerializable("ClientList",clientList);
        ArrayList<String> statusList = (ArrayList<String>) filterInquiryStatusList;
        bundle.putSerializable("StatusList",statusList);
        ArrayList<String> assignedList = (ArrayList<String>) filterAssignedToList;
        bundle.putSerializable("AssignedList",assignedList);
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

    //   ADDING DATA TO THE RECYCLER VIEW ADAPTER
    public static List<Inquiry> getInquiryData() {


        List<Inquiry> inquiryList = new ArrayList<>();
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
            current.setInquiryAssignTo(inquiryAssignedTo[i]);
            inquiryList.add(current);

        }
        return inquiryList;
    }
}
