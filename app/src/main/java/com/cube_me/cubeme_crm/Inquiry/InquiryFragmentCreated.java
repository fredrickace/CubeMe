package com.cube_me.cubeme_crm.Inquiry;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.ClickListener;
import com.cube_me.cubeme_crm.EditDeleteCallBackFragment;
import com.cube_me.cubeme_crm.EditDeleteFragment;
import com.cube_me.cubeme_crm.R;
import com.cube_me.cubeme_crm.RecyclerTouchListener;
import com.cube_me.cubeme_crm.SimpleDividerItemDecoration;
import com.cube_me.cubeme_crm.VolleySingleton;
import com.cube_me.cubeme_crm.tabs.SlidingTabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cube_me.cubeme_crm.BaseActivity.token;

/**
 * A simple {@link Fragment} subclass.
 */
public class InquiryFragmentCreated extends Fragment {

    InquiryRecyclerAdapter recyclerAdapterCreated;
    RecyclerView inquiryCreatedRecyclerView;
    int recyclerViewPosition;
    List<Inquiry> createdInquiryList;
    Context context;
    InquiryWebServices inquiryWebServices;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    public InquiryFragmentCreated() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.inquiry_fragment_create, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i("CreatedTab","Success");

        //BASIC INIT
        context = getContext();
        createdInquiryList = new ArrayList<>();
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading Created Inquiry List");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        inquiryWebServices = new InquiryWebServices(context);
        inquiryCreatedRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_inquiry);

        setCreatedInquiryList();
        //HANDLING THE CALLBACK FROM THE WEBSERVICES
        inquiryWebServices.setCallBack(new InquiryWebServices.InquiryWebServicesCommunicator() {
            @Override
            public void onSelectedInquiry(Inquiry inquiry) {
                Intent editInqIntent = new Intent(getContext(), InquiryNew.class);
                editInqIntent.putExtra("InquiryFlag", InquiryNew.INQUIRY_EDIT);
                editInqIntent.putExtra("ONLINE/OFFLINE", InquiryNew.INQUIRY_ONLINE);
                editInqIntent.putExtra("InquiryId", inquiry.inquiryID);
                editInqIntent.putExtra("Inquiry", inquiry);
                startActivity(editInqIntent);
            }

            @Override
            public void onGeneratedSalesCode(String salesCode) {

            }

            @Override
            public void onCreateInquiryResponse(String Message, boolean status) {

            }
        });



    }

    public void setCreatedInquiryList(){
        progressDialog.show();
        //STRING REQUEST TO GET THE CREATED INQUIRY
        final StringRequest createdInquiryRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.api_inquiry_create),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject inquiryObjects = new JSONObject(response);
                            if(inquiryObjects.getBoolean("status")) {
                                Log.i("Response", inquiryObjects.toString());
                                JSONArray responseObject = inquiryObjects.getJSONArray("response");/* ARRAY OF INQUIRIES*/

                                //SPLITTING THE INDIVIDUAL INQUIRY OBJECTS
                                for (int i = 0; i < responseObject.length(); i++) {
                                    JSONObject inquiryTemp = responseObject.getJSONObject(i);
                                    Log.i("Inquiry", inquiryTemp.toString());
                                    Log.i("Inquiry No", inquiryTemp.getString("sinquiry_no"));
                                    Log.i("Created Date", inquiryTemp.getString("sinquiry_start_datetime"));
                                    String startDate = inquiryTemp.getString("sinquiry_created_on");
                                    String fmtStartDate = BaseActivity.getFormattedDate(startDate);
//                                    String[] fmtStartDate = startDate.split(" ");

                                    Inquiry current = new Inquiry();
                                    current.setInquiryNo(inquiryTemp.getInt("sinquiry_id"));
                                    current.setInquiryID(inquiryTemp.getString("sinquiry_no"));
                                    current.setInquirySubject(inquiryTemp.getString("sinquiry_subject"));
                                    current.setInquiryCompanyName(inquiryTemp.getString("sinquiry_company"));
                                    current.setInquiryStatus(inquiryTemp.getString("sinquiry_inqstatus_name"));
                                    current.setInquiryStage(inquiryTemp.getString("sinquiry_inqstage_name"));
                                    current.setInquiryCreateDate(fmtStartDate);
//                                    current.setInquiryStatus(inquiryTemp.getString("sinquiry_inqstatus"));
                                    current.setInquiryContactPerson(inquiryTemp.getString("sinquiry_cperson"));
                                    createdInquiryList.add(current);
                                }

                                Log.i("List length", String.valueOf(createdInquiryList.size()));
//                            filteredList = createdInquiryList;
                                //SETTING THE INQUIRY RECYCLER VIEW
                                recyclerAdapterCreated = new InquiryRecyclerAdapter(getContext(), createdInquiryList);
                                inquiryCreatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                inquiryCreatedRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                inquiryCreatedRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
                                inquiryCreatedRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), inquiryCreatedRecyclerView, new ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {

                                        recyclerViewPosition = position;
                                        EditDeleteCallBackFragment editDeleteFragment = new EditDeleteCallBackFragment();
                                        editDeleteFragment.setCallBack(new EditDeleteFragment.EditDeleteCommunicator() {
                                            @Override
                                            public void deleteFromDialog() {
                                                Inquiry inquiry = createdInquiryList.get(recyclerViewPosition);
                                                inquiryWebServices.deleteInquiry(inquiry.inquiryNo);
                                            }

                                            @Override
                                            public void editFromDialog() {
                                                Inquiry inquiry = createdInquiryList.get(recyclerViewPosition);
                                                int inquiryID = inquiry.inquiryNo;
                                                inquiryWebServices.getInquiry(inquiryID);

                                            }

                                            @Override
                                            public void viewFromDialog() {
                                                Inquiry inquiry = createdInquiryList.get(recyclerViewPosition);
                                                Intent viewIntent = new Intent(context, InquiryView.class);
                                                viewIntent.putExtra("InquiryID",inquiry.inquiryNo);
                                                viewIntent.putExtra("ONLINE/OFFLINE", InquiryNew.INQUIRY_ONLINE);
                                                startActivity(viewIntent);

                                            }
                                        });
                                        editDeleteFragment.show(getFragmentManager(), "Edit");
//                                    EditDeleteFragment editDeleteFragment = new EditDeleteFragment();
//                                    editDeleteFragment.show(getFragmentManager(),"Edit");
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }
                                }));
                                inquiryCreatedRecyclerView.setAdapter(recyclerAdapterCreated);
                            }else {
                                Log.i("Inquiry Created", "Not found");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        progressDialog.cancel();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return BaseActivity.getHeaders();
            }
        };

        requestQueue.add(createdInquiryRequest);
    }


}
