package com.cube_me.cubeme_crm.Accounts;

import android.os.Parcel;
import android.os.Parcelable;

import com.cube_me.cubeme_crm.PhoneNumber;
import com.cube_me.cubeme_crm.AddressObject;
import com.cube_me.cubeme_crm.Email;

import java.util.List;

/**
 * Created by Fredrick on 14-Jul-16.
 */

public class Accounts implements Parcelable {

    public String accountName;
    public String accountContactName;
    public int accountId;
    public String accountCode;
    public String accountAbbreviation;
    public String accountFax;
    public String accountContactPerson;
    public String accountDescription;
    public String accountCreatedDate;
    public String accountWeb;
    public List<AddressObject> addressObjectList;
    public List<Email> emailList;
    public List<PhoneNumber> phoneNumberList;

    public void setAccountWeb(String accountWeb) {
        this.accountWeb = accountWeb;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAccountContactName(String accountContactName) {
        this.accountContactName = accountContactName;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public void setAccountAbbreviation(String accountAbbreviation) {
        this.accountAbbreviation = accountAbbreviation;
    }

    public void setAccountFax(String accountFax) {
        this.accountFax = accountFax;
    }

    public void setAccountContactPerson(String accountContactPerson) {
        this.accountContactPerson = accountContactPerson;
    }

    public void setAccountDescription(String accountDescription) {
        this.accountDescription = accountDescription;
    }

    public void setAccountCreatedDate(String accountCreatedDate) {
        this.accountCreatedDate = accountCreatedDate;
    }

    public void setAddressObjectList(List<AddressObject> addressObjectList) {
        this.addressObjectList = addressObjectList;
    }

    public void setEmailList(List<Email> emailList) {
        this.emailList = emailList;
    }

    public void setPhoneNumberList(List<PhoneNumber> phoneNumberList) {
        this.phoneNumberList = phoneNumberList;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accountName);
        dest.writeString(this.accountContactName);
        dest.writeInt(this.accountId);
        dest.writeString(this.accountCode);
        dest.writeString(this.accountAbbreviation);
        dest.writeString(this.accountFax);
        dest.writeString(this.accountContactPerson);
        dest.writeString(this.accountDescription);
        dest.writeString(this.accountCreatedDate);
        dest.writeString(this.accountWeb);
        dest.writeTypedList(this.addressObjectList);
        dest.writeTypedList(this.emailList);
        dest.writeTypedList(this.phoneNumberList);
    }

    public Accounts() {
    }

    protected Accounts(Parcel in) {
        this.accountName = in.readString();
        this.accountContactName = in.readString();
        this.accountId = in.readInt();
        this.accountCode = in.readString();
        this.accountAbbreviation = in.readString();
        this.accountFax = in.readString();
        this.accountContactPerson = in.readString();
        this.accountDescription = in.readString();
        this.accountCreatedDate = in.readString();
        this.accountWeb = in.readString();
        this.addressObjectList = in.createTypedArrayList(AddressObject.CREATOR);
        this.emailList = in.createTypedArrayList(Email.CREATOR);
        this.phoneNumberList = in.createTypedArrayList(PhoneNumber.CREATOR);
    }

    public static final Parcelable.Creator<Accounts> CREATOR = new Parcelable.Creator<Accounts>() {
        @Override
        public Accounts createFromParcel(Parcel source) {
            return new Accounts(source);
        }

        @Override
        public Accounts[] newArray(int size) {
            return new Accounts[size];
        }
    };
}

