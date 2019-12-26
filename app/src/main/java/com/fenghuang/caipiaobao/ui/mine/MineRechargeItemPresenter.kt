package com.fenghuang.caipiaobao.ui.mine

import androidx.recyclerview.widget.LinearLayoutManager
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import com.fenghuang.caipiaobao.ui.mine.data.MinePayTypeList
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog
import kotlinx.android.synthetic.main.fragment_mine_charge_item.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/11/1- 12:53
 * @ Describe
 *
 */

class MineRechargeItemPresenter : BaseMvpPresenter<MineRechargeItemFragment>() {


    fun getPayTypeList() {
        mView.showPageLoadingDialog()
        MineApi.getPayTypeList {
            onSuccess {
                if (mView.isActive()) {
                    initAdapter(it)
                    mView.hidePageLoadingDialog()
                }
            }
            onFailed {
                if (mView.isActive()) {
                    ExceptionDialog.showExpireDialog(mView.requireContext(), it)
                    mView.hidePageLoadingDialog()
                }
            }
        }
    }

    private fun initAdapter(data: List<MinePayTypeList>) {
        val mineRechargeItemAdapter = MineRechargeItemAdapter(mView.requireContext())
        mineRechargeItemAdapter.addAll(data)
        mView.rvRecharges.adapter = mineRechargeItemAdapter
        val value = object : LinearLayoutManager(mView.context) {
            override fun canScrollVertically(): Boolean {
                return true
            }
        }
        mView.rvRecharges.layoutManager = value


    }
}