package com.bigfat.lmusicplayer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bigfat.lmusicplayer.common.Const;
import com.bigfat.lmusicplayer.service.AudioService;

/**
 * Created by yueban on 6/5/15.
 */
public class AudioReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Const.ACTION_AUDIO)) {
            Intent serviceIntent = new Intent(context, AudioService.class);
            serviceIntent.putExtra(Const.EXTRA_COMMAND, intent.getIntExtra(Const.EXTRA_COMMAND, -1));
            context.startService(serviceIntent);
        }
    }
}
