package com.example.ankur.agencyapp.Model;

/**
 * Created by ALONE on 8/16/2017.
 */

public class PhotoBook {
    //id INTEGER PRIMARY KEY, agentId INTEGER,ageentPhoneNumber TEXT , photoPath TEXT)";
    private long id;
    private long agentId;
    private String ageentPhoneNumber;
    private String PhotoPath;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAgentId() {
        return agentId;
    }

    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }

    public String getAgeentPhoneNumber() {
        return ageentPhoneNumber;
    }

    public void setAgeentPhoneNumber(String ageentPhoneNumber) {
        this.ageentPhoneNumber = ageentPhoneNumber;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }
}
