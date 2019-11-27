package com.fenghuang.caipiaobao.ui.mine

import ExceptionDialog
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.ui.mine.data.RechargeApi
import kotlinx.android.synthetic.main.fragment_invest.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/28- 19:51
 * @ Describe
 *
 */

class MineInvestPresenter : BaseMvpPresenter<MineInvestFragment>() {


    fun getInvestUrl(amount: Float, channels: Int) {
        if (channels == 3) {
            mView.showPageLoading()
            RechargeApi.getToPayUrl(amount, channels) {
                onSuccess {
                    mView.investWebView.loadUrl(it.url.replace("\\", "/"))
                    mView.hidePageLoading()
                }
                onFailed {
                    ExceptionDialog.showExpireDialog(mView.requireContext(), it)
                    mView.hidePageLoading()
                }
            }
        }
        if (channels == 1) {
            mView.showPageLoading()
            RechargeApi.getPayUrl(amount, channels) {
                onSuccess {
                    mView.investWebView.loadUrl(it.url.replace("\\", "/"))
                    mView.hidePageLoading()
                }
                onFailed {
                    ExceptionDialog.showExpireDialog(mView.requireContext(), it)
                    mView.hidePageLoading()
                }
            }
        }




    }
}