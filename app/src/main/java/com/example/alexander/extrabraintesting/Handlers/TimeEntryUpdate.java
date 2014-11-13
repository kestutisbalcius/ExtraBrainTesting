package com.example.alexander.extrabraintesting.Handlers;

import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.example.alexander.extrabraintesting.Models.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class TimeEntryUpdate extends AsyncTask<URL,Integer,Integer>
{
    private int timeEntryId;

    public TimeEntryUpdate(JSONObject updatedTimeEntry)
    {
        // TimeEntry in, getJSON
        try
        {
            this.timeEntryId = updatedTimeEntry.getInt("id");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    // Identifier of an API resource
    private String getRequestUri()
    {
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("http")
               .authority("praktikanter.android-extrabrain.macchiato.standout.se")
               .appendPath("android_api")
               .appendPath("time_entries")
               .appendPath(String.valueOf(timeEntryId));

       return builder.build().toString();
    }

    private String getAuthorizationToken()
    {
        String stringToEncode = User.getId() + ":" + User.getApiKey();
        String encodedString = Base64.encodeToString(stringToEncode.getBytes(), Base64.NO_WRAP);

        return "Basic " + encodedString;
    }

    private int requestUpdate()
    {
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("Extrabrain android client");

        HttpPatch httpPatch = new HttpPatch(getRequestUri());
        httpPatch.setHeader("Authorization", getAuthorizationToken());
        // Apparently required by the Ruby on Rails server, gives "HTTP Error 406 Not acceptable" without it.
        httpPatch.setHeader("Accept","application/json");
        httpPatch.setHeader("Content-Type","application/json");

        HttpResponse response;
        int statusCode = 0;

        try
        {
            response = httpClient.execute(httpPatch);
            statusCode = response.getStatusLine().getStatusCode();
            Log.d("status code", String.valueOf(statusCode));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            httpClient.close();
        }

        return new Integer(statusCode);
    }

    @Override
    protected Integer doInBackground(URL... params)
    {
        return requestUpdate();
    }

    @Override
    protected void onPostExecute(Integer statusCode)
    {
        super.onPostExecute(statusCode);
        // TODO Delete object from local list
        Log.d("Statuscode from HttpPatch", String.valueOf(statusCode));

    }
}
