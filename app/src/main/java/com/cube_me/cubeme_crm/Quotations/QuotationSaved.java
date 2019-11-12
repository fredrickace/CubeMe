package com.cube_me.cubeme_crm.Quotations;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cube_me.cubeme_crm.ClickListener;
import com.cube_me.cubeme_crm.EditDeleteCallBackFragment;
import com.cube_me.cubeme_crm.EditDeleteFragment;
import com.cube_me.cubeme_crm.R;
import com.cube_me.cubeme_crm.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class QuotationSaved extends AppCompatActivity {

    Toolbar toolbar;
    QuotationViewListRecyclerAdapter quotationViewListRecyclerAdapter;
    List<Quotation> quotationList;
    RecyclerView quoRecyclerView;
    Context context;
    QuotationDataBaseAdapter quotationDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quotation_saved);

        //TOOLBAR INIT
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Offline Quotations");

        //BASIC INIT
        context = getApplicationContext();
        quoRecyclerView = (RecyclerView) findViewById(R.id.quoSaved_recycler);
        quotationDataBaseAdapter = new QuotationDataBaseAdapter(context);
        quotationList = new ArrayList<>();
        //SETTING UP THE RECYCLER VIEW
        quotationList = quotationDataBaseAdapter.getBaseQuotation();
        quotationViewListRecyclerAdapter = new QuotationViewListRecyclerAdapter(quotationList,context);
        quoRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, quoRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                EditDeleteCallBackFragment editDeleteCallBackFragment = new EditDeleteCallBackFragment();
                editDeleteCallBackFragment.setCallBack(new EditDeleteFragment.EditDeleteCommunicator() {
                    @Override
                    public void deleteFromDialog() {
                        new AlertDialog.Builder(QuotationSaved.this).setMessage(R.string.confirmDelete)
                                .setTitle("Warning")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Quotation temp = quotationList.get(position);
                                        int result = quotationDataBaseAdapter.deleteQuotation(temp.quotationNo);
                                        if(result<0){
                                            Toast.makeText(context, "Error while Deleting", Toast.LENGTH_SHORT).show();
                                        }else {
                                            quotationList.remove(position);
                                            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                        }
                                        updateRecycler();
                                    }
                                }).setNegativeButton("No",null).create().show();

                    }

                    @Override
                    public void editFromDialog() {

                    }

                    @Override   
                    public void viewFromDialog() {
                        Quotation temp = quotationList.get(position);
                        Quotation temp1 = quotationDataBaseAdapter.getQuotation(temp);
                        Intent viewIntent = new Intent(context,QuotationView.class);
                        viewIntent.putExtra("Quotation",temp1);
                        startActivity(viewIntent);
                    }
                });
                editDeleteCallBackFragment.show(getSupportFragmentManager(),"EditDelete");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        quoRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        quoRecyclerView.setAdapter(quotationViewListRecyclerAdapter);
    }

    public void updateRecycler(){
        quotationViewListRecyclerAdapter = new QuotationViewListRecyclerAdapter(quotationList,context);
        quoRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        quoRecyclerView.setAdapter(quotationViewListRecyclerAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
