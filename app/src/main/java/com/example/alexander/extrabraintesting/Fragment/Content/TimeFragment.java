package com.example.alexander.extrabraintesting.Fragment.Content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.alexander.extrabraintesting.Activity.ChangeEntriesActivity;
import com.example.alexander.extrabraintesting.Adapter.TimeEntryAdapter;
import com.example.alexander.extrabraintesting.Models.TimeEntry;
import com.example.alexander.extrabraintesting.R;

import java.util.ArrayList;
import java.util.Date;

public class TimeFragment extends ListFragment
{
    public static final String PARCEL_TIME_ENTRY_LIST = "time entry list";
    private ArrayList<TimeEntry> timeEntryList;
    private TimeEntryAdapter timeEntryAdapter;
    private View.OnClickListener activityOnClickListener;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.d("Inside","TimeFragment");
        // Get the timeEntryList sent in with arguments
        timeEntryList = getArguments().getParcelableArrayList(PARCEL_TIME_ENTRY_LIST);
        if (timeEntryList == null)
        {
            Log.d("We've come up empty: ", "No timeEntryList");
        }

        timeEntryAdapter = new TimeEntryAdapter(getActivity(), timeEntryList);

        if (timeEntryAdapter == null)
        {
            Log.d("We've come up empty: ", "No adapter");
        }
//        setListAdapter(timeEntryAdapter);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        // When the fragment is attached to the activity, save it's listener.
//        activityOnClickListener = (View.OnClickListener) activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View timeFragmentView = inflater.inflate(R.layout.fragment_time,container,false);

        // Use the listener from the activity to send back click events to
//        timeFragmentView.setOnClickListener(activityOnClickListener);

        return timeFragmentView;
    }

    // On click item in list, deletes it server side
    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        TimeEntry selectedTimeEntry = timeEntryList.get(position);

        // Removes entry from the list locally
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
