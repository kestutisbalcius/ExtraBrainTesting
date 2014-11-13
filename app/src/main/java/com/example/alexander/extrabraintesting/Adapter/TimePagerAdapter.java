package com.example.alexander.extrabraintesting.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;


import com.example.alexander.extrabraintesting.Callbacks.OnTimeEntriesReady;
import com.example.alexander.extrabraintesting.Fragment.Content.TimeFragment;
import com.example.alexander.extrabraintesting.Handlers.TimeEntriesRead;
import com.example.alexander.extrabraintesting.Models.TimeEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alexander on 2014-11-13.
 * Edited by Haris on 2014-11-13
 */
public class TimePagerAdapter extends FragmentStatePagerAdapter
{
    ArrayList<TimeEntry> timeEntries;

    public TimePagerAdapter(FragmentManager fm, ArrayList<TimeEntry> timeEntries)
    {
        super(fm);
        this.timeEntries = timeEntries;
    }

    @Override
    public Fragment getItem(int i)
    {
        Log.d("Pager Item", String.valueOf(i));
        TimeFragment frag = new TimeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(TimeFragment.PARCEL_TIME_ENTRY_LIST, timeEntries);
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public int getCount()
    {
        if (timeEntries == null)
            return 0;
        else
            return 10000;//timeEntries.size();
    }

    public void swapList(ArrayList<TimeEntry> entries) {
        if (timeEntries == entries)
            return;

        this.timeEntries = entries;
        notifyDataSetChanged();
    }

    public ArrayList<TimeEntry> getTimeEntries() {
        return timeEntries;
    }
}
