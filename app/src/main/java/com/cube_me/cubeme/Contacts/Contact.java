package com.cube_me.cubeme.Contacts;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fredrick on 23-Jul-16.
 */

public class Contact implements Parcelable {

    public String contactName;
    public String companyName;
    public int contactNumber;
    public String email;
    public String contactType;
    public String notes;
    Image contactImage;


    // SETTERS

    public void setName(String name) {
        contactName = name;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setContactImage(Image contactImage) {
        this.contactImage = contactImage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.contactName);
        dest.writeString(this.companyName);
        dest.writeInt(this.contactNumber);
        dest.writeString(this.email);
        dest.writeString(this.contactType);
        dest.writeString(this.notes);
    }

    public Contact() {
    }

    protected Contact(Parcel in) {
        this.contactName = in.readString();
        this.companyName = in.readString();
        this.contactNumber = in.readInt();
        this.email = in.readString();
        this.contactType = in.readString();
        this.notes = in.readString();
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
