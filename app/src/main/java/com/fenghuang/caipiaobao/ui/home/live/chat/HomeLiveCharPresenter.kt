package com.fenghuang.caipiaobao.ui.home.live.chat

import ExceptionDialog.showExpireDialog
import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.widget.TextView
import com.fenghuang.baselib.base.recycler.BaseRecyclerPresenter
import com.fenghuang.baselib.utils.SpUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.baselib.widget.dialog.MaterialLoadingDialog
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.constant.UserConstant
import com.fenghuang.caipiaobao.data.api.WebUrlProvider
import com.fenghuang.caipiaobao.socket.WsManager
import com.fenghuang.caipiaobao.socket.listener.WsStatusListener
import com.fenghuang.caipiaobao.ui.home.data.*
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import com.fenghuang.caipiaobao.utils.Arith
import com.fenghuang.caipiaobao.utils.JsonUtils
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.gift.SendGiftBean
import com.hwangjr.rxbus.RxBus
import kotlinx.android.synthetic.main.fragment_live_chat.*
import me.jessyan.autosize.utils.LogUtils
import okhttp3.OkHttpClient
import okhttp3.Response
import okio.ByteString
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * author : Peter
 * date   : 2019/8/31 11:11
 */
class HomeLiveCharPresenter(val context: Context, private val anchorId: Int) : BaseRecyclerPresenter<HomeLiveChatFragment>() {

    private var mWsManager: WsManager? = null
    private var mTimer: Timer? = null
    private var mWsStatusListener: WsStatusListener? = null
    private var isShowPageEmpty: Boolean = true
    // 推送
    private val TYPE_SUBSCRIBE = "subscribe"
    // 初始化
    private val TYPE_PUBLISH = "publish"
    // 心跳
    private val TYPE_PING = "ping"
    // 礼物&红包
    private val TYPE_GIFT = "gift"
    // 红包的id
    private var mRid = 0

    override fun loadData(page: Int) {
        initStatusListener()
        getRoomRed(UserInfoSp.getUserId())
    }

    fun startWebSocketConnect() {
        mWsManager = WsManager.Builder(context)
                .client(OkHttpClient().newBuilder()
                        .pingInterval(1000 * 50, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build())
                .needReconnect(true).wsUrl(WebUrlProvider.API_URL_WEB_SOCKET)
                .build()
        mWsManager?.setWsStatusListener(mWsStatusListener)
        mWsManager?.startConnect()
    }

    /**
     * 释放wobSocket
     */
    fun stopConnect() {
        if (null != mWsManager) {
            mWsManager?.stopConnect()
            mWsManager = null
        }
    }

    /**
     * 发送一条消息
     */
    fun sendMessage(content: String) {
        mWsManager?.sendMessage(getPublishParams(content))
    }


    private fun initStatusListener() {

        mWsStatusListener = object : WsStatusListener() {
            override fun onOpen(response: Response) {
                super.onOpen(response)
                LogUtils.d("WsManager-----onOpen response=$response")
                if (isShowPageEmpty) {
                    mView.showPageEmpty()
                    isShowPageEmpty = false
                }
                mWsManager?.sendMessage(getSubscribeParams())
                if (mTimer == null) mTimer = Timer()
                mTimer?.schedule(object : TimerTask() {
                    override fun run() {
                        mWsManager?.sendMessage(getPingParams())
                        LogUtils.d("WsManager-----发送了心跳")
                    }
                }, 0, 1000 * 54)

            }

            override fun onMessage(text: String) {
                super.onMessage(text)
                LogUtils.d("WsManager-----onMessage$text")
                val data = WebUrlProvider.getData<HomeLiveChatBean>(text, HomeLiveChatBean::class.java)
                LogUtils.d("WsManager-----111onMessage${data.toString()}")
                // 发送通知弹幕
                if (data != null) {
                    RxBus.get().post(data)
                    if (mView.isActive()) {
                        mView.addItem(data)
                    }
                    // 告知有红包
                    if (data.gift_type == 4) {
                        mView.onIsShowRedEnvelope(HomeLiveRedMessageBean(4, data.r_id, data.gift_text, data.userName))
                        RxBus.get().post(HomeLiveRedMessageBean(4, data.r_id, data.gift_text, data.userName))
                    }
                }
            }

            override fun onMessage(bytes: ByteString) {
                super.onMessage(bytes)
                LogUtils.d("WsManager-----onMessage$bytes")
            }

            override fun onReconnect() {
                super.onReconnect()
                LogUtils.d("WsManager-----onReconnect")
            }

            override fun onClosing(code: Int, reason: String) {
                super.onClosing(code, reason)
                LogUtils.d("WsManager-----onClosing")
            }

            override fun onClosed(code: Int, reason: String) {
                super.onClosed(code, reason)
                LogUtils.d("WsManager-----onClosed")
            }

            override fun onFailure(t: Throwable?, response: Response?) {
                super.onFailure(t, response)
                LogUtils.d("WsManager-----onFailure$response=$t")
                if (mTimer != null) {
                    mTimer?.cancel()
                    mTimer = null
                }
            }
        }
    }


    /**
     * 获取gif礼物
     */
    private var data: String = "-1"
    fun loadGifData() {
        HomeApi.getGiftList {
            onSuccess {
                if (!TextUtils.isEmpty(it)) {
                    data = it
                    mView.homeGiftList = JsonUtils.fromJson(data, HomeLiveChatGifTitleBean::class.java)
                    try {
                        mView.updateGifList(mView.homeGiftList.xk)
                    } catch (e: Exception) {

                    }

                }
            }
            onFailed {
                ToastUtils.showInfo("获取礼物数据失败")
            }
        }
        MineApi.getUserDiamond {
            onSuccess {
                mView.mDialog.findViewById<TextView>(R.id.tvDiamondTotal)?.text = it.diamond
            }
            onFailed {
                showExpireDialog(mView.requireContext(), it)
            }
        }

    }

    /**
     * 送礼物
     */
    fun sendGift(gift_id: Int, gift_num: Int, gift_name: String, gift_Total: String, icon: String) {
        HomeApi.setGift(UserInfoSp.getUserId(), anchorId, gift_id, gift_num) {
            onSuccess {
                ToastUtils.showSuccess("赠送成功")
                mView.mDialog.dismiss()
                RxBus.get().post(HomeLiveSmallAnimatorBean(gift_id, gift_name, icon, UserInfoSp.getUserId(), UserInfoSp.getUserPhoto().toString(), UserInfoSp.getUserNickName().toString()))
                mWsManager?.sendMessage(getGifParams(1, 0, gift_name, 0f, 1, "", gift_id, icon, "1", UserInfoSp.getUserPhoto().toString()))
                val textView = mView.mDialog.findViewById<TextView>(R.id.tvDiamondTotal)
                if (!TextUtils.isEmpty(textView?.text)) textView?.text = Arith.sub(textView?.text.toString(), gift_Total)
            }
            onFailed {
                ToastUtils.showError("赠送失败")
            }
        }
    }

    /**
     * 初始化最新20条消息
     */
    fun initRecentlyNews(userId: Int, anchorId: Int) {
        HomeApi.getRecentlyNews(userId, anchorId) {
            onSuccess {
                mView.addAll(it)
                if (mView.anchorName != "-1") {
                    mView.addItem(HomeLiveChatBean("", "", 0,
                            "", "", "", "first", "", "", false, 0, "",
                            UserInfoSp.getUserNickName().toString(), 0, 0, mView.anchorName, "0".toFloat(), 0, "", "", 0, "", 0))
                }
            }
        }
    }


    /**
     * 发送红包
     */
    fun sendRedEnvelope(anchorId: Int, userId: Int, amount: Int, num: Int, text: String, password: String) {
        HomeApi.getHomeLiveSendRedEnvelope(anchorId, userId, amount, num, text, password) {
            onSuccess {
                ToastUtils.showSuccess("发送红包成功")
                // 通知直播间有红包
                mWsManager?.sendMessage(getGifParams(4, it.rid, "红包", amount.toFloat(), num, text, 0, "", "", UserInfoSp.getUserPhoto().toString()))
            }
            onFailed {
                if (it.getCode() == 2) {
                    // 余额不足
                    mView.showReChargePopup()
                }
                ToastUtils.showError(it.getMsg())
            }
        }
    }

    /**
     * 抢红包
     */
    fun sendRedReceive(rid: Int) {
        HomeApi.getRedReceive(UserInfoSp.getUserId(), rid) {
            onSuccess {
                mView.showOpenRedContent(it)
            }
            onFailed {
                if (it.getCode() == 2) {
                    // 红包被抢完了
                    mView.showOpenRedOverKnew(JsonUtils.fromJson(it.getDataCode().toString(), HomeLiveRedReceiveBean::class.java))
                } else ToastUtils.showError(it.getMsg())
            }
        }
    }

    /**
     * 获取直播间红包队列
     */
    fun getRoomRed(userId: Int) {
        HomeApi.getRoomRed(userId, anchorId) {
            onSuccess {
                if (it.isNotEmpty()) {
                    val homeLiveRedRoom = it[it.size - 1]
                    mView.onIsShowRedEnvelope(HomeLiveRedMessageBean(4, homeLiveRedRoom.id, "", ""))
                    RxBus.get().post(HomeLiveRedMessageBean(4, homeLiveRedRoom.id, "", ""))
                }
            }
            onFailed {
                //                ToastUtils.showError(it.getMsg())
            }
        }
    }

    /**
     * 验证支付密码
     */
    fun getIsPayPassword() {
        val dialog = MaterialLoadingDialog.Builder(context).show("红包发送中...")
        HomeApi.getIsPayPassword(UserInfoSp.getUserId()) {
            onSuccess {
                dialog.dismiss()
                mView.sendRedEnvelope()
            }
            onFailed {
                dialog.dismiss()
                if (it.getMsg() == "未设置支付密码") {
                    mView.settingPayPasswordPopup()
                }
                ToastUtils.showError(it.getMsg())
            }
        }
    }

    /**
     * 设置支付密码
     */
    fun getSettingPayPassword(newPassword: String, passwordRepeat: String) {
        HomeApi.getSettingPayPassword("", newPassword, passwordRepeat) {
            onSuccess {
                ToastUtils.showSuccess("支付密码设置成功，请您牢记")
            }
            onFailed {
                ToastUtils.showError(it.getMsg())
            }
        }
    }

    /**
     * 礼物动画
     */
    private val giftList = ArrayList<SendGiftBean>()

    @SuppressLint("SetTextI18n")
    fun initGiftAnimator(data: HomeLiveSmallAnimatorBean) {
        val bean = SendGiftBean(data.user_id, data.gift_id, data.user_name, data.user_icon, data.git_name, data.gift_icon, 3000)
        giftList.add(bean)
        mView.rewardLayout.put(bean)

//        when (data.gift_id) {
//
//
//        }
    }

    private fun getSubscribeParams(): String {
        val jsonObject = JSONObject()
        jsonObject.put("room_id", anchorId)
        jsonObject.put("user_id", UserInfoSp.getUserId())
        jsonObject.put("type", TYPE_SUBSCRIBE)
        jsonObject.put("userName", UserInfoSp.getUserNickName())
        return jsonObject.toString()
    }

    private fun getPublishParams(content: String): String {
        val jsonObject = JSONObject()
        jsonObject.put("room_id", anchorId)
        jsonObject.put("user_id", SpUtils.getInt(UserConstant.USER_ID, 0))
        jsonObject.put("type", TYPE_PUBLISH)
        jsonObject.put("userName", UserInfoSp.getUserNickName())
        jsonObject.put("text", content)
        return jsonObject.toString()
    }

    private fun getPingParams(): String {
        val jsonObject = JSONObject()
        jsonObject.put("room_id", anchorId)
        jsonObject.put("user_id", UserInfoSp.getUserId())
        jsonObject.put("type", TYPE_PING)
        jsonObject.put("userName", UserInfoSp.getUserNickName())
        return jsonObject.toString()
    }

    private fun getGifParams(gifType: Int, rId: Int, giftName: String, giftPrice: Float, giftNum: Int, text: String, gift_id: Int, icon: String, vip: String, avatar: String): String {
        val jsonObject = JSONObject()
        jsonObject.put("room_id", anchorId)
        jsonObject.put("user_id", SpUtils.getInt(UserConstant.USER_ID, 0))
        jsonObject.put("type", TYPE_GIFT)
        jsonObject.put("userName", UserInfoSp.getUserNickName())
        // gift_type: 礼物类型 1-普通 2-表白 3-彩票  4-红包
        if (gifType == 4) {
            jsonObject.put("r_id", rId)
            jsonObject.put("gift_text", text)
        } else if (gifType == 1) {
            jsonObject.put("gift_id", gift_id)
            jsonObject.put("icon", icon)
            jsonObject.put("vip", vip)
        }
        jsonObject.put("gift_type", gifType)
        jsonObject.put("gift_name", giftName)
        jsonObject.put("gift_price", giftPrice)
        jsonObject.put("gift_num", giftNum)
        jsonObject.put("avatar", avatar)
        return jsonObject.toString()
    }
}
