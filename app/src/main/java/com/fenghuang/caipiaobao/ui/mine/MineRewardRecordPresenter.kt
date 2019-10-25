package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.ui.mine.data.MineApi

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/14- 15:30
 * @ Describe
 *
 */

class MineRewardRecordPresenter : BaseMvpPresenter<MineRewardRecordFragment>() {


    fun getRewordRecord() {
        MineApi.getRewardRecord {
            onSuccess {
                if (mView.isActive()) mView.upDateRewardRecord(it)
            }
            onFailed {
                mView.showPageEmpty(it.getMsg())
            }
        }
    }


}