package com.fenghuang.caipiaobao.ui.lottery

import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.caipiaobao.R

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/3- 15:21
 * @ Describe  开奖页面
 *
 */

class LotteryFragment : BaseMvpFragment<LotteryPresenter>() {


    override fun attachView() = mPresenter.attachView(this)

    override fun getPageTitle() = getString(R.string.lottery_open)

    override fun attachPresenter() = LotteryPresenter()

    override fun getContentResID() = R.layout.fragment_lottery

    override fun isOverridePage() = false

    override fun isShowBackIcon() = false

    override fun initContentView() {
        super.initContentView()
        loadRootFragment(R.id.linLotteryType, LotteryTypeFragment())
    }


}