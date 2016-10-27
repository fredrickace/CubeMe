package com.cube_me.cubeme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText userNameET;
    EditText passET;
    Button loginButton;
    String userName;
    String password;
    SharedPreferences loginPreferences;
    LinearLayout loginLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        userNameET = (EditText) findViewById(R.id.login_userNameET);
        passET = (EditText)  findViewById(R.id.login_passwordET);
        loginButton = (Button) findViewById(R.id.login_loginButton);
        loginLayout = (LinearLayout) findViewById(R.id.login_layout);
        loginPreferences = this.getSharedPreferences("com.cube_me.cubeme",MODE_PRIVATE);
        userName = loginPreferences.getString("UserName","test");
        password = loginPreferences.getString("Password","");

        if(userName.equals("admin") && password.equals("admin123")){
            startActivityForResult(new Intent(this, MainActivity.class),0);
        }else {
            loginLayout.setVisibility(View.VISIBLE);
        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginLayout.setVisibility(View.VISIBLE);
        loginPreferences.edit().remove("UserName").commit();
        loginPreferences.edit().remove("Password").commit();
    }

    private void login() {

        if(BaseActivity.ifEditTextNotEmptyErrMsg(userNameET) && (BaseActivity.ifEditTextNotEmptyErrMsg(passET))){
            userName = userNameET.getText().toString();
            password = passET.getText().toString();
            if (userName.equals("admin") && password.equals("admin123")){
                loginPreferences.edit().putString("UserName",userName).apply();
                loginPreferences.edit().putString("Password",password).apply();
                startActivity(new Intent(this, MainActivity.class));
            }else {
                Toast.makeText(LoginActivity.this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
