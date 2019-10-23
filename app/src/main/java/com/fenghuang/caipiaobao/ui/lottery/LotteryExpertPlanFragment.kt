package com.fenghuang.caipiaobao.ui.lottery

import android.os.Bundle
import com.fenghuang.baselib.base.recycler.BaseRecyclerFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.constant.IntentConstant
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryExpertPlanResponse
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryGetExpert
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/23- 12:00
 * @ Describe 专家计划
 *
 */

class LotteryExpertPlanFragment : BaseRecyclerFragment<LotteryExpertPlanPresenter, LotteryExpertPlanResponse>() {

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = LotteryExpertPlanPresenter(arguments?.getInt(IntentConstant.LOTTERY_EXPERT_LOTTERY_ID)
            ?: 0, arguments?.getString(IntentConstant.LOTTERY_EXPERT_LOTTERY_ISSUE, "0")!!)

    override fun attachAdapter() = LotteryExpertPlanAdapter(getPageActivity())

    override fun isRegisterRxBus() = true

    override fun isEnableLoadMore() = false

    override fun initPageView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    /**
     * 接收LotteryId
     *
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onReciveExpert(eventBean: LotteryGetExpert) {
        mPresenter.getExpertPlan(eventBean.lottery_id, eventBean.issue)
    }

    companion object {
        private lateinit var mBundle: Bundle
        fun newInstance(lotteryId: Int, issue: String): LotteryExpertPlanFragment {
            val fragment = LotteryExpertPlanFragment()
            mBundle = Bundle()
            mBundle.putInt(IntentConstant.LOTTERY_EXPERT_LOTTERY_ID, lotteryId)
            mBundle.putString(IntentConstant.LOTTERY_EXPERT_LOTTERY_ISSUE, issue)
            fragment.arguments = mBundle
            return fragment
        }
    }
}

