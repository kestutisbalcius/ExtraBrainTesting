package com.example.alexander.extrabraintesting.Models;

import android.util.Log;

import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

public class Team
{
    private int id;
    private String name;
    private String subdomain;

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SUBDOMAIN = "subdomain";

    public Team(JSONObject team)
    {
        try
        {
            id = team.getInt(ID);
            name = team.getString(NAME);
            subdomain = team.getString(SUBDOMAIN);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public String getSubdomain()
    {
        return subdomain;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
