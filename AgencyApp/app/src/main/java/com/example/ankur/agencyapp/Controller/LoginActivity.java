package com.example.ankur.agencyapp.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.ankur.agencyapp.Controller.HomeActivity;
import com.example.ankur.agencyapp.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button login_btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        login_btnLogin = (Button) findViewById(R.id.login_btnLogin);
        login_btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btnLogin:
                Intent homeActivity = new Intent(this,HomeActivity.class);
                startActivity(homeActivity);
                finish();
                break;
            case R.id.login_btnReset:
                break;
            default:
                break;
        }
    }
}