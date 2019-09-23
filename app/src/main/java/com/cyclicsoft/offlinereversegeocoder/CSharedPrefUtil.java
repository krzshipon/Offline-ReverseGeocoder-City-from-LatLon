/**
 * Author Shipon
 * A generic {@link android.content.SharedPreferences}
 * utility class.
 */
package com.cyclicsoft.offlinereversegeocoder;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class CSharedPrefUtil {
    public static final String TAG = CSharedPrefUtil.class.getSimpleName();

    // string
    public static void saveValue(Context context, String prefsKey, String prefsValue) {
        SharedPreferences.Editor editor = getPrefsEditor(context);
        editor.putString(prefsKey, prefsValue);
        editor.commit();
    }

    // int
    public static void saveValue(Context context, String prefsKey, int prefsValue) {
        SharedPreferences.Editor editor = getPrefsEditor(context);
        editor.putInt(prefsKey, prefsValue);
        editor.commit();
    }

    // boolean
    public static void saveValue(Context context, String prefsKey, boolean prefsValue) {
        SharedPreferences.Editor editor = getPrefsEditor(context);
        editor.putBoolean(prefsKey, prefsValue);
        editor.commit();
    }

    public static int getInt(Context context, String prefsKey) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(prefsKey, -9999);
    }

    public static int getInt(Context context, String prefsKey, int defValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(prefsKey, defValue);
    }


    public static long getLong(Context context, String prefsKey) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(prefsKey, -999999999);
    }

    public static long getLong(Context context, String prefsKey, long defValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(prefsKey, defValue);
    }

    public static float getFloat(Context context, String prefsKey) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getFloat(prefsKey, -99999999);
    }

    public static float getFloat(Context context, String prefsKey, float defValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getFloat(prefsKey, defValue);
    }

    public static String getString(Context context, String prefsKey) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(prefsKey, null);
    }

    public static String getString(Context context, String prefsKey, String defValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(prefsKey, defValue);
    }

    private static SharedPreferences.Editor getPrefsEditor(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.edit();
    }

}
