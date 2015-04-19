package com.bigfat.lmusicplayer.common;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yueban on 15/4/12.
 */
public class ActivityCollector {
    private static List<Activity> activities = new ArrayList<>();

    public static void add(Activity activity){
        activities.add(activity);
    }

    public static void remove(Activity activity){
        activities.remove(activity);
    }
}
