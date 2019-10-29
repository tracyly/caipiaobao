package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.utils.LaunchUtils.startFragment
import kotlinx.android.synthetic.main.fragment_mine_cash_out.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/10- 15:02
 * @ Describe 提现
 *
 */

class MineRechargeCashOutFragment : BaseMvpFragment<MineRechargeCashOutPresenter>() {

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineRechargeCashOutPresenter()

    override fun getLayoutResID() = R.layout.fragment_mine_cash_out

    override fun isOverridePage() = false


    override fun initEvent() {
        rlAddBankItem.setOnClickListener {
            startFragment(context, MineAddBankCardFragment())
        }
        rlBankItem.setOnClickListener {
            startFragment(context, MineBankCardList())
        }
    }


    override fun initData() {
        mPresenter.getBankList()
//        val list = mPresenter.getBankList()
//        if (list.isNotEmpty()) {
//            rlBank.visibility = View.VISIBLE
//            rlAddBank.visibility = View.GONE
//            when {
//                list[0].bankType == "1" -> imgBank.background = getDrawable(R.mipmap.ic_mine_zggs)
//                list[0].bankType == "2" -> imgBank.background = getDrawable(R.mipmap.ic_mine_jsyh)
//                list[0].bankType == "3" -> imgBank.background = getDrawable(R.mipmap.ic_mine_shpf)
//                list[0].bankType == "4" -> imgBank.background = getDrawable(R.mipmap.ic_mine_zsyh)
//            }
//            tvBankName.text = list[0].bankName
//            tvBankCode.text = "尾号" + list[0].bankCode.substring(list[0].bankCode.length - 4, list[0].bankCode.length) + "储蓄号"
//        }
    }

}