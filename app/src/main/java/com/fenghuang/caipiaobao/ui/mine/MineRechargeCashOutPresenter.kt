package com.fenghuang.caipiaobao.ui.mine

import ExceptionDialog
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import kotlinx.android.synthetic.main.fragment_mine_cash_out.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/11- 16:02
 * @ Describe
 *
 */

class MineRechargeCashOutPresenter : BaseMvpPresenter<MineRechargeCashOutFragment>() {


    fun getBankList() {
        MineApi.getBankList {
            onSuccess {
                if (it.isNotEmpty()) {
                    mView.setGone(R.id.rlAddBankItem)
                    mView.setVisibility(R.id.rlBankItem, true)
                    ImageManager.loadPayTypeListLogo(it[0].img, mView.imgBankItem)
                    mView.tvBankNameItem.text = it[0].name
                    mView.tvBankCodeItem.text = it[0].code
                } else {
                    mView.setGone(R.id.rlBankItem)
                    mView.setVisibility(R.id.rlAddBankItem, true)
                }
            }
            onFailed {
                ExceptionDialog.showExpireDialog(mView.requireContext(), it)
            }
        }
    }

}