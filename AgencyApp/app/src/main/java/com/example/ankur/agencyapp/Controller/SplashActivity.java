package com.example.ankur.agencyapp.Controller;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ankur.agencyapp.DAO.DBHelper;
import com.example.ankur.agencyapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DBHelper dbHelper = new DBHelper(this);
        dbHelper.getWritableDatabase();
        dbHelper.close();


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 2500);
    }
}
