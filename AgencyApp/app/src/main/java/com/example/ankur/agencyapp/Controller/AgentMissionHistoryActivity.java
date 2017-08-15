package com.example.ankur.agencyapp.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankur.agencyapp.DAO.MissionDAO;
import com.example.ankur.agencyapp.Model.Mission;
import com.example.ankur.agencyapp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AgentMissionHistoryActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView agentMission_lstMission;
    List<Mission> lstMisionHistory,lstMision;
    AgentMissionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_mission_history);
        initToolBar();
        agentMission_lstMission = (ListView) findViewById(R.id.agentMission_lstMission);

        lstMisionHistory = new ArrayList<Mission>();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Intent intent = getIntent();
        String missionId = intent.getStringExtra("missionId");

        List<String> misionIdList = Arrays.asList(missionId.split("\\s*,\\s*"));


        MissionDAO missionDAO = new MissionDAO(this);
        lstMision =  missionDAO.dbSearch();
        missionDAO.close();

        for(int i=0;i<lstMision.size();i++){
            for(int j=0;j<misionIdList.size();j++){
                if(Long.toString(lstMision.get(i).getMissionId()).equals(misionIdList.get(j))){
                    lstMisionHistory.add(lstMision.get(i));
                }
            }
        }

        adapter = new AgentMissionAdapter(getApplicationContext(),lstMisionHistory);
        agentMission_lstMission.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        TextView txtToolbar = (TextView) toolbar.findViewById(R.id.txtToolbar);
        ImageView imgToolbarBack = (ImageView) toolbar.findViewById(R.id.imgToolbarBack);

        txtToolbar.setText("Mission History");

        setSupportActionBar(toolbar);

        imgToolbarBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        /*toolbar.setTitle("Mission History");

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                }
        );*/
    }
}
