package com.cube_me.cubeme.Accounts;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cube_me.cubeme.Inquiry.Inquiry;
import com.cube_me.cubeme.Inquiry.InquiryFragment;
import com.cube_me.cubeme.Inquiry.InquiryRecyclerAdapter;
import com.cube_me.cubeme.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountViewAttachedFragment extends Fragment {

    //    ToggleButton toggleButton;
    LinearLayout testLinearLayout;
    ImageButton addImageButton;
    ImageButton removeImageButton;
    RecyclerView inquiryAttachedRecyclerView;
    InquiryRecyclerAdapter inquiryRecyclerAdapter;
    static List<Inquiry> inquiryList;


    Activity act;

    View view;

    public AccountViewAttachedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        act = getActivity();
        view = inflater.inflate(R.layout.account_view_attached_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        act = getActivity();
        inquiryList = InquiryFragment.getInquiryData();
        inquiryAttachedRecyclerView = (RecyclerView) act.findViewById(R.id.inquiryAttached_recycler);
        inquiryRecyclerAdapter = new InquiryRecyclerAdapter(getContext(), inquiryList);
        inquiryAttachedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        inquiryAttachedRecyclerView.setAdapter(inquiryRecyclerAdapter);

//        testLinearLayout = (LinearLayout) act.findViewById(R.id.toggleTestLinearLayout);

        addImageButton = (ImageButton) act.findViewById(R.id.addImageButton);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inquiryAttachedRecyclerView.setVisibility(View.VISIBLE);
//                testLinearLayout.setVisibility(View.VISIBLE);
                addImageButton.setVisibility(View.INVISIBLE);
                removeImageButton.setVisibility(View.VISIBLE);
            }
        });
        removeImageButton = (ImageButton) act.findViewById(R.id.removeImageButton);
        removeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inquiryAttachedRecyclerView.setVisibility(View.GONE);
//                testLinearLayout.setVisibility(View.GONE);
                addImageButton.setVisibility(View.VISIBLE);
                removeImageButton.setVisibility(View.INVISIBLE);
            }
        });


    }


    public void updateRecycler(){
        inquiryRecyclerAdapter = new InquiryRecyclerAdapter(getContext(), inquiryList);
        inquiryAttachedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        inquiryAttachedRecyclerView.setAdapter(inquiryRecyclerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateRecycler();
    }

}
