package com.example.alexander.extrabraintesting.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alexander.extrabraintesting.Models.TimeEntry;
import com.example.alexander.extrabraintesting.R;

import java.util.ArrayList;


public class TimeEntryAdapter extends ArrayAdapter<TimeEntry>
{
    private final ArrayList<TimeEntry> timeEntryList;
    private LayoutInflater inflater;

    public TimeEntryAdapter(Context context, ArrayList<TimeEntry> timeEntryList)
    {
        super(context, R.layout.time_list_item, timeEntryList);

        this.timeEntryList = timeEntryList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.time_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.textViewTitle);
            viewHolder.project = (TextView) convertView.findViewById(R.id.textViewProjectName);
            viewHolder.duration = (TextView) convertView.findViewById(R.id.textViewDuration);
            viewHolder.charging = (TextView) convertView.findViewById(R.id.textViewCharging);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TimeEntry timeEntry = timeEntryList.get(position);

        viewHolder.title.setText(timeEntry.getTitle());
        viewHolder.project.setText(timeEntry.getProject());
        viewHolder.duration.setText(timeEntry.getFormattedDuration());
        viewHolder.charging.setText(timeEntry.getCharging());

        return convertView;
    }

    private static class ViewHolder
    {
        public TextView title;
        public TextView project;
        public TextView duration;
        public TextView charging;
    }
}
