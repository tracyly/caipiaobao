package com.fenghuang.caipiaobao.ui.lottery

import android.annotation.SuppressLint
import android.os.CountDownTimer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.fenghuang.baselib.base.adapter.BaseFragmentPageAdapter
import com.fenghuang.baselib.base.fragment.BaseFragment
import com.fenghuang.baselib.base.fragment.PlaceholderFragment
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryCodeNewResponse
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryGerId
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryTypeResponse
import com.hwangjr.rxbus.RxBus
import kotlinx.android.synthetic.main.fragment_lottery.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/3- 15:21
 * @ Describe  开奖页面
 *
 */

class LotteryFragment : BaseMvpFragment<LotteryPresenter>() {


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
        mPresenter.getLotteryType()
    }

    /**
     * 彩种
     */
    fun initLotteryType(data: List<LotteryTypeResponse>?) {
        val value = LinearLayoutManager(getPageActivity(), LinearLayoutManager.HORIZONTAL, false)
        val lotteryTypeAdapter = LotteryTypeAdapter(getPageActivity())
        lotteryTypeAdapter.addAll(data)
        rvLotteryType.adapter = lotteryTypeAdapter
        rvLotteryType.layoutManager = value
        lotteryTypeAdapter.setOnItemClickListener { data, position ->
            lotteryTypeAdapter.changeBackground(position)
            mPresenter.getLotteryOpenCode(data.lottery_id)
            RxBus.get().post(LotteryGerId(data.lottery_id))
        }
    }

    /**
     * 开奖号码
     */
    private var cutDown: CountDownTimer? = null

    @SuppressLint("SetTextI18n")
    fun initLotteryOpenCode(data: LotteryCodeNewResponse?) {
        tvOpenCount.text = "第 " + data!!.issue + " 期开奖结果"
        tvTime.text = "-- : --"
        cutDown?.cancel()
        cuntDownTime(data.next_lottery_time.toLong() * 1000, data.lottery_id)
        cutDown?.start()
        val value = LinearLayoutManager(getPageActivity(), LinearLayoutManager.HORIZONTAL, false)
        val lotteryOpenCodeAdapter = LotteryOpenCodeAdapter(getPageActivity())
        lotteryOpenCodeAdapter.addAll(data.code.split(","))
        rvOpenCode.adapter = lotteryOpenCodeAdapter
        rvOpenCode.layoutManager = value
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
     * 历史开奖号码
     */
    fun getLotteryHistoryCode(lotteryId: Int) {
        val fragments = arrayListOf<BaseFragment>(
                LotteryHistoryOpenCodeFragment.newInstance(lotteryId),
                PlaceholderFragment.newInstance("专家计划", isMainPage = true, placeholder = R.mipmap.ic_placeholder_empty)
        )
        val adapter = BaseFragmentPageAdapter(childFragmentManager, fragments)
        viewPagers.adapter = adapter
        viewPagers.currentItem = 0
        viewPagers.offscreenPageLimit = fragments.size
        showContent(placeholders)
    }


    /**
     *  历史开奖 专家计划
     */
    override fun initData() {

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
    }

}