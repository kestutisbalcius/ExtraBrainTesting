package com.example.alexander.extrabraintesting.Handlers;

import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.example.alexander.extrabraintesting.Callbacks.OnTimeEntriesReady;
import com.example.alexander.extrabraintesting.Models.TimeEntry;
import com.example.alexander.extrabraintesting.Models.User;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimeEntriesReadMulti extends AsyncTask<URL,Integer,ArrayList<TimeEntry>>
{
    private final OnTimeEntriesReady listener;
    public TimeEntriesReadMulti(OnTimeEntriesReady listener, Date day)
    {
        this.listener = listener;
    }
    // Identifier of an API resource
    private String getResourceId(Date day)
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(User.getSelectedTeam().getSubdomain() + ".android-extrabrain.macchiato.standout.se")
                .appendPath("android_api")
                .appendPath("time_entries")
                .appendQueryParameter("day", getFormattedDate(day));
        return builder.build().toString();
    }
    private String getFormattedDate(Date day)
    {
        SimpleDateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd");
        return iso8601.format(day);
    }
    private String getAuthorizationToken()
    {
        String stringToEncode = User.getId() + ":" + User.getApiKey();
        String encodedString = Base64.encodeToString(stringToEncode.getBytes(), Base64.NO_WRAP);
        return "Basic " + encodedString;
    }
    private ArrayList<TimeEntry> requestTimeEntries(Date day)
    {
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("Extrabrain android client");
        HttpGet httpGet = new HttpGet(getResourceId(day));
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Authorization", getAuthorizationToken());
        ArrayList<TimeEntry> timeEntryList = new ArrayList<TimeEntry>();
        try
        {
            String rawTextResponse = httpClient.execute(httpGet, new BasicResponseHandler());
            JSONObject response = new JSONObject(rawTextResponse);
            Log.d("JSON response object",response.toString());
            JSONArray jsonTimeEntries = response.getJSONArray("time_entries");
            for (int pos = 0; pos < jsonTimeEntries.length(); pos++)
            {
                timeEntryList.add(new TimeEntry(jsonTimeEntries.getJSONObject(pos)));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        finally
        {
            httpClient.close();
        }
        return timeEntryList;
    }
    @Override
    protected ArrayList<TimeEntry> doInBackground(URL... params)
    {
        requestTimeEntries();
        requestTimeEntries();
        requestTimeEntries();
        return
    }
    @Override
    protected void onPostExecute(ArrayList<TimeEntry> timeEntries)
    {
        super.onPostExecute(timeEntries);
        listener.onTimeEntriesReady(timeEntries);
    }
}