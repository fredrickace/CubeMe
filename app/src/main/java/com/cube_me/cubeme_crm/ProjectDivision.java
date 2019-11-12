package com.cube_me.cubeme_crm;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FredrickCyril on 1/29/17.
 */

public class ProjectDivision implements Parcelable {


    public int divisionID;
    public String divisionCode;
    public String divisionAbbr;
    public String divisionName;

    //SETTER

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    public void setDivisionAbbr(String divisionAbbr) {
        this.divisionAbbr = divisionAbbr;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    //PARCELABLE

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.divisionID);
        dest.writeString(this.divisionCode);
        dest.writeString(this.divisionAbbr);
        dest.writeString(this.divisionName);
    }

    public ProjectDivision() {
    }

    protected ProjectDivision(Parcel in) {
        this.divisionID = in.readInt();
        this.divisionCode = in.readString();
        this.divisionAbbr = in.readString();
        this.divisionName = in.readString();
    }

    public static final Parcelable.Creator<ProjectDivision> CREATOR = new Parcelable.Creator<ProjectDivision>() {
        @Override
        public ProjectDivision createFromParcel(Parcel source) {
            return new ProjectDivision(source);
        }

        @Override
        public ProjectDivision[] newArray(int size) {
            return new ProjectDivision[size];
        }
    };
}
