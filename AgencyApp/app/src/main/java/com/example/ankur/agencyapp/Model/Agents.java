package com.example.ankur.agencyapp.Model;

import java.io.Serializable;

/**
 * Created by ALONE on 8/3/2017.
 */

public class Agents implements Serializable {
    private long agentId;
    private String agentName;
    private String agencyName;
    private String agentLevel;
    private String agentCountry;
    private String ageentPhoneNumber;
    private String agentURL;
    private String ageentAddress;
    private String missionId;

    public Agents(long agentId, String agentName, String agencyName, String agentLevel, String agentCountry, String ageentPhoneNumber, String agentURL, String ageentAddress) {
        this.agentId = agentId;
        this.agentName = agentName;
        this.agencyName = agencyName;
        this.agentLevel = agentLevel;
        this.agentCountry = agentCountry;
        this.ageentPhoneNumber = ageentPhoneNumber;
        this.agentURL = agentURL;
        this.ageentAddress = ageentAddress;
    }

    public Agents(){

    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public long getAgentId() {
        return agentId;
    }

    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgentLevel() {
        return agentLevel;
    }

    public void setAgentLevel(String agentLevel) {
        this.agentLevel = agentLevel;
    }

    public String getAgentCountry() {
        return agentCountry;
    }

    public void setAgentCountry(String agentCountry) {
        this.agentCountry = agentCountry;
    }

    public String getAgeentPhoneNumber() {
        return ageentPhoneNumber;
    }

    public void setAgeentPhoneNumber(String ageentPhoneNumber) {
        this.ageentPhoneNumber = ageentPhoneNumber;
    }

    public String getAgentURL() {
        return agentURL;
    }

    public void setAgentURL(String agentURL) {
        this.agentURL = agentURL;
    }

    public String getAgeentAddress() {
        return ageentAddress;
    }

    public void setAgeentAddress(String ageentAddress) {
        this.ageentAddress = ageentAddress;
    }
}
