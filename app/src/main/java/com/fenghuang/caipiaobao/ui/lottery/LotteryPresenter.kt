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
    }


}