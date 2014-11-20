package com.example.alexander.extrabraintesting.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by devHaris on 2014-11-15.
 */
public class PagerDay implements Parcelable {

    public PagerDay(Date date, ArrayList<TimeEntry> timeEntries){
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
    private PagerDay(Parcel in)
    {
        setDay(new Date(in.readLong()));
        _timeEntries  = new ArrayList<TimeEntry>();
        in.readTypedList(_timeEntries, TimeEntry.CREATOR);
    }

    public static final Parcelable.Creator<PagerDay> CREATOR = new Parcelable.Creator<PagerDay>()
    {
        @Override
        public PagerDay createFromParcel(Parcel source) {
            return new PagerDay(source);
        }

        @Override
        public PagerDay[] newArray(int size) {
            return new PagerDay[size];
        }
    };
}
