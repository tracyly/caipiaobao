package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.fragment.BaseNavFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/9- 14:50
 * @ Describe 用户反馈
 *
 */

class MineFeedBackFragment : BaseNavFragment() {


    override fun getContentResID() = R.layout.fragment_feedback

    override fun getPageTitle() = getString(R.string.mine_feed_back)

    override fun isShowBackIconWhite() = false


    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
    }

}