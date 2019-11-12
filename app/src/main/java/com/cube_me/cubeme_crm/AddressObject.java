package com.cube_me.cubeme_crm;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FredrickCyril on 1/24/17.
 */

//CLASS FOR THE ADDRESS IN THE ACCOUNTS
public class AddressObject implements Parcelable {
    public int addressId;
    public String addressLabelName;
    public int addressLabelId;
    public String addressDetail;
    public String addressCreatedDate;

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public void setAddressLabelName(String addressLabelName) {
        this.addressLabelName = addressLabelName;
    }

    public void setAddressLabelId(int addressLabelId) {
        this.addressLabelId = addressLabelId;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public void setAddressCreatedDate(String addressCreatedDate) {
        this.addressCreatedDate = addressCreatedDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.addressId);
        dest.writeString(this.addressLabelName);
        dest.writeInt(this.addressLabelId);
        dest.writeString(this.addressDetail);
        dest.writeString(this.addressCreatedDate);
    }

    public AddressObject() {
    }

    public AddressObject(Parcel in) {
        this.addressId = in.readInt();
        this.addressLabelName = in.readString();
        this.addressLabelId = in.readInt();
        this.addressDetail = in.readString();
        this.addressCreatedDate = in.readString();
    }

    public static final Parcelable.Creator<AddressObject> CREATOR = new Parcelable.Creator<AddressObject>() {
        @Override
        public AddressObject createFromParcel(Parcel source) {
            return new AddressObject(source);
        }

        @Override
        public AddressObject[] newArray(int size) {
            return new AddressObject[size];
        }
    };
}