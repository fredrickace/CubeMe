package com.cube_me.cubeme.Inquiry;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
public class InquiryFragment extends Fragment {

    RecyclerView inquiryRecyclerView;
    static InquiryRecyclerAdapter recyclerAdapter;
    FloatingActionButton inquiryAddFAB;

    public InquiryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.inquiry_fragment, container, false);
        BaseActivity.appToolbar.setTitle("Inquiry");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Setting up Recycler View
        inquiryRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_inquiry);
        recyclerAdapter = new InquiryRecyclerAdapter(getContext(),getInquiryData());
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

//        Setting Up FAB
        inquiryAddFAB = (FloatingActionButton) getView().findViewById(R.id.inquiry_addNewFAB);
        inquiryAddFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view,"We will Rock Baby",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                startActivity(new Intent(getContext(),InquiryNew.class));
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_inquiry,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //    Adding Data to the the recvclerViewAdapter
    public List<Inquiry> getInquiryData(){

        List<Inquiry> data = new ArrayList<>();
        String[] inquiryNo = {"#Wo12","#Sh29","#GC32","#AR112","#AR102","#QP27"};
        String[] inquirySubject = {"Door Installation", "Roof Painting","Door Replacing","Concrete Breaking","Fire Doors","Injection"};
        String[] inquiryCompany = {"Woqood","Shell","GCC","AlRayaan","AlRayaan","Qatar Petroleum"};
        String[] inquiryStatus = {"Open","Estimation","Quotation Stage","Approved", "Rework","Rejected"};
        String[] inquiryAssignedTo = {"Bharath","Fazith","Ram","Dhinesh","Christy","Prabhu"};

        for(int i = 0; i<inquiryNo.length; i++){
            Inquiry current = new Inquiry();
            current.setInquiryNumber(inquiryNo[i]);
            current.setInquirySubject(inquirySubject[i]);
            current.setInquiryCompanyName(inquiryCompany[i]);
            current.setInquiryStatus(inquiryStatus[i]);
            current.setInquiryAssignTo(inquiryAssignedTo[i]);
            data.add(current);

        }
        return data;
    }
}
