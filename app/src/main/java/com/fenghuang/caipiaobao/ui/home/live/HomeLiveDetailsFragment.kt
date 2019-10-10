package com.fenghuang.caipiaobao.ui.home.live

import android.os.Bundle
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.constant.IntentConstant
import com.fenghuang.caipiaobao.function.isNotEmpty
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatBean
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveRoomBean
import com.fenghuang.caipiaobao.ui.home.live.chat.HomeLiveChatFragment
import com.fenghuang.caipiaobao.utils.palyer.PIPManager
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.DefinitionController
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.widget.DefinitionVideoView
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import kotlinx.android.synthetic.main.fragment_live_details.*
import me.jessyan.autosize.internal.CancelAdapt
import java.util.*

/**
 *  author : Peter
 *  date   : 2019/8/13 14:53
 *  desc   : 直播详情页 直播界面
 */
class HomeLiveDetailsFragment : BaseMvpFragment<HomeLiveDetailsPresenter>(), CancelAdapt {

    // 悬浮播放
    private lateinit var mPIPManager: PIPManager
    private lateinit var mVideoView: DefinitionVideoView

    override fun getLayoutResID() = R.layout.fragment_live_details

    override fun attachPresenter() = HomeLiveDetailsPresenter()

    override fun attachView() = mPresenter.attachView(this)

    override fun isRegisterRxBus() = true

    override fun initContentView() {
        super.initContentView()
        setStatusBarHeight(statusView)
        mPresenter.loadLiveInfo(arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID) ?: 0, 0)
        initVideo()
        initPagerContent()
    }

    /**
     * 初始化播放器
     */
    fun initVideo() {
        mPIPManager = PIPManager.getInstance()
        mPIPManager.setContext(context!!)
        mVideoView = mPIPManager.mVideoView
        val controller = DefinitionController(getPageActivity())
        mVideoView.setVideoController(controller)
        if (mPIPManager.isStartFloatWindow) {
            mPIPManager.stopFloatWindow()
            controller.setPlayerState(mVideoView.currentPlayerState)
            controller.setPlayState(mVideoView.currentPlayState)
        } else {
            mPIPManager.mFragment = HomeLiveDetailsFragment()
            controller.setLive()
            mVideoView.setIsLive(true)
            controller.setTitle(arguments?.getString(IntentConstant.HOME_LIVE_CHAT_TITLE))

        }
        controller.setOnBackListener {
            mPIPManager.reset()
            pop()
        }
        playerContainer.addView(mVideoView)
        mVideoView.showDanMu()
    }

    /**
     * 开始直播
     */
    fun startLive(mVideos: LinkedHashMap<String, String>) {
        mVideoView.setDefinitionVideos(mVideos)
        mVideoView.start()
    }

    /**
     * 主播信息：Logo 昵称等
     */
    fun setLogoInfo(it: HomeLiveRoomBean) {
        ImageManager.loadRoundLogo(it.avatar, findView(R.id.ivAnchorLogo))
        setText(tvTopUserName, it.nickname)
        setText(tvTopPeople, getString(R.string.live_chat_online, it.online))
    }

    /**
     * 初始化页面内容
     */
    private fun initPagerContent() {
        loadRootFragment(R.id.rlTabLayout, HomeLiveRoomTopFragment.newInstance(arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
                ?: 0))
        loadRootFragment(R.id.rlChatLayout, HomeLiveChatFragment())
    }

    override fun initData() {
        super.initData()
        statusView.bringToFront()
    }

    /**
     * 接收弹幕消息事件
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onUpdateDanmu(data: HomeLiveChatBean) {
        if (isNotEmpty(data.text)) mVideoView.addDanmaku(data.text, data.isMe)

    }

    override fun onBackPressedSupport(): Boolean {
        if (isFullScreen()) {
            setFullScreen(false)
            if (mPIPManager.onBackPress())
                return true
        }
        // 这里是打开悬浮播放的权限开关，如果需要悬浮播放，打开此代码即可
//        AndPermission.with(getPageActivity()).overlay().onGranted {
//            mPIPManager.startFloatWindow()
//        }.start()
        return super.onBackPressedSupport()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPIPManager.reset()
        StatusBarUtils.setStatusBarForegroundColor(activity, true)
    }

    companion object {
        fun newInstance(anchorId: Int, title: String): HomeLiveDetailsFragment {
            val fragment = HomeLiveDetailsFragment()
            val bundle = Bundle()
            bundle.putInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID, anchorId)
            bundle.putString(IntentConstant.HOME_LIVE_CHAT_TITLE, title)
            fragment.arguments = bundle
            return fragment
        }
    }

}