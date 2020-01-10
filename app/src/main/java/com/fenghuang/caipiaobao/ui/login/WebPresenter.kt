package com.fenghuang.caipiaobao.ui.login

import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.LogUtils
import com.fenghuang.caipiaobao.ui.home.data.HomeApi
import kotlinx.android.synthetic.main.fragment_bet.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/12/12- 12:21
 * @ Describe
 *
 */

class WebPresenter : BaseMvpPresenter<WebFragment>() {


    fun getUrl() {
        HomeApi.getLotteryUrl {
            onSuccess {
                if (mView.isActive()) {

                    mView.baseUrl = it.protocol
                    mView.baseBetWebView.loadUrl(it.protocol)
                }
            }
            onFailed { }
        }
    }


}