package com.cube_me.cubeme_crm;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FredrickCyril on 2/4/17.
 */

public class MasterAppConstants implements Parcelable {

    public String stringSeparator;
    public String salesCode;
    public String inquiryPrefix;
    public String estimationPrefix;
    public String estimationJobPrefix;
    public String quotationPrefix;
    public String poPrefix;
    public String jobPrefix;


    //SETTERS


    public void setStringSeparator(String stringSeparator) {
        this.stringSeparator = stringSeparator;
    }

    public void setSalesCode(String salesCode) {
        this.salesCode = salesCode;
    }

    public void setInquiryPrefix(String inquiryPrefix) {
        this.inquiryPrefix = inquiryPrefix;
    }

    public void setEstimationPrefix(String estimationPrefix) {
        this.estimationPrefix = estimationPrefix;
    }

    public void setEstimationJobPrefix(String estimationJobPrefix) {
        this.estimationJobPrefix = estimationJobPrefix;
    }

    public void setQuotationPrefix(String quotationPrefix) {
        this.quotationPrefix = quotationPrefix;
    }

    public void setPoPrefix(String poPrefix) {
        this.poPrefix = poPrefix;
    }

    public void setJobPrefix(String jobPrefix) {
        this.jobPrefix = jobPrefix;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stringSeparator);
        dest.writeString(this.salesCode);
        dest.writeString(this.inquiryPrefix);
        dest.writeString(this.estimationPrefix);
        dest.writeString(this.estimationJobPrefix);
        dest.writeString(this.quotationPrefix);
        dest.writeString(this.poPrefix);
        dest.writeString(this.jobPrefix);
    }

    public MasterAppConstants() {
    }

    protected MasterAppConstants(Parcel in) {
        this.stringSeparator = in.readString();
        this.salesCode = in.readString();
        this.inquiryPrefix = in.readString();
        this.estimationPrefix = in.readString();
        this.estimationJobPrefix = in.readString();
        this.quotationPrefix = in.readString();
        this.poPrefix = in.readString();
        this.jobPrefix = in.readString();
    }

    public static final Parcelable.Creator<MasterAppConstants> CREATOR = new Parcelable.Creator<MasterAppConstants>() {
        @Override
        public MasterAppConstants createFromParcel(Parcel source) {
            return new MasterAppConstants(source);
        }

        @Override
        public MasterAppConstants[] newArray(int size) {
            return new MasterAppConstants[size];
        }
    };
}
