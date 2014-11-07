package com.example.alexander.extrabraintesting.Callbacks;

import com.example.alexander.extrabraintesting.Models.TimeEntry;

import java.util.ArrayList;

/**
 * Created by Alexander on 2014-11-05.
 */
public interface OnTimeEntriesReady
{
    void onTimeEntriesReady(ArrayList<TimeEntry> timeEntryList);
}
