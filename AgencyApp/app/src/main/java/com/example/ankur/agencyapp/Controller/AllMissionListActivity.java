package com.example.ankur.agencyapp.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankur.agencyapp.DAO.AgentDAO;
import com.example.ankur.agencyapp.DAO.MissionDAO;
import com.example.ankur.agencyapp.Model.Agents;
import com.example.ankur.agencyapp.Model.Mission;
import com.example.ankur.agencyapp.R;

import java.util.List;

public class AllMissionListActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView allMission_lstMission;
    List<Mission> lstMision;
    AgentMissionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_mission_list);
        allMission_lstMission = (ListView) findViewById(R.id.allMission_lstMission);
        initToolBar();

        loadMissionList();
        registerForContextMenu(allMission_lstMission);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMissionList();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem update = menu.add("Update");
        update.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

                Mission mission = (Mission) allMission_lstMission.getItemAtPosition(info.position);

                Intent updateMission = new Intent(AllMissionListActivity.this,AddMissionActivity.class);
                updateMission.putExtra("mission",mission);
                startActivity(updateMission);

              /*  AgentDAO dao = new AgentDAO(AgentListActivity.this);
                dao.dbUpdate(agent);
                dao.close();
                Toast.makeText(AgentListActivity.this,"Agent Updated",Toast.LENGTH_SHORT).show();
                loadAgentList();*/
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void loadMissionList() {
        MissionDAO missionDAO = new MissionDAO(this);
        lstMision =  missionDAO.dbSearch();
        missionDAO.close();
        if(lstMision!=null && lstMision.size()>0){
            adapter = new AgentMissionAdapter(getApplicationContext(),lstMision);
            allMission_lstMission.setAdapter(adapter);
        }
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        TextView txtToolbar = (TextView) toolbar.findViewById(R.id.txtToolbar);
        ImageView imgToolbarBack = (ImageView) toolbar.findViewById(R.id.imgToolbarBack);

        txtToolbar.setText("All Mission");

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
