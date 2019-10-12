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
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.fenghuang.baselib.base.recycler.BaseMultiRecyclerFragment
import com.fenghuang.baselib.utils.*
import com.fenghuang.baselib.widget.dialog.MaterialBottomDialog
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.function.isEmpty
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatBean
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatGifBean
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatPostEvenBean
import com.fenghuang.caipiaobao.ui.widget.ChatGifTabView
import com.fenghuang.caipiaobao.widget.pagegridview.GridPager
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

    // 软件盘弹起后所占高度阀值
    private var mKeyHeight = 0
    // 屏幕高度
    private var mScreenHeight = 0
    private lateinit var mDialog: MaterialBottomDialog
    // 依次为：普通礼物，表白礼物，彩票礼物
    private lateinit var mGridPager: GridPager

    private lateinit var mNetWorkReceiver: NetWorkChangReceiver

    override fun getContentResID() = R.layout.fragment_live_chat

    override fun attachView() = mPresenter.attachView(this)

    override fun onResume() {
        super.onResume()
        StatusBarUtils.setStatusBarForegroundColor(activity, false)
    }

    override fun isRegisterRxBus() = true
    override fun isEnableLoadMore() = false
    override fun isEnableRefresh() = false
    override fun attachPresenter() = HomeLiveCharPresenter(getPageActivity())
    override fun initPageView() {
        super.initPageView()
        mScreenHeight = getPageActivity().windowManager.defaultDisplay.height
        mKeyHeight = mScreenHeight / 3
        SoftHideKeyBoardUtil().init(getPageActivity())
        register(HomeLiveChatBean::class.java, HomeLiveChatHolder())
        mEmoticonKeyboard.setupWithEditText(chatEditText)
        initGifBottom()
    }


    override fun initData() {
        super.initData()
        mNetWorkReceiver = NetWorkChangReceiver()
        val filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        getPageActivity().registerReceiver(mNetWorkReceiver, filter)
        mPresenter.loadGifData()
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
        mDialog.cancel()
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
    fun onSendDanmaku(eventBean: HomeLiveChatPostEvenBean) {
        mPresenter.sendMessage(eventBean.content)
    }

    override fun initEvent() {
        super.initEvent()
        rootView.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            val layoutParams = chatEditText.layoutParams as RelativeLayout.LayoutParams
            //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
            if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > mKeyHeight)) {
                layoutParams.marginEnd = ViewUtils.dp2px(100)
            } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > mKeyHeight)) {
                layoutParams.marginEnd = ViewUtils.dp2px(240)
            }
            chatEditText.layoutParams = layoutParams
        }

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
        // 礼物
        ivGift.setOnClickListener {
            mDialog.show()
        }
    }

    /**
     * 初始化礼物数据dialog
     */
    private fun initGifBottom() {
        mDialog = MaterialBottomDialog(getPageActivity())
        mDialog.setContentView(R.layout.dialog_chat_bottom_gif)
        val chatGifTabView = mDialog.findViewById<ChatGifTabView>(R.id.chatGifTabView)
        mGridPager = mDialog.findViewById<GridPager>(R.id.gridPager)!!
        chatGifTabView?.setChatGifTab()
        chatGifTabView?.setOnSelectListener {
            when (it) {
                0 -> {
//                    mPresenter.loadGifData()
                }
                1 -> {
//                    mPresenter.loadGifData()
                }
                2 -> {
//                    mPresenter.loadGifData()
                }
            }
        }
    }

    fun updateGifList(listData: ArrayList<HomeLiveChatGifBean>) {
        mGridPager.setDataAllCount(listData.size)
                .setViewPageHeight(150)
                .setItemBindDataListener { imageView, tvTitle, tvTitleHint, linearLayout, position ->
                    imageView.setImageResource(listData[position].gifUrl)
                    tvTitle.text = listData[position].title
                    tvTitleHint.text = listData[position].gold.toString()
                    tvTitleHint.visibility = VISIBLE
                    if (listData[position].isSelect) {
                        linearLayout.background = getDrawable(R.drawable.shape_home_live_chat_gif_selected_bg)
                    } else {
                        linearLayout.background = getDrawable(R.drawable.shape_home_live_chat_gif_normal_bg)
                    }
                }
                .setGridItemClickListener { position, adapter ->
                    listData.forEach {
                        it.isSelect = false
                    }
                    listData[position].isSelect = true
                    adapter.notifyDataSetChanged()
                }.show()
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