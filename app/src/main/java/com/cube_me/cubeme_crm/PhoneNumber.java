package com.cube_me.cubeme_crm;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FredrickCyril on 1/24/17.
 */

//CLASS FOR THE PHONE NUMBERS
public class PhoneNumber implements Parcelable {

    public int accountPhoneId;
    public String accountPhoneLabel;
    public String accountPhoneNumber;

    public void setAccountPhoneId(int accountPhoneId) {
        this.accountPhoneId = accountPhoneId;
    }

    public void setAccountPhoneLabel(String accountPhoneLabel) {
        this.accountPhoneLabel = accountPhoneLabel;
    }

    public void setAccountPhoneNumber(String accountPhoneNumber) {
        this.accountPhoneNumber = accountPhoneNumber;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.accountPhoneId);
        dest.writeString(this.accountPhoneLabel);
        dest.writeString(this.accountPhoneNumber);
    }

    public PhoneNumber() {
    }

    protected PhoneNumber(Parcel in) {
        this.accountPhoneId = in.readInt();
        this.accountPhoneLabel = in.readString();
        this.accountPhoneNumber = in.readString();
    }

    public static final Parcelable.Creator<PhoneNumber> CREATOR = new Parcelable.Creator<PhoneNumber>() {
        @Override
        public PhoneNumber createFromParcel(Parcel source) {
            return new PhoneNumber(source);
        }

        @Override
        public PhoneNumber[] newArray(int size) {
            return new PhoneNumber[size];
        }
    };
}
