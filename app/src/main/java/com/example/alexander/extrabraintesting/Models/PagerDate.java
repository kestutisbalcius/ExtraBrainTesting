package com.example.alexander.extrabraintesting.Models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by devHaris on 2014-11-15.
 */
public class PagerDate implements Parcelable {

    public PagerDate(Date date, ArrayList<TimeEntry> timeEntries){
        _day = date;
        _timeEntries = timeEntries;
    }

    private Date _day;
    private ArrayList<TimeEntry> _timeEntries;

    public ArrayList<TimeEntry> getTimeEntries() {
        return _timeEntries;
    }

    public void setTimeEntries(List<TimeEntry> timeEntries) {
        timeEntries = timeEntries;
    }

    public Date getDay() {
        return _day;
    }

    public void setDay(Date day) {
        _day = day;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeList(getTimeEntries());
        dest.writeString(getDay().toString());
    }

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private PagerDate(Parcel in)
    {
        setDay(new Date(in.readLong()));
        _timeEntries  = new ArrayList<TimeEntry>();
        in.readTypedList(_timeEntries, TimeEntry.CREATOR);
    }

    public static final Parcelable.Creator<PagerDate> CREATOR = new Parcelable.Creator<PagerDate>()
    {
        @Override
        public PagerDate createFromParcel(Parcel source) {
            return new PagerDate(source);
        }

        @Override
        public PagerDate[] newArray(int size) {
            return new PagerDate[size];
        }
    };
}
