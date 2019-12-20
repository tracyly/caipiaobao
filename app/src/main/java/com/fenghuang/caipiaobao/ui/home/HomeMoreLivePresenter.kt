package com.fenghuang.caipiaobao.ui.home

import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.ui.home.data.HomeApi
import com.pingerx.rxnetgo.rxcache.CacheMode
import kotlinx.android.synthetic.main.fragment_live_more.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/12/10- 15:44
 * @ Describe
 *
 */

class HomeMoreLivePresenter : BaseMvpPresenter<HomeMoreLiveFragment>() {

    fun getHotLive(page: Int) {
        if (mView.mPage == 0) mView.showPageLoadingDialog()
        HomeApi.getHomeHotLiveListResult(10, page, CacheMode.NONE) {
            onSuccess {
                if (mView.isActive()) {
                    if (mView.isActive() && it.isNotEmpty()) {
                        if (it.isNotEmpty()) mView.mPage++
                        mView.updateHotLive(it)
                    }
                    mView.hidePageLoadingDialog()
                    mView.moreSmartRefreshLayout.finishLoadMore()
                    mView.moreSmartRefreshLayout.finishRefresh()
                }
            }
            onFailed {
                mView.hidePageLoadingDialog()
            }
        }
    }

    fun getExpert(page: Int) {
        if (mView.mPage == 0) mView.showPageLoadingDialog()
        HomeApi.getHomeExpertListResult(10, page, CacheMode.NONE) {
            onSuccess {
                if (mView.isActive() && it.isNotEmpty()) {
                    mView.updateHotLive(it)
                    if (it.isNotEmpty()) mView.mPage++
                }

                mView.hidePageLoadingDialog()
                mView.moreSmartRefreshLayout.finishLoadMore()
            }
            onFailed {
                mView.hidePageLoadingDialog()
            }
        }
    }

}