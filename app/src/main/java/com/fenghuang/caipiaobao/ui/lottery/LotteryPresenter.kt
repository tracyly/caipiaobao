package com.fenghuang.caipiaobao.ui.lottery

import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.NetWorkUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryApi
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog
import kotlinx.android.synthetic.main.fragment_home_new.errorContainer
import kotlinx.android.synthetic.main.fragment_lottery.*

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
                        mView.lotterySmartRefresh.finishRefresh()
                        mView.initLotteryType(it)
                        getLotteryOpenCode(it[0].lottery_id)
                    }
                }
                onFailed {
                    mView.lotterySmartRefresh.finishRefresh()
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
                    mView.lotterySmartRefresh.finishRefresh()
                }
            }
            onFailed {
                ToastUtils.showError(it.getMsg())
                mView.lotterySmartRefresh.finishRefresh()
            }
        }
    }

    /**
     * 获取余额去判断是否被顶下去
     */
    fun getMoney() {
        MineApi.getUserBalance {
            onSuccess { }
            onFailed { if (it.getCode() == 2003) ExceptionDialog.loginMore(mView.requireContext()) }
        }
    }

}