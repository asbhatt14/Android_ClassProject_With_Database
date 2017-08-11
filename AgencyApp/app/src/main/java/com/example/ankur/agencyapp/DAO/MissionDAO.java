package com.example.ankur.agencyapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ankur.agencyapp.Model.Mission;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALONE on 8/11/2017.
 */

public class MissionDAO extends SQLiteOpenHelper {

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public MissionDAO(Context context){
        super(context,"AgencyDB",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
       /* String sqlMission = "CREATE TABLE Mission(missionId INTEGER PRIMARY KEY, missionName TEXT NOT NULL,missionDate TEXT NOT NULL ,missionStatus TEXT)";
        db.execSQL(sqlMission);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void dbInsert(Mission mission){
        SQLiteDatabase database = getWritableDatabase();
//missionId INTEGER PRIMARY KEY, missionName TEXT NOT NULL,missionDate TEXT NOT NULL ,missionStatus TEXT)";
        ContentValues values = new ContentValues();
        values.put("missionName",mission.getMissionName());
        values.put("missionDate",df.format(mission.getMissionDate()));
        values.put("missionStatus",mission.getMissionStatus());

        database.insert("Mission",null,values);
    }

    public List<Mission> dbSearch(){
        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * from Mission";

        Cursor c = db.rawQuery(sql,null);

        List<Mission> missionList = new ArrayList<Mission>();

        while (c.moveToNext()){
            Mission objMission = new Mission();
            //missionId INTEGER PRIMARY KEY, missionName TEXT NOT NULL,missionDate TEXT NOT NULL ,missionStatus TEXT)";
            objMission.setMissionId(c.getLong(c.getColumnIndex("missionId")));
            objMission.setMissionName(c.getString(c.getColumnIndex("missionName")));
            objMission.setMissionStatus(c.getString(c.getColumnIndex("missionStatus")));
            try {
                objMission.setMissionDate(df.parse(c.getString(c.getColumnIndex("missionDate"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            missionList.add(objMission);
        }
        c.close();

        return missionList;
    }

}
