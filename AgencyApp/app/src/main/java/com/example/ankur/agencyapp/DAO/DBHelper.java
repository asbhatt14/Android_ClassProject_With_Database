package com.example.ankur.agencyapp.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ALONE on 8/11/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context,"AgencyDB",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlMission = "CREATE TABLE Mission(missionId INTEGER PRIMARY KEY, missionName TEXT NOT NULL,missionDate TEXT NOT NULL ,missionStatus TEXT)";
        db.execSQL(sqlMission);
        String sql = "CREATE TABLE Agent(agentId INTEGER PRIMARY KEY, agentName TEXT NOT NULL,agencyName TEXT NOT NULL ,agentLevel TEXT,agentCountry TEXT, ageentPhoneNumber TEXT, agentURL TEXT, ageentAddress TEXT NOT NULL,missionId TEXT NOT NULL,agentPhotoPath TEXT)";
        db.execSQL(sql);
        String sqlPhoto = "CREATE TABLE PhotoBook (id INTEGER PRIMARY KEY, agentId INTEGER,ageentPhoneNumber TEXT , photoPath TEXT)";
        db.execSQL(sqlPhoto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
