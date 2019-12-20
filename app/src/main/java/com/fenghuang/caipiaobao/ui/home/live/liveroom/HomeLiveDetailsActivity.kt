//package com.fenghuang.caipiaobao.ui.home.live.liveroom
//
//import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog.showExpireDialog
//import android.annotation.SuppressLint
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import android.graphics.Color
//import android.net.ConnectivityManager
//import android.os.Build
//import android.os.Bundle
//import android.text.TextUtils
//import android.view.Gravity
//import android.view.KeyEvent
//import android.view.LayoutInflater
//import android.view.View
//import android.view.animation.Animation
//import android.view.animation.AnimationSet
//import android.widget.AdapterView
//import android.widget.GridView
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.annotation.RequiresApi
//import androidx.core.view.isVisible
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import androidx.viewpager.widget.ViewPager
//import com.fenghuang.baselib.base.mvp.BaseMvpActivity
//import com.fenghuang.baselib.base.recycler.decorate.HorizontalItemSpaceDecoration
//import com.fenghuang.baselib.base.recycler.multitype.MultiTypeAdapter
//import com.fenghuang.baselib.theme.AppTheme
//import com.fenghuang.baselib.utils.*
//import com.fenghuang.baselib.widget.dialog.MaterialBottomDialog
//import com.fenghuang.baselib.widget.round.RoundTextView
//import com.fenghuang.caipiaobao.R
//import com.fenghuang.caipiaobao.constant.IntentConstant
//import com.fenghuang.caipiaobao.constant.UserConstant
//import com.fenghuang.caipiaobao.function.isEmpty
//import com.fenghuang.caipiaobao.function.isNotEmpty
//import com.fenghuang.caipiaobao.manager.ImageManager
//import com.fenghuang.caipiaobao.ui.home.data.*
//import com.fenghuang.caipiaobao.ui.home.live.HomeLiveRoomTopAdapter
//import com.fenghuang.caipiaobao.ui.home.live.chat.HomeLiveChatHolder
//import com.fenghuang.caipiaobao.ui.widget.ChatGifTabView
//import com.fenghuang.caipiaobao.ui.widget.popup.OpenRedEnvelopeDialog
//import com.fenghuang.caipiaobao.ui.widget.popup.ReChargePopup
//import com.fenghuang.caipiaobao.ui.widget.popup.RedEnvelopePopup
//import com.fenghuang.caipiaobao.utils.SvgaUtils
//import com.fenghuang.caipiaobao.utils.UserInfoSp
//import com.fenghuang.caipiaobao.widget.gift.AnimUtils
//import com.fenghuang.caipiaobao.widget.gift.NumAnim
//import com.fenghuang.caipiaobao.widget.gift.RewardLayout
//import com.fenghuang.caipiaobao.widget.gift.SendGiftBean
//import com.fenghuang.caipiaobao.widget.grildscroll.GridViewAdapter
//import com.fenghuang.caipiaobao.widget.grildscroll.ViewPagerAdapter
//import com.fenghuang.caipiaobao.widget.ijkplayer.controller.player.DanmukuVideoView
//import com.fenghuang.caipiaobao.widget.ijkplayer.controller.player.widget.PIPManager
//import com.fenghuang.caipiaobao.widget.ijkplayer.controller.widget.StandardVideoController
//import com.fenghuang.caipiaobao.widget.sideslipdeletelayout.ResolveConflictViewPager
//import com.google.android.material.bottomsheet.BottomSheetBehavior
//import com.hwangjr.rxbus.annotation.Subscribe
//import com.hwangjr.rxbus.thread.EventThread
//import kotlinx.android.synthetic.main.activity_live_details.*
//import kotlinx.android.synthetic.main.activity_live_details.chatEditTexts
//import kotlinx.android.synthetic.main.activity_live_details.chatRecyclerView
//import kotlinx.android.synthetic.main.activity_live_details.ivEnvelopeTip
//import kotlinx.android.synthetic.main.activity_live_details.ivGifts
//import kotlinx.android.synthetic.main.activity_live_details.ivRecharges
//import kotlinx.android.synthetic.main.activity_live_details.ivRedEnvelopes
//import kotlinx.android.synthetic.main.activity_live_details.rewardLayout
//import kotlinx.android.synthetic.main.activity_live_details.rvTabLayout
//import kotlinx.android.synthetic.main.activity_live_details.svgaImage
//import kotlinx.android.synthetic.main.activity_live_details.tvMoreInfo
//import kotlinx.android.synthetic.main.activity_live_details.tvSendMessage
//import kotlinx.android.synthetic.main.activity_live_details.tvTopPeople
//import kotlinx.android.synthetic.main.activity_live_details.tvTopUserName
//import kotlinx.android.synthetic.main.dkplayer_layout_standard_controller.view.*
//import kotlinx.android.synthetic.main.fragment_live_details.*
//import java.util.*
//
///**
// *
// * @ Author  QinTian
// * @ Date  2019/11/29- 17:32
// * @ Describe
// *
// */
//
//class HomeLiveDetailsActivity : BaseMvpActivity<HomeLiveDetailsActivityPresenter>() {
//
//    var isBottom = true
//
//    var mBottomGiftDialog: MaterialBottomDialog? = null
//
//    private lateinit var mBottomGiftDialogViewPager: ResolveConflictViewPager
//
//    var homeGiftList: HomeLiveChatGifTitleBean? = null
//
//    // 礼物窗口的圆点
//    private lateinit var mNetWorkReceiver: NetWorkChangReceiver
//
//    var mTotal: Int? = null
//
//    var mRedNumber: Int = 0
//
//    var mRedContent: String = ""
//
//    var mPassword: String = ""
//
//    private lateinit var mOpenRedPopup: OpenRedEnvelopeDialog
//
//    lateinit var svgaUtils: SvgaUtils
//    // 悬浮播放
//    private var mPIPManager: PIPManager? = null
//
//    lateinit var mController: StandardVideoController<DanmukuVideoView>
//
//    private lateinit var mVideoView: DanmukuVideoView
//
//    lateinit var multiTypeAdapter: MultiTypeAdapter
//
//    override val layoutResID = R.layout.activity_live_details
//
//    override fun isOverride() = true
//
//    override fun attachView() = mPresenter.attachView(this)
//
//    override fun attachPresenter() = HomeLiveDetailsActivityPresenter(this, intent.getIntExtra("anchorId", 0))
//
//    override fun isStatusBarTranslate() = false
//
//    override fun isStatusBarForegroundBlack() = false
//
//    override fun isSetAppTheme() = true
//
//    override fun getAppTheme(): AppTheme = AppTheme.Black
//
//    override fun initContentView() {
//        initVideo()
//        initKeyBord()
//        initRewardAnimator()
//        multiTypeAdapter = MultiTypeAdapter(this@HomeLiveDetailsActivity)
//        mPresenter.loadData()
//
//        //是否首冲
//        if (UserInfoSp.getIsFirstRecharge()) {
//            ivRecharges.setImageResource(R.mipmap.ic_live_chat_recharge_first)
//            AnimUtils.getFirstRechargeAnimation(ivRecharges)
//        } else ivRecharges.setImageResource(R.mipmap.ic_live_chat_recharge)
//        initEmotionMainFragment()
//    }
//
//
//    private fun initEmotionMainFragment() {
//    }
//
//    override fun initData() {
//        super.initData()
//        //初始化20条消息
//        mPresenter.initRecentlyNews(SpUtils.getInt(UserConstant.USER_ID, 0), intent.getIntExtra(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID, 0))
//        //初始化socket
//        mNetWorkReceiver = NetWorkChangReceiver()
//        val filter = IntentFilter()
//        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
//        registerReceiver(mNetWorkReceiver, filter)
//    }
//
//    /**
//     * 初始化播放器
//     */
//    private fun initVideo() {
//        mVideoView = findView(R.id.mVideoView)
//        mController = StandardVideoController(this)
//        mController.setLive()
////        mController.setPresenter(mPresenter)
//        mVideoView.setVideoController(mController)
//        mVideoView.setUrl("rtmp://media3.sinovision.net:1935/live/livestream")
//        mVideoView.start()
//        mController.setOnBackListener {
//            //            mPIPManager?.reset()
//            finish()
//        }
////        mPIPManager = PIPManager.getInstance()
////        mController.setTitle(arguments?.getString(IntentConstant.HOME_LIVE_CHAT_TITLE))
////        if (mPIPManager?.isStartFloatWindow!!) {
////            mPIPManager?.stopFloatWindow()
////            mController.setPlayerState(mVideoView.currentPlayerState)
////            mController.setPlayState(mVideoView.currentPlayState)
////        } else {
////            mPIPManager?.actClass =  HomeLiveDetailsFragment.javaClass
////            mController.setLive()
////            mController.setTitle(arguments?.getString(IntentConstant.HOME_LIVE_CHAT_TITLE))
////        }
//
//////        playerContainer.removeAllViews()
//////        playerContainer.addView(mVideoView)
////        mVideoView!!.showDanMu()
//
//    }
//
//    /**
//     * 初始化底部聊天框
//     */
//    private fun initKeyBord() {
////        SoftHideKeyBoardUtil().init(this)   废除
//
//    }
//
//    /**
//     * 开始直播
//     */
//    fun startLive(mVideos: LinkedHashMap<String, String>, status: Int) {
////        if (status == 1) {
////            LogUtils.e("------>"+getValueFromLinkedMap(mVideos, 0))
//////            mVideoView?.setUrl(getValueFromLinkedMap(mVideos, 0))
////            mVideoView?.setUrl("rtmp://media3.sinovision.net:1935/live/livestream")
////            mVideoView?.start()
////        } else {
////            mVideoView?.noPlayer()
////        }
//    }
//
//    /**
//     * 主播信息：Logo 昵称等
//     */
//    fun setLogoInfo(it: HomeLiveRoomBean) {
//        ImageManager.loadRoundLogo(it.avatar, findView(R.id.ivAnchorLogo))
//        setText(tvTopUserName, it.nickname)
//        setText(tvTopPeople, getString(R.string.live_chat_online, it.online))
//    }
//
//    /**
//     * 初始化页面内容
//     */
//    fun initPagerContent() {
//        mPresenter.getRewardGiftList()
//        svgaUtils = SvgaUtils(this, svgaImage)
//    }
//
//    override fun initEvent() {
//        super.initEvent()
////        keyboard_layout.setOnKeyboardStatusListener { isShowing, _ ->
////            when (isShowing) {
////                true -> {
////                    LogUtils.e("--------->>>>"+isShowing)
////                    setGone(ivGifts)
////                    setGone(ivRedEnvelopes)
////                    setGone(ivRecharges)
////                    if (ivRecharges!=null) ivRecharges.clearAnimation()
////                    setVisible(tvSendMessage)
////                }
////                false -> {
////                    LogUtils.e("--------->>>>"+isShowing+"---"+panel_layout.isVisible)
////                    if ( !SoftInputUtils.isSoftShowing(this)){
////                        setVisible(ivGifts)
////                        setVisible(ivRedEnvelopes)
////                        setVisible(ivRecharges)
////                        setGone(tvSendMessage)
////                        if (ivRecharges!=null && UserInfoSp.getIsFirstRecharge()) AnimUtils.getFirstRechargeAnimation(ivRecharges)
////                    }
////                }
////            }
////
////        }
//        ivRecharges.setOnClickListener {
//            //            toggleKeyboard()
//        }
//        //底部有新消息
//        tvMoreInfo.setOnClickListener {
//            scrollToBottom(chatRecyclerView, multiTypeAdapter)
//            setGone(tvMoreInfo)
//        }
//        // 输入框的发送按钮事件
//        chatEditTexts.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
//            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
//                sendMessage()
//                return@OnKeyListener true
//            }
//            false
//        })
//
//        tvSendMessage.setOnClickListener {
//            sendMessage()
//        }
//        // 礼物
//        ivGifts.setOnClickListener {
//            if (UserInfoSp.getIsLogin()) {
//                initBottomGift()
//                mPresenter.loadGifData()
//            } else showExpireDialog(this)
//        }
//        // 检测支付密码是否设置
//        ivRedEnvelopes.setOnClickListener {
//            if (UserInfoSp.getIsLogin()) {
//                if (UserInfoSp.getIsSetPayPassWord()) {
//                    sendRed()
//                } else mPresenter.isSetPassWord()
//            } else showExpireDialog(this)
//
//        }
//        //imgEmoji表情
//        imgEmoji.setOnClickListener {
//            toggleKeyboard()
//        }
//
////        SoftInputUtils.setOnTouchOutsideListener {
////            if (panel_layout.isVisible) {
////                setGone(R.id.panel_layout)
////            }
////        }
//    }
//
//
//    /**
//     * 显示表情与隐藏表情
//     */
//    private fun toggleKeyboard() {
////        panel_layout.togglePanelView(R.id.panel_emoji, keyboard_layout.isKeyboardShow, chatEditTexts)
////        setGone(ivGifts)
////        setGone(ivRedEnvelopes)
////        setGone(ivRecharges)
////        if (ivRecharges!=null) ivRecharges.clearAnimation()
//    }
//
//
//    /**
//     * 底部礼物栏
//     */
//    /*总的页数*/
//    private var pageCount: Int = 3
//    /*当前显示的是第几页*/
//    private val arr = arrayOfNulls<GridViewAdapter>(3)
//    private lateinit var chatGifTabView: ChatGifTabView
//    private var selectHomeGiftListBean: HomeLiveChatGifBean? = null
//    @SuppressLint("SetTextI18n")
//    fun initBottomGift() {
//        mBottomGiftDialog = MaterialBottomDialog(this)
//        mBottomGiftDialog?.setContentView(R.layout.dialog_chat_bottom_gif)
//        val root = mBottomGiftDialog?.delegate?.findViewById<View>(R.id.design_bottom_sheet)
//        val behavior = BottomSheetBehavior.from(root)
//        behavior.isHideable = false
//        mBottomGiftDialog?.delegate?.findViewById<View>(R.id.design_bottom_sheet)?.setBackgroundColor(Color.TRANSPARENT)
//        chatGifTabView = mBottomGiftDialog?.findViewById(R.id.chatGifTabView)!!
//        mBottomGiftDialogViewPager = mBottomGiftDialog?.findViewById(R.id.viewPager)!!
//        chatGifTabView.setChatGifTab()
//        chatGifTabView.setOnSelectListener {
//            mBottomGiftDialogViewPager.currentItem = it
//        }
//        mBottomGiftDialog?.show()
//    }
//
//    /**
//     * 礼物数据源
//     */
//    fun updateGifList() {
//        val mInflater = LayoutInflater.from(this)
//        val mPagerList = ArrayList<View>()
//        //总的页数=总数/每页数量，并取整
////        pageCount = ceil(3 * 1.0 / pageSize).toInt()
//        lateinit var gridAdapter: GridViewAdapter
//        for (j in 0 until pageCount) {
//            val gridView = mInflater.inflate(R.layout.bottom_vp_gridview, mBottomGiftDialogViewPager, false) as GridView
//            when (j) {
//                0 -> gridAdapter = GridViewAdapter(this, homeGiftList?.xk, 0)
//                1 -> gridAdapter = GridViewAdapter(this, homeGiftList?.lm, 1)
//                2 -> gridAdapter = GridViewAdapter(this, homeGiftList?.zg, 2)
//            }
//            gridView.adapter = gridAdapter
//            arr[j] = gridAdapter
//            mPagerList.add(gridView)
//            gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
//                lateinit var listClick: ArrayList<HomeLiveChatGifBean>
//                when (j) {
//                    0 -> listClick = homeGiftList?.xk!!
//                    1 -> listClick = homeGiftList?.lm!!
//                    2 -> listClick = homeGiftList?.zg!!
//                }
//                for (i in listClick.indices) {
//                    val model = listClick[i]
//                    if (listClick[i].name == listClick[position].name) {
//                        if (model.isSelect) {
//                            selectHomeGiftListBean = null
//                            model.isSelect = (false)
//                        } else {
//                            model.isSelect = (true)
//                            selectHomeGiftListBean = listClick[i]
//                        }
//                    } else {
//                        model.isSelect = (false)
//                    }
//                }
//                arr[0]?.notificationItem(listClick[position].name)
//                arr[1]?.notificationItem(listClick[position].name)
//                arr[2]?.notificationItem(listClick[position].name)
//            }
//        }
//        mBottomGiftDialogViewPager.adapter = ViewPagerAdapter(mPagerList, this)
//        mBottomGiftDialogViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrollStateChanged(state: Int) {
//            }
//
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//            }
//
//            override fun onPageSelected(position: Int) {
//                chatGifTabView.setTabSelect(position)
//            }
//        })
//        mBottomGiftDialog?.findViewById<RoundTextView>(R.id.btSendGift)?.setOnClickListener {
//            if (selectHomeGiftListBean != null) {
//                mPresenter.sendGift(selectHomeGiftListBean?.id!!, 1, selectHomeGiftListBean?.name.toString(), selectHomeGiftListBean?.amount!!, selectHomeGiftListBean?.icon.toString())
//            } else ToastUtils.showNormal("请选择礼物")
//        }
//    }
//
//    /**
//     * 发红包
//     */
//    var popRedEnvelope: RedEnvelopePopup? = null
//
//    fun sendRed() {
//        popRedEnvelope = RedEnvelopePopup(this)
//        popRedEnvelope!!.animationStyle = R.style.dialogAnim
////        popRedEnvelope?.width = ViewUtils.dp2px(280)
////        popRedEnvelope?.height = ViewUtils.dp2px(330)
//        popRedEnvelope?.showAtLocation(keyboard_layout, Gravity.CENTER, 0, 0)
//        popRedEnvelope?.setOnSendClickListener { total, redNumber, redContent ->
//            when {
//                TextUtils.isEmpty(total) -> ToastUtils.showNormal("请输入金额")
//                TextUtils.isEmpty(redNumber) -> ToastUtils.showNormal("请输入红包个数")
//                else -> {
//                    mTotal = total.toInt()
//                    mRedNumber = redNumber.toInt()
//                    mRedContent = redContent
//                    mPresenter.initPassWordDialog()
//                }
//            }
//        }
//    }
//
//
//    /**
//     * 发送红包
//     */
//    fun sendRedEnvelope() {
//        mPresenter.sendRedEnvelope(intent.getIntExtra(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID, 0), SpUtils.getInt(UserConstant.USER_ID, 0), mTotal!!, mRedNumber, mRedContent, mPassword)
//    }
//
//    /**
//     * 显示余额不足
//     */
//    fun showReChargePopup() {
//        val reChargePopup = ReChargePopup(this)
//        reChargePopup.setOnSendClickListener {
//            // TODO 跳转充值
//        }
////        reChargePopup.showAtLocation(rootViews, Gravity.CENTER, 0, 0)
//    }
//
//    /**
//     * 初始化打赏榜
//     */
//    fun initRewardList(data: List<HomeLiveRoomRewardBean>) {
//        val adapter = HomeLiveRoomTopAdapter(this)
//        val layoutManager = LinearLayoutManager(getContext())
//        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
//        rvTabLayout?.layoutManager = layoutManager
//        rvTabLayout?.adapter = adapter
//        rvTabLayout?.addItemDecoration(HorizontalItemSpaceDecoration(ViewUtils.dp2px(10)))
//        adapter.addAll(data)
//    }
//
//    /**
//     * 初始化聊天室
//     */
//    fun initChatRoom(data: ArrayList<HomeLiveChatBean>) {
//        //初始化聊天RecycleView
////        multiTypeAdapter.register(HomeLiveChatBean::class.java, HomeLiveChatHolder())
//        chatRecyclerView?.adapter = multiTypeAdapter
//        val layoutManager = LinearLayoutManager(getContext())
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        chatRecyclerView?.layoutManager = layoutManager
//        multiTypeAdapter.addAll(data)
//
//        // 获取列表的滑动事件，控制一键到底部
//        chatRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
//                    isBottom = isStayBottom(chatRecyclerView!!, multiTypeAdapter)
//                }
//            }
//        })
//    }
//
//    //Socket
//    inner class NetWorkChangReceiver : BroadcastReceiver() {
//        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//        override fun onReceive(context: Context?, intent: Intent?) {
//            if (NetWorkUtils.isNetworkConnected()) {
//                mPresenter.startWebSocketConnect()
//            } else {
//                LogUtils.d("WsManager----------网络不可用")
//                mPresenter.stopConnect()
//            }
//        }
//    }
//
//    fun sendMessage() {
//        val content = chatEditTexts.text.trim().toString()
//        if (isEmpty(content)) {
//            ToastUtils.showNormal(getString(R.string.live_chat_empty))
//        } else {
//            chatEditTexts.setText("")
//
//            mPresenter.sendMessage(content)
//        }
//    }
//
//    /**
//     * 接收弹幕消息事件
//     */
//    @Subscribe(thread = EventThread.MAIN_THREAD)
//    fun onUpdateDanmu(data: HomeLiveChatBean) {
//        if (mController.mMediaPlayer != null && mController.mMediaPlayer.isFullScreen) {
//            if (isNotEmpty(data.text)) mVideoView.addDanmaku(data.text, data.isMe)
//        }
//    }
//
//    fun onIsShowRedEnvelope(eventBean: HomeLiveRedMessageBean) {
//        if (eventBean.gift_type == 4) {
//            // 通知有可抢的红包
//            setVisible(ivEnvelopeTip)
//            ivEnvelopeTip.setOnClickListener {
//                mOpenRedPopup = OpenRedEnvelopeDialog(this)
//                mOpenRedPopup.setRedTitle("恭喜发财，大吉大利")
//                mOpenRedPopup.setOnOpenClickListener {
//                    mPresenter.sendRedReceive(eventBean.rid, false)
//                }
//                mOpenRedPopup.show()
//            }
////            mController.showRedEnvelope(mPresenter, eventBean)
//        }
//    }
//
//    /**
//     * 抢到红包后的回调
//     */
//    fun showOpenRedContent(it: HomeLiveRedReceiveBean) {
//        setGone(ivEnvelopeTip)
//        mPresenter.getRoomRed(UserInfoSp.getUserId())
//        mOpenRedPopup.setRedContent(it.send_text)
//        mOpenRedPopup.setRedMoney(it.count.toString())
//        mOpenRedPopup.setRedUserName(it.send_user_name)
//        mOpenRedPopup.setRedLogo(it.send_user_avatar)
//        mOpenRedPopup.isShowRedLogo(true)
//    }
//
//    /**
//     * 提示该红包已抢完
//     */
//    fun showOpenRedOverKnew(bean: HomeLiveRedReceiveBean) {
//        setGone(ivEnvelopeTip)
//        mPresenter.getRoomRed(SpUtils.getInt(UserConstant.USER_ID, 0))
//        mOpenRedPopup.showRedOver(bean)
//    }
//
//    /**
//     * 接收动画
//     * 小礼物 炫酷：齐天大圣      浪漫： love  求带走   尊贵：至尊宝。臭鸡蛋
//     */
//    @Subscribe(thread = EventThread.MAIN_THREAD)
//    fun onGetGiftSmall(data: HomeLiveSmallAnimatorBean) {
//        if (data.gift_id == 18 || data.gift_id == 23 || data.gift_id == 24 || data.gift_id == 30 || data.gift_id == 31) {
//            mPresenter.initGiftAnimator(data)
//        }
//        if (data.gift_id == 15 || data.gift_id == 16 || data.gift_id == 17 || data.gift_id == 27 || data.gift_id == 28 || data.gift_id == 29) {
//            mPresenter.loadSvga(data)
//        }
//    }
//
//
//    override fun onBackClick() {
//        SoftInputUtils.hideSoftInput(this)
//        if (isFullScreen()) {
//            if (mController.softInputDialog != null) {
//                mController.softInputDialog.dismiss()
//            }
//            setFullScreen(false)
//            mController.doStartStopFullScreen(mController.sendLayout)
////            if (mPIPManager != null && mPIPManager?.onBackPress()!!)
////                return true
//        } else {
//            mVideoView.stopPlayback()
//            super.onBackClick()
//        }
//
//    }
//
//
//    @SuppressLint("SetTextI18n")
//    fun initRewardAnimator() {
//        rewardLayout.setGiftAdapter(object : RewardLayout.GiftAdapter<SendGiftBean> {
//            override fun onInit(view: View?, bean: SendGiftBean?): View {
//                // 初始化数据
//                val giftNum = view?.findViewById<TextView>(R.id.tv_gift_amount)
//                val giftImage = view?.findViewById<ImageView>(R.id.iv_gift_img)
//                val userName = view?.findViewById<TextView>(R.id.tv_user_name)
//                val giftName = view?.findViewById<TextView>(R.id.tv_gift_name)
//                val userPhoto = view?.findViewById<ImageView>(R.id.riv_gift_my_avatar)
//                giftNum?.text = "x " + bean?.theSendGiftSize
//                bean?.theGiftCount = bean?.theSendGiftSize!!
//                ImageManager.loadImage(bean.giftImg, giftImage!!)
//                ImageManager.loadImage(bean.userPhoto, userPhoto!!)
//                userName?.text = bean.userName
//                giftName?.text = "送出 " + bean.giftName
//                return view
//            }
//
//            override fun onUpdate(view: View?, o: SendGiftBean?, t: SendGiftBean?): View {
//                val showNum = o?.theGiftCount as Int + o.theSendGiftSize
//                val giftNum = view?.findViewById<TextView>(R.id.tv_gift_amount)
//                val giftImage = view?.findViewById<ImageView>(R.id.iv_gift_img)
//                /* 刷新已存在的gifted界面数据 */
//                giftNum?.text = "x$showNum"
//                ImageManager.loadImage(o.giftImg, giftImage!!)
//
//                // 数字刷新动画
//                NumAnim().start(giftNum!!)
//                // 更新累计礼物数量
//                o.theGiftCount = showNum
//                // 更新其它自定义字段
////              o.setUserName(t.getUserName());
//                return view
//            }
//
//            override fun onKickEnd(bean: SendGiftBean?) {
//            }
//
//            override fun onComboEnd(bean: SendGiftBean?) {
//            }
//
//            override fun addAnim(view: View?) {
//                val textView = view?.findViewById<View>(R.id.tv_gift_amount) as TextView
//                val img = view.findViewById<View>(R.id.iv_gift_img) as ImageView
//                // 整个giftview动画
//                val giftInAnim = AnimUtils.getInAnimation(this@HomeLiveDetailsActivity)
//                // 礼物图像动画
//                val imgInAnim = AnimUtils.getInAnimation(this@HomeLiveDetailsActivity)
//                // 首次连击动画
//                val comboAnim = NumAnim()
//                imgInAnim.startTime = 500
//                imgInAnim.setAnimationListener(object : Animation.AnimationListener {
//                    override fun onAnimationStart(animation: Animation) {
//                        textView.visibility = View.GONE
//                    }
//
//                    override fun onAnimationEnd(animation: Animation) {
//                        textView.visibility = View.VISIBLE
//                        comboAnim.start(textView)
//                    }
//
//                    override fun onAnimationRepeat(animation: Animation) {
//                    }
//                })
//                view.startAnimation(giftInAnim)
//                img.startAnimation(imgInAnim)
//            }
//
//            override fun outAnim(): AnimationSet {
//                return AnimUtils.getOutAnimation(this@HomeLiveDetailsActivity)
//            }
//
//            override fun checkUnique(o: SendGiftBean?, t: SendGiftBean?): Boolean {
//                return o?.theGiftId === t?.theGiftId && o?.theUserId === t?.theUserId
//            }
//
//            override fun generateBean(bean: SendGiftBean?): SendGiftBean? {
//                try {
//                    return bean?.clone() as SendGiftBean
//                } catch (e: CloneNotSupportedException) {
//                    e.printStackTrace()
//                }
//                return null
//            }
//
//        })
//    }
//
//    companion object {
//        fun newInstance(anchorId: Int, title: String, status: Int): HomeLiveDetailsFragment {
//            val fragment = HomeLiveDetailsFragment()
//            val bundle = Bundle()
//            bundle.putInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID, anchorId)
//            bundle.putString(IntentConstant.HOME_LIVE_CHAT_TITLE, title)
//            bundle.putInt(IntentConstant.HOME_LIVE_CHAT_STATUE, status)
//            fragment.arguments = bundle
//            return fragment
//        }
//    }
//
//
//    /**
//     * 是否停留在列表底部
//     */
//    private fun isStayBottom(recyclerView: RecyclerView, multiTypeAdapter: MultiTypeAdapter): Boolean {
//        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager
//        return layoutManager?.findLastVisibleItemPosition() == multiTypeAdapter.itemCount - 1
//    }
//
//    /**
//     * 滑动到最底部
//     */
//    fun scrollToBottom(recyclerView: RecyclerView, multiTypeAdapter: MultiTypeAdapter) {
//        if (!isStayBottom(recyclerView, multiTypeAdapter)) {
//            recyclerView.smoothScrollToPosition((recyclerView.layoutManager as? LinearLayoutManager)?.itemCount
//                    ?: 0)
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        mPIPManager?.pause()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        mPIPManager?.resume()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mVideoView.stopPlayback()
//        mPIPManager?.reset()
//        StatusBarUtils.setStatusBarForegroundColor(this, true)
//        mPresenter.stopConnect()
//        unregisterReceiver(mNetWorkReceiver)
//    }
//
//}