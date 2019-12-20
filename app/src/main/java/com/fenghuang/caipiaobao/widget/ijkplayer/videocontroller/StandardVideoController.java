package com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fenghuang.caipiaobao.R;
import com.fenghuang.caipiaobao.manager.ImageManager;
import com.fenghuang.caipiaobao.ui.home.live.liveroom.HomeLiveDetailsPresenter;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.component.CompleteView;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.component.ErrorView;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.component.GestureView;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.component.LiveControlView;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.component.PrepareView;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.component.VodControlView;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.controller.GestureVideoController;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.player.VideoView;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.util.L;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.util.PlayerUtils;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoview.view.DanmukuVideoView;

/**
 * 直播/点播控制器
 * Created by dueeeke on 2017/4/7.
 */

public class StandardVideoController extends GestureVideoController implements View.OnClickListener {

    public LiveControlView liveControlView;
    protected ImageView imgAnchorNotHomePhoto, mLockButton, imgExit;
    protected ProgressBar mLoadingProgress;
    private BackListener mOnBackListener;
    private DanmukuVideoView danmukuVideoView;

    private FrameLayout fmNoAnchor;

    public StandardVideoController(@NonNull Context context) {
        this(context, null);
    }

    public StandardVideoController(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StandardVideoController(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnBackListener(BackListener listener) {
        mOnBackListener = listener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dkplayer_layout_standard_controller;
    }

    @Override
    protected void initView() {
        super.initView();
        mLockButton = findViewById(R.id.lock);
        mLockButton.setOnClickListener(this);
        mLoadingProgress = findViewById(R.id.loading);
        //-----------------
        imgExit = findViewById(R.id.imgExit);
        imgExit.setOnClickListener(this);
        fmNoAnchor = findViewById(R.id.fmNoAnchor);
        //未直播
        imgAnchorNotHomePhoto = findViewById(R.id.imgAnchorNotHomePhoto);

    }

    /**
     * 快速添加各个组件
     *
     * @param title  标题
     * @param isLive 是否为直播
     */
    public void addDefaultControlComponent(String title, boolean isLive, HomeLiveDetailsPresenter homeLiveDetailsPresenter, int anchorId) {
        CompleteView completeView = new CompleteView(getContext());
        ErrorView errorView = new ErrorView(getContext());
        PrepareView prepareView = new PrepareView(getContext());
        prepareView.setClickStart();
//        TitleView titleView = new TitleView(getContext());
//        titleView.setTitle(title);
        addControlComponent(completeView, errorView, prepareView);
        if (isLive) {
            LiveControlView liveControlView = new LiveControlView(getContext());
            liveControlView.setPresenter(homeLiveDetailsPresenter, anchorId);
            addControlComponent(liveControlView);
        } else {
            addControlComponent(new VodControlView(getContext()));
        }
        addControlComponent(new GestureView(getContext()));
        setCanChangePosition(!isLive);
    }

    public void setLiveControl(HomeLiveDetailsPresenter homeLiveDetailsPresenter, int anchorId, DanmukuVideoView danmukuVideoView) {
        this.danmukuVideoView = danmukuVideoView;
        CompleteView completeView = new CompleteView(getContext());
        ErrorView errorView = new ErrorView(getContext());
        PrepareView prepareView = new PrepareView(getContext());
        prepareView.setClickStart();
        prepareView.findViewById(R.id.imgPreBack).setOnClickListener(view -> {
            if (!onBackPressed()) {
                mOnBackListener.onBackListener();
            }
        });
        errorView.findViewById(R.id.imgErrorBack).setOnClickListener(view -> {
            if (!onBackPressed()) {
                mOnBackListener.onBackListener();
            }
        });
        addControlComponent(completeView, errorView, prepareView);
        liveControlView = new LiveControlView(getContext());
        liveControlView.setPresenter(homeLiveDetailsPresenter, anchorId);
        addControlComponent(liveControlView);
        addControlComponent(new GestureView(getContext()));
        setCanChangePosition(false);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.lock) {
            mControlWrapper.toggleLockState();
        } else if (i == R.id.imgExit) {
            if (!onBackPressed()) {
                mOnBackListener.onBackListener();
            }
        }
    }

    @Override
    protected void onLockStateChanged(boolean isLocked) {
        if (isLocked) {
            mLockButton.setSelected(true);
            Toast.makeText(getContext(), R.string.dkplayer_locked, Toast.LENGTH_SHORT).show();
        } else {
            mLockButton.setSelected(false);
            Toast.makeText(getContext(), R.string.dkplayer_unlocked, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onVisibilityChanged(boolean isVisible, Animation anim) {
        if (mControlWrapper.isFullScreen()) {
            if (isVisible) {
                if (mLockButton.getVisibility() == GONE) {
                    mLockButton.setVisibility(VISIBLE);
                    if (anim != null) {
                        mLockButton.startAnimation(anim);
                    }
                }
            } else {
                mLockButton.setVisibility(GONE);
                if (anim != null) {
                    mLockButton.startAnimation(anim);
                }
            }
        }
    }

    @Override
    protected void onPlayerStateChanged(int playerState) {
        super.onPlayerStateChanged(playerState);
        switch (playerState) {
            case VideoView.PLAYER_NORMAL:
                L.e("PLAYER_NORMAL");
                setLayoutParams(new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                mLockButton.setVisibility(GONE);
                if (danmukuVideoView != null) danmukuVideoView.hideDanMu();
                imgExit.setVisibility(VISIBLE);
                break;
            case VideoView.PLAYER_FULL_SCREEN:
                L.e("PLAYER_FULL_SCREEN");
                if (isShowing()) {
                    mLockButton.setVisibility(VISIBLE);
                } else {
                    mLockButton.setVisibility(GONE);
                }
                imgExit.setVisibility(GONE);
                if (danmukuVideoView != null) danmukuVideoView.showDanMu();
                break;

        }

        if (mActivity != null && hasCutout()) {
            int orientation = mActivity.getRequestedOrientation();
            int dp24 = PlayerUtils.dp2px(getContext(), 24);
            int cutoutHeight = getCutoutHeight();
            if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                FrameLayout.LayoutParams lblp = (FrameLayout.LayoutParams) mLockButton.getLayoutParams();
                lblp.setMargins(dp24, 0, dp24, 0);
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mLockButton.getLayoutParams();
                layoutParams.setMargins(dp24 + cutoutHeight, 0, dp24 + cutoutHeight, 0);
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mLockButton.getLayoutParams();
                layoutParams.setMargins(dp24, 0, dp24, 0);
            }
        }

    }

    @Override
    protected void onPlayStateChanged(int playState) {
        super.onPlayStateChanged(playState);
        switch (playState) {
            //调用release方法会回到此状态
            case VideoView.STATE_IDLE:
                L.e("STATE_IDLE");
                mLockButton.setSelected(false);
                mLoadingProgress.setVisibility(GONE);
                break;
            case VideoView.STATE_PLAYING:
                L.e("STATE_PLAYING");
                mLoadingProgress.setVisibility(GONE);
                imgExit.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_PAUSED:
                L.e("STATE_PAUSED");
                mLoadingProgress.setVisibility(GONE);
                imgExit.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_PREPARING:
                L.e("STATE_PREPARING");
                mLoadingProgress.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_PREPARED:
                L.e("STATE_PREPARED");
                mLoadingProgress.setVisibility(GONE);
                break;
            case VideoView.STATE_ERROR:
                L.e("STATE_ERROR");
                mLoadingProgress.setVisibility(GONE);
                imgExit.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_BUFFERING:
                L.e("STATE_BUFFERING");
                mLoadingProgress.setVisibility(VISIBLE);
                imgExit.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_BUFFERED:
                L.e("STATE_BUFFERED");
                mLoadingProgress.setVisibility(GONE);
                imgExit.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_PLAYBACK_COMPLETED:
                mLoadingProgress.setVisibility(GONE);
                mLockButton.setVisibility(GONE);
                mLockButton.setSelected(false);
                imgExit.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_NO_ANCHOR:
                mLoadingProgress.setVisibility(GONE);
                mLockButton.setVisibility(GONE);
                mLockButton.setSelected(false);
                fmNoAnchor.setVisibility(VISIBLE);
                imgExit.setVisibility(VISIBLE);
                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        if (isLocked()) {
            show();
            Toast.makeText(getContext(), R.string.dkplayer_lock_tip, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (mControlWrapper.isFullScreen()) {
            return stopFullScreen();
        }
        return super.onBackPressed();
    }

    /**
     * 未开播设置头头像
     */
    public void setNoAnchorPhoto(String url) {
        ImageManager.INSTANCE.loadRoundLogo(url, imgAnchorNotHomePhoto);
    }

    public interface BackListener {
        void onBackListener();
    }
}
