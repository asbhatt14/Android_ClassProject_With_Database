package com.example.ankur.agencyapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ankur.agencyapp.Controller.HomeActivity;
import com.example.ankur.agencyapp.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button login_btnLogin,login_btnReset;
    EditText login_edtPassword,login_edtUserName;
    private  final static  String loginUserName = "admin";
    private  final static  String loginPassword = "admin";
    boolean isLoggedIn = false;
    String Preference_Name="LoginPrefs";
    SharedPreferences logInPref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        logInPref = getSharedPreferences(Preference_Name,0);
        if (logInPref.getBoolean("loggedIn",false)) {
            Intent homeActivity = new Intent(this,HomeActivity.class);
            startActivity(homeActivity);
            finish();
        }

        login_btnLogin = (Button) findViewById(R.id.login_btnLogin);
        login_btnReset = (Button) findViewById(R.id.login_btnReset);
        login_edtPassword = (EditText) findViewById(R.id.login_edtPassword);
        login_edtUserName = (EditText) findViewById(R.id.login_edtUserName);
        login_btnLogin.setOnClickListener(this);
        login_btnReset.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btnLogin:
                goToHomeScreen();
                break;
            case R.id.login_btnReset:
                login_edtUserName.setText("");
                login_edtPassword.setText("");
                login_edtPassword.clearFocus();
                login_edtUserName.clearFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                break;
            default:
                break;
        }
    }

    private void goToHomeScreen() {
        String username = login_edtUserName.getText().toString();
        String password = login_edtPassword.getText().toString();

        if((username != null && !username.trim().isEmpty()) && (password != null && !password.trim().isEmpty()) ){
            if(username.equals(loginUserName) && password.equals(loginPassword)){
                Intent homeActivity = new Intent(this,HomeActivity.class);
                editor = logInPref.edit();
                editor.putBoolean("loggedIn",true);
                editor.commit();
                startActivity(homeActivity);
                finish();
            }else{
                Toast.makeText(this,"Wrong User name or password",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this,"Please Enter all details",Toast.LENGTH_SHORT).show();
        }

    }

}

