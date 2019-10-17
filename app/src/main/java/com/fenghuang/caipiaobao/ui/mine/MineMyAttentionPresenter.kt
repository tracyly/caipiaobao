package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.ui.mine.data.MineApi

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/17- 16:01
 * @ Describe
 *
 */

class MineMyAttentionPresenter : BaseMvpPresenter<MineMyAttentionFragment>() {

    fun getAttentionList() {
        MineApi.getAttentionList(1) {
            onSuccess {
                if (mView.isActive()) mView.upDateAttentionList(it)
            }
            onFailed {
                mView.showPageError()
            }
        }
    }

}