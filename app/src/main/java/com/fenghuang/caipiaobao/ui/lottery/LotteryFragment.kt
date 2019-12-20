package com.fenghuang.caipiaobao.ui.lottery

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.fenghuang.baselib.base.adapter.BaseFragmentPageAdapter
import com.fenghuang.baselib.base.fragment.BaseFragment
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.home.data.HomeClickVideo
import com.fenghuang.caipiaobao.ui.home.data.HomeGameListResponse
import com.fenghuang.caipiaobao.ui.home.live.liveroom.HomeLiveDetailsFragment
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryCodeNewResponse
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryGetExpert
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryTypeResponse
import com.fenghuang.caipiaobao.utils.LaunchUtils
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.lighter.Lighter
import com.fenghuang.caipiaobao.widget.lighter.parameter.Direction
import com.fenghuang.caipiaobao.widget.lighter.parameter.LighterParameter
import com.fenghuang.caipiaobao.widget.lighter.parameter.MarginOffset
import com.fenghuang.caipiaobao.widget.lighter.shape.CircleShape
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import kotlinx.android.synthetic.main.fragment_lottery.*
import java.util.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/3- 15:21
 * @ Describe  开奖页面
 *
 */

class LotteryFragment : BaseMvpFragment<LotteryPresenter>() {

    private lateinit var lighter: Lighter

    private var isLoadBottom: Boolean = false

    private var lotterySpUrl: String = ""

    private var lotteryId: Int? = null
    private var lotteryName: String? = null
    private var dataList: List<HomeGameListResponse>? = null

    override fun attachView() = mPresenter.attachView(this)

    override fun getPageTitle() = getString(R.string.lottery_open)

    override fun attachPresenter() = LotteryPresenter()

    override fun getContentResID() = R.layout.fragment_lottery

    override fun isOverridePage() = false

    override fun isShowBackIcon() = false

    override fun isRegisterRxBus() = true

    override fun initContentView() {
        super.initContentView()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
        anchorTabView.setRankingTab()
        anchorTabView.setTabBackgroundColor(getColor(R.color.white))
        anchorTabView.setTabText("历史开奖", "专家计划")
    }

    override fun initData() {
        super.initData()
        //默认数据---------------------start
        baseInitLotteryType()
        baseInitLotteryOpenCode()
        //-----------------------------end
        mPresenter.getLotteryType()
        //首次引导图
        if (!UserInfoSp.getOpenCodeGuide()) {
            lighter = Lighter.with(activity)
            lighter.setAutoNext(true)
                    .addHighlight(
                            LighterParameter.Builder()
                                    .setHighlightedViewId(R.id.imgSp)
                                    .setTipLayoutId(R.layout.dialog_guide_lottery)
                                    .setLighterShape(CircleShape(20f))
                                    .setTipViewRelativeDirection(Direction.TOP)
                                    .setTipViewRelativeOffset(MarginOffset(30, 0, 0, 30))
                                    .build(),
                            LighterParameter.Builder()
                                    .setHighlightedViewId(R.id.tipTabView)
                                    .setTipLayoutId(R.layout.dialog_guide_lottery_plan)
                                    .setTipViewRelativeDirection(Direction.BOTTOM)
                                    .setTipViewRelativeOffset(MarginOffset(0, -120, 0, 0))
                                    .build()).setBackgroundColor(getColor(R.color.transparent_82))
                    .show()
            UserInfoSp.putOpenCodeGuide(true)
        }
    }

//------------------------------彩种------------------------------------------------------------------------------------------------------------------------------
    /**
     * 首次默认彩种
     */
    private fun baseInitLotteryType() {
        val list: List<LotteryTypeResponse>
        list = ArrayList()
        for (index in 1..6) {
            list.add(LotteryTypeResponse(0, "加载中..", "-1", ""))
        }
        val value = LinearLayoutManager(getPageActivity(), LinearLayoutManager.HORIZONTAL, false)
        val lotteryTypeAdapter = LotteryTypeAdapter(getPageActivity())
        lotteryTypeAdapter.addAll(list)
        rvLotteryType.adapter = lotteryTypeAdapter
        rvLotteryType.layoutManager = value

    }

    /**
     * 彩种
     */
    fun initLotteryType(data: List<LotteryTypeResponse>?) {
        setPageTitle(data?.get(0)?.cname)
        val videoUrl = data?.get(0)?.video_url.toString()
        lotterySpUrl = videoUrl
        lotteryId = data?.get(0)?.lottery_id
        lotteryName = data?.get(0)?.cname
        val value = LinearLayoutManager(getPageActivity(), LinearLayoutManager.HORIZONTAL, false)
        val lotteryTypeAdapter = LotteryTypeAdapter(getPageActivity())
        lotteryTypeAdapter.addAll(data)
        rvLotteryType.adapter = lotteryTypeAdapter
        rvLotteryType.layoutManager = value
        lotteryTypeAdapter.setOnItemClickListener { dates, position ->
            lotteryTypeAdapter.changeBackground(position)
            mPresenter.getLotteryOpenCode(dates.lottery_id)
            setPageTitle(dates.cname)
            lotteryName = dates.cname
            lotterySpUrl = dates.video_url
        }
    }

//------------------------------开奖号码------------------------------------------------------------------------------------------------------------------------------
    /**
     * 默认开奖号码
     */
    fun baseInitLotteryOpenCode() {
        tvOpenCount.text = "第(null)期开奖结果"
        tvTime.text = "-- : --"
    }

    /**
     * 开奖号码
     */
    private var cutDown: CountDownTimer? = null

    @SuppressLint("SetTextI18n")
    fun initLotteryOpenCode(data: LotteryCodeNewResponse) {
        setGone(firstPlace)
        RxBus.get().post(LotteryGetExpert(data.lottery_id, data.issue))
        lotteryId = data.lottery_id
        cutDown?.cancel()
        tvOpenCount.text = "第 " + data.issue + " 期开奖结果"
        tvTime.text = "-- : --"
        val value = LinearLayoutManager(getPageActivity(), LinearLayoutManager.HORIZONTAL, false)
        if (data.lottery_id != 8) {
            val lotteryOpenCodeAdapter = LotteryOpenCodeAdapter(getPageActivity())
            lotteryOpenCodeAdapter.addAll(data.code.split(","))
            rvOpenCode.adapter = lotteryOpenCodeAdapter
        } else {
            val lotteryOpenCodeHongKongAdapter = LotteryOpenCodeHongKongAdapter(getPageActivity())
            val tbList: ArrayList<String> = data.code.split(",") as ArrayList<String>
            tbList.add(6, "+")
            lotteryOpenCodeHongKongAdapter.addAll(tbList)
            rvOpenCode.adapter = lotteryOpenCodeHongKongAdapter
        }
        rvOpenCode.layoutManager = value
        //判断底部view是否已经初始化
        if (!isLoadBottom) {
            getLotteryHistoryExpertPlan(data.lottery_id, data.issue)
            isLoadBottom = true
        } else {
//            RxBus.get().post(LotteryGetExpert(data.lottery_id, data.issue))
            LotteryHistoryOpenCodeFragment.newInstance(data.lottery_id)
            LotteryExpertPlanFragment.newInstance(data.lottery_id, data.issue)
        }
        if (data.next_lottery_time.toLong() > 0) {
            cuntDownTime(data.next_lottery_time.toLong() * 1000, data.lottery_id)
            cutDown?.start()
        } else {
            val t = Timer()
            t.schedule(object : TimerTask() {
                override fun run() {
                    cutDown?.onFinish()
                    t.cancel()
                }
            }, 3500)
        }
    }

    /**
     * 倒计时
     */
    private fun cuntDownTime(millisUntilFinished: Long, lotteryId: Int) {
        cutDown = object : CountDownTimer(millisUntilFinished, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                val day: Long = millisUntilFinished / (1000 * 60 * 60 * 24)/*单位 天*/
                val hour: Long = (millisUntilFinished - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)/*单位 时*/
                val minute: Long = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60)/*单位 分*/
                val second: Long = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000 /*单位 秒*/
                if (day > 0) {
                    tvTime.text = dataLong(day) + "天:" + dataLong(hour) + ":" + dataLong(minute) + ":" + dataLong(second)
                } else tvTime.text = dataLong(hour) + ":" + dataLong(minute) + ":" + dataLong(second)
            }

            override fun onFinish() {
                mPresenter.getLotteryOpenCode(lotteryId)
            }
        }
    }

    fun dataLong(c: Long)// 这个方法是保证时间两位数据显示，如果为1点时，就为01
            : String {
        return if (c >= 10)
            c.toString()
        else
            "0$c"
    }

    /**
     * 历史开奖号码,专家计划
     */
    private fun getLotteryHistoryExpertPlan(lotteryId: Int, issue: String) {
        setGone(secondPlace)
        val fragments = arrayListOf<BaseFragment>(
                LotteryHistoryOpenCodeFragment.newInstance(lotteryId),
                LotteryExpertPlanFragment.newInstance(lotteryId, issue)
        )
        val adapter = BaseFragmentPageAdapter(childFragmentManager, fragments)
        viewPagers.adapter = adapter
        viewPagers.currentItem = 0
        viewPagers.offscreenPageLimit = fragments.size
        showContent(placeholders)
    }

    override fun initEvent() {
        anchorTabView.setOnSelectListener {
            viewPagers.currentItem = it
        }
        viewPagers.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                anchorTabView.setTabSelect(position)
            }

        })
        imgSp.setOnClickListener {
            // x5webLottery.setBackgroundColor(getColor(R.color.black))
//            initWebViewSettings(x5webLottery, getPageActivity())
//            relWebSp.startAnimation(showAnimation())
//            setVisibility(R.id.relWebSp, true)
//            x5webLottery.loadUrl(lotterySpUrl)
//            LotteryGuideDialog(getPageActivity()).show()
            if (dataList != null) {
                for ((index, e) in dataList!!.withIndex()) {
                    if (e.name == lotteryName && e.live_status == 1) {
                        LaunchUtils.startFragment(getPageActivity(), HomeLiveDetailsFragment.newInstance(e.anchor_id, "", e.live_status, ""))
                        return@setOnClickListener
                    }
                    if ((index + 1) == dataList?.size) ToastUtils.showNormal("此彩种暂无直播")
                }
            } else ToastUtils.showNormal("此彩种暂无直播")
        }
        relClose.setOnClickListener {
            relWebSp.startAnimation(hideAnimation())
            setGone(R.id.relWebSp)
            x5webLottery.loadUrl("about:blank")
        }
        tvErrorRetry.setOnClickListener {
            mPresenter.getLotteryType()
        }
    }

    //显示动画
    private fun showAnimation(): TranslateAnimation {
        val mShowAction = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f)
        mShowAction.duration = 500
        return mShowAction
    }

    //隐藏动画
    private fun hideAnimation(): TranslateAnimation {
        val mHiddenAction = TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f)
        mHiddenAction.duration = 500
        return mHiddenAction
    }

    override fun onDestroy() {
        cutDown?.cancel()
        if (x5webLottery != null) x5webLottery.destroy()
        super.onDestroy()
    }

    /**
     * 接收游戏榜
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onReciveGameList(eventBean: HomeClickVideo) {
        dataList = eventBean.list
    }

}