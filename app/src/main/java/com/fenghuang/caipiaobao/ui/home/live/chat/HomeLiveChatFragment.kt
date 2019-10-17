package com.fenghuang.caipiaobao.ui.home.live.chat

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import androidx.viewpager.widget.ViewPager
import com.fenghuang.baselib.base.recycler.BaseMultiRecyclerFragment
import com.fenghuang.baselib.utils.*
import com.fenghuang.baselib.widget.dialog.MaterialBottomDialog
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.constant.IntentConstant
import com.fenghuang.caipiaobao.function.isEmpty
import com.fenghuang.caipiaobao.function.isNotEmpty
import com.fenghuang.caipiaobao.ui.home.data.*
import com.fenghuang.caipiaobao.ui.widget.ChatGifTabView
import com.fenghuang.caipiaobao.ui.widget.popup.OpenRedEnvelopePopup
import com.fenghuang.caipiaobao.ui.widget.popup.ReChargePopup
import com.fenghuang.caipiaobao.ui.widget.popup.RedEnvelopePopup
import com.fenghuang.caipiaobao.ui.widget.popup.SettingPayPasswordPopup
import com.fenghuang.caipiaobao.widget.pagegridview.GridViewAdapter
import com.fenghuang.caipiaobao.widget.pagegridview.ViewPagerAdapter
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import kotlinx.android.synthetic.main.fragment_live_chat.*
import kotlinx.android.synthetic.main.include_comment_layout.*
import kotlin.math.ceil


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
    private lateinit var mDialogViewPager: ViewPager
    // 礼物窗口的圆点
    private lateinit var mGifDot: LinearLayout
    private lateinit var mNetWorkReceiver: NetWorkChangReceiver
    private var mTotal: Int = 0
    private var mRedNumber: Int = 0
    private var mRedContent: String = ""
    private var mPassword: String = ""
    private lateinit var mOpenRedPopup: OpenRedEnvelopePopup

    override fun getContentResID() = R.layout.fragment_live_chat

    override fun attachView() = mPresenter.attachView(this)


    override fun isRegisterRxBus() = true
    override fun isEnableLoadMore() = false
    override fun isEnableRefresh() = false
    override fun attachPresenter() = HomeLiveCharPresenter(getPageActivity(), arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
            ?: 0)

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

    /**
     * 接收红包, 礼物通知
     */
//    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onIsShowRedEnvelope(eventBean: HomeLiveRedMessageBean) {
        if (eventBean.gift_type == 4) {
            // 通知有可抢的红包
            setVisible(ivEnvelopeTips)
            ivEnvelopeTips.setOnClickListener {
                mOpenRedPopup = OpenRedEnvelopePopup(getPageActivity())
                mOpenRedPopup.setOnOpenClickListener {
                    mPresenter.sendRedReceive(eventBean.rid)
                }
                mOpenRedPopup.showAtLocation(rootView, Gravity.CENTER, 0, 0)
            }
        }

    }

    /**
     * 抢到红包后的回调
     */
    fun showOpenRedContent(it: HomeLiveRedReceiveBean) {
        setGone(ivEnvelopeTips)
        mPresenter.getRoomRed(IntentConstant.USER_ID)
        mOpenRedPopup.setRedContent(it.send_text)
        mOpenRedPopup.setRedMoney(it.count.toString())
        mOpenRedPopup.setRedUserName(it.send_user_name)
        if (null != it.send_user_avatar) mOpenRedPopup.setRedLogo(it.send_user_avatar)
        mOpenRedPopup.isShowRedLogo(true)

    }

    /**
     * 提示该红包已抢完
     */
    fun showOpenRedOverKnew() {
        setGone(ivEnvelopeTips)
        mPresenter.getRoomRed(IntentConstant.USER_ID)
        mOpenRedPopup.showRedOver()
    }

    override fun initEvent() {
        super.initEvent()
        rootView.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            val layoutParams = chatEditText.layoutParams as RelativeLayout.LayoutParams
            // 只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
            if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > mKeyHeight)) {
                setGone(ivGift)
                setGone(ivRedEnvelope)
                setGone(ivRecharge)
                layoutParams.marginEnd = ViewUtils.dp2px(0)
            } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > mKeyHeight)) {
                setVisible(ivGift)
                setVisible(ivRedEnvelope)
                setVisible(ivRecharge)
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
        // 获取列表的滑动事件，控制一键到底部
        getRecyclerView()?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == SCROLL_STATE_IDLE || newState == SCROLL_STATE_DRAGGING) {
                    setVisibility(ivChatMore, !isStayBottom())
                }
            }
        })
        // 输入框的发送按钮事件
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
        // 红包
        ivRedEnvelope.setOnClickListener {
            val popup = RedEnvelopePopup(getPageActivity())
            popup.width = ViewUtils.dp2px(280)
            popup.showAtLocation(rootView, Gravity.CENTER, 0, 0)
            popup.setOnSendClickListener { total, redNumber, redContent, password ->
                if (isNotEmpty(password)) {
                    popup.dismiss()
                    mTotal = total.toInt()
                    mRedNumber = redNumber.toInt()
                    mRedContent = redContent
                    mPassword = password
                    mPresenter.getIsPayPassword()
                } else ToastUtils.showToast(getString(R.string.live_chat_red_hin_password))
            }
        }
    }

    /**
     * 发送红包
     */
    fun sendRedEnvelope() {
        mPresenter.sendRedEnvelope(arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
                ?: 0, IntentConstant.USER_ID, mTotal, mRedNumber, mRedContent, mPassword)
    }

    /**
     * 初始化礼物数据dialog
     */
    private fun initGifBottom() {
        mDialog = MaterialBottomDialog(getPageActivity())
        mDialog.setContentView(R.layout.dialog_chat_bottom_gif)
        val chatGifTabView = mDialog.findViewById<ChatGifTabView>(R.id.chatGifTabView)
        mDialogViewPager = mDialog.findViewById<ViewPager>(R.id.viewPager)!!
        mGifDot = mDialog.findViewById<LinearLayout>(R.id.gifDot)!!
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

    // 礼物数据源
    fun updateGifList(listData: ArrayList<HomeLiveChatGifBean>) {
        val pageSize = 8
        var curIndex = 0
        var inflater = LayoutInflater.from(getPageActivity())
        // 总的页数=总数/每页数量，并取整
        val pageCount = ceil(listData.size * 1.0 / pageSize).toInt()
        val gridViewList = arrayOfNulls<GridViewAdapter>(pageCount)
        // 页面集合
        var pagerList = arrayListOf<View>()
        for (i in 0 until pageCount) {
            val gridView = inflater.inflate(R.layout.bottom_vp_gridview, mDialogViewPager, false) as GridView
            val gridViewAdapter = GridViewAdapter(getPageActivity(), listData, i)
            gridView.adapter = gridViewAdapter
            gridViewList[i] = gridViewAdapter
            gridView.setOnItemClickListener { _, _, _, id ->
                listData.forEachIndexed { index, homeLiveChatGifBean ->
                    if (index == id.toInt()) {
                        homeLiveChatGifBean.isSelect = !homeLiveChatGifBean.isSelect
                    } else {
                        homeLiveChatGifBean.isSelect = false
                    }
                }
                gridViewAdapter.notifyDataSetChanged()
            }
            pagerList.add(gridView)
        }
        mDialogViewPager.adapter = ViewPagerAdapter(pagerList)
        // 设置圆点
        for (i in 0 until pageCount) {
            mGifDot.addView(inflater.inflate(R.layout.layout_gif_dot, null))
        }
        mGifDot.getChildAt(0).findViewById<View>(R.id.viewDot).setBackgroundResource(R.drawable.shape_emoji_indicator_select)
        mDialogViewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                gridViewList[0]?.notifyDataSetChanged()
                gridViewList[1]?.notifyDataSetChanged()
                gridViewList[2]?.notifyDataSetChanged()
                mGifDot.getChildAt(curIndex).findViewById<View>(R.id.viewDot).setBackgroundResource(R.drawable.shape_emoji_indicator_normal)
                mGifDot.getChildAt(position).findViewById<View>(R.id.viewDot).setBackgroundResource(R.drawable.shape_emoji_indicator_select)
                curIndex = position
            }
        })
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


    /**
     * 设置支付密码
     */
    fun settingPayPasswordPopup() {
        val passwordPopup = SettingPayPasswordPopup(getPageActivity())
        passwordPopup.showAtLocation(rootView, Gravity.CENTER, 0, 0)
        passwordPopup.setOnSendClickListener { password, passwordOk ->
            if (password != passwordOk) {
                ToastUtils.showToast("密码不一致，请重新输入")
            } else {
                passwordPopup.dismiss()
                mPresenter.getSettingPayPassword(password, passwordOk)
            }
        }
    }

    /**
     * 显示余额不足
     */
    fun showReChargePopup() {
        val reChargePopup = ReChargePopup(getPageActivity())
        reChargePopup.setOnSendClickListener {
            // TODO 跳转充值
        }
        reChargePopup.showAtLocation(rootView, Gravity.CENTER, 0, 0)

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

    companion object {
        fun newInstance(anchorId: Int): HomeLiveChatFragment {
            val fragment = HomeLiveChatFragment()
            val bundle = Bundle()
            bundle.putInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID, anchorId)
            fragment.arguments = bundle
            return fragment
        }
    }
}