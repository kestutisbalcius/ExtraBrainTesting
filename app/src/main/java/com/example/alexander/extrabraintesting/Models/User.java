package com.example.alexander.extrabraintesting.Models;
import android.graphics.Color;
import android.util.Log;
import com.example.alexander.extrabraintesting.Activity.LoginActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
public class User
{
    private static int id;
    private static String apiKey;
    private static String email;
    private static String username;
    private static String firstName;
    private static String lastName;
    private static String initials;
    private static int colorInitials;
    private static int colorAvatar;
    private static ArrayList<Team> teamList;
    private static Team selectedTeam;
    // Stored preference name
    public static final String STORED_API_RESPONSE = "JSON API Response";
    // JSONObject property names
    private static final String USER = "user";
    private static final String ID = "id";
    private static final String API_KEY = "api_key";
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String INITIALS = "initials";
    private static final String COLOR_INITIALS = "initials_color";
    private static final String COLOR_AVATAR = "avatar_color";
    private static final String TEAMS = "teams";
    public static boolean loadUser(String apiResponse)
    {
        if (apiResponse.isEmpty())
        {
            return false;
        }
        Log.d("User - API Response",apiResponse);
        try
        {
            JSONObject response = new JSONObject(apiResponse);
            JSONObject user = response.getJSONObject(USER);
            JSONArray teamList = user.getJSONArray(TEAMS);
            Log.d("Teamlist length", String.valueOf(teamList.length()));
            User.teamList = new ArrayList<Team>();
            for (int position = 0; position < teamList.length(); position++)
            {
                JSONObject team = teamList.getJSONObject(position);
                User.teamList.add(new Team(team));
                Log.d("Team Id", User.teamList.get(position).getSubdomain());
            }
            id = user.getInt(ID);
            apiKey = user.getString(API_KEY);
            email = user.getString(EMAIL);
            username = user.getString(USERNAME);
            firstName = user.getString(FIRST_NAME);
            lastName = user.getString(LAST_NAME);
            initials = user.getString(INITIALS);
            colorInitials = Color.parseColor(user.getString(COLOR_INITIALS));
            colorAvatar = Color.parseColor(user.getString(COLOR_AVATAR));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        LoginActivity.preferences.edit().putString(STORED_API_RESPONSE,apiResponse).apply();
        return true;
    }
    public static void logOut()
    {
        LoginActivity.preferences.edit().clear().apply();
    }
    public static boolean isLoggedIn()
    {
        return LoginActivity.preferences.contains(API_KEY);
    }
    public static int getId() {
        return id;
    }
    public static String getApiKey() {
        return apiKey;
    }
    public static String getEmail() {
        return email;
    }
    public static String getUsername() {
        return username;
    }
    public static String getFirstName() {
        return firstName;
    }
    public static String getLastName() {
        return lastName;
    }
    public static String getInitials()
    {
        return initials;
    }
    public static int getColorInitials()
    {
        return colorInitials;
    }
    public static int getColorAvatar()
    {
        return colorAvatar;
    }
    public static ArrayList<Team> getTeamList()
    {
        return teamList;
    }
    public static void setSelectedTeam(Team selectedTeam)
    {
        User.selectedTeam = selectedTeam;
    }
    public static Team getSelectedTeam()
    {
        return selectedTeam;
    }
    public static String getSubDomain()
    {
        return getSelectedTeam().getSubdomain();
    }
}