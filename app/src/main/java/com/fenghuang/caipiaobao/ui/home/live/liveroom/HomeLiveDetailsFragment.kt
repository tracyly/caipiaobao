package com.fenghuang.caipiaobao.ui.home.live.liveroom

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.base.recycler.decorate.HorizontalItemSpaceDecoration
import com.fenghuang.baselib.base.recycler.multitype.MultiTypeAdapter
import com.fenghuang.baselib.utils.*
import com.fenghuang.baselib.widget.dialog.MaterialBottomDialog
import com.fenghuang.baselib.widget.round.RoundTextView
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.constant.IntentConstant
import com.fenghuang.caipiaobao.constant.IntentConstant.HOME_LIVE_CHAT_STATUE
import com.fenghuang.caipiaobao.constant.UserConstant
import com.fenghuang.caipiaobao.function.isNotEmpty
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.anchor.HomeAnchorFragment
import com.fenghuang.caipiaobao.ui.home.data.*
import com.fenghuang.caipiaobao.ui.home.live.HomeLiveRoomTopAdapter
import com.fenghuang.caipiaobao.ui.home.live.chat.HomeLiveChatHolder
import com.fenghuang.caipiaobao.ui.login.data.LoginSuccess
import com.fenghuang.caipiaobao.ui.mine.MineRechargeFragment
import com.fenghuang.caipiaobao.ui.mine.data.MineIsAnchorLive
import com.fenghuang.caipiaobao.ui.widget.ChatGifTabView
import com.fenghuang.caipiaobao.ui.widget.popup.OpenRedEnvelopeDialog
import com.fenghuang.caipiaobao.ui.widget.popup.ReChargePopup
import com.fenghuang.caipiaobao.ui.widget.popup.RedEnvelopePopup
import com.fenghuang.caipiaobao.utils.FastClickUtils
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog.showExpireDialog
import com.fenghuang.caipiaobao.utils.LaunchUtils
import com.fenghuang.caipiaobao.utils.SvgaUtils
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.dialog.BottomInputDialog
import com.fenghuang.caipiaobao.widget.dialog.TipsConfirmDialog
import com.fenghuang.caipiaobao.widget.gift.AnimUtils
import com.fenghuang.caipiaobao.widget.gift.NumAnim
import com.fenghuang.caipiaobao.widget.gift.RewardLayout
import com.fenghuang.caipiaobao.widget.gift.SendGiftBean
import com.fenghuang.caipiaobao.widget.grildscroll.GridViewAdapter
import com.fenghuang.caipiaobao.widget.grildscroll.ViewPagerAdapter
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.widget.DefinitionVideoView.getValueFromLinkedMap
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.StandardVideoController
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.player.VideoView.STATE_NO_ANCHOR
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.player.VideoViewManager
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.util.Tag
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoview.PIPManager
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoview.view.DanmukuVideoView
import com.fenghuang.caipiaobao.widget.sideslipdeletelayout.ResolveConflictViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.yanzhenjie.permission.AndPermission
import kotlinx.android.synthetic.main.fragment_live_details.*
import me.jessyan.autosize.internal.CancelAdapt
import java.util.*

/**
 *  author : Peter
 *  date   : 2019/8/13 14:53
 *  desc   : 直播详情页 直播界面
 */
class HomeLiveDetailsFragment : BaseMvpFragment<HomeLiveDetailsPresenter>(), CancelAdapt {

    private var liveUrl: String? = null

    var isPressBack = false

    var ifClickShow: Boolean = false

    var isBottom = true
    // 软件盘弹起后所占高度阀值
    private var mKeyHeight = 0
    var mBottomGiftDialog: MaterialBottomDialog? = null
    private lateinit var mBottomGiftDialogViewPager: ResolveConflictViewPager
    var homeGiftList: HomeLiveChatGifTitleBean? = null
    // 礼物窗口的圆点
    private lateinit var mNetWorkReceiver: NetWorkChangReceiver
    var mTotal: Int? = null
    var mRedNumber: Int = 0
    var mRedContent: String = ""
    var mPassword: String = ""
    private var mOpenRedPopup: OpenRedEnvelopeDialog? = null

    var bottomInputDialog: BottomInputDialog? = null

    private var redIsShow: Boolean = false

    lateinit var svgaUtils: SvgaUtils
    // 悬浮播放
    private var mPIPManager: PIPManager? = null


//    lateinit var mController: StandardVideoController<DanmukuVideoView>

//    private lateinit var mDanmukuVideoView: DanmukuVideoView

    lateinit var mVideoView: DanmukuVideoView

    lateinit var mController: StandardVideoController

    lateinit var multiTypeAdapter: MultiTypeAdapter


    override fun getLayoutResID() = R.layout.fragment_live_details

    override fun attachPresenter() = HomeLiveDetailsPresenter(getPageActivity(), arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
            ?: 0)

    override fun attachView() = mPresenter.attachView(this)

    override fun isRegisterRxBus() = true

    override fun initContentView() {
        super.initContentView()
        setStatusBarHeight(statusView)
        getPageActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        mPresenter.loadLiveInfo(arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
                ?: 0, SpUtils.getInt(UserConstant.USER_ID, 0))
        initVideo()

        initChatRoom()
        initRewardAnimator()


        //是否首冲
        if (UserInfoSp.getIsFirstRecharge()) {
            ivRecharges.setImageResource(R.mipmap.ic_live_chat_recharge_first)
            AnimUtils.getFirstRechargeAnimation(ivRecharges)
        } else ivRecharges.setImageResource(R.mipmap.ic_live_chat_recharge)

    }

    override fun initData() {
        super.initData()
        statusView.bringToFront()
        mPresenter.loadData()
        //初始化20条消息
        mPresenter.initRecentlyNews(SpUtils.getInt(UserConstant.USER_ID, 0), arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
                ?: 0)
        //初始化socket
        mNetWorkReceiver = NetWorkChangReceiver()
        val filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        getPageActivity().registerReceiver(mNetWorkReceiver, filter)
    }

    /**
     * 初始化播放器
     */
    private fun initVideo() {
        val playerContainer = findView<FrameLayout>(R.id.player_container)
        mPIPManager = PIPManager.getInstance()
        mVideoView = VideoViewManager.instance().get(Tag.PIP)
        mController = StandardVideoController(getPageActivity())
        mController.setLiveControl(mPresenter, arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
                ?: 0, mVideoView)
        mVideoView.setVideoController(mController)
        mPIPManager?.setContext(getPageActivity())
        val thumb = mController.findViewById<ImageView>(R.id.thumb)

        val imgExit = mController.findViewById<ImageView>(R.id.imgExit)
        imgExit.setOnClickListener {
            if (arguments?.getInt(HOME_LIVE_CHAT_STATUE) != 0) {
                if (UserInfoSp.getIsAboveLive()) {
                    isPressBack = true
                    startFloatWindow()
                } else isOpenAboveLive() //悬浮播放
            } else pop()

        }
        if (mPIPManager?.isStartFloatWindow!!) {
            mPIPManager!!.stopFloatWindow()
        } else {
            mPIPManager?.actClass = HomeLiveDetailsFragment.javaClass
            mPIPManager?.setAnchorInfo(arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
                    ?: 0, arguments?.getString(IntentConstant.HOME_LIVE_CHAT_ANCHOR_PHOTO), arguments?.getInt(HOME_LIVE_CHAT_STATUE)
                    ?: 0)
//            Glide.with(this)
//                    .load("http://sh.people.com.cn/NMediaFile/2016/0112/LOCAL201601121344000138197365721.jpg")
//                    .placeholder(android.R.color.darker_gray)
//                    .into(thumb)
        }
        mController.setOnBackListener {
            pop()
        }
        playerContainer.addView(mVideoView)

    }

    /**
     * 开始直播
     *   mVideoView.setUrl("rtmp://media3.sinovision.net:1935/live/livestream")
     */

    fun startLive(mVideos: LinkedHashMap<String, String>, status: Int, photo: String) {
        if (status == 1) {
            liveUrl = getValueFromLinkedMap(mVideos, 0)
            mVideoView.setUrl(liveUrl)
            mVideoView.start()
        } else {
            mController.setPlayState(STATE_NO_ANCHOR)
            mController.setNoAnchorPhoto(photo)
        }
    }

    /**
     * 主播信息：Logo 昵称等
     */
    fun setLogoInfo(it: HomeLiveRoomBean) {
        ImageManager.loadRoundLogo(it.avatar, findView(R.id.ivAnchorLogo))
        if (it.isFollow) {
            setVisible(tvAnchorAddHaveAttention)
            setGone(tvAnchorAddAttention)
        } else {
            setVisible(tvAnchorAddAttention)
            setGone(tvAnchorAddHaveAttention)
        }
        setText(tvTopUserName, it.nickname)
        setText(tvTopPeople, it.online.toString() + "人")
        tvAnchorAddHaveAttention.setOnClickListener {
            mPresenter.getMoney()
            mPresenter.setAttention(UserInfoSp.getUserId(), arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
                    ?: 0, "已取消关注", "取关失败")
        }
        tvAnchorAddAttention.setOnClickListener {
            mPresenter.getMoney()
            mPresenter.setAttention(UserInfoSp.getUserId(), arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
                    ?: 0, "关注成功", "关注失败")
        }
        setFullScreenInfo(it)
    }

    /**
     * 设置横屏主播信息：Logo 昵称等
     */

    private fun setFullScreenInfo(it: HomeLiveRoomBean) {
        mController.liveControlView.setLiveInfo(it.avatar, it.nickname, it.online.toString(), it.isFollow, UserInfoSp.getUserId(), arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
                ?: 0)
    }


    /**
     * 初始化页面内容
     */
    fun initPagerContent() {
        mPresenter.getRewardGiftList()
        svgaUtils = SvgaUtils(requireActivity(), svgaImage)
        svgaUtils.initAnimator()
    }

    override fun initEvent() {
        super.initEvent()
        mKeyHeight = ViewUtils.getScreenHeight() / 4
        rootViews.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            // 只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
            if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > mKeyHeight)) {

            } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > mKeyHeight)) {
                if (bottomInputDialog != null) bottomInputDialog?.dialogInputKeyBordDown()
            }
        }
        //聊天框
        chatTextView.setOnClickListener {
            if (mPresenter.mWsManager != null && mPresenter.mWsManager?.isWsConnected!!) {
                if (UserInfoSp.getIsLogin()) {
                    mPresenter.getMoney()
                    setGone(scrollToInputs)  //弹起的时候隐藏红包
                    if (ivEnvelopeTip.isVisible) {
                        redIsShow = true
                        setGone(ivEnvelopeTip)
                    } else redIsShow = false
                    bottomInputDialog = BottomInputDialog(getPageActivity(), getPageActivity(), mPresenter)
                    bottomInputDialog?.show()
                    bottomInputDialog?.setOnDismissListener {
                        setVisible(R.id.scrollToInputs)
                        if (redIsShow) setVisible(ivEnvelopeTip)//显示的时候隐藏红包
                    }
                } else showExpireDialog(getPageActivity())
            }
        }
        //底部有新消息
        tvMoreInfo.setOnClickListener {
            scrollToBottom(chatRecyclerView, multiTypeAdapter)
            setGone(tvMoreInfo)
        }


        // 礼物
        ivGifts.setOnClickListener {
            if (UserInfoSp.getIsLogin()) {
                if (mPresenter.mWsManager != null && mPresenter.mWsManager?.isWsConnected!!) {
                    mPresenter.getMoney()
                    initBottomGift()
                    mPresenter.loadGifData()
                }
            } else showExpireDialog(getPageActivity())
        }
        // 检测支付密码是否设置
        ivRedEnvelopes.setOnClickListener {
            if (UserInfoSp.getIsLogin()) {
                mPresenter.getMoney()
                if (mPresenter.mWsManager != null && mPresenter.mWsManager?.isWsConnected!!) {
                    if (UserInfoSp.getIsSetPayPassWord()) {
                        sendRed()
                    } else mPresenter.isSetPassWord()
                }
            } else showExpireDialog(getPageActivity())

        }

        //滑动监听
        chatRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (chatRecyclerView != null && !chatRecyclerView.canScrollVertically(1)) {
                    setGone(R.id.tvMoreInfo)
                }

            }
        })
        //充值
        ivRecharges.setOnClickListener {
            if (UserInfoSp.getIsLogin()) {
                LaunchUtils.startFragment(getPageActivity(), MineRechargeFragment())
            } else showExpireDialog(getPageActivity())
        }
        //头像点击
        ivAnchorLogo.setOnClickListener {
            LaunchUtils.startFragment(getPageActivity(), HomeAnchorFragment.newInstance(arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
                    ?: 0, true))
        }
    }

    /**
     * 底部礼物栏
     */
    /*总的页数*/
    private var pageCount: Int = 3
    /*当前显示的是第几页*/
    private val arr = arrayOfNulls<GridViewAdapter>(3)
    private lateinit var chatGifTabView: ChatGifTabView
    private var selectHomeGiftListBean: HomeLiveChatGifBean? = null
    @SuppressLint("SetTextI18n")
    fun initBottomGift() {
        mBottomGiftDialog = MaterialBottomDialog(getPageActivity())
        mBottomGiftDialog?.setContentView(R.layout.dialog_chat_bottom_gif)
        val root = mBottomGiftDialog?.delegate?.findViewById<View>(R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from(root)
        behavior.isHideable = false
        mBottomGiftDialog?.delegate?.findViewById<View>(R.id.design_bottom_sheet)?.setBackgroundColor(Color.TRANSPARENT)
        chatGifTabView = mBottomGiftDialog?.findViewById(R.id.chatGifTabView)!!
        mBottomGiftDialogViewPager = mBottomGiftDialog?.findViewById(R.id.viewPager)!!
        chatGifTabView.setChatGifTab()
        chatGifTabView.setOnSelectListener {
            mBottomGiftDialogViewPager.currentItem = it
        }
        mBottomGiftDialog?.show()
    }

    /**
     * 礼物数据源
     */
    fun updateGifList() {
        val mInflater = LayoutInflater.from(getPageActivity())
        val mPagerList = ArrayList<View>()
        //总的页数=总数/每页数量，并取整
//        pageCount = ceil(3 * 1.0 / pageSize).toInt()
        lateinit var gridAdapter: GridViewAdapter
        for (j in 0 until pageCount) {
            val gridView = mInflater.inflate(R.layout.bottom_vp_gridview, mBottomGiftDialogViewPager, false) as GridView
            when (j) {
                0 -> gridAdapter = GridViewAdapter(getPageActivity(), homeGiftList?.xk, 0)
                1 -> gridAdapter = GridViewAdapter(getPageActivity(), homeGiftList?.lm, 1)
                2 -> gridAdapter = GridViewAdapter(getPageActivity(), homeGiftList?.zg, 2)
            }
            gridView.adapter = gridAdapter
            arr[j] = gridAdapter
            mPagerList.add(gridView)
            gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                lateinit var listClick: ArrayList<HomeLiveChatGifBean>
                when (j) {
                    0 -> listClick = homeGiftList?.xk!!
                    1 -> listClick = homeGiftList?.lm!!
                    2 -> listClick = homeGiftList?.zg!!
                }
                for (i in listClick.indices) {
                    val model = listClick[i]
                    if (listClick[i].name == listClick[position].name) {
                        if (model.isSelect) {
                            selectHomeGiftListBean = null
                            model.isSelect = (false)
                        } else {
                            model.isSelect = (true)
                            selectHomeGiftListBean = listClick[i]
                        }
                    } else {
                        model.isSelect = (false)
                    }
                }
                arr[0]?.notificationItem(listClick[position].name)
                arr[1]?.notificationItem(listClick[position].name)
                arr[2]?.notificationItem(listClick[position].name)
            }
        }
        mBottomGiftDialogViewPager.adapter = ViewPagerAdapter(mPagerList, getPageActivity())
        mBottomGiftDialogViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                chatGifTabView.setTabSelect(position)
            }
        })
        mBottomGiftDialog?.findViewById<RoundTextView>(R.id.btSendGift)?.setOnClickListener {
            if (selectHomeGiftListBean != null) {
                mPresenter.sendGift(selectHomeGiftListBean?.id!!, 1, selectHomeGiftListBean?.name.toString(), selectHomeGiftListBean?.amount!!, selectHomeGiftListBean?.icon.toString())
                mBottomGiftDialog?.dismiss()
            } else ToastUtils.showNormal("请选择礼物")
        }
    }

    /**
     * 发红包
     */
    var popRedEnvelope: RedEnvelopePopup? = null

    fun sendRed() {
        popRedEnvelope = RedEnvelopePopup(getPageActivity())
        popRedEnvelope!!.animationStyle = R.style.dialogAnim
//        popRedEnvelope?.width = ViewUtils.dp2px(280)
//        popRedEnvelope?.height = ViewUtils.dp2px(330)
        popRedEnvelope?.showAtLocation(rootViews, Gravity.CENTER, 0, 0)
        popRedEnvelope?.setOnSendClickListener { total, redNumber, redContent ->
            when {
                TextUtils.isEmpty(total) -> ToastUtils.showNormal("请输入金额")
                TextUtils.isEmpty(redNumber) -> ToastUtils.showNormal("请输入红包个数")
                else -> {
                    mTotal = total.toInt()
                    mRedNumber = redNumber.toInt()
                    mRedContent = redContent
                    mPresenter.initPassWordDialog()
                }
            }
        }
    }


    /**
     * 发送红包
     */
    fun sendRedEnvelope() {
        mPresenter.sendRedEnvelope(arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
                ?: 0, SpUtils.getInt(UserConstant.USER_ID, 0), mTotal!!, mRedNumber, mRedContent, mPassword)
    }

    /**
     * 显示余额不足
     */
    fun showReChargePopup() {
        val reChargePopup = ReChargePopup(getPageActivity())
        reChargePopup.setOnSendClickListener {
            LaunchUtils.startFragment(getPageActivity(), MineRechargeFragment())
        }
        reChargePopup.show()
    }

    /**
     * 初始化打赏榜
     */
    fun initRewardList(data: List<HomeLiveRoomRewardBean>) {
        val adapter = this.context?.let { HomeLiveRoomTopAdapter(it) }
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvTabLayout?.layoutManager = layoutManager
        rvTabLayout?.adapter = adapter
        rvTabLayout?.addItemDecoration(HorizontalItemSpaceDecoration(ViewUtils.dp2px(10)))
        adapter?.addAll(data)
    }

    /**
     * 初始化聊天室
     */
    private fun initChatRoom() {
        multiTypeAdapter = MultiTypeAdapter(getPageActivity())
        multiTypeAdapter.register(HomeLiveChatBeanNew::class.java, HomeLiveChatHolder())
        //初始化聊天RecycleView
        chatRecyclerView?.adapter = multiTypeAdapter
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        chatRecyclerView?.layoutManager = layoutManager

        // 获取列表的滑动事件，控制一键到底部
        chatRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    isBottom = isStayBottom(chatRecyclerView!!, multiTypeAdapter)
                }
            }
        })
    }

    //Socket
    inner class NetWorkChangReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (NetWorkUtils.isNetworkConnected()) {
                mPresenter.startWebSocketConnect()
            } else {
                LogUtils.d("WsManager----------网络不可用")
                mPresenter.stopConnect()
            }
        }
    }


    /**
     * 接收弹幕消息事件
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onUpdateDanmu(data: HomeLiveChatBeanNew) {
        if (mVideoView.isFullScreen && UserInfoSp.getDanMuSwitch()) {
            if (isNotEmpty(data.text)) mVideoView.addDanmaku(data.text, data.isMe)
            if (isNotEmpty(data.type) && data.type == "gift") {
                val text = data.userName + " 送给主播" + data.gift_num + " 个 " + data.gift_name
                mVideoView.addDanmaku(text, data.isMe)
            }
        }
    }


    /**
     * 显示有下角小红包
     */
    fun onIsShowRedEnvelope(eventBean: HomeLiveRedMessageBean) {
        if (eventBean.gift_type == 4) {
            try {
                redIsShow = true
                // 通知有可抢的红包
                setVisible(ivEnvelopeTip)
                ivEnvelopeTip.setOnClickListener {
                    if (FastClickUtils.isFastClick()) {
                        showRed(eventBean)
                    }
                }
                mController.showRedEnvelopeFirsr(mPresenter, eventBean)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 显示有下角小红包并且直接弹出红包
     */
    fun onIsShowRedEnvelopeOnSocket(eventBean: HomeLiveRedMessageBean) {
        if (eventBean.gift_type == 4) {
            try {
                redIsShow = true
                // 通知有可抢的红包
                if (!ivEnvelopeTip.isVisible) {
                    setVisible(ivEnvelopeTip)
                    ivEnvelopeTip.setOnClickListener {
                        if (FastClickUtils.isFastClick()) {
                            showRed(eventBean)
                        }
                    }
                }
                if (!mVideoView.isFullScreen && this.isVisible) showRed(eventBean)
                mController.showRedEnvelope(mPresenter, eventBean)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 显示红包
     */
    private fun showRed(eventBean: HomeLiveRedMessageBean) {
        if (mOpenRedPopup == null) {
            mOpenRedPopup = OpenRedEnvelopeDialog(context!!)
            mOpenRedPopup?.setRedTitle("恭喜发财，大吉大利")
            mOpenRedPopup?.setOnOpenClickListener {
                if (FastClickUtils.isFastClick()) {
                    mPresenter.sendRedReceive(eventBean.rid, false, mOpenRedPopup, null)
                }
            }
            mOpenRedPopup?.setOnDismissListener {
                mPresenter.getRoomRed(UserInfoSp.getUserId())
            }
            mOpenRedPopup?.show()
        } else if (!mOpenRedPopup?.isShowing!!) {
            mOpenRedPopup = OpenRedEnvelopeDialog(context!!)
            mOpenRedPopup?.setRedTitle("恭喜发财，大吉大利")
            mOpenRedPopup?.setOnOpenClickListener {
                if (FastClickUtils.isFastClick()) {
                    mPresenter.sendRedReceive(eventBean.rid, false, mOpenRedPopup, null)
                }
            }
            mOpenRedPopup?.setOnDismissListener {
                ifClickShow = false
                mPresenter.getRoomRed(UserInfoSp.getUserId())

            }
            mOpenRedPopup?.show()
        }
    }

    /**
     * 抢到红包后的回调
     */
    fun showOpenRedContent(it: HomeLiveRedReceiveBean) {
        setGone(ivEnvelopeTip)
        mController.isShowRed = false
        mOpenRedPopup?.setRedContent(it.send_text)
        mOpenRedPopup?.setRedMoney(it.amount)
        mOpenRedPopup?.setRedUserName(it.send_user_name)
        mOpenRedPopup?.setRedLogo(it.send_user_avatar)
        mOpenRedPopup?.isShowRedLogo(true)
    }

    /**
     * 提示该红包已抢完
     */
    fun showOpenRedOverKnew(bean: HomeLiveRedReceiveBean) {
        setGone(ivEnvelopeTip)
        mPresenter.getRoomRed(SpUtils.getInt(UserConstant.USER_ID, 0))
        mOpenRedPopup?.showRedOver(bean)
    }

    /**
     * 接收动画
     * 小礼物 炫酷：齐天大圣      浪漫： love  求带走   尊贵：至尊宝。臭鸡蛋
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onGetGiftSmall(data: HomeLiveSmallAnimatorBean) {
        if (data.gift_id == 18 || data.gift_id == 24 || data.gift_id == 30 || data.gift_id == 31 || data.gift_id == 22 || data.gift_id == 21) {
            mPresenter.initGiftAnimator(data)
        }
        if (data.gift_id == 15 || data.gift_id == 16 || data.gift_id == 17 || data.gift_id == 27 || data.gift_id == 28 || data.gift_id == 29 || data.gift_id == 23) {
            mPresenter.loadSvga(data)
        }
    }


    @SuppressLint("SetTextI18n")
    fun initRewardAnimator() {

        rewardLayout.setGiftAdapter(object : RewardLayout.GiftAdapter<SendGiftBean> {
            override fun onInit(view: View?, bean: SendGiftBean?): View {
                // 初始化数据
                val giftNum = view?.findViewById<TextView>(R.id.tv_gift_amount)
                val giftImage = view?.findViewById<ImageView>(R.id.iv_gift_img)
                val userName = view?.findViewById<TextView>(R.id.tv_user_name)
                val giftName = view?.findViewById<TextView>(R.id.tv_gift_name)
                val userPhoto = view?.findViewById<ImageView>(R.id.riv_gift_my_avatar)
                giftNum?.text = "x " + bean?.theSendGiftSize
                bean?.theGiftCount = bean?.theSendGiftSize!!
                ImageManager.loadImage(bean.giftImg, giftImage!!)
                ImageManager.loadRoundLogo(bean.userPhoto, userPhoto!!)
                userName?.text = bean.userName
                giftName?.text = "送出 " + bean.giftName
                return view
            }

            override fun onUpdate(view: View?, o: SendGiftBean?, t: SendGiftBean?): View {
                val showNum = o?.theGiftCount as Int + o.theSendGiftSize
                val giftNum = view?.findViewById<TextView>(R.id.tv_gift_amount)
                val giftImage = view?.findViewById<ImageView>(R.id.iv_gift_img)
                /* 刷新已存在的gifted界面数据 */
                giftNum?.text = "x$showNum"
                ImageManager.loadImage(o.giftImg, giftImage!!)
                // 数字刷新动画
                NumAnim().start(giftNum!!)
                // 更新累计礼物数量
                o.theGiftCount = showNum
                // 更新其它自定义字段
//              o.setUserName(t.getUserName());
                return view
            }

            override fun onKickEnd(bean: SendGiftBean?) {
            }

            override fun onComboEnd(bean: SendGiftBean?) {
            }

            override fun addAnim(view: View?) {
                val textView = view?.findViewById<View>(R.id.tv_gift_amount) as TextView
                val img = view.findViewById<View>(R.id.iv_gift_img) as ImageView
                // 整个giftview动画
                val giftInAnim = AnimUtils.getInAnimation(getPageActivity())
                // 礼物图像动画
                val imgInAnim = AnimUtils.getInAnimation(getPageActivity())
                // 首次连击动画
                val comboAnim = NumAnim()
                imgInAnim.startTime = 500
                imgInAnim.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {
                        textView.visibility = View.GONE
                    }

                    override fun onAnimationEnd(animation: Animation) {
                        textView.visibility = View.VISIBLE
                        comboAnim.start(textView)
                    }

                    override fun onAnimationRepeat(animation: Animation) {
                    }
                })
                view.startAnimation(giftInAnim)
                img.startAnimation(imgInAnim)
            }

            override fun outAnim(): AnimationSet {
                return AnimUtils.getOutAnimation(getPageActivity())
            }

            override fun checkUnique(o: SendGiftBean?, t: SendGiftBean?): Boolean {
                return o?.theGiftId === t?.theGiftId && o?.theUserId === t?.theUserId
            }

            override fun generateBean(bean: SendGiftBean?): SendGiftBean? {
                try {
                    return bean?.clone() as SendGiftBean
                } catch (e: CloneNotSupportedException) {
                    e.printStackTrace()
                }
                return null
            }

        })
    }

    companion object {
        fun newInstance(anchorId: Int, title: String, status: Int, anchorPhoto: String, openInfo: String): HomeLiveDetailsFragment {
            val fragment = HomeLiveDetailsFragment()
            val bundle = Bundle()
            bundle.putInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID, anchorId)
            bundle.putString(IntentConstant.HOME_LIVE_CHAT_TITLE, title)
            bundle.putInt(HOME_LIVE_CHAT_STATUE, status)
            bundle.putString(IntentConstant.HOME_LIVE_CHAT_ANCHOR_PHOTO, anchorPhoto)
            bundle.putString("openInfo", openInfo)
            fragment.arguments = bundle
            return fragment
        }
    }


    /**
     * 是否停留在列表底部
     */
    private fun isStayBottom(recyclerView: RecyclerView, multiTypeAdapter: MultiTypeAdapter): Boolean {
        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager
        return layoutManager?.findLastVisibleItemPosition() == multiTypeAdapter.itemCount - 1
    }

    /**
     * 滑动到最底部
     */
    fun scrollToBottom(recyclerView: RecyclerView, multiTypeAdapter: MultiTypeAdapter) {
        if (!isStayBottom(recyclerView, multiTypeAdapter)) {
            recyclerView.smoothScrollToPosition((recyclerView.layoutManager as? LinearLayoutManager)?.itemCount
                    ?: 0)
        }
    }

    /**
     * 悬浮播放
     */
    private fun startFloatWindow() {
        // 这里是打开悬浮播放的权限开关，如果需要悬浮播放，打开此代码即可
        AndPermission.with(getPageActivity()).overlay().onGranted {
            mPIPManager?.startFloatWindow()
            UserInfoSp.isAboveLive(true)
            pop()
        }.onDenied { pop() }.start()
    }


    override fun onBackPressedSupport(): Boolean {
        if (isFullScreen()) {
            if (mController.liveControlView.softInputDialog != null) {
                mController.liveControlView.softInputDialog.dismiss()
            }
            setFullScreen(false)
            if (mPIPManager != null && mPIPManager?.onBackPress()!!)
                return true
            return true
        }
        SoftInputUtils.hideSoftInput(getPageActivity())
        return super.onBackPressedSupport()
    }


    override fun onStop() {
        super.onStop()
        if (!isPressBack) mVideoView.pause()
    }

    override fun onResume() {
        super.onResume()
        mPIPManager?.resume()
//        if (mPresenter.mWsManager == null) {
//            mPresenter.startWebSocketConnect()
//        } else if (mPresenter.mWsManager.isWsConnected)mPresenter.mWsManager!!.tryReconnect()
    }


    override fun onDestroy() {
        super.onDestroy()
        mPIPManager?.reset()
        StatusBarUtils.setStatusBarForegroundColor(activity, true)
        mPresenter.stopConnect()
        getPageActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    //提示是否打开悬浮
    private fun isOpenAboveLive() {
        if ((arguments?.getInt(HOME_LIVE_CHAT_STATUE) ?: 0) == 1) {
            val dialog = TipsConfirmDialog(getPageActivity(), "是否打开小窗播放", "是", "否", "")
            dialog.setConfirmClickListener {
                isPressBack = true
                startFloatWindow()
            }
            dialog.setCanCelClickListerner {
                isPressBack = false
                pop()
            }

            dialog.show()
        }
    }


    /**
     * 更新直播预告
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onReciveID(eventBean: MineIsAnchorLive) {
        if (eventBean.isLive == "trueTrue") {
            setVisible(tvAnchorAddAttention)
            setGone(tvAnchorAddHaveAttention)
        }
        if (eventBean.isLive == "falseFalse") {
            setGone(tvAnchorAddAttention)
            setVisible(tvAnchorAddHaveAttention)
        }
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onEditUserInfo(eventBean: LoginSuccess) {
        setGone(ivEnvelopeTip)
        mPresenter.getRoomRed(UserInfoSp.getUserId())
        mPresenter.loadLiveInfoTwice(arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
                ?: 0, UserInfoSp.getUserId())
    }

}