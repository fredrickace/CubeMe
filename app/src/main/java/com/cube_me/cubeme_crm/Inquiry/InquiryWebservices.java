package com.cube_me.cubeme_crm.Inquiry;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.R;
import com.cube_me.cubeme_crm.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.cube_me.cubeme_crm.BaseActivity.token;

/**
 * Created by FredrickCyril on 1/30/17.
 */

public class InquiryWebServices {

    Context context;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    InquiryWebServicesCommunicator communicator;
    public final static int UPDATE = 0;
    public final static int POST = 1;
    public final static boolean ADDED_SUCCESSFULLY = true;
    public final static boolean ERROR_ADDING = false;

    public InquiryWebServices(Context context) {
        this.context = context;
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage(context.getResources().getString(R.string.progressBarTitle));
        progressDialog.setIndeterminate(true);

    }

    public void getInquiry(int id) {

        progressDialog.show();
        String url = context.getResources().getString(R.string.api_inquiry_single) + id;
        //STRING REQUEST TO GET THE ASSGINED INQUIRY
        final StringRequest inquiryRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Inquiry inquiryCurrent = new Inquiry();
                        Log.i("Single Inquiry", response);
                        try {
                            JSONObject inquiryObjects = new JSONObject(response);

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
                                String dueDate = inquiryTemp.getString("sinquiry_due_datetime");
                                String[] fmtDueDate = dueDate.split(" ");


                                inquiryCurrent.setInquiryNo(inquiryTemp.getInt("sinquiry_id"));
                                inquiryCurrent.setInquiryID(inquiryTemp.getString("sinquiry_no"));
                                inquiryCurrent.setClientReferenceNo(inquiryTemp.getString("sinquiry_user_ref_no"));
                                inquiryCurrent.setInquirySubject(inquiryTemp.getString("sinquiry_subject"));
                                inquiryCurrent.setInquiryDescription(inquiryTemp.getString("sinquiry_description"));
                                inquiryCurrent.setInquiryCompanyName(inquiryTemp.getString("sinquiry_company"));
                                inquiryCurrent.setInquiryCompanyAbbr(inquiryTemp.getString("sinquiry_company_abbr"));
                                inquiryCurrent.setInquiryCompanyEmailID(inquiryTemp.getString("sinquiry_company_email_id"));
                                inquiryCurrent.setInquiryCompanyPhoneNumber(inquiryTemp.getString("sinquiry_company_phone_number"));
                                inquiryCurrent.setInquiryCompanyAddress(inquiryTemp.getString("sinquiry_company_address"));
                                inquiryCurrent.setInquiryCreateDate(fmtStartDate);
                                inquiryCurrent.setInquiryDueDate(fmtDueDate[0]);
                                inquiryCurrent.setInquiryDepartment(inquiryTemp.getString("sinquiry_inqdepartment_name"));
                                inquiryCurrent.setInquiryDepartmentCode(inquiryTemp.getString("sinquiry_inqdepartment_code"));
                                inquiryCurrent.setInquiryDepartmentId(inquiryTemp.getInt("sinquiry_inqdepartment_id"));
                                inquiryCurrent.setInquiryStatus(inquiryTemp.getString("sinquiry_inqstatus_name"));
                                inquiryCurrent.setInquiryStage(inquiryTemp.getString("sinquiry_inqstage_name"));
                                inquiryCurrent.setInquiryContactPerson(inquiryTemp.getString("sinquiry_cperson"));
                                inquiryCurrent.setInquiryContactPhoneNumber(inquiryTemp.getString("sinquiry_phone_number"));
                                inquiryCurrent.setInquiryContactEmailID(inquiryTemp.getString("sinquiry_email_id"));
                                inquiryCurrent.setContactPersonDesignation(inquiryTemp.getString("sinquiry_cperson_designation"));
                                inquiryCurrent.setInquirySource(inquiryTemp.getString("sinquiry_inqsrc_id"));
                                inquiryCurrent.setConsultant(inquiryTemp.getString("sinquiry_consultant"));
                                inquiryCurrent.setMainContractor(inquiryTemp.getString("sinquiry_main_contractor"));
                                inquiryCurrent.setSubContractor(inquiryTemp.getString("sinquiry_sub_contractor"));
                                inquiryCurrent.setInquiryComments(inquiryTemp.getString("sinquiry_comments"));
//                                inquiryCurrent.setProjectTypeID(inquiryTemp.getInt("sinquiry_pt_id"));
                                if (!String.valueOf(inquiryTemp.getString("sinquiry_pt_id")).equals("null")) {
                                    Log.i("Project ID", inquiryTemp.getString("sinquiry_pt_id"));
                                    inquiryCurrent.setProjectTypeID(Integer.valueOf(inquiryTemp.getString("sinquiry_pt_id")));
                                } else {
                                    Log.i("Project ID", "null");
                                    inquiryCurrent.setProjectTypeID(-1);
                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        communicator.onSelectedInquiry(inquiryCurrent);
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
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("_token", token);
                return headers;
            }
        };
        RetryPolicy policy = new DefaultRetryPolicy(BaseActivity.socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        inquiryRequest.setRetryPolicy(policy);
//            createdInquiryRequest.setRetryPolicy(new DefaultRetryPolicy(6000, 1, 1));

        requestQueue.add(inquiryRequest);
    }

    public void setCallBack(InquiryWebServicesCommunicator communicator) {
        this.communicator = communicator;
    }

    public void getSalesCode(final int departmentId, final String salesPrefix, final String companyName) {
        progressDialog.setMessage("Generating Sales Code!!! Please Wait!!!");
        progressDialog.show();
        StringRequest salesCodeRequest = new StringRequest(Request.Method.POST, context.getResources().getString(R.string.api_generate_sales_code),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String salesCode = "";
                        try {
                            JSONObject responseObject = new JSONObject(response);
                            if (responseObject.getBoolean("status") == true) {
                                salesCode = responseObject.getString("response");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.cancel();
                        communicator.onGeneratedSalesCode(salesCode);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return BaseActivity.getHeaders();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sales_prefix", salesPrefix);
                params.put("sales_company_name", companyName);
                params.put("sales_dept_id", String.valueOf(departmentId));

                return params;
            }

        };

        requestQueue.add(salesCodeRequest);

    }


    public void postInquiryLive(final Inquiry inquiry, int postMethod) {
        progressDialog.setMessage("Processing Inquiry");
        progressDialog.show();
        Log.i("InquriyNo", String.valueOf(inquiry.inquiryNo));
        String url = "";
        if (postMethod == POST) {
            url = context.getResources().getString(R.string.api_inquiry_post);
        } else if (postMethod == UPDATE) {
            url = context.getResources().getString(R.string.api_inquiry_post) + "/" + inquiry.inquiryNo;
        }
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Post Response", response);
                try {
                    JSONObject responseObject = new JSONObject(response);
                    if (responseObject.getBoolean("status")) {
                        Log.i("Inquiry Created", responseObject.getString("message"));
                        communicator.onCreateInquiryResponse(responseObject.getString("message"), true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressDialog.cancel();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                //MANDATORY FIELDS
                params.put("sinquiry_no", inquiry.inquiryID);
                params.put("sinquiry_subject", inquiry.inquirySubject);
                params.put("sinquiry_description", inquiry.inquiryDescription);
                params.put("sinquiry_inqdepartment_id", String.valueOf(inquiry.inquiryDepartmentId));
                params.put("sinquiry_company", inquiry.inquiryCompanyName);
                params.put("sinquiry_company_phone_number", inquiry.inquiryCompanyPhoneNumber);
                params.put("sinquiry_company_address", inquiry.inquiryCompanyAddress);
                params.put("sinquiry_cperson", inquiry.inquiryContactPerson);
                params.put("sia_account_id", String.valueOf(inquiry.salesPersonID));

                //NON MANDATORY FIELDS
                if (!inquiry.inquiryComments.equals("null")) {
                    params.put("sinquiry_comments", inquiry.inquiryComments);
                }
                if (!inquiry.clientReferenceNo.equals("null")) {
                    params.put("sinquiry_user_ref_no", inquiry.clientReferenceNo);
                }
                if (inquiry.projectTypeID != -1) {
                    params.put("sinquiry_pt_id", String.valueOf(inquiry.projectTypeID));
                }
                if (!inquiry.consultant.equals("null")) {
                    params.put("sinquiry_consultant", inquiry.consultant);
                }
                if (!inquiry.mainContractor.equals("null")) {
                    params.put("sinquiry_main_contractor", inquiry.mainContractor);
                }
                if (!inquiry.subContractor.equals("null")) {
                    params.put("sinquiry_sub_contractor", inquiry.subContractor);
                }
                if (!inquiry.inquiryCompanyEmailID.equals("null")) {
                    params.put("sinquiry_company_email_id", inquiry.inquiryCompanyEmailID);
                }
                if (!inquiry.inquiryContactPhoneNumber.equals("null")) {
                    Log.i("Phone Number", inquiry.inquiryContactPhoneNumber);
                    params.put("sinquiry_phone_number", inquiry.inquiryContactPhoneNumber);
                }
                if (!inquiry.inquiryContactEmailID.equals("null")) {
                    params.put("sinquiry_email_id", inquiry.inquiryContactEmailID);
                }
//                if(!inquiryObj.contactPersonDesignation.equals("null")){
//                    Log.i("Contact WDesignation", inquiryObj.contactPersonDesignation);
//                    params.put("sinquiry_cperson_desgination", inquiryObj.contactPersonDesignation);
//                }

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return BaseActivity.getHeaders();
            }
        };

        requestQueue.add(postRequest);
    }

    public void deleteInquiry(int inquiryNo) {
        String url = context.getResources().getString(R.string.api_inquiry_post) + "/" + inquiryNo;
        StringRequest inquiryDeleteRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseObject = new JSONObject(response);
                    if (responseObject.getBoolean("status")) {
                        Log.i("Delete Inquiry", responseObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return BaseActivity.getHeaders();
            }
        };

        requestQueue.add(inquiryDeleteRequest);


        //SAMPLE JSONOBJECT REQUEST

//        JsonObjectRequest testRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(), new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
    }

    public interface InquiryWebServicesCommunicator {

        public void onSelectedInquiry(Inquiry inquiry);

        public void onGeneratedSalesCode(String salesCode);

        public void onCreateInquiryResponse(String Message, boolean status);
    }
}

