package com.example.ankur.agencyapp.Controller;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankur.agencyapp.BuildConfig;
import com.example.ankur.agencyapp.DAO.MissionDAO;
import com.example.ankur.agencyapp.DAO.PhotoBookDAO;
import com.example.ankur.agencyapp.Model.Agents;
import com.example.ankur.agencyapp.Model.PhotoBook;
import com.example.ankur.agencyapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MissionUpdateGridActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    GridView missionupdate_grdImages;
    MissionGridAdapter adapter;
    Button missionupdate_btnSend;
    LinearLayout lnrAddMissionImage;
    static final int CAMERA_REQUEST_CODE = 01;
    static final int CAMERA_CODE = 05;
    String driAppPhoto;
    ImageView imagePreview;
    Agents agent;
    List<PhotoBook> lstSelectedPhotos,lstPhotoBook;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_update_grid);

        initToolBar();
        missionupdate_grdImages = (GridView) findViewById(R.id.missionupdate_grdImages);
        missionupdate_btnSend = (Button) findViewById(R.id.missionupdate_btnSend);
        lnrAddMissionImage = (LinearLayout) findViewById(R.id.lnrAddMissionImage);

        lstSelectedPhotos = new ArrayList<PhotoBook>();

        Intent intent = getIntent();
        agent = (Agents) intent.getSerializableExtra("agent");

       /* listImages.add(0,"1");
        listImages.add(1,"1");
        listImages.add(2,"1");
        listImages.add(3,"1");

        adapter = new MissionGridAdapter(getApplicationContext(),listImages);
        missionupdate_grdImages.setAdapter(adapter);*/

        missionupdate_btnSend.setEnabled(false);

        /*if(listImages.size()>0){
            missionupdate_grdImages.setVisibility(View.VISIBLE);
            missionupdate_btnSend.setEnabled(true);
        }else{
            missionupdate_grdImages.setVisibility(View.GONE);
            missionupdate_btnSend.setEnabled(false);
        }*/
        lnrAddMissionImage.setOnClickListener(this);
        missionupdate_btnSend.setOnClickListener(this);
        missionupdate_grdImages.setOnItemClickListener(this);
        bindAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();

      /*  if(listImages.size()>0){
            missionupdate_grdImages.setVisibility(View.VISIBLE);
            missionupdate_btnSend.setEnabled(true);
        }else{
            missionupdate_grdImages.setVisibility(View.GONE);
            missionupdate_btnSend.setEnabled(false);
        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    openCamera();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == CAMERA_CODE){
                loadImage();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lnrAddMissionImage:
                startCamera();
                break;
            case R.id.missionupdate_btnSend:
                sendSMS();
                break;
            default:
                break;
        }
    }

    private void startCamera() {
        if (ContextCompat.checkSelfPermission(this,
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

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else{
            openCamera();
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

    private void loadImage() {
        if(driAppPhoto != null){

            final Dialog dialog = new Dialog(this);

            View view = getLayoutInflater().inflate(R.layout.custom_dialog_imaglayout, null);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.setContentView(view);

            imagePreview = (ImageView) view.findViewById(R.id.imagePreview);
            Button dialog_btnSave = (Button) view.findViewById(R.id.dialog_btnSave);
            Button dialog_btnCacel = (Button) view.findViewById(R.id.dialog_btnCacel);

            dialog_btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if((String) imagePreview.getTag()!=null){
                        PhotoBook photoBook = new PhotoBook();

                        photoBook.setAgentId(agent.getAgentId());
                        photoBook.setAgeentPhoneNumber(agent.getAgeentPhoneNumber());
                        photoBook.setPhotoPath((String) imagePreview.getTag());


                        PhotoBookDAO photoBookDAO = new PhotoBookDAO(MissionUpdateGridActivity.this);
                        photoBookDAO.dbInsert(photoBook);
                        photoBookDAO.close();
                        //Toast.makeText(this,"Photo Added",Toast.LENGTH_SHORT).show();
                       // imagePreview.setImageResource(R.mipmap.ic_launcher);
                        imagePreview.setTag(null);
                        bindAdapter();

                    }else{
                        Toast.makeText(MissionUpdateGridActivity.this,"Please Take Photo First",Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();

                }
            });

            dialog_btnCacel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);


            dialog.show();

            Bitmap bitmap = BitmapFactory.decodeFile(driAppPhoto);
            Bitmap lowResBitmap = Bitmap.createScaledBitmap(bitmap,300,300,true);

            imagePreview.setImageBitmap(lowResBitmap);
            imagePreview.setScaleType(ImageView.ScaleType.FIT_XY);
            imagePreview.setTag(driAppPhoto);

            if(bitmap!=null)
            {
                bitmap.recycle();
                bitmap=null;
            }

            //  Toast.makeText(this,driAppPhoto,Toast.LENGTH_SHORT).show();
        }
    }

    private void bindAdapter() {
        PhotoBookDAO photoBookDAO = new PhotoBookDAO(this);
        //lstPhotoBook = photoBookDAO.dbSearch();
        lstPhotoBook = photoBookDAO.dbSearchWitName(Long.toString(agent.getAgentId()));
        photoBookDAO.close();

        if(lstPhotoBook.size()>0){
            adapter = new MissionGridAdapter(getApplicationContext(),lstPhotoBook);
            missionupdate_grdImages.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int selectedIndex = adapter.selectedPositions.indexOf(position);
        if (selectedIndex > -1) {
            adapter.selectedPositions.remove(selectedIndex);
            lstSelectedPhotos.remove(parent.getItemAtPosition(position));
            adapter.notifyDataSetChanged();
        } else {
            adapter.selectedPositions.add(position);
            lstSelectedPhotos.add((PhotoBook) parent.getItemAtPosition(position));
            adapter.notifyDataSetChanged();
        }

        if(lstSelectedPhotos.size()>0){
            missionupdate_btnSend.setEnabled(true);
        }else{
            missionupdate_btnSend.setEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView txtToolbar = (TextView) toolbar.findViewById(R.id.txtToolbar);
        ImageView imgToolbarBack = (ImageView) toolbar.findViewById(R.id.imgToolbarBack);

        txtToolbar.setText("Mission Image Add");

        setSupportActionBar(toolbar);

        imgToolbarBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    protected void sendSMS() {
        ArrayList<Uri> uris = new ArrayList<Uri>();
        String[] filePaths = new String[lstSelectedPhotos.size()] ;
        for(int i =0; i<lstSelectedPhotos.size();i++){
            filePaths[i] = lstSelectedPhotos.get(i).getPhotoPath();
        }
        for (String file : filePaths)
        {
            File fileIn = new File(file);
            Uri u = Uri.fromFile(fileIn);
            uris.add(u);
        }

        Log.i("Send SMS", "");

        String photo = "";
        for(int i =0 ; i<adapter.selectedPositions.size(); i++){
            photo = photo +" photo " + (adapter.selectedPositions.get(i)+1);
        }
        //Toast.makeText(this,photo,Toast.LENGTH_SHORT).show();
        String messageBody = "The Photo Section was fantastic, please look "+ photo + " ,they are incredible";

        Intent mmsIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        mmsIntent.putExtra("address" ,agent.getAgeentPhoneNumber());
       // mmsIntent.putExtra("",)
        mmsIntent.putExtra("sms_body", messageBody);
        mmsIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        mmsIntent.setType("image/gif");

        try {
            startActivity(Intent.createChooser(mmsIntent,"Send"));
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }
}
