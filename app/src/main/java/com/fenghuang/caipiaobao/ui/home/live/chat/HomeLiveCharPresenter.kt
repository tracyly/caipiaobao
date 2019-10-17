package com.fenghuang.caipiaobao.ui.home.live.chat

import android.content.Context
import com.fenghuang.baselib.base.recycler.BaseRecyclerPresenter
import com.fenghuang.baselib.utils.SpUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.baselib.widget.dialog.MaterialLoadingDialog
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.constant.UserConstant
import com.fenghuang.caipiaobao.data.api.WebUrlProvider
import com.fenghuang.caipiaobao.socket.WsManager
import com.fenghuang.caipiaobao.socket.listener.WsStatusListener
import com.fenghuang.caipiaobao.ui.home.data.HomeApi
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatBean
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatGifBean
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveRedMessageBean
import com.hwangjr.rxbus.RxBus
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
        getRoomRed(SpUtils.getInt(UserConstant.USER_ID, 0))
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
                // 发送通知弹幕
                RxBus.get().post(data)
                if (data != null && mView.isActive()) {
                    mView.addItem(data)
                }
                // 告知有红包
                if (data?.gift_type == 4) {
                    mView.onIsShowRedEnvelope(HomeLiveRedMessageBean(4, data.r_id))
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
    fun loadGifData() {
        var listData = arrayListOf<HomeLiveChatGifBean>()
        for (i in 0..20) {
            listData.add(HomeLiveChatGifBean(R.mipmap.ic_home_live_notice_1, "礼物$i", i, false))
        }
        mView.updateGifList(listData)
    }

    /**
     * 发送红包
     */
    fun sendRedEnvelope(anchorId: Int, userId: Int, amount: Int, num: Int, text: String, password: String) {
        HomeApi.getHomeLiveSendRedEnvelope(anchorId, userId, amount, num, text, password) {
            onSuccess {
                ToastUtils.showSuccess("发送红包成功")
                // 通知直播间有红包
                mWsManager?.sendMessage(getGifParams(4, it.rid, "", amount.toFloat(), num))

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
        HomeApi.getRedReceive(SpUtils.getInt(UserConstant.USER_ID, 0), rid) {
            onSuccess {
                mView.showOpenRedContent(it)
            }
            onFailed {
                if (it.getCode() == 2) {
                    // 红包被抢完了
                    mView.showOpenRedOverKnew()
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
                    mView.onIsShowRedEnvelope(HomeLiveRedMessageBean(4, homeLiveRedRoom.id))
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
        HomeApi.getIsPayPassword(SpUtils.getInt(UserConstant.USER_ID, 0)) {
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

    private fun getSubscribeParams(): String {
        var jsonObject = JSONObject()
        jsonObject.put("room_id", anchorId)
        jsonObject.put("user_id", SpUtils.getInt(UserConstant.USER_ID, 0))
        jsonObject.put("type", TYPE_SUBSCRIBE)
        jsonObject.put("userName", "指法大仙")
        return jsonObject.toString()
    }

    private fun getPublishParams(content: String): String {
        var jsonObject = JSONObject()
        jsonObject.put("room_id", anchorId)
        jsonObject.put("user_id", SpUtils.getInt(UserConstant.USER_ID, 0))
        jsonObject.put("type", TYPE_PUBLISH)
        jsonObject.put("userName", "指法大仙")
        jsonObject.put("text", content)
        return jsonObject.toString()
    }

    private fun getPingParams(): String {
        var jsonObject = JSONObject()
        jsonObject.put("room_id", anchorId)
        jsonObject.put("user_id", SpUtils.getInt(UserConstant.USER_ID, 0))
        jsonObject.put("type", TYPE_PING)
        jsonObject.put("userName", "指法大仙")
        return jsonObject.toString()
    }

    private fun getGifParams(gifType: Int, rId: Int, giftName: String, giftPrice: Float, giftNum: Int): String {
        var jsonObject = JSONObject()
        jsonObject.put("room_id", anchorId)
        jsonObject.put("user_id", SpUtils.getInt(UserConstant.USER_ID, 0))
        jsonObject.put("type", TYPE_GIFT)
        jsonObject.put("userName", "指法大仙")
        // gift_type: 礼物类型 1-普通 2-表白 3-彩票  4-红包
        if (gifType == 4) {
            jsonObject.put("r_id", rId)
        }
        jsonObject.put("gift_type", gifType)
        jsonObject.put("gift_name", giftName)
        jsonObject.put("gift_price", giftPrice)
        jsonObject.put("gift_num", giftNum)
        return jsonObject.toString()
    }
}
