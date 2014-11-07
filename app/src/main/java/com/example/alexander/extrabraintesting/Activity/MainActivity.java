package com.example.alexander.extrabraintesting.Activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.alexander.extrabraintesting.Fragment.Content.TimeFragment;
import com.example.alexander.extrabraintesting.Fragment.NavigationFragments.NavigationDrawerFragment;
import com.example.alexander.extrabraintesting.Models.User;
import com.example.alexander.extrabraintesting.R;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

   /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     **/
    private NavigationDrawerFragment mNavigationDrawerFragment;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     **/
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
        getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
    if (position == 8 ) {
        User.logOut();
        Intent IntentSuccess = new Intent(this, LoginActivity.class);
        startActivity(IntentSuccess);
    }
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        TimeFragment timeFragment = new TimeFragment();
        fragmentManager.beginTransaction().replace(R.id.container, getSelectedFragment(position)).commit();
    }

    private Fragment getSelectedFragment(int drawerPosition){
        switch (drawerPosition)
        {
            case 0: return new TimeFragment();
            default: return new Fragment();
        }
    }
    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_Time);
                break;
            case 2:
                mTitle = getString(R.string.title_Tasks);
                break;
            case 3:
                mTitle = getString(R.string.title_Projects);
            case 4:
                mTitle = getString(R.string.title_Contacts);
                break;
            case 5:
                mTitle = getString(R.string.title_Invoices);
                break;
            case 6:
                mTitle = getString(R.string.title_Statistics);
            case 7:
                mTitle = getString(R.string.title_Estimates);
                break;
            case 8:
                mTitle = getString(R.string.title_Team);
                break;
        }
    }

    void restoreActionBar() {
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
                Toast.makeText(getBaseContext(), "Example action TimeEntries.", Toast.LENGTH_SHORT).show();

                Intent IntentSuccess = new Intent(this, CreateEntriesActivity.class);
                startActivityForResult(IntentSuccess);
                return true;

            case R.id.Profile:
                Toast.makeText(getBaseContext(), "Example action Profile.", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Logout:
                User.logOut();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void startActivityForResult(Intent intent) {

    }
}
