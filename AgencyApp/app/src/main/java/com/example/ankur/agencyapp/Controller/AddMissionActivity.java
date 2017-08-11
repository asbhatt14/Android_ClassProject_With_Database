package com.example.ankur.agencyapp.Controller;

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
        MissionDAO dao = new MissionDAO(this);
        dao.dbInsert(objMission);
        dao.close();
        Toast.makeText(this,"Mission Added",Toast.LENGTH_SHORT).show();
        finish();
    }

    private Mission missionHelper() {

        Mission objMission = new Mission();
        objMission.setMissionName(addMission_edtMissionName.getText().toString());
        try {
            objMission.setMissionDate(df.parse(addMission_edtMissionDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        objMission.setMissionStatus(addMission_edtMissionStatus.getText().toString());

        return objMission;
    }
}
