package com.cube_me.cubeme_crm.Estimation;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.R;
import com.cube_me.cubeme_crm.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by FredrickCyril on 3/8/17.
 */

public class EstimationWebServices {

    Context context;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    public EstimationWebServices(Context context) {
        this.context = context;
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage(context.getResources().getString(R.string.progressBarTitle));
        progressDialog.setIndeterminate(true);
    }

    public void prepareEstimationNew(final String inquiryID, final String companyName, final Estimation estimation, int inqNo){

        String url = context.getResources().getString(R.string.api_estimation_prepare) + "/"+ inqNo;

        StringRequest prepareEstimationRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseObject = new JSONObject(response);
                            if(responseObject.getBoolean("status")){
                                Toast.makeText(context, "Estimation Successfully Created", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Estimation Creation Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("sinquiry_no", inquiryID);
                params.put("sinquiry_company", companyName);
                params.put("sestimate_project_title", estimation.projectTitle);

                //GETTING THE JOB LIST FROM THE ESTIMATION AND SENDING EACH JOB AS AN ARRAY
                List<EstimationJobs> estimationJobsList = estimation.jobsList;
                ArrayList<Float> jobTotalArray = new ArrayList<>();
                for(int i = 0; i< estimationJobsList.size(); i++){
                    EstimationJobs estimationJobs = estimationJobsList.get(i);

                    params.put("sej_name["+i+"]", estimationJobs.jobName);
                    params.put("sej_measuring_unit["+i+"]", estimationJobs.jobUnit);
                    params.put("sej_quantity["+i+"]", String.valueOf(estimationJobs.jobQuantity));
                    params.put("sej_description["+i+"]", estimationJobs.jobDescription);
                    params.put("sej_total["+i+"]", String.valueOf(estimationJobs.jobTotal));
                    jobTotalArray.add(estimationJobs.jobTotal);
                }
                Log.i("Job Array", jobTotalArray.toString());
                params.put("sestimate_job_total", jobTotalArray.toString());

                //GETTING THE DIRECT MATERIAL LIST AND SENDING THEM EACH IN AN ARRAY
                List<EstimationDirectMaterial> estimationDirectMaterialList = estimation.estimationDirectMaterialList;
                if(estimationDirectMaterialList.size()>0) {//CHECKING IF THE DIRECT MATERIALS LIST ARE GREATER THAN ONE
                    for (int i = 0; i < estimationDirectMaterialList.size(); i++) {

                        EstimationDirectMaterial directMaterial = estimationDirectMaterialList.get(i);
                        params.put("sej_1_job[" + i + "]", directMaterial.jobName);
                        params.put("sej_1_material[" + i + "]", directMaterial.materialName);
                        params.put("sej_1_dimension[" + i + "]", directMaterial.materialDimension);
                        params.put("sej_1_uom[" + i + "]", directMaterial.materialUOM);
                        params.put("sej_1_budget[" + i + "]", String.valueOf(directMaterial.materialBudget));
                        params.put("sej_1_price[" + i + "]", String.valueOf(directMaterial.materialUnitPrice));
                        params.put("sej_1_quantity[" + i + "]", String.valueOf(directMaterial.materialNoOfUnits));
                        params.put("sej_1_amount[" + i + "]", String.valueOf(directMaterial.materialTotal));

                    }
                    params.put("sej_1_subtotal", String.valueOf(estimation.directMaterialTotal));//DIRECT MATERIAL TOTAL
                }//END OF SECTION

                //GETTING THE MAN POWER LIST AND SENDING THEN EACH IN AN ARRAY
                List<EstimationOtherMaterials> manPowerList = estimation.manPowerList;
                if(manPowerList.size()>0){
                    for (int i = 0; i<manPowerList.size(); i++){
                        EstimationOtherMaterials manPower = manPowerList.get(i);
                        params.put("sej_2_job[" + i +"]", manPower.jobName);
                        params.put("sej_2_name[" + i +"]", manPower.otherMaterialName);
                        params.put("sej_2_uom[" +i+"]", manPower.otherMaterialUOM);
                        params.put("sej_2_units[" +i+"]", String.valueOf(manPower.otherMaterialNoOfUnits));
                        params.put("sej_2_no_of_res[" +i+"]", String.valueOf(manPower.otherMaterialNoOfResources));
                        params.put("sej_2_budget[" +i+"]", String.valueOf(manPower.otherMaterialBudgetPerUnit));
                        params.put("sej_2_price[" +i+ "]", String.valueOf(manPower.otherMaterialPricePerUnit));
                        params.put("sej_2_amount[" +i+ "]", String.valueOf(manPower.otherMaterialTotal));

                    }
                    params.put("sej_2_subtotal", String.valueOf(estimation.manPowerTotal));
                }//END OF SECTION

                //GETTING THE TOOLS LIST AND SENDING THEM EACH IN AN ARRAY
                List<EstimationOtherMaterials> toolsList = estimation.toolsList;
                if(toolsList.size()>0){
                    for(int i = 0; i<toolsList.size(); i++){
                        EstimationOtherMaterials tools = toolsList.get(i);
                        params.put("sej_3_job[" + i +"]", tools.jobName);
                        params.put("sej_3_name[" + i +"]", tools.otherMaterialName);
                        params.put("sej_3_uom[" +i+"]", tools.otherMaterialUOM);
                        params.put("sej_3_units[" +i+"]", String.valueOf(tools.otherMaterialNoOfUnits));
                        params.put("sej_3_no_of_res[" +i+"]", String.valueOf(tools.otherMaterialNoOfResources));
                        params.put("sej_3_budget[" +i+"]", String.valueOf(tools.otherMaterialBudgetPerUnit));
                        params.put("sej_3_price[" +i+ "]", String.valueOf(tools.otherMaterialPricePerUnit));
                        params.put("sej_3_amount[" +i+ "]", String.valueOf(tools.otherMaterialTotal));

                    }
                    params.put("sej_3_subtotal", String.valueOf(estimation.toolsTotal));
                }//END OF SECTION

                //GETTING THE TRANSPORT LIST AND SENDING THEM IN AN ARRAY
                List<EstimationOtherMaterials> transportList = estimation.transportList;
                if(transportList.size()>0){
                    for(int i =0 ;i<transportList.size(); i++){
                        EstimationOtherMaterials transport = transportList.get(i);
                        params.put("sej_4_job[" + i +"]", transport.jobName);
                        params.put("sej_4_name[" + i +"]", transport.otherMaterialName);
                        params.put("sej_4_uom[" +i+"]", transport.otherMaterialUOM);
                        params.put("sej_4_units[" +i+"]", String.valueOf(transport.otherMaterialNoOfUnits));
                        params.put("sej_4_no_of_res[" +i+"]", String.valueOf(transport.otherMaterialNoOfResources));
                        params.put("sej_4_budget[" +i+"]", String.valueOf(transport.otherMaterialBudgetPerUnit));
                        params.put("sej_4_price[" +i+ "]", String.valueOf(transport.otherMaterialPricePerUnit));
                        params.put("sej_4_amount[" +i+ "]", String.valueOf(transport.otherMaterialTotal));
                    }


                    params.put("sej_4_subtotal", String.valueOf(estimation.transportTotal));
                }//END OF SECTION

                //GETTING THE CONSUMABLES LIST AND SENDING THEM IN AN ARRAY
                List<EstimationConsumables> consumablesList = estimation.consumableList;
                if(consumablesList.size()>0){
                    for(int i = 0; i<consumablesList.size(); i++){
                        EstimationConsumables consumables = consumablesList.get(i);

                        params.put("sej_5_job[" +i+"]",consumables.jobName);
                        params.put("sej_5_name[" +i+"]", consumables.consumableName);
                        params.put("sej_5_uom["+i+ "]", consumables.consumableUOM);
                        params.put("sej_5_units["+i+ "]", String.valueOf(consumables.consumableNoOfUnits));
                        params.put("sej_5_budget[" +i+"]", String.valueOf(consumables.consumableBudgetPrice));
                        params.put("sej_5_unit_price[" +i+ "]", String.valueOf(consumables.consumableUnitPrice));
                        params.put("sej_5_amount[" +i+ "]", String.valueOf(consumables.consumableTotal));

                    }
                    params.put("sej_5_subtotal", String.valueOf(estimation.consumablesTotal));
                }//END OF SECTION

                //ADDING THE TOTAL, MARGIN PERCENTAGE AND THE OVERHEAD PERCENTAGE
                params.put("sestimate_overhead_percentage", String.valueOf(estimation.overHeadPercentage));
                params.put("sestimate_margin_percentage", String.valueOf(estimation.marginPercentage));
                params.put("sestimate_grand_total", String.valueOf(estimation.grandTotal));



                return checkParams(params);
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return BaseActivity.getHeaders();
            }



            };

        requestQueue.add(prepareEstimationRequest);

    }

    private Map<String, String> checkParams(Map<String, String> map) {
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
            if (pairs.getValue() == null) {
                map.put(pairs.getKey(), "");
            }
        }
        return map;

    }
}
