package com.cube_me.cubeme_crm;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FredrickCyril on 1/29/17.
 */

public class MasterProjectType implements Parcelable {

    public int projectTypeID;
    public String projectTypeAbb;
    public String projectTypeName;

    public void setProjectTypeID(int projectTypeID) {
        this.projectTypeID = projectTypeID;
    }

    public void setProjectTypeAbb(String projectTypeAbb) {
        this.projectTypeAbb = projectTypeAbb;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    //PARCELABLE


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.projectTypeID);
        dest.writeString(this.projectTypeAbb);
        dest.writeString(this.projectTypeName);
    }

    public MasterProjectType() {
    }

    protected MasterProjectType(Parcel in) {
        this.projectTypeID = in.readInt();
        this.projectTypeAbb = in.readString();
        this.projectTypeName = in.readString();
    }

    public static final Parcelable.Creator<MasterProjectType> CREATOR = new Parcelable.Creator<MasterProjectType>() {
        @Override
        public MasterProjectType createFromParcel(Parcel source) {
            return new MasterProjectType(source);
        }

        @Override
        public MasterProjectType[] newArray(int size) {
            return new MasterProjectType[size];
        }
    };
}
