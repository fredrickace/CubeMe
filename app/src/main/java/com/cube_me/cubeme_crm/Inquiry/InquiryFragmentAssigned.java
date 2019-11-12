package com.cube_me.cubeme_crm.Inquiry;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
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
public class InquiryFragmentAssigned extends Fragment {

    InquiryRecyclerAdapter recyclerAdapterAssigned;
    RecyclerView inquiryAssignedRecyclerView;
    int recyclerViewPosition;
    List<Inquiry> assignedInquiryList;
    Context context;
    InquiryWebServices inquiryWebServices;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    public InquiryFragmentAssigned() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.inquiry_fragment_assigned, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //BASIC INIT
        context = getContext();
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading Assigned");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        inquiryWebServices = new InquiryWebServices(context);
        inquiryAssignedRecyclerView = (RecyclerView) getView().findViewById(R.id.inquiry_assignedRecycler);
        setAssignedInquiryList();
        assignedInquiryList = new ArrayList<>();

        //SETTING THE INQUIRY WEBSERVICES CALLBACK FOR COMMUNICATION
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

    public void setAssignedInquiryList() {

        progressDialog.show();
        //STRING REQUEST TO GET THE ASSGINED INQUIRY
        final StringRequest assignedInquiryRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.api_inquiry_assigned),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("Assigned Response", response);
                        try {
                            JSONObject inquiryObjects = new JSONObject(response);
                            Log.i("AssignedResponseObject", inquiryObjects.toString());
                            JSONArray responseObject = inquiryObjects.getJSONArray("response");/* ARRAY OF INQUIRIES*/


                            //SPLITTING THE INDIVIDUAL INQUIRY OBJECTS
                            for (int i = 0; i < responseObject.length(); i++) {
                                JSONObject inquiryTemp = responseObject.getJSONObject(i);
                                Log.i("Inquiry", inquiryTemp.toString());
                                Log.i("Inquiry No", inquiryTemp.getString("sinquiry_no"));
                                Log.i("Created Date", inquiryTemp.getString("sinquiry_start_datetime"));
                                String startDate = inquiryTemp.getString("sinquiry_created_on");
                                String fmtStartDate = BaseActivity.getFormattedDate(startDate);

                                Inquiry current = new Inquiry();
                                current.setInquiryNo(inquiryTemp.getInt("sinquiry_id"));
                                current.setInquiryID(inquiryTemp.getString("sinquiry_no"));
                                current.setInquirySubject(inquiryTemp.getString("sinquiry_subject"));
                                current.setInquiryCompanyName(inquiryTemp.getString("sinquiry_company"));
                                current.setInquiryCreateDate(fmtStartDate);

                                current.setInquiryStatus(inquiryTemp.getString("sinquiry_inqstatus_name"));
                                current.setInquiryStage(inquiryTemp.getString("sinquiry_inqstage_name"));
                                current.setInquiryContactPerson(inquiryTemp.getString("sinquiry_cperson"));
                                assignedInquiryList.add(current);
                            }

                            //SETTING THE INQUIRY ASSIGNED RECYCLER VIEW
                            recyclerAdapterAssigned = new InquiryRecyclerAdapter(getContext(), assignedInquiryList);
                            inquiryAssignedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            inquiryAssignedRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            inquiryAssignedRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
                            inquiryAssignedRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), inquiryAssignedRecyclerView, new ClickListener() {
                                @Override
                                public void onClick(View view, int position) {

                                    recyclerViewPosition = position;
                                    EditDeleteCallBackFragment editDeleteFragment = new EditDeleteCallBackFragment();
                                    editDeleteFragment.setCallBack(new EditDeleteFragment.EditDeleteCommunicator() {
                                        @Override
                                        public void deleteFromDialog() {
                                            Inquiry inquiry = assignedInquiryList.get(recyclerViewPosition);
                                            inquiryWebServices.deleteInquiry(inquiry.inquiryNo);
                                            assignedInquiryList = new ArrayList<Inquiry>();
                                            setAssignedInquiryList();

                                        }

                                        @Override
                                        public void editFromDialog() {
                                            Inquiry inquiry = assignedInquiryList.get(recyclerViewPosition);
                                            inquiryWebServices.getInquiry(inquiry.inquiryNo);
                                        }

                                        @Override
                                        public void viewFromDialog() {

                                        }
                                    });
                                    editDeleteFragment.show(getFragmentManager(), "Edit");
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                            inquiryAssignedRecyclerView.setAdapter(recyclerAdapterAssigned);

                            Log.i("Assigned List length", String.valueOf(assignedInquiryList.size()));

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
        RetryPolicy policy = new DefaultRetryPolicy(BaseActivity.socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        assignedInquiryRequest.setRetryPolicy(policy);
        requestQueue.add(assignedInquiryRequest);


    }


}
