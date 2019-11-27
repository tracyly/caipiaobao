package com.fenghuang.caipiaobao.widget.ijkplayer.controller.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fenghuang.baselib.utils.ViewUtils;
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.interf.DefinitionMediaPlayerControl;
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.player.VideoView;

import java.util.LinkedHashMap;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * 清晰度切换
 * Created by xinyu on 2018/4/16.
 */

public class DefinitionVideoView extends VideoView implements DefinitionMediaPlayerControl {

    private LinkedHashMap<String, String> mDefinitionMap;
    private String mCurrentDefinition;
    private DanmakuView mDanmaku;
    private DanmakuContext mContext;
    private BaseDanmakuParser mParser;

    // 是否直播
    private boolean mIsLive = false;

    public DefinitionVideoView(@NonNull Context context) {
        super(context);
    }

    public DefinitionVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DefinitionVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static String getValueFromLinkedMap(LinkedHashMap<String, String> map, int index) {
        int currentIndex = 0;
        for (String key : map.keySet()) {
            if (currentIndex == index) {
                return map.get(key);
            }
            currentIndex++;
        }
        return null;
    }

    @Override
    protected void initPlayer() {
        super.initPlayer();
        if (mDanmaku == null) {
            initDanMuView();
        }
        mPlayerContainer.removeView(mDanmaku);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        layoutParams.topMargin = (int) PlayerUtils.getStatusBarHeight(getContext());
        mPlayerContainer.addView(mDanmaku, layoutParams);
        //将控制器提到最顶层，如果有的话
        if (mVideoController != null) {
            mVideoController.bringToFront();
        }
    }

    private void initDanMuView() {
        mDanmaku = new DanmakuView(getContext());
        mContext = DanmakuContext.create();
        mContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3).setDuplicateMergingEnabled(false).setScrollSpeedFactor(1.2f).setScaleTextSize(1.2f)
                .setMaximumLines(null)
                .preventOverlapping(null)
                .setDanmakuMargin(20);
        if (mDanmaku != null) {
            mParser = new BaseDanmakuParser() {
                @Override
                protected IDanmakus parse() {
                    return new Danmakus();
                }
            };
            mDanmaku.setCallback(new DrawHandler.Callback() {
                @Override
                public void prepared() {
                    mDanmaku.start();
                }

                @Override
                public void updateTimer(DanmakuTimer timer) {

                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {

                }

                @Override
                public void drawingFinished() {

                }
            });
            mDanmaku.setOnDanmakuClickListener(new IDanmakuView.OnDanmakuClickListener() {
                @Override
                public boolean onDanmakuClick(IDanmakus danmakus) {
                    return false;
                }

                @Override
                public boolean onDanmakuLongClick(IDanmakus danmakus) {
                    return false;
                }

                @Override
                public boolean onViewClick(IDanmakuView view) {
                    return false;
                }
            });
//            mDanmaku.showFPS(BuildConfig.DEBUG);
            mDanmaku.enableDanmakuDrawingCache(true);
        }
    }

    /**
     * 显示弹幕
     */
    public void showDanMu() {
        if (mDanmaku != null) mDanmaku.show();
    }

    /**
     * 隐藏弹幕
     */
    public void hideDanMu() {
        if (mDanmaku != null) mDanmaku.hide();
    }

    /**
     * 发送文字弹幕
     *
     * @param text   弹幕文字
     * @param isSelf 是不是自己发的
     */
    public void addDanmaku(String text, boolean isSelf) {
        if (mDanmaku == null) return;
        mContext.setCacheStuffer(new SpannedCacheStuffer(), null);
//        mContext.alignBottom(true);
        BaseDanmaku danmaku = mContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || mDanmaku == null) {
            return;
        }

        danmaku.text = text;
        danmaku.priority = 0;  // 可能会被各种过滤器过滤并隐藏显示
        danmaku.isLive = true;
        danmaku.setTime(mDanmaku.getCurrentTime() + 1200);

        danmaku.textSize = ViewUtils.INSTANCE.sp2px(14);
        danmaku.textColor = Color.WHITE;
        danmaku.textShadowColor = Color.GRAY;
        // danmaku.underlineColor = Color.GREEN;
        danmaku.borderColor = isSelf ? Color.GREEN : Color.TRANSPARENT;
        mDanmaku.addDanmaku(danmaku);
    }

    @Override
    public void seekTo(long pos) {
        super.seekTo(pos);
        if (isInPlaybackState()) {
            if (mDanmaku != null) mDanmaku.seekTo(pos);
        }
    }

    @Override
    protected void startInPlaybackState() {
        super.startInPlaybackState();
        if (mDanmaku != null && mDanmaku.isPrepared() && mDanmaku.isPaused()) {
            mDanmaku.resume();
        }
    }

    @Override
    public void pause() {
        super.pause();
        if (isInPlaybackState()) {
            if (mDanmaku != null && mDanmaku.isPrepared()) {
                mDanmaku.pause();
            }
        }
    }

    @Override
    public void resume() {
        super.resume();
        if (mDanmaku != null && mDanmaku.isPrepared() && mDanmaku.isPaused()) {
            mDanmaku.resume();
        }
    }

    @Override
    public void release() {
        super.release();
        if (mDanmaku != null) {
            // dont forget release!
            mDanmaku.release();
            mDanmaku = null;
        }
    }


    @Override
    protected void startPrepare(boolean needReset) {
        super.startPrepare(needReset);
        if (mDanmaku != null) {
            mDanmaku.prepare(mParser, mContext);
        }
    }

    @Override
    public LinkedHashMap<String, String> getDefinitionData() {
        return mDefinitionMap;
    }

    @Override
    public void switchDefinition(String definition) {
        String url = mDefinitionMap.get(definition);
        if (definition.equals(mCurrentDefinition)) return;
        if (mIsLive) {
            replay(true);
        } else {
            addDisplay();
            getCurrentPosition();
        }
        startPrepare(true);
        mCurrentDefinition = definition;
    }

    public void setDefinitionVideos(LinkedHashMap<String, String> videos) {
        this.mDefinitionMap = videos;
    }

    public void setIsLive(boolean isLive) {
        mIsLive = isLive;
    }

}
