package com.example.ankur.agencyapp.Controller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.Date;

public class AddMissionActivity extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener {

    EditText addMission_edtMissionName,addMission_edtMissionStatus;
    Button addMission_btnAddMission;
    Toolbar toolbar;
    TextView txtToolbar,addMission_edtMissionDate;
    ImageView imgToolbarBack;
    DateFormat df;
    Mission mission,objMission;
    private int year, yearTemp;
    private int month, monthTemp;
    private int day, dayTemp;
    final int DATE_DIALOG_ID = 9;
    private DatePicker dpResult;
    boolean isUpdate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mission);
        initToolBar();
        addMission_edtMissionName = (EditText) findViewById(R.id.addMission_edtMissionName);
        addMission_edtMissionDate = (TextView) findViewById(R.id.addMission_edtMissionDate);
        addMission_edtMissionStatus = (EditText) findViewById(R.id.addMission_edtMissionStatus);
        addMission_btnAddMission = (Button) findViewById(R.id.addMission_btnAddMission);
        addMission_btnAddMission.setOnClickListener(this);
        df = new SimpleDateFormat("dd/MM/yyyy");

        dpResult = new DatePicker(this);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        dpResult.init(year, month, day, null);

         objMission = new Mission();
        Intent intent = getIntent();
        mission = (Mission) intent.getSerializableExtra("mission");

        if(mission!=null){
            fillDetails();
            isUpdate = true;
        }
        addMission_edtMissionDate.setOnClickListener(this);

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
        switch (v.getId()){
            case R.id.addMission_btnAddMission:
                saveMission();
                break;
            case R.id.addMission_edtMissionDate:
                showDialog(DATE_DIALOG_ID);
                break;
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                if(isUpdate){
                    return new DatePickerDialog(this, this,
                            (yearTemp+1900), (monthTemp),dayTemp);

                }else{
                    return new DatePickerDialog(this, this,
                            year, month,day);
                }

        }
        return null;
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
                && (missionStatus != null && !missionStatus.trim().isEmpty())){
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


        Date date  = mission.getMissionDate();
        yearTemp = date.getYear();
        monthTemp = date.getMonth();
        dayTemp = date.getDate();

        dpResult.init((yearTemp+1900), (monthTemp+1), dayTemp, null);

        objMission = mission;
    }

    @Override
    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

        year = selectedYear;
        month = selectedMonth;
        day = selectedDay;

        // set selected date into textview
        addMission_edtMissionDate.setText(new StringBuilder().append(day)
                .append("/").append(month + 1).append("/").append(year)
                .append(" "));

        // set selected date into datepicker also
        dpResult.init(year, month, day, null);
    }
}
