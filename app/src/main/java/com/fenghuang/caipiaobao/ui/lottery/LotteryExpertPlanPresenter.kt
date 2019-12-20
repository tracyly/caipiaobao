package com.fenghuang.caipiaobao.ui.lottery

import com.fenghuang.baselib.base.recycler.BaseRecyclerPresenter
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryApi

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/23- 12:02
 * @ Describe
 *
 */

class LotteryExpertPlanPresenter(var mLotteryId: Int, var mIssue: String) : BaseRecyclerPresenter<LotteryExpertPlanFragment>() {


    fun getExpertPlan(lottery_id: Int, issue: String) {
        mLotteryId = lottery_id
        mIssue = issue
        LotteryApi.getExpertPlan(lottery_id, issue) {
            onSuccess {
                if (mView.isActive()) {

                    if (it.isNotEmpty()) {
                        if (mView.getStartPage() == 1) {
                            mView.clear()
                        }
                        mView.addDatas(it)
                    } else {
                        mView.showPageEmpty()
                    }
                }
            }
            onFailed {
                mView.showPageError(it.getMsg())
            }
        }
    }

    override fun loadData(page: Int) {
        getExpertPlan(mLotteryId, mIssue)
    }

}