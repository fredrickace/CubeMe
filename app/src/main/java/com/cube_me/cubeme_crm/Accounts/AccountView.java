package com.cube_me.cubeme_crm.Accounts;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cube_me.cubeme_crm.PhoneNumber;
import com.cube_me.cubeme_crm.AddressObject;
import com.cube_me.cubeme_crm.Email;
import com.cube_me.cubeme_crm.BaseActivity;
import com.cube_me.cubeme_crm.Inquiry.InquiryNew;
import com.cube_me.cubeme_crm.R;
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

public class AccountView extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager accountsViewViewPager;
    SlidingTabLayout accountsViewSlidingTabLayout;
    public static final String activityName = "ACCOUNT_VIEW";
    RequestQueue requestQueue;
    int accountId;
    Intent intent;
    ProgressDialog progressDialog;
//    NewInquiryCommunicator communicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_view);


        //SETTING UP TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Athab Qatar");


        //BASIC INIT
        intent = getIntent();
        accountId = intent.getIntExtra("AccountId", 0);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.progressBarTitle));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(true);
        requestQueue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
        if (BaseActivity.networkIsConnected) {
            progressDialog.show();
            getAccountDetails();
        } else {
            Toast.makeText(this, getResources().getString(R.string.WebServiceError), Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_accounts_viewdetail, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.nav_accountViewDetail_new:

                //ADDING A DIALOG BOX TO SELECT THE SUB CATEGORIES
                CharSequence colors[] = new CharSequence[]{"Inquiry", "Estimations", "Budgets", "Quotations", "Jobs"};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("New");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on colors[which]
                        switch (which) {
                            case 0:
                                Intent i = new Intent(getApplicationContext(), InquiryNew.class);
                                i.putExtra("InquiryFlag", InquiryNew.INQUIRY_NEW);
                                startActivity(i);
                                break;

                        }
                    }
                });
                builder.show();
                break;

            case R.id.nav_accountViewDetail_edit:

        }
        return super.onOptionsItemSelected(item);

    }


    public void getAccountDetails() {

            String url = getResources().getString(R.string.api_company_retrieve_id) + accountId;
            StringRequest accountIdRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Accounts currentAccount = new Accounts();
                            List<Email> emailList = new ArrayList<>();
                            List<PhoneNumber> phoneList = new ArrayList<>();
                            List<AddressObject> addressList = new ArrayList<>();
                            Log.i("Individual Account", response);
                            try {
                                JSONObject responseObject = new JSONObject(response);
                                if (responseObject.getBoolean("status")) {
                                    JSONArray companyArray = responseObject.getJSONArray("response");
                                    JSONObject companyObject = companyArray.getJSONObject(0);
                                    currentAccount.setAccountId(companyObject.getInt("company_id"));
                                    currentAccount.setAccountName(companyObject.getString("company_name"));
                                    currentAccount.setAccountContactName(companyObject.getString("company_contact_person_text"));
                                    currentAccount.setAccountCode(companyObject.getString("company_code"));
                                    currentAccount.setAccountWeb(companyObject.getString("company_website"));
                                    currentAccount.setAccountDescription(companyObject.getString("company_description"));
                                    //SETTING THE TOOLBAR TITLE
                                    toolbar.setTitle(companyObject.getString("company_abbr"));

                                    //GETTING THE LIST OF ADDRESS
                                    JSONArray addressArray = companyObject.getJSONArray("company_address");
                                    for (int i = 0; i < addressArray.length(); i++) {
                                        AddressObject tempAddress = new AddressObject();
                                        JSONObject addressObject = addressArray.getJSONObject(i);
                                        tempAddress.setAddressDetail(addressObject.getString("caddress_detail"));
                                        tempAddress.setAddressLabelName(addressObject.getString("caddress_clabel_name"));
                                        addressList.add(tempAddress);
                                        Log.i("Company Address", addressObject.toString());
                                    }
                                    //GETTING THE LIST OF EMAILS
                                    JSONArray emailArray = companyObject.getJSONArray("company_email");
                                    for (int i = 0; i < emailArray.length(); i++) {
                                        Email emailTemp = new Email();
                                        JSONObject emailObject = emailArray.getJSONObject(i);
                                        emailTemp.setAccountEmail(emailObject.getString("cemail_email_id"));
                                        emailTemp.setAccountEmailLabel(emailObject.getString("cemail_clabel_name"));
                                        emailList.add(emailTemp);
                                        Log.i("Email Address", emailObject.toString());
                                    }
                                    //GETTING THE LIST OF PHONE NUMBERS
                                    JSONArray phoneArray = companyObject.getJSONArray("company_phone");
                                    for (int i = 0; i < phoneArray.length(); i++) {
                                        PhoneNumber phoneTemp = new PhoneNumber();
                                        JSONObject phoneObject = phoneArray.getJSONObject(i);
                                        phoneTemp.setAccountPhoneNumber(phoneObject.getString("cphone_number"));
                                        phoneTemp.setAccountPhoneLabel(phoneObject.getString("cphone_clabel_name"));
                                        phoneList.add(phoneTemp);
                                        Log.i("Phone Number", phoneObject.toString());
                                    }
                                    currentAccount.setAddressObjectList(addressList);
                                    currentAccount.setEmailList(emailList);
                                    currentAccount.setPhoneNumberList(phoneList);
                                    Log.i("CompanyObject", companyObject.toString());

                                    //SETTING UP VIEWPAGER
                                    accountsViewViewPager = (ViewPager) findViewById(R.id.accountsViewViewPager);
                                    FragmentManager fragmentManager = getSupportFragmentManager();
                                    AccountsViewPagerAdapter accountsViewAdapter = new AccountsViewPagerAdapter(fragmentManager,
                                            getApplicationContext(),currentAccount);
                                    accountsViewViewPager.setAdapter(accountsViewAdapter);

                                    //SETTING UP SLIDINGTABLAYOUT
                                    accountsViewSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.accountViewSlideTab);
                                    accountsViewSlidingTabLayout.setDistributeEvenly(true);
                                    accountsViewSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                    accountsViewSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.ivory));
                                    accountsViewSlidingTabLayout.setViewPager(accountsViewViewPager);

                                    accountsViewViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                        @Override
                                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                        }

                                        @Override
                                        public void onPageSelected(int position) {

                                        }

                                        @Override
                                        public void onPageScrollStateChanged(int state) {

                                        }
                                    });

                                    progressDialog.cancel();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
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

            requestQueue.add(accountIdRequest);

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == InquiryNew.RETURN_TRUE) {
//            Inquiry inquiry = data.getParcelableExtra("New Inquiry");
//            Toast.makeText(AccountView.this, inquiry.inquirySubject, Toast.LENGTH_SHORT).show();
//            AccountViewAttachedFragment.inquiryList.add(inquiry);
////            AccountViewAttachedFragment.sendInquiryToFragment(communicator);
////            communicator.setNewInquiry(inquiry);
//        }
//    }


//    interface NewInquiryCommunicator {
//        public void setNewInquiry(Inquiry inquiry);
//    }
}
