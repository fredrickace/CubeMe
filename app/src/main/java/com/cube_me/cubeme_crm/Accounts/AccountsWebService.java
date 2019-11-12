package com.cube_me.cubeme_crm.Accounts;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cube_me.cubeme_crm.AddressObject;
import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.Email;
import com.cube_me.cubeme_crm.PhoneNumber;
import com.cube_me.cubeme_crm.R;
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
 * Created by FredrickCyril on 1/30/17.
 */

public class AccountsWebService {

    Context context;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    AccountsWebServiceCommunicator communicator;

    public AccountsWebService(Context context) {
        this.context = context;
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(context.getResources().getString(R.string.progressBarTitle));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    public void getBasicAccounts() {
        final StringRequest companyRequest = new StringRequest(Request.Method.GET, context.getResources().getString(R.string.api_company_retrieve_all),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<Accounts> accountsList = new ArrayList<>();
                        JSONObject responseObject = null;
                        try {
                            responseObject = new JSONObject(response);
                            JSONArray responseArray = responseObject.getJSONArray("response");
                            //SPLITTING THE RESPONSE INTO INDIVIDUAL COMPANIES
                            for (int i = 0; i < responseArray.length(); i++) {
                                Accounts temp = new Accounts();
                                JSONObject companyTemp = responseArray.getJSONObject(i);
                                temp.setAccountId(companyTemp.getInt("company_id"));
                                temp.setAccountName(companyTemp.getString("company_name"));
                                temp.setAccountAbbreviation(companyTemp.getString("company_abbr"));
                                temp.setAccountContactName(companyTemp.getString("company_contact_person_text"));

                                JSONArray phoneNumberArray = companyTemp.getJSONArray("company_phone");
                                temp.setPhoneNumberList(getPhoneNumberList(phoneNumberArray));

                                JSONArray emailArray = companyTemp.getJSONArray("company_email");
                                temp.setEmailList(getEmailList(emailArray));
                                accountsList.add(temp);

                                JSONArray addressArray = companyTemp.getJSONArray("company_address");
                                temp.setAddressObjectList(getAddressList(addressArray));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        communicator.onBasicAccountDetailsReceived(accountsList);
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
        requestQueue.add(companyRequest);
    }



    //METHOD TO GET THE LIST OF EMAIL FROM THE JSON ARRAY
    public List<Email> getEmailList(JSONArray emailArray){
        List<Email> emailList = new ArrayList<>();
        for (int j = 0; j < emailArray.length(); j++) {
            Email email = new Email();
            JSONObject emailObject = null;
            try {
                emailObject = emailArray.getJSONObject(j);
                email.setAccountEmailLabel(emailObject.getString("cemail_clabel_name"));
                email.setAccountEmail(emailObject.getString("cemail_email_id"));
                Log.i(emailObject.getString("cemail_clabel_name"),emailObject.getString("cemail_email_id"));
                emailList.add(email);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return emailList;
    }

    //METHOD TO GET THE LIST OF PHONE NUMBERS FROM THS JSON ARRAY
    public List<PhoneNumber> getPhoneNumberList(JSONArray phoneNumberArray) {
        List<PhoneNumber> phoneNumberList = new ArrayList<>();
        Log.i("Phone Number Size", String.valueOf(phoneNumberArray.length()));
        for (int j = 0; j < phoneNumberArray.length(); j++) {
            PhoneNumber phoneNumber = new PhoneNumber();
            JSONObject phoneNumberObject = null;
            try {
                phoneNumberObject = phoneNumberArray.getJSONObject(j);
                phoneNumber.setAccountPhoneLabel(phoneNumberObject.getString("cphone_clabel_name"));
                phoneNumber.setAccountPhoneNumber(phoneNumberObject.getString("cphone_number"));
                Log.i(phoneNumberObject.getString("cphone_clabel_name"),phoneNumberObject.getString("cphone_number"));
                phoneNumberList.add(phoneNumber);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return phoneNumberList;

    }

    //METHOD TO GET THE LIST OF ADDRESS FROM THE JSONARRAY
    public List<AddressObject> getAddressList(JSONArray addressArray){
        List<AddressObject> addressObjectList = new ArrayList<>();
        for(int i = 0; i<addressArray.length(); i++){
            try {
                AddressObject addressObject = new AddressObject();
                JSONObject address = addressArray.getJSONObject(i);
                addressObject.setAddressLabelName(address.getString("caddress_clabel_name"));
                addressObject.setAddressDetail(address.getString("caddress_detail"));
                addressObject.setAddressId(address.getInt("caddress_id"));

                addressObjectList.add(addressObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return addressObjectList;
    }

    public void setCallBack(AccountsWebServiceCommunicator communicator) {
        this.communicator = communicator;
    }

    public interface AccountsWebServiceCommunicator {
        public void onBasicAccountDetailsReceived(List<Accounts> accountsList);
    }
}
