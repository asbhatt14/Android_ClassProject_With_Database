package com.example.ankur.agencyapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ankur.agencyapp.Model.PhotoBook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALONE on 8/16/2017.
 */

public class PhotoBookDAO extends SQLiteOpenHelper {

    public PhotoBookDAO(Context context){
        super(context,"AgencyDB",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void dbInsert(PhotoBook photoBook){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = getContentValues(photoBook);
        database.insert("PhotoBook",null,values);

    }

    public List<PhotoBook> dbSearchWitName(String agentId){
        SQLiteDatabase db = getReadableDatabase();

        //String sql = "SELECT * from PhotoBook";

        String sql = "SELECT * from PhotoBook " + " WHERE "
                + " agentId " + "='" + agentId + "'";

        Cursor c = db.rawQuery(sql,null);

        List<PhotoBook> agentPhotoList = new ArrayList<PhotoBook>();

        while (c.moveToNext()){
            PhotoBook objPhotoBook = new PhotoBook();
            //id INTEGER PRIMARY KEY, name TEXT NOT NULL, photoPath TEXT)";
            objPhotoBook.setId(c.getLong(c.getColumnIndex("id")));
            objPhotoBook.setAgentId(c.getLong(c.getColumnIndex("agentId")));
            objPhotoBook.setAgeentPhoneNumber(c.getString(c.getColumnIndex("ageentPhoneNumber")));
            objPhotoBook.setPhotoPath(c.getString(c.getColumnIndex("photoPath")));

            agentPhotoList.add(objPhotoBook);
        }
        c.close();

        return agentPhotoList;
    }

    private ContentValues getContentValues(PhotoBook photoBook) {
        //id INTEGER PRIMARY KEY, agentId INTEGER,ageentPhoneNumber TEXT , photoPath TEXT)";
        ContentValues values = new ContentValues();
        values.put("agentId",photoBook.getAgentId());
        values.put("ageentPhoneNumber",photoBook.getAgeentPhoneNumber());
        values.put("photoPath",photoBook.getPhotoPath());

        return  values;
    }
}
