package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.dialog.guide.RewardGuideDialog
import kotlinx.android.synthetic.main.fragment_mine_reward.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/14- 15:30
 * @ Describe
 *
 */

class MineRewardRecordPresenter : BaseMvpPresenter<MineRewardRecordFragment>() {


    fun getRewordRecord(page: Int) {
        MineApi.getRewardRecord(page, 10) {
            onSuccess {
                if (mView.isActive()) {
                    if (it.isEmpty()) {
                        mView.setVisible(mView.rlNoReward)
                    } else {
                        mView.upDateRewardRecord(it)
                        mView.mPage++
                        if (!UserInfoSp.getRewardnGuide()) {
                            RewardGuideDialog(mView.requireActivity()).show()
                            UserInfoSp.putRewardnGuide(true)
                        }
                    }
                }
            }
            onFailed {
                if (mView.isActive()) {
                    ExceptionDialog.showExpireDialog(mView.requireContext(), it)
                    mView.showPageEmpty(it.getMsg())
                    mView.pop()
                }
            }
            mView.rewardSmartRefresh.finishRefresh()
        }
    }

    fun getRewordRecordMore(page: Int) {
        MineApi.getRewardRecord(page, 10) {
            onSuccess {
                if (mView.isActive()) {
                    if (it.isNotEmpty()) {
                        mView.mineRewardRecordAdapter?.addAll(it)
                        mView.mPage++
                    }
                }
            }
            onFailed {
                ExceptionDialog.showExpireDialog(mView.requireContext(), it)
                mView.showPageEmpty(it.getMsg())
                mView.pop()
            }
            mView.rewardSmartRefresh.finishLoadMore()
        }
    }


    fun deleteRewardRecord(reward_id: Int) {
        MineApi.deleteRewardRecord(reward_id) {
            onSuccess {
                if (mView.mineRewardRecordAdapter?.getCount()!! != 0) {
                    if (mView.mineRewardRecordAdapter?.getCount()!! < 10) {
                        mView.mPage++
                        getRewordRecordMore(mView.mPage)
                    }
                } else mView.setVisible(mView.rlNoReward)
            }
            onFailed { ToastUtils.showError("删除失败:" + it.getMsg()) }
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