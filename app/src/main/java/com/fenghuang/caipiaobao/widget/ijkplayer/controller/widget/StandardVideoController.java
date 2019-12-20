package com.fenghuang.caipiaobao.widget.ijkplayer.controller.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.fenghuang.baselib.utils.SoftInputUtils;
import com.fenghuang.baselib.utils.SpUtils;
import com.fenghuang.baselib.utils.ToastUtils;
import com.fenghuang.baselib.widget.round.RoundTextView;
import com.fenghuang.caipiaobao.R;
import com.fenghuang.caipiaobao.constant.UserConstant;
import com.fenghuang.caipiaobao.manager.ImageManager;
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatGifBean;
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatGifTitleBean;
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveRedMessageBean;
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveRedReceiveBean;
import com.fenghuang.caipiaobao.ui.home.live.liveroom.HomeLiveDetailsPresenter;
import com.fenghuang.caipiaobao.ui.widget.ChatFullGifTabView;
import com.fenghuang.caipiaobao.ui.widget.popup.OpenRedEnvelopeFullDialog;
import com.fenghuang.caipiaobao.ui.widget.popup.RedEnvelopeFullPopup;
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog;
import com.fenghuang.caipiaobao.utils.UserInfoSp;
import com.fenghuang.caipiaobao.widget.dialog.SoftInputDialog;
import com.fenghuang.caipiaobao.widget.dialog.guide.GiftFullDialog;
import com.fenghuang.caipiaobao.widget.grildscroll.GridViewFullScreenAdapter;
import com.fenghuang.caipiaobao.widget.grildscroll.ViewPagerAdapter;
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.BatteryReceiver;
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.GestureVideoController;
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.MarqueeTextView;
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.MediaPlayerControl;
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.player.VideoView;
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.util.CutoutUtil;
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.util.L;
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.util.PlayerUtils;
import com.fenghuang.caipiaobao.widget.sideslipdeletelayout.ResolveConflictViewPager;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;

/**
 * 直播/点播控制器
 * Created by Devlin_n on 2017/4/7.
 */

public class StandardVideoController<T extends MediaPlayerControl> extends GestureVideoController<T>
        implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, GestureVideoController.GestureListener {
    private final int BOND = 1;
    public SoftInputDialog softInputDialog;
    protected TextView mTotalTime, mCurrTime;
    protected ImageView mFullScreenButton;
    protected LinearLayout mBottomContainer, mTopContainer;
    protected SeekBar mVideoProgress;
    public RoundTextView rtvFullAttention;
    protected ImageView mLockButton;
    protected MarqueeTextView mTitle;
    protected ImageView mRefreshButton;
    protected StatusView mStatusView;
    protected CenterView mCenterView;
    /**
     * 是否需要适配刘海屏
     */
    protected boolean mNeedAdaptCutout;
    protected int mPadding;
    private boolean mIsLive;
    private boolean mIsDragging;
    private ProgressBar mBottomProgress;
    private ImageView mPlayButton;
    private ImageView mStartPlayButton;
    public RoundTextView rtvFullHaveAttention;
    private ImageView mThumb;
    public ImageView imgTopBack;
    private ImageView mStopFullscreen;
    private TextView mSysTime;//系统当前时间
    private ImageView mBatteryLevel;//电量
    private Animation mShowAnim;
    private Animation mHideAnim;
    private BatteryReceiver mBatteryReceiver;
    private EditText chatEditText;
    // 输入框的布局
    private LinearLayout mSendLayout;
    private BackListener mOnBackListener;
    private TextView tvChatTextView;
    private EditText etChatEditText;
    private int mCurrentOrientation = -1;
    public GiftFullDialog materialBottomDialog;
    public SpinKitView giftLoading;
    protected ImageView imgExit;
    private SpinKitView mLoadingProgress;
    private FrameLayout mCompleteContainer, fmNoAnchor;
    private ImageView iv_danmu;
    private RelativeLayout rlAttention;
    private ImageView imgFullPhoto;
    private TextView tvFullName, tvFullTotal;
    /**
     * 横屏红包
     *
     * @param context
     */
    private HomeLiveDetailsPresenter mPresenter;
    private FrameLayout videoRootView;
    private ImageView imgFullEnvelope, ivRedEnvelope;
    private Boolean isShowRed = false;
    private RedEnvelopeFullPopup popRedEnvelope; //横屏发红包
    private RelativeLayout rlAnchorNotHome;
    private ImageView imgAnchorNotHomePhoto;
    private ImageView ivGift;
    private int HOME_LIVE_CHAT_ANCHOR_ID;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == BOND) {
                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(etChatEditText, 0);
                hideAllViews();
            }
        }

    };
    /**
     * 横屏时候显示红包
     */
    private OpenRedEnvelopeFullDialog mOpenRedPopup;

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

    /**
     * 横屏底部礼物
     */
    private ChatFullGifTabView chatGifTabView;


    @Override
    public void setMediaPlayer(T mediaPlayer) {
        super.setMediaPlayer(mediaPlayer);
        mStatusView.attachMediaPlayer(mMediaPlayer);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().unregisterReceiver(mBatteryReceiver);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getContext().registerReceiver(mBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        checkCutout();
    }

    /**
     * 检查是否需要适配刘海
     */
    private void checkCutout() {
        mNeedAdaptCutout = CutoutUtil.allowDisplayToCutout(getContext());
        if (mNeedAdaptCutout) {
            mPadding = (int) PlayerUtils.getStatusBarHeight(getContext());
        }
        L.d("needAdaptCutout: " + mNeedAdaptCutout + " padding: " + mPadding);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adjustView();
    }

    @Override
    public void onOrientationChanged(int orientation) {
        super.onOrientationChanged(orientation);
        adjustView();
    }

    private void adjustView() {
        if (mNeedAdaptCutout) {
            Activity activity = PlayerUtils.scanForActivity(getContext());
            if (activity == null) {
                return;
            }
            int o = activity.getRequestedOrientation();
            if (o == mCurrentOrientation) {
                return;
            }
            L.d("adjustView");
            if (o == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                adjustPortrait();
            } else if (o == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                adjustLandscape();
            } else if (o == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                adjustReserveLandscape();
            }
            mCurrentOrientation = o;
        }
    }

    private ResolveConflictViewPager bottomViewPager;
    private HomeLiveChatGifBean selectHomeGiftListBean = null;

    protected void adjustReserveLandscape() {
        mTopContainer.setPadding(0, 0, mPadding, 0);
        mBottomContainer.setPadding(0, 0, mPadding, 0);
        mBottomProgress.setPadding(0, 0, mPadding, 0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mLockButton.getLayoutParams();
        int dp24 = PlayerUtils.dp2px(getContext(), 24);
        layoutParams.setMargins(dp24, 0, dp24, 0);
        FrameLayout.LayoutParams sflp = (FrameLayout.LayoutParams) mStopFullscreen.getLayoutParams();
        sflp.setMargins(0, 0, 0, 0);
    }

    @Override
    protected void initView() {
        super.initView();
        mFullScreenButton = mControllerView.findViewById(R.id.fullscreen);
        mFullScreenButton.setOnClickListener(this);
        mBottomContainer = mControllerView.findViewById(R.id.bottom_container);
        mTopContainer = mControllerView.findViewById(R.id.top_container);
        mVideoProgress = mControllerView.findViewById(R.id.seekBar);
        mSendLayout = mControllerView.findViewById(R.id.sendLayout);
        mVideoProgress.setOnSeekBarChangeListener(this);
        mTotalTime = mControllerView.findViewById(R.id.total_time);
        mCurrTime = mControllerView.findViewById(R.id.curr_time);
        imgExit = mControllerView.findViewById(R.id.imgExit);
        imgExit.setOnClickListener(this);
        mLockButton = mControllerView.findViewById(R.id.lock);
        mLockButton.setOnClickListener(this);
        mThumb = mControllerView.findViewById(R.id.thumb);
        mThumb.setOnClickListener(this);
        mPlayButton = mControllerView.findViewById(R.id.iv_play);
        mPlayButton.setOnClickListener(this);
        mStartPlayButton = mControllerView.findViewById(R.id.start_play);
        mLoadingProgress = mControllerView.findViewById(R.id.loading);
        mBottomProgress = mControllerView.findViewById(R.id.bottom_progress);
        ImageView rePlayButton = mControllerView.findViewById(R.id.iv_replay);
        rePlayButton.setOnClickListener(this);
        mCompleteContainer = mControllerView.findViewById(R.id.complete_container);
        mCompleteContainer.setOnClickListener(this);
        fmNoAnchor = mControllerView.findViewById(R.id.fmNoAnchor);
        fmNoAnchor.setOnClickListener(this);
        mStopFullscreen = mControllerView.findViewById(R.id.stop_fullscreen);
        mStopFullscreen.setOnClickListener(this);
        mTitle = mControllerView.findViewById(R.id.title);
        mSysTime = mControllerView.findViewById(R.id.sys_time);
        mBatteryLevel = mControllerView.findViewById(R.id.iv_battery);
        mBatteryReceiver = new BatteryReceiver(mBatteryLevel);
        mRefreshButton = mControllerView.findViewById(R.id.iv_refresh);
        mRefreshButton.setOnClickListener(this);
        imgFullEnvelope = mControllerView.findViewById(R.id.imgFullEnvelope);
        videoRootView = mControllerView.findViewById(R.id.videoRootView);
        ivRedEnvelope = mControllerView.findViewById(R.id.ivRedEnvelope);
        ivGift = mControllerView.findViewById(R.id.ivGift);
        iv_danmu = mControllerView.findViewById(R.id.iv_danmu);
        imgTopBack = mControllerView.findViewById(R.id.imgTopBack);
        imgTopBack.setOnClickListener(this);

        //关注
        rlAttention = mControllerView.findViewById(R.id.rlAttention);
        imgFullPhoto = mControllerView.findViewById(R.id.imgFullPhoto);
        tvFullName = mControllerView.findViewById(R.id.tvFullName);
        tvFullTotal = mControllerView.findViewById(R.id.tvFullTotal);
        rtvFullAttention = mControllerView.findViewById(R.id.rtvFullAttention);
        rtvFullHaveAttention = mControllerView.findViewById(R.id.rtvFullHaveAttention);
        rlAnchorNotHome = mControllerView.findViewById(R.id.rlAnchorNotHome);
        //未直播
        imgAnchorNotHomePhoto = mControllerView.findViewById(R.id.imgAnchorNotHomePhoto);

        setGestureListener(this);
        mStatusView = new StatusView(getContext());
        mCenterView = new CenterView(getContext());
        mCenterView.setVisibility(GONE);
        addView(mCenterView);

        mHideAnim = new AlphaAnimation(1f, 0f);
        mHideAnim.setDuration(300);
        mShowAnim = new AlphaAnimation(0f, 1f);
        mShowAnim.setDuration(300);

        mSendLayout.setVisibility(INVISIBLE);

        chatEditText = mControllerView.findViewById(R.id.chatEditText);

        if (UserInfoSp.INSTANCE.getDanMuSwitch()) {
            iv_danmu.setImageResource(R.drawable.ic_player_danmu);
        } else iv_danmu.setImageResource(R.drawable.ic_player_wudanmu);


        //键盘dialog
        tvChatTextView = mControllerView.findViewById(R.id.tvChatTextView);
        tvChatTextView.setOnClickListener(v -> {
            softInputDialog = new SoftInputDialog(getContext(), mPresenter);
            etChatEditText = softInputDialog.findViewById(R.id.etInput);
            etChatEditText.setFocusableInTouchMode(true);
            etChatEditText.requestFocus();
            softInputDialog.show();
            handler.sendEmptyMessageDelayed(BOND, 200);
            hideAllViews();
        });
        ivRedEnvelope.setOnClickListener(v -> {
            if (mPresenter != null) {
//                initPassWordDialog(mPresenter);
                mPresenter.initFullPassWordDialog();
            }
        });
        ivGift.setOnClickListener(v -> initBottomGift());

        iv_danmu.setOnClickListener(v -> {
            if (UserInfoSp.INSTANCE.getDanMuSwitch()) {
                iv_danmu.setImageResource(R.drawable.ic_player_wudanmu);
                UserInfoSp.INSTANCE.putDanMuSwitch(false);
            } else {
                iv_danmu.setImageResource(R.drawable.ic_player_danmu);
                UserInfoSp.INSTANCE.putDanMuSwitch(true);
            }
        });
        rtvFullHaveAttention.setOnClickListener(v -> mPresenter.setAttention(UserInfoSp.INSTANCE.getUserId(), HOME_LIVE_CHAT_ANCHOR_ID, "已取消关注", "取关失败"));
        rtvFullAttention.setOnClickListener(v -> mPresenter.setAttention(UserInfoSp.INSTANCE.getUserId(), HOME_LIVE_CHAT_ANCHOR_ID, "关注成功", "关注失败"));

    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    //竖屏
    protected void adjustPortrait() {
        mTopContainer.setPadding(0, 0, 0, 0);
        mBottomContainer.setPadding(0, 0, 0, 0);
        mBottomProgress.setPadding(0, 0, 0, 0);
        FrameLayout.LayoutParams lblp = (FrameLayout.LayoutParams) mLockButton.getLayoutParams();
        int dp24 = PlayerUtils.dp2px(getContext(), 24);
        lblp.setMargins(dp24, 0, dp24, 0);
        FrameLayout.LayoutParams sflp = (FrameLayout.LayoutParams) mStopFullscreen.getLayoutParams();
        sflp.setMargins(0, 0, 0, 0);

    }

    //横屏
    protected void adjustLandscape() {
        mTopContainer.setPadding(mPadding, PlayerUtils.dp2px(getContext(), 16), 0, 0);
        mBottomContainer.setPadding(mPadding, 0, PlayerUtils.dp2px(getContext(), 20), 0);
        mBottomProgress.setPadding(mPadding, 0, 0, 0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mLockButton.getLayoutParams();
        int dp24 = PlayerUtils.dp2px(getContext(), 24);
        layoutParams.setMargins(dp24 + mPadding, 0, dp24 + mPadding, 0);
        FrameLayout.LayoutParams sflp = (FrameLayout.LayoutParams) mStopFullscreen.getLayoutParams();
        sflp.setMargins(mPadding, 0, 0, 0);
    }

    /**
     * 显示移动网络播放警告
     */
    @Override
    public boolean showNetWarning() {
        //现在是按父类的逻辑显示移动网络播放警告
        if (super.showNetWarning()) {
            mStatusView.showNetWarning(this);
        }
        return super.showNetWarning();
    }

    @Override
    public void hideNetWarning() {
        mStatusView.dismiss();
    }

    protected void doLockUnlock() {
        if (mIsLocked) {
            mIsLocked = false;
            mShowing = false;
            mIsGestureEnabled = true;
            show();
            mLockButton.setSelected(false);
            Toast.makeText(getContext(), R.string.dkplayer_unlocked, Toast.LENGTH_SHORT).show();
        } else {
            hide();
            mIsLocked = true;
            mIsGestureEnabled = false;
            mLockButton.setSelected(true);
            Toast.makeText(getContext(), R.string.dkplayer_locked, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 设置是否为直播视频
     */
    public void setLive() {
        mIsLive = true;
        mBottomProgress.setVisibility(GONE);
        mVideoProgress.setVisibility(INVISIBLE);
        mTotalTime.setVisibility(INVISIBLE);
        mCurrTime.setVisibility(INVISIBLE);
        mRefreshButton.setVisibility(VISIBLE);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mIsDragging = true;
        removeCallbacks(mShowProgress);
        removeCallbacks(mFadeOut);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        long duration = mMediaPlayer.getDuration();
        long newPosition = (duration * seekBar.getProgress()) / mVideoProgress.getMax();
        mMediaPlayer.seekTo((int) newPosition);
        mIsDragging = false;
        post(mShowProgress);
        show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) {
            return;
        }

        long duration = mMediaPlayer.getDuration();
        long newPosition = (duration * progress) / mVideoProgress.getMax();
        if (mCurrTime != null)
            mCurrTime.setText(stringForTime((int) newPosition));
    }

    @Override
    public void hide() {
        if (mShowing) {
            boolean isSoftShowing = SoftInputUtils.INSTANCE.isSoftShowing((Activity) getContext());
            if (mMediaPlayer.isFullScreen()) {
                mLockButton.setVisibility(GONE);
                mLockButton.setAnimation(mHideAnim);
                if (!mIsLocked) {
                    // 如果软有键盘弹起，显示底部控件，隐藏头部控件
                    if (!isSoftShowing) {
                        hideAllViews();
                    }
                }
            } else {
                mBottomContainer.setVisibility(GONE);
                mBottomContainer.startAnimation(mHideAnim);
            }
            if (!mIsLive && !mIsLocked) {
                mBottomProgress.setVisibility(VISIBLE);
                mBottomProgress.startAnimation(mShowAnim);
            }
            mShowing = false;
        }
    }

    private void hideAllViews() {
        mTopContainer.setVisibility(GONE);
        mTopContainer.startAnimation(mHideAnim);
        mBottomContainer.setVisibility(GONE);
        mBottomContainer.startAnimation(mHideAnim);
    }

    private void show(int timeout) {
        if (mSysTime != null)
            mSysTime.setText(getCurrentSystemTime());
        if (!mShowing) {
            if (mMediaPlayer.isFullScreen()) {
                if (mLockButton.getVisibility() != VISIBLE) {
                    mLockButton.setVisibility(VISIBLE);
                    mLockButton.setAnimation(mShowAnim);
                }
                if (!mIsLocked) {
                    showAllViews();
                }
            } else {
                mBottomContainer.setVisibility(VISIBLE);
                mBottomContainer.startAnimation(mShowAnim);
            }
            if (!mIsLocked && !mIsLive) {
                mBottomProgress.setVisibility(GONE);
                mBottomProgress.startAnimation(mHideAnim);
            }
            mShowing = true;
        }
        removeCallbacks(mFadeOut);
        if (timeout != 0) {
            postDelayed(mFadeOut, timeout);
        }
    }

    private void showAllViews() {
        mBottomContainer.setVisibility(VISIBLE);
        mBottomContainer.startAnimation(mShowAnim);
        mTopContainer.setVisibility(VISIBLE);
        mTopContainer.startAnimation(mShowAnim);
    }

    @Override
    public void show() {
        show(mDefaultTimeout);
    }

    @Override
    protected int setProgress() {
        if (mMediaPlayer == null || mIsDragging) {
            return 0;
        }

        if (mIsLive) return 0;

        int position = (int) mMediaPlayer.getCurrentPosition();
        int duration = (int) mMediaPlayer.getDuration();
        if (mVideoProgress != null) {
            if (duration > 0) {
                mVideoProgress.setEnabled(true);
                int pos = (int) (position * 1.0 / duration * mVideoProgress.getMax());
                mVideoProgress.setProgress(pos);
                mBottomProgress.setProgress(pos);
            } else {
                mVideoProgress.setEnabled(false);
            }
            int percent = mMediaPlayer.getBufferedPercentage();
            if (percent >= 95) { //解决缓冲进度不能100%问题
                mVideoProgress.setSecondaryProgress(mVideoProgress.getMax());
                mBottomProgress.setSecondaryProgress(mBottomProgress.getMax());
            } else {
                mVideoProgress.setSecondaryProgress(percent * 10);
                mBottomProgress.setSecondaryProgress(percent * 10);
            }
        }

        if (mTotalTime != null)
            mTotalTime.setText(stringForTime(duration));
        if (mCurrTime != null)
            mCurrTime.setText(stringForTime(position));

        return position;
    }

    @Override
    protected void slideToChangePosition(float deltaX) {
        if (mIsLive) {
            mNeedSeek = false;
        } else {
            super.slideToChangePosition(deltaX);
        }
    }

    public ImageView getThumb() {
        return mThumb;
    }

    @Override
    public boolean onBackPressed() {
        if (mIsLocked) {
            show();
            Toast.makeText(getContext(), R.string.dkplayer_lock_tip, Toast.LENGTH_SHORT).show();
            return true;
        }

        Activity activity = PlayerUtils.scanForActivity(getContext());
        if (activity == null) return super.onBackPressed();
        if (mMediaPlayer.isFullScreen()) {
            mSendLayout.setVisibility(View.INVISIBLE);
            stopFullScreenFromUser();
            return true;
        }
        return super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.fullscreen || i == R.id.stop_fullscreen) {
            doStartStopFullScreen(mSendLayout);
        } else if (i == R.id.lock) {
            doLockUnlock();
        } else if (i == R.id.iv_play || i == R.id.thumb) {
            doPauseResume();
        } else if (i == R.id.iv_replay || i == R.id.iv_refresh) {
            mMediaPlayer.replay(true);
        } else if (i == R.id.imgExit) {
            if (!onBackPressed()) {
                mOnBackListener.onBackListener();
            }
        } else if (i == R.id.fmNoAnchor) {
            if (mShowing) {
                hide();
            } else show();
        } else if (i == R.id.imgTopBack) {
            doStartStopFullScreen(mSendLayout);
        }
    }

    @Override
    public void onBrightnessChange(int percent) {
        mCenterView.setProVisibility(View.VISIBLE);
        mCenterView.setIcon(R.drawable.ic_player_action_brightness);
        mCenterView.setTextView(percent + "%");
        mCenterView.setProPercent(percent);
    }

    @Override
    public void onVolumeChange(int percent) {
        mCenterView.setProVisibility(View.VISIBLE);
        if (percent <= 0) {
            mCenterView.setIcon(R.drawable.ic_player_action_volume_off);
        } else {
            mCenterView.setIcon(R.drawable.ic_player_action_volume_up);
        }
        mCenterView.setTextView(percent + "%");
        mCenterView.setProPercent(percent);
    }

    @Override
    public void onStartSlide() {
        hide();
        mCenterView.setVisibility(VISIBLE);
    }

    @Override
    public void onStopSlide() {
        if (mCenterView.getVisibility() == VISIBLE) {
            mCenterView.setVisibility(GONE);
        }
    }

    @Override
    public void setPlayerState(int playerState) {
        super.setPlayerState(playerState);
        switch (playerState) {
            case VideoView.PLAYER_NORMAL:
                L.e("PLAYER_NORMAL");
                if (mIsLocked) return;
                if (mNeedAdaptCutout) {
                    CutoutUtil.adaptCutoutAboveAndroidP(getContext(), false);
                }
                setLayoutParams(new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                mIsGestureEnabled = false;
                mFullScreenButton.setSelected(false);
                imgExit.setVisibility(VISIBLE);
                mTitle.setVisibility(INVISIBLE);
                mTitle.setNeedFocus(false);
                mSysTime.setVisibility(GONE);
                mBatteryLevel.setVisibility(GONE);
                mTopContainer.setVisibility(VISIBLE);
                mStopFullscreen.setVisibility(GONE);
                tvChatTextView.setVisibility(GONE);
                chatEditText.setVisibility(VISIBLE);
                imgFullEnvelope.setVisibility(GONE);
                iv_danmu.setVisibility(GONE);
                rlAttention.setVisibility(GONE);
                mLockButton.setVisibility(GONE);
                imgTopBack.setVisibility(GONE);
                break;
            case VideoView.PLAYER_FULL_SCREEN:
                L.e("PLAYER_FULL_SCREEN");
                if (mIsLocked) return;
                if (mNeedAdaptCutout) {
                    CutoutUtil.adaptCutoutAboveAndroidP(getContext(), true);
                }
                mIsGestureEnabled = true;
                mFullScreenButton.setSelected(true);
                imgExit.setVisibility(GONE);
//                mTitle.setVisibility(GONE);
//                mTitle.setNeedFocus(true);
                imgTopBack.setVisibility(VISIBLE);
                mSysTime.setVisibility(VISIBLE);
                mBatteryLevel.setVisibility(GONE);
                mStopFullscreen.setVisibility(VISIBLE);
                if (mShowing) {
                    mLockButton.setVisibility(VISIBLE);
                    mTopContainer.setVisibility(VISIBLE);
                } else {
                    mLockButton.setVisibility(GONE);
                }
                tvChatTextView.setVisibility(VISIBLE);
                chatEditText.setVisibility(GONE);
                if (isShowRed) imgFullEnvelope.setVisibility(VISIBLE);
                iv_danmu.setVisibility(VISIBLE);
                rlAttention.setVisibility(VISIBLE);
                break;
        }
    }

    /**
     * 抢到红包后的回调
     */
    public void showOpenRedContent(HomeLiveRedReceiveBean it, HomeLiveDetailsPresenter mPresenter) {
        imgFullEnvelope.setVisibility(GONE);
        isShowRed = false;
        mPresenter.getRoomRed(UserInfoSp.INSTANCE.getUserId());
        mOpenRedPopup.setRedContent(it.getSend_text());
        mOpenRedPopup.setRedMoney(it.getCount() + "");
        mOpenRedPopup.setRedUserName(it.getSend_user_name());
        mOpenRedPopup.setRedLogo(it.getSend_user_avatar());
        mOpenRedPopup.isShowRedLogo(true);
    }

    /**
     * 提示该红包已抢完
     */
    public void showOpenRedOverKnew(HomeLiveRedReceiveBean bean, HomeLiveDetailsPresenter mPresenter) {
        isShowRed = false;
        imgFullEnvelope.setVisibility(GONE);
        mPresenter.getRoomRed(SpUtils.INSTANCE.getInt(UserConstant.USER_ID, 0));
        mOpenRedPopup.showRedOver(bean);
    }

    public interface BackListener {
        void onBackListener();
    }

    @Override
    public void setPlayState(int playState) {
        super.setPlayState(playState);
        switch (playState) {
            //调用release方法会回到此状态
            case VideoView.STATE_IDLE:
                L.e("STATE_IDLE");
                hide();
                mIsLocked = false;
                mLockButton.setSelected(false);
                mBottomProgress.setProgress(0);
                mBottomProgress.setSecondaryProgress(0);
                mVideoProgress.setProgress(0);
                mVideoProgress.setSecondaryProgress(0);
                mCompleteContainer.setVisibility(GONE);
                mBottomProgress.setVisibility(GONE);
                mLoadingProgress.setVisibility(GONE);
                mStatusView.dismiss();
                mStartPlayButton.setVisibility(VISIBLE);
                mThumb.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_PLAYING:
                L.e("STATE_PLAYING");
                //开始刷新进度
                post(mShowProgress);
                mPlayButton.setSelected(true);
                mLoadingProgress.setVisibility(GONE);
                mCompleteContainer.setVisibility(GONE);
                mThumb.setVisibility(GONE);
                mStartPlayButton.setVisibility(GONE);
                break;
            case VideoView.STATE_PAUSED:
                L.e("STATE_PAUSED");
                mPlayButton.setSelected(false);
                mStartPlayButton.setVisibility(GONE);
                //removeCallbacks(mShowProgress);
                break;
            case VideoView.STATE_PREPARING:
                L.e("STATE_PREPARING");
                mCompleteContainer.setVisibility(GONE);
                mStartPlayButton.setVisibility(GONE);
                mStatusView.dismiss();
                mLoadingProgress.setVisibility(VISIBLE);
//                mThumb.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_PREPARED:
                L.e("STATE_PREPARED");
                if (!mIsLive) mBottomProgress.setVisibility(VISIBLE);
//                mLoadingProgress.setVisibility(GONE);
                break;
            case VideoView.STATE_ERROR:
                L.e("STATE_ERROR");
                removeCallbacks(mFadeOut);
                hide();
                mStatusView.showErrorView(this);
                removeCallbacks(mShowProgress);
                mStartPlayButton.setVisibility(GONE);
                mLoadingProgress.setVisibility(GONE);
                mThumb.setVisibility(GONE);
                mBottomProgress.setVisibility(GONE);
                mTopContainer.setVisibility(GONE);
                break;
            case VideoView.STATE_BUFFERING:
                L.e("STATE_BUFFERING");
                mStartPlayButton.setVisibility(GONE);
                mLoadingProgress.setVisibility(VISIBLE);
                mThumb.setVisibility(GONE);
                mPlayButton.setSelected(mMediaPlayer.isPlaying());
                break;
            case VideoView.STATE_BUFFERED:
                L.e("STATE_BUFFERED");
                mLoadingProgress.setVisibility(GONE);
                mStartPlayButton.setVisibility(GONE);
                mThumb.setVisibility(GONE);
                mPlayButton.setSelected(mMediaPlayer.isPlaying());
                break;
            case VideoView.STATE_PLAYBACK_COMPLETED:
                L.e("STATE_PLAYBACK_COMPLETED");
                hide();
                removeCallbacks(mShowProgress);
                mStartPlayButton.setVisibility(GONE);
                mThumb.setVisibility(VISIBLE);
                mCompleteContainer.setVisibility(VISIBLE);
                mStopFullscreen.setVisibility(mMediaPlayer.isFullScreen() ? VISIBLE : GONE);
                mStopFullscreen.setVisibility(VISIBLE);
                mBottomProgress.setVisibility(GONE);
                mBottomProgress.setProgress(0);
                mBottomProgress.setSecondaryProgress(0);
                mLoadingProgress.setVisibility(GONE);
                mIsLocked = false;
                break;
            case VideoView.STATE_NO_ANCHOR:
                mIsLocked = false;
                mLockButton.setSelected(false);
                mBottomProgress.setProgress(0);
                mBottomProgress.setSecondaryProgress(0);
                mVideoProgress.setProgress(0);
                mVideoProgress.setSecondaryProgress(0);
                fmNoAnchor.setVisibility(VISIBLE);
                mBottomProgress.setVisibility(GONE);
                mLoadingProgress.setVisibility(GONE);
                mStatusView.dismiss();
                mStartPlayButton.setVisibility(GONE);
                mThumb.setVisibility(VISIBLE);
                rlAnchorNotHome.setVisibility(VISIBLE);


                break;
        }
    }

    @Override
    public void onPositionChange(int slidePosition, int currentPosition, int duration) {
//        mCenterView.setProVisibility(View.GONE);
//        if (slidePosition > currentPosition) {
//            mCenterView.setIcon(R.drawable.ic_player_action_fast_forward);
//        } else {
//            mCenterView.setIcon(R.drawable.ic_player_action_fast_rewind);
//        }
//        mCenterView.setTextView(stringForTime(slidePosition) + "/" + stringForTime(duration));
    }

    public void showRedEnvelope(HomeLiveDetailsPresenter mPresenter, HomeLiveRedMessageBean eventBean) {
        isShowRed = true;
        if (mMediaPlayer.isFullScreen()) imgFullEnvelope.setVisibility(VISIBLE);
        imgFullEnvelope.setOnClickListener(v -> {
            mOpenRedPopup = new OpenRedEnvelopeFullDialog(getContext());
            mOpenRedPopup.setRedTitle("恭喜发财，大吉大利");
//            mOpenRedPopup.setOnOpenClickListener(v1 -> mPresenter.sendRedReceive(eventBean.getRid(), true));
            mOpenRedPopup.show();
        });
    }

    public void setPresenter(HomeLiveDetailsPresenter mPresenter, int anchorId) {
        this.mPresenter = mPresenter;
        this.HOME_LIVE_CHAT_ANCHOR_ID = anchorId;
    }

    private void initPassWordDialog(HomeLiveDetailsPresenter mPresenter) {
        if (UserInfoSp.INSTANCE.getIsSetPayPassWord()) {
            popRedEnvelope = new RedEnvelopeFullPopup(getContext());
            popRedEnvelope.getEtRedEnvelopeSend().setOnClickListener(v1 -> {
                if (!TextUtils.isEmpty(popRedEnvelope.getEtRedETotal().getText())) {
                    if (!TextUtils.isEmpty(popRedEnvelope.getEtRedRedNumber().getText())) {
                        mPresenter.initFullPassWordDialog();
                    } else ToastUtils.INSTANCE.showNormal("请输入红包个数");
                } else ToastUtils.INSTANCE.showNormal("请输入金额");
            });
            popRedEnvelope.show();
        } else ExceptionDialog.INSTANCE.noSetPassWord(getContext());

    }

    public void initBottomGift() {
        materialBottomDialog = new GiftFullDialog(getContext());
        giftLoading = materialBottomDialog.findViewById(R.id.giftLoading);
        chatGifTabView = materialBottomDialog.findViewById(R.id.chatGifTabView);
        bottomViewPager = materialBottomDialog.findViewById(R.id.viewPager);
        chatGifTabView.setChatGifTab();
        chatGifTabView.setOnSelectListener(integer -> {
            bottomViewPager.setCurrentItem(integer);
            return null;
        });
        materialBottomDialog.show();
        mPresenter.loadGifData();
    }

    public void initBottomGiftData(HomeLiveChatGifTitleBean homeGiftList) {
        GridViewFullScreenAdapter[] arrList = new GridViewFullScreenAdapter[3];
        LayoutInflater mInflater = LayoutInflater.from(getContext());
        ArrayList mPagerList = new ArrayList<View>();
        GridViewFullScreenAdapter gridAdapter = null;
        for (int i = 0; i < 3; i++) {
            GridView gridView = (GridView) mInflater.inflate(R.layout.bottom_vp_gridview_full, bottomViewPager, false);
            if (i == 0)
                gridAdapter = new GridViewFullScreenAdapter(getContext(), homeGiftList.getXk(), 0);
            if (i == 1)
                gridAdapter = new GridViewFullScreenAdapter(getContext(), homeGiftList.getLm(), 1);
            if (i == 2)
                gridAdapter = new GridViewFullScreenAdapter(getContext(), homeGiftList.getZg(), 2);
            gridView.setAdapter(gridAdapter);
            arrList[i] = (gridAdapter);
            mPagerList.add(gridView);
            int finalI = i;
            gridView.setOnItemClickListener((parent, view, position, id) -> {
                ArrayList<HomeLiveChatGifBean> listClick = null;
                if (finalI == 0) listClick = homeGiftList.getXk();
                if (finalI == 1) listClick = homeGiftList.getLm();
                if (finalI == 2) listClick = homeGiftList.getZg();
                for (int j = 0; j < listClick.size(); j++) {
                    HomeLiveChatGifBean model = listClick.get(j);
                    if (listClick.get(j).getName().equals(listClick.get(position).getName())) {
                        if (model.isSelect()) {
                            selectHomeGiftListBean = null;
                            model.setSelect(false);
                        } else {
                            model.setSelect(true);
                            selectHomeGiftListBean = listClick.get(j);
                        }
                    } else {
                        model.setSelect(false);
                    }
                }
                arrList[0].notificationItem(listClick.get(position).getName());
                arrList[1].notificationItem(listClick.get(position).getName());
                arrList[2].notificationItem(listClick.get(position).getName());
            });
        }
        bottomViewPager.setAdapter(new ViewPagerAdapter(mPagerList, getContext()));
        bottomViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                chatGifTabView.setTabSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        materialBottomDialog.findViewById(R.id.btSendGift).setOnClickListener(v -> {
            if (selectHomeGiftListBean != null) {
                mPresenter.sendGift(selectHomeGiftListBean.getId(), 1, selectHomeGiftListBean.getName(), selectHomeGiftListBean.getAmount(), selectHomeGiftListBean.getIcon());
                materialBottomDialog.dismiss();
            } else ToastUtils.INSTANCE.showNormal("请选择礼物");
        });
    }


    /**
     * 设置直播信息
     */
    @SuppressLint("SetTextI18n")
    public void setLiveInfo(String url, String name, String total, Boolean isFollow, int userID, int anchorId) {
        ImageManager.INSTANCE.loadRoundLogo(url, imgFullPhoto);
        tvFullName.setText(name);
        tvFullTotal.setText(total + "人");
        if (isFollow) {
            rtvFullAttention.setVisibility(GONE);
            rtvFullHaveAttention.setVisibility(VISIBLE);
        } else {
            rtvFullAttention.setVisibility(VISIBLE);
            rtvFullHaveAttention.setVisibility(GONE);
        }

    }

    /**
     * 未开播设置头头像
     */
    public void setNoAnchorPhoto(String url) {
        ImageManager.INSTANCE.loadRoundLogo(url, imgAnchorNotHomePhoto);
    }

}
