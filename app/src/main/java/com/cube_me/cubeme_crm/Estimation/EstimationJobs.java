package com.cube_me.cubeme_crm.Estimation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FredrickCyril on 8/13/16.
 */
public class EstimationJobs implements Parcelable {
    public String jobName;
    public String jobDescription;
    public String jobUnit;
    public float jobUnitPrice;
    public float jobQuantity;
    public float jobTotal;

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public void setJobUnitPrice(float jobUnitPrice) {
        this.jobUnitPrice = jobUnitPrice;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setJobUnit(String jobUnit) {
        this.jobUnit = jobUnit;
    }

    public void setJobQuantity(float jobQuantity) {
        this.jobQuantity = jobQuantity;
    }

    public void setJobTotal(float jobTotal) {
        this.jobTotal = jobTotal;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.jobName);
        dest.writeString(this.jobDescription);
        dest.writeString(this.jobUnit);
        dest.writeFloat(this.jobQuantity);
        dest.writeFloat(this.jobTotal);
        dest.writeFloat(this.jobUnitPrice);
    }

    public EstimationJobs() {
    }

    protected EstimationJobs(Parcel in) {
        this.jobName = in.readString();
        this.jobDescription = in.readString();
        this.jobUnit = in.readString();
        this.jobQuantity = in.readFloat();
        this.jobTotal = in.readFloat();
        this.jobUnitPrice = in.readFloat();
    }

    public static final Parcelable.Creator<EstimationJobs> CREATOR = new Parcelable.Creator<EstimationJobs>() {
        @Override
        public EstimationJobs createFromParcel(Parcel source) {
            return new EstimationJobs(source);
        }

        @Override
        public EstimationJobs[] newArray(int size) {
            return new EstimationJobs[size];
        }
    };
}


