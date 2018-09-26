package com.emdad.mankenaretam.model;

/**
 * Created by HP on 2018/02/02.
 */

public class User {

    private long mobileUserId;


    private String name;


    private String family;


    private String nationalCode;


    private long mobileNumber;


    private int tempCode;

    public long getMobileUserId() {
        return mobileUserId;
    }

    public void setMobileUserId(long mobileUserId) {
        this.mobileUserId = mobileUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getTempCode() {
        return tempCode;
    }

    public void setTempCode(int tempCode) {
        this.tempCode = tempCode;
    }
}
