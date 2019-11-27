package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/10- 13:38
 * @ Describe
 *
 */

class MineRechargeItemFragment : BaseMvpFragment<MineRechargeItemPresenter>() {


    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineRechargeItemPresenter()

    override fun isOverridePage() = false

    override fun getLayoutResID() = R.layout.fragment_mine_charge_item




    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
    }

    override fun initData() {
        mPresenter.getPayTypeList()
    }


}