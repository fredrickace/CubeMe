package com.cube_me.cubeme_crm.Contacts;

import android.os.Parcel;
import android.os.Parcelable;

import com.cube_me.cubeme_crm.AddressObject;
import com.cube_me.cubeme_crm.Email;
import com.cube_me.cubeme_crm.PhoneNumber;

import java.util.List;

/**
 * Created by Fredrick on 23-Jul-16.
 */

public class Contact implements Parcelable {

    public int contactID;
    public String contactName;
    public String contactSecondName;
    public String companyName;
    public int contactNumber;
    public String email;
    public String contactDesignation;
    public String contactAddress;
    public String notes;
    public List<AddressObject> addressList;
    public List<PhoneNumber> phoneNumberList;
    public List<Email> emailList;


    // SETTERS


    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactSecondName(String contactSecondName) {
        this.contactSecondName = contactSecondName;
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

    public void setContactDesignation(String contactDesignation) {
        this.contactDesignation = contactDesignation;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setAddressList(List<AddressObject> addressList) {
        this.addressList = addressList;
    }

    public void setPhoneNumberList(List<PhoneNumber> phoneNumberList) {
        this.phoneNumberList = phoneNumberList;
    }

    public void setEmailList(List<Email> emailList) {
        this.emailList = emailList;
    }

    // END OF SETTERS

    //PARCELABLE


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.contactID);
        dest.writeString(this.contactName);
        dest.writeString(this.contactSecondName);
        dest.writeString(this.companyName);
        dest.writeInt(this.contactNumber);
        dest.writeString(this.email);
        dest.writeString(this.contactDesignation);
        dest.writeString(this.contactAddress);
        dest.writeString(this.notes);
        dest.writeTypedList(this.phoneNumberList);
        dest.writeTypedList(this.emailList);
    }

    public Contact() {
    }

    protected Contact(Parcel in) {
        this.contactID = in.readInt();
        this.contactName = in.readString();
        this.contactSecondName = in.readString();
        this.companyName = in.readString();
        this.contactNumber = in.readInt();
        this.email = in.readString();
        this.contactDesignation = in.readString();
        this.contactAddress = in.readString();
        this.notes = in.readString();
        this.phoneNumberList = in.createTypedArrayList(PhoneNumber.CREATOR);
        this.emailList = in.createTypedArrayList(Email.CREATOR);
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

    //END OF PARCELABLES
}
