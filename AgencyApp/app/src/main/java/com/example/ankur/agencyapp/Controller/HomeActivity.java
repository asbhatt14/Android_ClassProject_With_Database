package com.example.ankur.agencyapp.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ankur.agencyapp.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

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
        home_lnrAgentList.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_lnrAgentList:
                Intent agentList = new Intent(this,AgentListActivity.class);
                startActivity(agentList);
                break;
            case R.id.home_lnrAgentSearch:
                break;
            case R.id.home_lnrAgentAdd:
                break;
            default:
                break;
        }
    }
}
