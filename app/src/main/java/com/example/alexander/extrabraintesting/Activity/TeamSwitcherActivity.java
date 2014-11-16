package com.example.alexander.extrabraintesting.Activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.alexander.extrabraintesting.Models.Team;
import com.example.alexander.extrabraintesting.Models.User;

import java.util.ArrayList;

public class TeamSwitcherActivity extends ListActivity
{
    ArrayList<Team> teamList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        teamList = User.getTeamList();
        if (teamList != null && teamList.size() == 1)
        {
            User.setSelectedTeam(teamList.get(0));
            startMainActivity();
        }

        ArrayAdapter<Team> teamAdapter = new ArrayAdapter<Team>(this, android.R.layout.simple_list_item_1, teamList);
        setListAdapter(teamAdapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        User.setSelectedTeam(teamList.get(position));
        startMainActivity();
    }

    private void startMainActivity()
    {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }
}
