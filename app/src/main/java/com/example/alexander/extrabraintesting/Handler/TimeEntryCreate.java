package com.example.alexander.extrabraintesting.Handler;

        import android.net.Uri;
        import android.net.http.AndroidHttpClient;
        import android.os.AsyncTask;
        import android.util.Base64;
        import android.util.Log;

        import com.example.alexander.extrabraintesting.Callbacks.OnTimeEntryCreated;
        import com.example.alexander.extrabraintesting.Models.TimeEntry;
        import com.example.alexander.extrabraintesting.Models.User;

        import org.apache.http.HttpResponse;
        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.entity.StringEntity;
        import org.apache.http.util.EntityUtils;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;
        import java.io.UnsupportedEncodingException;
        import java.net.URL;

public class TimeEntryCreate extends AsyncTask<URL, Integer, HttpResponse>
{
    private final OnTimeEntryCreated listener;
    private final TimeEntry timeEntryNew;

    public TimeEntryCreate(OnTimeEntryCreated listener, TimeEntry timeEntryNew)
    {
        this.listener = listener;
        this.timeEntryNew = timeEntryNew;
    }

    @Override
    protected HttpResponse doInBackground(URL... params)
    {
        HttpPost post = getHttpPost(getApiUri(), getAccessToken(), timeEntryNew.getJSON());

        HttpResponse response = executeApiRequest(post);

        return response;
    }

    @Override
    protected void onPostExecute(HttpResponse response)
    {
        super.onPostExecute(response);
        String responseContent = getStringFromEntity(response);
        Log.d("onPostExecute:",responseContent);
        JSONObject jsonTimeEntry = responseToJson(responseContent);
        listener.onTimeEntryCreated(new TimeEntry(jsonTimeEntry));

        Log.d("Statuscode from Http Request", String.valueOf(response.getStatusLine().getStatusCode()));
    }

    JSONObject responseToJson(String response)
    {
        try
        {
            return new JSONObject(response);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    String getStringFromEntity(HttpResponse response)
    {
        try
        {
            return EntityUtils.toString(response.getEntity());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }


    private HttpPost getHttpPost(String uri, String accessToken, JSONObject content)
    {
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader("Authorization", accessToken);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(entityWrap(content));

        // Ruby on Rails server apparently requires an Accept Header. Gives "HTTP Error 406 Not acceptable" otherwise.
        httpPost.setHeader("Accept", "application/json");

        return httpPost;
    }

    private HttpResponse executeApiRequest(HttpPost httpPost)
    {
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("Extrabrain Android client");

        try
        {
            HttpResponse response = httpClient.execute(httpPost);
            return response;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            httpClient.close();
        }
    }

    // Identifier of an API resource
    private String getApiUri()
    {
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("http")
                .authority("praktikanter.android-extrabrain.macchiato.standout.se")
                .appendPath("android_api")
                .appendPath("time_entries");

        return builder.build().toString();
    }

    private String getAccessToken()
    {
        String stringToEncode = User.getId() + ":" + User.getApiKey();
        String encodedString = Base64.encodeToString(stringToEncode.getBytes(), Base64.NO_WRAP);

        return "Basic " + encodedString;
    }

    private StringEntity entityWrap(JSONObject content)
    {
        try
        {
            StringEntity entity = new StringEntity(content.toString());
            return entity;
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
