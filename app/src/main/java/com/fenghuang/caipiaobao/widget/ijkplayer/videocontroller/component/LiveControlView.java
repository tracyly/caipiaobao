package com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.component;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.fenghuang.baselib.utils.ToastUtils;
import com.fenghuang.baselib.widget.round.RoundTextView;
import com.fenghuang.caipiaobao.R;
import com.fenghuang.caipiaobao.manager.ImageManager;
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatGifBean;
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatGifTitleBean;
import com.fenghuang.caipiaobao.ui.home.live.liveroom.HomeLiveDetailsPresenter;
import com.fenghuang.caipiaobao.ui.mine.MineRechargeFragment;
import com.fenghuang.caipiaobao.ui.widget.ChatFullGifTabView;
import com.fenghuang.caipiaobao.ui.widget.popup.RedEnvelopeFullPopup;
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog;
import com.fenghuang.caipiaobao.utils.LaunchUtils;
import com.fenghuang.caipiaobao.utils.UserInfoSp;
import com.fenghuang.caipiaobao.widget.dialog.SoftInputDialog;
import com.fenghuang.caipiaobao.widget.dialog.guide.GiftFullDialog;
import com.fenghuang.caipiaobao.widget.gift.AnimUtils;
import com.fenghuang.caipiaobao.widget.grildscroll.GridViewFullScreenAdapter;
import com.fenghuang.caipiaobao.widget.grildscroll.ViewPagerAdapter;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.controller.ControlWrapper;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.controller.IControlComponent;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.player.VideoView;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.util.PlayerUtils;
import com.fenghuang.caipiaobao.widget.sideslipdeletelayout.ResolveConflictViewPager;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;

/**
 * 直播底部控制栏
 */
public class LiveControlView extends FrameLayout implements IControlComponent, View.OnClickListener {

    private final int BOND = 1;
    public SoftInputDialog softInputDialog;
    public RoundTextView rtvFullAttention;
    public RoundTextView rtvFullHaveAttention;
    public GiftFullDialog materialBottomDialog;
    public SpinKitView giftLoading;
    protected ImageView imgExit, ivRecharge;
    public ControlWrapper mControlWrapper;
    private ImageView mFullScreen;
    private LinearLayout mBottomContainer;
    private LinearLayout mTopContainer;
    private ImageView mPlayButton;
    private EditText chatEditText;
    // 输入框的布局
    private LinearLayout mSendLayout;
    private TextView tvChatTextView;
    private EditText etChatEditText;
    private int mCurrentOrientation = -1;
    private RelativeLayout rlAttention;
    private ImageView imgFullPhoto;
    private TextView tvFullName, tvFullTotal;
    private RelativeLayout rlAnchorNotHome;
    private ImageView imgStopFullScreen;
    /**
     * 横屏红包
     *
     * @param context
     */
    private HomeLiveDetailsPresenter mPresenter;
    public Boolean isShowRed = false;
    private ImageView ivRedEnvelope;
    private RedEnvelopeFullPopup popRedEnvelope; //横屏发红包
    private ImageView ivGift, iv_danmu;
    private int HOME_LIVE_CHAT_ANCHOR_ID;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == BOND) {
                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(etChatEditText, 0);
                mControlWrapper.hide();
            }
        }

    };

    /**
     * 横屏底部礼物
     */
    private ChatFullGifTabView chatGifTabView;
    private ResolveConflictViewPager bottomViewPager;
    private HomeLiveChatGifBean selectHomeGiftListBean = null;

    {
        setVisibility(GONE);
        LayoutInflater.from(getContext()).inflate(R.layout.dkplayer_layout_live_control_view, this, true);
        mFullScreen = findViewById(R.id.fullscreen);
        mFullScreen.setOnClickListener(this);
        mBottomContainer = findViewById(R.id.bottom_container);
        mTopContainer = findViewById(R.id.top_container);
        mPlayButton = findViewById(R.id.iv_play);
        mPlayButton.setOnClickListener(this);
        ImageView refresh = findViewById(R.id.iv_refresh);
        refresh.setOnClickListener(this);

        //----------------------------------------------------------------
        mSendLayout = findViewById(R.id.scrollToInput);
        iv_danmu = findViewById(R.id.iv_danmu);

        ivRedEnvelope = findViewById(R.id.ivRedEnvelope);
        ivGift = findViewById(R.id.ivGift);
        ivRecharge = findViewById(R.id.ivRecharge);
        //关注
        rlAttention = findViewById(R.id.rlAttention);
        imgFullPhoto = findViewById(R.id.imgFullPhoto);
        tvFullName = findViewById(R.id.tvFullName);
        tvFullTotal = findViewById(R.id.tvFullTotal);
        rtvFullAttention = findViewById(R.id.rtvFullAttention);
        rtvFullHaveAttention = findViewById(R.id.rtvFullHaveAttention);
        rlAnchorNotHome = findViewById(R.id.rlAnchorNotHome);

        chatEditText = findViewById(R.id.chatEditText);

        imgStopFullScreen = findViewById(R.id.imgStopFullScreen);
        imgStopFullScreen.setOnClickListener(view -> {
            toggleFullScreen();
        });
        //键盘dialog
        tvChatTextView = findViewById(R.id.tvChatTextView);
        tvChatTextView.setOnClickListener(v -> {
            if (UserInfoSp.INSTANCE.getIsLogin()) {
                softInputDialog = new SoftInputDialog(getContext(), mPresenter);
                etChatEditText = softInputDialog.findViewById(R.id.etInput);
                etChatEditText.setFocusableInTouchMode(true);
                etChatEditText.requestFocus();
                softInputDialog.show();
                handler.sendEmptyMessageDelayed(BOND, 200);
                mControlWrapper.hide();
            } else ExceptionDialog.INSTANCE.showExpireDialog(getContext());
        });
        ivRedEnvelope.setOnClickListener(v -> {
            if (mPresenter != null) {
                if (UserInfoSp.INSTANCE.getIsLogin()) {
                    initPassWordDialog(mPresenter);
                } else ExceptionDialog.INSTANCE.showExpireDialog(getContext());
            }
        });
        ivGift.setOnClickListener(v -> {
                    if (UserInfoSp.INSTANCE.getIsLogin()) {
                        initBottomGift();
                    } else ExceptionDialog.INSTANCE.showExpireDialog(getContext());
                }
        );

        //是否首冲
        if (UserInfoSp.INSTANCE.getIsFirstRecharge()) {
            ivRecharge.setImageResource(R.mipmap.ic_live_chat_recharge_first);
            AnimUtils.INSTANCE.getFirstRechargeAnimation(ivRecharge);
        } else ivRecharge.setImageResource(R.mipmap.ic_live_chat_recharge);

        ivRecharge.setOnClickListener(view -> {
            if (UserInfoSp.INSTANCE.getIsLogin()) {
                toggleFullScreen();
                LaunchUtils.INSTANCE.startFragment(getContext(), new MineRechargeFragment());
            } else ExceptionDialog.INSTANCE.showExpireDialog(getContext());
        });

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

    public LiveControlView(@NonNull Context context) {
        super(context);
    }

    public LiveControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LiveControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void attach(@NonNull ControlWrapper controlWrapper) {
        mControlWrapper = controlWrapper;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onVisibilityChanged(boolean isVisible, Animation anim) {
        if (isVisible) {
            if (getVisibility() == GONE) {
                setVisibility(VISIBLE);
                if (anim != null) {
                    startAnimation(anim);
                }
            }
        } else {
            if (getVisibility() == VISIBLE) {
                setVisibility(GONE);
                if (anim != null) {
                    startAnimation(anim);
                }
            }
        }
    }

    @Override
    public void onPlayStateChanged(int playState) {
        switch (playState) {
            case VideoView.STATE_IDLE:
            case VideoView.STATE_START_ABORT:
            case VideoView.STATE_PREPARING:
            case VideoView.STATE_PREPARED:
            case VideoView.STATE_ERROR:
            case VideoView.STATE_PLAYBACK_COMPLETED:
                setVisibility(GONE);
                break;
            case VideoView.STATE_PLAYING:
            case VideoView.STATE_PAUSED:
            case VideoView.STATE_BUFFERING:
            case VideoView.STATE_BUFFERED:
                mPlayButton.setSelected(mControlWrapper.isPlaying());
                break;
        }
    }

    @Override
    public void onPlayerStateChanged(int playerState) {
        switch (playerState) {
            case VideoView.PLAYER_NORMAL:
                mFullScreen.setSelected(false);
                mTopContainer.setVisibility(GONE);
                //-------------------------添加--------------------
                tvChatTextView.setVisibility(GONE);
                chatEditText.setVisibility(VISIBLE);
                rlAttention.setVisibility(GONE);
                imgStopFullScreen.setVisibility(GONE);
                mSendLayout.setVisibility(GONE);
                iv_danmu.setVisibility(GONE);
                break;
            case VideoView.PLAYER_FULL_SCREEN:
                mFullScreen.setSelected(true);
                mTopContainer.setVisibility(VISIBLE);
                //-------------------------添加--------------------
                tvChatTextView.setVisibility(VISIBLE);
                imgStopFullScreen.setVisibility(VISIBLE);
                chatEditText.setVisibility(GONE);
                rlAttention.setVisibility(VISIBLE);
                mSendLayout.setVisibility(VISIBLE);
                iv_danmu.setVisibility(VISIBLE);
                break;
        }


        Activity activity = PlayerUtils.scanForActivity(getContext());
        if (activity != null && mControlWrapper.hasCutout()) {
            int orientation = activity.getRequestedOrientation();
            int cutoutHeight = mControlWrapper.getCutoutHeight();
            if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                mBottomContainer.setPadding(0, 0, 0, 0);
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                mBottomContainer.setPadding(cutoutHeight, 0, PlayerUtils.getNavigationBarHeight(getContext()), 0);
                mTopContainer.setPadding((int) PlayerUtils.getStatusBarHeight(getContext()), 0, PlayerUtils.getNavigationBarHeight(getContext()), 0);
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                mBottomContainer.setPadding(0, 0, cutoutHeight, 0);
            }
        }
    }

    @Override
    public void setProgress(int duration, int position) {

    }

    @Override
    public void onLockStateChanged(boolean isLocked) {
        onVisibilityChanged(!isLocked, null);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fullscreen) {
            toggleFullScreen();
        } else if (id == R.id.iv_play) {
            mControlWrapper.togglePlay();
        } else if (id == R.id.iv_refresh) {
            mControlWrapper.replay(true);
        }
    }

    /**
     * 横竖屏切换
     */
    private void toggleFullScreen() {
        Activity activity = PlayerUtils.scanForActivity(getContext());
        mControlWrapper.toggleFullScreen(activity);
    }

    /**
     * 添加
     */


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
                        popRedEnvelope.dismiss();
                        mPresenter.initFullPassWordDialog(popRedEnvelope, popRedEnvelope.getEtRedETotal().getText().toString(), popRedEnvelope.getEtRedRedNumber().getText().toString(), popRedEnvelope.getEtRedContent().getText().toString());
                    } else ToastUtils.INSTANCE.showNormal("请输入红包个数");
                } else ToastUtils.INSTANCE.showNormal("请输入金额");
            });
            popRedEnvelope.show();
        } else ToastUtils.INSTANCE.showNormal("设置支付密码");

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


}
