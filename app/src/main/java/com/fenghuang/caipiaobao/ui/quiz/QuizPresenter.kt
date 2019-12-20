package com.fenghuang.caipiaobao.ui.quiz

import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.SpUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.constant.UserConstant
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import com.fenghuang.caipiaobao.ui.quiz.data.QuizApi
import com.fenghuang.caipiaobao.ui.quiz.data.QuizResponse
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog
import kotlinx.android.synthetic.main.fragment_quiz.*

/**
 *  author : Peter
 *  date   : 2019/10/17 20:26
 *  desc   : 竟猜p层
 */
class QuizPresenter : BaseMvpPresenter<QuizFragment>() {


    fun loadData(page: Int) {
        QuizApi.getQuizArticleListResult(10, page) {
            onSuccess {
                if (it.isNotEmpty()) {
                    if (mView.isActive()) {
                        mView.initRecycle(it)
                        mView.mPage++
                    }
                }

            }
            onFailed {
                ToastUtils.showNormal(it.getMsg())
            }
            mView.smartRefreshLayoutQuiz.finishRefresh()
        }
    }

    fun loadMore(page: Int) {
        QuizApi.getQuizArticleListResult(10, page) {
            onSuccess {
                if (it.isNotEmpty()) {
                    if (mView.isActive()) {
//                        if (page == mView.getStartPage()) {
//                            mView.clear()
//                            mView.addItem(QuizTopImageBean(R.mipmap.ic_quiz_title_image))
//                            mView.addItem(QuizTitleBean("热门讨论"))
//                        }
                        mView.addItem(it)
                    }
                }
            }
            onFailed {
                ToastUtils.showNormal(it.getMsg())
            }
            mView.smartRefreshLayoutQuiz.finishLoadMore()
        }
    }

    /**
     * 点赞
     */
    fun postQuizLike(bean: QuizResponse, articleId: Int, clickPosition: Int) {
        /**
         * 获取余额去判断是否被顶下去
         */
        MineApi.getUserBalance {
            onSuccess {
                QuizApi.getQuizArticleLikeResult(articleId, SpUtils.getInt(UserConstant.USER_ID)) {
                    onSuccess {
                        //                mView.notifyQuizHolder(articleId, clickPosition)
//                ToastUtils.showSuccess(it)
                    }
                    onFailed {
                        ExceptionDialog.showExpireDialog(mView.requireContext(), it)
                        mView.notifyQuizHolder(bean, articleId, clickPosition)
                    }
                }
            }
            onFailed { ExceptionDialog.showExpireDialog(mView.requireContext(), it) }
        }

    }

    /**
     * 顶部banner
     */

    fun getBanner() {
        QuizApi.getQuizTopBanner {
            onSuccess {
                if (mView.isActive()) {

                    mView.initBanner(it)
                }
            }
            onFailed { }
        }
    }


}