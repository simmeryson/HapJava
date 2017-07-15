package com.guok.hapandroid;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 *
 */
public class PreferencesUtil {
    public static final String KEYS = "HapKeys";
    public static final String CONFIG = "HapConfig";

    public enum NameSpace {
        HapKeys,
        HapConfig
    }

    private static Context sContext;

    public static void setContext(Context context) {
        if (sContext == null && context != null)
            sContext = context.getApplicationContext();
    }

    public static boolean putString(NameSpace nameSpace, String key, String value) {
        SharedPreferences settings = sContext.getSharedPreferences(nameSpace.name(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getString(NameSpace nameSpace, String key) {
        return getString(nameSpace, key, "");
    }

    public static String getString(NameSpace nameSpace, String key, String defaultValue) {
        SharedPreferences settings = sContext.getSharedPreferences(nameSpace.name(), Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    public static boolean putInt(NameSpace nameSpace, String key, int value) {
        SharedPreferences settings = sContext.getSharedPreferences(nameSpace.name(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }


    public static boolean putStringSet(NameSpace nameSpace, String key, Set<String> values) {
        SharedPreferences settings = sContext.getSharedPreferences(nameSpace.name(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(key, values);
        return editor.commit();
    }

    public static int getInt(NameSpace nameSpace, String key) {
        return getInt(nameSpace, key, -1);
    }

    public static int getInt(NameSpace nameSpace, String key, int defaultValue) {
        SharedPreferences settings = sContext.getSharedPreferences(nameSpace.name(), Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    public static boolean removeKey(NameSpace nameSpace, String key) {
        SharedPreferences settings = sContext.getSharedPreferences(nameSpace.name(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        return editor.commit();
    }

    public static void clear(NameSpace nameSpace) {
        SharedPreferences settings = sContext.getSharedPreferences(nameSpace.name(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();
    }

}
