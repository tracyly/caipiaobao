package com.fenghuang.caipiaobao.ui.lottery

import com.fenghuang.baselib.base.recycler.BaseRecyclerPresenter
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryApi

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/21- 15:39
 * @ Describe
 *
 */

class LotteryHistoryOpenCodePresenter(val lotteryId: Int, val date: String) : BaseRecyclerPresenter<LotteryHistoryOpenCodeFragment>() {


    fun getHisttoryData(lotteryId: Int, date: String) {
        LotteryApi.getLotteryHistoryCode(lotteryId, date) {
            onSuccess {
                if (it.isNotEmpty()) {
                    if (mView.getStartPage() == 1) {
                        mView.clear()
                    }
                    mView.addDatas(it)
                } else {
                    mView.showPageEmpty()
                }
            }
            onFailed {
                mView.showPageError(it.getMsg())
            }
        }
    }

    override fun loadData(page: Int) {
        getHisttoryData(lotteryId, date)
    }

}