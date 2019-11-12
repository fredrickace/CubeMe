package com.cube_me.cubeme_crm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText userNameET;
    EditText passET;
    Button loginButton;
    String userName;
    String password;
    SharedPreferences loginPreferences;
    LinearLayout loginLayout;
    Boolean loginStatus;

    //SERVER RESPONSE VALUES
    String loginToken;
    String loginError;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        userNameET = (EditText) findViewById(R.id.login_userNameET);
        passET = (EditText)  findViewById(R.id.login_passwordET);
        loginButton = (Button) findViewById(R.id.login_loginButton);
        loginLayout = (LinearLayout) findViewById(R.id.login_layout);
        loginPreferences = this.getSharedPreferences("com.cube_me.cubeme",MODE_PRIVATE);
        loginStatus = loginPreferences.getBoolean("LoginStatus",false);
        Log.i("LoginStatus", loginStatus.toString());
        requestQueue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();

        if(loginStatus){
            startActivityForResult(new Intent(this, MainActivity.class),0);
        }else {
            loginLayout.setVisibility(View.VISIBLE);
        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(BaseActivity.ifEditTextNotEmptyErrMsg(userNameET) && (BaseActivity.ifEditTextNotEmptyErrMsg(passET))) {
                    userName = userNameET.getText().toString();
                    password = passET.getText().toString();

                    StringRequest loginRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.login_url),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("Volley Response", response);
                                    try {
                                        JSONObject userObj = new JSONObject(response);
                                        if(userObj.getBoolean("status") == true){
                                            loginToken = userObj.getString("_token");
                                            Log.i("UserToken", loginToken);
                                            getUserDetails(loginToken);
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
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("username_email",userName);
                            params.put("password",password);
                            return params;
                        }
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            //headers.put("Content-Type", "application/json");
                            headers.put("Content-Type", "application/x-www-form-urlencoded");
                            return headers;
                        }
                    };


                    requestQueue.add(loginRequest);


                }
            }
        });
    }


    public void getUserDetails(final String token){


        final StringRequest userDetailRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.url_user_info),
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String userName = "";
                try {
                    JSONObject userObj = new JSONObject(response);
                    JSONObject user = userObj.getJSONObject("response");
                    String firstName = user.getString("detail_firstname");
                    String lastName = user.getString("detail_lastname");
                    userName = firstName+" "+lastName;
                    Log.i("UserName",userName);
                    Log.i("EMail ID", user.getString("account_email_id"));
                    Log.i("UserCode",user.getString("account_usercode"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // ADDING THE TOKEN IN SHARED PREFERENCES
                SharedPreferences.Editor editor = loginPreferences.edit();
                editor.putBoolean("LoginStatus",true);
                editor.putString("Token",token);
                editor.putString("UserName",userName);
                editor.apply();
                startActivityForResult(new Intent(getApplicationContext(), MainActivity.class),0);

                Log.i("UserContent", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Message", error.toString());
            }
        })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("_token", token);
                return headers;
            }
        };

        requestQueue.add(userDetailRequest);
//        RequestQueue userRequestQueue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
//        userRequestQueue.add(userDetailRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginLayout.setVisibility(View.VISIBLE);
        String userName = loginPreferences.getString("UserName","UsrName");
        Log.i("Sign Out",userName);
        loginPreferences.edit().putBoolean("LoginStatus",false).commit();
        loginPreferences.edit().remove("UserName").commit();
        loginPreferences.edit().remove("Token").commit();
    }


}
