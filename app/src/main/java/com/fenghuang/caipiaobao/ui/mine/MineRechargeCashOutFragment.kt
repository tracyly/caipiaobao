package com.fenghuang.caipiaobao.ui.mine

import android.annotation.SuppressLint
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.mine.data.MineSaveBank
import com.fenghuang.caipiaobao.utils.LaunchUtils.startFragment
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import kotlinx.android.synthetic.main.fragment_mine_cash_out.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/10- 15:02
 * @ Describe 提现
 *
 */

class MineRechargeCashOutFragment(var balance: String) : BaseMvpFragment<MineRechargeCashOutPresenter>() {

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineRechargeCashOutPresenter()

    override fun getLayoutResID() = R.layout.fragment_mine_cash_out

    override fun isOverridePage() = false

    override fun isRegisterRxBus() = true


    override fun initEvent() {
        rlAddBankItem.setOnClickListener {
            startFragment(context, MineAddBankCardFragment())
        }
        tvGetMoneyAll.setOnClickListener {
            etGetMoneyToBank.setText(balance)
        }
        btUserGetCash.setOnClickListener {
            mPresenter.getCashOutMoney()
        }
    }

    override fun initData() {
        mPresenter.getBankList()
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun saveUserBankSelect(event: MineSaveBank) {
        ImageManager.loadPayTypeListLogo(event.data.bank_img, imgBankItem)
        tvBankNameItem.text = event.data.bank_name
        tvBankCodeItem.text = "尾号" + event.data.card_num.substring(event.data.card_num.length - 4, event.data.card_num.length) + "储蓄卡"
    }


}