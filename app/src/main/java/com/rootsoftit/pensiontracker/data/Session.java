package com.rootsoftit.pensiontracker.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String TOKEN = "token";
    public static User user;
    public static SharedPreferences preferences;
    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Session.token = token;

        preferences.edit().putString(TOKEN, token).apply();
    }

    public static String getBearerToken() {
        return "Bearer " + token;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Session.user = user;

        preferences.edit()
                .putString(EMAIL, user.getEmail())
                .putString(NAME, user.getName())
                .apply();
    }

    public static void logOut() {
        Session.user = null;
        Session.token = null;

        preferences.edit()
                .putString(EMAIL, "")
                .putString(NAME, "")
                .putString(TOKEN, "")
                .apply();

    }

    public static void init(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        user = new User();
        user.setEmail(preferences.getString(EMAIL, ""));
        user.setName(preferences.getString(NAME, ""));

        if (user.getEmail().isEmpty()) {
            user = null;
        }

        token = preferences.getString(TOKEN, "");
    }
}
