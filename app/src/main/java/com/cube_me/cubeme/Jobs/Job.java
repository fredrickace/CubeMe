package com.cube_me.cubeme.Jobs;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FredrickCyril on 9/27/16.
 */

public class Job implements Parcelable {

    String jobNumber;
    String jobName;
    String jobDesc;
    String jobAssigned;
    String jobStartDate;
    String jobEndDate;
    String jobComments;
    String jobCreatedDate;
    String jobEditDate;
    String jobCreatedTime;
    String jobEditTime;
    String jobClient;

    //SETTERS
    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public void setJobAssigned(String jobAssigned) {
        this.jobAssigned = jobAssigned;
    }

    public void setJobStartDate(String jobStartDate) {
        this.jobStartDate = jobStartDate;
    }

    public void setJobEndDate(String jobEndDate) {
        this.jobEndDate = jobEndDate;
    }

    public void setJobComments(String jobComments) {
        this.jobComments = jobComments;
    }

    public void setJobCreatedDate(String jobCreatedDate) {
        this.jobCreatedDate = jobCreatedDate;
    }

    public void setJobEditDate(String jobEditDate) {
        this.jobEditDate = jobEditDate;
    }

    public void setJobCreatedTime(String jobCreatedTime) {
        this.jobCreatedTime = jobCreatedTime;
    }

    public void setJobEditTime(String jobEditTime) {
        this.jobEditTime = jobEditTime;
    }

    public void setJobClient(String jobClient) {
        this.jobClient = jobClient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.jobNumber);
        dest.writeString(this.jobName);
        dest.writeString(this.jobDesc);
        dest.writeString(this.jobAssigned);
        dest.writeString(this.jobStartDate);
        dest.writeString(this.jobEndDate);
        dest.writeString(this.jobComments);
        dest.writeString(this.jobCreatedDate);
        dest.writeString(this.jobEditDate);
        dest.writeString(this.jobCreatedTime);
        dest.writeString(this.jobEditTime);
        dest.writeString(this.jobClient);
    }

    public Job() {
    }

    protected Job(Parcel in) {
        this.jobNumber = in.readString();
        this.jobName = in.readString();
        this.jobDesc = in.readString();
        this.jobAssigned = in.readString();
        this.jobStartDate = in.readString();
        this.jobEndDate = in.readString();
        this.jobComments = in.readString();
        this.jobCreatedDate = in.readString();
        this.jobEditDate = in.readString();
        this.jobCreatedTime = in.readString();
        this.jobEditTime = in.readString();
        this.jobClient = in.readString();
    }

    public static final Parcelable.Creator<Job> CREATOR = new Parcelable.Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel source) {
            return new Job(source);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };
}
