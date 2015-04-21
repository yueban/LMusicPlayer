package com.bigfat.lmusicplayer.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bigfat.lmusicplayer.common.App;


/**
 * Created by yueban on 15/4/7.
 */
public class SPUtil {
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    private static SharedPreferences getSP() {
        if (sp == null) {
            sp = PreferenceManager.getDefaultSharedPreferences(App.getInstance());
            editor = sp.edit();
        }
        return sp;
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return getSP().getBoolean(key, defValue);
    }

    public static String getString(String key, String defValue) {
        return getSP().getString(key, defValue);
    }

    public static void putString(String key, String value) {
        editor.putString(key, value).apply();
    }

    public static void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }
}
