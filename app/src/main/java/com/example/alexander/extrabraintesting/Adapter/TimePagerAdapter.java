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

/**
 * Created by Alexander on 2014-11-13.
 */
public class TimePagerAdapter extends FragmentStatePagerAdapter implements OnTimeEntriesReady
{
    public TimePagerAdapter(FragmentManager fm)
    {
        super(fm);
        requestTimeEntries(new Date());
    }

    @Override
    public Fragment getItem(int i)
    {
        Log.d("Pager Item", String.valueOf(i));

        return null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        return super.instantiateItem(container, position);

    }

    private void requestTimeEntries(Date day)
    {
        TimeEntriesRead handler = new TimeEntriesRead(this, day);
        handler.execute();
    }

    @Override
    public int getCount()
    {
        return 7;
    }

    @Override
    public void onTimeEntriesReady(ArrayList<TimeEntry> timeEntryList)
    {
        TimeFragment timeFragment = new TimeFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(TimeFragment.PARCEL_TIME_ENTRY_LIST, timeEntryList);
        timeFragment.setArguments(arguments);
    }

//    @Override
//    public void startUpdate(ViewGroup container)
//    {
//        super.startUpdate(container);
//        get
//    }
}
