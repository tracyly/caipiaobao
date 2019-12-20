//package com.fenghuang.caipiaobao.ui.home.live.liveroom
//
//import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog.showExpireDialog
//import android.annotation.SuppressLint
//import android.content.Context
//import android.text.Editable
//import android.text.TextUtils
//import android.text.TextWatcher
//import android.view.View
//import android.widget.TextView
//import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
//import com.fenghuang.baselib.utils.SpUtils
//import com.fenghuang.baselib.utils.ToastUtils
//import com.fenghuang.baselib.utils.ViewUtils
//import com.fenghuang.caipiaobao.R
//import com.fenghuang.caipiaobao.constant.IntentConstant
//import com.fenghuang.caipiaobao.constant.UserConstant
//import com.fenghuang.caipiaobao.data.api.WebUrlProvider
//import com.fenghuang.caipiaobao.socket.WsManager
//import com.fenghuang.caipiaobao.socket.listener.WsStatusListener
//import com.fenghuang.caipiaobao.ui.home.data.*
//import com.fenghuang.caipiaobao.ui.mine.data.MineApi
//import com.fenghuang.caipiaobao.ui.mine.data.MinePassWordTime
//import com.fenghuang.caipiaobao.ui.widget.popup.RedEnvelopeFullPopup
//import com.fenghuang.caipiaobao.utils.Arith
//import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog
//import com.fenghuang.caipiaobao.utils.JsonUtils
//import com.fenghuang.caipiaobao.utils.UserInfoSp
//import com.fenghuang.caipiaobao.widget.dialog.PassWordDialog
//import com.fenghuang.caipiaobao.widget.dialog.PassWordFullDialog
//import com.fenghuang.caipiaobao.widget.gift.SendGiftBean
//import com.github.ybq.android.spinkit.SpinKitView
//import com.hwangjr.rxbus.RxBus
//import kotlinx.android.synthetic.main.fragment_live_details.*
//import me.jessyan.autosize.utils.LogUtils
//import okhttp3.OkHttpClient
//import okhttp3.Response
//import okio.ByteString
//import org.json.JSONObject
//import java.lang.Exception
//import java.util.*
//import java.util.concurrent.TimeUnit
//
//
///**
// *  author : Peter
// *  date   : 2019/8/13 14:55
// *  desc   : 直播详情页的P层
// */
//class HomeLiveDetailsActivityPresenter(val context: Context, private val anchorId: Int) : BaseMvpPresenter<HomeLiveDetailsActivity>() {
//
//    private var mWsManager: WsManager? = null
//    private var mTimer: Timer? = null
//    private var mWsStatusListener: WsStatusListener? = null
//    // 推送
//    private val TYPE_SUBSCRIBE = "subscribe"
//    // 初始化
//    private val TYPE_PUBLISH = "publish"
//    // 心跳
//    private val TYPE_PING = "ping"
//    // 礼物&红包
//    private val TYPE_GIFT = "gift"
//    // 红包的id
//    private var mRid = 0
//
//    private val mVideos = LinkedHashMap<String, String>()
//
//    fun loadData() {
//        getRoomRed(UserInfoSp.getUserId())
//    }
//
//    fun startWebSocketConnect() {
//        initStatusListener()
//        mWsManager = WsManager.Builder(context)
//                .client(OkHttpClient().newBuilder()
//                        .pingInterval(1000 * 50, TimeUnit.SECONDS)
//                        .retryOnConnectionFailure(true)
//                        .build())
//                .needReconnect(true).wsUrl(WebUrlProvider.API_URL_WEB_SOCKET)
//                .build()
//        mWsManager?.setWsStatusListener(mWsStatusListener)
//        mWsManager?.startConnect()
//    }
//
//    /**
//     * 释放wobSocket
//     */
//    fun stopConnect() {
//        if (null != mWsManager) {
//            mWsManager?.stopConnect()
//            mWsManager = null
//        }
//    }
//
//    /**
//     * 发送一条消息
//     */
//    fun sendMessage(content: String) {
//        mWsManager?.sendMessage(getPublishParams(content))
//    }
//
//
//    private fun initStatusListener() {
//        mWsStatusListener = object : WsStatusListener() {
//            override fun onOpen(response: Response) {
//                super.onOpen(response)
//                LogUtils.d("WsManager-----onOpen response=$response")
//                mWsManager?.sendMessage(getSubscribeParams())
//                if (mTimer == null) mTimer = Timer()
//                mTimer?.schedule(object : TimerTask() {
//                    override fun run() {
//                        mWsManager?.sendMessage(getPingParams())
//                        LogUtils.d("WsManager-----发送了心跳")
//                    }
//                }, 0, 1000 * 54)
//            }
//
//            override fun onMessage(text: String) {
//                super.onMessage(text)
//                LogUtils.d("WsManager-----onMessage$text")
//                val data = WebUrlProvider.getData<HomeLiveChatBean>(text, HomeLiveChatBean::class.java)
//                LogUtils.d("WsManager-----111onMessage${data.toString()}")
//                // 发送通知弹幕
//                if (data != null) {
//                    RxBus.get().post(data)
//                    if (mView.isActive()) {
//                        if (data.type == "subscribe" || data.type == "first" || data.type == "publish" || data.type == "gift") {
//                            mView.multiTypeAdapter.add(data)
//                            if (mView.isBottom) {
//                                mView.scrollToBottom(mView.chatRecyclerView, mView.multiTypeAdapter)
//                                mView.setGone(mView.tvMoreInfo)
//                            } else {
//                                mView.tvMoreInfo.visibility = View.VISIBLE
//                            }
//                        } else if (data.code == 403) {
//                            showExpireDialog(context)
//                        }
//
//                        // 告知有红包
//                        if (data.gift_type == 4) {
//                            mView.onIsShowRedEnvelope(HomeLiveRedMessageBean(4, data.r_id, data.gift_text, data.userName))
//                            RxBus.get().post(HomeLiveRedMessageBean(4, data.r_id, data.gift_text, data.userName))
//                        }
//                        if (data.gift_type == 1) {
//                            val textInfo = data.userName + ": 送给主播1个 " + data.gift_name
//                            val bean = HomeLiveChatBean("", "", 0,
//                                    "", "", "", "", textInfo, "", false, 0, UserInfoSp.getUserType()!!,
//                                    "", 0, 0, "", "0".toFloat(), 0, UserInfoSp.getVipLevel(), "", 0, "", 0)
//                            RxBus.get().post(bean)
//                            if (!data.isMe) {
//                                RxBus.get().post(HomeLiveSmallAnimatorBean(data.gift_id, data.gift_name, data.icon, data.user_id, data.avatar.toString(), UserInfoSp.getUserNickName().toString()))
//                            }
//                        }
//                    }
//                }
//            }
//
//            override fun onMessage(bytes: ByteString) {
//                super.onMessage(bytes)
//                LogUtils.d("WsManager-----onMessage$bytes")
//            }
//
//            override fun onReconnect() {
//                super.onReconnect()
//                LogUtils.d("WsManager-----onReconnect")
//            }
//
//            override fun onClosing(code: Int, reason: String) {
//                super.onClosing(code, reason)
//                LogUtils.d("WsManager-----onClosing")
//            }
//
//            override fun onClosed(code: Int, reason: String) {
//                super.onClosed(code, reason)
//                LogUtils.d("WsManager-----onClosed")
//            }
//
//            override fun onFailure(t: Throwable?, response: Response?) {
//                super.onFailure(t, response)
//                LogUtils.d("WsManager-----onFailure$response=$t")
//                if (mTimer != null) {
//                    mTimer?.cancel()
//                    mTimer = null
//                }
//            }
//        }
//    }
//
//
//    /**
//     * 获取直播间红包队列
//     */
//    fun getRoomRed(userId: Int) {
//        HomeApi.getRoomRed(userId, anchorId) {
//            onSuccess {
//                if (mView.isActive()) {
//                    if (it.isNotEmpty()) {
//                        val homeLiveRedRoom = it[it.size - 1]
//                        mView.onIsShowRedEnvelope(HomeLiveRedMessageBean(4, homeLiveRedRoom.id, "", ""))
//                    }
//                }
//
//            }
//            onFailed {
//                //                ToastUtils.showError(it.getMsg())
//            }
//        }
//    }
//
//    fun loadLiveInfo(anchorId: Int, userId: Int) {
//        HomeApi.getHomeLiveRoomResult(anchorId, userId) {
//            onSuccess {
//                if (mView.isActive()) {
//                    if (!it.liveInfo.isNullOrEmpty()) {
//                        it.liveInfo.forEachIndexed { _, homeLiveRoomListBean ->
//                            if (homeLiveRoomListBean.type == "HDL") {
//                                mVideos["标清"] = homeLiveRoomListBean.liveUrl.originPullUrl
//                            }
//                        }
//                        mView.setLogoInfo(it)
//                        mView.startLive(mVideos, it.live_status)
//                    }
//                    mView.initPagerContent()
//
//                }
//            }
//            onFailed {
//                ToastUtils.showToast("主播数据异常，请稍后重试")
//            }
//        }
//    }
//
//    /**
//     * 获取打赏榜
//     */
//    fun getRewardGiftList() {
//        HomeApi.getHomeLiveChatRewardResult(mView.intent.getIntExtra(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID, 0)) {
//            onSuccess {
//                if (mView.isActive()) {
//                mView.initRewardList(it)
//                }
//            }
//            onFailed {
//                ToastUtils.showToast("获取打赏榜失败")
//            }
//        }
//    }
//
//    /**
//     * 初始化最新20条消息
//     */
//    fun initRecentlyNews(userId: Int, anchorId: Int) {
//        HomeApi.getRecentlyNews(userId, anchorId) {
//            onSuccess {
//                if (mView.isActive()) {
//                mView.initChatRoom(it)
//                mView.multiTypeAdapter.add(HomeLiveChatBean("", "", 0,
//                        "", "", "", "first", "", "", false, 0, UserInfoSp.getUserType()!!,
//                        UserInfoSp.getUserNickName().toString(), 0, 0, mView.tvTopUserName?.text.toString(), "0".toFloat(), 0, UserInfoSp.getVipLevel(), "", 0, "", 0))
//                mView.chatRecyclerView.scrollToPosition(mView.multiTypeAdapter.itemCount - 1)
//
//                }
//            }
//        }
//    }
//
//    fun loadSvga(data: HomeLiveSmallAnimatorBean) {
//        if (mView.isActive()) {
////            if (!mView.mVideoView.isFullScreen) {
////                mView.svgaUtils.initAnimator()
////                when (data.gift_id) {
////                    15 -> mView.svgaUtils.startAnimator("lanbocar")
////                    16 -> mView.svgaUtils.startAnimator("yanhua")
////                    17 -> mView.svgaUtils.startAnimator("fenghuangjiche")
////                    27 -> mView.svgaUtils.startAnimator("diwang")
////                    29 -> mView.svgaUtils.startAnimator("youting")
////                    28 -> mView.svgaUtils.startAnimator("fenghuang")
////                }
////            }
//        }
//
//    }
//
//    /**
//     * 发送红包
//     */
//    fun sendRedEnvelope(anchorId: Int, userId: Int, amount: Int, num: Int, text: String, password: String) {
//        HomeApi.getHomeLiveSendRedEnvelope(anchorId, userId, amount, num, text, password) {
//            onSuccess {
//                if (mView.isActive()) {
//
//                ToastUtils.showSuccess("发送红包成功")
//                passWordDialog?.dismiss()
//                passWordFullDialog?.dismiss()
//                mView.popRedEnvelope?.dismiss()
//                redEnvelopePopups?.dismiss()
//                // 通知直播间有红包
//                mWsManager?.sendMessage(getGifParams(4, it.rid, "红包", amount.toFloat(), num, text, 0, "", "", UserInfoSp.getUserPhoto().toString()))
//                mView.hidePageLoadingDialog()
//                }
//            }
//            onFailed {
//                if (it.getCode() == 2) {
//                    // 余额不足
//                    mView.hidePageLoadingDialog()
//                    mView.showReChargePopup()
//                }
//                ToastUtils.showError(it.getMsg())
//                mView.hidePageLoadingDialog()
//            }
//        }
//    }
//
//    /**
//     * 抢红包
//     */
//    fun sendRedReceive(rid: Int, isControl: Boolean) {
//        HomeApi.getRedReceive(UserInfoSp.getUserId(), rid) {
//            onSuccess {
//                if (mView.isActive()) {
//                if (isControl) {
////                    mView.mController.showOpenRedContent(it, this@HomeLiveDetailsActivityPresenter)
//                    mView.setGone(mView.ivEnvelopeTip)
//                } else mView.showOpenRedContent(it)
//
//                }
//            }
//            onFailed {
//                if (it.getCode() == 2) {
//                    // 红包被抢完了
//                    if (isControl) {
////                        mView.mController.showOpenRedOverKnew(JsonUtils.fromJson(it.getDataCode().toString(), HomeLiveRedReceiveBean::class.java), this@HomeLiveDetailsActivityPresenter)
//                    } else mView.showOpenRedOverKnew(JsonUtils.fromJson(it.getDataCode().toString(), HomeLiveRedReceiveBean::class.java))
//                }
//            }
//        }
//    }
//
//    /**
//     * 检测是否设置支付密码
//     */
//    fun isSetPassWord() {
//        mView.showPageLoadingDialog()
//        HomeApi.isSetPassWord {
//            onSuccess {
//                if (mView.isActive()) {
//
//                mView.hidePageLoading()
//                mView.sendRed()
//                mView.hidePageLoadingDialog()
//                UserInfoSp.putIsSetPayPassWord(true)
//                }
//            }
//            onFailed {
//                mView.hidePageLoadingDialog()
//                showExpireDialog(mView, it)
//                UserInfoSp.putIsSetPayPassWord(false)
//            }
//        }
//    }
//
//    /**
//     * 验证支付密码
//     */
//    var passWordDialog: PassWordDialog? = null
//
//    fun initPassWordDialog() {
//        passWordDialog = PassWordDialog(mView, ViewUtils.getScreenWidth(), ViewUtils.dp2px(156))
//        passWordDialog!!.setTextWatchListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                if (s?.length == 6) {
//                    //验证支付密码
//                    mView.showPageLoadingDialog()
//                    MineApi.verifyPayPassWord(s.toString()) {
//                        onSuccess {
//                            if (mView.isActive()) {
//
//                            mView.mPassword = s.toString()
//                            //发红包
//                            mView.sendRedEnvelope()
//                            }
//                        }
//                        onFailed {
//                            if (it.getCode() == 1002) {
//                                passWordDialog!!.showTipsText(it.getMsg().toString() + "," + "您还有" + JsonUtils.fromJson(it.getDataCode().toString(), MinePassWordTime::class.java).remain_times.toString() + "次机会")
//                            } else {
//                                passWordDialog!!.showTipsText(it.getMsg().toString())
//                            }
//                            mView.hidePageLoadingDialog()
//                        }
//                    }
//                }
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//        })
//        passWordDialog!!.show()
//    }
//
//    /**
//     * 横屏验证支付密码
//     */
//    var passWordFullDialog: PassWordFullDialog? = null
//    var redEnvelopePopups: RedEnvelopeFullPopup? = null
//
//    fun initFullPassWordDialog(redEnvelopePopup: RedEnvelopeFullPopup, mTotal: String, mRedNumber: String, mRedContent: String) {
//        redEnvelopePopups = redEnvelopePopup
//        passWordFullDialog = PassWordFullDialog(mView)
//        passWordFullDialog!!.setTextWatchListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                if (s?.length == 6) {
//                    //验证支付密码
//                    mView.showPageLoadingDialog()
//                    MineApi.verifyPayPassWord(s.toString()) {
//                        onSuccess {
//                            if (mView.isActive()) {
//                            //发红包
//                            sendRedEnvelope(mView.intent.getIntExtra(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID, 0), SpUtils.getInt(UserConstant.USER_ID, 0), mTotal.toInt(), mRedNumber.toInt(), mRedContent, s.toString())
//                            }
//                        }
//                        onFailed {
//                            if (it.getCode() == 1002) {
//                                passWordFullDialog!!.showTipsText(it.getMsg().toString() + "," + "您还有" + JsonUtils.fromJson(it.getDataCode().toString(), MinePassWordTime::class.java).remain_times.toString() + "次机会")
//                            } else {
//                                passWordFullDialog!!.showTipsText(it.getMsg().toString())
//                            }
//                            mView.hidePageLoadingDialog()
//                        }
//                    }
//                }
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//        })
//        passWordFullDialog?.show()
//    }
//
//
//    /**
//     * 获取gif礼物
//     */
//    fun loadGifData() {
//        if (mView.homeGiftList != null) {
//            //获取钻石
//            MineApi.getUserDiamond {
//                onSuccess {
//                    if (mView.isActive()) {
//
//                    if (mView.mBottomGiftDialog != null && mView.mBottomGiftDialog?.isShowing!!) {
//                        mView.updateGifList()
//                        mView.mBottomGiftDialog?.findViewById<TextView>(R.id.tvDiamondTotal)?.text = it.diamond
//                        mView.setGone(mView.mBottomGiftDialog?.findViewById<SpinKitView>(R.id.giftLoading))
//                    }
//                    if (mView.mController.materialBottomDialog != null && mView.mController.materialBottomDialog?.isShowing!!) {
//                        mView.setGone(mView.mController.materialBottomDialog?.findViewById<SpinKitView>(R.id.giftLoading))
//                        mView.mController.materialBottomDialog?.findViewById<TextView>(R.id.tvDiamondTotal)?.text = it.diamond
//                        mView.mController.initBottomGiftData(mView.homeGiftList)
//                    }
//                    }
//
//                }
//                onFailed {
//                    showExpireDialog(mView, it)
//                    if (mView.mBottomGiftDialog != null && mView.mBottomGiftDialog?.isShowing!!) {
//                        mView.setGone(mView.mBottomGiftDialog?.findViewById<SpinKitView>(R.id.giftLoading))
//                    }
//                    if (mView.mController.materialBottomDialog != null && mView.mController.materialBottomDialog?.isShowing!!) {
//                        mView.setGone(mView.mController.materialBottomDialog?.findViewById<SpinKitView>(R.id.giftLoading))
//                    }
//                }
//            }
//        } else {
//            HomeApi.getGiftList {
//                onSuccess { it ->
//                    if (mView.isActive()) {
//
//                    if (!TextUtils.isEmpty(it)) {
//                        mView.homeGiftList = JsonUtils.fromJson(it, HomeLiveChatGifTitleBean::class.java)
//                        if (mView.mBottomGiftDialog != null && mView.mBottomGiftDialog?.isShowing!!) {
//                            mView.updateGifList()
//                        }
//                        if (mView.mController.materialBottomDialog != null && mView.mController.materialBottomDialog?.isShowing!!) {
//                            mView.mController.initBottomGiftData(mView.homeGiftList)
//                        }
//                        //获取钻石
//                        MineApi.getUserDiamond {
//                            onSuccess {
//                                if (mView.isActive()) {
//
//                                if (mView.mBottomGiftDialog != null && mView.mBottomGiftDialog?.isShowing!!) {
//                                    mView.mBottomGiftDialog!!.findViewById<TextView>(R.id.tvDiamondTotal)?.text = it.diamond
//                                    mView.setGone(mView.mBottomGiftDialog!!.findViewById<SpinKitView>(R.id.giftLoading))
//                                }
//                                if (mView.mController.materialBottomDialog != null && mView.mController.materialBottomDialog?.isShowing!!) {
//                                    mView.setGone(mView.mController.materialBottomDialog!!.findViewById<SpinKitView>(R.id.giftLoading))
//                                    mView.mController.materialBottomDialog!!.findViewById<TextView>(R.id.tvDiamondTotal)?.text = it.diamond
//                                }
//                                }
//                    }
//                            }
//                            onFailed {
//                                showExpireDialog(mView, it)
//                                if (mView.mBottomGiftDialog != null && mView.mBottomGiftDialog?.isShowing!!) {
//                                    mView.setGone(mView.mBottomGiftDialog!!.findViewById<SpinKitView>(R.id.giftLoading))
//                                }
//                                if (mView.mController.materialBottomDialog != null && mView.mController.materialBottomDialog != null && mView.mController.materialBottomDialog?.isShowing!!) {
//                                    mView.setGone(mView.mController.materialBottomDialog!!.findViewById<SpinKitView>(R.id.giftLoading))
//                                }
//                            }
//                        }
//                    }
//                }
//                onFailed {
//                    ToastUtils.showNormal("获取礼物数据失败")
//                }
//            }
//        }
//    }
//
//
//    /**
//     * 送礼物
//     */
//    fun sendGift(gift_id: Int, gift_num: Int, gift_name: String, gift_Total: String, icon: String) {
//        HomeApi.setGift(UserInfoSp.getUserId(), anchorId, gift_id, gift_num) {
//            onSuccess {
//                if (mView.isActive()) {
//
//                mView.mBottomGiftDialog?.dismiss()
//                RxBus.get().post(HomeLiveSmallAnimatorBean(gift_id, gift_name, icon, UserInfoSp.getUserId(), UserInfoSp.getUserPhoto().toString(), UserInfoSp.getUserNickName().toString()))
//                mWsManager?.sendMessage(getGifParams(1, 0, gift_name, 0f, 1, "", gift_id, icon, "1", UserInfoSp.getUserPhoto().toString()))
//                val textView = mView.mBottomGiftDialog?.findViewById<TextView>(R.id.tvDiamondTotal)
//                if (!TextUtils.isEmpty(textView?.text)) textView?.text = Arith.sub(textView?.text.toString(), gift_Total)
//                }
//            }
//            onFailed {
//                ToastUtils.showNormal("赠送失败")
//            }
//        }
//    }
//
//
//    /**
//     * 礼物动画
//     */
//    private val giftList = ArrayList<SendGiftBean>()
//
//    @SuppressLint("SetTextI18n")
//    fun initGiftAnimator(data: HomeLiveSmallAnimatorBean) {
//        val bean = SendGiftBean(data.user_id, data.gift_id, data.user_name, data.user_icon, data.git_name, data.gift_icon, 3000)
//        giftList.add(bean)
//        mView.rewardLayout.put(bean)
//    }
//
//    private fun getSubscribeParams(): String {
//        val jsonObject = JSONObject()
//        jsonObject.put("room_id", anchorId)
//        jsonObject.put("user_id", UserInfoSp.getUserId())
//        jsonObject.put("type", TYPE_SUBSCRIBE)
//        jsonObject.put("userName", UserInfoSp.getUserNickName())
//        return jsonObject.toString()
//    }
//
//    private fun getPublishParams(content: String): String {
//        val jsonObject = JSONObject()
//        jsonObject.put("room_id", anchorId)
//        jsonObject.put("user_id", SpUtils.getInt(UserConstant.USER_ID, 0))
//        jsonObject.put("type", TYPE_PUBLISH)
//        jsonObject.put("userName", UserInfoSp.getUserNickName())
//        jsonObject.put("text", content)
//        return jsonObject.toString()
//    }
//
//    private fun getPingParams(): String {
//        val jsonObject = JSONObject()
//        jsonObject.put("room_id", anchorId)
//        jsonObject.put("user_id", UserInfoSp.getUserId())
//        jsonObject.put("type", TYPE_PING)
//        jsonObject.put("userName", UserInfoSp.getUserNickName())
//        return jsonObject.toString()
//    }
//
//    private fun getGifParams(gifType: Int, rId: Int, giftName: String, giftPrice: Float, giftNum: Int, text: String, gift_id: Int, icon: String, vip: String, avatar: String): String {
//        val jsonObject = JSONObject()
//        jsonObject.put("room_id", anchorId)
//        jsonObject.put("user_id", SpUtils.getInt(UserConstant.USER_ID, 0))
//        jsonObject.put("type", TYPE_GIFT)
//        jsonObject.put("userName", UserInfoSp.getUserNickName())
//        // gift_type: 礼物类型 1-普通 2-表白 3-彩票  4-红包
//        if (gifType == 4) {
//            jsonObject.put("r_id", rId)
//            jsonObject.put("gift_text", text)
//        } else if (gifType == 1) {
//            jsonObject.put("gift_id", gift_id)
//            jsonObject.put("icon", icon)
//            jsonObject.put("vip", vip)
//        }
//        jsonObject.put("gift_type", gifType)
//        jsonObject.put("gift_name", giftName)
//        jsonObject.put("gift_price", giftPrice)
//        jsonObject.put("gift_num", giftNum)
//        jsonObject.put("avatar", avatar)
//        return jsonObject.toString()
//    }
//
//
//}