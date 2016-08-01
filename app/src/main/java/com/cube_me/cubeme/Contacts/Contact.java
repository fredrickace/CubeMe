package com.cube_me.cubeme.Contacts;

import android.media.Image;

/**
 * Created by Fredrick on 23-Jul-16.
 */

public class Contact {

    private String Name;
    private String companyName;
    private int contactNumber;
    private String email;
    private String contactType;
    private String notes;
    Image contactImage;



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
