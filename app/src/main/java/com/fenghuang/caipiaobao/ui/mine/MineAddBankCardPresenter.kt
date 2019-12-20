package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import com.fenghuang.caipiaobao.ui.mine.data.MineUpDateBank
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog
import com.hwangjr.rxbus.RxBus
import kotlinx.android.synthetic.main.fragment_mine_add_bank_card.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/11- 14:42
 * @ Describe 添加银行卡
 *
 */

class MineAddBankCardPresenter : BaseMvpPresenter<MineAddBankCardFragment>() {


    fun getBankList() {
        mView.showPageLoadingDialog()
        MineApi.getBankList {
            onSuccess {
                if (mView.isActive()) {
                    mView.dataList = it
                    if (it.isNotEmpty()) {
                        mView.bankListAll = it
                        mView.tvUserBankCard.text = it[it.size / 2].name
                        mView.bankCode = it[it.size / 2].code
                        val banNameList = arrayListOf<String>()
                        for (i in it.iterator()) {
                            banNameList.add(i.name)
                        }
                        mView.initWheelView(banNameList)
                    }
                }
                mView.hidePageLoadingDialog()
            }
            onFailed {
                ExceptionDialog.showExpireDialog(mView.requireContext(), it)
                mView.hidePageLoadingDialog()
            }
        }
    }

    fun bindBankCard(bank_code: String, province: String, city: String, branch: String, realname: String, card_num: String, fund_password: String) {
        mView.showPageLoadingDialog()
        MineApi.bingBankCard(bank_code, province, city, branch, realname, card_num, fund_password) {
            onSuccess {
                if (mView.isActive()) {
                    ToastUtils.showSuccess("绑定成功")
                    RxBus.get().post(MineUpDateBank(true))
                    mView.hidePageLoadingDialog()
                    mView.pop()
                }
            }
            onFailed {
                mView.hidePageLoadingDialog()
                ExceptionDialog.showExpireDialog(mView.requireActivity(), it)
            }
        }
    }


}