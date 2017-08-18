package com.example.ankur.agencyapp.Controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
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
    LinearLayout home_lnrAgentList,home_lnrAgentSearch,home_lnrAgentAdd,home_lnrMissionAdd,home_lnrMissionList;
    String Preference_Name="LoginPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initToolBar();
        home_lnrAgentList = (LinearLayout) findViewById(R.id.home_lnrAgentList);
       // home_lnrAgentSearch = (LinearLayout) findViewById(R.id.home_lnrAgentSearch);
        home_lnrAgentAdd = (LinearLayout) findViewById(R.id.home_lnrAgentAdd);
        home_lnrMissionAdd = (LinearLayout) findViewById(R.id.home_lnrMissionAdd);
        home_lnrMissionList = (LinearLayout) findViewById(R.id.home_lnrMissionList);
        home_lnrAgentList.setOnClickListener(this);
        home_lnrAgentAdd.setOnClickListener(this);
        home_lnrMissionAdd.setOnClickListener(this);
        home_lnrMissionList.setOnClickListener(this);
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        TextView txtToolbar = (TextView) toolbar.findViewById(R.id.txtToolbar);
        ImageView imgToolbarBack = (ImageView) toolbar.findViewById(R.id.imgToolbarBack);
        TextView txtToolbarSave = (TextView) toolbar.findViewById(R.id.txtToolbarSave);
        ImageView imgLogOut = (ImageView) toolbar.findViewById(R.id.imgLogOut);

        imgLogOut.setVisibility(View.VISIBLE);
        txtToolbar.setText("Home");
        imgToolbarBack.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        imgLogOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_lnrAgentList:
                Intent agentList = new Intent(this,AgentListActivity.class);
                startActivity(agentList);
                break;
            /*case R.id.home_lnrAgentSearch:
                break;*/
            case R.id.home_lnrAgentAdd:
                Intent agentAdd = new Intent(this,AddAgentActivity.class);
                startActivity(agentAdd);
                break;
            case R.id.home_lnrMissionAdd:
                Intent missionAdd = new Intent(this,AddMissionActivity.class);
                startActivity(missionAdd);
                break;
            case R.id.home_lnrMissionList:
                Intent missionList = new Intent(this,AllMissionListActivity.class);
                startActivity(missionList);
                break;
            case R.id.imgLogOut:
                logOut();
                break;
            default:
                break;
        }
    }

    private void logOut() {

       new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to Logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent loginActivity = new Intent(HomeActivity.this,LoginActivity.class);
                        loginActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        SharedPreferences settings = getSharedPreferences(Preference_Name, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.clear();
                        editor.commit();

                        startActivity(loginActivity);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();



    }
}
