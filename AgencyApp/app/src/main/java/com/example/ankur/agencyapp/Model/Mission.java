package com.example.ankur.agencyapp.Model;

import java.util.Date;

/**
 * Created by ALONE on 8/3/2017.
 */

public class Mission {
    private String missionId;
    private String missionName;
    private Date missionDate;
    private String missionStatus;

    public Mission(String missionId, String missionName, Date missionDate, String missionStatus) {
        this.missionId = missionId;
        this.missionName = missionName;
        this.missionDate = missionDate;
        this.missionStatus = missionStatus;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public Date getMissionDate() {
        return missionDate;
    }

    public void setMissionDate(Date missionDate) {
        this.missionDate = missionDate;
    }

    public String getMissionStatus() {
        return missionStatus;
    }

    public void setMissionStatus(String missionStatus) {
        this.missionStatus = missionStatus;
    }
}
