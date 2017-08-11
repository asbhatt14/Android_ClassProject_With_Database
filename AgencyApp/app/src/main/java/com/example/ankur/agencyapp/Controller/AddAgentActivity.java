package com.example.ankur.agencyapp.Controller;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankur.agencyapp.DAO.AgentDAO;
import com.example.ankur.agencyapp.DAO.MissionDAO;
import com.example.ankur.agencyapp.Model.Agents;
import com.example.ankur.agencyapp.Model.Mission;
import com.example.ankur.agencyapp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AddAgentActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout agentAdd_lnrMission;
    CustomDialogAdapter adapter;
    List<Mission> lstMision;
    List<Long> lstMissionId;
    TextView agent_misionDetails;
    Toolbar toolbar;
    DateFormat df;
    EditText agentAdd_edtAgentName,agentAdd_edtAgentLevel,agentAdd_edtAgency,agentAdd_edtAgentWebsite;
    EditText agentAdd_edtAgentCountry,agentAdd_edtAgentPhoneNumber,agentAdd_edtAgentAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agent);
        initToolBar();
        df = new SimpleDateFormat("dd/MM/yyyy");
        agentAdd_lnrMission = (LinearLayout) findViewById(R.id.agentAdd_lnrMission);
        agent_misionDetails = (TextView) findViewById(R.id.agent_misionDetails);
        agentAdd_edtAgentName = (EditText) findViewById(R.id.agentAdd_edtAgentName);
        agentAdd_edtAgentLevel = (EditText) findViewById(R.id.agentAdd_edtAgentLevel);
        agentAdd_edtAgency = (EditText) findViewById(R.id.agentAdd_edtAgency);
        agentAdd_edtAgentWebsite = (EditText) findViewById(R.id.agentAdd_edtAgentWebsite);
        agentAdd_edtAgentCountry = (EditText) findViewById(R.id.agentAdd_edtAgentCountry);
        agentAdd_edtAgentPhoneNumber = (EditText) findViewById(R.id.agentAdd_edtAgentPhoneNumber);
        agentAdd_edtAgentAddress = (EditText) findViewById(R.id.agentAdd_edtAgentAddress);

        agentAdd_lnrMission.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.agentAdd_lnrMission:
                showDialog();
                break;
            case R.id.txtToolbarSave:
                saveAgent();
                break;
            case R.id.txtToolbarCancel:
                finish();
                break;
            default:
                break;
        }
    }

    private void showDialog() {

        final Dialog dialog = new Dialog(this);

        View view = getLayoutInflater().inflate(R.layout.custom_dialog_layout, null);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);

        ListView lstDialog = (ListView) view.findViewById(R.id.lstDialog);
        Button dialog_btnSave = (Button) view.findViewById(R.id.dialog_btnSave);
        Button dialog_btnCacel = (Button) view.findViewById(R.id.dialog_btnCacel);
        lstMissionId = new ArrayList<Long>();

       // lstMision = new ArrayList<Mission>();
        /*try {
            lstMision.add(new Mission(1,"Mission 1",df.parse("01/05/2015"),"Open"));
            lstMision.add(new Mission(2,"Mission 2",df.parse("01/05/2016s"),"Done"));
            lstMision.add(new Mission(3,"Mission 3",df.parse("01/05/2016"),"Cancelled"));
            lstMision.add(new Mission(4,"Mission 4",df.parse("01/05/2017"),"On going"));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        MissionDAO missionDAO = new MissionDAO(this);
        lstMision =  missionDAO.dbSearch();
        missionDAO.close();

        adapter = new CustomDialogAdapter(getApplicationContext(),lstMision);
        lstDialog.setAdapter(adapter);


        dialog_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.selectedItems.size()>0){
                    for(int i= 0;i<adapter.selectedItems.size();i++){
                        //Toast.makeText(AddAgentActivity.this,Integer.toString(adapter.selectedItems.size()),Toast.LENGTH_SHORT).show();
                        agent_misionDetails.setText(agent_misionDetails.getText() + adapter.selectedItems.get(i).getMissionName() + "\n");
                        lstMissionId.add(adapter.selectedItems.get(i).getMissionId());
                    }
                    dialog.dismiss();
                }

            }
        });

        dialog_btnCacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView txtToolbar = (TextView) toolbar.findViewById(R.id.txtToolbar);
        TextView txtToolbarCancel = (TextView) toolbar.findViewById(R.id.txtToolbarCancel);
        TextView txtToolbarSave = (TextView) toolbar.findViewById(R.id.txtToolbarSave);
        ImageView imgToolbarBack = (ImageView) toolbar.findViewById(R.id.imgToolbarBack);

        txtToolbar.setText("Add Agent");

        setSupportActionBar(toolbar);

        imgToolbarBack.setVisibility(View.GONE);
        txtToolbarSave.setVisibility(View.VISIBLE);
        txtToolbarCancel.setVisibility(View.VISIBLE);
        txtToolbarSave.setOnClickListener(this);
        txtToolbarCancel.setOnClickListener(this);

    }

    private void saveAgent() {
        Agents objAgent = agentsHelper();
        AgentDAO dao = new AgentDAO(this);
        dao.dbInsert(objAgent);
        dao.close();
        Toast.makeText(this,"Agent Added",Toast.LENGTH_SHORT).show();
        finish();
    }

    private Agents agentsHelper() {
//agentId INTEGER PRIMARY KEY, agentName TEXT NOT NULL,agencyName TEXT NOT NULL ,agentLevel TEXT,agentCountry TEXT, ageentPhoneNumber TEXT, agentURL TEXT, ageentAddress TEXT NOT NULL,missionId TEXT NOT NULL)";
        Agents objAgent = new Agents();
        objAgent.setAgentName(agentAdd_edtAgentName.getText().toString());
        objAgent.setAgentLevel(agentAdd_edtAgentLevel.getText().toString());
        objAgent.setAgencyName(agentAdd_edtAgency.getText().toString());
        objAgent.setAgeentAddress(agentAdd_edtAgentAddress.getText().toString());
        objAgent.setAgeentPhoneNumber(agentAdd_edtAgentPhoneNumber.getText().toString());
        objAgent.setAgentCountry(agentAdd_edtAgentCountry.getText().toString());
        objAgent.setAgentURL(agentAdd_edtAgentWebsite.getText().toString());

        String missionId="";
        if(lstMissionId.size()>0){
            StringBuilder commaSepValueBuilder = new StringBuilder();

            //Looping through the list
            for ( int i = 0; i< lstMissionId.size(); i++){
                //append the value into the builder
                commaSepValueBuilder.append(lstMissionId.get(i));

                //if the value is not the last element of the list
                //then append the comma(,) as well
                if ( i != lstMissionId.size()-1){
                    commaSepValueBuilder.append(", ");
                }
            }
            missionId = commaSepValueBuilder.toString();
        }

        objAgent.setMissionId(missionId);
        return objAgent;
    }

}
