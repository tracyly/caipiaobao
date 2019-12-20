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

class LotteryHistoryOpenCodePresenter(var mLotteryId: Int, var date: String) : BaseRecyclerPresenter<LotteryHistoryOpenCodeFragment>() {


    fun getHistoryData(lotteryId: Int, date: String) {
        mLotteryId = lotteryId

        LotteryApi.getLotteryHistoryCode(mLotteryId, date) {
            onSuccess {
                if (mView.isActive() && it.isNotEmpty()) {
                    if (mView.getStartPage() == 1) {
                        mView.clear()
                    }

                    mView.showDatas(it)
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
        if (mView.isActive())
            getHistoryData(mLotteryId, date)
    }

}