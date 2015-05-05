package com.bigfat.lmusicplayer.util;

import com.bigfat.lmusicplayer.common.App;

/**
 * Created by yueban on 15/5/4.
 */
public class ResUtil {
    private ResUtil() {
    }

    public static String getString(int resId) {
        return App.getContext().getResources().getString(resId);
    }

    public static int getColor(int id) {
        return App.getContext().getResources().getColor(id);
    }
}
