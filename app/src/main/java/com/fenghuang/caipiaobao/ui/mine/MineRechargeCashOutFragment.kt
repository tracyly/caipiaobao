package com.fenghuang.caipiaobao.ui.mine

import android.view.View
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.StatusBarUtils
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

    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    override fun initEvent() {
        rlAddBank.setOnClickListener {
            startFragment(context, MineAddBankCardFragment())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
    }

    override fun initData() {
        val list = mPresenter.getBankList()
        if (list.isNotEmpty()) {
            rlBank.visibility = View.VISIBLE
            rlAddBank.visibility = View.GONE
            when {
                list[0].bankType == "1" -> imgBank.background = getDrawable(R.mipmap.ic_mine_zggs)
                list[0].bankType == "2" -> imgBank.background = getDrawable(R.mipmap.ic_mine_jsyh)
                list[0].bankType == "3" -> imgBank.background = getDrawable(R.mipmap.ic_mine_shpf)
                list[0].bankType == "4" -> imgBank.background = getDrawable(R.mipmap.ic_mine_zsyh)
            }
            tvBankName.text = list[0].bankName
            tvBankCode.text = list[0].bankCode
        }
    }
}