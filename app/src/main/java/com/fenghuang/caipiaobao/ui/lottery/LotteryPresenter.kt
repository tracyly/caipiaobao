package com.fenghuang.caipiaobao.ui.lottery

import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryApi

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/3- 15:23
 * @ Describe
 *
 */

class LotteryPresenter : BaseMvpPresenter<LotteryFragment>() {


    fun getLotteryType() {
        LotteryApi.getLotteryType {
            onSuccess {
                mView.initLotteryType(it)
                getLotteryOpenCode(it[0].lottery_id)
            }
            onFailed {
                ToastUtils.showError(it.getMsg())
            }
        }
//        val newResults = arrayListOf<LotteryDataBean>()
//        newResults.add(LotteryDataBean("北京赛车(PK10)", R.mipmap.logo))
//        newResults.add(LotteryDataBean("幸运飞艇", R.mipmap.logo))
//        newResults.add(LotteryDataBean("澳洲幸运10", R.mipmap.logo))
//        newResults.add(LotteryDataBean("极速赛车", R.mipmap.logo))
//        newResults.add(LotteryDataBean("欢乐赛车", R.mipmap.logo))

    }

    fun getLotteryOpenCode(lotteryId: Int) {
        LotteryApi.getLotteryNewCode(lotteryId) {
            onSuccess {
                mView.initLotteryOpenCode(it)
            }
            onFailed {
                ToastUtils.showError(it.getMsg())
            }
        }
//        val codeResults = arrayListOf<LotteryOpenCodeDataBean>()
//        codeResults.add(LotteryOpenCodeDataBean("09"))
//        codeResults.add(LotteryOpenCodeDataBean("10"))
//        codeResults.add(LotteryOpenCodeDataBean("01"))
//        codeResults.add(LotteryOpenCodeDataBean("02"))
//        codeResults.add(LotteryOpenCodeDataBean("03"))
//        codeResults.add(LotteryOpenCodeDataBean("04"))
//        codeResults.add(LotteryOpenCodeDataBean("05"))
//        codeResults.add(LotteryOpenCodeDataBean("06"))
//        codeResults.add(LotteryOpenCodeDataBean("07"))
//        codeResults.add(LotteryOpenCodeDataBean("08"))
//        mView.initLotteryOpenCode(codeResults)
    }

    fun getLotteryOpenHistoryCode(lotteryId: Int, date: String) {
        LotteryApi.getLotteryHistoryCode(lotteryId, date) {
            onSuccess {
                mView.getLotteryHistoryCode(it)
            }
            onFailed {
                ToastUtils.showError(it.getMsg())
            }
        }
    }
}