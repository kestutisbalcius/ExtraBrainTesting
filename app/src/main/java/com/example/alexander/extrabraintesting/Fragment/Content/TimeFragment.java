package com.example.alexander.extrabraintesting.Fragment.Content;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.example.alexander.extrabraintesting.Activity.ChangeEntriesActivity;
import com.example.alexander.extrabraintesting.Adapter.TimeEntryAdapter;
import com.example.alexander.extrabraintesting.Callbacks.OnTimeEntriesReady;
import com.example.alexander.extrabraintesting.Handlers.TimeEntriesRead;
import com.example.alexander.extrabraintesting.Models.TimeEntry;
import com.example.alexander.extrabraintesting.R;
import com.example.alexander.extrabraintesting.Views.DayButton;
import com.example.alexander.extrabraintesting.Views.WeekView;

import java.util.ArrayList;
import java.util.Date;

public class TimeFragment extends ListFragment implements OnTimeEntriesReady, RadioGroup.OnCheckedChangeListener
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

        Intent changeEntry = new Intent(getActivity(), ChangeEntriesActivity.class);
        changeEntry.putExtra(TimeEntry.PARCELABLE_TIME_ENTRY, selectedTimeEntry);
        startActivityForResult(changeEntry, ChangeEntriesActivity.EDIT_OR_REMOVE_TIME_ENTRY);

        // TimeEntryDelete timeEntryDelete = new TimeEntryDelete(this, selectedTimeEntry.getId());
        // timeEntryDelete.execute();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // default values Don't Trust
        Boolean shouldBeRemoved;
        int myTimeEntry;

        // Check with request we responding to
        if (requestCode == ChangeEntriesActivity.EDIT_OR_REMOVE_TIME_ENTRY)
        {
            // make sure the request was successful
            if (resultCode == Activity.RESULT_OK)
            {
                shouldBeRemoved = data.getBooleanExtra(ChangeEntriesActivity.REMOVING_TIME_ENTRY, false);
                if (shouldBeRemoved)
                {
                    myTimeEntry = data.getIntExtra(ChangeEntriesActivity.REMOVED_THIS_TIME_ENTRY_ID, -1);
                    removeTimeEntryFromListById(myTimeEntry);
                }
            }
        }
    }

    public void removeTimeEntryFromListById(int id)
    {
        for (TimeEntry timeEntry : timeEntryList)
        {
            if (id == timeEntry.getId())
            {
                timeEntryList.remove(timeEntry);
                timeEntryAdapter.notifyDataSetChanged();

                // Breaks the loop when done. Prevents crash on next loop because the list is modified...
                break;
            }
        }
    }
}
