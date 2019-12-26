package com.fenghuang.caipiaobao.ui.mine

import android.annotation.SuppressLint
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.mine.data.MineSaveBank
import com.fenghuang.caipiaobao.ui.mine.data.MineUpDateBank
import com.fenghuang.caipiaobao.ui.mine.data.MineUpDateMoney
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog
import com.fenghuang.caipiaobao.utils.LaunchUtils.startFragment
import com.fenghuang.caipiaobao.utils.MoneyValueFilter
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import kotlinx.android.synthetic.main.fragment_mine_cash_out.*
import java.math.BigDecimal


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/10- 15:02
 * @ Describe 提现
 *
 */

class MineRechargeCashOutFragment(var balance: String) : BaseMvpFragment<MineRechargeCashOutPresenter>() {


    var balanceNow = balance

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineRechargeCashOutPresenter()

    override fun getLayoutResID() = R.layout.fragment_mine_cash_out

    override fun isOverridePage() = false

    override fun isRegisterRxBus() = true


    override fun initEvent() {
        rlAddBankItem.setOnClickListener {
            if (UserInfoSp.getIsSetPayPassWord()) {
                startFragment(context, MineAddBankCardFragment())
            } else ExceptionDialog.noSetPassWord(getPageActivity())
        }
        tvGetMoneyAll.setOnClickListener {
            etGetMoneyToBank.setText(balanceNow)
        }
        btUserGetCash.setOnClickListener {
            mPresenter.getCashOutMoney()
        }

        etGetMoneyToBank.filters = arrayOf<InputFilter>(MoneyValueFilter())
        etGetMoneyToBank.addTextChangedListener(object : TextWatcher {
            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length!! > 0 && BigDecimal(p0.toString()).compareTo(BigDecimal(balance)) == 1) {
                    ToastUtils.showInfo("余额不足")
                    etGetMoneyToBank.setText("")
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
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


    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun upDateUserBankSelect(event: MineUpDateBank) {
        mPresenter.getBankList()
    }


    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun upDateUserMoney(event: MineUpDateMoney) {
        balanceNow = event.money
    }
}