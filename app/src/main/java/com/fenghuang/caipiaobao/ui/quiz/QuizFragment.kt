package com.fenghuang.caipiaobao.ui.quiz

import com.fenghuang.baselib.base.recycler.BaseMultiRecyclerNavFragment
import com.fenghuang.caipiaobao.R

/**
 *  author : Peter
 *  date   : 2019/10/17 20:20
 *  desc   : 竟猜
 */
class QuizFragment : BaseMultiRecyclerNavFragment<QuizPresenter>() {

    override fun getPageTitle() = getString(R.string.tab_quiz)
    override fun attachPresenter() = QuizPresenter()

    override fun attachView() = mPresenter.attachView(this)

}