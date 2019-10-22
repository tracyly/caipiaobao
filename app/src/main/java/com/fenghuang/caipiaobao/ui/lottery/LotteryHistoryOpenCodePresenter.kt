package com.fenghuang.caipiaobao.ui.lottery

import com.fenghuang.baselib.base.recycler.BaseRecyclerPresenter
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryCodeHistoryResponse

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/21- 15:39
 * @ Describe
 *
 */

class LotteryHistoryOpenCodePresenter(var data: List<LotteryCodeHistoryResponse>) : BaseRecyclerPresenter<LotteryHistoryOpenCodeFragment>() {

    override fun loadData(page: Int) {
        mView.addDatas(data)
    }

}