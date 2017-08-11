package com.example.ankur.agencyapp.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ankur.agencyapp.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    LinearLayout home_lnrAgentList,home_lnrAgentSearch,home_lnrAgentAdd,home_lnrMissionAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initToolBar();
        home_lnrAgentList = (LinearLayout) findViewById(R.id.home_lnrAgentList);
        home_lnrAgentSearch = (LinearLayout) findViewById(R.id.home_lnrAgentSearch);
        home_lnrAgentAdd = (LinearLayout) findViewById(R.id.home_lnrAgentAdd);
        home_lnrMissionAdd = (LinearLayout) findViewById(R.id.home_lnrMissionAdd);
        home_lnrAgentList.setOnClickListener(this);
        home_lnrAgentAdd.setOnClickListener(this);
        home_lnrMissionAdd.setOnClickListener(this);
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        TextView txtToolbar = (TextView) toolbar.findViewById(R.id.txtToolbar);
        ImageView imgToolbarBack = (ImageView) toolbar.findViewById(R.id.imgToolbarBack);

        txtToolbar.setText("Home Screen");
        imgToolbarBack.setVisibility(View.GONE);
        setSupportActionBar(toolbar);

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
                Intent agentAdd = new Intent(this,AddAgentActivity.class);
                startActivity(agentAdd);
                break;
            case R.id.home_lnrMissionAdd:
                Intent missionAdd = new Intent(this,AddMissionActivity.class);
                startActivity(missionAdd);
                break;
            default:
                break;
        }
    }
}
