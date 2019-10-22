package com.fenghuang.caipiaobao.ui.home

import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.SpUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.constant.UserConstant
import com.fenghuang.caipiaobao.ui.home.data.HomeApi

/**
 *  author : Peter
 *  date   : 2019/10/19 16:19
 *  desc   :
 */
class HomeAnchorPresenter : BaseMvpPresenter<HomeAnchorFragment>() {

    fun loadData(anchorId: Int) {
        HomeApi.getHomeLiveAnchorInfo(SpUtils.getInt(UserConstant.USER_ID), anchorId) {
            onSuccess {
                mView.updateInfo(it)
            }
            onFailed {
                ToastUtils.showError(it.getMsg())
            }
        }
    }
}