package com.example.ankur.agencyapp.Controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankur.agencyapp.Model.Agents;
import com.example.ankur.agencyapp.R;

import java.util.ArrayList;
import java.util.List;

public class AgentProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TextView agentProfile_txtAgentName,agentProfile_txtAgentLevel,agentProfile_txtAgency,agentProfile_txtAgentWebsite;
    TextView agentProfile_txtAgentCountry,agentProfile_txtAgentPhoneNumber,agentProfile_txtAgentAddress;
    ImageView agentProfile_imgAgentInfo,agentProfile_imgAgentSMS,agentProfile_imgAgentWebSite,agentProfile_imgAgent;
    ImageView agentProfile_imgAgentCall,agentProfile_imgAgentLocation,agentProfile_imgAgentCamera;
    Agents agent;
    static final int MESSAGE_REQUEST_CODE = 03;
    static final int CALL_REQ_CODE = 9;

    String[] permissions = new String[] {
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_profile_layout);
        initToolBar();
        Intent intent = getIntent();
        agent = (Agents) intent.getSerializableExtra("agent");
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
        agentProfile_imgAgent = (ImageView) findViewById(R.id.agentProfile_imgAgent);

        if(agent!=null){
            fillDetails();
        }
        agentProfile_imgAgentInfo.setOnClickListener(this);
        agentProfile_imgAgentCamera.setOnClickListener(this);
        agentProfile_imgAgentWebSite.setOnClickListener(this);
        agentProfile_imgAgentSMS.setOnClickListener(this);
        agentProfile_imgAgentCall.setOnClickListener(this);
        agentProfile_imgAgentLocation.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        TextView txtToolbar = (TextView) toolbar.findViewById(R.id.txtToolbar);
        ImageView imgToolbarBack = (ImageView) toolbar.findViewById(R.id.imgToolbarBack);
        //toolbar.setTitle("Agent List");
        txtToolbar.setText("Agent Profile");

        setSupportActionBar(toolbar);

        imgToolbarBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*toolbar.setTitle("Agent Profile");

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.agentProfile_imgAgentInfo:
                Intent agentMission = new Intent(this,AgentMissionHistoryActivity.class);
                agentMission.putExtra("missionId",agent.getMissionId());
                startActivity(agentMission);
                break;
            case R.id.agentProfile_imgAgentCamera:
                Intent missionUpdate = new Intent(this,MissionUpdateGridActivity.class);
                missionUpdate.putExtra("agent",agent);
                startActivity(missionUpdate);
                break;
            case R.id.agentProfile_imgAgentWebSite:
                openWebBrowser();
                break;
            case R.id.agentProfile_imgAgentSMS:
                sendSms();
                break;
            case R.id.agentProfile_imgAgentCall:
                MakeCall();
                break;
            case R.id.agentProfile_imgAgentLocation:
                GoToLocation();
                break;
            default:
                break;
        }
    }

    private void GoToLocation() {
        Intent callIntent = new Intent(Intent.ACTION_VIEW);
        callIntent.setData(Uri.parse("geo:0,0?q="+agent.getAgeentAddress()));
        startActivity(callIntent);
    }

    private void MakeCall() {

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},CALL_REQ_CODE);
        }else{
            OpenDefaultCall();
        }

    }

    private void OpenDefaultCall() {
        Intent callIntent = new Intent(Intent.ACTION_VIEW);
        callIntent.setData(Uri.parse("tel:"+agent.getAgeentPhoneNumber()));
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MESSAGE_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
               /* if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    openDefaultSMS();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;*/

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openDefaultSMS();
                } else {
                    /*String permissions = "";
                    for (String per : permissionsList) {
                        permissions += "\n" + per;
                    }*/
                    // permissions list of don't granted permission
                }
                return;
            }

            case CALL_REQ_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    OpenDefaultCall();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;


            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private void sendSms() {

        if(checkPermissions()){
            openDefaultSMS();
        }

        /*if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MESSAGE_REQUEST_CODE);

            }
        }else{
            openDefaultSMS();
        }*/

    }
    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MESSAGE_REQUEST_CODE );
            return false;
        }
        return true;
    }

    private void openDefaultSMS() {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("sms:"+agent.getAgeentPhoneNumber()));
        startActivity(smsIntent);
    }

    private void openWebBrowser() {
        Intent website = new Intent(Intent.ACTION_VIEW);
        String websiteAddress = agent.getAgentURL();
        if(!websiteAddress.startsWith("http://")){
            websiteAddress = "http://" + websiteAddress;
        }
        website.setData(Uri.parse(websiteAddress));
        startActivity(website);
    }
    private void fillDetails() {
        agentProfile_txtAgentName.setText(agent.getAgentName());

        agentProfile_txtAgentLevel.setText(agent.getAgentLevel());

        agentProfile_txtAgency.setText(agent.getAgencyName());
        agentProfile_txtAgentWebsite.setText(agent.getAgentURL());
        agentProfile_txtAgentCountry.setText(agent.getAgentCountry());
        agentProfile_txtAgentPhoneNumber.setText(agent.getAgeentPhoneNumber());
        agentProfile_txtAgentAddress.setText(agent.getAgeentAddress());

        if(agent.getAgentPhotoPath() != null){
            Bitmap bitmap = BitmapFactory.decodeFile(agent.getAgentPhotoPath());
            Bitmap lowResBitmap = Bitmap.createScaledBitmap(bitmap,300,300,true);

            agentProfile_imgAgent.setImageBitmap(lowResBitmap);
            agentProfile_imgAgent.setScaleType(ImageView.ScaleType.FIT_XY);
            //agentProfile_imgAgent.setTag(agent.getAgentPhotoPath());

            if(bitmap!=null)
            {
                bitmap.recycle();
                bitmap=null;
            }
        }

    }
}
