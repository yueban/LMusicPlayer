package com.bigfat.lmusicplayer.common;

/**
 * Created by yueban on 15/4/12.
 */
public class Const {
    public static final String PACKAGE_NAME = App.getContext().getPackageName();

    //SharedPreference配置
    public static final String SP_IS_FIRST_START = PACKAGE_NAME+"is_first_start";

    //广播
    public static final String ACTION_AUDIO = PACKAGE_NAME + "action_audio";//对应AudioReceiver
    public static final String EXTRA_COMMAND = PACKAGE_NAME + "extra_command";//附加命令
    public static final String EXTRA_AUDIO_URL = PACKAGE_NAME + "extra_audio_url";//附加音频播放url

    public static final int COMMAND_AUDIO_PLAY = 1001;//播放
    public static final int COMMAND_AUDIO_PAUSE = 1002;//暂停
    public static final int COMMAND_AUDIO_PREVIOUS = 1003;//上一首
    public static final int COMMAND_AUDIO_NEXT = 1004;//下一首
    public static final int COMMAND_AUDIO_REPEAT = 1005;//重复播放模式
    public static final int COMMAND_AUDIO_RANDOM = 1006;//随机播放模式
}
