package com.fenghuang.caipiaobao.ui.lottery

import com.fenghuang.baselib.base.recycler.BaseRecyclerFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryCodeHistoryResponse

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/21- 15:34
 * @ Describe 历史开奖号码
 *
 */

class LotteryHistoryOpenCodeFragment(val data: List<LotteryCodeHistoryResponse>) : BaseRecyclerFragment<LotteryHistoryOpenCodePresenter, LotteryCodeHistoryResponse>() {

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = LotteryHistoryOpenCodePresenter(data)

    override fun attachAdapter() = LotteryHistoryOpenCodeAdapter(getPageActivity())

    override fun isEnableLoadMore() = false

    override fun isEnableRefresh() = false

    override fun initPageView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

}