package com.cube_me.cubeme_crm;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FredrickCyril on 1/24/17.
 */

//CLASS FOR THE EMAIL DETAILS
public class Email implements Parcelable {

    public int accountEmailId;
    public String accountEmailLabel;
    public String accountEmail;
    public String accountEmailCreated;

    public void setAccountEmailId(int accountEmailId) {
        this.accountEmailId = accountEmailId;
    }

    public void setAccountEmailLabel(String accountEmailLabel) {
        this.accountEmailLabel = accountEmailLabel;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public void setAccountEmailCreated(String accountEmailCreated) {
        this.accountEmailCreated = accountEmailCreated;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.accountEmailId);
        dest.writeString(this.accountEmailLabel);
        dest.writeString(this.accountEmail);
        dest.writeString(this.accountEmailCreated);
    }

    public Email() {
    }

    public Email(Parcel in) {
        this.accountEmailId = in.readInt();
        this.accountEmailLabel = in.readString();
        this.accountEmail = in.readString();
        this.accountEmailCreated = in.readString();
    }

    public static final Parcelable.Creator<Email> CREATOR = new Parcelable.Creator<Email>() {
        @Override
        public Email createFromParcel(Parcel source) {
            return new Email(source);
        }

        @Override
        public Email[] newArray(int size) {
            return new Email[size];
        }
    };
}
