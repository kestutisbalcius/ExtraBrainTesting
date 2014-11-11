package com.example.alexander.extrabraintesting.Fragment.Content;


import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.alexander.extrabraintesting.Adapter.TimeEntryAdapter;
import com.example.alexander.extrabraintesting.Callbacks.OnTimeEntryDeleted;
import com.example.alexander.extrabraintesting.Handlers.TimeEntriesRead;
import com.example.alexander.extrabraintesting.Callbacks.OnTimeEntriesReady;
import com.example.alexander.extrabraintesting.Handlers.TimeEntryDelete;
import com.example.alexander.extrabraintesting.Models.SwipeDetector;
import com.example.alexander.extrabraintesting.Views.DayButton;
import com.example.alexander.extrabraintesting.Models.TimeEntry;
import com.example.alexander.extrabraintesting.Views.WeekView;
import com.example.alexander.extrabraintesting.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TimeFragment extends ListFragment implements OnTimeEntriesReady, OnTimeEntryDeleted, View.OnClickListener
{

    private ArrayList<TimeEntry> timeEntryList;
    private TimeEntryAdapter timeEntryAdapter;
    SwipeDetector swipeDetector;
    Date currentDate = new Date();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestTimeEntries(currentDate);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View timeFragmentView = inflater.inflate(R.layout.fragment_time,container,false);

        swipeDetector = new SwipeDetector();
        timeFragmentView.setOnTouchListener(swipeDetector);
        timeFragmentView.setOnClickListener(this);

        return timeFragmentView;
    }

    // Gets selected current day
    private void requestTimeEntries(Date day)
    {
        TimeEntriesRead handler = new TimeEntriesRead(this, day);
        handler.execute();
    }

    // Callback method? when an entry is ready and loaded
    @Override
    public void onTimeEntriesReady(ArrayList<TimeEntry> timeEntryList)
    {
        this.timeEntryList = timeEntryList;
        timeEntryAdapter = new TimeEntryAdapter(getActivity(), timeEntryList);
        setListAdapter(timeEntryAdapter);

        Log.d(TimeFragment.class.toString(), "Nu visas dag " + currentDate.toString() + " och i listan finns:" + timeEntryAdapter.toString());
    }

    // On click item in list, deletes it server side
    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        TimeEntry selectedTimeEntry = timeEntryList.get(position);
        TimeEntryDelete timeEntryDelete = new TimeEntryDelete(this, selectedTimeEntry.getId());
        timeEntryDelete.execute();
    }

    // Removes entry from the list locally
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

    @Override
    public void onClick(View view) {
        if (swipeDetector.getAction() == SwipeDetector.Action.LR) {
            Toast.makeText(getActivity().getApplicationContext(), "Swipe RIGHT!", Toast.LENGTH_LONG).show();

            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.DATE, -1);
            currentDate = c.getTime();

            requestTimeEntries(currentDate);

        }
        else if (swipeDetector.getAction() == SwipeDetector.Action.RL) {
            Toast.makeText(getActivity().getApplicationContext(), "Swipe LEFT!", Toast.LENGTH_LONG).show();

            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.DATE, 1);
            currentDate = c.getTime();

            requestTimeEntries(currentDate);
        }
        else if (swipeDetector.getAction() == SwipeDetector.Action.TB) {
            Toast.makeText(getActivity().getApplicationContext(), "Swipe TOP!", Toast.LENGTH_LONG).show();
        }
    }
}
