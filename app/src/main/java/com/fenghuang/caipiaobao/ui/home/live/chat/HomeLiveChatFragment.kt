package com.fenghuang.caipiaobao.ui.home.live.chat

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.view.View.VISIBLE
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.fenghuang.baselib.base.recycler.BaseMultiRecyclerFragment
import com.fenghuang.baselib.utils.LogUtils
import com.fenghuang.baselib.utils.NetWorkUtils
import com.fenghuang.baselib.utils.SoftHideKeyBoardUtil
import com.fenghuang.baselib.utils.SoftInputUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.function.isEmpty
import com.fenghuang.caipiaobao.ui.home.live.data.LiveChatBean
import com.fenghuang.caipiaobao.ui.home.live.data.LiveChatPostEvenBean
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import kotlinx.android.synthetic.main.fragment_live_chat.*
import kotlinx.android.synthetic.main.include_comment_layout.*


/**
 *  author : Peter
 *  date   : 2019/8/29 17:31
 *  desc   : 聊天室
 */
@Suppress("DEPRECATED_IDENTITY_EQUALS")
class HomeLiveChatFragment : BaseMultiRecyclerFragment<HomeLiveCharPresenter>() {

    private lateinit var mNetWorkReceiver: NetWorkChangReceiver

    override fun getContentResID() = R.layout.fragment_live_chat

    override fun attachView() = mPresenter.attachView(this)

    override fun isRegisterRxBus() = true
    override fun isEnableLoadMore() = false
    override fun isEnableRefresh() = false
    override fun attachPresenter() = HomeLiveCharPresenter(getPageActivity())
    override fun initPageView() {
        super.initPageView()
        SoftHideKeyBoardUtil().init(getPageActivity())
        register(LiveChatBean::class.java, HomeLiveChatHolder())
        mEmoticonKeyboard.setupWithEditText(chatEditText)
    }


    override fun initData() {
        super.initData()
        mNetWorkReceiver = NetWorkChangReceiver()
        val filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        getPageActivity().registerReceiver(mNetWorkReceiver, filter)
    }

    override fun <T> addItem(data: T?) {
        super.addItem(data)
        scrollToBottom()
    }

    /**
     * 是否停留在列表底部
     */
    private fun isStayBottom(): Boolean {
        val layoutManager = getRecyclerView()?.layoutManager as? LinearLayoutManager
        return layoutManager?.findLastVisibleItemPosition() == mAdapter.itemCount - 1
    }

    /**
     * 滑动到最底部
     */
    private fun scrollToBottom() {
        if (!isStayBottom()) {
            smoothScrollToPosition((getRecyclerView()?.layoutManager as? LinearLayoutManager)?.itemCount
                    ?: 0)
        }
    }

    override fun showPageEmpty(msg: String?) {
        super.showPageEmpty(msg)
        if (isEmptyRecycler()) {
            setGoneEmpty()
            setVisible(liveChatEmptyContainer)
        }
    }

    override fun showPageContent() {
        super.showPageContent()
        setGone(liveChatEmptyContainer)
    }


    override fun onDestroy() {
        super.onDestroy()
        getPageActivity().unregisterReceiver(mNetWorkReceiver)
        mPresenter.stopConnect()
    }

    /**
     * 显示表情与隐藏表情
     */
    private fun toggleKeyboard() {
        if (mEmoticonKeyboard.visibility === VISIBLE) {
            SoftInputUtils.showSoftInput(chatEditText)
            setGone(mEmoticonKeyboard)
        } else {
            SoftInputUtils.hideSoftInput(getPageActivity())
            setVisible(mEmoticonKeyboard)
        }
    }

    /**
     * 接收横屏发送弹幕聊天
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onSendDanmaku(eventBean: LiveChatPostEvenBean) {
        mPresenter.sendMessage(eventBean.content)
    }

    override fun initEvent() {
        super.initEvent()
        chatEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                setGone(mEmoticonKeyboard)
            }
        }
//        ivChatEmoji.setOnClickListener {
//            toggleKeyboard()
//        }
        ivChatMore.setOnClickListener {
            scrollToBottom()
        }
        getRecyclerView()?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == SCROLL_STATE_IDLE || newState == SCROLL_STATE_DRAGGING) {
                    setVisibility(ivChatMore, !isStayBottom())
                }
            }
        })

        chatEditText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                sendMessage()
                return@OnKeyListener true
            }
            false
        })
//        chatEditText.setOnClickListener {
//            if (mEmoticonKeyboard.visibility === VISIBLE) setGone(mEmoticonKeyboard)
//        }
    }


    private fun sendMessage() {
        val content = chatEditText.text.trim().toString()
        if (isEmpty(content)) {
            showToast(getString(R.string.live_chat_empty))
        } else {
            chatEditText.setText("")
            if (mEmoticonKeyboard.visibility === VISIBLE) setGone(mEmoticonKeyboard)
            mPresenter.sendMessage(content)
        }
    }

    inner class NetWorkChangReceiver : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onReceive(context: Context?, intent: Intent?) {
            if (NetWorkUtils.isNetworkConnected()) {
                mPresenter.startWebSocketConnect()
            } else {
                LogUtils.d("WsManager----------网络不可用")
                mPresenter.stopConnect()
            }
        }
    }
}