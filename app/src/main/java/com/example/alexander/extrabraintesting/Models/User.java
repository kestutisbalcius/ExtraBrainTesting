package com.example.alexander.extrabraintesting.Models;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    public static SharedPreferences.Editor toEdit;
    public static int id;
    public static String api_key;
    public static String email;
    public static String username;
    public static String first_name;
    public static String last_name;
    public static void setUser(JSONObject jsonObject, SharedPreferences sharedPreferences) {
        try {

            JSONObject user = jsonObject.getJSONObject("user");
            id = user.getInt("id");
            api_key = user.getString("api_key");
            email = user.getString("email");
            username = user.getString("username");
            first_name = user.getString("first_name");
            last_name = user.getString("last_name");

            toEdit = sharedPreferences.edit();
            toEdit.putInt("Id", id);
            Log.d("JSONObject user(Id)", String.valueOf(id));
            toEdit.putString("Api_key", api_key);
            Log.d("JSONObject user(Api_key)", String.valueOf(api_key));
            toEdit.putString("Email", email);
            Log.d("JSONObject user(Email)", String.valueOf(email));
            toEdit.putString("Username", username);
            Log.d("JSONObject user(Username)", String.valueOf(username));
            toEdit.putString("First_name", first_name);
            Log.d("JSONObject user(First_name)", String.valueOf(first_name));
            toEdit.putString("Last_name", last_name);
            Log.d("JSONObject user(Last_name)", String.valueOf(last_name));
            toEdit.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        User.id = id;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getApi_key() {
        return api_key;
    }

    public static void setApi_key(String api_key) {
        User.api_key = api_key;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public static String getFirst_name() {
        return first_name;
    }

    public static void setFirst_name(String first_name) {
        User.first_name = first_name;
    }

    public static String getLast_name() {
        return last_name;
    }

    public static void setLast_name(String last_name) {
        User.last_name = last_name;
    }

}
