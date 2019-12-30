package com.fenghuang.caipiaobao.ui.home.anchor

import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.ui.home.data.HomeApi
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import com.fenghuang.caipiaobao.ui.mine.data.MineIsAnchorLive
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.hwangjr.rxbus.RxBus
import kotlinx.android.synthetic.main.fragment_home_anchor_information.*


/**
 *  author : Peter
 *  date   : 2019/10/19 16:19
 *  desc   :
 */
class HomeAnchorPresenter : BaseMvpPresenter<HomeAnchorFragment>() {

    fun loadData(anchorId: Int) {
        mView.showPageLoadingDialog()
        HomeApi.getHomeLiveAnchorInfo(UserInfoSp.getUserId(), anchorId) {
            onSuccess {
                if (mView.isActive()) {
                    mView.updateInfo(it)
                    mView.hidePageLoadingDialog()
                }
            }
            onFailed {
                ToastUtils.showError(it.getMsg())
                mView.hidePageLoadingDialog()
            }
        }
    }

    /**
     * 关注或者取关
     */
    fun setAttention(anchorId: Int, isAttention: Int) {
        mView.showPageLoadingDialog()
        HomeApi.setAttention(UserInfoSp.getUserId(), anchorId) {
            onSuccess {
                if (mView.isActive()) {
                    mView.hidePageLoadingDialog()
                    if (isAttention == 0) {
                        mView.setGone(mView.tvAnchorAddAttentions)
                        mView.setVisibility(mView.tvAnchorNoAttentions, true)
                        RxBus.get().post(MineIsAnchorLive("falseFalse"))
                        mView.tvAnchorFan.text = (mView.tvAnchorFan.text.toString().toLong() + 1).toString()
                    } else {
                        RxBus.get().post(MineIsAnchorLive("trueTrue"))
                        mView.setVisibility(mView.tvAnchorAddAttentions, true)
                        mView.setGone(mView.tvAnchorNoAttentions)
                        mView.tvAnchorFan.text = (mView.tvAnchorFan.text.toString().toLong() - 1).toString()
                    }
                }
            }
            onFailed {
                ExceptionDialog.showExpireDialog(mView.requireContext(), it)
                mView.hidePageLoadingDialog()
            }
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