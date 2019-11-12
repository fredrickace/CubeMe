package com.cube_me.cubeme_crm.Inquiry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fredrick on 25-Jul-16.
 */

public class Inquiry implements Parcelable {

    public int inquiryNo;
    public String clientReferenceNo;
    public String inquirySubject;
    public String inquiryDescription;
    public String inquiryDepartment;
    public String inquiryDepartmentCode;
    public int inquiryDepartmentId;
    public String inquiryStatus;
    public String inquiryStage;
    public String inquiryID;
    public String inquiryCompanyName;
    public String inquiryCompanyAbbr;
    public String inquiryCompanyPhoneNumber;
    public String inquiryCompanyEmailID;
    public String inquiryCompanyAddress;
    public String inquiryContactPerson;
    public String inquiryContactPhoneNumber;
    public String inquiryContactEmailID;
    public String contactPersonDesignation;
    public String salesPerson;
    public int salesPersonID;
    public String inquiryCreateDate;
    public String inquiryLastEditDate;
    public String inquiryCreateTime;
    public String inquiryLastEditTime;
    public String inquiryDueDate;
    public String projectType;
    public int projectTypeID;
    public String consultant;
    public String mainContractor;
    public String subContractor;
    public String inquiryUserRef;
    public String inquiryComments;
    public int inquirySourceID;
    public String inquirySource;
    public int inquiryPriorityID;





    // SETTERS


    public void setInquiryDueDate(String inquiryDueDate) {
        this.inquiryDueDate = inquiryDueDate;
    }

    public void setInquiryNo(int inquiryNo) {
        this.inquiryNo = inquiryNo;
    }

    public void setClientReferenceNo(String clientReferenceNo) {
        this.clientReferenceNo = clientReferenceNo;
    }

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

    public void setInquiryStage(String inquiryStage) {
        this.inquiryStage = inquiryStage;
    }

    public void setInquiryCompanyAbbr(String inquiryCompanyAbbr) {
        this.inquiryCompanyAbbr = inquiryCompanyAbbr;
    }

    public void setInquiryContactPhoneNumber(String inquiryContactPhoneNumber) {
        this.inquiryContactPhoneNumber = inquiryContactPhoneNumber;
    }

    public void setInquiryContactEmailID(String inquiryContactEmailID) {
        this.inquiryContactEmailID = inquiryContactEmailID;
    }

    public void setInquiryCompanyAddress(String inquiryCompanyAddress) {
        this.inquiryCompanyAddress = inquiryCompanyAddress;
    }

    public void setInquiryUserRef(String inquiryUserRef) {
        this.inquiryUserRef = inquiryUserRef;
    }

    public void setInquirySourceID(int inquirySourceID) {
        this.inquirySourceID = inquirySourceID;
    }

    public void setInquirySource(String inquirySource) {
        this.inquirySource = inquirySource;
    }

    public void setInquiryPriorityID(int inquiryPriorityID) {
        this.inquiryPriorityID = inquiryPriorityID;
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

    public void setInquiryDepartmentCode(String inquiryDepartmentCode) {
        this.inquiryDepartmentCode = inquiryDepartmentCode;
    }

    public void setInquiryDepartmentId(int inquiryDepartmentId) {
        this.inquiryDepartmentId = inquiryDepartmentId;
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

    public void setInquiryCompanyPhoneNumber(String inquiryCompanyPhoneNumber) {
        this.inquiryCompanyPhoneNumber = inquiryCompanyPhoneNumber;
    }

    public void setInquiryCompanyEmailID(String inquiryCompanyEmailID) {
        this.inquiryCompanyEmailID = inquiryCompanyEmailID;
    }

    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }

    public void setSalesPersonID(int salesPersonID) {
        this.salesPersonID = salesPersonID;
    }

    public void setContactPersonDesignation(String contactPersonDesignation) {
        this.contactPersonDesignation = contactPersonDesignation;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public void setProjectTypeID(int projectTypeID) {
        this.projectTypeID = projectTypeID;
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

    public void setInquiryComments(String inquiryComments) {
        this.inquiryComments = inquiryComments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.inquiryNo);
        dest.writeString(this.clientReferenceNo);
        dest.writeString(this.inquirySubject);
        dest.writeString(this.inquiryDescription);
        dest.writeString(this.inquiryDepartment);
        dest.writeString(this.inquiryDepartmentCode);
        dest.writeInt(this.inquiryDepartmentId);
        dest.writeString(this.inquiryStatus);
        dest.writeString(this.inquiryStage);
        dest.writeString(this.inquiryID);
        dest.writeString(this.inquiryCompanyName);
        dest.writeString(this.inquiryCompanyAbbr);
        dest.writeString(this.inquiryCompanyPhoneNumber);
        dest.writeString(this.inquiryCompanyEmailID);
        dest.writeString(this.inquiryCompanyAddress);
        dest.writeString(this.inquiryContactPerson);
        dest.writeString(this.inquiryContactPhoneNumber);
        dest.writeString(this.inquiryContactEmailID);
        dest.writeString(this.contactPersonDesignation);
        dest.writeString(this.salesPerson);
        dest.writeInt(this.salesPersonID);
        dest.writeString(this.inquiryCreateDate);
        dest.writeString(this.inquiryLastEditDate);
        dest.writeString(this.inquiryCreateTime);
        dest.writeString(this.inquiryLastEditTime);
        dest.writeString(this.inquiryDueDate);
        dest.writeString(this.projectType);
        dest.writeInt(this.projectTypeID);
        dest.writeString(this.consultant);
        dest.writeString(this.mainContractor);
        dest.writeString(this.subContractor);
        dest.writeString(this.inquiryUserRef);
        dest.writeInt(this.inquirySourceID);
        dest.writeString(this.inquirySource);
        dest.writeInt(this.inquiryPriorityID);
        dest.writeString(this.inquiryComments);
    }

    public Inquiry() {
    }

    protected Inquiry(Parcel in) {
        this.inquiryNo = in.readInt();
        this.clientReferenceNo = in.readString();
        this.inquirySubject = in.readString();
        this.inquiryDescription = in.readString();
        this.inquiryDepartment = in.readString();
        this.inquiryDepartmentCode = in.readString();
        this.inquiryDepartmentId = in.readInt();
        this.inquiryStatus = in.readString();
        this.inquiryStage = in.readString();
        this.inquiryID = in.readString();
        this.inquiryCompanyName = in.readString();
        this.inquiryCompanyAbbr = in.readString();
        this.inquiryCompanyPhoneNumber = in.readString();
        this.inquiryCompanyEmailID = in.readString();
        this.inquiryCompanyAddress = in.readString();
        this.inquiryContactPerson = in.readString();
        this.inquiryContactPhoneNumber = in.readString();
        this.inquiryContactEmailID = in.readString();
        this.contactPersonDesignation = in.readString();
        this.salesPerson = in.readString();
        this.salesPersonID = in.readInt();
        this.inquiryCreateDate = in.readString();
        this.inquiryLastEditDate = in.readString();
        this.inquiryCreateTime = in.readString();
        this.inquiryLastEditTime = in.readString();
        this.inquiryDueDate = in.readString();
        this.projectType = in.readString();
        this.projectTypeID = in.readInt();
        this.consultant = in.readString();
        this.mainContractor = in.readString();
        this.subContractor = in.readString();
        this.inquiryUserRef = in.readString();
        this.inquirySourceID = in.readInt();
        this.inquirySource = in.readString();
        this.inquiryPriorityID = in.readInt();
        this.inquiryComments = in.readString();
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
