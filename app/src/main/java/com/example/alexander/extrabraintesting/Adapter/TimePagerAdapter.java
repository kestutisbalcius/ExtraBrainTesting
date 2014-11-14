package com.example.alexander.extrabraintesting.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;


import com.example.alexander.extrabraintesting.Callbacks.OnTimeEntriesReady;
import com.example.alexander.extrabraintesting.Fragment.Content.TimeFragment;
import com.example.alexander.extrabraintesting.Models.TimeEntry;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Alexander on 2014-11-13.
 * Edited by Haris on 2014-11-13
 */

public class TimePagerAdapter extends FragmentStatePagerAdapter implements OnTimeEntriesReady, ViewPager.OnPageChangeListener
{
    ArrayList<ArrayList<TimeEntry>> timeEntryArrayList;

    public TimePagerAdapter(FragmentManager fm, ArrayList<TimeEntry> timeEntryList)
    {
        super(fm);

        timeEntryArrayList = sortIntoLists(timeEntryList);
    }

    // Credit to Albert for learning me HashMap
    private ArrayList<ArrayList<TimeEntry>> sortIntoLists(ArrayList<TimeEntry> allTimeEntries)
    {
        HashMap<String, ArrayList<TimeEntry>> hashMap = new HashMap<String, ArrayList<TimeEntry>>();

        ArrayList<TimeEntry> dailyEntries;
        for (TimeEntry timeEntry : allTimeEntries)
        {
            if (hashMap.containsKey(timeEntry.getDay()))
            {
                dailyEntries = hashMap.get(timeEntry.getDay());
            }
            else
            {
                dailyEntries = new ArrayList<TimeEntry>();
                hashMap.put(timeEntry.getDay(), dailyEntries);
            }

            dailyEntries.add(timeEntry);
        }

        return new ArrayList<ArrayList<TimeEntry>>(hashMap.values());
    }

   @Override
    public Fragment getItem(int i)
    {
        Log.d("Pager Item", String.valueOf(i));
        TimeFragment frag = new TimeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(TimeFragment.PARCEL_TIME_ENTRY_LIST, timeEntryArrayList.get(i));
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public int getCount()
    {
        if (timeEntryArrayList == null)
            return 0;
        else
            return 2;//timeEntries.size();
    }

    public void swapList(ArrayList<ArrayList<TimeEntry>> entries) {
        if (timeEntryArrayList == entries)
            return;

        this.timeEntryArrayList = entries;
        notifyDataSetChanged();
    }

    public List<ArrayList<TimeEntry>> getTimeEntries() {
        return timeEntryArrayList;
    }


    @Override
    public void onTimeEntriesReady(ArrayList<TimeEntry> timeEntryList)
    {
        timeEntryArrayList.add(timeEntryList);
    }


    @Override
    public void onPageScrolled(int i, float v, int i2)
    {

    }

    @Override
    public void onPageSelected(int i)
    {

    }

    @Override
    public void onPageScrollStateChanged(int i)
    {

    }
}
