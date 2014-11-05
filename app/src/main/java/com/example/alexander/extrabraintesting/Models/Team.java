package com.example.alexander.extrabraintesting.Models;

import android.util.Log;

import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

class Team
{
    public static JSONObject data;
    public static void setTeam(JSONObject jsonObject)     {
        try {
            JSONObject user = jsonObject.getJSONObject("user");
            JSONArray teams = user.getJSONArray("teams");
            Log.d("JSONObject teams_data ", String.valueOf(teams));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
