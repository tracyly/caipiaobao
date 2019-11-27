package com.fenghuang.caipiaobao.widget.ijkplayer.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fenghuang.baselib.utils.SoftInputUtils;
import com.fenghuang.baselib.utils.ViewUtils;
import com.fenghuang.caipiaobao.R;
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveRedMessageBean;
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveRedReceiveBean;
import com.fenghuang.caipiaobao.ui.widget.popup.OpenRedEnvelopeDialog;
import com.fenghuang.caipiaobao.ui.widget.popup.RedEnvelopePopup;
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.player.VideoView;
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.util.PlayerUtils;
import com.github.ybq.android.spinkit.SpinKitView;


/**
 * 直播/点播控制器
 * Created by Devlin_n on 2017/4/7.
 */

public class StandardVideoController extends GestureVideoController implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    protected TextView mTotalTime, mCurrTime;
    protected ImageView mFullScreenButton;
    protected LinearLayout mBottomContainer, mTopContainer;
    protected SeekBar mVideoProgress;
    @SuppressLint("InlinedApi")
    protected static final int FULLSCREEN_FLAGS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    protected ImageView mLockButton;
    protected MarqueeTextView mTitle;
    protected ImageView mRefreshButton;
    private boolean mIsLive;
    private boolean mIsDragging;
    private ProgressBar mBottomProgress;
    private final int BOND = 1;
    private ImageView mStartPlayButton;
    public Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
    private ImageView mThumb;
    private FrameLayout mCompleteContainer;
    private ImageView mStopFullscreen;
    private TextView mSysTime;//系统当前时间
    private ImageView mBatteryLevel;//电量
    private Animation mShowAnim = AnimationUtils.loadAnimation(getContext(), R.anim.player_anim_alpha_in);
    private Animation mHideAnim = AnimationUtils.loadAnimation(getContext(), R.anim.player_anim_alpha_out);
    private BatteryReceiver mBatteryReceiver;
    private BackListener mOnBackListener;
    // 输入框的布局
    private LinearLayout mSendLayout;
    private EditText mChatEditText;
    protected ImageView mBackButton, ivRedEnvelope;
    protected View mHideNavBarView;
    int ori = mConfiguration.orientation; //获取屏幕方向
    private ImageView mPlayButton, mIvRecharge, ivEnvelopeTips;

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

    private SpinKitView mLoadingProgress;

    @Override
    protected int getLayoutId() {
        return R.layout.dkplayer_layout_standard_controller;
    }

    private TextView mTvFullTextView;
    private FrameLayout videoRootView;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == BOND) {
                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().unregisterReceiver(mBatteryReceiver);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getContext().registerReceiver(mBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private OpenRedEnvelopeDialog openRedEnvelopePopup;

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    //    private HomeLiveDetailsPresenter presenter;
    private boolean isShowRed = false;
    private HomeLiveRedMessageBean bean;

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.fullscreen) {
            doStartStopFullScreen(mSendLayout);
        } else if (i == R.id.lock) {
            doLockUnlock();
        } else if (i == R.id.iv_play || i == R.id.thumb) {
            doPauseResume();
        } else if (i == R.id.iv_replay || i == R.id.iv_refresh) {
            mMediaPlayer.replay(true);
        } else if (i == R.id.back || i == R.id.stop_fullscreen) {
            if (!onBackPressed()) {
                mOnBackListener.onBackListener();
            }
            ivEnvelopeTips.setVisibility(View.GONE);
        }
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

    private void show(int timeout) {
        if (mSysTime != null)
            mSysTime.setText(getCurrentSystemTime());
        if (!mShowing) {
            if (mMediaPlayer.isFullScreen()) {
                mLockButton.setVisibility(VISIBLE);
                if (!mIsLocked) {
                    showAllViews();
                }
            } else {
                showAllViews();
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

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        super.initView();
        mHideNavBarView = new View(getContext());
        mHideNavBarView.setSystemUiVisibility(FULLSCREEN_FLAGS);
        mFullScreenButton = mControllerView.findViewById(R.id.fullscreen);
        mSendLayout = mControllerView.findViewById(R.id.sendLayout);
        mTvFullTextView = mControllerView.findViewById(R.id.tvFullTextView);
        mFullScreenButton.setOnClickListener(this);
        mBottomContainer = mControllerView.findViewById(R.id.bottom_container);
        mTopContainer = mControllerView.findViewById(R.id.top_container);
        mChatEditText = mControllerView.findViewById(R.id.chatEditText);
        mVideoProgress = mControllerView.findViewById(R.id.seekBar);
        mVideoProgress.setOnSeekBarChangeListener(this);
        mTotalTime = mControllerView.findViewById(R.id.total_time);
        mCurrTime = mControllerView.findViewById(R.id.curr_time);
        mBackButton = mControllerView.findViewById(R.id.back);
        mBackButton.setOnClickListener(this);
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
        mStopFullscreen = mControllerView.findViewById(R.id.stop_fullscreen);
        mStopFullscreen.setOnClickListener(this);
        mTitle = mControllerView.findViewById(R.id.title);
        mSysTime = mControllerView.findViewById(R.id.sys_time);
        mBatteryLevel = mControllerView.findViewById(R.id.iv_battery);
        mBatteryReceiver = new BatteryReceiver(mBatteryLevel);
        mRefreshButton = mControllerView.findViewById(R.id.iv_refresh);
        ivRedEnvelope = mControllerView.findViewById(R.id.ivRedEnvelope);
        videoRootView = mControllerView.findViewById(R.id.videoRootView);
        mIvRecharge = mControllerView.findViewById(R.id.ivRecharge);
        ivEnvelopeTips = mControllerView.findViewById(R.id.ivEnvelopeTips);
        mRefreshButton.setOnClickListener(this);
//        mChatEditText.setOnKeyListener((v, keyCode, event) -> {
//            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
//                String content = mChatEditText.getText().toString().trim();
//                if (TextUtils.isEmpty(content)) {
//                    ToastUtils.INSTANCE.showToast(getContext().getResources().getString(R.string.live_chat_empty));
//                } else {
//                    mChatEditText.setText("");
//                    RxBus.get().post(new HomeLiveChatPostEvenBean(content));
//                    SoftInputUtils.INSTANCE.hideSoftInput(getContext());
//                }
//                return true;
//            }
//            return false;
//        });


//        mTvFullTextView.setOnClickListener(v -> {
//            SoftInputDialog softInputDialog = new SoftInputDialog(getContext());
//            softInputDialog.setOnShowListener(dialog -> {
//                softInputDialog.showBord();
//                mMediaPlayer.showNavBar();
//                handler.sendEmptyMessageDelayed(BOND, 450);
//            });
//            softInputDialog.setOnDismissListener(dialog -> mMediaPlayer.hideNavBar());
//            softInputDialog.show();
//            hideAllViews();
//        });
        ivRedEnvelope.setOnClickListener(v -> {
            RedEnvelopePopup redEnvelopePopup = new RedEnvelopePopup(getContext());
            redEnvelopePopup.setWidth(ViewUtils.INSTANCE.dp2px(280));
            redEnvelopePopup.setHeight(ViewUtils.INSTANCE.dp2px(356));
            redEnvelopePopup.showAtLocation(videoRootView, Gravity.CENTER, 0, 0);
        });
        ivEnvelopeTips.setOnClickListener(v -> {
            openRedEnvelopePopup = new OpenRedEnvelopeDialog(getContext());
            openRedEnvelopePopup.setRedTitle("恭喜发财，大吉大利");
//            openRedEnvelopePopup(ViewUtils.INSTANCE.dp2px(280));
//            openRedEnvelopePopup.setHeight(ViewUtils.INSTANCE.dp2px(320));
//            openRedEnvelopePopup.getIvOpenRedEnvelope().setOnClickListener(v1 -> presenter.sendRedReceive(bean.getRid()));
            openRedEnvelopePopup.show();
        });
    }

    @Override
    public void setPlayerState(int playerState) {
        super.setPlayerState(playerState);
        switch (playerState) {
            case VideoView.PLAYER_NORMAL:
                if (mIsLocked) return;
                setLayoutParams(new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                mIsGestureEnabled = true;
                mFullScreenButton.setSelected(true);
                mLockButton.setVisibility(GONE);
//                mTitle.setVisibility(INVISIBLE);
                mTitle.setNeedFocus(false);
                mSysTime.setVisibility(GONE);
                mBatteryLevel.setVisibility(GONE);
                mTopContainer.setVisibility(VISIBLE);
                mIvRecharge.setVisibility(VISIBLE);
                show();
                mStopFullscreen.setVisibility(GONE);
                mFullScreenButton.setVisibility(VISIBLE);
                break;
            case VideoView.PLAYER_FULL_SCREEN:
                if (mIsLocked) return;
                mIsGestureEnabled = true;
                mIvRecharge.setVisibility(GONE);
                mFullScreenButton.setSelected(true);
//                mTitle.setVisibility(VISIBLE);
                mTitle.setNeedFocus(true);
                mSysTime.setVisibility(VISIBLE);
                mBatteryLevel.setVisibility(VISIBLE);
                mStopFullscreen.setVisibility(GONE);
                mFullScreenButton.setVisibility(GONE);
                if (mShowing) {
                    mLockButton.setVisibility(VISIBLE);
                    mTopContainer.setVisibility(VISIBLE);
                } else {
                    mLockButton.setVisibility(GONE);
                }
                break;
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
    public void setPlayState(int playState) {
        super.setPlayState(playState);
        switch (playState) {
            case VideoView.STATE_IDLE:
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
                mStartPlayButton.setVisibility(VISIBLE);
                mThumb.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_PLAYING:
                post(mShowProgress);
                mPlayButton.setSelected(true);
                mLoadingProgress.setVisibility(GONE);
                mCompleteContainer.setVisibility(GONE);
                mThumb.setVisibility(GONE);
                mStartPlayButton.setVisibility(GONE);
                break;
            case VideoView.STATE_PAUSED:
                mPlayButton.setSelected(false);
                mStartPlayButton.setVisibility(GONE);
                break;
            case VideoView.STATE_PREPARING:
                mCompleteContainer.setVisibility(GONE);
                mStartPlayButton.setVisibility(GONE);
                mLoadingProgress.setVisibility(VISIBLE);
//                mThumb.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_PREPARED:
                if (!mIsLive) mBottomProgress.setVisibility(VISIBLE);
//                mLoadingProgress.setVisibility(GONE);
                mStartPlayButton.setVisibility(GONE);
                break;
            case VideoView.STATE_ERROR:
                mStartPlayButton.setVisibility(GONE);
                mLoadingProgress.setVisibility(GONE);
                mThumb.setVisibility(GONE);
                mBottomProgress.setVisibility(GONE);
                mTopContainer.setVisibility(GONE);
                break;
            case VideoView.STATE_BUFFERING:
                mStartPlayButton.setVisibility(GONE);
                mLoadingProgress.setVisibility(VISIBLE);
                mThumb.setVisibility(GONE);
                mPlayButton.setSelected(mMediaPlayer.isPlaying());
                break;
            case VideoView.STATE_BUFFERED:
                mLoadingProgress.setVisibility(GONE);
                mStartPlayButton.setVisibility(GONE);
                mThumb.setVisibility(GONE);
                mPlayButton.setSelected(mMediaPlayer.isPlaying());
                break;
            case VideoView.STATE_PLAYBACK_COMPLETED:
                hide();
                removeCallbacks(mShowProgress);
                mStartPlayButton.setVisibility(GONE);
                mThumb.setVisibility(GONE);
                mCompleteContainer.setVisibility(GONE);
//                mStopFullscreen.setVisibility(mMediaPlayer.isFullScreen() ? VISIBLE : GONE);
                mBottomProgress.setVisibility(GONE);
                mBottomProgress.setProgress(0);
                mBottomProgress.setSecondaryProgress(0);
                mIsLocked = false;
                mRefreshButton.setVisibility(GONE);
                mPlayButton.setVisibility(GONE);
                mBatteryLevel.setVisibility(GONE);
                break;

        }
    }

    @Override
    public void hide() {
        if (mShowing) {
            boolean isSoftShowing = SoftInputUtils.INSTANCE.isSoftShowing((Activity) getContext());
            if (mMediaPlayer.isFullScreen()) {
                mLockButton.setVisibility(GONE);
                if (!mIsLocked) {
                    // 如果软有键盘弹起，显示底部控件，隐藏头部控件
                    if (!isSoftShowing) {
                        hideAllViews();
                    } else {
                        mTopContainer.setVisibility(GONE);
                        mTopContainer.startAnimation(mHideAnim);
//                        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
//                            //隐藏NavigationBar
//                            this.removeView(mHideNavBarView);
//                        }
                    }
                }
            } else {
                hideAllViews();
            }
            if (!mIsLive && !mIsLocked) {
                mBottomProgress.setVisibility(VISIBLE);
                mBottomProgress.startAnimation(mShowAnim);
            }
            mShowing = isSoftShowing;
        }
    }

    private void hideAllViews() {
        if (mTopContainer.getVisibility() == View.VISIBLE) {
            mTopContainer.setVisibility(GONE);
            mTopContainer.startAnimation(mHideAnim);
        }
        mBottomContainer.setVisibility(GONE);
        mBottomContainer.startAnimation(mHideAnim);

    }

//    public void showRed(HomeLiveRedMessageBean bean, HomeLiveDetailsPresenter presenter) {
//        isShowRed = true;
//        this.presenter = presenter;
//        this.bean = bean;
//    }

    public void showOpenRedContent(HomeLiveRedReceiveBean bean, Integer anchorId) {
        ivEnvelopeTips.setVisibility(GONE);
        isShowRed = false;
//        if (presenter != null) presenter.getRoomRed(UserInfoSp.INSTANCE.getUserId(), anchorId);
        openRedEnvelopePopup.setRedContent(bean.getSend_text());
        openRedEnvelopePopup.setRedMoney(bean.getCount() + "");
        openRedEnvelopePopup.setRedUserName(bean.getSend_user_name());
        openRedEnvelopePopup.setRedLogo(bean.getSend_user_avatar());
        openRedEnvelopePopup.isShowRedLogo(true);
    }

    /**
     * 提示该红包已抢完
     */
    public void showOpenRedOverKnew(HomeLiveRedReceiveBean bean, Integer anchorId) {
        ivEnvelopeTips.setVisibility(GONE);
//        presenter.getRoomRed(SpUtils.INSTANCE.getInt(UserConstant.USER_ID, 0),anchorId);
        openRedEnvelopePopup.showRedOver(bean);
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
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mMediaPlayer.stopFullScreen();
            return true;
        }
        return super.onBackPressed();
    }

    public interface BackListener {
        void onBackListener();
    }


}
