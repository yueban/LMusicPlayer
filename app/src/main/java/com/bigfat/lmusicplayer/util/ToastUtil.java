package com.bigfat.lmusicplayer.util;

import android.widget.Toast;

import com.bigfat.lmusicplayer.common.App;


/**
 * Created by yueban on 15/4/10.
 */
public class ToastUtil {
    private static Toast mToast;

    public static void show(String text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void show(String text, int length) {
        if (mToast == null) {
            mToast = Toast.makeText(App.getContext(), text, length);
        } else {
            mToast.setText(text);
            mToast.setDuration(length);
        }
        mToast.show();
    }
}