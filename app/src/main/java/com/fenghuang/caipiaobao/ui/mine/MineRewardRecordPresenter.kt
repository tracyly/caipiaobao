package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import me.jessyan.autosize.utils.LogUtils

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
                LogUtils.e(it.toString())
                if (mView.isActive()) mView.upDateRewardRecord(it)
            }
            onFailed {
                mView.showPageError()
            }
        }
    }


}