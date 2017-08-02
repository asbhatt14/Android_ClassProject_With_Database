package com.example.ankur.agencyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout home_lnrAgentList,home_lnrAgentSearch,home_lnrAgentAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initToolBar();
        home_lnrAgentList = (LinearLayout) findViewById(R.id.home_lnrAgentList);
        home_lnrAgentSearch = (LinearLayout) findViewById(R.id.home_lnrAgentSearch);
        home_lnrAgentAdd = (LinearLayout) findViewById(R.id.home_lnrAgentAdd);
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home Screen");

        setSupportActionBar(toolbar);

       /* toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(AndroidToolbarExample.this, "clicking the toolbar!", Toast.LENGTH_SHORT).show();
                    }
                }

        );*/
    }
}
