package com.fenghuang.caipiaobao.ui.home.live.chat

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import androidx.viewpager.widget.ViewPager
import com.fenghuang.baselib.base.recycler.BaseMultiRecyclerFragment
import com.fenghuang.baselib.utils.*
import com.fenghuang.baselib.widget.dialog.MaterialBottomDialog
import com.fenghuang.baselib.widget.round.RoundTextView
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.constant.IntentConstant
import com.fenghuang.caipiaobao.constant.UserConstant
import com.fenghuang.caipiaobao.function.isEmpty
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.data.*
import com.fenghuang.caipiaobao.ui.widget.ChatGifTabView
import com.fenghuang.caipiaobao.ui.widget.popup.OpenRedEnvelopeDialog
import com.fenghuang.caipiaobao.ui.widget.popup.ReChargePopup
import com.fenghuang.caipiaobao.ui.widget.popup.RedEnvelopePopup
import com.fenghuang.caipiaobao.ui.widget.popup.SettingPayPasswordPopup
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.dialog.GiftSendDialog
import com.fenghuang.caipiaobao.widget.gift.AnimUtils
import com.fenghuang.caipiaobao.widget.gift.NumAnim
import com.fenghuang.caipiaobao.widget.gift.RewardLayout
import com.fenghuang.caipiaobao.widget.gift.SendGiftBean
import com.fenghuang.caipiaobao.widget.pagegridview.GridViewAdapter
import com.fenghuang.caipiaobao.widget.pagegridview.ViewPagerAdapter
import com.fenghuang.caipiaobao.widget.sideslipdeletelayout.ResolveConflictViewPager
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

    //主播名
    var anchorName = "-1"

    // 软件盘弹起后所占高度阀值
    private var mKeyHeight = 0
    // 屏幕高度
    private var mScreenHeight = 0
    lateinit var mDialog: MaterialBottomDialog
    private lateinit var mDialogViewPager: ResolveConflictViewPager
    // 礼物窗口的圆点
    private lateinit var mGifDot: LinearLayout
    private lateinit var mNetWorkReceiver: NetWorkChangReceiver
    private var mTotal: Int = 0
    private var mRedNumber: Int = 0
    private var mRedContent: String = ""
    private var mPassword: String = ""
    private lateinit var mOpenRedPopup: OpenRedEnvelopeDialog

    lateinit var homeGiftList: HomeLiveChatGifTitleBean

    override fun getContentResID() = R.layout.fragment_live_chat

    override fun attachView() = mPresenter.attachView(this)


    override fun isRegisterRxBus() = true
    override fun isEnableLoadMore() = false
    override fun isEnableRefresh() = false

    override fun attachPresenter() = HomeLiveCharPresenter(getPageActivity(), arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
            ?: 0)

    override fun initPageView() {
        super.initPageView()
        getPageActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        mScreenHeight = ViewUtils.getScreenHeight()
        mKeyHeight = mScreenHeight / 3
        SoftHideKeyBoardUtil().init(getPageActivity())
        register(HomeLiveChatBean::class.java, HomeLiveChatHolder())
        mEmoticonKeyboard.setupWithEditText(chatEditText)
        initRewardAnimator()
    }


    override fun initData() {
        super.initData()
        anchorName = arguments?.getString(IntentConstant.HOME_LIVE_ANCHOR_NAME).toString()
        mPresenter.initRecentlyNews(UserInfoSp.getUserId(), arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
                ?: 0)
        mNetWorkReceiver = NetWorkChangReceiver()
        val filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        getPageActivity().registerReceiver(mNetWorkReceiver, filter)
        initGifBottom()
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
//        super.showPageEmpty(msg)
//        if (isEmptyRecycler()) {
//            setGoneEmpty()
//            setVisible(liveChatEmptyContainer)
//        }
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
        SoftInputUtils.hideSoftInput(getPageActivity())
        mAdapter.notifyDataSetChanged()
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
                mOpenRedPopup = OpenRedEnvelopeDialog(getPageActivity())
                mOpenRedPopup.setRedTitle("恭喜发财，大吉大利")
                mOpenRedPopup.setOnOpenClickListener {
                    mPresenter.sendRedReceive(eventBean.rid)
                }
                mOpenRedPopup.show()
            }
        }

    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun closeSoftKeyBord(eventBean: HomeLiveColseSoftKeyBord) {
        hideSoftInput()
    }

    /**
     * 抢到红包后的回调
     */
    fun showOpenRedContent(it: HomeLiveRedReceiveBean) {
        setGone(ivEnvelopeTips)
        mPresenter.getRoomRed(UserInfoSp.getUserId())
        mOpenRedPopup.setRedContent(it.send_text)
        mOpenRedPopup.setRedMoney(it.count.toString())
        mOpenRedPopup.setRedUserName(it.send_user_name)
        mOpenRedPopup.setRedLogo(it.send_user_avatar)
        mOpenRedPopup.isShowRedLogo(true)
    }

    /**
     * 提示该红包已抢完
     */
    fun showOpenRedOverKnew(bean: HomeLiveRedReceiveBean) {
        setGone(ivEnvelopeTips)
        mPresenter.getRoomRed(SpUtils.getInt(UserConstant.USER_ID, 0))
        mOpenRedPopup.showRedOver(bean)
    }

    override fun initEvent() {
        super.initEvent()
        rootView.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            val layoutParams = chatEditText.layoutParams as LinearLayout.LayoutParams
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
                layoutParams.marginEnd = ViewUtils.dp2px(30)
            }
            chatEditText.layoutParams = layoutParams
        }

        chatEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                setGone(mEmoticonKeyboard)
            }
        }
        ivRecharge.setOnClickListener {
            toggleKeyboard()
        }
//        ivChatMore.setOnClickListener {
//            scrollToBottom()
//        }
        // 获取列表的滑动事件，控制一键到底部
        getRecyclerView()?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == SCROLL_STATE_IDLE || newState == SCROLL_STATE_DRAGGING) {
//                    setVisibility(ivChatMore, !isStayBottom())
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
            popup.height = ViewUtils.dp2px(290)
            popup.showAtLocation(rootView, Gravity.CENTER, 0, 0)
            popup.setOnSendClickListener { total, redNumber, redContent ->
                //                if (isNotEmpty(password)) {
                popup.dismiss()
                mTotal = total.toInt()
                mRedNumber = redNumber.toInt()
                mRedContent = redContent
//                    mPassword = password
//                    mPresenter.getIsPayPassword()
//                } else ToastUtils.showToast(getString(R.string.live_chat_red_hin_password))
            }
        }
    }

    /**
     * 发送红包
     */
    fun sendRedEnvelope() {
        mPresenter.sendRedEnvelope(arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
                ?: 0, SpUtils.getInt(UserConstant.USER_ID, 0), mTotal, mRedNumber, mRedContent, mPassword)
    }

    /**
     * 初始化礼物数据dialog
     */
    private fun initGifBottom() {
        mPresenter.loadGifData()
        mDialog = MaterialBottomDialog(getPageActivity())
        mDialog.setContentView(R.layout.dialog_chat_bottom_gif)
        mDialog.delegate.findViewById<View>(R.id.design_bottom_sheet)?.setBackgroundColor(Color.TRANSPARENT)
        val chatGifTabView = mDialog.findViewById<ChatGifTabView>(R.id.chatGifTabView)
        mDialogViewPager = mDialog.findViewById(R.id.viewPager)!!
        mGifDot = mDialog.findViewById(R.id.gifDot)!!
        chatGifTabView?.setChatGifTab()
        chatGifTabView?.setOnSelectListener {
            when (it) {
                0 -> {
                    updateGifList(homeGiftList.xk)
                }
                1 -> {
                    updateGifList(homeGiftList.lm)
                }
                2 -> {
                    updateGifList(homeGiftList.zg)
                }
            }
        }
    }

    // 礼物数据源
    fun updateGifList(listData: ArrayList<HomeLiveChatGifBean>) {
        val pageSize = 8
        var selectedItem: HomeLiveChatGifBean? = null
        val inflater = LayoutInflater.from(getPageActivity())
        // 总的页数=总数/每页数量，并取整
        val pageCount = ceil(listData.size * 1.0 / pageSize).toInt()
        val gridViewList = arrayOfNulls<GridViewAdapter>(pageCount)
        // 页面集合
        val pagerList = arrayListOf<View>()
        for (i in 0 until pageCount) {
            val gridView = inflater.inflate(R.layout.bottom_vp_gridview, mDialogViewPager, false) as GridView
            val gridViewAdapter = GridViewAdapter(getPageActivity(), listData, i)
            gridView.adapter = gridViewAdapter
            gridViewList[i] = gridViewAdapter
            gridView.setOnItemClickListener { _, _, _, id ->
                listData.forEachIndexed { index, homeLiveChatGifBean ->
                    if (index == id.toInt()) {
                        homeLiveChatGifBean.isSelect = !homeLiveChatGifBean.isSelect
                        selectedItem = homeLiveChatGifBean
                    } else {
                        homeLiveChatGifBean.isSelect = false
                    }
                }
                gridViewAdapter.notifyDataSetChanged()
            }
            pagerList.add(gridView)
        }
        mDialogViewPager.adapter = ViewPagerAdapter(pagerList)
        mDialog.findViewById<RoundTextView>(R.id.btSendGift)?.setOnClickListener {
            if (selectedItem != null) {
                if (!UserInfoSp.getSendGiftTips()) {
                    val giftSendDialog = GiftSendDialog(getPageActivity(), "是否需要花费" + selectedItem?.amount + "送主播" + " '" + selectedItem?.name + "'")
                    giftSendDialog.setConfirmClickListener {
                        //送礼物
                        mPresenter.sendGift(selectedItem?.id!!, 1, selectedItem?.name.toString(), selectedItem?.amount!!, selectedItem?.icon.toString())
                    }
                    giftSendDialog.show()
                } else {
                    //送礼物
                    mPresenter.sendGift(selectedItem?.id!!, 1, selectedItem?.name.toString(), selectedItem?.amount!!, selectedItem?.icon.toString())
                }
            } else {
                ToastUtils.showNormal("请选择要赠送的礼物")
            }
        }
        // 设置圆点
        for (i in 0 until pageCount) {
            mGifDot.addView(inflater.inflate(R.layout.layout_gif_dot, null))
        }
        mGifDot.getChildAt(0).findViewById<View>(R.id.viewDot).setBackgroundResource(R.drawable.shape_emoji_indicator_select)
        mDialogViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
            override fun onPageSelected(position: Int) {
                gridViewList[0]?.notifyDataSetChanged()
                gridViewList[1]?.notifyDataSetChanged()
                gridViewList[2]?.notifyDataSetChanged()
//                mGifDot.getChildAt(curIndex).findViewById<View>(R.id.viewDot).setBackgroundResource(R.drawable.shape_emoji_indicator_normal)
//                mGifDot.getChildAt(position).findViewById<View>(R.id.viewDot).setBackgroundResource(R.drawable.shape_emoji_indicator_select)
//                curIndex = position
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

    /**
     * 接收小动画
     * 小礼物 炫酷：齐天大圣      浪漫： love  求带走   尊贵：至尊宝。臭鸡蛋
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onGetGiftSmall(data: HomeLiveSmallAnimatorBean) {
        if (data.gift_id == 18 || data.gift_id == 23 || data.gift_id == 24 || data.gift_id == 30 || data.gift_id == 31) {
            mPresenter.initGiftAnimator(data)
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
                ImageManager.loadImage(bean.userPhoto, userPhoto!!)
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
                        textView.visibility = VISIBLE
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
        fun newInstance(anchorId: Int, anchorName: String): HomeLiveChatFragment {
            val fragment = HomeLiveChatFragment()
            val bundle = Bundle()
            bundle.putInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID, anchorId)
            bundle.putString(IntentConstant.HOME_LIVE_ANCHOR_NAME, anchorName)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onPause() {
        super.onPause()
        if (rewardLayout != null) {
            rewardLayout.onPause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (rewardLayout != null) {
            rewardLayout.onResume()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (rewardLayout != null) {
            rewardLayout.onDestroy()
        }
    }

}