package com.cube_me.cubeme_crm.Contacts;

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
 * Created by FredrickCyril on 1/28/17.
 */

public class ContactWebService {

    Context context;
    RequestQueue requestQueue;
    List<Contact> contactList;
    ProgressDialog progressDialog;
    ContactsWebServiceInterface communicator;

    public ContactWebService(Context context) {
        this.context = context;
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.progressBarTitle));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(true);

    }

    public void getContacts() {
        progressDialog.show();
        contactList = new ArrayList<>();
        StringRequest allContactsRequest = new StringRequest(Request.Method.GET,
                context.getResources().getString(R.string.api_contact_retieve_all), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Contact Response", response);

                try {
                    JSONObject responseObject = new JSONObject(response);
                    if (responseObject.getBoolean("status") == true) {
                        JSONArray contactArray = responseObject.getJSONArray("response");
                        for (int i = 0; i < contactArray.length(); i++) {
                            JSONObject currentContact = contactArray.getJSONObject(i);
                            Contact contact = new Contact();
                            contact.setContactName(currentContact.getString("cperson_firstname"));
                            contact.setContactSecondName(currentContact.getString("cperson_lastname"));
                            contact.setCompanyName(currentContact.getString("cperson_company_name"));
                            contact.setContactAddress(currentContact.getString("cperson_personal_address"));
                            contact.setContactDesignation(currentContact.getString("cperson_job_position"));

                            Log.i("ContactName", currentContact.getString("cperson_firstname"));
                            // GETTING THE ARRAY OF PHONE NUMBER AND SETTING THEM IN THE CONTACT NUMBER PHONE NUMBER LIST
                            JSONArray phoneNumberArray = currentContact.getJSONArray("cperson_phone");
//                            List<PhoneNumber> phoneNumberList = new ArrayList<>();
//                            for(int j=0; j<phoneNumberArray.length(); j++){
//                                PhoneNumber phoneNumber = new PhoneNumber();
//                                JSONObject phoneNumberObject = phoneNumberArray.getJSONObject(j);
//                                phoneNumber.setAccountPhoneLabel(phoneNumberObject.getString("cpphone_clabel_name"));
//                                phoneNumber.setAccountPhoneNumber(phoneNumberObject.getString("cpphone_number"));
//
//                                phoneNumberList.add(phoneNumber);
//                            }
                            contact.setPhoneNumberList(getPhoneNumberList(phoneNumberArray));

                            //GETTING THE ARRAY OF EMAIL'S AND SETTING THEM IN THE EMAIL LIST
                            JSONArray emailArray = currentContact.getJSONArray("cperson_email");

                            contact.setEmailList(getEmailList(emailArray));

                            contactList.add(contact);

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                communicator.returnAllContacts(contactList);
                progressDialog.cancel();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();
                Toast.makeText(context, context.getResources().getString(R.string.WebServiceError), Toast.LENGTH_SHORT).show();
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


        requestQueue.add(allContactsRequest);
    }



    public void getCompanyContactList(int companyID) {

        progressDialog.show();
        String url = context.getResources().getString(R.string.api_contact_company) + companyID;
        StringRequest companyContactsListRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        List<Contact> contactList = new ArrayList<>();
                        try {
                            JSONObject responseObject = new JSONObject(response);
                            if(responseObject.getBoolean("status") == true){
                                JSONArray contactArray = responseObject.getJSONArray("response");
                                for(int i = 0; i<contactArray.length(); i++){
                                    Contact temp = new Contact();
                                    JSONObject contactObject = contactArray.getJSONObject(i);
                                    temp.setContactName(contactObject.getString("cperson_firstname"));
                                    temp.setContactSecondName(contactObject.getString("cperson_lastname"));
                                    temp.setContactID(contactObject.getInt("cperson_id"));
                                    temp.setCompanyName(contactObject.getString("cperson_company_name"));
                                    temp.setContactDesignation(contactObject.getString("cperson_job_position"));

                                    JSONArray phoneNumberArray = contactObject.getJSONArray("cperson_phone");
                                    temp.setPhoneNumberList(getPhoneNumberList(phoneNumberArray));

                                    JSONArray emailArray = contactObject.getJSONArray("cperson_email");
                                    temp.setEmailList(getEmailList(emailArray));

                                    contactList.add(temp);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        communicator.onReturnCompanyContacts(contactList);
                        progressDialog.cancel();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return BaseActivity.getHeaders();
            }
        };

        requestQueue.add(companyContactsListRequest);
    }

    public interface ContactsWebServiceInterface {
        void returnAllContacts(List<Contact> contactList);
        void onReturnCompanyContacts(List<Contact> contactList);
        void returnSpecificContact(Contact contact);
    }


    public List<PhoneNumber> getPhoneNumberList(JSONArray phoneNumberArray) {
        List<PhoneNumber> phoneNumberList = new ArrayList<>();
        Log.i("Phone Number Size", String.valueOf(phoneNumberArray.length()));
        for (int j = 0; j < phoneNumberArray.length(); j++) {
            PhoneNumber phoneNumber = new PhoneNumber();
            JSONObject phoneNumberObject = null;
            try {
                phoneNumberObject = phoneNumberArray.getJSONObject(j);
                phoneNumber.setAccountPhoneLabel(phoneNumberObject.getString("cpphone_clabel_name"));
                phoneNumber.setAccountPhoneNumber(phoneNumberObject.getString("cpphone_number"));
                Log.i(phoneNumberObject.getString("cpphone_clabel_name"),phoneNumberObject.getString("cpphone_number"));
                phoneNumberList.add(phoneNumber);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return phoneNumberList;

    }

    public List<Email> getEmailList(JSONArray emailArray){
        List<Email> emailList = new ArrayList<>();
        for (int j = 0; j < emailArray.length(); j++) {
            Email email = new Email();
            JSONObject emailObject = null;
            try {
                emailObject = emailArray.getJSONObject(j);
                email.setAccountEmailLabel(emailObject.getString("cpemail_clabel_name"));
                email.setAccountEmail(emailObject.getString("cpemail_email_id"));
                emailList.add(email);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return emailList;
    }
    public void setCallBack(ContactsWebServiceInterface communicator) {
        this.communicator = communicator;
    }




}
