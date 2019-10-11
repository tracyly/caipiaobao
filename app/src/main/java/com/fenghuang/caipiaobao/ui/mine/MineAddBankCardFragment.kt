package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import kotlinx.android.synthetic.main.fragment_mine_add_bank_card.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/10- 15:45
 * @ Describe 添加银卡
 *
 */

class MineAddBankCardFragment : BaseMvpFragment<MineAddBankCardPresenter>() {

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineAddBankCardPresenter()

    override fun getContentResID() = R.layout.fragment_mine_add_bank_card

    override fun getPageTitle() = getString(R.string.mine_add_bank_card)

    override fun isShowBackIconWhite() = false

    override fun isOverridePage() = false

    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
    }

    override fun initData() {
        mPresenter.initSpinnerCardList(spCard, getPageActivity())

    }
}
