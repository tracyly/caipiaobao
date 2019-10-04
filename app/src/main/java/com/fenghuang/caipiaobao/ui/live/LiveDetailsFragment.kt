package com.fenghuang.caipiaobao.ui.live

import com.fenghuang.baselib.base.fragment.BaseFragment
import com.fenghuang.baselib.base.fragment.PlaceholderFragment
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.LogUtils
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.live.chat.LiveChatFragment
import com.fenghuang.caipiaobao.ui.live.data.LiveChatBean
import com.fenghuang.caipiaobao.utils.palyer.PIPManager
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.DefinitionController
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.widget.DefinitionVideoView
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.yanzhenjie.permission.AndPermission
import kotlinx.android.synthetic.main.fragment_live_details.*
import me.jessyan.autosize.internal.CancelAdapt

/**
 *  author : Peter
 *  date   : 2019/8/13 14:53
 *  desc   : 直播详情页 直播界面
 */
class LiveDetailsFragment : BaseMvpFragment<LiveDetailsPresenter>(), CancelAdapt {

    private lateinit var mPIPManager: PIPManager
    private lateinit var mVideoView: DefinitionVideoView

    override fun getLayoutResID() = R.layout.fragment_live_details

    override fun attachPresenter() = LiveDetailsPresenter()

    override fun attachView() = mPresenter.attachView(this)

    override fun isRegisterRxBus() = true

    override fun initContentView() {
        super.initContentView()
        setStatusBarHeight(statusView)
        mPresenter.loadLiveInfo()
        initVideo()
        initPagerContent()
    }

    /**
     * 初始化播放器
     */
    private fun initVideo() {
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
            mPIPManager.mFragment = LiveDetailsFragment()
            controller.setLive()
            mVideoView.setIsLive(true)
            mVideoView.setDefinitionVideos(mPresenter.getViewDeo())
            controller.setTitle("直播标题")
            mVideoView.start()
        }
        controller.setOnBackListener {
            mPIPManager.reset()
            pop()
        }
        playerContainer.addView(mVideoView)
    }

    /**
     * 初始化页面内容
     */
    private fun initPagerContent() {
        val fragments = arrayListOf<BaseFragment>()
        val titles = arrayListOf(getString(R.string.live_tab_chat), getString(R.string.live_tab_reward), getString(R.string.live_tab_anchor))
        titles.forEachIndexed { index, _ ->
            when (index) {
                0 -> fragments.add(LiveChatFragment())
                1 -> fragments.add(PlaceholderFragment.newInstance(R.mipmap.ic_placeholder_empty))
                2 -> fragments.add(PlaceholderFragment.newInstance(R.mipmap.ic_placeholder_empty))
            }
        }

        setTabAdapter(viewPager, tabLayout, fragments, titles)
        ViewUtils.setTabLayoutTextStyle(tabLayout)
        // 设置第一条选中
        ViewUtils.updateTabLayoutView(tabLayout.getTabAt(0), true)
        showContent(placeholder)
    }

    override fun initData() {
        super.initData()
        statusView.bringToFront()
        mVideoView.showDanMu()
    }

    /**
     * 接收消息事件
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onUpdateDanmu(data: LiveChatBean) {
        LogUtils.d("===========接收到了弹幕==========")
        mVideoView.addDanmaku(data.text, data.isMe)
    }

    override fun onBackPressedSupport(): Boolean {
        if (isFullScreen()) {
            setFullScreen(false)
            if (mPIPManager.onBackPress())
                return true
        }
//        mPIPManager.reset()
        AndPermission.with(getPageActivity()).overlay().onGranted {
            mPIPManager.startFloatWindow()
        }.start()
        return super.onBackPressedSupport()
    }


    override fun onDestroy() {
        super.onDestroy()
        mPIPManager.reset()
    }

}