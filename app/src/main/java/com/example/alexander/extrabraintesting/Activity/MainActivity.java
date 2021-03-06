package com.example.alexander.extrabraintesting.Activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.alexander.extrabraintesting.Adapter.TimePagerAdapter;
import com.example.alexander.extrabraintesting.Callbacks.OnTimeEntriesReady;
import com.example.alexander.extrabraintesting.Fragment.NavigationFragments.NavigationDrawerFragment;
import com.example.alexander.extrabraintesting.Handler.TimeEntriesReadMulti;
import com.example.alexander.extrabraintesting.Helper.DateHelper;
import com.example.alexander.extrabraintesting.Models.PagerDay;
import com.example.alexander.extrabraintesting.Models.TimeEntry;
import com.example.alexander.extrabraintesting.Models.User;
import com.example.alexander.extrabraintesting.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnTimeEntriesReady
{
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    // Our created menu to use
    private Menu mymenu;
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
    }

    private ArrayList<Date> getDayList()
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH,-50);
        ArrayList<Date> dayList = new ArrayList<Date>();
        for (int i = 0; i < 100; i++)
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
        List<PagerDay> pagerDates = null;
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

            case 1:
                User.logOut();
                Intent IntentSuccess = new Intent(this, LoginActivity.class);
                startActivity(IntentSuccess);
            break;

            case 2:
                Log.d("onclick: ", "switchTeam!");
                Intent switchTeam = new Intent(this, TeamSwitcherActivity.class);
                startActivity(switchTeam);
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
            // We should save our menu so we can use it to reset our updater.
            mymenu = menu;
            requestTimeEntries(getDayList());
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
            case R.id.action_refresh:
                // Do animation start
                spinnerStartRefreshAnimation(item);
                new UpdateRefreshAnimationTask(this).execute();
                requestTimeEntries(getDayList());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void spinnerStartRefreshAnimation(MenuItem item) {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView iv = (ImageView)inflater.inflate(R.layout.iv_refresh, null);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate_refresh);
        rotation.setRepeatCount(Animation.INFINITE);
        iv.startAnimation(rotation);
        item.setActionView(iv);
    }

    public void spinnerStopRefreshAnimation()
    {
        // Get our refresh item from the menu
        MenuItem m = mymenu.findItem(R.id.action_refresh);
        if(m.getActionView()!=null)
        {
            // Remove the animation.
            m.getActionView().clearAnimation();
            m.setActionView(null);
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
