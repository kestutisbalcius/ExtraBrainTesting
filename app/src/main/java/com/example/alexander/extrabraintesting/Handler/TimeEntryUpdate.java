package com.example.alexander.extrabraintesting.Handler;

import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.example.alexander.extrabraintesting.Callbacks.OnTimeEntryUpdated;
import com.example.alexander.extrabraintesting.Models.TimeEntry;
import com.example.alexander.extrabraintesting.Models.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class TimeEntryUpdate extends AsyncTask<URL,Integer,Integer>
{
    private final OnTimeEntryUpdated listener;
    private int timeEntryId;
    private final JSONObject jsonContainer;

    public TimeEntryUpdate(OnTimeEntryUpdated listener, TimeEntry updatedTimeEntry)
    {
        // TimeEntry in, getJSON
        this.listener = listener;
        jsonContainer = updatedTimeEntry.getJSON();

        Log.d("Updated JSON", jsonContainer.toString());



        try
        {
            this.timeEntryId = jsonContainer.getJSONObject("time_entry").getInt("id");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
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
        listener.onTimeEntryUpdated();
        Log.d("Statuscode from HttpPatch", String.valueOf(statusCode));
    }

    private int requestUpdate()
    {
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("Extrabrain android client");

        HttpPatch httpPatch = new HttpPatch(getApiUri(timeEntryId));
        httpPatch.setHeader("Authorization", getAuthorizationToken());
        // Apparently required by the Ruby on Rails server, gives "HTTP Error 406 Not acceptable" without it.
        httpPatch.setHeader("Accept","application/json");
        httpPatch.setHeader("Content-Type","application/json");
        httpPatch.setEntity(getJsonContent());

        int responseCode = executeApiRequest(httpClient, httpPatch);

        return responseCode;
    }

    private int executeApiRequest(AndroidHttpClient httpClient, HttpPatch httpPatch)
    {
        try
        {
            HttpResponse response = httpClient.execute(httpPatch);
            int statusCode = response.getStatusLine().getStatusCode();
            Log.d("status code", String.valueOf(statusCode));
            return statusCode;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return 0;
        }
        finally
        {
            httpClient.close();
        }
    }

    // Identifier of an API resource
    private String getApiUri(int timeEntryId)
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

    private StringEntity getJsonContent()
    {
        try
        {
            StringEntity entity = new StringEntity(jsonContainer.toString());
            return entity;
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
