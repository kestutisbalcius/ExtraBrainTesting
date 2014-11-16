package com.example.alexander.extrabraintesting.Handler;

import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.example.alexander.extrabraintesting.Callbacks.OnTimeEntryDeleted;
import com.example.alexander.extrabraintesting.Models.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;

import java.io.IOException;
import java.net.URL;

public class TimeEntryDelete extends AsyncTask<URL,Integer,Integer>
{
    private final int timeEntryId;
    private final OnTimeEntryDeleted listener;

    public TimeEntryDelete(OnTimeEntryDeleted listener, int timeEntryId)
    {
        this.listener = listener;
        this.timeEntryId = timeEntryId;
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

    private int requestDeletion()
    {
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("Extrabrain android client");

        HttpDelete httpDelete = new HttpDelete(getRequestUri());
        httpDelete.setHeader("Authorization", getAuthorizationToken());
        // Apparently required by the Ruby on Rails server, gives "HTTP Error 406 Not acceptable" without it.
        httpDelete.setHeader("Accept","*/*");

        HttpResponse response;
        int statusCode = 0;

        try
        {
            response = httpClient.execute(httpDelete);
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
        return requestDeletion();
    }

    @Override
    protected void onPostExecute(Integer statusCode)
    {
        super.onPostExecute(statusCode);
        // TODO Delete object from local list
        listener.onTimeEntryDeleted(timeEntryId);
    }
}
