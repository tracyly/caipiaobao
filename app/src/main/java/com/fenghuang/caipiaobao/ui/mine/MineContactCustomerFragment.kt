package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.fragment.BaseNavFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/9- 17:25
 * @ Describe 联系客服
 *
 */

class MineContactCustomerFragment : BaseNavFragment() {


    override fun getContentResID() = R.layout.fragment_mine_contant_customer

    override fun getPageTitle() = getString(R.string.mine_contact_customer)


    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
    }
}