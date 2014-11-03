package com.example.alexander.extrabraintesting.Fragment.Content;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alexander.extrabraintesting.Handlers.TimeEntriesHandler;
import com.example.alexander.extrabraintesting.Models.TimeEntry;
import com.example.alexander.extrabraintesting.R;

import java.util.ArrayList;

public class TimeFragment extends ListFragment implements TimeEntriesHandler.OnTimeEntriesReady
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        TimeEntriesHandler handler = new TimeEntriesHandler(this);
        handler.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_time,container,false);
    }

    @Override
    public void onTimeEntriesReady(ArrayList<TimeEntry> timeEntryList)
    {

    }
}
