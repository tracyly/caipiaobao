package com.fenghuang.caipiaobao.ui.mine

import android.os.Bundle
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.constant.IntentConstant.MINE_INVEST_AMOUNT
import com.fenghuang.caipiaobao.constant.IntentConstant.MINE_RECHARGE_ID
import com.fenghuang.caipiaobao.constant.IntentConstant.MINE_RECHARGE_URL
import com.fenghuang.caipiaobao.data.api.BaseApi
import kotlinx.android.synthetic.main.fragment_invest.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/28- 17:05
 * @ Describe 充值Web
 *
 */

class MineInvestFragment : BaseMvpFragment<MineInvestPresenter>(), BaseApi {

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineInvestPresenter()

    override fun getPageTitle() = getString(R.string.mine_online_invest)

    override fun getContentResID() = R.layout.fragment_invest

    override fun isShowBackIconWhite() = false

    override fun isOverridePage() = false


    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    override fun initData() {
        val money = arguments?.getDouble(MINE_INVEST_AMOUNT)!!.toFloat()
        val id = arguments?.getInt(MINE_RECHARGE_ID)!!
        val url = arguments?.getString(MINE_RECHARGE_URL)!!
        mPresenter.getInvestUrl(money, id, url)
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
        if (investWebView != null) {
            investWebView.destroy()
        }
    }


    companion object {
        fun newInstance(amount: Double, id: Int, url: String): MineInvestFragment {
            val fragment = MineInvestFragment()
            val bundle = Bundle()
            bundle.putString(MINE_RECHARGE_URL, url)
            bundle.putDouble(MINE_INVEST_AMOUNT, amount)
            bundle.putInt(MINE_RECHARGE_ID, id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBackPressedSupport(): Boolean {
        if (investWebView.canGoBack()) {
            investWebView.goBack()
            return true
        } else {
            pop()
        }
        return super.onBackPressedSupport()
    }
}