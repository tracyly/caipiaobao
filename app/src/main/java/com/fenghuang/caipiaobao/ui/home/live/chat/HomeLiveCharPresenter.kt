package com.fenghuang.caipiaobao.ui.home.live.chat

import android.content.Context
import com.fenghuang.baselib.base.recycler.BaseRecyclerPresenter
import com.fenghuang.caipiaobao.data.api.WebUrlProvider
import com.fenghuang.caipiaobao.socket.WsManager
import com.fenghuang.caipiaobao.socket.listener.WsStatusListener
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatBean
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
class HomeLiveCharPresenter(val context: Context) : BaseRecyclerPresenter<HomeLiveChatFragment>() {

    private var mWsManager: WsManager? = null
    private var mTimer: Timer? = null
    private var mWsStatusListener: WsStatusListener? = null
    private var jsonObject = JSONObject()
    private var isShowPageEmpty: Boolean = true

    override fun loadData(page: Int) {
        jsonObject.put("room_id", "100001")
        jsonObject.put("user_id", "6")
        initStatusListener()
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
        jsonObject.put("type", "publish")
        jsonObject.put("room_id", "100001")
        jsonObject.put("user_id", "6")
        jsonObject.put("userName", "指法大仙")
        jsonObject.put("text", content)
        mWsManager?.sendMessage(jsonObject.toString())
    }


    private fun initStatusListener() {

        mWsStatusListener = object : WsStatusListener() {
            override fun onOpen(response: Response) {
                super.onOpen(response)
                LogUtils.d("WsManager-----onOpen response=$response")
                jsonObject.put("type", "subscribe")
                if (isShowPageEmpty) {
                    mView.showPageEmpty()
                    isShowPageEmpty = false
                }
                mWsManager?.sendMessage(jsonObject.toString())
                if (mTimer == null) mTimer = Timer()
                mTimer?.schedule(object : TimerTask() {
                    override fun run() {
                        jsonObject.put("type", "ping")
                        mWsManager?.sendMessage(jsonObject.toString())
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
}
