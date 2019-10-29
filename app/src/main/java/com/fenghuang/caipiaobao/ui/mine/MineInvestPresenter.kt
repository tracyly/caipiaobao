package com.fenghuang.caipiaobao.ui.mine

import ExceptionDialog
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
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
        mView.showPageLoading()
        MineApi.getPayUrl(amount, channels) {
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