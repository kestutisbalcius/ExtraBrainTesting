package com.example.alexander.extrabraintesting.Fragment.Content;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.alexander.extrabraintesting.Adapter.TimeEntryAdapter;
import com.example.alexander.extrabraintesting.Handlers.TimeEntryHandler;
import com.example.alexander.extrabraintesting.Models.DayButton;
import com.example.alexander.extrabraintesting.Models.TimeEntry;
import com.example.alexander.extrabraintesting.Models.WeekView;
import com.example.alexander.extrabraintesting.R;

import java.util.ArrayList;
import java.util.Date;

public class TimeFragment extends ListFragment implements TimeEntryHandler.OnTimeEntriesReady, RadioGroup.OnCheckedChangeListener
{
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
        TimeEntryHandler handler = new TimeEntryHandler(this, day);
        handler.execute();
    }

    @Override
    public void onTimeEntriesReady(ArrayList<TimeEntry> timeEntryList)
    {
        setListAdapter(new TimeEntryAdapter(getActivity(), timeEntryList));
    }
}
