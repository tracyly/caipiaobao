package com.fenghuang.caipiaobao.ui.quiz

import com.fenghuang.baselib.base.recycler.BaseRecyclerPresenter
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.quiz.data.QuizApi
import com.fenghuang.caipiaobao.ui.quiz.data.QuizTitleBean
import com.fenghuang.caipiaobao.ui.quiz.data.QuizTopImageBean

/**
 *  author : Peter
 *  date   : 2019/10/17 20:26
 *  desc   : 竟猜p层
 */
class QuizPresenter : BaseRecyclerPresenter<QuizFragment>() {

    override fun loadData(page: Int) {

        QuizApi.getQuizArticleListResult(10, page) {
            onSuccess {
                if (it.isNotEmpty()) {
                    if (mView.isActive()) {
                        if (page == mView.getStartPage()) {
                            mView.clear()
                            mView.addItem(QuizTopImageBean(R.mipmap.ic_quiz_title_image))
                            mView.addItem(QuizTitleBean("热门讨论"))
                        }
                        mView.addAll(it)
                    }
                } else {
                    mView.showPageEmpty()
                }
            }

            onFailed {
                mView.showPageError(it.getMsg())
            }
        }

    }

}