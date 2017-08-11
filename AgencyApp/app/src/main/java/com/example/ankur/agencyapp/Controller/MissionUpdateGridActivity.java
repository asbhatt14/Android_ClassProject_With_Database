package com.example.ankur.agencyapp.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.ankur.agencyapp.R;

import java.util.ArrayList;

public class MissionUpdateGridActivity extends AppCompatActivity {

    GridView missionupdate_grdImages;
    MissionGridAdapter adapter;
    private ArrayList<String> listImages;
    Button missionupdate_btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_update_grid);

        listImages = new ArrayList<String>();
        missionupdate_grdImages = (GridView) findViewById(R.id.missionupdate_grdImages);
        missionupdate_btnSend = (Button) findViewById(R.id.missionupdate_btnSend);

        listImages.add(0,"1");
        listImages.add(1,"1");
        listImages.add(2,"1");
        listImages.add(3,"1");

        adapter = new MissionGridAdapter(getApplicationContext(),listImages);
        missionupdate_grdImages.setAdapter(adapter);

        if(listImages.size()>0){
            missionupdate_grdImages.setVisibility(View.VISIBLE);
            missionupdate_btnSend.setEnabled(true);
        }else{
            missionupdate_grdImages.setVisibility(View.GONE);
            missionupdate_btnSend.setEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(listImages.size()>0){
            missionupdate_grdImages.setVisibility(View.VISIBLE);
            missionupdate_btnSend.setEnabled(true);
        }else{
            missionupdate_grdImages.setVisibility(View.GONE);
            missionupdate_btnSend.setEnabled(false);
        }
    }
}
