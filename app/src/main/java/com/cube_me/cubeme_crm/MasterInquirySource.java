package com.cube_me.cubeme_crm;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FredrickCyril on 1/29/17.
 */

public class MasterInquirySource implements Parcelable {

    public int inquirySrcID;
    public String inquirySrcName;
    public String inquirySrcDesc;

    public void setInquirySrcID(int inquirySrcID) {
        this.inquirySrcID = inquirySrcID;
    }

    public void setInquirySrcName(String inquirySrcName) {
        this.inquirySrcName = inquirySrcName;
    }

    public void setInquirySrcDesc(String inquirySrcDesc) {
        this.inquirySrcDesc = inquirySrcDesc;
    }

    //PARCELABLE

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.inquirySrcID);
        dest.writeString(this.inquirySrcName);
        dest.writeString(this.inquirySrcDesc);
    }

    public MasterInquirySource() {
    }

    protected MasterInquirySource(Parcel in) {
        this.inquirySrcID = in.readInt();
        this.inquirySrcName = in.readString();
        this.inquirySrcDesc = in.readString();
    }

    public static final Parcelable.Creator<MasterInquirySource> CREATOR = new Parcelable.Creator<MasterInquirySource>() {
        @Override
        public MasterInquirySource createFromParcel(Parcel source) {
            return new MasterInquirySource(source);
        }

        @Override
        public MasterInquirySource[] newArray(int size) {
            return new MasterInquirySource[size];
        }
    };
}
