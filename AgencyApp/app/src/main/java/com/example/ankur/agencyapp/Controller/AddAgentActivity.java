package com.example.ankur.agencyapp.Controller;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankur.agencyapp.BuildConfig;
import com.example.ankur.agencyapp.DAO.AgentDAO;
import com.example.ankur.agencyapp.DAO.MissionDAO;
import com.example.ankur.agencyapp.Model.Agents;
import com.example.ankur.agencyapp.Model.Mission;
import com.example.ankur.agencyapp.R;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddAgentActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout agentAdd_lnrMission;
    CustomDialogAdapter adapter;
    List<Mission> lstMision,lstMissionHistoryTemp;
    List<Long> lstMissionId;
    TextView agent_misionDetails;
    Toolbar toolbar;
    DateFormat df;
    EditText agentAdd_edtAgentName,agentAdd_edtAgentLevel,agentAdd_edtAgency,agentAdd_edtAgentWebsite;
    EditText agentAdd_edtAgentCountry,agentAdd_edtAgentPhoneNumber,agentAdd_edtAgentAddress;
    Agents agent,objAgent;
    RelativeLayout agentAdd_rltChangePhoto;
    ImageView agentProfile_imgAgent;
    static final int CAMERA_REQUEST_CODE = 01;
    static final int CAMERA_CODE = 05;
    String driAppPhoto;

    String[] permissions = new String[] {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
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
        agentAdd_rltChangePhoto = (RelativeLayout) findViewById(R.id.agentAdd_rltChangePhoto);
        agentProfile_imgAgent = (ImageView) findViewById(R.id.agentProfile_imgAgent);

        agentAdd_lnrMission.setOnClickListener(this);
        agentAdd_rltChangePhoto.setOnClickListener(this);

        Intent intent = getIntent();
        agent = (Agents) intent.getSerializableExtra("agent");
        lstMissionId = new ArrayList<Long>();
        lstMissionHistoryTemp = new ArrayList<Mission>();
         objAgent = new Agents();
        if(agent!=null){
            fillForm();
        }
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
            case R.id.agentAdd_rltChangePhoto:
                startCamera();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == CAMERA_CODE){
                loadImage();
            }
        }
    }

    private void loadImage() {
        if(driAppPhoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(driAppPhoto);
            Bitmap lowResBitmap = Bitmap.createScaledBitmap(bitmap,300,300,true);

            agentProfile_imgAgent.setImageBitmap(lowResBitmap);
            agentProfile_imgAgent.setScaleType(ImageView.ScaleType.FIT_XY);
            agentProfile_imgAgent.setTag(driAppPhoto);

            if(bitmap!=null)
            {
                bitmap.recycle();
                bitmap=null;
            }

          //  Toast.makeText(this,driAppPhoto,Toast.LENGTH_SHORT).show();
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


        MissionDAO missionDAO = new MissionDAO(this);
        lstMision =  missionDAO.dbSearch();
        missionDAO.close();

        adapter = new CustomDialogAdapter(getApplicationContext(),lstMision,lstMissionHistoryTemp);
        lstDialog.setAdapter(adapter);


        dialog_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                agent_misionDetails.setText("");
                if(adapter.selectedItems.size()>0){
                    lstMissionId.clear();
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
        if(objAgent!=null){
            AgentDAO dao = new AgentDAO(this);

            if(objAgent.getAgentId() == 0){
                dao.dbInsert(objAgent);
                Toast.makeText(this,"Agent Added",Toast.LENGTH_SHORT).show();
            }else{
                dao.dbUpdate(objAgent);
                Toast.makeText(this,"Agent Updated",Toast.LENGTH_SHORT).show();
            }

            dao.close();

            finish();
        }
    }

    private Agents agentsHelper() {
//agentId INTEGER PRIMARY KEY, agentName TEXT NOT NULL,agencyName TEXT NOT NULL ,agentLevel TEXT,agentCountry TEXT, ageentPhoneNumber TEXT, agentURL TEXT, ageentAddress TEXT NOT NULL,missionId TEXT NOT NULL)";
        //Agents objAgent = new Agents();

        String agentName = agentAdd_edtAgentName.getText().toString();
        String agnetLevel = agentAdd_edtAgentLevel.getText().toString();
        String agnecyName = agentAdd_edtAgency.getText().toString();
        String agentAddress = agentAdd_edtAgentAddress.getText().toString();
        String agentPhoneNumber = agentAdd_edtAgentPhoneNumber.getText().toString();
        String agentCountry = agentAdd_edtAgentCountry.getText().toString();
        String agentUrl = agentAdd_edtAgentWebsite.getText().toString();

        if((agentName != null && !agentName.trim().isEmpty()) && (agnetLevel != null && !agnetLevel.trim().isEmpty()) &&
                (agnecyName != null && !agnecyName.trim().isEmpty()) &&  (agentAddress != null && !agentAddress.trim().isEmpty())
                && (agentPhoneNumber != null && !agentPhoneNumber.trim().isEmpty()) && (agentCountry != null && !agentCountry.trim().isEmpty())
                && (agentUrl != null && !agentUrl.trim().isEmpty()) && lstMissionId.size()>0 ){
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
            objAgent.setAgentPhotoPath((String) agentProfile_imgAgent.getTag());
            return objAgent;
        }else{
            Toast.makeText(this,"Please Enter all details",Toast.LENGTH_SHORT).show();
            return null;
        }


    }

    private void fillForm() {

        agentAdd_edtAgentName.setText(agent.getAgentName());

        agentAdd_edtAgentLevel.setText(agent.getAgentLevel());

        agentAdd_edtAgency.setText(agent.getAgencyName());
        agentAdd_edtAgentWebsite.setText(agent.getAgentURL());
        agentAdd_edtAgentCountry.setText(agent.getAgentCountry());
        agentAdd_edtAgentPhoneNumber.setText(agent.getAgeentPhoneNumber());
        agentAdd_edtAgentAddress.setText(agent.getAgeentAddress());

        List<String> misionIdList = Arrays.asList(agent.getMissionId().split("\\s*,\\s*"));


        MissionDAO missionDAO = new MissionDAO(this);
        List<Mission> lstMissionTemp =  missionDAO.dbSearch();

        missionDAO.close();

        for(int i=0;i<lstMissionTemp.size();i++){
            for(int j=0;j<misionIdList.size();j++){
                if(Long.toString(lstMissionTemp.get(i).getMissionId()).equals(misionIdList.get(j))){
                    lstMissionHistoryTemp.add(lstMissionTemp.get(i));
                    lstMissionId.add(lstMissionTemp.get(i).getMissionId());
                }
            }
        }

        for(int i=0;i<lstMissionHistoryTemp.size();i++){
            agent_misionDetails.setText(agent_misionDetails.getText() + lstMissionHistoryTemp.get(i).getMissionName() + "\n");
        }

        if(agent.getAgentPhotoPath() != null){
            Bitmap bitmap = BitmapFactory.decodeFile(agent.getAgentPhotoPath());
            Bitmap lowResBitmap = Bitmap.createScaledBitmap(bitmap,300,300,true);

            agentProfile_imgAgent.setImageBitmap(lowResBitmap);
            agentProfile_imgAgent.setScaleType(ImageView.ScaleType.FIT_XY);
            agentProfile_imgAgent.setTag(agent.getAgentPhotoPath());

            if(bitmap!=null)
            {
                bitmap.recycle();
                bitmap=null;
            }
        }



        objAgent = agent;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
/*                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    openCamera();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }*/
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    /*String permissions = "";
                    for (String per : permissionsList) {
                        permissions += "\n" + per;
                    }*/
                    // permissions list of don't granted permission
                }
                return;

                // other 'case' lines to check for other
                // permissions this app might request
            }
        }
    }

    private void openCamera() {
        Intent actioncamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        driAppPhoto = getExternalFilesDir("AgentPhotos") + "/" + System.currentTimeMillis() + ".jpg";

        File filephoto = new File(driAppPhoto);

        Uri photoURI;
       /* Uri photoURI = FileProvider.getUriForFile(this,
                BuildConfig.APPLICATION_ID + ".provider",
                filephoto);*/

        //Uri photoURI = Uri.fromFile(filephoto);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            photoURI = FileProvider.getUriForFile(this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    filephoto);
        } else{
            photoURI = Uri.fromFile(filephoto);
        }

        actioncamera.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(actioncamera,CAMERA_CODE);
    }

    private void startCamera() {


        if(checkPermissions()){
            openCamera();
        }



        /*if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_REQUEST_CODE);

            }
        }else{
            openCamera();
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
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),CAMERA_REQUEST_CODE );
            return false;
        }
        return true;
    }
}
