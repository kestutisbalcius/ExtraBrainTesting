package com.example.alexander.extrabraintesting.Fragment.Content;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.example.alexander.extrabraintesting.Adapter.TimeEntryAdapter;
import com.example.alexander.extrabraintesting.Callbacks.OnTimeEntryDeleted;
import com.example.alexander.extrabraintesting.Handlers.TimeEntriesRead;
import com.example.alexander.extrabraintesting.Callbacks.OnTimeEntriesReady;
import com.example.alexander.extrabraintesting.Handlers.TimeEntryDelete;
import com.example.alexander.extrabraintesting.Views.DayButton;
import com.example.alexander.extrabraintesting.Models.TimeEntry;
import com.example.alexander.extrabraintesting.Views.WeekView;
import com.example.alexander.extrabraintesting.R;

import java.util.ArrayList;
import java.util.Date;

public class TimeFragment extends ListFragment implements OnTimeEntriesReady, OnTimeEntryDeleted, RadioGroup.OnCheckedChangeListener
{
    private ArrayList<TimeEntry> timeEntryList;
    private TimeEntryAdapter timeEntryAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestTimeEntries(new Date());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View timeFragmentView = inflater.inflate(R.layout.fragment_time,container,false);

        WeekView weekView = (WeekView) timeFragmentView.findViewById(R.id.weekView);
        weekView.setOnCheckedChangeListener(this);

        return timeFragmentView;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        DayButton dayButton = (DayButton) group.findViewById(checkedId);
        requestTimeEntries(dayButton.getDay());
    }

    private void requestTimeEntries(Date day)
    {
        TimeEntriesRead handler = new TimeEntriesRead(this, day);
        handler.execute();
    }

    @Override
    public void onTimeEntriesReady(ArrayList<TimeEntry> timeEntryList)
    {
        this.timeEntryList = timeEntryList;
        timeEntryAdapter = new TimeEntryAdapter(getActivity(), timeEntryList);
        setListAdapter(timeEntryAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        TimeEntry selectedTimeEntry = timeEntryList.get(position);
        TimeEntryDelete timeEntryDelete = new TimeEntryDelete(this, selectedTimeEntry.getId());
        timeEntryDelete.execute();
    }

    @Override
    public void onTimeEntryDeleted(int idDeleted)
    {
        for (TimeEntry timeEntry : timeEntryList)
        {
            if (idDeleted == timeEntry.getId())
            {
                timeEntryList.remove(timeEntry);
                timeEntryAdapter.notifyDataSetChanged();
            }
        }
    }
}
