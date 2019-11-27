package com.fenghuang.caipiaobao.ui.mine

import android.os.Bundle
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.constant.IntentConstant.MINE_CHANNELS_ID
import com.fenghuang.caipiaobao.constant.IntentConstant.MINE_INVEST_AMOUNT
import com.fenghuang.caipiaobao.ui.widget.X5WebView
import kotlinx.android.synthetic.main.fragment_invest.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/28- 17:05
 * @ Describe 充值Web
 *
 */

class MineInvestFragment : BaseMvpFragment<MineInvestPresenter>() {

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineInvestPresenter()

    override fun getPageTitle() = getString(R.string.mine_online_invest)

    override fun getContentResID() = R.layout.fragment_invest

    override fun isShowBackIconWhite() = false

    override fun isOverridePage() = false


    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
        X5WebView.initWebViewSettings(investWebView, getPageActivity())
    }

    override fun initData() {
        mPresenter.getInvestUrl((arguments?.getDouble(MINE_INVEST_AMOUNT)!!.toFloat()), arguments?.getInt(MINE_CHANNELS_ID)
                ?: 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
        if (investWebView != null) {
            investWebView.destroy()
        }
    }


    companion object {
        fun newInstance(amount: Double, channels_id: Int): MineInvestFragment {
            val fragment = MineInvestFragment()
            val bundle = Bundle()
            bundle.putInt(MINE_CHANNELS_ID, channels_id)
            bundle.putDouble(MINE_INVEST_AMOUNT, amount)
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