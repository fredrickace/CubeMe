package com.cube_me.cubeme.Estimation;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cube_me.cubeme.BaseActivity;
import com.cube_me.cubeme.ClickListener;
import com.cube_me.cubeme.R;
import com.cube_me.cubeme.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstimationFragment extends Fragment {


    public static final int RETURN_TRUE = 0;
    public static final int RETURN_FALSE = 1;

    List<Estimation> estimationList;
    RecyclerView estimationRecyclerView;
    EstimationFragmentRecyclerAdapter recyclerAdapter;
    TextView noContentTV;

    public EstimationFragment() {
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
        // INFLATE THE LAYOUT FOR THIS FRAGMENT]
        View view = inflater.inflate(R.layout.estimation_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BaseActivity.appToolbar.setTitle("Estimation");
        estimationList = new ArrayList<>();

        noContentTV = (TextView) getView().findViewById(R.id.estimationFrag_noContentTV);
        if(estimationList.size()<0){
            noContentTV.setVisibility(View.VISIBLE);
        }

        estimationRecyclerView = (RecyclerView) getView().findViewById(R.id.estimationFrag_recycler);
        recyclerAdapter = new EstimationFragmentRecyclerAdapter(estimationList,getContext());
        estimationRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), estimationRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getContext(), "Tested Okay", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        estimationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        estimationRecyclerView.setAdapter(recyclerAdapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Estimation estimation = new Estimation();
        if(resultCode == RETURN_TRUE){
            estimation = data.getParcelableExtra("New Estimation");
            estimationList.add(estimation);
            Toast.makeText(getContext(), estimation.estimationID, Toast.LENGTH_SHORT).show();
            noContentTV.setVisibility(View.GONE);
            updateRecycler();

        }
    }

    public void updateRecycler(){
        recyclerAdapter = new EstimationFragmentRecyclerAdapter(estimationList,getContext());
        estimationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        estimationRecyclerView.setAdapter(recyclerAdapter);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_estimation_fragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_EstimationFragment_loadSaved:
                Intent i = new Intent(getContext(), EstimationSaved.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
