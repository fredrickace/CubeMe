package com.cube_me.cubeme_crm.PurchaseOrder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FredrickCyril on 9/26/16.
 */

public class PurchaseOrder implements Parcelable {

    String quoNumber;
    String PONumber;
    String POTitle;
    String PODescription;
    String clientName;
    String POCreateDate;
    String POEditDate;
    String POCreateTime;
    String POEditTime;

    //SETTERS
    public void setQuoNumber(String quoNumber) {
    }

    public void setPONumber(String PONumber) {
        this.PONumber = PONumber;
    }

    public void setPOTitle(String POTitle) {
        this.POTitle = POTitle;
    }

    public void setPODescription(String PODescription) {
        this.PODescription = PODescription;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setPOCreateDate(String POCreateDate) {
        this.POCreateDate = POCreateDate;
    }

    public void setPOEditDate(String POEditDate) {
        this.POEditDate = POEditDate;
    }

    public void setPOCreateTime(String POCreateTime) {
        this.POCreateTime = POCreateTime;
    }

    public void setPOEditTime(String POEditTime) {
        this.quoNumber = quoNumber;
        this.POEditTime = POEditTime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.quoNumber);
        dest.writeString(this.PONumber);
        dest.writeString(this.POTitle);
        dest.writeString(this.PODescription);
        dest.writeString(this.clientName);
        dest.writeString(this.POCreateDate);
        dest.writeString(this.POEditDate);
        dest.writeString(this.POCreateTime);
        dest.writeString(this.POEditTime);
    }

    public PurchaseOrder() {
    }

    protected PurchaseOrder(Parcel in) {
        this.quoNumber = in.readString();
        this.PONumber = in.readString();
        this.POTitle = in.readString();
        this.PODescription = in.readString();
        this.clientName = in.readString();
        this.POCreateDate = in.readString();
        this.POEditDate = in.readString();
        this.POCreateTime = in.readString();
        this.POEditTime = in.readString();
    }

    public static final Parcelable.Creator<PurchaseOrder> CREATOR = new Parcelable.Creator<PurchaseOrder>() {
        @Override
        public PurchaseOrder createFromParcel(Parcel source) {
            return new PurchaseOrder(source);
        }

        @Override
        public PurchaseOrder[] newArray(int size) {
            return new PurchaseOrder[size];
        }
    };
}
