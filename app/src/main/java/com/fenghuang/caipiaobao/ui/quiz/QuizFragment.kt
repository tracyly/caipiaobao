package com.fenghuang.caipiaobao.ui.quiz

import com.fenghuang.baselib.base.recycler.BaseMultiRecyclerNavFragment
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.quiz.data.QuizResponse
import com.fenghuang.caipiaobao.ui.quiz.data.QuizTitleBean
import com.fenghuang.caipiaobao.ui.quiz.data.QuizTopImageBean

/**
 *  author : Peter
 *  date   : 2019/10/17 20:20
 *  desc   : 竟猜
 */
class QuizFragment : BaseMultiRecyclerNavFragment<QuizPresenter>() {

    override fun getPageTitle() = getString(R.string.tab_quiz)
    override fun attachPresenter() = QuizPresenter()

    override fun attachView() = mPresenter.attachView(this)

    override fun getContentResID() = R.layout.fragment_quiz


//    override fun getItemDivider(): RecyclerView.ItemDecoration? {
//        return DividerItemDecoration(getColor(R.color.color_f7f7f7), com.fenghuang.baselib.utils.ViewUtils.dp2px(10))
//    }

    override fun initPageView() {
        super.initPageView()
        register(QuizResponse::class.java, QuizHolder())
        register(QuizTitleBean::class.java, QuizTitleHolder())
        register(QuizTopImageBean::class.java, QuizTopImageHolder())
    }

}