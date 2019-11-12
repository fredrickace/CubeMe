package com.cube_me.cubeme_crm;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FredrickCyril on 1/29/17.
 */

public class MasterSalesPerson implements Parcelable {

    public int salesPersonID;
    public String salesPersonCode;
    public String salesPersonName;
    public String salesPersonEmail;

    public void setSalesPersonID(int salesPersonID) {
        this.salesPersonID = salesPersonID;
    }

    public void setSalesPersonCode(String salesPersonCode) {
        this.salesPersonCode = salesPersonCode;
    }

    public void setSalesPersonName(String salesPersonName) {
        this.salesPersonName = salesPersonName;
    }

    public void setSalesPersonEmail(String salesPersonEmail) {
        this.salesPersonEmail = salesPersonEmail;
    }

    //PARCELABLE


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.salesPersonID);
        dest.writeString(this.salesPersonCode);
        dest.writeString(this.salesPersonName);
        dest.writeString(this.salesPersonEmail);
    }

    public MasterSalesPerson() {
    }

    protected MasterSalesPerson(Parcel in) {
        this.salesPersonID = in.readInt();
        this.salesPersonCode = in.readString();
        this.salesPersonName = in.readString();
        this.salesPersonEmail = in.readString();
    }

    public static final Parcelable.Creator<MasterSalesPerson> CREATOR = new Parcelable.Creator<MasterSalesPerson>() {
        @Override
        public MasterSalesPerson createFromParcel(Parcel source) {
            return new MasterSalesPerson(source);
        }

        @Override
        public MasterSalesPerson[] newArray(int size) {
            return new MasterSalesPerson[size];
        }
    };
}
