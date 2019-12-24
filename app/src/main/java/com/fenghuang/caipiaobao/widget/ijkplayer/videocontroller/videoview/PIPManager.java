package com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoview;

import android.app.Activity;
import android.view.View;

import com.fenghuang.caipiaobao.app.CaiPiaoBaoApplication;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.player.VideoViewManager;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.util.Tag;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.util.Utils;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoview.view.DanmukuVideoView;

/**
 * 悬浮播放
 */

public class PIPManager {

    private static PIPManager instance;
    private DanmukuVideoView mVideoView;
    private FloatView mFloatView;
    public FloatController mFloatController;
    private boolean mIsShowing;
    private int mPlayingPosition = -1;
    private Class mActClass;
    private int anchorId;
    private int liveState;
    private String photo;
    private Activity activity;

    private PIPManager() {
        mVideoView = new DanmukuVideoView(CaiPiaoBaoApplication.Companion.getInstance());
        VideoViewManager.instance().add(mVideoView, Tag.PIP);
        mFloatController = new FloatController(CaiPiaoBaoApplication.Companion.getInstance());
        mFloatView = new FloatView(CaiPiaoBaoApplication.Companion.getInstance(), 0, 0);
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

    public void startFloatWindow() {
        if (mIsShowing) return;
        Utils.removeViewFormParent(mVideoView);
        mVideoView.setVideoController(mFloatController);
        mFloatController.setPlayState(mVideoView.getCurrentPlayState());
        mFloatController.setPlayerState(mVideoView.getCurrentPlayerState());
        mFloatView.addView(mVideoView);
        mFloatView.addToWindow();
        mIsShowing = true;
    }

    public void stopFloatWindow() {
        if (!mIsShowing) return;
        mFloatView.removeFromWindow();
        Utils.removeViewFormParent(mVideoView);
        mVideoView.release(); //释放播放器从新开始播放
        mIsShowing = false;
    }

    public int getPlayingPosition() {
        return mPlayingPosition;
    }

    public void setPlayingPosition(int position) {
        this.mPlayingPosition = position;
    }

    public void pause() {
        if (mIsShowing) return;
        mVideoView.pause();
    }

    public void resume() {
        if (mIsShowing) return;
        mVideoView.resume();
    }

    public void startPlay(String url) {
        mVideoView.setUrl(url);
        mVideoView.start();
    }

    public void prePared() {
        mVideoView.onPrepared();
    }

    public void reset() {
        if (mIsShowing) return;
        Utils.removeViewFormParent(mVideoView);
        mVideoView.release();
        mVideoView.setVideoController(null);
        mPlayingPosition = -1;
        mActClass = null;
    }

    public boolean onBackPress() {
        return !mIsShowing && mVideoView.onBackPressed();
    }

    public boolean isStartFloatWindow() {
        return mIsShowing;
    }

    /**
     * 显示悬浮窗
     */
    public void setFloatViewVisible() {
        if (mIsShowing) {
            mVideoView.resume();
            mFloatView.setVisibility(View.VISIBLE);
        }
    }

    public Class getActClass() {
        return mActClass;
    }

    public void setActClass(Class cls) {
        this.mActClass = cls;
    }

    /**
     * 设置主播信息
     */
    public void setAnchorInfo(int anchorId, String photo, int liveState) {
        this.anchorId = anchorId;
        this.photo = photo;
        this.liveState = liveState;

    }

    public int getAnchorID() {
        return anchorId;
    }

    public int getLiveState() {
        return liveState;
    }


    public String getAnchorPhoto() {
        return photo;
    }


    public void setContext(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }
}
