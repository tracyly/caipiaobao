package com.fenghuang.caipiaobao.ui.mine

import ExceptionDialog
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import com.fenghuang.caipiaobao.utils.UserInfoSp

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/17- 16:01
 * @ Describe
 *
 */

class MineMyAttentionPresenter : BaseMvpPresenter<MineMyAttentionFragment>() {

    fun getAttentionList() {
        MineApi.getAttentionList{
            onSuccess {
                if (mView.isActive()) mView.upDateAttentionList(it)
            }
            onFailed {
                ExceptionDialog.showExpireDialog(mView.requireContext(),it)
                mView.showPageEmpty(it.getMsg())
            }
        }
    }

}