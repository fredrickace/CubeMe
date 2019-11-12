package com.cube_me.cubeme_crm.Calendar;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FredrickCyril on 9/3/16.
 */
public class CalendarEvent implements Parcelable {

    public String eventDate;
    public String eventClientName;
    public String eventTitle;
    public String eventDescription;
    public String eventReport;

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventClientName(String eventClientName) {
        this.eventClientName = eventClientName;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public void setEventReport(String eventReport) {
        this.eventReport = eventReport;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.eventDate);
        dest.writeString(this.eventClientName);
        dest.writeString(this.eventTitle);
        dest.writeString(this.eventDescription);
        dest.writeString(this.eventReport);
    }

    public CalendarEvent() {
    }

    protected CalendarEvent(Parcel in) {
        this.eventDate = in.readString();
        this.eventClientName = in.readString();
        this.eventTitle = in.readString();
        this.eventDescription = in.readString();
        this.eventReport = in.readString();
    }

    public static final Parcelable.Creator<CalendarEvent> CREATOR = new Parcelable.Creator<CalendarEvent>() {
        @Override
        public CalendarEvent createFromParcel(Parcel source) {
            return new CalendarEvent(source);
        }

        @Override
        public CalendarEvent[] newArray(int size) {
            return new CalendarEvent[size];
        }
    };
}
