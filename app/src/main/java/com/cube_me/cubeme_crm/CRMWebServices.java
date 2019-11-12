package com.cube_me.cubeme_crm;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cube_me.cubeme_crm.BaseActivity.token;

/**
 * Created by FredrickCyril on 1/29/17.
 */

public class CRMWebServices {

    Context context;
    RequestQueue requestQueue;
    MasterWebServiceCommunicator communicator;

    public CRMWebServices(Context context) {
        this.context = context;
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
    }


    public void getProjectType() {

        StringRequest projectTypeRequest = new StringRequest(Request.Method.GET, context.getResources().getString(R.string.api_master_projectType),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        List<MasterProjectType> projectTypeList = new ArrayList<>();
                        try {
                            JSONObject responseObject = new JSONObject(response);
                            if (responseObject.getBoolean("status") == true) {
                                Log.i("Project Type", response);
                                JSONArray projectTypeArray = responseObject.getJSONArray("response");
                                for (int i = 0; i < projectTypeArray.length(); i++) {
                                    JSONObject projectTypeObject = projectTypeArray.getJSONObject(i);
                                    MasterProjectType masterProjectType = new MasterProjectType();
                                    masterProjectType.setProjectTypeID(projectTypeObject.getInt("pt_id"));
                                    masterProjectType.setProjectTypeAbb(projectTypeObject.getString("pt_abbr"));
                                    masterProjectType.setProjectTypeName(projectTypeObject.getString("pt_name"));
                                    projectTypeList.add(masterProjectType);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        communicator.setProjectType(projectTypeList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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

        requestQueue.add(projectTypeRequest);

    }

    public void getProjectDivision() {

        StringRequest projectDivisionRequest = new StringRequest(Request.Method.GET, context.getResources().getString(R.string.api_master_division),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<ProjectDivision> projectDivisionList = new ArrayList<>();
                        try {
                            JSONObject responseObject = new JSONObject(response);
                            if(responseObject.getBoolean("status") == true){
                                JSONArray divisionArray = responseObject.getJSONArray("response");
                                Log.i("Inquiry Deparment", response);
                                for(int i = 0; i<divisionArray.length(); i++){
                                    ProjectDivision currentDivision = new ProjectDivision();
                                    JSONObject divisionObject = divisionArray.getJSONObject(i);
                                    currentDivision.setDivisionID(divisionObject.getInt("inqdepartment_id"));
                                    currentDivision.setDivisionName(divisionObject.getString("inqdepartment_name"));
                                    currentDivision.setDivisionCode(divisionObject.getString("inqdepartment_code"));
                                    currentDivision.setDivisionAbbr(divisionObject.getString("inqdepartment_abbr"));

                                    projectDivisionList.add(currentDivision);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        communicator.setProjectDivision(projectDivisionList);

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
        };

        requestQueue.add(projectDivisionRequest);
    }

    public void getInquirySource() {

        StringRequest inquirySrcRequest = new StringRequest(Request.Method.GET, context.getResources().getString(R.string.api_master_inquirySrc),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<MasterInquirySource> inquirySourceList = new ArrayList<>();

                        communicator.setInquirySource(inquirySourceList);

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
        };

        requestQueue.add(inquirySrcRequest);
    }

    public void getSalesPerson() {

        StringRequest salesPersonRequest = new StringRequest(Request.Method.GET, context.getResources().getString(R.string.api_master_salesPerson),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<MasterSalesPerson> salesPersonList = new ArrayList<>();

                        try {
                            JSONObject responseObject = new JSONObject(response);
                            if(responseObject.getBoolean("status") == true){
                                JSONArray salesPersonArray = responseObject.getJSONArray("response");
                                for(int i = 0; i<salesPersonArray.length(); i++){
                                    MasterSalesPerson temp = new MasterSalesPerson();
                                    JSONObject salesPersonObject = salesPersonArray.getJSONObject(i);
                                    temp.setSalesPersonID(salesPersonObject.getInt("account_id"));
                                    temp.setSalesPersonCode(salesPersonObject.getString("account_usercode"));
                                    temp.setSalesPersonEmail(salesPersonObject.getString("account_email_id"));
                                    temp.setSalesPersonName(salesPersonObject.getString("account_username"));
                                    salesPersonList.add(temp);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        communicator.setSalesPerson(salesPersonList);

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
        };

        requestQueue.add(salesPersonRequest);
    }

    public void getAppConstants(){

        StringRequest appConstantRequest = new StringRequest(Request.Method.GET, context.getResources().getString(R.string.api_master_app_constants),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        MasterAppConstants masterAppConstants = new MasterAppConstants();
                        try {
                            JSONObject responseObject = new JSONObject(response);
                            if(responseObject.getBoolean("status")){
                                JSONObject appConstantObject = responseObject.getJSONObject("response");
                                masterAppConstants.setStringSeparator(appConstantObject.getString("APP_STRING_SEPARATOR"));
                                masterAppConstants.setSalesCode(appConstantObject.getString("APP_SALES_STATIC_CODE"));
                                masterAppConstants.setInquiryPrefix(appConstantObject.getString("APP_SALES_INQUIRY_CODE_PREFIX"));
                                masterAppConstants.setEstimationPrefix(appConstantObject.getString("APP_SALES_ESTIMATION_CODE_PREFIX"));
                                masterAppConstants.setEstimationJobPrefix(appConstantObject.getString("APP_SALES_ESTIMATION_JOB_CODE_PREFIX"));
                                masterAppConstants.setQuotationPrefix(appConstantObject.getString("APP_SALES_QUOTATION_CODE_PREFIX"));
                                masterAppConstants.setPoPrefix(appConstantObject.getString("APP_SALES_PURCHASE_ORDER_CODE_PREFIX"));
                                masterAppConstants.setJobPrefix(appConstantObject.getString("APP_SALES_JOB_CODE_PREFIX"));

                            }
                        } catch (JSONException e) {


                            e.printStackTrace();
                        }

                        communicator.setAppConstants(masterAppConstants);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Message", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return BaseActivity.getHeaders();
            }
        };

        requestQueue.add(appConstantRequest);
    }


    public void setCallBack(MasterWebServiceCommunicator communicator) {
        this.communicator = communicator;
    }

    public interface MasterWebServiceCommunicator {
        public void setProjectType(List<MasterProjectType> projectTypeList);

        public void setProjectDivision(List<ProjectDivision> projectDivisionList);

        public void setInquirySource(List<MasterInquirySource> masterInquirySourceList);

        public void setSalesPerson(List<MasterSalesPerson> masterSalesPersonList);

        public void setAppConstants(MasterAppConstants masterAppConstants);
    }
}
