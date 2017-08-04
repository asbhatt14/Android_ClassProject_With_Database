package com.example.ankur.agencyapp.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ankur.agencyapp.Model.Agents;
import com.example.ankur.agencyapp.R;

import java.util.ArrayList;

public class AgentListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Toolbar toolbar;
    ListView agentList_lstAgent;
    ArrayList<Agents> listOfAgent;
    AgentListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_list);
        initToolBar();
        agentList_lstAgent = (ListView) findViewById(R.id.agentList_lstAgent);

        listOfAgent = new ArrayList<Agents>();
        //String agentId, String agentName, String agencyName, int agentLevel, String agentCountry, String ageentPhoneNumber, String agentURL, String ageentAddress
        listOfAgent.add(new Agents("1", "Michel", "ACI","001", "USA","6472447260","www.yahoo.com","225 Van Horne Avenue"));
        listOfAgent.add(new Agents("2", "John", "ACI","005", "USA","6472447260","www.gmail.com","281 Van Horne Avenue"));
        listOfAgent.add(new Agents("3", "Strack", "ACI","007", "USA","6472447260","www.yahoo.com","231 Van Horne Avenue"));
        listOfAgent.add(new Agents("4", "Cersi", "ACI","003", "USA","6472447260","www.abc.com","260 Van Horne Avenue"));
        listOfAgent.add(new Agents("5", "Ankur", "ACI","002", "USA","6472447260","www.xyz.com","260 Van Horne Avenue"));
        listOfAgent.add(new Agents("6", "Sanjay", "ACI","004", "USA","6472447260","www.pwq.com","260 Van Horne Avenue"));
        listOfAgent.add(new Agents("7", "Dutt", "ACI","006","USA","6472447260","www.cvv.com","260 Van Horne Avenue"));

        adapter = new AgentListAdapter(getApplicationContext(),listOfAgent);
        agentList_lstAgent.setAdapter(adapter);

        agentList_lstAgent.setOnItemClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Agent List");

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.toolbar_back);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                }
        );
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Agents objAgent = listOfAgent.get(position);
        Intent agentProfile = new Intent(this,AgentProfileActivity.class);
        agentProfile.putExtra("agent", objAgent);
        startActivity(agentProfile);
    }
}
