package com.example.alexander.extrabraintesting.Fragment.Content;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.alexander.extrabraintesting.Activity.ChangeEntriesActivity;
import com.example.alexander.extrabraintesting.Activity.MainActivity;
import com.example.alexander.extrabraintesting.Adapter.TimeEntryAdapter;
import com.example.alexander.extrabraintesting.Adapter.TimePagerAdapter;
import com.example.alexander.extrabraintesting.Helper.DateHelper;
import com.example.alexander.extrabraintesting.Models.PagerDate;
import com.example.alexander.extrabraintesting.Models.TimeEntry;
import com.example.alexander.extrabraintesting.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class TimeFragment extends ListFragment implements View.OnClickListener, PullToRefreshBase.OnRefreshListener {
    public static final String PARCEL_TIME_ENTRY_LIST = "time entry list";
    private PagerDate pagerDate;
    private TimeEntryAdapter timeEntryAdapter;
    private SimpleDateFormat formatter;

    ImageButton previousDayBtn;
    ImageButton nextDayBtn;

    ViewPager viewPager;
    PullToRefreshListView pullToRefreshView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        formatter = new SimpleDateFormat("EEE, d MMM yyyy");
        // Get the timeEntryList sent in with arguments
        pagerDate = getArguments().getParcelable(PARCEL_TIME_ENTRY_LIST);
        timeEntryAdapter = new TimeEntryAdapter(getActivity(), pagerDate.getTimeEntries());

        // Sort list desc
        timeEntryAdapter.sort(new Comparator<TimeEntry>() {
            @Override
            public int compare(TimeEntry lhs, TimeEntry rhs) {
                TimeEntry stringName1 = lhs;
                TimeEntry stringName2 = rhs;

                return stringName1.getId() - stringName2.getId();
            }
        });

        // Get the pager to change page when user clicks on listener.
        viewPager  = ((MainActivity)getActivity()).getPager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View timeFragmentView = inflater.inflate(R.layout.fragment_time,container,false);

        pullToRefreshView = (PullToRefreshListView)timeFragmentView.findViewById(R.id.ptrListView);
        pullToRefreshView.setOnRefreshListener(this);
        pullToRefreshView.getRefreshableView().setBackgroundColor(getResources().getColor(R.color.standoutGreen));
        pullToRefreshView.setAdapter(timeEntryAdapter);


        TextView dateTv = (TextView)timeFragmentView.findViewById(R.id.dateTv);
        String formattedDay = DateHelper.isOneDayNear(pagerDate.getDay());
        if(formattedDay == "") {
            formattedDay = formatter.format(pagerDate.getDay());
        }
        dateTv.setText(formattedDay);

        previousDayBtn = (ImageButton)timeFragmentView.findViewById(R.id.previousDayBtn);
        nextDayBtn = (ImageButton)timeFragmentView.findViewById(R.id.nextDayBtn);
        previousDayBtn.setOnClickListener(this);
        nextDayBtn.setOnClickListener(this);

        return timeFragmentView;
    }

    public static Comparator<String> StringDescComparator = new Comparator<String>() {

        public int compare(String app1, String app2) {

            String stringName1 = app1;
            String stringName2 = app2;

            return stringName2.compareToIgnoreCase(stringName1);
        }
    };

    // On click item in list, deletes it server side
    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        TimeEntry selectedTimeEntry = pagerDate.getTimeEntries().get(position);

        // Removes entry from the list locally
        Intent changeEntry = new Intent(getActivity(), ChangeEntriesActivity.class);
        changeEntry.putExtra(TimeEntry.PARCELABLE_TIME_ENTRY, selectedTimeEntry);
        startActivityForResult(changeEntry, ChangeEntriesActivity.EDIT_OR_REMOVE_TIME_ENTRY);
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

    public void removeTimeEntryFromListById(int id){
        for (TimeEntry timeEntry : pagerDate.getTimeEntries())
        {
            if (id == timeEntry.getId())
            {
                pagerDate.getTimeEntries().remove(timeEntry);
                timeEntryAdapter.notifyDataSetChanged();

                // Breaks the loop when done. Prevents crash on next loop because the list is modified...
                break;
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.previousDayBtn:
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                break;
            case R.id.nextDayBtn:
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                break;
        }
    }

    @Override
    public void onRefresh(final PullToRefreshBase pullToRefreshBase) {
        pullToRefreshBase.getRefreshableView().setBackgroundColor(getResources().getColor(R.color.standoutGreen));
        // Do work to refresh the list here.
        timeEntryAdapter.insert(new TimeEntry(0, "New time entry", 0, "internal", ""), 0);

        timeEntryAdapter.notifyDataSetChanged();

        // TODO: Init a view to set the newly created time entry.

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                pullToRefreshBase.onRefreshComplete();
            }
        }, 200);
    }
}
