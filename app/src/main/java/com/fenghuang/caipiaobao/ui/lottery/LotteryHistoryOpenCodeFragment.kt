package com.fenghuang.caipiaobao.ui.lottery

import android.os.Bundle
import com.fenghuang.baselib.base.recycler.BaseRecyclerFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.TimeUtils
import com.fenghuang.caipiaobao.constant.IntentConstant
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryCodeHistoryResponse
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryGetExpert
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/21- 15:34
 * @ Describe 历史开奖号码
 *
 */

class LotteryHistoryOpenCodeFragment : BaseRecyclerFragment<LotteryHistoryOpenCodePresenter, LotteryCodeHistoryResponse>() {

    private var lotteryId = arguments?.getInt(IntentConstant.LOTTERY_HISTORY_OPEN_CODE_ID) ?: 0

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = LotteryHistoryOpenCodePresenter(arguments?.getInt(IntentConstant.LOTTERY_HISTORY_OPEN_CODE_ID)
            ?: 0, TimeUtils.getToday())

    override fun attachAdapter() = LotteryHistoryOpenCodeAdapter(getPageActivity(), lotteryId)

    override fun isRegisterRxBus() = true

    override fun isEnableLoadMore() = false

    override fun initPageView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    /**
     * 接收LotteryId
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onReciveID(eventBean: LotteryGetExpert) {
        mPresenter.getHistoryData(eventBean.lottery_id, TimeUtils.getToday())
    }

    companion object {
        private lateinit var mBundle: Bundle
        fun newInstance(lotteryId: Int): LotteryHistoryOpenCodeFragment {
            val fragment = LotteryHistoryOpenCodeFragment()
            mBundle = Bundle()
            mBundle.putInt(IntentConstant.LOTTERY_HISTORY_OPEN_CODE_ID, lotteryId)
            fragment.arguments = mBundle
            return fragment
        }
    }
}