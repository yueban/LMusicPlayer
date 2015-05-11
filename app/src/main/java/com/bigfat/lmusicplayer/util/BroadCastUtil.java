package com.bigfat.lmusicplayer.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.bigfat.lmusicplayer.common.Const;

/**
 * Created by yueban on 11/5/15.
 */
public class BroadCastUtil {
    /**
     * 发送本地音频广播
     *
     * @param context       广播发起Context
     * @param command_extra 音频指令
     */
    public static void sendAudioBroadCast(Context context, int command_extra) {
        Intent intent = new Intent(Const.ACTION_AUDIO);
        intent.putExtra(Const.EXTRA_COMMAND, command_extra);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
