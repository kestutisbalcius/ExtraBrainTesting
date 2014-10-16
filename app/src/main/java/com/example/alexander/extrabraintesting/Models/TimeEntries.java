package com.example.alexander.extrabraintesting.Models;

import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class TimeEntries {

       public static int task_id;
       public static String project_id;
       public static String duration;
       public static String project_title;
       public static String title;
       public static String charging;
    public static void getTimeEntries(JSONObject jsonObject) {
        try {
            task_id = jsonObject.getInt("task_id");
            project_id = jsonObject.getString("project_id");
            duration = jsonObject.getString("duration");
            project_title = jsonObject.getString("project_title");
            title = jsonObject.getString("title");
            charging = jsonObject.getString("charging");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
