package com.example.alexander.extrabraintesting.Activity;

import android.app.ActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.alexander.extrabraintesting.Adapter.TimePagerAdapter;
import com.example.alexander.extrabraintesting.Callbacks.OnTimeEntriesReady;
import com.example.alexander.extrabraintesting.Fragment.NavigationFragments.NavigationDrawerFragment;
import com.example.alexander.extrabraintesting.Handler.TimeEntriesReadMulti;
import com.example.alexander.extrabraintesting.Helper.DateHelper;
import com.example.alexander.extrabraintesting.Models.PagerDate;
import com.example.alexander.extrabraintesting.Models.TimeEntry;
import com.example.alexander.extrabraintesting.Models.User;
import com.example.alexander.extrabraintesting.R;
import com.example.alexander.extrabraintesting.Transformation.ZoomOutPageTransformer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnTimeEntriesReady
{
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    Date currentDate = new Date();
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment)getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout));

        requestTimeEntries(getDayList());
    }

    private ArrayList<Date> getDayList()
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(currentDate);

        ArrayList<Date> dayList = new ArrayList<Date>();
        for (int i = 0; i < 2; i++)
        {
            dayList.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dayList;
    }

    private void requestTimeEntries(ArrayList<Date> dayList)
    {
        TimeEntriesReadMulti handler = new TimeEntriesReadMulti(this, dayList);
        handler.execute();
    }

    @Override
    public void onTimeEntriesReady(ArrayList<TimeEntry> timeEntries)
    {
        viewPager = (ViewPager) findViewById(R.id.pager);
        //viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        DateHelper dateHelper = new DateHelper();
        List<PagerDate> pagerDates = null;
        pagerDates = dateHelper.CalculateDayFromToday(timeEntries);

        int center = pagerDates.size() / 2;
        viewPager.setAdapter(new TimePagerAdapter(getSupportFragmentManager(), pagerDates));
        viewPager.setCurrentItem(center);
    }

    public ViewPager getPager(){
        return viewPager;
    }


    @Override
    public void onNavigationDrawerItemSelected(int position)
    {
        switch (position)
        {
            case 0:
                requestTimeEntries(getDayList());
                break;
            case 8:
                User.logOut();
                Intent IntentSuccess = new Intent(this, LoginActivity.class);
                startActivity(IntentSuccess);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent IntentSuccess = new Intent(this, CreateEntriesActivity.class);
                startActivity(IntentSuccess);
                return true;

            case R.id.Profile:
                Toast.makeText(getBaseContext(), "Example action Profile.", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_switch_team:
                Intent switchTeam = new Intent(this, TeamSwitcherActivity.class);
                startActivity(switchTeam);
                return true;

            case R.id.Logout:
                User.logOut();
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


//    @Override
//    public void onClick(View view) {
//        if (swipeDetector.getAction() == SwipeDetector.Action.LR) {
//            Toast.makeText(this, "Swipe RIGHT!", Toast.LENGTH_LONG).show();
//
//            Calendar c = Calendar.getInstance();
//            c.setTime(currentDate);
//            c.add(Calendar.DATE, -1);
//            currentDate = c.getTime();
//
//            requestTimeEntries(currentDate);
//
//        }
//        else if (swipeDetector.getAction() == SwipeDetector.Action.RL) {
//            Toast.makeText(this, "Swipe LEFT!", Toast.LENGTH_LONG).show();
//
//            Calendar c = Calendar.getInstance();
//            c.setTime(currentDate);
//            c.add(Calendar.DATE, 1);
//            currentDate = c.getTime();
//
//            requestTimeEntries(currentDate);
//        }
//        else if (swipeDetector.getAction() == SwipeDetector.Action.TB) {
//            Toast.makeText(this, "Swipe TOP!", Toast.LENGTH_LONG).show();
//        }
//    }
}
