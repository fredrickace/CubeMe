package com.cube_me.cubeme.Jobs;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
public class JobFragment extends Fragment {


    RecyclerView jobListRecycler;
    FloatingActionButton jobAddFAB;
    List<Job> jobList;
    JobListRecyclerAdapter jobListRecyclerAdapter;
    Context context;
    TextView noContentTV;
    public JobFragment() {
        //REQUIRED EMPTY PUBLIC CONSTRUCTOR
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //INFLATE THE LAYOUT FOR THIS FRAGMENT
        View view =  inflater.inflate(R.layout.job_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BaseActivity.appToolbar.setTitle("Jobs");

        //BASIC INIT
        jobListRecycler = (RecyclerView) getView().findViewById(R.id.jobFrag_recycler);
        jobList = new ArrayList<>();
        context = getContext();
        noContentTV = (TextView) getView().findViewById(R.id.jobFrag_noContentTV);


        //CHECK WITH ADDING ONE JOB
        Job check = new Job();
        check.setJobNumber("JOB/HBK/DRS/CUBE/1234");
        check.setJobName("Door Job");
        check.setJobClient("HBK");
        check.setJobStartDate("12/Jun/2016");
        check.setJobEndDate("1/Oct/2016");
        check.setJobAssigned("Fazith");

        jobList.add(check);

       // SETTING UP NO DATA FOUND DISPLAY IF JOBLIST IS EMPTY
        if(!jobList.isEmpty()){
            noContentTV.setVisibility(View.GONE);
        }


        //SETTING UP RECYCLERVIEW
        jobListRecyclerAdapter = new JobListRecyclerAdapter(jobList,context);
        jobListRecycler.addItemDecoration(new SimpleDividerItemDecoration(context));
        jobListRecycler.addOnItemTouchListener(new RecyclerTouchListener(context, jobListRecycler, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        jobListRecycler.setLayoutManager(new LinearLayoutManager(context));
        jobListRecycler.setAdapter(jobListRecyclerAdapter);


        jobAddFAB = (FloatingActionButton) getView().findViewById(R.id.jobFrag_addJobFAB);
        jobAddFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),JobNew.class));
//                getActivity().overridePendingTransition(R.anim.move_up,R.anim.move_down);
            }
        });
    }
}
