package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import kotlinx.android.synthetic.main.fragment_mine_bank_card_list.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/11- 17:17
 * @ Describe 银行卡列表
 *
 */

class MineBankCardList : BaseMvpFragment<MineBankCardListPresenter>() {


    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineBankCardListPresenter()

    override fun isOverridePage() = false

    override fun getContentResID() = R.layout.fragment_mine_bank_card_list

    override fun getPageTitle() = getString(R.string.mine_card_list)

    override fun isShowBackIconWhite() = false

    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
    }

    override fun initData() {
        mPresenter.initList(getPageActivity(), rvCardList)


    }
}