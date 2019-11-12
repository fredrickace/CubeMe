package com.cube_me.cubeme_crm.Quotations;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.ClickListener;
import com.cube_me.cubeme_crm.R;
import com.cube_me.cubeme_crm.RecyclerTouchListener;
import com.cube_me.cubeme_crm.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuotationFragment extends Fragment {

    RecyclerView quotationListRecycler;
    TextView noContentTV;
    QuotationViewListRecyclerAdapter quotationViewListRecyclerAdapter;
    List<Quotation> quotationList;
    Context context;

    public QuotationFragment() {
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
        View view= inflater.inflate(R.layout.quotation_fragment, container, false);
        BaseActivity.appToolbar.setTitle("Quotations");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //BASIC INIT
        quotationListRecycler = (RecyclerView) getView().findViewById(R.id.quotationFragment_Recycler);
        noContentTV = (TextView) getView().findViewById(R.id.quotationFragment_NoContentTV);
        quotationList = new ArrayList<>();
        context = getContext();

        //INITIAL CHECK
        Quotation check = new Quotation();
        check.setQuotationClient("HBK");
        check.setQuotationTitle("Doors Inquiry");
        check.setQuotationTotal(8000);
        check.setQuotationNo("Quo/HBK/Cub/DRS/29-July-2016");
        check.setQuotationStatus("Approved");
        check.setQuotationCreateDate("17/Feb/2016");
        check.setQuotationEditDate("17/May/2016");
        quotationList.add(check);

        if(!quotationList.isEmpty()){
            noContentTV.setVisibility(View.GONE);
        }

        //SETTING UP RECYCLER
        quotationViewListRecyclerAdapter = new QuotationViewListRecyclerAdapter(quotationList, context);
        quotationListRecycler.addItemDecoration(new SimpleDividerItemDecoration(context));
        quotationListRecycler.addOnItemTouchListener(new RecyclerTouchListener(context, quotationListRecycler, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        quotationListRecycler.setLayoutManager(new LinearLayoutManager(context));
        quotationListRecycler.setAdapter(quotationViewListRecyclerAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_quotation_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_quoFrag_offline:
                Intent offlineIntent = new Intent(getContext(),QuotationSaved.class);
                startActivity(offlineIntent);
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
