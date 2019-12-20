package com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.util;

import android.util.Log;

import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.player.VideoViewManager;


/**
 * 日志类
 * Created by dueeeke on 2017/6/5.
 */

public final class L {

    private static final String TAG = "DKPlayer";
    private static boolean isDebug = VideoViewManager.getConfig().mIsEnableLog;

    private L() {
    }

    public static void d(String msg) {
        if (isDebug) {
            Log.d(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (isDebug) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (isDebug) {
            Log.w(TAG, msg);
        }
    }

    public static void setDebug(boolean isDebug) {
        L.isDebug = isDebug;
    }
}
