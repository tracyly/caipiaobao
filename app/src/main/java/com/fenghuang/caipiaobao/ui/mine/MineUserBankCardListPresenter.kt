package com.fenghuang.caipiaobao.ui.mine

import android.annotation.SuppressLint
import androidx.recyclerview.widget.LinearLayoutManager
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog.showExpireDialog
import kotlinx.android.synthetic.main.fragment_mine_bank_card_list.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/13- 13:29
 * @ Describe
 *
 */

class MineUserBankCardListPresenter : BaseMvpPresenter<MineUserBankCardList>() {

    @SuppressLint("SetTextI18n")
    fun getBankList() {
        mView.showPageLoadingDialog()
        MineApi.getUserBankList {
            onSuccess {
                if (mView.isActive()) {
                    val mineItemAdapter = MineUserBankCardListAdapter(mView.requireContext())
                    mineItemAdapter.addAll(it)
                    mView.rvCardList.adapter = mineItemAdapter
                    val value = object : LinearLayoutManager(mView.requireContext()) {
                        override fun canScrollVertically(): Boolean {
                            return false
                        }
                    }
                    mView.rvCardList.layoutManager = value
                }
                mView.hidePageLoadingDialog()
            }
            onFailed {
                if (mView.isActive()) {
                    showExpireDialog(mView.requireContext(), it)
                    mView.hidePageLoadingDialog()
                }
            }
        }
    }
}


