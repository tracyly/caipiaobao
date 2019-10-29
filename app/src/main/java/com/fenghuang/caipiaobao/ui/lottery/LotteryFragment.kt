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
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryCodeNewResponse
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryGetExpert
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryTypeResponse
import com.fenghuang.caipiaobao.ui.widget.X5WebView.initWebViewSettings
import com.hwangjr.rxbus.RxBus
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

    private var isLoadBottom: Boolean = false

    private var lotterySpUrl: String = ""

    override fun attachView() = mPresenter.attachView(this)

    override fun getPageTitle() = getString(R.string.lottery_open)

    override fun attachPresenter() = LotteryPresenter()

    override fun getContentResID() = R.layout.fragment_lottery

    override fun isOverridePage() = false

    override fun isShowBackIcon() = false

    override fun initContentView() {
        super.initContentView()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
        anchorTabView.setRankingTab()
        anchorTabView.setTabBackgroundColor(getColor(R.color.white))
        anchorTabView.setTabText("历史开奖", "专家计划")
    }

    override fun initData() {
        super.initData()
        mPresenter.getLotteryType()
    }


    /**
     * 彩种
     */
    fun initLotteryType(data: List<LotteryTypeResponse>?) {
        setPageTitle(data?.get(0)?.cname)
        val videoUrl = data?.get(0)?.video_url.toString()
        isShowView(videoUrl)
        lotterySpUrl = videoUrl
        val value = LinearLayoutManager(getPageActivity(), LinearLayoutManager.HORIZONTAL, false)
        val lotteryTypeAdapter = LotteryTypeAdapter(getPageActivity())
        lotteryTypeAdapter.addAll(data)
        rvLotteryType.adapter = lotteryTypeAdapter
        rvLotteryType.layoutManager = value
        lotteryTypeAdapter.setOnItemClickListener { dates, position ->
            lotteryTypeAdapter.changeBackground(position)
            mPresenter.getLotteryOpenCode(dates.lottery_id)
            setPageTitle(dates.cname)
            isShowView(dates.video_url + "")
            lotterySpUrl = dates.video_url
        }
    }

    private fun isShowView(url: String) {
        if (url != "null") {
            setVisibility(R.id.imgSp, true)
        } else setVisibility(R.id.imgSp, false)
    }

    /**
     * 开奖号码
     */
    private var cutDown: CountDownTimer? = null

    @SuppressLint("SetTextI18n")
    fun initLotteryOpenCode(data: LotteryCodeNewResponse) {
        RxBus.get().post(LotteryGetExpert(data.lottery_id, data.issue))
        cutDown?.cancel()
        tvOpenCount.text = "第 " + data.issue + " 期开奖结果"
        tvTime.text = "-- : --"
        val value = LinearLayoutManager(getPageActivity(), LinearLayoutManager.HORIZONTAL, false)
        val lotteryOpenCodeAdapter = LotteryOpenCodeAdapter(getPageActivity())
        lotteryOpenCodeAdapter.addAll(data.code.split(","))
        rvOpenCode.adapter = lotteryOpenCodeAdapter
        rvOpenCode.layoutManager = value
        //判断底部view是否已经初始化
        if (!isLoadBottom) {
            getLotteryHistoryExpertPlan(data.lottery_id, data.issue)
            isLoadBottom = true
        } else {
            RxBus.get().post(LotteryGetExpert(data.lottery_id, data.issue))
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
                if (minute < 10 && second < 10) {
                    tvTime.text = "0$minute:0$second"
                }
                if (minute < 10 && second > 10) {
                    tvTime.text = "0$minute:$second"
                }
                if (minute > 10 && second > 10) {
                    tvTime.text = "$minute:$second"
                }
                if (minute > 10 && second < 10) {
                    tvTime.text = "$minute:0$second"
                }
            }

            override fun onFinish() {
                mPresenter.getLotteryOpenCode(lotteryId)
            }
        }
    }

    /**
     * 历史开奖号码,专家计划
     */
    private fun getLotteryHistoryExpertPlan(lotteryId: Int, issue: String) {
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
            x5webLottery.onResume()
            x5webLottery.setBackgroundColor(getColor(R.color.black))
            initWebViewSettings(x5webLottery)
            relWebSp.startAnimation(showAnimation())
            setVisibility(R.id.relWebSp, true)
            x5webLottery.loadUrl(lotterySpUrl)
        }
        relClose.setOnClickListener {
            relWebSp.startAnimation(hideAnimation())
            setGone(R.id.relWebSp)
            x5webLottery.onPause()
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
        x5webLottery.destroy()
        super.onDestroy()
    }

}