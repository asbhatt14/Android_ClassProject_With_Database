package com.example.ankur.agencyapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.ankur.agencyapp.Model.Agents;
import com.example.ankur.agencyapp.Model.Mission;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALONE on 8/11/2017.
 */

public class AgentDAO extends SQLiteOpenHelper {

    public AgentDAO(Context context){
        super(context,"AgencyDB",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void dbInsert(Agents agents){
        SQLiteDatabase database = getWritableDatabase();
//agentId INTEGER PRIMARY KEY, agentName TEXT NOT NULL,agencyName TEXT NOT NULL ,agentLevel TEXT,agentCountry TEXT, ageentPhoneNumber TEXT, agentURL TEXT, ageentAddress TEXT NOT NULL,missionId TEXT NOT NULL)";
        ContentValues values = getContentValues(agents);
        database.insert("Agent",null,values);
    }

    @NonNull
    private ContentValues getContentValues(Agents agents) {
        ContentValues values = new ContentValues();
        values.put("agentName",agents.getAgentName());
        values.put("agencyName",agents.getAgencyName());
        values.put("agentLevel",agents.getAgentLevel());
        values.put("agentCountry",agents.getAgentCountry());
        values.put("ageentPhoneNumber",agents.getAgeentPhoneNumber());
        values.put("agentURL",agents.getAgentURL());
        values.put("ageentAddress",agents.getAgeentAddress());
        values.put("missionId",agents.getMissionId());
        return values;
    }

    public List<Agents> dbSearch(){
        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * from Agent";

        Cursor c = db.rawQuery(sql,null);

        List<Agents> agentsList = new ArrayList<Agents>();

        while (c.moveToNext()){
            Agents objAgent = new Agents();
            //agentId INTEGER PRIMARY KEY, agentName TEXT NOT NULL,agencyName TEXT NOT NULL ,agentLevel TEXT,agentCountry TEXT, ageentPhoneNumber TEXT, agentURL TEXT, ageentAddress TEXT NOT NULL,missionId TEXT NOT NULL)";
            objAgent.setAgentId(c.getLong(c.getColumnIndex("agentId")));
            objAgent.setAgentName(c.getString(c.getColumnIndex("agentName")));
            objAgent.setAgentLevel(c.getString(c.getColumnIndex("agentLevel")));
            objAgent.setAgencyName(c.getString(c.getColumnIndex("agencyName")));
            objAgent.setAgeentAddress(c.getString(c.getColumnIndex("ageentAddress")));//ageentAddress
            objAgent.setAgeentPhoneNumber(c.getString(c.getColumnIndex("ageentPhoneNumber")));
            objAgent.setAgentCountry(c.getString(c.getColumnIndex("agentCountry")));//agentURL
            objAgent.setAgentURL(c.getString(c.getColumnIndex("agentURL")));//agentCountry
            objAgent.setMissionId(c.getString(c.getColumnIndex("missionId")));

            agentsList.add(objAgent);
        }
        c.close();

        return agentsList;
    }

    public void dbDelete(Agents agent){
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {((Long.toString(agent.getAgentId())))};
        db.delete("Agent","agentId = ?",params);
    }

    public void dbUpdate(Agents agent){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues agentData = getContentValues(agent);
        String[] params = {((Long.toString(agent.getAgentId())))};
        db.update("Agent",agentData,"agentId = ?",params);
    }

    public List<Agents> dbSearchByName(String agentName){
        SQLiteDatabase db = getReadableDatabase();

       // String sql = "SELECT * from Agent";

        String sql = "SELECT * from Agent " + " WHERE "
                + "agentName LIKE" + "'"+ agentName +"%'";

        //SELECT * FROM Customers        WHERE CustomerName LIKE '%a'
        //+ emailAddressCoulmnName + "='" + emailAddress +"'AND " + passwordCoulmnName + "='" + password +"'";

        Cursor c = db.rawQuery(sql,null);

        List<Agents> agentsList = new ArrayList<Agents>();

        while (c.moveToNext()){
            Agents objAgent = new Agents();
            //agentId INTEGER PRIMARY KEY, agentName TEXT NOT NULL,agencyName TEXT NOT NULL ,agentLevel TEXT,agentCountry TEXT, ageentPhoneNumber TEXT, agentURL TEXT, ageentAddress TEXT NOT NULL,missionId TEXT NOT NULL)";
            objAgent.setAgentId(c.getLong(c.getColumnIndex("agentId")));
            objAgent.setAgentName(c.getString(c.getColumnIndex("agentName")));
            objAgent.setAgentLevel(c.getString(c.getColumnIndex("agentLevel")));
            objAgent.setAgencyName(c.getString(c.getColumnIndex("agencyName")));
            objAgent.setAgeentAddress(c.getString(c.getColumnIndex("ageentAddress")));//ageentAddress
            objAgent.setAgeentPhoneNumber(c.getString(c.getColumnIndex("ageentPhoneNumber")));
            objAgent.setAgentCountry(c.getString(c.getColumnIndex("agentCountry")));//agentURL
            objAgent.setAgentURL(c.getString(c.getColumnIndex("agentURL")));//agentCountry
            objAgent.setMissionId(c.getString(c.getColumnIndex("missionId")));

            agentsList.add(objAgent);
        }
        c.close();

        return agentsList;
    }
}
