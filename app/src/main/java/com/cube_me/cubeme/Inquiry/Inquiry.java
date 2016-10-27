package com.cube_me.cubeme.Inquiry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fredrick on 25-Jul-16.
 */

public class Inquiry implements Parcelable {

    public String inquirySubject = " ";
    public String inquiryDescription = " ";
    public String inquiryDepartment = " ";
    public String inquiryStatus = " ";
    public String inquiryID = " ";
    public String inquiryCompanyName  = " ";
    public String inquiryContactPerson = " ";
    public String contactPersonDesignation;
    public String inquiryContactNumber;
    public String inquiryEmailID = " ";
    public String inquiryAssignTo = " ";
    public String inquiryCreateDate;
    public String inquiryLastEditDate;
    public String inquiryCreateTime;
    public String inquiryLastEditTime;
    public String projectType;
    public String consultant;
    public String mainContractor;
    public String subContractor;




    // SETTERS
    public void setInquiryCreateDate(String inquiryCreateDate) {
        this.inquiryCreateDate = inquiryCreateDate;
    }

    public void setInquiryLastEditDate(String inquiryLastEditDate) {
        this.inquiryLastEditDate = inquiryLastEditDate;
    }

    public void setInquiryCreateTime(String inquiryCreateTime) {
        this.inquiryCreateTime = inquiryCreateTime;
    }

    public void setInquiryLastEditTime(String inquiryLastEditTime) {
        this.inquiryLastEditTime = inquiryLastEditTime;
    }


    public void setInquirySubject(String inquirySubject) {
        this.inquirySubject = inquirySubject;
    }

    public void setInquiryDescription(String inquiryDescription) {
        this.inquiryDescription = inquiryDescription;
    }

    public void setInquiryDepartment(String inquiryDepartment) {
        this.inquiryDepartment = inquiryDepartment;
    }

    public void setInquiryStatus(String inquiryStatus) {
        this.inquiryStatus = inquiryStatus;
    }


    public void setInquiryID(String inquiryID) {
        this.inquiryID = inquiryID;
    }

    public void setInquiryCompanyName(String inquiryCompanyName) {
        this.inquiryCompanyName = inquiryCompanyName;
    }

    public void setInquiryContactPerson(String inquiryContactPerson) {
        this.inquiryContactPerson = inquiryContactPerson;
    }

    public void setInquiryContactNumber(String inquiryContactNumber) {
        this.inquiryContactNumber = inquiryContactNumber;
    }

    public void setInquiryEmailID(String inquiryEmailID) {
        this.inquiryEmailID = inquiryEmailID;
    }

    public void setInquiryAssignTo(String inquiryAssignTo) {
        this.inquiryAssignTo = inquiryAssignTo;
    }

    public void setContactPersonDesignation(String contactPersonDesignation) {
        this.contactPersonDesignation = contactPersonDesignation;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant;
    }

    public void setMainContractor(String mainContractor) {
        this.mainContractor = mainContractor;
    }

    public void setSubContractor(String subContractor) {
        this.subContractor = subContractor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.inquirySubject);
        dest.writeString(this.inquiryDescription);
        dest.writeString(this.inquiryDepartment);
        dest.writeString(this.inquiryStatus);
        dest.writeString(this.inquiryID);
        dest.writeString(this.inquiryCompanyName);
        dest.writeString(this.inquiryContactPerson);
        dest.writeString(this.contactPersonDesignation);
        dest.writeString(this.inquiryContactNumber);
        dest.writeString(this.inquiryEmailID);
        dest.writeString(this.inquiryAssignTo);
        dest.writeString(this.inquiryCreateDate);
        dest.writeString(this.inquiryLastEditDate);
        dest.writeString(this.inquiryCreateTime);
        dest.writeString(this.inquiryLastEditTime);
        dest.writeString(this.consultant);
        dest.writeString(this.mainContractor);
        dest.writeString(this.subContractor);
        dest.writeString(this.projectType);
    }

    public Inquiry() {
    }

    protected Inquiry(Parcel in) {
        this.inquirySubject = in.readString();
        this.inquiryDescription = in.readString();
        this.inquiryDepartment = in.readString();
        this.inquiryStatus = in.readString();
        this.inquiryID = in.readString();
        this.inquiryCompanyName = in.readString();
        this.inquiryContactPerson = in.readString();
        this.contactPersonDesignation = in.readString();
        this.inquiryContactNumber = in.readString();
        this.inquiryEmailID = in.readString();
        this.inquiryAssignTo = in.readString();
        this.inquiryCreateDate = in.readString();
        this.inquiryLastEditDate = in.readString();
        this.inquiryCreateTime = in.readString();
        this.inquiryLastEditTime = in.readString();
        this.consultant = in.readString();
        this.mainContractor = in.readString();
        this.subContractor = in.readString();
        this.projectType = in.readString();
    }

    public static final Parcelable.Creator<Inquiry> CREATOR = new Parcelable.Creator<Inquiry>() {
        @Override
        public Inquiry createFromParcel(Parcel source) {
            return new Inquiry(source);
        }

        @Override
        public Inquiry[] newArray(int size) {
            return new Inquiry[size];
        }
    };
}
