package com.fenghuang.caipiaobao.widget.ijkplayer.controller.player.widget;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.fenghuang.caipiaobao.app.CaiPiaoBaoApplication;
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.player.DanmukuVideoView;

/**
 * @ Author  QinTian
 * @ Date  2019/11/19- 14:55
 * @ Describe
 */
public class PIPManager {
    private static PIPManager instance;
    private DanmukuVideoView mVideoView;
    private FloatView floatView;
    private FloatController mFloatController;
    private boolean isShowing;
    //    private KeyReceiver mKeyReceiver;
    private int mPlayingPosition = -1;
    private Class mActClass;
//    private MyVideoListener mMyVideoListener = new MyVideoListener() {
//        @Override
//        public void onComplete() {
//            super.onComplete();
//            reset();
//        }
//    };


    private PIPManager() {
        mVideoView = new DanmukuVideoView(CaiPiaoBaoApplication.Companion.getInstance());
//        mVideoView.setVideoListener(mMyVideoListener);
//        mKeyReceiver = new KeyReceiver();
        mFloatController = new FloatController(CaiPiaoBaoApplication.Companion.getInstance());
//        IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
//        MyApplication.getInstance().registerReceiver(mKeyReceiver, homeFilter);
        floatView = new FloatView(CaiPiaoBaoApplication.Companion.getInstance(), 0, 0);
    }

    public static PIPManager getInstance() {
        if (instance == null) {
            synchronized (PIPManager.class) {
                if (instance == null) {
                    instance = new PIPManager();
                }
            }
        }
        return instance;
    }

    public DanmukuVideoView getVideoView() {
        return mVideoView;
    }

    public void startFloatWindow() {
        if (isShowing) return;
        removePlayerFormParent();
        mFloatController.setPlayState(mVideoView.getCurrentPlayState());
        mFloatController.setPlayerState(mVideoView.getCurrentPlayerState());
        mVideoView.setVideoController(mFloatController);
        floatView.addView(mVideoView);
        floatView.addToWindow();
        isShowing = true;
    }

    public void stopFloatWindow() {
        if (!isShowing) return;
        floatView.removeFromWindow();
        removePlayerFormParent();
        isShowing = false;
    }

    private void removePlayerFormParent() {
        ViewParent parent = mVideoView.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(mVideoView);
        }
    }

    public int getPlayingPosition() {
        return mPlayingPosition;
    }

    public void setPlayingPosition(int position) {
        this.mPlayingPosition = position;
    }

    public void pause() {
        if (isShowing) return;
        mVideoView.pause();
    }

    public void resume() {
        if (isShowing) return;
        mVideoView.resume();
    }

    public void reset() {
        if (isShowing) return;
        removePlayerFormParent();
        mVideoView.release();
        mVideoView.setVideoController(null);
        mPlayingPosition = -1;
        mActClass = null;
    }

    public boolean onBackPress() {
        return !isShowing && mVideoView.onBackPressed();
    }

    public boolean isStartFloatWindow() {
        return isShowing;
    }

    /**
     * 显示悬浮窗
     */
    public void setFloatViewVisible() {
        if (isShowing) {
            mVideoView.resume();
            floatView.setVisibility(View.VISIBLE);
        }
    }

    public Class getActClass() {
        return mActClass;
    }

    public void setActClass(Class cls) {
        this.mActClass = cls;
    }

}
