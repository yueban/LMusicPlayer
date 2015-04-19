package com.bigfat.lmusicplayer.common;

import android.app.Application;
import android.content.Context;

/**
 * Created by yueban on 15/4/12.
 */
public class App extends Application {
    private static App app;
    private static Context context;

    public static Application getInstance() {
        if (app == null) {
            app = new App();
        }
        return app;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        context = getApplicationContext();
    }
}
