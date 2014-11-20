package com.example.alexander.extrabraintesting.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;


import com.example.alexander.extrabraintesting.Callbacks.OnTimeEntriesReady;
import com.example.alexander.extrabraintesting.Fragment.Content.TimeFragment;
import com.example.alexander.extrabraintesting.Models.PagerDay;
import com.example.alexander.extrabraintesting.Models.TimeEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 2014-11-13.
 * Edited by Haris on 2014-11-13
 */

public class TimePagerAdapter extends FragmentStatePagerAdapter implements OnTimeEntriesReady
{
    private List<PagerDay> _pageDateList;

    public TimePagerAdapter(FragmentManager fm, List<PagerDay> pageDateList)
    {
        super(fm);
        this._pageDateList = pageDateList;
        //timeEntryArrayList = sortIntoLists(timeEntryList);
    }

    // Credit to Albert for learning me HashMap
//    private ArrayList<ArrayList<TimeEntry>> sortIntoLists(List<PagerDate> PageDates)
//    {
//        HashMap<String, ArrayList<PagerDate>> hashMap = new HashMap<String, ArrayList<PagerDate>>();
//
//        ArrayList<PagerDate> dailyEntries;
//
//        for(PagerDate pageDate : PageDates){
//            for (TimeEntry timeEntry : pageDate)
//            {
//                if (hashMap.containsKey(timeEntry.getDay()))
//                {
//                    dailyEntries = hashMap.get(timeEntry.getDay());
//                }
//                else
//                {
//                    dailyEntries = new ArrayList<PagerDate>();
//                    hashMap.put(timeEntry.getDay(), dailyEntries);
//                }
//
//                dailyEntries.add(pageDate);
//            }
//        }


//        return new ArrayList<ArrayList<TimeEntry>>(hashMap.values());
//    }

   @Override
    public Fragment getItem(int i)
    {
        Log.d("Pager Item", String.valueOf(i));
        TimeFragment frag = new TimeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TimeFragment.PARCEL_TIME_ENTRY_LIST, _pageDateList.get(i));
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public int getCount()
    {
        if (_pageDateList == null)
            return 0;
        else
           return _pageDateList.size();
    }

    public void swapList(List<PagerDay> pageDateList) {
        if (_pageDateList == pageDateList)
            return;

        this._pageDateList = pageDateList;
        notifyDataSetChanged();
    }

    public List<PagerDay> getPageDates() {
        return _pageDateList;
    }

    @Override
    public void onTimeEntriesReady(ArrayList<TimeEntry> timeEntryList)
    {
        //_pageDateList.add(timeEntryList);
    }
}
