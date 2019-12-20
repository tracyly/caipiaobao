package com.fenghuang.caipiaobao.ui.lottery

import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.NetWorkUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryApi
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog
import kotlinx.android.synthetic.main.fragment_home_new.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/3- 15:23
 * @ Describe
 *
 */

class LotteryPresenter : BaseMvpPresenter<LotteryFragment>() {


    fun getLotteryType() {
        if (NetWorkUtils.isNetworkNotConnected()) {
            mView.setVisible(mView.errorContainer)
        } else {
            getMoney()
            mView.setGone(mView.errorContainer)
            LotteryApi.getLotteryType {
                onSuccess {
                    if (mView.isActive()) {

                        mView.initLotteryType(it)
                        getLotteryOpenCode(it[0].lottery_id)
                    }
                }
                onFailed {
                    ToastUtils.showError(it.getMsg())
                }
            }
        }
    }

    fun getLotteryOpenCode(lotteryId: Int) {
        LotteryApi.getLotteryNewCode(lotteryId) {
            onSuccess {
                if (mView.isActive()) {
                    mView.initLotteryOpenCode(it)
                }
            }
            onFailed {
                ToastUtils.showError(it.getMsg())
            }
        }
    }

    /**
     * 获取余额去判断是否被顶下去
     */
    fun getMoney() {
        MineApi.getUserBalance {
            onSuccess { }
            onFailed { ExceptionDialog.showExpireDialog(mView.requireContext(), it) }
        }
    }

}