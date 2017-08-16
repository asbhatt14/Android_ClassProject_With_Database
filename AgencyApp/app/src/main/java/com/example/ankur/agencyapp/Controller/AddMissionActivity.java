package com.example.ankur.agencyapp.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankur.agencyapp.DAO.MissionDAO;
import com.example.ankur.agencyapp.Model.Agents;
import com.example.ankur.agencyapp.Model.Mission;
import com.example.ankur.agencyapp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddMissionActivity extends AppCompatActivity implements View.OnClickListener {

    EditText addMission_edtMissionName,addMission_edtMissionDate,addMission_edtMissionStatus;
    Button addMission_btnAddMission;
    Toolbar toolbar;
    TextView txtToolbar;
    ImageView imgToolbarBack;
    DateFormat df;
    Mission mission,objMission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mission);
        initToolBar();
        addMission_edtMissionName = (EditText) findViewById(R.id.addMission_edtMissionName);
        addMission_edtMissionDate = (EditText) findViewById(R.id.addMission_edtMissionDate);
        addMission_edtMissionStatus = (EditText) findViewById(R.id.addMission_edtMissionStatus);
        addMission_btnAddMission = (Button) findViewById(R.id.addMission_btnAddMission);
        addMission_btnAddMission.setOnClickListener(this);
        df = new SimpleDateFormat("dd/MM/yyyy");

         objMission = new Mission();
        Intent intent = getIntent();
        mission = (Mission) intent.getSerializableExtra("mission");
        if(mission!=null){
            fillDetails();
        }
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtToolbar = (TextView) toolbar.findViewById(R.id.txtToolbar);
        imgToolbarBack = (ImageView) toolbar.findViewById(R.id.imgToolbarBack);

        txtToolbar.setText("Add Mission");

        setSupportActionBar(toolbar);

        imgToolbarBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onClick(View v) {
        saveMission();
    }

    private void saveMission() {
        Mission objMission = missionHelper();

        if(objMission!=null){
            MissionDAO dao = new MissionDAO(this);

            if(objMission.getMissionId() == 0){
                dao.dbInsert(objMission);
                Toast.makeText(this,"Mission Added",Toast.LENGTH_SHORT).show();
            }else {
                dao.dbUpdate(objMission);
                Toast.makeText(this,"Mission Udated",Toast.LENGTH_SHORT).show();
            }

            dao.close();

            finish();
        }

    }

    private Mission missionHelper() {

        String missionName = addMission_edtMissionName.getText().toString();
        String missionDate = addMission_edtMissionDate.getText().toString();
        String missionStatus = addMission_edtMissionStatus.getText().toString();

        if((missionName != null && !missionName.trim().isEmpty()) && (missionDate != null && !missionDate.trim().isEmpty())
                && (missionName != null && !missionName.trim().isEmpty())){
            objMission.setMissionName(addMission_edtMissionName.getText().toString());
            try {
                objMission.setMissionDate(df.parse(addMission_edtMissionDate.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            objMission.setMissionStatus(addMission_edtMissionStatus.getText().toString());

            return objMission;
        }else{
            Toast.makeText(this,"Please Enter all details",Toast.LENGTH_SHORT).show();
            return null;
        }


    }

    private void fillDetails() {
        addMission_edtMissionName.setText(mission.getMissionName());
        addMission_edtMissionDate.setText(df.format(mission.getMissionDate()));
        addMission_edtMissionStatus.setText(mission.getMissionStatus());
        objMission = mission;
    }
}
