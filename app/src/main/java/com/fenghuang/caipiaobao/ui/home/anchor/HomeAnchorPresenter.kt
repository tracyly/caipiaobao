package com.fenghuang.caipiaobao.ui.home.anchor

import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.ui.home.data.HomeApi
import com.fenghuang.caipiaobao.utils.UserInfoSp

/**
 *  author : Peter
 *  date   : 2019/10/19 16:19
 *  desc   :
 */
class HomeAnchorPresenter : BaseMvpPresenter<HomeAnchorFragment>() {

    fun loadData(anchorId: Int) {
        HomeApi.getHomeLiveAnchorInfo(UserInfoSp.getUserId(), anchorId) {
            onSuccess {
                mView.updateInfo(it)
            }
            onFailed {
                ToastUtils.showError(it.getMsg())
            }
        }
    }
}