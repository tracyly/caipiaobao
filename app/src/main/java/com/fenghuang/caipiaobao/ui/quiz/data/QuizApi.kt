package com.fenghuang.caipiaobao.ui.quiz.data

import com.fenghuang.caipiaobao.data.api.ApiSubscriber
import com.fenghuang.caipiaobao.data.api.BaseApi

/**
 *  author : Peter
 *  date   : 2019/10/18 12:59
 *  desc   : 竞猜模块的所有接口
 */
object QuizApi : BaseApi {

    private const val QUIZ_ARTICLE = "/article/index"

    /**
     * 获取竞猜列表
     */
    fun getQuizArticleListResult(limit: Int, page: Int, function: ApiSubscriber<List<QuizResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<QuizResponse>>() {}
        subscriber.function()
        QuizApi.getAipQuizUrl().get<List<QuizResponse>>(QUIZ_ARTICLE)
//                .headers("Authorization", "Bearer " + SpUtils.getString(UserConstant.TOKEN))
                .params("limit", limit)
                .params("page", page)
                .subscribe(subscriber)
    }
}