package com.cube_me.cubeme.Quotations;

import android.os.Parcel;
import android.os.Parcelable;

import com.cube_me.cubeme.Estimation.EstimationJobs;

import java.util.List;

/**
 * Created by FredrickCyril on 10/4/16.
 */

public class Quotation implements Parcelable {

    String quotationNo;
    String quotationTitle;
    String quotationStatus;
    String quotationDescription;
    String quotationClient;
    float quotationTotal;
    float quotationDiscount;
    boolean quotationDiscountTypePercent;   //IF DISCOUNT TYPE IS % BOOLEAN VALUE = TRUE;
    List<EstimationJobs> quotationJobs;
    String quotationCreateDate;
    String quotationCreateTime;
    String quotationEditDate;
    String quotationEditTime;

    public void setQuotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
    }

    public void setQuotationTitle(String quotationTitle) {
        this.quotationTitle = quotationTitle;
    }

    public void setQuotationStatus(String quotationStatus) {
        this.quotationStatus = quotationStatus;
    }

    public void setQuotationDescription(String quotationDescription) {
        this.quotationDescription = quotationDescription;
    }

    public void setQuotationTotal(float quotationTotal) {
        this.quotationTotal = quotationTotal;
    }

    public void setQuotationDiscount(float quotationDiscount) {
        this.quotationDiscount = quotationDiscount;
    }

    public void setQuotationDiscountTypePercent(boolean quotationDiscountTypePercent) {
        this.quotationDiscountTypePercent = quotationDiscountTypePercent;
    }

    public void setQuotationJobs(List<EstimationJobs> quotationJobs) {
        this.quotationJobs = quotationJobs;
    }

    public void setQuotationClient(String quotationClient) {
        this.quotationClient = quotationClient;
    }

    public void setQuotationCreateDate(String quotationCreateDate) {
        this.quotationCreateDate = quotationCreateDate;
    }

    public void setQuotationCreateTime(String quotationCreateTime) {
        this.quotationCreateTime = quotationCreateTime;
    }

    public void setQuotationEditDate(String quotationEditDate) {
        this.quotationEditDate = quotationEditDate;
    }

    public void setQuotationEditTime(String quotationEditTime) {
        this.quotationEditTime = quotationEditTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.quotationNo);
        dest.writeString(this.quotationTitle);
        dest.writeString(this.quotationStatus);
        dest.writeString(this.quotationDescription);
        dest.writeString(this.quotationClient);
        dest.writeFloat(this.quotationTotal);
        dest.writeFloat(this.quotationDiscount);
        dest.writeByte(this.quotationDiscountTypePercent ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.quotationJobs);
        dest.writeString(this.quotationCreateDate);
        dest.writeString(this.quotationEditDate);
        dest.writeString(this.quotationCreateTime);
        dest.writeString(this.quotationEditTime);
    }

    public Quotation() {
    }

    protected Quotation(Parcel in) {
        this.quotationNo = in.readString();
        this.quotationTitle = in.readString();
        this.quotationStatus = in.readString();
        this.quotationDescription = in.readString();
        this.quotationClient = in.readString();
        this.quotationTotal = in.readFloat();
        this.quotationDiscount = in.readFloat();
        this.quotationDiscountTypePercent = in.readByte() != 0;
        this.quotationJobs = in.createTypedArrayList(EstimationJobs.CREATOR);
        this.quotationCreateDate = in.readString();
        this.quotationEditDate = in.readString();
        this.quotationCreateTime = in.readString();
        this.quotationEditTime = in.readString();
    }

    public static final Parcelable.Creator<Quotation> CREATOR = new Parcelable.Creator<Quotation>() {
        @Override
        public Quotation createFromParcel(Parcel source) {
            return new Quotation(source);
        }

        @Override
        public Quotation[] newArray(int size) {
            return new Quotation[size];
        }
    };
}
