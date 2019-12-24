package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.home.data.HomeApi
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import com.fenghuang.caipiaobao.ui.mine.data.MineIsAnchorLive
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.dialog.guide.AttentionGuideDialog
import com.hwangjr.rxbus.RxBus

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/17- 16:01
 * @ Describe
 *
 */

class MineMyAttentionPresenter : BaseMvpPresenter<MineMyAttentionFragment>() {

    fun getAttentionList() {
        MineApi.getAttentionList {
            onSuccess {
                if (mView.isActive()) {
                    mView.upDateAttentionList(it)
                    if (!UserInfoSp.getAttentionGuide()) {
                        AttentionGuideDialog(mView.requireActivity()).show()
                        UserInfoSp.putAttentionGuide(true)
                    }
                }
            }
            onFailed {
                if (mView.isActive()) {
                    if (it.getCode() == 2 || it.getMsg().equals("暂无关注")) {
                        mView.setVisible(R.id.rlNoAttention)
                    } else {
                        ExceptionDialog.showExpireDialog(mView.requireContext(), it)
                    }
                }
            }
        }

    }

    /**
     * 取关
     */
    fun unDepose(userId: Int, anchorId: Int) {
        HomeApi.setAttention(userId, anchorId) {
            onSuccess {
                RxBus.get().post(MineIsAnchorLive("GoUp"))
                if (mView.mineMyAttentionAdapter?.getCount() == 0) mView.setVisible(R.id.rlNoAttention)
            }
            onFailed { ToastUtils.showError(it.getMsg()) }
        }
    }

    /**
     * 获取余额去判断是否被顶下去
     */
    fun getMoney() {
        MineApi.getUserBalance {
            onSuccess { }
            onFailed { ExceptionDialog.showExpireDialog(mView.requireContext(), it) }
        }
    }
}