package com.example.alexander.extrabraintesting.Activity;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Daniel on 27/11/2014.
 */
class UpdateRefreshAnimationTask extends AsyncTask<Void, Void, Void> {

    private Context mCon;

    public UpdateRefreshAnimationTask(Context con)
    {
        mCon = con;
    }

    @Override
    protected Void doInBackground(Void... nope) {
        try {
            // Set a time to simulate a long update process.
            Thread.sleep(1000);

            return null;

        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Void nope) {
        // Give some feedback on the UI.


        // Change the menu back
        ((MainActivity) mCon).spinnerStopRefreshAnimation();
    }
}