package com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.util;

import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.player.VideoViewConfig;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.player.VideoViewManager;

import java.lang.reflect.Field;

/**
 * @ Author  QinTian
 * @ Date  2019/12/9- 12:04
 * @ Describe
 */
public final class Utils {

    private Utils() {
    }


    /**
     * 获取当前的播放核心
     */
    public static Object getCurrentPlayerFactory() {
        VideoViewConfig config = VideoViewManager.getConfig();
        Object playerFactory = null;
        try {
            Field mPlayerFactoryField = config.getClass().getDeclaredField("mPlayerFactory");
            mPlayerFactoryField.setAccessible(true);
            playerFactory = mPlayerFactoryField.get(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playerFactory;
    }

    /**
     * 将View从父控件中移除
     */
    public static void removeViewFormParent(View v) {
        if (v == null) return;
        ViewParent parent = v.getParent();
        if (parent instanceof FrameLayout) {
            ((FrameLayout) parent).removeView(v);
        }
    }


}