package com.cube_me.cubeme.Accounts;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fredrick on 14-Jul-16.
 */

public class Accounts implements Parcelable {

    String accountName;
    String accountContactName;

    public Accounts() {
//        Empty Constructor
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAccountContactName(String accountContactName) {
        this.accountContactName = accountContactName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accountName);
        dest.writeString(this.accountContactName);
    }

    protected Accounts(Parcel in) {
        this.accountName = in.readString();
        this.accountContactName = in.readString();
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
