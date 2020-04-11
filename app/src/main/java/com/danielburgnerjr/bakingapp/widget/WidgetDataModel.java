package com.danielburgnerjr.bakingapp.widget;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

import com.danielburgnerjr.bakingapp.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

class WidgetDataModel {
    private static String RECIPE_KEY = "rec";

    static void saveRecipe(Context context, Recipe recipe) {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        prefsEditor.putString(RECIPE_KEY, json);
        prefsEditor.apply();
    }

    static Recipe getRecipe(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(RECIPE_KEY, null);
        Type type = new TypeToken<Recipe>() {}.getType();
        return gson.fromJson(json, type);
    }
}
