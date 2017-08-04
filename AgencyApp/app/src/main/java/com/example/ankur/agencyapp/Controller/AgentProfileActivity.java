package com.example.ankur.agencyapp.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankur.agencyapp.Model.Agents;
import com.example.ankur.agencyapp.R;

public class AgentProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TextView agentProfile_txtAgentName,agentProfile_txtAgentLevel,agentProfile_txtAgency,agentProfile_txtAgentWebsite;
    TextView agentProfile_txtAgentCountry,agentProfile_txtAgentPhoneNumber,agentProfile_txtAgentAddress;
    ImageView agentProfile_imgAgentInfo,agentProfile_imgAgentSMS,agentProfile_imgAgentWebSite;
    ImageView agentProfile_imgAgentCall,agentProfile_imgAgentLocation,agentProfile_imgAgentCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_profile);
        initToolBar();
        Intent intent = getIntent();
        Agents agent = (Agents) intent.getSerializableExtra("agent");
        agentProfile_txtAgentName = (TextView) findViewById(R.id.agentProfile_txtAgentName);
        agentProfile_txtAgentLevel = (TextView) findViewById(R.id.agentProfile_txtAgentLevel);
        agentProfile_txtAgency = (TextView) findViewById(R.id.agentProfile_txtAgency);
        agentProfile_txtAgentWebsite = (TextView) findViewById(R.id.agentProfile_txtAgentWebsite);
        agentProfile_txtAgentCountry = (TextView) findViewById(R.id.agentProfile_txtAgentCountry);
        agentProfile_txtAgentPhoneNumber = (TextView) findViewById(R.id.agentProfile_txtAgentPhoneNumber);
        agentProfile_txtAgentAddress = (TextView) findViewById(R.id.agentProfile_txtAgentAddress);

        agentProfile_imgAgentInfo = (ImageView) findViewById(R.id.agentProfile_imgAgentInfo);
        agentProfile_imgAgentSMS = (ImageView) findViewById(R.id.agentProfile_imgAgentSMS);
        agentProfile_imgAgentWebSite = (ImageView) findViewById(R.id.agentProfile_imgAgentWebSite);
        agentProfile_imgAgentCall = (ImageView) findViewById(R.id.agentProfile_imgAgentCall);
        agentProfile_imgAgentLocation = (ImageView) findViewById(R.id.agentProfile_imgAgentLocation);
        agentProfile_imgAgentCamera = (ImageView) findViewById(R.id.agentProfile_imgAgentCamera);

        agentProfile_txtAgentName.setText(agent.getAgentName());

       agentProfile_txtAgentLevel.setText(agent.getAgentLevel());

        agentProfile_txtAgency.setText(agent.getAgencyName());
        agentProfile_txtAgentWebsite.setText(agent.getAgentURL());
        agentProfile_txtAgentCountry.setText(agent.getAgentCountry());
        agentProfile_txtAgentPhoneNumber.setText(agent.getAgeentPhoneNumber());
        agentProfile_txtAgentAddress.setText(agent.getAgeentAddress());

        agentProfile_imgAgentInfo.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Agent Profile");

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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.agentProfile_imgAgentInfo:
                Toast.makeText(this,"1 profile",Toast.LENGTH_SHORT).show();
                Intent agentMission = new Intent(this,AgentMissionHistoryActivity.class);
                Toast.makeText(this,"2  profile",Toast.LENGTH_SHORT).show();
                startActivity(agentMission);
                break;
            default:
                break;
        }
    }
}
