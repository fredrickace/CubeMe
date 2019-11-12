package com.cube_me.cubeme_crm.Accounts;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.cube_me.cubeme_crm.ClickListener;
import com.cube_me.cubeme_crm.Estimation.Estimation;
import com.cube_me.cubeme_crm.Estimation.EstimationListRecyclerAdapter;
import com.cube_me.cubeme_crm.Inquiry.Inquiry;
import com.cube_me.cubeme_crm.Inquiry.InquiryFragment;
import com.cube_me.cubeme_crm.Inquiry.InquiryRecyclerAdapter;
import com.cube_me.cubeme_crm.Jobs.Job;
import com.cube_me.cubeme_crm.Jobs.JobListRecyclerAdapter;
import com.cube_me.cubeme_crm.PurchaseOrder.PurchaseListRecyclerAdapter;
import com.cube_me.cubeme_crm.PurchaseOrder.PurchaseOrder;
import com.cube_me.cubeme_crm.Quotations.Quotation;
import com.cube_me.cubeme_crm.Quotations.QuotationViewListRecyclerAdapter;
import com.cube_me.cubeme_crm.R;
import com.cube_me.cubeme_crm.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountViewAttachedFragment extends Fragment {


    Context context;

//    //INQUIRY SECTION
//    ImageButton inquiryDownIB;
//    ImageButton inquiryUPIB;
//    RecyclerView inquiryAttachedRecyclerView;
//    InquiryRecyclerAdapter inquiryRecyclerAdapter;
//    static List<Inquiry> inquiryList;
//
//    //QUOTATION SECTION
//    ImageButton quoDownIB;
//    ImageButton quoUPIB;
//    RecyclerView quoRecycler;
//    QuotationViewListRecyclerAdapter quoRecyclerAdapter;
//    List<Quotation> quoList;
//
//    //ESTIMATION SECTION
//    ImageButton estimationDownIB;
//    ImageButton estimationUPIB;
//    RecyclerView estimationRecycler;
//    EstimationListRecyclerAdapter estRecyclerAdapter;
//    List<Estimation> estimationList;
//
//
//    //PO SECTION
//    ImageButton poDownIB;
//    ImageButton poUPIB;
//    RecyclerView poRecycler;
//    PurchaseListRecyclerAdapter poRecyclerAdapter;
//    List<PurchaseOrder> poList;
//
//    //JOBS SECTION
//    ImageButton jobsDownIB;
//    ImageButton jobsUPIB;
//    RecyclerView jobsRecycler;
//    JobListRecyclerAdapter jobRecyclerAdapter;
//    List<Job> jobList;
//
//
//    Activity act;
//
//    View view;
//
//    public AccountViewAttachedFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        act = getActivity();
//        view = inflater.inflate(R.layout.account_view_attached_fragment, container, false);
//        return view;
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        //BASIC INIT
//        act = getActivity();
//        context = getContext();
//
//        inquiryDownIB = (ImageButton) act.findViewById(R.id.accountAttached_inquiryDownIB);
//        inquiryUPIB = (ImageButton) act.findViewById(R.id.accountAttached_inquiryUPIB);
//        inquiryAttachedRecyclerView = (RecyclerView) act.findViewById(R.id.accountAttached_inquiryRecycler);
//        estimationDownIB = (ImageButton) act.findViewById(R.id.accountAttached_estDownIB);
//        estimationUPIB = (ImageButton) act.findViewById(R.id.accountAttached_estUpIB);
//        estimationRecycler = (RecyclerView) act.findViewById(R.id.accountAttached_estimationRecycler);
//        quoDownIB = (ImageButton) act.findViewById(R.id.accountAttached_quoDownIB);
//        quoUPIB = (ImageButton) act.findViewById(R.id.accountAttached_quoUPIB);
//        quoRecycler = (RecyclerView) act.findViewById(R.id.accountAttached_quotationRecycler);
//        poDownIB = (ImageButton) act.findViewById(R.id.accountAttached_PODownIB);
//        poUPIB = (ImageButton) act.findViewById(R.id.accountAttached_POUpIB);
//        poRecycler = (RecyclerView) act.findViewById(R.id.accountAttached_PORecycler);
//        jobsDownIB = (ImageButton) act.findViewById(R.id.accountAttached_jobDownIB);
//        jobsUPIB = (ImageButton) act.findViewById(R.id.accountAttached_jobUpIB);
//        jobsRecycler = (RecyclerView) act.findViewById(R.id.accountAttached_jobsRecycler);
//
//        //INQUIRY SECTION
//
//        inquiryDownIB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                inquiryAttachedRecyclerView.setVisibility(View.VISIBLE);
////                testLinearLayout.setVisibility(View.VISIBLE);
//                inquiryDownIB.setVisibility(View.INVISIBLE);
//                inquiryUPIB.setVisibility(View.VISIBLE);
//            }
//        });
//        inquiryUPIB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                inquiryAttachedRecyclerView.setVisibility(View.GONE);
////                testLinearLayout.setVisibility(View.GONE);
//                inquiryDownIB.setVisibility(View.VISIBLE);
//                inquiryUPIB.setVisibility(View.INVISIBLE);
//            }
//        });
//        /*SETTING UP THE INQUIRY RECYCLER ADAPTER*/
//
//        inquiryRecyclerAdapter = new InquiryRecyclerAdapter(getContext(), inquiryList);
//        inquiryAttachedRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, inquiryAttachedRecyclerView, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
//        inquiryAttachedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        inquiryAttachedRecyclerView.setAdapter(inquiryRecyclerAdapter);
//
//
//
//        //ESTIMATION SECTION
//        estimationDownIB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                estimationRecycler.setVisibility(View.VISIBLE);
//                estimationDownIB.setVisibility(View.GONE);
//                estimationUPIB.setVisibility(View.VISIBLE);
//            }
//        });
//
//        estimationUPIB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                estimationRecycler.setVisibility(View.GONE);
//                estimationDownIB.setVisibility(View.VISIBLE);
//                estimationUPIB.setVisibility(View.GONE);
//            }
//        });
//        /*SETTING UP THE ESTIMATION RECYCLER*/
//        estimationList = new ArrayList<>();
//        estRecyclerAdapter = new EstimationListRecyclerAdapter(estimationList,context);
//        estimationRecycler.addOnItemTouchListener(new RecyclerTouchListener(context, estimationRecycler, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
//        estimationRecycler.setLayoutManager(new LinearLayoutManager(context));
//        estimationRecycler.setAdapter(estRecyclerAdapter);
//
//        //QUOTATION SECTION
//        quoDownIB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                quoRecycler.setVisibility(View.VISIBLE);
//                quoDownIB.setVisibility(View.GONE);
//                quoUPIB.setVisibility(View.VISIBLE);
//            }
//        });
//
//        quoUPIB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                quoRecycler.setVisibility(View.GONE);
//                quoDownIB.setVisibility(View.VISIBLE);
//                quoUPIB.setVisibility(View.GONE);
//            }
//        });
//
//        /*SETTING UP THE QUOTATION RECYCLER VIEW*/
//        quoList = new ArrayList<>();
//        quoRecyclerAdapter = new QuotationViewListRecyclerAdapter(quoList,context);
//        quoRecycler.addOnItemTouchListener(new RecyclerTouchListener(context, quoRecycler, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
//        quoRecycler.setLayoutManager(new LinearLayoutManager(context));
//        quoRecycler.setAdapter(quoRecyclerAdapter);
//
//
//        //PO SECTION
//        poDownIB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                poRecycler.setVisibility(View.VISIBLE);
//                poDownIB.setVisibility(View.GONE);
//                poUPIB.setVisibility(View.VISIBLE);
//            }
//        });
//
//        poUPIB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                poRecycler.setVisibility(View.GONE);
//                poDownIB.setVisibility(View.VISIBLE);
//                poUPIB.setVisibility(View.GONE);
//            }
//        });
//
//        /*SETTING UP THE PO RECYCLER VIEW*/
//        poList = new ArrayList<>();
//        poRecyclerAdapter = new PurchaseListRecyclerAdapter(poList,context);
//        poRecycler.addOnItemTouchListener(new RecyclerTouchListener(context, poRecycler, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
//        poRecycler.setLayoutManager(new LinearLayoutManager(context));
//        poRecycler.setAdapter(poRecyclerAdapter);
//
//
//        //JOBS SECTION
//        jobsDownIB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                jobsRecycler.setVisibility(View.VISIBLE);
//                jobsDownIB.setVisibility(View.GONE);
//                jobsUPIB.setVisibility(View.VISIBLE);
//            }
//        });
//        jobsUPIB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                jobsRecycler.setVisibility(View.GONE);
//                jobsDownIB.setVisibility(View.VISIBLE);
//                jobsUPIB.setVisibility(View.GONE);
//            }
//        });
//
//        /*SETTING UP THE JOBS RECYCLER*/
//        jobList = new ArrayList<>();
//        jobRecyclerAdapter = new JobListRecyclerAdapter(jobList,context);
//        jobsRecycler.addOnItemTouchListener(new RecyclerTouchListener(context, jobsRecycler, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
//        jobsRecycler.setLayoutManager(new LinearLayoutManager(context));
//        jobsRecycler.setAdapter(jobRecyclerAdapter);
//    }
//
//
//    public void updateRecycler(){
//        inquiryRecyclerAdapter = new InquiryRecyclerAdapter(getContext(), inquiryList);
//        inquiryAttachedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        inquiryAttachedRecyclerView.setAdapter(inquiryRecyclerAdapter);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        updateRecycler();
//    }

}
