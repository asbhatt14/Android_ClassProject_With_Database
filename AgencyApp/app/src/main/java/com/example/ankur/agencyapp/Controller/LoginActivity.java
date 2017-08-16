package com.example.ankur.agencyapp.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ankur.agencyapp.Controller.HomeActivity;
import com.example.ankur.agencyapp.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button login_btnLogin;
    EditText login_edtPassword,login_edtUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        login_btnLogin = (Button) findViewById(R.id.login_btnLogin);
        login_edtPassword = (EditText) findViewById(R.id.login_edtPassword);
        login_edtUserName = (EditText) findViewById(R.id.login_edtUserName);
        login_btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btnLogin:
                goToHomeScreen();
                break;
            case R.id.login_btnReset:
                break;
            default:
                break;
        }
    }

    private void goToHomeScreen() {
        String username = login_edtUserName.getText().toString();
        String password = login_edtPassword.getText().toString();

        if((username != null && !username.trim().isEmpty()) && (password != null && !password.trim().isEmpty()) ){
            Intent homeActivity = new Intent(this,HomeActivity.class);
            startActivity(homeActivity);
            finish();
        }else{
            Toast.makeText(this,"Please Enter all details",Toast.LENGTH_SHORT).show();
        }

    }

}

