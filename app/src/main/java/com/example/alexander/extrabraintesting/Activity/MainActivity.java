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
        fragmentManager.beginTransaction()
                .replace(R.id.container, getSelectedFragment(position))
                .commit();

    }

    private Fragment getSelectedFragment(int drawerPosition){
        switch (drawerPosition)
        {
            case 0: return new TimeFragment();
            default: return new Fragment();
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
}
