package com.cube_me.cubeme.PurchaseOrder;


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
public class PurchaseFragment extends Fragment {


    RecyclerView poListRecycler;
    PurchaseListRecyclerAdapter recyclerAdapter;
    List<PurchaseOrder> purchaseOrderList;
    Context context;
    FloatingActionButton poAddFAB;
    TextView noContentTV;
    public PurchaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.purchase_fragment, container, false);

        BaseActivity.appToolbar.setTitle("Purchase Orders");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //BASIC INIT
        poListRecycler = (RecyclerView) getView().findViewById(R.id.poFragment_poListRecycler);
        poAddFAB = (FloatingActionButton) getView().findViewById(R.id.poFragment_addPOFAB);
        noContentTV = (TextView) getView().findViewById(R.id.poFragment_noContentTV);
        purchaseOrderList = new ArrayList<>();
        context = getContext();

        //CHECK BY ADDING A DATA WITHIN PO LIST
        PurchaseOrder check = new PurchaseOrder();
        check.setClientName("HBK");
        check.setPONumber("PO/HBK/DRS/CUBE/12-May-2016");
        check.setPOTitle("Fixing Doors");
        purchaseOrderList.add(check);

        //SETTING UP THE NO CONTENT TEXT VIEW IF THE PO LIST IS EMPTY
        if(purchaseOrderList.isEmpty()){
            noContentTV.setVisibility(View.VISIBLE);
        }

        //SETTING UP THE RECYCLER VIEW
        recyclerAdapter = new PurchaseListRecyclerAdapter(purchaseOrderList,context);
        poListRecycler.addItemDecoration(new SimpleDividerItemDecoration(context));
        poListRecycler.addOnItemTouchListener(new RecyclerTouchListener(context, poListRecycler, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        poListRecycler.setLayoutManager(new LinearLayoutManager(context));
        poListRecycler.setAdapter(recyclerAdapter);

        poAddFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),PurchaseOrderNew.class));
            }
        });
    }
}
